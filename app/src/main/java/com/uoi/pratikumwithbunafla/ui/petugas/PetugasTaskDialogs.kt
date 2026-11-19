package com.uoi.pratikumwithbunafla.ui.petugas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.uoi.pratikumwithbunafla.data.model.*
import com.uoi.pratikumwithbunafla.ui.admin.*
import com.uoi.pratikumwithbunafla.ui.components.*
import com.uoi.pratikumwithbunafla.ui.theme.AppSurface
import com.uoi.pratikumwithbunafla.ui.theme.AppText
import com.uoi.pratikumwithbunafla.ui.theme.AppTextMuted
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch

@Composable
internal fun CapacityLimitNotice(
    title: String,
    detail: String,
    status: String?,
    actionLabel: String?,
    onAction: () -> Unit,
) {
    Column(
        modifier =
            Modifier.fillMaxWidth()
                .clip(RoundedCornerShape(13.dp))
                .background(RoleOrange.copy(alpha = 0.11f))
                .padding(horizontal = 10.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(3.dp),
    ) {
        Text(title, color = RoleOrange, fontSize = 11.sp, fontWeight = FontWeight.Bold)
        Text(detail, color = AppTextMuted, fontSize = 10.sp, lineHeight = 14.sp)
        if (!status.isNullOrBlank()) {
            Text(status, color = AppText, fontSize = 10.sp, fontWeight = FontWeight.Medium)
        }
        if (actionLabel != null) {
            TextButton(
                onClick = onAction,
                contentPadding =
                    androidx.compose.foundation.layout.PaddingValues(
                        horizontal = 0.dp,
                        vertical = 0.dp,
                    ),
            ) {
                Text(
                    actionLabel,
                    color = LoginAccent,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                )
            }
        }
    }
}

internal fun clearanceStatusText(request: CapacityClearanceRequest): String =
    when (request.status.lowercase()) {
        "menunggu" -> "Menunggu pemeriksaan Admin"
        "disetujui" -> "Disetujui · kapasitas sudah dikosongkan"
        "ditolak" ->
            "Ditolak${request.adminNote.takeIf { it.isNotBlank() }?.let { ": $it" }.orEmpty()}"
        else -> request.status.replace('_', ' ').replaceFirstChar { it.uppercase() }
    }

@Composable
internal fun PetugasAssignmentDialog(
    report: WasteRecord,
    vehicles: List<WasteVehicle>,
    facilities: List<WasteFacility>,
    onDismiss: () -> Unit,
    onAssign: suspend (Int, Int) -> Unit,
) {
    var selectedVehicle by remember(report.id) { mutableStateOf<WasteVehicle?>(null) }
    var selectedFacility by remember(report.id) { mutableStateOf<WasteFacility?>(null) }
    var saving by remember(report.id) { mutableStateOf(false) }
    var saveError by remember(report.id) { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val eligibleVehicles = vehicles.filter {
        it.condition.equals("baik", ignoreCase = true) && it.availableKg >= report.weightKg
    }
    val eligibleFacilities = facilities.filter {
        it.remainingTon * 1000 >= report.weightKg
    }
    val canAssign =
        selectedVehicle?.let(eligibleVehicles::contains) == true &&
            selectedFacility?.let(eligibleFacilities::contains) == true

    AlertDialog(
        onDismissRequest = { if (!saving) onDismiss() },
        title = { Text("Terima & tugaskan", color = LoginInk, fontWeight = FontWeight.Bold) },
        text = {
            ScrollableDialogContent(spacing = 8.dp) {
                Text(
                    "${report.code} · ${report.weightKg.pretty()} kg",
                    color = LoginMuted,
                    fontSize = 11.sp,
                )
                Text(
                    "Pilih armada dan TPA. Laporan akan menjadi tugas milik akun Petugas yang sedang masuk.",
                    color = AppText,
                    fontSize = 11.sp,
                )
                SelectionField(
                    "Kendaraan angkut",
                    selectedVehicle?.let { "${it.plate} · sisa ${it.availableKg.pretty()} kg" },
                    eligibleVehicles,
                    { "${it.plate} · sisa ${it.availableKg.pretty()} kg" },
                ) {
                    selectedVehicle = it
                }
                SelectionField(
                    "TPA tujuan",
                    selectedFacility?.let { "${it.name} · sisa ${it.remainingTon.pretty()} ton" },
                    eligibleFacilities,
                    { "${it.name} · sisa ${it.remainingTon.pretty()} ton" },
                ) {
                    selectedFacility = it
                }
                if (eligibleVehicles.isEmpty() || eligibleFacilities.isEmpty()) {
                    val unavailable = buildList {
                        if (eligibleVehicles.isEmpty())
                            add("kendaraan sehat dengan kapasitas cukup")
                        if (eligibleFacilities.isEmpty()) add("TPA yang kapasitasnya cukup")
                    }
                        .joinToString(" dan ")
                    Text(
                        "Belum dapat diterima: tidak tersedia $unavailable.",
                        color = RoleRed,
                        fontSize = 10.sp,
                    )
                }
                if (saveError.isNotBlank()) InlineError(saveError)
            }
        },
        confirmButton = {
            TextButton(
                enabled = canAssign && !saving,
                onClick = {
                    val vehicleId = selectedVehicle?.id ?: return@TextButton
                    val tpaId = selectedFacility?.id ?: return@TextButton
                    scope.launch {
                        saving = true
                        saveError = ""
                        try {
                            onAssign(vehicleId, tpaId)
                        } catch (cancelled: CancellationException) {
                            throw cancelled
                        } catch (error: Exception) {
                            saveError = error.message ?: "Penugasan gagal disimpan."
                        } finally {
                            saving = false
                        }
                    }
                },
            ) {
                Text(if (saving) "Menyimpan…" else "Terima laporan", color = LoginAccent)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss, enabled = !saving) { Text("Batal", color = LoginMuted) }
        },
        containerColor = AppSurface,
    )
}
