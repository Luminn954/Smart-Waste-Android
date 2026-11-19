package com.uoi.pratikumwithbunafla.ui.admin

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material.icons.filled.Archive
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.uoi.pratikumwithbunafla.data.model.*
import com.uoi.pratikumwithbunafla.domain.WasteFlow
import com.uoi.pratikumwithbunafla.ui.components.*
import com.uoi.pratikumwithbunafla.ui.petugas.*
import com.uoi.pratikumwithbunafla.ui.theme.AppBorder
import com.uoi.pratikumwithbunafla.ui.theme.AppSurface
import com.uoi.pratikumwithbunafla.ui.theme.AppText
import com.uoi.pratikumwithbunafla.ui.theme.AppTextMuted
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch

@Composable
fun AdminVerificationScreen(
    reports: List<WasteRecord>,
    archivedReports: List<ArchivedWasteRecord>,
    workers: List<AdminWorker>,
    vehicles: List<WasteVehicle>,
    facilities: List<WasteFacility>,
    selectedStatus: String?,
    showAllReports: Boolean,
    showingArchive: Boolean,
    errorMessage: String,
    account: UserAccount,
    onOpenPhoto: (String) -> Unit,
    onArchiveViewChange: (Boolean) -> Unit,
    onArchiveReport: suspend (WasteRecord) -> Unit,
    onAssignReport: suspend (WasteRecord, Int, Int, Int) -> Unit,
    onRestoreArchivedReport: suspend (WasteRecord) -> Unit,
    onDeleteArchivedReport: suspend (WasteRecord) -> Unit,
    onReportViewChange: (Boolean) -> Unit,
    onFilterChange: (String?) -> Unit,
    onNavigate: (String) -> Unit,
    onLogout: () -> Unit,
    isInitialLoading: Boolean = false,
    isRefreshing: Boolean = false,
    onRefresh: () -> Unit = {},
) {
    var archivingReport by remember { mutableStateOf<WasteRecord?>(null) }
    var assigningReport by remember { mutableStateOf<WasteRecord?>(null) }
    var restoringReport by remember { mutableStateOf<WasteRecord?>(null) }
    var deletingArchivedReport by remember { mutableStateOf<WasteRecord?>(null) }
    var archiveActionError by remember { mutableStateOf("") }
    var archiveActionRunning by remember { mutableStateOf(false) }
    var reportSearch by rememberSaveable(showingArchive) { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val waitingReports = reports.filter { it.status == WasteFlow.WAITING }
    val effectiveSelectedStatus = selectedStatus.takeUnless { it == "bahaya" }
    val activeFilter =
        if (showingArchive) "arsip"
        else
            when {
                !showAllReports || effectiveSelectedStatus == WasteFlow.WAITING -> "baru"
                effectiveSelectedStatus == null -> "semua"
                effectiveSelectedStatus in
                    setOf(WasteFlow.PROCESSING, WasteFlow.ASSIGNED, WasteFlow.TRANSPORTED) ->
                    "proses"
                effectiveSelectedStatus == WasteFlow.DONE -> "selesai"
                else -> "ditolak"
            }
    val visibleReports =
        if (showingArchive) emptyList()
        else
            when {
                !showAllReports -> waitingReports
                effectiveSelectedStatus == null -> reports
                effectiveSelectedStatus in
                    setOf(WasteFlow.PROCESSING, WasteFlow.ASSIGNED, WasteFlow.TRANSPORTED) ->
                    reports.filter { WasteFlow.isInProgress(it.status) }
                else -> reports.filter { it.status == effectiveSelectedStatus }
            }
    val searchQuery = reportSearch.trim().lowercase()
    fun matchesSearch(report: WasteRecord): Boolean =
        searchQuery.isBlank() ||
            listOf(
                    report.code,
                    report.type,
                    report.location,
                    report.description,
                    report.reportedBy,
                    report.workerName,
                    WasteFlow.label(report.status),
                )
                .any { it.lowercase().contains(searchQuery) }
    val filteredReports = visibleReports.filter(::matchesSearch)
    val filteredArchivedReports = archivedReports.filter { matchesSearch(it.report) }
    val reportSearchSuggestions =
        remember(reports, archivedReports) {
            (reports + archivedReports.map { it.report }).flatMap { report ->
                listOf(
                    report.code,
                    report.type,
                    report.location,
                    report.reportedBy,
                    report.workerName,
                )
            }
        }
    val reportLoadFailed = errorMessage.lineSequence().any { it.startsWith("Laporan:") }
    val archiveLoadFailed = errorMessage.lineSequence().any { it.startsWith("Arsip laporan:") }
    val reportFilters =
        listOf(
            "baru" to "Baru",
            "proses" to "Proses",
            "selesai" to "Selesai",
            "ditolak" to "Ditolak",
            "semua" to "Semua",
        )
    AdminPage(
        "reports",
        account,
        onNavigate,
        onLogout,
        isInitialLoading = isInitialLoading,
        isRefreshing = isRefreshing,
        onRefresh = onRefresh,
    ) {
        StaggeredPageEntry("admin-inbox", 0) {
            AdminSectionHeading(
                "INBOX • ADMIN",
                "Laporan warga",
                "Lihat laporan yang masuk dan pantau perkembangannya.",
            )
        }
        if (errorMessage.isNotBlank())
            StaggeredPageEntry("admin-inbox", 1) { InlineError(errorMessage) }
        if (showingArchive) {
            StaggeredPageEntry("admin-inbox-archive", 2) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                ) {
                    TextButton(
                        onClick = {
                            archiveActionError = ""
                            onArchiveViewChange(false)
                        },
                        colors = ButtonDefaults.textButtonColors(contentColor = LoginAccent),
                    ) {
                        Text("← Kembali ke laporan", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
        StaggeredPageEntry("admin-inbox-filter-$showingArchive", 3) {
            if (!showingArchive) {
                val reportFilterPositions = remember {
                    mutableStateMapOf<String, Pair<Float, Float>>()
                }
                val selectedFilterBounds = reportFilterPositions[activeFilter]
                val selectedFilterAccent =
                    when (activeFilter) {
                        "baru" -> RoleGreen
                        "proses" -> RoleTeal
                        "selesai" -> RoleGreenDark
                        "ditolak" -> RoleRed
                        else -> RoleNavy
                    }
                val reportIndicatorColor by
                    animateColorAsState(
                        targetValue = selectedFilterAccent.copy(alpha = 0.38f),
                        animationSpec = tween(360, easing = FastOutSlowInEasing),
                        label = "report-filter-indicator-color",
                    )
                val reportIndicatorX by
                    animateFloatAsState(
                        targetValue = selectedFilterBounds?.first ?: 0f,
                        animationSpec = tween(360, easing = FastOutSlowInEasing),
                        label = "report-filter-indicator-x",
                    )
                val reportIndicatorWidth by
                    animateFloatAsState(
                        targetValue = selectedFilterBounds?.second ?: 0f,
                        animationSpec = tween(360, easing = FastOutSlowInEasing),
                        label = "report-filter-indicator-width",
                    )
                Row(
                    modifier =
                        Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()).drawBehind {
                            if (reportIndicatorWidth > 0f) {
                                drawRoundRect(
                                    color = reportIndicatorColor,
                                    topLeft = Offset(reportIndicatorX, 0f),
                                    size = Size(reportIndicatorWidth, size.height),
                                    cornerRadius = CornerRadius(12.dp.toPx()),
                                )
                            }
                        },
                    horizontalArrangement = Arrangement.spacedBy(5.dp),
                ) {
                    reportFilters.forEach { (filter, label) ->
                        val active = activeFilter == filter
                        val filterAccent =
                            when (filter) {
                                "baru" -> RoleGreen
                                "proses" -> RoleTeal
                                "selesai" -> RoleGreenDark
                                "ditolak" -> RoleRed
                                else -> RoleNavy
                            }
                        val filterSurface by
                            animateColorAsState(
                                targetValue = filterAccent.copy(alpha = if (active) 0f else 0.08f),
                                animationSpec = tween(240, easing = FastOutSlowInEasing),
                                label = "report-filter-$filter-surface",
                            )
                        TextButton(
                            onClick = {
                                when (filter) {
                                    "baru" -> {
                                        onReportViewChange(false)
                                        onFilterChange(null)
                                    }
                                    "proses" -> {
                                        onReportViewChange(true)
                                        onFilterChange(WasteFlow.PROCESSING)
                                    }
                                    "selesai" -> {
                                        onReportViewChange(true)
                                        onFilterChange(WasteFlow.DONE)
                                    }
                                    "ditolak" -> {
                                        onReportViewChange(true)
                                        onFilterChange(WasteFlow.REJECTED)
                                    }
                                    else -> {
                                        onReportViewChange(true)
                                        onFilterChange(null)
                                    }
                                }
                            },
                            contentPadding =
                                androidx.compose.foundation.layout.PaddingValues(
                                    horizontal = 11.dp,
                                    vertical = 8.dp,
                                ),
                            modifier =
                                Modifier.onGloballyPositioned { coordinates ->
                                    val bounds =
                                        coordinates.positionInParent().x to
                                            coordinates.size.width.toFloat()
                                    if (reportFilterPositions[filter] != bounds) {
                                        reportFilterPositions[filter] = bounds
                                    }
                                },
                            colors =
                                ButtonDefaults.textButtonColors(
                                    contentColor = if (active) LoginAccent else AppTextMuted,
                                    containerColor = filterSurface,
                                ),
                            shape = RoundedCornerShape(12.dp),
                        ) {
                            Text(label, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                        }
                        if (filter == "semua") {
                            IconButton(
                                onClick = {
                                    archiveActionError = ""
                                    onArchiveViewChange(true)
                                },
                                modifier = Modifier.size(40.dp),
                            ) {
                                Icon(
                                    Icons.Filled.Archive,
                                    contentDescription =
                                        "Buka arsip tersimpan, ${archivedReports.size} laporan",
                                    tint = LoginAccent,
                                    modifier = Modifier.size(22.dp),
                                )
                            }
                        }
                    }
                }
            }
        }

        StaggeredPageEntry("admin-inbox", 4) {
            SmartWasteSearchField(
                value = reportSearch,
                onValueChange = { reportSearch = it.take(100) },
                placeholder = "Cari kode, jenis, lokasi, atau petugas",
                suggestionSource = reportSearchSuggestions,
            )
        }

        StaggeredPageEntry("admin-inbox", 5) {
            SectionTitle(
                if (showingArchive) "${filteredArchivedReports.size} laporan tersimpan"
                else
                    "${filteredReports.size} ${when (activeFilter) {
                    "baru" -> "laporan baru"
                    "proses" -> "laporan diproses"
                    "selesai" -> "laporan selesai"
                    "ditolak" -> "laporan ditolak"
                    else -> "laporan"
                }}",
                if (showingArchive)
                    "Laporan tetap tersimpan di sini dan bisa dipulihkan kapan saja."
                else
                    when (activeFilter) {
                        "baru" -> "Laporan baru menunggu ditangani petugas."
                        "proses" -> "Mencakup laporan ditugaskan dan sedang diangkut."
                        "selesai" -> "Laporan yang sudah selesai diterima di TPA."
                        "ditolak" -> "Laporan yang ditolak setelah ditinjau."
                        else -> "Pantau seluruh laporan warga."
                    },
            )
        }
        when {
            showingArchive && archivedReports.isEmpty() && isInitialLoading -> {
                StaggeredPageEntry("admin-inbox-archive", 6) {
                    PetugasLoadingState("Memuat arsip", "Mengambil laporan tersimpan dari server.")
                }
            }
            showingArchive && archivedReports.isEmpty() && archiveLoadFailed -> {
                StaggeredPageEntry("admin-inbox-archive", 6) {
                    PetugasLoadFailure("Arsip laporan belum dapat dimuat.", onRefresh)
                }
            }
            showingArchive && filteredArchivedReports.isEmpty() && reportSearch.isBlank() -> {
                StaggeredPageEntry("admin-inbox-archive", 6) {
                    EmptyAdminState("Arsip masih kosong.")
                }
            }
            showingArchive && filteredArchivedReports.isEmpty() -> {
                StaggeredPageEntry("admin-inbox-archive", 6) {
                    EmptyAdminState("Tidak ada arsip yang cocok dengan pencarian.")
                }
            }
            showingArchive -> {
                filteredArchivedReports.forEachIndexed { index, archived ->
                    StaggeredPageEntry("admin-inbox-archive", 7 + index) {
                        Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
                            AdminReportCard(
                                archived.report,
                                onOpenPhoto,
                                compact = true,
                                onRestore = { restoringReport = archived.report },
                                onPermanentDelete = { deletingArchivedReport = archived.report },
                            )
                            Text(
                                "Disimpan ${archived.archivedAt} · ${archived.archivedBy}",
                                color = AppTextMuted,
                                fontSize = 10.sp,
                                modifier = Modifier.padding(start = 12.dp, bottom = 5.dp),
                            )
                        }
                    }
                }
            }
            filteredReports.isEmpty() -> {
                StaggeredPageEntry("admin-inbox", 6) {
                    if (reportLoadFailed) {
                        PetugasLoadFailure("Daftar laporan belum dapat dimuat.", onRefresh)
                    } else {
                        EmptyAdminState(
                            when {
                                reportSearch.isNotBlank() ->
                                    "Tidak ada laporan yang cocok dengan pencarian."
                                showAllReports -> "Belum ada laporan pada status ini."
                                else -> "Semua laporan baru sudah ditinjau."
                            },
                        )
                    }
                }
            }
            else -> {
                filteredReports.forEachIndexed { index, report ->
                    StaggeredPageEntry("admin-inbox", 7 + index) {
                        AdminReportCard(
                            report,
                            onOpenPhoto,
                            compact = true,
                            onAssign =
                                report
                                    .takeIf { it.status == WasteFlow.WAITING }
                                    ?.let {
                                        { assigningReport = it }
                                    },
                            assignmentLabel = "Setujui & tugaskan",
                            onArchive = { archivingReport = report },
                        )
                    }
                }
            }
        }
    }

    archivingReport?.let { report ->
        AlertDialog(
            onDismissRequest = { if (!archiveActionRunning) archivingReport = null },
            title = {
                Text("Simpan laporan ke Arsip?", color = LoginInk, fontWeight = FontWeight.Bold)
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        "${report.code} akan disembunyikan dari daftar utama. " +
                            "Data laporan, foto, riwayat, dan statistik tetap tersimpan; " +
                            "kamu bisa memulihkannya dari Arsip.",
                        color = AppTextMuted,
                    )
                    if (archiveActionError.isNotBlank()) InlineError(archiveActionError)
                }
            },
            confirmButton = {
                TextButton(
                    enabled = !archiveActionRunning,
                    onClick = {
                        scope.launch {
                            archiveActionRunning = true
                            archiveActionError = ""
                            try {
                                onArchiveReport(report)
                                archivingReport = null
                            } catch (cancelled: CancellationException) {
                                throw cancelled
                            } catch (error: Exception) {
                                archiveActionError = error.message ?: "Laporan gagal diarsipkan."
                            } finally {
                                archiveActionRunning = false
                            }
                        }
                    },
                ) {
                    Text(
                        if (archiveActionRunning) "Menyimpan…" else "Simpan ke Arsip",
                        color = LoginAccent,
                    )
                }
            },
            dismissButton = {
                TextButton(
                    enabled = !archiveActionRunning,
                    onClick = { archivingReport = null },
                ) {
                    Text("Batal", color = LoginMuted)
                }
            },
            containerColor = AppSurface,
        )
    }
    restoringReport?.let { report ->
        AlertDialog(
            onDismissRequest = { if (!archiveActionRunning) restoringReport = null },
            title = { Text("Pulihkan laporan?", color = LoginInk, fontWeight = FontWeight.Bold) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        "${report.code} (${WasteFlow.label(report.status)}) akan kembali ke daftar laporan Admin.",
                        color = AppTextMuted,
                    )
                    if (archiveActionError.isNotBlank()) InlineError(archiveActionError)
                }
            },
            confirmButton = {
                TextButton(
                    enabled = !archiveActionRunning,
                    onClick = {
                        scope.launch {
                            archiveActionRunning = true
                            archiveActionError = ""
                            try {
                                onRestoreArchivedReport(report)
                                restoringReport = null
                            } catch (cancelled: CancellationException) {
                                throw cancelled
                            } catch (error: Exception) {
                                archiveActionError = error.message ?: "Laporan gagal dipulihkan."
                            } finally {
                                archiveActionRunning = false
                            }
                        }
                    },
                ) {
                    Text(
                        if (archiveActionRunning) "Memulihkan…" else "Pulihkan",
                        color = LoginAccent,
                    )
                }
            },
            dismissButton = {
                TextButton(
                    enabled = !archiveActionRunning,
                    onClick = { restoringReport = null },
                ) {
                    Text("Batal", color = LoginMuted)
                }
            },
            containerColor = AppSurface,
        )
    }
    deletingArchivedReport?.let { report ->
        AlertDialog(
            onDismissRequest = { if (!archiveActionRunning) deletingArchivedReport = null },
            title = {
                Text("Hapus laporan permanen?", color = LoginInk, fontWeight = FontWeight.Bold)
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        "${report.code} dan data laporan, foto, serta riwayatnya akan dihapus " +
                            "dari database dan tidak bisa dipulihkan. Data ini juga hilang dari " +
                            "akun warga dan Petugas.",
                        color = AppTextMuted,
                    )
                    if (archiveActionError.isNotBlank()) InlineError(archiveActionError)
                }
            },
            confirmButton = {
                TextButton(
                    enabled = !archiveActionRunning,
                    onClick = {
                        scope.launch {
                            archiveActionRunning = true
                            archiveActionError = ""
                            try {
                                onDeleteArchivedReport(report)
                                deletingArchivedReport = null
                            } catch (cancelled: CancellationException) {
                                throw cancelled
                            } catch (error: Exception) {
                                archiveActionError =
                                    error.message ?: "Laporan gagal dihapus permanen."
                            } finally {
                                archiveActionRunning = false
                            }
                        }
                    },
                ) {
                    Text(
                        if (archiveActionRunning) "Menghapus…" else "Hapus permanen",
                        color = RoleRed,
                    )
                }
            },
            dismissButton = {
                TextButton(
                    enabled = !archiveActionRunning,
                    onClick = { deletingArchivedReport = null },
                ) {
                    Text("Batal", color = LoginMuted)
                }
            },
            containerColor = AppSurface,
        )
    }
    assigningReport?.let { report ->
        AdminReportAssignmentDialog(
            report = report,
            workers = workers,
            vehicles = vehicles,
            facilities = facilities,
            onDismiss = { assigningReport = null },
            onAssign = { workerId, vehicleId, tpaId ->
                onAssignReport(report, workerId, vehicleId, tpaId)
                assigningReport = null
            },
        )
    }
}

