package com.uoi.pratikumwithbunafla.ui.petugas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.uoi.pratikumwithbunafla.data.model.*
import com.uoi.pratikumwithbunafla.ui.admin.*
import com.uoi.pratikumwithbunafla.ui.components.StaggeredPageEntry
import com.uoi.pratikumwithbunafla.ui.components.*
import com.uoi.pratikumwithbunafla.ui.theme.*

@Composable
internal fun PetugasVehicleRepairQueue(
    repairs: List<VehicleRepair>,
    showingHistory: Boolean,
    isInitialLoading: Boolean,
    hasLoadError: Boolean,
    onRefresh: () -> Unit,
    onHistoryChange: (Boolean) -> Unit,
    onRepairAction: (VehicleRepair, String) -> Unit,
) {
    val activeRepairs = repairs.filter {
        it.status !in setOf("selesai", "ditolak")
    }
    val repairHistory = repairs.filter {
        it.status in setOf("selesai", "ditolak")
    }
    val visibleRepairs = if (showingHistory) repairHistory else activeRepairs
    StaggeredPageEntry("petugas-tugas-perbaikan", 4) {
        PetugasFilterGrid(
            title = "Tahap perbaikan",
            choices =
                listOf(
                    PetugasFilterOption(
                        "berjalan",
                        "Berjalan",
                        activeRepairs.size,
                    ),
                    PetugasFilterOption(
                        "riwayat",
                        "Riwayat",
                        repairHistory.size,
                    ),
                ),
            selectedKey = if (showingHistory) "riwayat" else "berjalan",
            onSelect = { onHistoryChange(it == "riwayat") },
        )
    }
    if (isInitialLoading && repairs.isEmpty()) {
        StaggeredPageEntry("petugas-tugas-perbaikan", 5) {
            PetugasLoadingState(
                "Memuat perbaikan",
                "Mengambil status laporan kerusakan.",
            )
        }
    } else if (repairs.isEmpty() && hasLoadError) {
        StaggeredPageEntry("petugas-tugas-perbaikan", 5) {
            PetugasLoadFailure(
                "Data perbaikan belum dapat dimuat.",
                onRefresh,
            )
        }
    } else if (repairs.isEmpty()) {
        StaggeredPageEntry("petugas-tugas-perbaikan", 5) {
            PetugasEmptyState(
                "Belum ada proses perbaikan",
                "Laporkan kerusakan dari tab Armada untuk memulai alur persetujuan.",
            )
        }
    } else if (visibleRepairs.isEmpty()) {
        StaggeredPageEntry("petugas-tugas-perbaikan", 5) {
            PetugasEmptyState(
                if (showingHistory) "Belum ada riwayat perbaikan"
                else "Semua proses perbaikan sudah selesai.",
                "Pilih Riwayat untuk melihat pekerjaan yang sudah ditutup.",
            )
        }
    } else {
        visibleRepairs.forEachIndexed { index, repair ->
            StaggeredPageEntry("petugas-tugas-perbaikan", 5 + index) {
                VehicleRepairCard(repair = repair) {
                    when (repair.status) {
                        "disetujui",
                        "perlu_perbaikan" ->
                            TextButton(
                                onClick = {
                                    onRepairAction(repair, "mulai")
                                },
                            ) {
                                Text(
                                    "Mulai / lanjutkan perbaikan",
                                    color = LoginAccent,
                                    fontWeight = FontWeight.SemiBold,
                                )
                            }
                        "dalam_perbaikan" ->
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(6.dp),
                            ) {
                                TextButton(
                                    onClick = {
                                        onRepairAction(repair, "progres")
                                    },
                                ) {
                                    Text(
                                        "Tambah progres",
                                        color = LoginAccent,
                                        fontWeight = FontWeight.SemiBold,
                                    )
                                }
                                TextButton(
                                    onClick = {
                                        onRepairAction(repair, "pemeriksaan")
                                    },
                                ) {
                                    Text(
                                        "Minta pemeriksaan",
                                        color = LoginAccent,
                                        fontWeight = FontWeight.SemiBold,
                                    )
                                }
                            }
                        "menunggu_persetujuan" ->
                            Text(
                                "Menunggu persetujuan Admin",
                                color = AppTextMuted,
                                fontSize = 13.sp,
                            )
                        "menunggu_pemeriksaan" ->
                            Text(
                                "Hasil dikirim · menunggu pemeriksaan Admin",
                                color = AppTextMuted,
                                fontSize = 13.sp,
                            )
                    }
                }
            }
        }
    }
}

