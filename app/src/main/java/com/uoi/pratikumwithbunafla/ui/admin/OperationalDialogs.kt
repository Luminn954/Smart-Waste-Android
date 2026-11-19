package com.uoi.pratikumwithbunafla.ui.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Inbox
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.uoi.pratikumwithbunafla.data.model.*
import com.uoi.pratikumwithbunafla.ui.components.*
import com.uoi.pratikumwithbunafla.ui.petugas.*
import com.uoi.pratikumwithbunafla.ui.theme.AppSurface
import com.uoi.pratikumwithbunafla.ui.theme.AppText
import com.uoi.pratikumwithbunafla.ui.theme.AppTextMuted
import com.uoi.pratikumwithbunafla.ui.theme.LocalSmartWasteAppearance
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch

@Composable
internal fun PetugasEmptyState(title: String, message: String) {
    val isDark = LocalSmartWasteAppearance.current.darkMode
    val accent = if (isDark) Color(0xFFB9D4BE) else Color(0xFF286947)
    AppCard(modifier = Modifier.fillMaxWidth(), containerColor = AppSurface) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(17.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Box(
                modifier =
                    Modifier.size(40.dp).clip(CircleShape).background(accent.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    Icons.Filled.Inbox,
                    contentDescription = null,
                    tint = accent,
                    modifier = Modifier.size(21.dp),
                )
            }
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(title, color = AppText, fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
                Text(message, color = AppTextMuted, fontSize = 13.sp, lineHeight = 19.sp)
            }
        }
    }
}

@Composable
internal fun StaffAccountFormDialog(
    onDismiss: () -> Unit,
    onSave: suspend (String, String, String, String, String) -> Unit,
) {
    var name by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var zone by remember { mutableStateOf("") }
    var saving by remember { mutableStateOf(false) }
    var saveError by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                "Tambah akun petugas",
                color = LoginInk,
                fontWeight = FontWeight.Bold,
            )
        },
        text = {
            ScrollableDialogContent {
                Text(
                    "Akun staf masuk memakai username dan kata sandi yang dibuat di sini.",
                    color = LoginMuted,
                    fontSize = 11.sp,
                )
                Text("Role akun · Petugas", color = AppTextMuted, fontSize = 11.sp)
                FormField(name, { name = it.normalizePersonNameInput() }, "Nama lengkap")
                FormField(username, { username = it }, "Username")
                FormField(email, { email = it }, "Email akun")
                FormField(password, { password = it }, "Password awal (minimal 8 karakter)")
                FormField(zone, { zone = it }, "Zona tugas")
                if (saveError.isNotBlank()) InlineError(saveError)
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (!saving)
                        scope.launch {
                            saving = true
                            saveError = ""
                            try {
                                onSave(
                                    name.displayPersonName(),
                                    username.trim(),
                                    email.trim(),
                                    password,
                                    zone.trim(),
                                )
                            } catch (cancelled: CancellationException) {
                                throw cancelled
                            } catch (error: Exception) {
                                saveError = error.message ?: "Akun staf gagal dibuat."
                            } finally {
                                saving = false
                            }
                        }
                },
                enabled =
                    name.isNotBlank() &&
                        username.trim().length >= 3 &&
                        email.trim().matches(Regex("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")) &&
                        password.length >= 8 &&
                        zone.isNotBlank() &&
                        !saving,
            ) {
                Text(if (saving) "Menyimpan…" else "Simpan", color = LoginAccent)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss, enabled = !saving) {
                Text(
                    "Batal",
                    color = LoginMuted,
                )
            }
        },
        containerColor = AppSurface,
    )
}

@Composable
internal fun vehicleRepairAccent(status: String): Color =
    when (status) {
        "menunggu_persetujuan",
        "menunggu_pemeriksaan" -> RoleOrange
        "dalam_perbaikan",
        "disetujui" -> LoginAccent
        "perlu_perbaikan" -> RolePurple
        "selesai" -> RoleGreen
        else -> RoleRed
    }

