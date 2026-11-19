package com.uoi.pratikumwithbunafla.ui.admin

import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Inbox
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.uoi.pratikumwithbunafla.data.model.*
import com.uoi.pratikumwithbunafla.domain.WasteFlow
import com.uoi.pratikumwithbunafla.ui.components.*
import com.uoi.pratikumwithbunafla.ui.petugas.*
import com.uoi.pratikumwithbunafla.ui.theme.AppSurface
import com.uoi.pratikumwithbunafla.ui.theme.AppText
import com.uoi.pratikumwithbunafla.ui.theme.AppTextMuted

@Composable
fun NotificationCenterScreen(
    account: UserAccount,
    summary: AdminDashboardSummary,
    reports: List<WasteRecord>,
    tasks: List<WasteRecord>,
    incomingReports: List<WasteRecord>,
    completionReviews: List<TaskCompletionReview>,
    vehicleRepairs: List<VehicleRepair>,
    capacityClearanceRequests: List<CapacityClearanceRequest>,
    onNavigate: (String) -> Unit,
    onBack: () -> Unit,
    onLogout: () -> Unit,
    isInitialLoading: Boolean,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
) {
    BackHandler(onBack = onBack)

    val isAdmin = account.role == UserRole.ADMIN
    val notices = buildList {
        if (isAdmin) {
            val waitingReports = reports.filter { it.status == WasteFlow.WAITING }
            val latestWaitingReport = waitingReports.maxByOrNull { it.reportedAt }
            if (waitingReports.isNotEmpty())
                add(
                    WorkspaceNotice(
                        id = "admin_waiting_reports",
                        revision =
                            listOf(
                                    waitingReports.size,
                                    latestWaitingReport?.id,
                                    latestWaitingReport?.reportedAt.orEmpty(),
                                )
                                .joinToString(":"),
                        title = "Laporan menunggu verifikasi",
                        detail = "${waitingReports.size} laporan warga perlu diperiksa.",
                        count = waitingReports.size,
                        icon = Icons.Filled.Inbox,
                        accent = RoleOrange,
                        timestamp = latestWaitingReport?.reportedAt.orEmpty(),
                        destination = "status:${WasteFlow.WAITING}",
                    ),
                )

            if (completionReviews.isNotEmpty())
                add(
                    WorkspaceNotice(
                        id = "admin_completion_reviews",
                        revision =
                            listOf(
                                    completionReviews.size,
                                    completionReviews
                                        .maxByOrNull { it.submittedAt }
                                        ?.let { "${it.id}:${it.submittedAt}" }
                                        .orEmpty(),
                                )
                                .joinToString(":"),
                        title = "Bukti kerja menunggu persetujuan",
                        detail = "Periksa foto dan hasil tugas sebelum menyetujui penyelesaian.",
                        count = completionReviews.size,
                        icon = Icons.Filled.CheckCircle,
                        accent = RoleTeal,
                        timestamp =
                            completionReviews.maxByOrNull { it.submittedAt }?.submittedAt.orEmpty(),
                        destination = "ops:bukti",
                    ),
                )

            val locationEvidenceReports = reports.filter {
                !it.locationEvidencePhotoUrl.isNullOrBlank()
            }
            val latestLocationEvidence = locationEvidenceReports.maxByOrNull {
                it.locationEvidenceSubmittedAt
            }
            if (locationEvidenceReports.isNotEmpty())
                add(
                    WorkspaceNotice(
                        id = "admin_location_evidence",
                        revision =
                            listOf(
                                    locationEvidenceReports.size,
                                    latestLocationEvidence?.id,
                                    latestLocationEvidence?.locationEvidenceSubmittedAt.orEmpty(),
                                )
                                .joinToString(":"),
                        title = "Foto lokasi dari petugas",
                        detail = "Lihat bukti kondisi sampah di titik laporan.",
                        count = locationEvidenceReports.size,
                        icon = Icons.Filled.PhotoCamera,
                        accent = RoleTeal,
                        timestamp = latestLocationEvidence?.locationEvidenceSubmittedAt.orEmpty(),
                        destination = "ops:bukti",
                    ),
                )

            val repairsWaiting = vehicleRepairs.filter {
                it.status == "menunggu_persetujuan" || it.status == "menunggu_pemeriksaan"
            }
            val latestRepair = repairsWaiting.maxByOrNull { it.reportedAt }
            if (repairsWaiting.isNotEmpty())
                add(
                    WorkspaceNotice(
                        id = "admin_vehicle_repairs",
                        revision =
                            "${repairsWaiting.size}:${latestRepair?.id}:${latestRepair?.reportedAt.orEmpty()}",
                        title = "Perbaikan armada perlu ditinjau",
                        detail =
                            "Ada laporan kerusakan atau hasil perbaikan yang menunggu keputusan.",
                        count = repairsWaiting.size,
                        icon = Icons.Filled.Build,
                        accent = RoleOrange,
                        timestamp = latestRepair?.reportedAt.orEmpty(),
                        destination = "ops:armada",
                    ),
                )

            val clearancesWaiting = capacityClearanceRequests.filter { it.isPending }
            val latestClearance = clearancesWaiting.maxByOrNull { it.createdAt }
            if (clearancesWaiting.isNotEmpty())
                add(
                    WorkspaceNotice(
                        id = "admin_capacity_clearance",
                        revision =
                            "${clearancesWaiting.size}:${latestClearance?.id}:${latestClearance?.createdAt.orEmpty()}",
                        title = "Permintaan kapasitas menunggu",
                        detail =
                            "Tinjau bukti pengosongan TPA atau armada sebelum menyetujui reset.",
                        count = clearancesWaiting.size,
                        icon = Icons.Filled.LocalShipping,
                        accent = RolePurple,
                        timestamp = latestClearance?.createdAt.orEmpty(),
                        destination = "ops:pengosongan",
                    ),
                )
        } else {
            if (incomingReports.isNotEmpty())
                add(
                    WorkspaceNotice(
                        id = "worker_incoming_reports",
                        revision =
                            listOf(
                                    incomingReports.size,
                                    incomingReports
                                        .maxByOrNull { it.reportedAt }
                                        ?.let { "${it.id}:${it.reportedAt}" }
                                        .orEmpty(),
                                )
                                .joinToString(":"),
                        title = "Laporan baru tersedia",
                        detail =
                            "Pilih laporan yang siap ditangani dan cek tujuan pengangkutannya.",
                        count = incomingReports.size,
                        icon = Icons.Filled.Inbox,
                        accent = RoleOrange,
                        timestamp =
                            incomingReports.maxByOrNull { it.reportedAt }?.reportedAt.orEmpty(),
                        destination = "tasks",
                    ),
                )

            val revisionsNeeded = tasks.filter {
                it.completionStatus.equals("perlu_perbaikan", ignoreCase = true)
            }
            val latestRevision = revisionsNeeded.maxByOrNull { it.completionSubmittedAt }
            if (revisionsNeeded.isNotEmpty())
                add(
                    WorkspaceNotice(
                        id = "worker_proof_revisions",
                        revision =
                            listOf(
                                    revisionsNeeded.size,
                                    latestRevision?.id,
                                    latestRevision?.completionSubmittedAt.orEmpty(),
                                )
                                .joinToString(":"),
                        title = "Bukti perlu diperbaiki",
                        detail = "Periksa catatan Admin lalu kirim ulang foto penyelesaian.",
                        count = revisionsNeeded.size,
                        icon = Icons.Filled.CheckCircle,
                        accent = RoleOrange,
                        timestamp = latestRevision?.completionSubmittedAt.orEmpty(),
                        destination = "tasks",
                    ),
                )

            val reviewsPending = tasks.filter {
                it.status == WasteFlow.TRANSPORTED &&
                    it.completionStatus.equals("menunggu", ignoreCase = true)
            }
            val latestPendingReview = reviewsPending.maxByOrNull { it.completionSubmittedAt }
            if (reviewsPending.isNotEmpty())
                add(
                    WorkspaceNotice(
                        id = "worker_proof_reviews",
                        revision =
                            listOf(
                                    reviewsPending.size,
                                    latestPendingReview?.id,
                                    latestPendingReview?.completionSubmittedAt.orEmpty(),
                                )
                                .joinToString(":"),
                        title = "Bukti sedang diperiksa",
                        detail = "Admin sedang meninjau foto dan catatan penyelesaian tugas.",
                        count = reviewsPending.size,
                        icon = Icons.Filled.CheckCircle,
                        accent = RoleTeal,
                        timestamp = latestPendingReview?.completionSubmittedAt.orEmpty(),
                        destination = "tasks",
                    ),
                )

            val activeTasks = tasks.filter { WasteFlow.isInProgress(it.status) }
            val latestActiveTask = activeTasks.maxByOrNull { it.reportedAt }
            if (activeTasks.isNotEmpty())
                add(
                    WorkspaceNotice(
                        id = "worker_active_tasks",
                        revision =
                            "${activeTasks.size}:${latestActiveTask?.id}:${latestActiveTask?.reportedAt.orEmpty()}",
                        title = "Tugas masih berjalan",
                        detail =
                            "Lanjutkan pengangkutan dan kirim bukti setelah pekerjaan selesai.",
                        count = activeTasks.size,
                        icon = Icons.Filled.LocalShipping,
                        accent = RolePurple,
                        timestamp = latestActiveTask?.reportedAt.orEmpty(),
                        destination = "tasks",
                    ),
                )
        }
    }
    val context = LocalContext.current
    val readStateKey = "${account.role.name}:${account.username.lowercase(java.util.Locale.ROOT)}"
    val readPreferences =
        remember(context) {
            context.getSharedPreferences("smart_waste_notification_reads", Context.MODE_PRIVATE)
        }
    val readRevisions = remember(readStateKey) { mutableStateMapOf<String, String>() }
    LaunchedEffect(readStateKey, notices.map { it.id to it.revision }) {
        notices.forEach { notice ->
            readPreferences.getString("$readStateKey:${notice.id}", null)?.let { savedRevision ->
                readRevisions[notice.id] = savedRevision
            }
        }
    }
    val markNoticeRead: (WorkspaceNotice) -> Unit = { notice ->
        readPreferences.edit().putString("$readStateKey:${notice.id}", notice.revision).apply()
        readRevisions[notice.id] = notice.revision
    }

    AdminPage(
        current = "notifications",
        account = account,
        onNavigate = onNavigate,
        onLogout = onLogout,
        isInitialLoading = isInitialLoading,
        isRefreshing = isRefreshing,
        onRefresh = onRefresh,
        showBottomBar = false,
        showNotificationsButton = false,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            IconButton(onClick = onBack, modifier = Modifier.size(40.dp)) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Kembali",
                    tint = LoginAccent,
                )
            }
            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text(
                    "Notifikasi",
                    color = AppText,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.SemiBold,
                )
                Text(
                    if (isAdmin) "Pembaruan laporan dan operasional"
                    else "Pembaruan pekerjaan petugas",
                    color = AppTextMuted,
                    fontSize = 13.sp,
                )
            }
        }

        if (isAdmin) {
            AppCard(modifier = Modifier.fillMaxWidth(), containerColor = AppSurface) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    Box(
                        modifier =
                            Modifier.size(38.dp)
                                .clip(RoundedCornerShape(13.dp))
                                .background(LoginAccent.copy(alpha = 0.12f)),
                        contentAlignment = Alignment.Center,
                    ) {
                        Icon(
                            Icons.Filled.Inbox,
                            contentDescription = null,
                            tint = LoginAccent,
                            modifier = Modifier.size(20.dp),
                        )
                    }
                    Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
                        Text(
                            "Ringkasan laporan",
                            color = AppText,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                        )
                        Text(
                            "${summary.totalReports} laporan · ${summary.totalWeightKg.pretty()} kg tercatat",
                            color = AppTextMuted,
                            fontSize = 13.sp,
                            lineHeight = 18.sp,
                        )
                    }
                }
            }
        }

        SectionTitle(
            "Perlu ditangani",
            if (isAdmin) "Pembaruan yang membutuhkan perhatian Admin."
            else "Pembaruan tugas dan bukti pekerjaanmu.",
        )

        if (notices.isEmpty()) {
            AppCard(modifier = Modifier.fillMaxWidth(), containerColor = AppSurface) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    Box(
                        modifier =
                            Modifier.size(38.dp)
                                .clip(RoundedCornerShape(13.dp))
                                .background(LoginAccent.copy(alpha = 0.12f)),
                        contentAlignment = Alignment.Center,
                    ) {
                        Icon(
                            Icons.Filled.Notifications,
                            contentDescription = null,
                            tint = LoginAccent,
                            modifier = Modifier.size(20.dp),
                        )
                    }
                    Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
                        Text(
                            "Belum ada notifikasi",
                            color = AppText,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                        )
                        Text(
                            "Pembaruan berikutnya akan muncul di halaman ini.",
                            color = AppTextMuted,
                            fontSize = 13.sp,
                            lineHeight = 18.sp,
                        )
                    }
                }
            }
        } else {
            AppCard(modifier = Modifier.fillMaxWidth(), containerColor = AppSurface) {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 15.dp, vertical = 4.dp),
                    verticalArrangement = Arrangement.spacedBy(0.dp),
                ) {
                    notices.forEachIndexed { index, notice ->
                        val isRead = readRevisions[notice.id] == notice.revision
                        Row(
                            modifier =
                                Modifier.fillMaxWidth()
                                    .then(
                                        if (notice.destination != null)
                                            Modifier.clickable {
                                                markNoticeRead(notice)
                                                onNavigate(notice.destination)
                                            }
                                        else Modifier,
                                    )
                                    .padding(vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                        ) {
                            Box(
                                modifier =
                                    Modifier.size(38.dp)
                                        .clip(RoundedCornerShape(13.dp))
                                        .background(notice.accent.copy(alpha = 0.12f)),
                                contentAlignment = Alignment.Center,
                            ) {
                                Icon(
                                    notice.icon,
                                    contentDescription = null,
                                    tint = notice.accent,
                                    modifier = Modifier.size(19.dp),
                                )
                            }
                            Column(
                                modifier = Modifier.weight(1f),
                                verticalArrangement = Arrangement.spacedBy(3.dp),
                            ) {
                                Text(
                                    notice.title,
                                    color = AppText,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.SemiBold,
                                )
                                Text(
                                    notice.detail,
                                    color = AppTextMuted,
                                    fontSize = 12.sp,
                                    lineHeight = 17.sp,
                                )
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    Text(
                                        notice.timestamp
                                            .takeIf { it.isNotBlank() }
                                            ?.toDisplayDateTime() ?: "Waktu belum tersedia",
                                        color = AppTextMuted,
                                        fontSize = 11.sp,
                                    )
                                    Text(
                                        if (isRead) "Sudah dilihat" else "Baru",
                                        color = if (isRead) AppTextMuted else notice.accent,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.SemiBold,
                                    )
                                }
                            }
                            Column(
                                horizontalAlignment = Alignment.End,
                                verticalArrangement = Arrangement.spacedBy(3.dp),
                            ) {
                                Text(
                                    notice.count.toString(),
                                    color = notice.accent,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                )
                                if (notice.destination != null) {
                                    Text(
                                        "Buka",
                                        color = LoginAccent,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.SemiBold,
                                    )
                                }
                            }
                        }
                        if (index < notices.lastIndex) AppCardDivider()
                    }
                }
            }
        }
    }
}

internal data class WorkspaceNotice(
    val id: String,
    val revision: String,
    val title: String,
    val detail: String,
    val count: Int,
    val icon: ImageVector,
    val accent: Color,
    val timestamp: String = "",
    val destination: String? = null,
)
