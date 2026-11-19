package com.uoi.pratikumwithbunafla.data.local

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.uoi.pratikumwithbunafla.MainActivity
import com.uoi.pratikumwithbunafla.data.model.WasteRecord
import com.uoi.pratikumwithbunafla.domain.WasteFlow
import org.json.JSONObject
import java.util.Locale

object TaskSystemNotifications {
    private const val CHANNEL_ID = "smart_waste_task_updates"
    private const val CHANNEL_NAME = "Tugas dan pembaruan"
    private const val PREFERENCES_NAME = "smart_waste_task_notification_state"
    private const val INITIAL_NOTIFICATION_ID = 0
    private const val OVERFLOW_NOTIFICATION_ID = -1
    private const val MAX_TASK_NOTIFICATIONS = 3

    fun createChannel(context: Context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return

        val channel = NotificationChannel(
            CHANNEL_ID,
            CHANNEL_NAME,
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = "Pemberitahuan tugas dan perubahan status dari Smart Waste."
        }
        context.getSystemService(NotificationManager::class.java)
            .createNotificationChannel(channel)
    }

    fun observeTasks(context: Context, username: String, tasks: List<WasteRecord>) {
        if (username.isBlank()) return

        val notifications = NotificationManagerCompat.from(context)
        if (!notifications.areNotificationsEnabled()) return
        if (
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        val normalizedUsername = username.trim().lowercase(Locale.ROOT)
        val preferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
        val stateKey = "tasks:$normalizedUsername"
        val previous = preferences.getString(stateKey, null)
            ?.let { saved -> runCatching { JSONObject(saved) }.getOrNull() }

        val current = JSONObject()
        tasks.filter { it.id > 0 }.forEach { task ->
            current.put(task.id.toString(), taskSignature(task))
        }

        val activeTasks = tasks.filter {
            it.id > 0 && WasteFlow.isInProgress(it.status)
        }
        if (previous == null) {
            if (activeTasks.isNotEmpty()) {
                post(
                    context = context,
                    notifications = notifications,
                    username = normalizedUsername,
                    notificationId = INITIAL_NOTIFICATION_ID,
                    title = "Ada tugas yang perlu ditangani",
                    body = "Kamu memiliki " + activeTasks.size + " tugas aktif. Buka Smart Waste untuk melihat detail."
                )
            }
        } else {
            val newTasks = activeTasks.filter { !previous.has(it.id.toString()) }
            newTasks.take(MAX_TASK_NOTIFICATIONS).forEach { task ->
                post(
                    context = context,
                    notifications = notifications,
                    username = normalizedUsername,
                    notificationId = task.id,
                    title = "Tugas baru untukmu",
                    body = taskDescription(task)
                )
            }
            val overflow = newTasks.size - MAX_TASK_NOTIFICATIONS
            if (overflow > 0) {
                post(
                    context = context,
                    notifications = notifications,
                    username = normalizedUsername,
                    notificationId = OVERFLOW_NOTIFICATION_ID,
                    title = "Ada tugas baru lainnya",
                    body = "Ada " + overflow + " tugas baru lainnya di daftar tugas."
                )
            }

            tasks.filter { task ->
                task.id > 0 &&
                    previous.has(task.id.toString()) &&
                    previous.optString(task.id.toString()) != taskSignature(task)
            }.forEach { task ->
                val needsRevision = task.completionStatus.equals(
                    "perlu_perbaikan",
                    ignoreCase = true
                )
                val title = when {
                    needsRevision -> "Bukti tugas perlu diperbaiki"
                    task.status == WasteFlow.DONE -> "Tugas selesai"
                    task.status == WasteFlow.REJECTED -> "Tugas ditolak"
                    else -> "Status tugas diperbarui"
                }
                val body = if (needsRevision) {
                    task.workerDecisionNote
                        .ifBlank { task.completionReviewNote }
                        .ifBlank { taskDescription(task) }
                } else {
                    (task.code.ifBlank { "Tugas #" + task.id }) + " · " + WasteFlow.label(task.status)
                }
                post(
                    context = context,
                    notifications = notifications,
                    username = normalizedUsername,
                    notificationId = task.id,
                    title = title,
                    body = body
                )
            }
        }

        preferences.edit().putString(stateKey, current.toString()).apply()
    }

    private fun taskSignature(task: WasteRecord): String = JSONObject()
        .put("status", task.status)
        .put("completionStatus", task.completionStatus)
        .put("completionReviewNote", task.completionReviewNote)
        .put("workerDecisionNote", task.workerDecisionNote)
        .toString()

    private fun taskDescription(task: WasteRecord): String {
        val reference = task.code.ifBlank { "Tugas #" + task.id }
        val location = task.location.takeIf { it.isNotBlank() && it != "-" }
        return if (location == null) {
            "$reference · Buka Smart Waste untuk melihat detail."
        } else {
            "$reference · $location"
        }
    }

    private fun post(
        context: Context,
        notifications: NotificationManagerCompat,
        username: String,
        notificationId: Int,
        title: String,
        body: String
    ) {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        }
        val pendingIntent = PendingIntent.getActivity(
            context,
            username.hashCode() xor notificationId,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle(title)
            .setContentText(body)
            .setStyle(NotificationCompat.BigTextStyle().bigText(body))
            .setContentIntent(pendingIntent)
            .setCategory(NotificationCompat.CATEGORY_REMINDER)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setVisibility(NotificationCompat.VISIBILITY_PRIVATE)
            .setAutoCancel(true)
            .build()

        runCatching {
            notifications.notify("tasks:$username", notificationId, notification)
        }
    }
}