internal fun vehicleRepairEventLabel(action: String): String =
    when (action) {
        "dilaporkan" -> "Kerusakan dilaporkan"
        "disetujui_admin" -> "Disetujui Admin"
        "ditolak_admin" -> "Ditolak Admin"
        "perbaikan_dimulai" -> "Perbaikan dimulai"
        "progres_diperbarui" -> "Progres perbaikan"
        "diajukan_untuk_pemeriksaan" -> "Diajukan untuk pemeriksaan"
        "perlu_perbaikan" -> "Diminta perbaikan ulang"
        "perbaikan_disahkan" -> "Kendaraan dinyatakan layak"
        else -> action.replace('_', ' ').replaceFirstChar { it.uppercase() }
    }

@Composable
internal fun VehicleRepairCard(
    repair: VehicleRepair,
    actions: @Composable (VehicleRepair) -> Unit,
) {
    val timelineEvents = repair.timeline.filter { it.action != "dilaporkan" }
    var showFullTimeline by rememberSaveable(repair.id) { mutableStateOf(false) }
    AppCard(
        modifier = Modifier.fillMaxWidth(),
        containerColor = AppSurface,
        accentColor = vehicleRepairAccent(repair.status),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 14.dp, vertical = 13.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                Box(
                    modifier =
                        Modifier.size(38.dp)
                            .clip(RoundedCornerShape(13.dp))
                            .background(vehicleRepairAccent(repair.status).copy(alpha = 0.12f)),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        Icons.Filled.Build,
                        contentDescription = null,
                        tint = vehicleRepairAccent(repair.status),
                        modifier = Modifier.size(20.dp),
                    )
                }
                Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
                    Text(
                        repair.plate,
                        color = AppText,
                        fontSize = 15.sp,
                        lineHeight = 20.sp,
                        fontWeight = FontWeight.Bold,
                    )
                    Text(
                        "${repair.reporterName.displayPersonName()} · ${repair.reportedAt}",
                        color = AppTextMuted,
                        fontSize = 11.sp,
                        lineHeight = 15.sp,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
                Box(
                    modifier =
                        Modifier.clip(RoundedCornerShape(50.dp))
                            .background(vehicleRepairAccent(repair.status).copy(alpha = 0.12f))
                            .padding(horizontal = 9.dp, vertical = 5.dp),
                ) {
                    Text(
                        repair.statusLabel,
                        color = vehicleRepairAccent(repair.status),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                    )
                }
            }
            Text(
                "Penyebab · ${repair.cause}",
                color = AppText,
                fontSize = 13.sp,
                lineHeight = 18.sp,
                fontWeight = FontWeight.SemiBold,
            )
            Text(
                repair.details,
                color = AppTextMuted,
                fontSize = 12.sp,
                lineHeight = 18.sp,
                maxLines = 4,
                overflow = TextOverflow.Ellipsis,
            )
            if (repair.activeTaskCount > 0) {
                Text(
                    "Ada ${repair.activeTaskCount} tugas aktif terkait armada ini; " +
                        "cek penugasannya sebelum kendaraan dipakai lagi.",
                    color = RoleRed,
                    fontSize = 11.sp,
                    lineHeight = 16.sp,
                )
            }
            if (timelineEvents.isNotEmpty()) AppCardDivider()
            (if (showFullTimeline) timelineEvents else timelineEvents.takeLast(3)).forEach { event
                ->
                Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                    Text(
                        vehicleRepairEventLabel(event.action),
                        color = AppText,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                    )
                    Text(
                        listOf(
                                event.actorName.displayPersonName(),
                                event.actorRole,
                                event.createdAt,
                            )
                            .filter(String::isNotBlank)
                            .joinToString(" · "),
                        color = AppTextMuted,
                        fontSize = 10.sp,
                        lineHeight = 14.sp,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                    )
                    if (event.note.isNotBlank()) {
                        Text(
                            event.note,
                            color = AppTextMuted,
                            fontSize = 11.sp,
                            lineHeight = 16.sp,
                            maxLines = 3,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                }
            }
            if (timelineEvents.size > 3) {
                TextButton(onClick = { showFullTimeline = !showFullTimeline }) {
                    Text(
                        if (showFullTimeline) "Ringkas riwayat"
                        else "Lihat semua ${timelineEvents.size} langkah",
                        color = LoginAccent,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.SemiBold,
                    )
                }
            }
            if (
                repair.adminNote.isNotBlank() &&
                    repair.timeline.lastOrNull()?.note != repair.adminNote
            ) {
                Text(
                    "Keputusan Admin · ${repair.adminNote}",
                    color = RoleOrange,
                    fontSize = 11.sp,
                    lineHeight = 16.sp,
                )
            }
            actions(repair)
        }
    }
}