@Composable
internal fun AdminReportCard(
    report: WasteRecord,
    onOpenPhoto: (String) -> Unit,
    onReject: (() -> Unit)? = null,
    onAssign: (() -> Unit)? = null,
    compact: Boolean = false,
    assignmentLabel: String = "Tugaskan",
    onArchive: (() -> Unit)? = null,
    onRestore: (() -> Unit)? = null,
    onPermanentDelete: (() -> Unit)? = null,
    petugasStyle: Boolean = false,
) {
    var showDetails by rememberSaveable(report.id) { mutableStateOf(!compact) }
    val accent = reportAccent(report.status)
    val reportMeta = buildList {
        report.reportedBy
            .takeIf { it.isNotBlank() }
            ?.let {
                add("Pelapor: ${it.reporterDisplayName()}")
            }
        report.reportedAt
            .takeIf { it.isNotBlank() }
            ?.let {
                add(it.toDisplayDateTime())
            }
    }
        .joinToString("  ·  ")
    val hasActions =
        onReject != null ||
            onAssign != null ||
            onArchive != null ||
            onRestore != null ||
            onPermanentDelete != null
    AppCard(
        modifier = Modifier.fillMaxWidth(),
        containerColor = AppSurface,
        accentColor = if (petugasStyle) null else accent,
        borderColor = AppBorder,
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(9.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                Box(
                    modifier =
                        Modifier.size(38.dp)
                            .clip(RoundedCornerShape(13.dp))
                            .background(accent.copy(alpha = 0.12f)),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Assignment,
                        contentDescription = null,
                        tint = accent,
                        modifier = Modifier.size(20.dp),
                    )
                }
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(3.dp),
                ) {
                    Text(
                        report.code,
                        color = AppText,
                        fontSize = 15.sp,
                        lineHeight = 20.sp,
                        fontWeight = FontWeight.Bold,
                    )
                    if (reportMeta.isNotBlank()) {
                        Text(
                            reportMeta,
                            color = AppTextMuted,
                            fontSize = 11.sp,
                            lineHeight = 16.sp,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                }
                StatusPill(report.status, accent)
            }
            Text(
                "${report.type}${if (report.weightKg > 0) "  •  ${report.weightKg.pretty()} kg" else ""}",
                color = AppText,
                fontSize = 13.sp,
                lineHeight = 18.sp,
                fontWeight = FontWeight.SemiBold,
            )
            if (report.location.isNotBlank() && report.location != "-") {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                ) {
                    Icon(
                        Icons.Filled.Place,
                        contentDescription = null,
                        tint = LoginAccent,
                        modifier = Modifier.size(16.dp),
                    )
                    Text(
                        report.location.trim(),
                        color = AppTextMuted,
                        fontSize = 12.sp,
                        lineHeight = 17.sp,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }
            if (showDetails) {
                val assignmentSummary = report.assignmentSummary()
                if (assignmentSummary.isNotBlank()) {
                    Text(
                        assignmentSummary,
                        color = LoginMuted,
                        fontSize = 11.sp,
                        lineHeight = 16.sp,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
                if (report.description.isNotBlank())
                    Text(
                        report.description,
                        color = AppTextMuted,
                        fontSize = 12.sp,
                        lineHeight = 18.sp,
                        maxLines = 4,
                        overflow = TextOverflow.Ellipsis,
                    )
                if (!report.photoUrl.isNullOrBlank()) {
                    TextButton(onClick = { onOpenPhoto(report.photoUrl) }) {
                        Text(
                            "Lihat foto TKP",
                            color = LoginAccent,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                }
            }
            if (compact || hasActions) AppCardDivider()
            if (compact) {
                TextButton(
                    onClick = { showDetails = !showDetails },
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(0.dp),
                ) {
                    Text(
                        if (showDetails) "Tutup detail ↑" else "Lihat detail ↓",
                        color = if (showDetails) LoginMuted else LoginAccent,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
            if (hasActions) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                ) {
                    Spacer(modifier = Modifier.weight(1f))
                    if (onReject != null) {
                        TextButton(
                            onClick = onReject,
                            colors = ButtonDefaults.textButtonColors(contentColor = RoleRed),
                        ) {
                            Text("Tolak", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                    if (onAssign != null) {
                        TextButton(
                            onClick = onAssign,
                            colors = ButtonDefaults.textButtonColors(contentColor = LoginAccent),
                        ) {
                            Text(assignmentLabel, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                    if (onArchive != null) {
                        TextButton(
                            onClick = onArchive,
                            colors = ButtonDefaults.textButtonColors(contentColor = LoginAccent),
                        ) {
                            Text("Arsipkan", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                    if (onRestore != null) {
                        TextButton(
                            onClick = onRestore,
                            colors = ButtonDefaults.textButtonColors(contentColor = LoginAccent),
                        ) {
                            Text("Pulihkan", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                    if (onPermanentDelete != null) {
                        TextButton(
                            onClick = onPermanentDelete,
                            colors = ButtonDefaults.textButtonColors(contentColor = RoleRed),
                        ) {
                            Text("Hapus permanen", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}

@Composable
internal fun AssignmentDialog(
    report: WasteRecord,
    workers: List<AdminWorker>,
    vehicles: List<WasteVehicle>,
    facilities: List<WasteFacility>,
    onDismiss: () -> Unit,
    onAssign: suspend (Int, Int, Int) -> Unit,
) {
    var worker by remember(report.id) { mutableStateOf<AdminWorker?>(null) }
    var vehicle by remember(report.id) { mutableStateOf<WasteVehicle?>(null) }
    var facility by remember(report.id) { mutableStateOf<WasteFacility?>(null) }
    var saving by remember(report.id) { mutableStateOf(false) }
    var saveError by remember(report.id) { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val eligibleWorkers = workers.filter { it.active && it.hasAccount }
    val eligibleVehicles = vehicles.filter {
        it.condition.equals("baik", ignoreCase = true) && it.availableKg >= report.weightKg
    }
    val eligibleFacilities = facilities.filter {
        it.remainingTon >= report.weightKg / 1000.0
    }
    val canAssign =
        worker?.let(eligibleWorkers::contains) == true &&
            vehicle?.let(eligibleVehicles::contains) == true &&
            facility?.let(eligibleFacilities::contains) == true
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Buat penugasan", color = LoginInk, fontWeight = FontWeight.Bold) },
        text = {
            ScrollableDialogContent(spacing = 9.dp) {
                Text(
                    "${report.code} • ${report.weightKg.pretty()} kg",
                    color = LoginMuted,
                    fontSize = 11.sp,
                )
                SelectionField(
                    "Petugas",
                    worker?.let { "${it.name} · ${it.zone.ifBlank { "tanpa zona" }}" },
                    eligibleWorkers,
                    { "${it.name} · ${it.zone.ifBlank { "tanpa zona" }}" },
                ) {
                    worker = it
                }
                SelectionField(
                    "Kendaraan",
                    vehicle?.let { "${it.plate} · sisa ${it.availableKg.pretty()} kg" },
                    eligibleVehicles,
                    { "${it.plate} · sisa ${it.availableKg.pretty()} kg" },
                ) {
                    vehicle = it
                }
                SelectionField(
                    "TPA tujuan",
                    facility?.let { "${it.name} · sisa ${it.remainingTon.pretty()} ton" },
                    eligibleFacilities,
                    { "${it.name} · sisa ${it.remainingTon.pretty()} ton" },
                ) {
                    facility = it
                }
                if (
                    eligibleWorkers.isEmpty() ||
                        eligibleVehicles.isEmpty() ||
                        eligibleFacilities.isEmpty()
                ) {
                    val unavailable = buildList {
                        if (eligibleWorkers.isEmpty())
                            add("petugas aktif yang memiliki akun Android")
                        if (eligibleVehicles.isEmpty())
                            add("kendaraan sehat dengan kapasitas cukup")
                        if (eligibleFacilities.isEmpty()) add("TPA dengan kapasitas cukup")
                    }
                        .joinToString(", ")
                    Text(
                        "Belum bisa ditugaskan: tidak ada $unavailable. Periksa menu Operasional.",
                        color = RoleRed,
                        fontSize = 11.sp,
                    )
                }
                if (saveError.isNotBlank()) InlineError(saveError)
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (canAssign && !saving)
                        scope.launch {
                            saving = true
                            saveError = ""
                            try {
                                onAssign(worker!!.id, vehicle!!.id, facility!!.id)
                            } catch (cancelled: CancellationException) {
                                throw cancelled
                            } catch (error: Exception) {
                                saveError =
                                    error.message
                                        ?: "Penugasan gagal disimpan. Periksa koneksi dan coba lagi."
                            } finally {
                                saving = false
                            }
                        }
                },
                enabled = canAssign && !saving,
            ) {
                Text(
                    if (saving) "Menyimpan…" else "Tetapkan",
                    color = LoginAccent,
                    fontWeight = FontWeight.Bold,
                )
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
internal fun <T> SelectionField(
    title: String,
    selectedLabel: String?,
    choices: List<T>,
    label: (T) -> String,
    onSelected: (T) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    Column {
        Text(title, color = LoginMuted, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
        Box {
            TextButton(
                onClick = { expanded = true },
                enabled = choices.isNotEmpty(),
                modifier =
                    Modifier.fillMaxWidth()
                        .heightIn(min = 48.dp)
                        .background(
                            RoleAccentSurface.copy(alpha = 0.56f),
                            RoundedCornerShape(12.dp),
                        ),
            ) {
                Text(
                    selectedLabel ?: "Pilih $title",
                    color = LoginInk,
                    fontSize = 11.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
            }
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                choices.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(label(item), fontSize = 11.sp) },
                        onClick = {
                            onSelected(item)
                            expanded = false
                        },
                    )
                }
            }
        }
    }
}

@Composable
internal fun DeleteWorkerAccountDialog(
    worker: AdminWorker,
    replacementWorkers: List<AdminWorker>,
    onDismiss: () -> Unit,
    onDelete: suspend (AdminWorker, Int?) -> Unit,
) {
    var selectedReplacementId by
        remember(worker.id) {
            mutableStateOf(replacementWorkers.firstOrNull()?.id)
        }
    var menuExpanded by remember { mutableStateOf(false) }
    var deleting by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val needsReplacement = worker.activeTasks > 0
    val selectedReplacement = replacementWorkers.firstOrNull { it.id == selectedReplacementId }

    AlertDialog(
        onDismissRequest = { if (!deleting) onDismiss() },
        title = { Text("Hapus akun petugas?", color = LoginInk, fontWeight = FontWeight.Bold) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    "${worker.name.displayPersonName()} tidak bisa login lagi. " +
                        "Profil dan riwayat kinerja tetap disimpan.",
                    color = AppTextMuted,
                    fontSize = 12.sp,
                    lineHeight = 17.sp,
                )
                if (needsReplacement) {
                    Text(
                        "${worker.activeTasks} tugas aktif perlu dialihkan sebelum akun dihapus.",
                        color = AppText,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                    )
                    if (replacementWorkers.isEmpty()) {
                        Text(
                            "Belum ada petugas lain yang aktif. Aktifkan atau tambahkan " +
                                "akun petugas pengganti terlebih dahulu.",
                            color = RoleRed,
                            fontSize = 11.sp,
                        )
                    } else {
                        Box {
                            TextButton(onClick = { menuExpanded = true }, enabled = !deleting) {
                                Text(
                                    "Pengganti: " +
                                        "${selectedReplacement?.name?.displayPersonName() ?: "Pilih petugas"} ▾",
                                    color = LoginAccent,
                                )
                            }
                            DropdownMenu(
                                expanded = menuExpanded,
                                onDismissRequest = { menuExpanded = false },
                            ) {
                                replacementWorkers.forEach { replacement ->
                                    DropdownMenuItem(
                                        text = {
                                            Text(
                                                "${replacement.name.displayPersonName()} · @${replacement.username}",
                                            )
                                        },
                                        onClick = {
                                            selectedReplacementId = replacement.id
                                            menuExpanded = false
                                        },
                                    )
                                }
                            }
                        }
                    }
                }
                if (errorMessage.isNotBlank()) InlineError(errorMessage)
            }
        },
        confirmButton = {
            TextButton(
                enabled = !deleting && (!needsReplacement || selectedReplacement != null),
                onClick = {
                    scope.launch {
                        deleting = true
                        errorMessage = ""
                        try {
                            onDelete(worker, if (needsReplacement) selectedReplacementId else null)
                            onDismiss()
                        } catch (cancelled: CancellationException) {
                            throw cancelled
                        } catch (error: Exception) {
                            errorMessage = error.message ?: "Akun petugas gagal dihapus."
                        } finally {
                            deleting = false
                        }
                    }
                },
                colors = ButtonDefaults.textButtonColors(contentColor = RoleRed),
            ) {
                Text(if (deleting) "Menghapus…" else "Hapus akun", fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            TextButton(enabled = !deleting, onClick = onDismiss) {
                Text("Batal", color = LoginMuted)
            }
        },
        containerColor = AppSurface,
    )
}

@Composable
internal fun ConfirmWorkerActionDialog(
    worker: AdminWorker,
    title: String,
    detail: String,
    confirmLabel: String,
    confirmColor: Color,
    onDismiss: () -> Unit,
    onConfirm: suspend (AdminWorker) -> Unit,
) {
    var submitting by remember(worker.id, title) { mutableStateOf(false) }
    var errorMessage by remember(worker.id, title) { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    AlertDialog(
        onDismissRequest = { if (!submitting) onDismiss() },
        title = { Text(title, color = LoginInk, fontWeight = FontWeight.Bold) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    worker.name.displayPersonName(),
                    color = AppText,
                    fontWeight = FontWeight.SemiBold,
                )
                Text(detail, color = AppTextMuted, fontSize = 12.sp, lineHeight = 17.sp)
                if (errorMessage.isNotBlank()) InlineError(errorMessage)
            }
        },
        confirmButton = {
            TextButton(
                enabled = !submitting,
                onClick = {
                    scope.launch {
                        submitting = true
                        errorMessage = ""
                        try {
                            onConfirm(worker)
                            onDismiss()
                        } catch (cancelled: CancellationException) {
                            throw cancelled
                        } catch (error: Exception) {
                            errorMessage = error.message ?: "Perubahan petugas gagal disimpan."
                        } finally {
                            submitting = false
                        }
                    }
                },
                colors = ButtonDefaults.textButtonColors(contentColor = confirmColor),
            ) {
                Text(if (submitting) "Menyimpan…" else confirmLabel, fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            TextButton(enabled = !submitting, onClick = onDismiss) {
                Text("Batal", color = LoginMuted)
            }
        },
        containerColor = AppSurface,
    )
}