@Composable
internal fun PetugasFleetList(
    vehicles: List<WasteVehicle>,
    repairs: List<VehicleRepair>,
    clearanceRequests: List<CapacityClearanceRequest>,
    isInitialLoading: Boolean,
    hasLoadError: Boolean,
    onRefresh: () -> Unit,
    onCreateVehicle: () -> Unit,
    onEditVehicle: (WasteVehicle) -> Unit,
    onReportDamage: (WasteVehicle) -> Unit,
    onOpenRepairQueue: () -> Unit,
    onOpenClearance: (String, Int, String) -> Unit,
) {
    StaggeredPageEntry("petugas-tugas-armada", 4) {
        AppButton(
            "＋ Tambah kendaraan",
            { onCreateVehicle() },
            containerColor = LoginAccent,
        )
    }
    if (isInitialLoading && vehicles.isEmpty()) {
        StaggeredPageEntry("petugas-tugas-armada", 5) {
            PetugasLoadingState(
                "Memuat armada",
                "Mengambil data kendaraan dari server.",
            )
        }
    } else if (vehicles.isEmpty() && hasLoadError) {
        StaggeredPageEntry("petugas-tugas-armada", 5) {
            PetugasLoadFailure(
                "Data armada belum dapat dimuat.",
                onRefresh,
            )
        }
    } else if (vehicles.isEmpty()) {
        StaggeredPageEntry("petugas-tugas-armada", 5) {
            PetugasEmptyState(
                "Belum ada armada",
                "Tambahkan kendaraan agar laporan bisa ditugaskan.",
            )
        }
    }
    vehicles.forEachIndexed { index, vehicle ->
        val openRepair = repairs.firstOrNull {
            it.vehicleId == vehicle.id && it.status !in setOf("selesai", "ditolak")
        }
        val isFull = vehicle.capacityKg > 0 && vehicle.availableKg <= 0.01
        val emptyingRequest = clearanceRequests.firstOrNull {
            it.targetType == "kendaraan" && it.targetId == vehicle.id
        }
        StaggeredPageEntry("petugas-tugas-armada", 6 + index) {
            AppCard(
                modifier = Modifier.fillMaxWidth(),
                containerColor = AppSurface,
            ) {
                Column(
                    modifier =
                        Modifier.fillMaxWidth()
                            .padding(
                                horizontal = 16.dp,
                                vertical = 15.dp,
                            ),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Column(
                            Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(4.dp),
                        ) {
                            Text(
                                vehicle.plate,
                                color = AppText,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                            )
                            Text(
                                "${vehicle.loadKg.pretty()} kg terisi · " +
                                    "${vehicle.reservedKg.pretty()} kg dipesan dari " +
                                    "${vehicle.capacityKg.pretty()} kg · " +
                                    vehicle.condition,
                                color = AppTextMuted,
                                fontSize = 12.sp,
                                lineHeight = 17.sp,
                            )
                            Text(
                                "Rute: ${vehicle.route.ifBlank { "Belum ditentukan" }}",
                                color = AppTextMuted,
                                fontSize = 13.sp,
                            )
                        }
                        TextButton(
                            onClick = {
                                onEditVehicle(vehicle)
                            },
                        ) {
                            Text(
                                "Ubah",
                                color = LoginAccent,
                                fontSize = 13.sp,
                            )
                        }
                    }
                    if (isFull) {
                        CapacityLimitNotice(
                            title = "Kapasitas penuh · penugasan baru diblokir",
                            detail =
                                "Kosongkan muatan di TPA, lampirkan foto, " +
                                    "lalu ajukan persetujuan Admin.",
                            status =
                                emptyingRequest?.let {
                                    clearanceStatusText(it)
                                },
                            actionLabel =
                                if (emptyingRequest?.isPending == true) null
                                else "Ajukan pengosongan",
                            onAction = {
                                onOpenClearance("kendaraan", vehicle.id, vehicle.plate)
                            },
                        )
                    }
                    if (openRepair != null) {
                        TextButton(
                            onClick = {
                                onOpenRepairQueue()
                            },
                        ) {
                            Text(
                                "Lihat proses · ${openRepair.statusLabel}  ›",
                                color = RoleOrange,
                                fontSize = 13.sp,
                            )
                        }
                    } else if (
                        vehicle.condition.equals("baik", true) ||
                            vehicle.condition.equals("rusak", true)
                    ) {
                        TextButton(
                            onClick = {
                                onReportDamage(vehicle)
                            },
                        ) {
                            Text(
                                "Laporkan kerusakan",
                                color = RoleRed,
                                fontSize = 13.sp,
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
internal fun PetugasFacilityList(
    facilities: List<WasteFacility>,
    clearanceRequests: List<CapacityClearanceRequest>,
    isInitialLoading: Boolean,
    hasLoadError: Boolean,
    onRefresh: () -> Unit,
    onCreateFacility: () -> Unit,
    onEditFacility: (WasteFacility) -> Unit,
    onOpenClearance: (String, Int, String) -> Unit,
) {
    StaggeredPageEntry("petugas-tugas-tpa", 4) {
        AppButton(
            "＋ Tambah TPA",
            { onCreateFacility() },
            containerColor = LoginAccent,
        )
    }
    if (isInitialLoading && facilities.isEmpty()) {
        StaggeredPageEntry("petugas-tugas-tpa", 5) {
            PetugasLoadingState(
                "Memuat data TPA",
                "Mengambil kapasitas tujuan dari server.",
            )
        }
    } else if (facilities.isEmpty() && hasLoadError) {
        StaggeredPageEntry("petugas-tugas-tpa", 5) {
            PetugasLoadFailure(
                "Data TPA belum dapat dimuat.",
                onRefresh,
            )
        }
    } else if (facilities.isEmpty()) {
        StaggeredPageEntry("petugas-tugas-tpa", 5) {
            PetugasEmptyState(
                "Belum ada TPA",
                "Tambahkan TPA agar laporan bisa ditugaskan.",
            )
        }
    }
    facilities.forEachIndexed { index, facility ->
        val isFull = facility.capacityTon > 0 && facility.remainingTon <= 0.001
        val emptyingRequest = clearanceRequests.firstOrNull {
            it.targetType == "tpa" && it.targetId == facility.id
        }
        StaggeredPageEntry("petugas-tugas-tpa", 6 + index) {
            AppCard(
                modifier = Modifier.fillMaxWidth(),
                containerColor = AppSurface,
            ) {
                Column(
                    modifier =
                        Modifier.fillMaxWidth()
                            .padding(
                                horizontal = 16.dp,
                                vertical = 15.dp,
                            ),
                    verticalArrangement = Arrangement.spacedBy(9.dp),
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Column(
                            Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(4.dp),
                        ) {
                            Text(
                                facility.name,
                                color = AppText,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                            )
                            Text(
                                facility.address,
                                color = AppTextMuted,
                                fontSize = 13.sp,
                                lineHeight = 18.sp,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis,
                            )
                        }
                        TextButton(
                            onClick = {
                                onEditFacility(facility)
                            },
                        ) {
                            Text(
                                "Ubah",
                                color = LoginAccent,
                                fontSize = 13.sp,
                            )
                        }
                    }
                    Text(
                        "${facility.filledTon.pretty()} ton terisi · " +
                            "${facility.reservedTon.pretty()} ton dicadangkan dari " +
                            "${facility.capacityTon.pretty()} ton",
                        color = AppTextMuted,
                        fontSize = 13.sp,
                        lineHeight = 18.sp,
                    )
                    SoftProgress(
                        if (facility.capacityTon > 0) {
                            ((facility.filledTon + facility.reservedTon) / facility.capacityTon)
                                .toFloat()
                        } else 0f,
                    )
                    if (isFull) {
                        CapacityLimitNotice(
                            title = "TPA penuh · tidak menerima sampah baru",
                            detail =
                                "Setelah area dikosongkan, kirim foto dan alasan. " +
                                    "Admin akan memeriksa sebelum kapasitas direset.",
                            status =
                                emptyingRequest?.let {
                                    clearanceStatusText(it)
                                },
                            actionLabel =
                                if (emptyingRequest?.isPending == true) null
                                else "Ajukan pengosongan",
                            onAction = {
                                onOpenClearance("tpa", facility.id, facility.name)
                            },
                        )
                    }
                }
            }
        }
    }
}