@Composable
internal fun VehicleDamageDialog(
    vehicle: WasteVehicle,
    onDismiss: () -> Unit,
    onSubmit: suspend (String, String) -> Unit,
) {
    var cause by remember(vehicle.id) { mutableStateOf("") }
    var details by remember(vehicle.id) { mutableStateOf("") }
    var sending by remember(vehicle.id) { mutableStateOf(false) }
    var error by remember(vehicle.id) { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    AlertDialog(
        onDismissRequest = { if (!sending) onDismiss() },
        title = { Text("Laporkan kerusakan", color = LoginInk, fontWeight = FontWeight.Bold) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(9.dp)) {
                Text(
                    "${vehicle.plate} · kendaraan akan dikunci dari penugasan baru " +
                        "sampai Admin memeriksa perbaikannya.",
                    color = AppTextMuted,
                    fontSize = 11.sp,
                    lineHeight = 16.sp,
                )
                OutlinedTextField(
                    value = cause,
                    onValueChange = { cause = it.take(180) },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Penyebab yang diketahui") },
                    placeholder = { Text("Contoh: ban bocor, rem, mesin") },
                    singleLine = true,
                    enabled = !sending,
                )
                OutlinedTextField(
                    value = details,
                    onValueChange = { details = it.take(4000) },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Gejala dan kronologi") },
                    placeholder = { Text("Kapan terjadi, kondisi kendaraan, dan dampaknya") },
                    minLines = 3,
                    maxLines = 5,
                    enabled = !sending,
                )
                if (error.isNotBlank()) InlineError(error)
            }
        },
        confirmButton = {
            TextButton(
                enabled = cause.trim().length >= 3 && details.trim().length >= 8 && !sending,
                onClick = {
                    scope.launch {
                        sending = true
                        error = ""
                        try {
                            onSubmit(cause.trim(), details.trim())
                        } catch (cancelled: CancellationException) {
                            throw cancelled
                        } catch (failure: Exception) {
                            error = failure.message ?: "Laporan kerusakan gagal dikirim."
                        } finally {
                            sending = false
                        }
                    }
                },
            ) {
                Text(if (sending) "Mengirim…" else "Kirim laporan", color = RoleRed)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss, enabled = !sending) {
                Text("Batal", color = LoginMuted)
            }
        },
        containerColor = AppSurface,
    )
}

@Composable
internal fun VehicleRepairProcessDialog(
    repair: VehicleRepair,
    action: String,
    onDismiss: () -> Unit,
    onSubmit: suspend (String) -> Unit,
) {
    val isInspection = action == "pemeriksaan"
    val isStarting = action == "mulai"
    var note by remember(repair.id, action) { mutableStateOf("") }
    var sending by remember(repair.id, action) { mutableStateOf(false) }
    var error by remember(repair.id, action) { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val title =
        when {
            isInspection -> "Minta pemeriksaan Admin"
            isStarting -> "Mulai perbaikan"
            else -> "Catat progres perbaikan"
        }
    AlertDialog(
        onDismissRequest = { if (!sending) onDismiss() },
        title = { Text(title, color = LoginInk, fontWeight = FontWeight.Bold) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("${repair.plate} · ${repair.cause}", color = AppTextMuted, fontSize = 11.sp)
                OutlinedTextField(
                    value = note,
                    onValueChange = { note = it.take(800) },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(if (isInspection) "Hasil perbaikan" else "Catatan proses") },
                    placeholder = {
                        Text(
                            when {
                                isInspection ->
                                    "Sebutkan tindakan, hasil uji, dan kondisi akhir kendaraan"
                                isStarting -> "Tuliskan langkah perbaikan pertama"
                                else -> "Sebutkan tindakan atau komponen yang ditangani"
                            },
                        )
                    },
                    minLines = 3,
                    maxLines = 5,
                    enabled = !sending,
                )
                if (error.isNotBlank()) InlineError(error)
            }
        },
        confirmButton = {
            TextButton(
                enabled = note.trim().length >= 5 && !sending,
                onClick = {
                    scope.launch {
                        sending = true
                        error = ""
                        try {
                            onSubmit(note.trim())
                        } catch (cancelled: CancellationException) {
                            throw cancelled
                        } catch (failure: Exception) {
                            error = failure.message ?: "Proses perbaikan gagal disimpan."
                        } finally {
                            sending = false
                        }
                    }
                },
            ) {
                Text(
                    if (sending) "Menyimpan…" else if (isInspection) "Kirim ke Admin" else "Simpan",
                    color = LoginAccent,
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss, enabled = !sending) {
                Text("Batal", color = LoginMuted)
            }
        },
        containerColor = AppSurface,
    )
}

@Composable
internal fun VehicleRepairReviewDialog(
    repair: VehicleRepair,
    action: String,
    onDismiss: () -> Unit,
    onSubmit: suspend (String) -> Unit,
) {
    val requiresNote = action == "tolak" || action == "kembalikan"
    var note by remember(repair.id, action) { mutableStateOf("") }
    var sending by remember(repair.id, action) { mutableStateOf(false) }
    var error by remember(repair.id, action) { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val title =
        when (action) {
            "setujui" -> "Setujui perbaikan"
            "tolak" -> "Tolak laporan kerusakan"
            "kembalikan" -> "Minta perbaikan ulang"
            else -> "Sahkan kendaraan"
        }
    val confirmation =
        when (action) {
            "setujui" ->
                "Kendaraan tetap tidak tersedia sampai perbaikan selesai dan hasilnya disetujui Admin."
            "selesai" ->
                "Setelah pemeriksaan ini disetujui, kondisi kendaraan " +
                    "akan kembali Baik dan bisa dipilih untuk penugasan."
            "tolak" ->
                "Kendaraan akan tetap berstatus Rusak. Jelaskan alasan penolakan agar Petugas tahu tindak lanjutnya."
            else -> "Kendaraan tetap tidak tersedia. Jelaskan bagian yang masih perlu diperbaiki."
        }
    AlertDialog(
        onDismissRequest = { if (!sending) onDismiss() },
        title = { Text(title, color = LoginInk, fontWeight = FontWeight.Bold) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    "${repair.plate} · ${repair.reporterName.displayPersonName()}",
                    color = AppTextMuted,
                    fontSize = 11.sp,
                )
                Text(confirmation, color = AppText, fontSize = 12.sp, lineHeight = 17.sp)
                if (action == "tolak" || action == "kembalikan" || action == "selesai") {
                    OutlinedTextField(
                        value = note,
                        onValueChange = { note = it.take(800) },
                        modifier = Modifier.fillMaxWidth(),
                        label = {
                            Text(
                                if (action == "selesai") "Catatan pemeriksaan (opsional)"
                                else "Alasan keputusan",
                            )
                        },
                        placeholder = { Text("Tulis alasan atau hasil pemeriksaan") },
                        minLines = 2,
                        maxLines = 4,
                        enabled = !sending,
                    )
                }
                if (error.isNotBlank()) InlineError(error)
            }
        },
        confirmButton = {
            TextButton(
                enabled = (!requiresNote || note.trim().length >= 5) && !sending,
                onClick = {
                    scope.launch {
                        sending = true
                        error = ""
                        try {
                            onSubmit(note.trim())
                        } catch (cancelled: CancellationException) {
                            throw cancelled
                        } catch (failure: Exception) {
                            error = failure.message ?: "Keputusan Admin gagal disimpan."
                        } finally {
                            sending = false
                        }
                    }
                },
            ) {
                Text(
                    if (sending) "Menyimpan…" else title,
                    color = if (action == "tolak") RoleRed else LoginAccent,
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss, enabled = !sending) {
                Text("Batal", color = LoginMuted)
            }
        },
        containerColor = AppSurface,
    )
}

@Composable
internal fun VehicleFormDialog(
    vehicle: WasteVehicle?,
    onDismiss: () -> Unit,
    onSave: suspend (String, Double, String) -> Unit,
) {
    var plate by remember(vehicle?.id) { mutableStateOf(vehicle?.plate.orEmpty()) }
    var capacity by
        remember(vehicle?.id) {
            mutableStateOf(
                vehicle?.capacityKg?.toString().orEmpty(),
            )
        }
    var route by remember(vehicle?.id) { mutableStateOf(vehicle?.route.orEmpty()) }
    var saving by remember(vehicle?.id) { mutableStateOf(false) }
    var saveError by remember(vehicle?.id) { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    AlertDialog(
        onDismissRequest = { if (!saving) onDismiss() },
        title = {
            Text(
                if (vehicle == null) "Tambah kendaraan" else "Ubah kendaraan",
                color = LoginInk,
                fontWeight = FontWeight.Bold,
            )
        },
        text = {
            ScrollableDialogContent {
                FormField(plate, { plate = it }, "Nomor polisi")
                FormField(capacity, { capacity = it }, "Kapasitas (kg)")
                Text(
                    if (vehicle == null)
                        "Kendaraan baru tercatat Baik. Kerusakan dilaporkan melalui alur persetujuan Admin."
                    else
                        "Kondisi saat ini · ${vehicle.condition}. Perubahan kondisi mengikuti proses perbaikan Admin.",
                    color = AppTextMuted,
                    fontSize = 11.sp,
                    lineHeight = 16.sp,
                )
                FormField(route, { route = it }, "Rute")
                if (vehicle != null)
                    Text(
                        "Terpakai ${vehicle.loadKg.pretty()} kg · dicadangkan ${vehicle.reservedKg.pretty()} kg",
                        color = LoginMuted,
                        fontSize = 11.sp,
                    )
                if (saveError.isNotBlank()) InlineError(saveError)
            }
        },
        confirmButton = {
            val proposedCapacity = capacity.toDoubleOrNull() ?: 0.0
            val canFitCurrentLoad =
                vehicle == null || proposedCapacity >= vehicle.loadKg + vehicle.reservedKg
            TextButton(
                onClick = {
                    if (!saving)
                        scope.launch {
                            saving = true
                            saveError = ""
                            try {
                                onSave(plate.trim(), proposedCapacity, route.trim())
                            } catch (cancelled: CancellationException) {
                                throw cancelled
                            } catch (error: Exception) {
                                saveError = error.message ?: "Data kendaraan gagal disimpan."
                            } finally {
                                saving = false
                            }
                        }
                },
                enabled =
                    plate.isNotBlank() && proposedCapacity > 0 && canFitCurrentLoad && !saving,
            ) {
                Text(if (saving) "Menyimpan…" else "Simpan", color = LoginAccent)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss, enabled = !saving) {
                Text(
                    "Batal",
                    color = LoginMuted,
                )
            }
        },
        containerColor = AppSurface,
    )
}

@Composable
internal fun FacilityFormDialog(
    facility: WasteFacility?,
    onDismiss: () -> Unit,
    onSave: suspend (String, String, Double) -> Unit,
) {
    var name by remember(facility?.id) { mutableStateOf(facility?.name.orEmpty()) }
    var address by remember(facility?.id) { mutableStateOf(facility?.address.orEmpty()) }
    var capacity by
        remember(facility?.id) {
            mutableStateOf(
                facility?.capacityTon?.toString().orEmpty(),
            )
        }
    var saving by remember(facility?.id) { mutableStateOf(false) }
    var saveError by remember(facility?.id) { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    AlertDialog(
        onDismissRequest = { if (!saving) onDismiss() },
        title = {
            Text(
                if (facility == null) "Tambah TPA" else "Ubah TPA",
                color = LoginInk,
                fontWeight = FontWeight.Bold,
            )
        },
        text = {
            ScrollableDialogContent {
                FormField(name, { name = it }, "Nama TPA")
                FormField(address, { address = it }, "Alamat")
                FormField(capacity, { capacity = it }, "Kapasitas (ton)")
                if (facility != null)
                    Text(
                        "Terisi: ${facility.filledTon.pretty()} ton",
                        color = LoginMuted,
                        fontSize = 11.sp,
                    )
                if (saveError.isNotBlank()) InlineError(saveError)
            }
        },
        confirmButton = {
            val proposedCapacity = capacity.toDoubleOrNull() ?: 0.0
            val minimumCapacity = (facility?.filledTon ?: 0.0) + (facility?.reservedTon ?: 0.0)
            TextButton(
                onClick = {
                    if (!saving)
                        scope.launch {
                            saving = true
                            saveError = ""
                            try {
                                onSave(name.trim(), address.trim(), proposedCapacity)
                            } catch (cancelled: CancellationException) {
                                throw cancelled
                            } catch (error: Exception) {
                                saveError = error.message ?: "Data TPA gagal disimpan."
                            } finally {
                                saving = false
                            }
                        }
                },
                enabled =
                    name.isNotBlank() &&
                        proposedCapacity >= minimumCapacity &&
                        proposedCapacity > 0 &&
                        !saving,
            ) {
                Text(if (saving) "Menyimpan…" else "Simpan", color = LoginAccent)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss, enabled = !saving) {
                Text(
                    "Batal",
                    color = LoginMuted,
                )
            }
        },
        containerColor = AppSurface,
    )
}

@Composable
internal fun SingleFieldDialog(
    title: String,
    initial: String,
    label: String,
    onDismiss: () -> Unit,
    onSave: suspend (String) -> Unit,
) {
    var value by remember(title, initial) { mutableStateOf(initial) }
    var saving by remember(title, initial) { mutableStateOf(false) }
    var saveError by remember(title, initial) { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(title, color = LoginInk, fontWeight = FontWeight.Bold) },
        text = {
            ScrollableDialogContent {
                FormField(
                    value,
                    { value = it },
                    label,
                )
                if (saveError.isNotBlank()) InlineError(saveError)
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (!saving)
                        scope.launch {
                            saving = true
                            saveError = ""
                            try {
                                onSave(value.trim())
                            } catch (cancelled: CancellationException) {
                                throw cancelled
                            } catch (error: Exception) {
                                saveError = error.message ?: "Perubahan gagal disimpan."
                            } finally {
                                saving = false
                            }
                        }
                },
                enabled = value.isNotBlank() && !saving,
            ) {
                Text(if (saving) "Menyimpan…" else "Simpan", color = LoginAccent)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss, enabled = !saving) {
                Text(
                    "Batal",
                    color = LoginMuted,
                )
            }
        },
        containerColor = AppSurface,
    )
}
