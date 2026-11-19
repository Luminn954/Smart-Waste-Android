package com.uoi.pratikumwithbunafla.ui.petugas

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Inbox
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.rounded.History
import androidx.compose.material.icons.rounded.TaskAlt
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import com.uoi.pratikumwithbunafla.data.model.*
import com.uoi.pratikumwithbunafla.domain.WasteFlow
import com.uoi.pratikumwithbunafla.ui.admin.*
import com.uoi.pratikumwithbunafla.ui.components.StaggeredPageEntry
import com.uoi.pratikumwithbunafla.ui.components.*
import com.uoi.pratikumwithbunafla.ui.theme.AppSurface
import com.uoi.pratikumwithbunafla.ui.theme.AppText
import com.uoi.pratikumwithbunafla.ui.theme.AppTextMuted
import com.uoi.pratikumwithbunafla.ui.theme.LocalSmartWasteAppearance
import java.io.File
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch

@Composable
fun PetugasTasksScreen(
    tasks: List<WasteRecord>,
    archivedTasks: List<ArchivedWasteRecord>,
    incomingReports: List<WasteRecord>,
    vehicles: List<WasteVehicle>,
    vehicleRepairs: List<VehicleRepair>,
    facilities: List<WasteFacility>,
    capacityClearanceRequests: List<CapacityClearanceRequest>,
    errorMessage: String,
    account: UserAccount,
    onUpdateProfile: suspend (String) -> UserAccount,
    onUpdateProfilePhoto: suspend (Uri) -> UserAccount,
    onOpenPhoto: (String) -> Unit,
    onArchiveTask: suspend (WasteRecord) -> Unit,
    onRestoreArchivedTask: suspend (WasteRecord) -> Unit,
    onDeleteArchivedTask: suspend (WasteRecord) -> Unit,
    onAssignReport: suspend (WasteRecord, Int, Int) -> Unit,
    onRejectReport: suspend (WasteRecord, String) -> Unit,
    onMarkTransported: suspend (WasteRecord) -> Unit,
    onSubmitProof: suspend (WasteRecord, Uri, String) -> Unit,
    onSubmitLocationEvidence: suspend (WasteRecord, Uri) -> Unit,
    onCreateVehicle: suspend (String, Double, String) -> Unit,
    onUpdateVehicle: suspend (WasteVehicle) -> Unit,
    onReportVehicleDamage: suspend (WasteVehicle, String, String) -> Unit,
    onStartVehicleRepair: suspend (VehicleRepair, String) -> Unit,
    onUpdateVehicleRepairProgress: suspend (VehicleRepair, String) -> Unit,
    onSubmitVehicleRepairForInspection: suspend (VehicleRepair, String) -> Unit,
    onCreateFacility: suspend (String, String, Double) -> Unit,
    onUpdateFacility: suspend (WasteFacility) -> Unit,
    onRequestCapacityClearance: suspend (String, Int, Uri, String) -> Unit,
    isInitialLoading: Boolean,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    onOpenNotifications: () -> Unit,
    onLogout: () -> Unit,
    entryKey: Int = 0,
) {
    var selectedSection by rememberSaveable(account.username) { mutableStateOf("ringkasan") }
    var selectedWorkArea by rememberSaveable(account.username) { mutableStateOf("laporan") }
    var incomingSearch by rememberSaveable(account.username) { mutableStateOf("") }
    var activeTaskSearch by rememberSaveable(account.username) { mutableStateOf("") }
    var homeSearchQuery by rememberSaveable(account.username) { mutableStateOf("") }
    var historySearch by rememberSaveable(account.username) { mutableStateOf("") }
    var activeTaskFilter by rememberSaveable(account.username) { mutableStateOf("semua") }
    var historyFilter by rememberSaveable(account.username) { mutableStateOf("semua") }
    var showingTaskArchive by rememberSaveable(account.username) { mutableStateOf(false) }
    var showingRepairHistory by rememberSaveable(account.username) { mutableStateOf(false) }
    var archivingHistoryTask by remember { mutableStateOf<WasteRecord?>(null) }
    var restoringHistoryTask by remember { mutableStateOf<WasteRecord?>(null) }
    var deletingArchivedTask by remember { mutableStateOf<WasteRecord?>(null) }
    var archiveActionError by remember { mutableStateOf("") }
    var archiveActionRunning by remember { mutableStateOf(false) }
    var showVehicleForm by remember { mutableStateOf(false) }
    var editingVehicle by remember { mutableStateOf<WasteVehicle?>(null) }
    var reportingDamageVehicle by remember { mutableStateOf<WasteVehicle?>(null) }
    var vehicleRepairAction by remember { mutableStateOf<Pair<VehicleRepair, String>?>(null) }
    var showFacilityForm by remember { mutableStateOf(false) }
    var editingFacility by remember { mutableStateOf<WasteFacility?>(null) }
    var capacityClearanceTarget by remember { mutableStateOf<Triple<String, Int, String>?>(null) }
    var capacityClearancePhoto by rememberSaveable(account.username) { mutableStateOf<Uri?>(null) }
    var capacityClearanceReason by rememberSaveable(account.username) { mutableStateOf("") }
    var capacityClearanceError by remember { mutableStateOf("") }
    var capacityClearanceSending by remember { mutableStateOf(false) }
    var assigningReport by remember { mutableStateOf<WasteRecord?>(null) }
    var rejectingReport by remember { mutableStateOf<WasteRecord?>(null) }
    var rejectionNote by rememberSaveable { mutableStateOf("") }
    var reportActionError by remember { mutableStateOf("") }
    var processingReportAction by remember { mutableStateOf(false) }
    var transportingTaskId by remember { mutableStateOf<Int?>(null) }
    var selectedCompletionTaskId by
        rememberSaveable(account.username) { mutableStateOf<Int?>(null) }
    val selectedCompletionTask = tasks.firstOrNull { it.id == selectedCompletionTaskId }
    var selectedLocationTaskId by rememberSaveable(account.username) { mutableStateOf<Int?>(null) }
    val selectedLocationTask = tasks.firstOrNull { it.id == selectedLocationTaskId }
    var selectedProofUri by rememberSaveable(account.username) { mutableStateOf<Uri?>(null) }
    var selectedCameraUri by rememberSaveable(account.username) { mutableStateOf<Uri?>(null) }
    var galleryEvidenceUri by rememberSaveable(account.username) { mutableStateOf<Uri?>(null) }
    var cameraEvidencePath by rememberSaveable(account.username) { mutableStateOf<String?>(null) }
    val cameraEvidenceFile = cameraEvidencePath?.let(::File)
    var showProofSourceDialog by remember { mutableStateOf(false) }
    var proofNote by rememberSaveable { mutableStateOf("") }
    var proofError by remember { mutableStateOf("") }
    var sendingProof by remember { mutableStateOf(false) }
    var selectedLocationPhotoUri by
        rememberSaveable(account.username) { mutableStateOf<Uri?>(null) }
    var selectedLocationCameraUri by
        rememberSaveable(account.username) { mutableStateOf<Uri?>(null) }
    var locationGalleryUri by rememberSaveable(account.username) { mutableStateOf<Uri?>(null) }
    var locationCameraPath by rememberSaveable(account.username) { mutableStateOf<String?>(null) }
    val locationCameraFile = locationCameraPath?.let(::File)
    var showLocationPhotoSourceDialog by remember { mutableStateOf(false) }
    var locationPhotoError by remember { mutableStateOf("") }
    var sendingLocationTaskId by remember { mutableStateOf<Int?>(null) }
    var confirmingTransportTask by remember { mutableStateOf<WasteRecord?>(null) }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val proofPicker =
        rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) { uri ->
            if (uri != null) {
                runCatching {
                    context.contentResolver.takePersistableUriPermission(
                        uri,
                        Intent.FLAG_GRANT_READ_URI_PERMISSION,
                    )
                }
            }
            galleryEvidenceUri = uri
            selectedProofUri = uri
            if (uri == null) selectedCompletionTaskId = null
            showProofSourceDialog = false
        }
    val locationPhotoPicker =
        rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) { uri ->
            if (uri != null) {
                runCatching {
                    context.contentResolver.takePersistableUriPermission(
                        uri,
                        Intent.FLAG_GRANT_READ_URI_PERMISSION,
                    )
                }
                locationGalleryUri = uri
                selectedLocationPhotoUri = uri
                showLocationPhotoSourceDialog = false
            } else {
                showLocationPhotoSourceDialog = true
            }
        }
    val capacityClearancePicker =
        rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) { uri ->
            if (uri != null) {
                runCatching {
                    context.contentResolver.takePersistableUriPermission(
                        uri,
                        Intent.FLAG_GRANT_READ_URI_PERMISSION,
                    )
                }
            }
            capacityClearancePhoto = uri
        }
    val cameraPicker =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { captured ->
            selectedProofUri = if (captured) selectedCameraUri else null
            if (!captured) {
                cameraEvidenceFile?.delete()
                cameraEvidencePath = null
                selectedCameraUri = null
                proofError = "Foto belum diambil. Kamu bisa mencoba lagi atau memilih dari galeri."
                showProofSourceDialog = true
            }
        }
    val locationCameraPicker =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { captured ->
            selectedLocationPhotoUri = if (captured) selectedLocationCameraUri else null
            if (!captured) {
                locationCameraFile?.delete()
                locationCameraPath = null
                selectedLocationCameraUri = null
                locationPhotoError = "Foto belum diambil. Coba lagi atau pilih foto dari galeri."
                showLocationPhotoSourceDialog = true
            }
        }
    fun clearProofSelection() {
        cameraEvidenceFile?.delete()
        cameraEvidencePath = null
        selectedCameraUri = null
        galleryEvidenceUri?.let { uri ->
            runCatching {
                context.contentResolver.releasePersistableUriPermission(
                    uri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION,
                )
            }
        }
        galleryEvidenceUri = null
        selectedProofUri = null
        selectedCompletionTaskId = null
        showProofSourceDialog = false
    }
    fun requestCompletionProof(task: WasteRecord) {
        cameraEvidenceFile?.delete()
        cameraEvidencePath = null
        selectedCameraUri = null
        galleryEvidenceUri = null
        selectedCompletionTaskId = task.id
        selectedProofUri = null
        proofNote = ""
        proofError = ""
        showProofSourceDialog = true
    }
    fun launchEvidenceCamera() {
        val task = selectedCompletionTask ?: return
        val file =
            File(
                File(context.cacheDir, "evidence").apply { mkdirs() },
                "bukti-${task.id}-${System.currentTimeMillis()}.jpg",
            )
        runCatching {
            check(file.createNewFile()) { "File foto tidak dapat dibuat." }
            val uri =
                FileProvider.getUriForFile(
                    context,
                    "${context.packageName}.fileprovider",
                    file,
                )
            cameraEvidencePath = file.absolutePath
            selectedCameraUri = uri
            showProofSourceDialog = false
            cameraPicker.launch(uri)
        }
            .onFailure { error ->
                file.delete()
                cameraEvidencePath = null
                selectedCameraUri = null
                proofError = error.message ?: "Kamera tidak dapat dibuka. Pilih foto dari galeri."
            }
    }
    fun clearLocationPhotoSelection() {
        locationCameraFile?.delete()
        locationCameraPath = null
        selectedLocationCameraUri = null
        locationGalleryUri?.let { uri ->
            runCatching {
                context.contentResolver.releasePersistableUriPermission(
                    uri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION,
                )
            }
        }
        locationGalleryUri = null
        selectedLocationPhotoUri = null
        selectedLocationTaskId = null
        showLocationPhotoSourceDialog = false
    }
    fun requestLocationPhoto(task: WasteRecord) {
        clearLocationPhotoSelection()
        selectedLocationTaskId = task.id
        locationPhotoError = ""
        showLocationPhotoSourceDialog = true
    }
    fun launchLocationPhotoCamera() {
        val task = selectedLocationTask ?: return
        val file =
            File(
                File(context.cacheDir, "evidence").apply { mkdirs() },
                "lokasi-${task.id}-${System.currentTimeMillis()}.jpg",
            )
        runCatching {
            check(file.createNewFile()) { "File foto tidak dapat dibuat." }
            val uri =
                FileProvider.getUriForFile(
                    context,
                    "${context.packageName}.fileprovider",
                    file,
                )
            locationCameraPath = file.absolutePath
            selectedLocationCameraUri = uri
            showLocationPhotoSourceDialog = false
            locationCameraPicker.launch(uri)
        }
            .onFailure { error ->
                file.delete()
                locationCameraPath = null
                selectedLocationCameraUri = null
                locationPhotoError =
                    error.message ?: "Kamera tidak dapat dibuka. Coba pilih foto dari galeri."
                showLocationPhotoSourceDialog = true
            }
    }
    fun startTransport(task: WasteRecord) {
        if (transportingTaskId != null) return
        scope.launch {
            transportingTaskId = task.id
            reportActionError = ""
            try {
                onMarkTransported(task)
            } catch (cancelled: CancellationException) {
                throw cancelled
            } catch (error: Exception) {
                reportActionError = error.message ?: "Status angkut gagal diperbarui. Coba lagi."
            } finally {
                transportingTaskId = null
            }
        }
    }
    val currentSection =
        selectedSection.takeIf {
            it in setOf("ringkasan", "tugas", "riwayat", "profil")
        } ?: "ringkasan"
    BackHandler(enabled = currentSection == "profil") {
        selectedSection = "ringkasan"
    }
    val activeTasks =
        tasks
            .filter { it.status == WasteFlow.ASSIGNED || it.status == WasteFlow.TRANSPORTED }
            .sortedWith(
                compareBy<WasteRecord> {
                        when {
                            it.completionStatus.equals("perlu_perbaikan", ignoreCase = true) -> 0
                            it.status == WasteFlow.TRANSPORTED &&
                                !it.completionStatus.equals("menunggu", ignoreCase = true) -> 1
                            it.status == WasteFlow.ASSIGNED -> 2
                            else -> 3
                        }
                    }
                    .thenBy { it.reportedAt },
            )
    val completedTasks =
        tasks.filter { it.status == WasteFlow.DONE }.sortedByDescending { it.reportedAt }
    val rejectedTasks =
        tasks.filter { it.status == WasteFlow.REJECTED }.sortedByDescending { it.reportedAt }
    val historyTasks =
        tasks
            .filter { it.status == WasteFlow.DONE || it.status == WasteFlow.REJECTED }
            .sortedByDescending { it.reportedAt }
    val historyFilterRows =
        if (showingTaskArchive) archivedTasks.map { it.report } else historyTasks
    val newReports = incomingReports.sortedBy { it.reportedAt }
    val homeSearchSuggestions =
        remember(activeTasks, newReports) {
            (newReports + activeTasks)
                .flatMap { report ->
                    listOf(
                        report.code,
                        report.location,
                        report.type,
                        report.vehiclePlate,
                        report.tpaName,
                    )
                }
                .filter(String::isNotBlank)
                .distinct()
        }
    val tasksNeedingRevision = activeTasks.filter {
        it.completionStatus.equals("perlu_perbaikan", ignoreCase = true)
    }
    val tasksReadyForTransport = activeTasks.filter { it.status == WasteFlow.ASSIGNED }
    val tasksWaitingForAdmin = activeTasks.filter {
        it.status == WasteFlow.TRANSPORTED &&
            it.completionStatus.equals("menunggu", ignoreCase = true)
    }
    val tasksInRoute = activeTasks.filter {
        it.status == WasteFlow.TRANSPORTED &&
            !it.completionStatus.equals("menunggu", ignoreCase = true) &&
            !it.completionStatus.equals("perlu_perbaikan", ignoreCase = true)
    }

    fun matchesTaskSearch(report: WasteRecord, query: String): Boolean {
        val normalizedQuery = query.trim().lowercase()
        return normalizedQuery.isBlank() ||
            listOf(
                    report.code,
                    report.type,
                    report.location,
                    report.description,
                    report.vehiclePlate,
                    report.tpaName,
                )
                .any { it.lowercase().contains(normalizedQuery) }
    }

    val filteredIncomingReports = newReports.filter { matchesTaskSearch(it, incomingSearch) }
    val filteredActiveTasks = activeTasks.filter { task ->
        matchesTaskSearch(task, activeTaskSearch) &&
            when (activeTaskFilter) {
                "perbaikan" -> task in tasksNeedingRevision
                "siap" -> task in tasksReadyForTransport
                "rute" -> task in tasksInRoute
                "admin" -> task in tasksWaitingForAdmin
                else -> true
            }
    }
    val filteredHistoryTasks = historyTasks.filter { task ->
        matchesTaskSearch(task, historySearch) &&
            when (historyFilter) {
                "selesai" -> task.status == WasteFlow.DONE
                "ditolak" -> task.status == WasteFlow.REJECTED
                else -> true
            }
    }
    val filteredArchivedHistory = archivedTasks.filter { archived ->
        matchesTaskSearch(archived.report, historySearch) &&
            when (historyFilter) {
                "selesai" -> archived.report.status == WasteFlow.DONE
                "ditolak" -> archived.report.status == WasteFlow.REJECTED
                else -> true
            }
    }

    fun hasLoadError(label: String): Boolean =
        errorMessage.lineSequence().any { it.startsWith("$label:") }

    val sectionDataHasLoadError =
        when (currentSection) {
            "ringkasan" -> hasLoadError("Penugasan") || hasLoadError("Laporan masuk")
            "tugas" ->
                when (selectedWorkArea) {
                    "laporan" -> hasLoadError("Laporan masuk")
                    "aktif" -> hasLoadError("Penugasan")
                    "armada" -> hasLoadError("Armada")
                    "perbaikan" -> hasLoadError("Perbaikan armada")
                    "tpa" -> hasLoadError("TPA")
                    else -> false
                }
            "riwayat" -> hasLoadError(if (showingTaskArchive) "Arsip Petugas" else "Penugasan")
            else -> false
        }
    val sectionDataIsEmpty =
        when (currentSection) {
            "ringkasan" -> activeTasks.isEmpty() && newReports.isEmpty()
            "tugas" ->
                when (selectedWorkArea) {
                    "laporan" -> newReports.isEmpty()
                    "aktif" -> activeTasks.isEmpty()
                    "armada" -> vehicles.isEmpty()
                    "perbaikan" -> vehicleRepairs.isEmpty()
                    "tpa" -> facilities.isEmpty()
                    else -> true
                }
            "riwayat" -> if (showingTaskArchive) archivedTasks.isEmpty() else historyTasks.isEmpty()
            else -> true
        }

    AdminPage(
        current = "tasks",
        scrollKey = "$currentSection:$showingTaskArchive",
        account = account,
        onNavigate = {},
        onLogout = onLogout,
        onOpenNotifications = onOpenNotifications,
        topBarContent = {
            PetugasTopBar(
                account = account,
                onOpenProfile = { selectedSection = "profil" },
                onOpenNotifications = onOpenNotifications,
                onLogout = onLogout,
            )
        },
        workspacePaletteOverride =
            if (LocalSmartWasteAppearance.current.darkMode) {
                PetugasNightPalette
            } else {
                PetugasDayPalette
            },
        showTopBar = currentSection != "ringkasan",
        fullBleedContent =
            if (currentSection == "ringkasan") {
                {
                    RoleEntrySection(entryKey = entryKey, order = 0, animateLift = false) {
                        PetugasJourneyHero(
                            account = account,
                            tasks = tasks,
                            incomingReportCount = newReports.size,
                            searchQuery = homeSearchQuery,
                            searchSuggestions = homeSearchSuggestions,
                            onSearchQueryChange = { homeSearchQuery = it.take(100) },
                            onSubmitSearch = {
                                val query = homeSearchQuery.trim()
                                if (query.isNotBlank()) {
                                    val hasActiveMatch = activeTasks.any {
                                        matchesTaskSearch(it, query)
                                    }
                                    val hasIncomingMatch = newReports.any {
                                        matchesTaskSearch(it, query)
                                    }
                                    activeTaskSearch = query
                                    incomingSearch = query
                                    activeTaskFilter = "semua"
                                    selectedWorkArea =
                                        if (hasActiveMatch || !hasIncomingMatch) "aktif"
                                        else "laporan"
                                    homeSearchQuery = ""
                                    selectedSection = "tugas"
                                }
                            },
                            onOpenTasks = {
                                selectedSection = "tugas"
                                selectedWorkArea =
                                    if (activeTasks.isNotEmpty()) "aktif" else "laporan"
                                activeTaskFilter = "semua"
                                activeTaskSearch = ""
                                incomingSearch = ""
                            },
                            onOpenNotifications = onOpenNotifications,
                            onOpenProfile = { selectedSection = "profil" },
                            onLogout = onLogout,
                        )
                    }
                }
            } else null,
        bottomBar = { PetugasNavigationBar(currentSection) { selectedSection = it } },
        backgroundContent = { PetugasThemeBackdrop(Modifier.fillMaxSize()) },
    ) {
        if (currentSection != "profil") {
            if (sectionDataHasLoadError && !sectionDataIsEmpty) {
                StaggeredPageEntry("petugas-$currentSection", 0) {
                    PetugasLoadFailure(
                        "Sebagian data belum diperbarui. Data tersimpan tetap ditampilkan.",
                        onRefresh,
                    )
                }
            }
        }
        if (currentSection != "profil") {
            if (reportActionError.isNotBlank()) {
                StaggeredPageEntry("petugas-$currentSection", 1) { InlineError(reportActionError) }
            }
        }
        AnimatedContent(
            targetState = currentSection,
            modifier = Modifier.fillMaxWidth(),
            transitionSpec = {
                val sectionOrder = listOf("ringkasan", "tugas", "riwayat", "profil")
                val direction =
                    if (sectionOrder.indexOf(targetState) >= sectionOrder.indexOf(initialState)) 1
                    else -1
                if (targetState in setOf("tugas", "riwayat", "profil")) {
                    fadeIn(tween(150, easing = FastOutSlowInEasing)) togetherWith
                        fadeOut(tween(130))
                } else {
                    (slideInHorizontally(
                        animationSpec = tween(260, easing = FastOutSlowInEasing),
                    ) { width ->
                        width * direction * 18 / 100
                    } + fadeIn(tween(200))) togetherWith
                        (slideOutHorizontally(
                            animationSpec = tween(220, easing = FastOutSlowInEasing),
                        ) { width ->
                            -width * direction * 12 / 100
                        } + fadeOut(tween(160)))
                }
            },
            label = "petugas-section-slide",
        ) { section ->
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(13.dp),
            ) {
                when (section) {
                    "ringkasan" -> {
                        val nextTask = activeTasks.firstOrNull { it !in tasksWaitingForAdmin }
                        val nextReport = newReports.firstOrNull()
                        if (isInitialLoading) {
                            PetugasLoadingState(
                                "Menyiapkan ruang kerja",
                                "Memuat laporan, tugas, dan armada.",
                            )
                        } else {
                            val summaryUnavailable =
                                activeTasks.isEmpty() &&
                                    newReports.isEmpty() &&
                                    (hasLoadError("Penugasan") || hasLoadError("Laporan masuk"))
                            if (summaryUnavailable) {
                                PetugasLoadFailure("Ringkasan belum dapat dimuat.", onRefresh)
                            } else {
                                RoleEntrySection(
                                    entryKey = entryKey,
                                    order = 1,
                                    animateLift = false,
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically,
                                    ) {
                                        Column(
                                            modifier = Modifier.weight(1f),
                                            verticalArrangement = Arrangement.spacedBy(2.dp),
                                        ) {
                                            Text(
                                                "Akses utama",
                                                color = AppText,
                                                fontSize = 16.sp,
                                                lineHeight = 21.sp,
                                                fontWeight = FontWeight.SemiBold,
                                            )
                                            Text(
                                                "Laporan, tugas, armada, dan TPA.",
                                                color = AppTextMuted,
                                                fontSize = 12.sp,
                                                lineHeight = 16.sp,
                                                maxLines = 1,
                                                overflow = TextOverflow.Ellipsis,
                                            )
                                        }
                                        TextButton(
                                            onClick = {
                                                selectedSection = "tugas"
                                                selectedWorkArea =
                                                    if (activeTasks.isNotEmpty()) "aktif"
                                                    else "laporan"
                                            },
                                            contentPadding =
                                                androidx.compose.foundation.layout.PaddingValues(
                                                    horizontal = 6.dp,
                                                    vertical = 4.dp,
                                                ),
                                        ) {
                                            Text(
                                                "Semua tugas  ›",
                                                color = PetugasAccent,
                                                fontSize = 12.sp,
                                                fontWeight = FontWeight.SemiBold,
                                            )
                                        }
                                    }
                                }
                                RoleEntrySection(
                                    entryKey = entryKey,
                                    order = 2,
                                    animateLift = false,
                                ) {
                                    Row(
                                        modifier =
                                            Modifier.fillMaxWidth()
                                                .clip(RoundedCornerShape(20.dp))
                                                .background(AppSurface)
                                                .padding(horizontal = 4.dp, vertical = 7.dp),
                                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    ) {
                                        PetugasActionTile(
                                            "Masuk",
                                            newReports.size,
                                            Icons.Filled.Inbox,
                                            Modifier.weight(1f),
                                        ) {
                                            selectedSection = "tugas"
                                            selectedWorkArea = "laporan"
                                        }
                                        PetugasActionTile(
                                            "Aktif",
                                            activeTasks.size,
                                            Icons.Rounded.TaskAlt,
                                            Modifier.weight(1f),
                                        ) {
                                            selectedSection = "tugas"
                                            selectedWorkArea = "aktif"
                                        }
                                        PetugasActionTile(
                                            "Armada",
                                            vehicles.size,
                                            Icons.Filled.LocalShipping,
                                            Modifier.weight(1f),
                                        ) {
                                            selectedSection = "tugas"
                                            selectedWorkArea = "armada"
                                        }
                                        PetugasActionTile(
                                            "TPA",
                                            facilities.size,
                                            Icons.Filled.Place,
                                            Modifier.weight(1f),
                                        ) {
                                            selectedSection = "tugas"
                                            selectedWorkArea = "tpa"
                                        }
                                    }
                                }
                                RoleEntrySection(
                                    entryKey = entryKey,
                                    order = 3,
                                    verticalArrangement = Arrangement.spacedBy(13.dp),
                                    animateLift = false,
                                ) {
                                    when {
                                        nextTask != null -> {
                                            Text(
                                                "Tugas prioritas",
                                                color = AppText,
                                                fontSize = 16.sp,
                                                lineHeight = 21.sp,
                                                fontWeight = FontWeight.SemiBold,
                                            )
                                            PetugasTaskCard(
                                                report = nextTask,
                                                onOpenPhoto = onOpenPhoto,
                                                dense = true,
                                                onMarkTransported = {
                                                    confirmingTransportTask = it
                                                },
                                                isTransporting = transportingTaskId == nextTask.id,
                                                isActionEnabled = transportingTaskId == null,
                                                onComplete = ::requestCompletionProof,
                                                onSendLocationEvidence = ::requestLocationPhoto,
                                                isSendingLocationEvidence =
                                                    sendingLocationTaskId == nextTask.id,
                                            )
                                            TextButton(
                                                onClick = {
                                                    selectedSection = "tugas"
                                                    selectedWorkArea = "aktif"
                                                    activeTaskFilter = "semua"
                                                    activeTaskSearch = ""
                                                },
                                                modifier =
                                                    Modifier.align(Alignment.CenterHorizontally),
                                            ) {
                                                Text(
                                                    "Lihat semua tugas aktif",
                                                    color = PetugasAccent,
                                                    fontWeight = FontWeight.SemiBold,
                                                    fontSize = 12.sp,
                                                )
                                            }
                                        }
                                        nextReport != null -> {
                                            PetugasSectionHeading(
                                                eyebrow = "ANTREAN MASUK",
                                                title = "Laporan baru",
                                                subtitle =
                                                    "Tinjau kondisi dan siapkan penanganannya.",
                                                icon = Icons.Filled.Inbox,
                                            )
                                            AdminReportCard(
                                                report = nextReport,
                                                onOpenPhoto = onOpenPhoto,
                                                onReject = {
                                                    rejectionNote = ""
                                                    rejectingReport = nextReport
                                                },
                                                onAssign = { assigningReport = nextReport },
                                                compact = true,
                                                assignmentLabel = "Terima & tugaskan",
                                                petugasStyle = true,
                                            )
                                        }
                                        tasksWaitingForAdmin.isNotEmpty() -> {
                                            PetugasSectionHeading(
                                                eyebrow = "KONFIRMASI KERJA",
                                                title = "Bukti menunggu Admin",
                                                subtitle =
                                                    "${tasksWaitingForAdmin.size} bukti sedang diperiksa. " +
                                                        "Tidak perlu dikirim ulang.",
                                                icon = Icons.Filled.CheckCircle,
                                            )
                                            PetugasTaskCard(
                                                report = tasksWaitingForAdmin.first(),
                                                onOpenPhoto = onOpenPhoto,
                                            )
                                            TextButton(
                                                onClick = {
                                                    selectedSection = "tugas"
                                                    selectedWorkArea = "aktif"
                                                    activeTaskFilter = "admin"
                                                },
                                                modifier =
                                                    Modifier.align(Alignment.CenterHorizontally),
                                            ) {
                                                Text(
                                                    "Lihat bukti terkirim",
                                                    color = PetugasAccent,
                                                    fontWeight = FontWeight.SemiBold,
                                                    fontSize = 12.sp,
                                                )
                                            }
                                        }
                                        else ->
                                            PetugasEmptyState(
                                                "Semua pekerjaan tertangani",
                                                "Belum ada tugas aktif atau laporan baru. " +
                                                    "Tarik ke bawah untuk memperbarui.",
                                            )
                                    }
                                }
                            }
                        }
                    }

                    "tugas" -> {
                        val workAreaHeading =
                            when (selectedWorkArea) {
                                "laporan" ->
                                    "Laporan masuk" to "Tinjau laporan dan siapkan penugasan."
                                "aktif" -> "Tugas aktif" to "Pantau progres dan kirim bukti kerja."
                                "armada" -> "Armada" to "Cek kapasitas dan kondisi kendaraan."
                                "perbaikan" ->
                                    "Proses perbaikan" to "Lanjutkan perbaikan dan kirim hasilnya."
                                else -> "TPA" to "Cek kapasitas sebelum mengatur angkutan."
                            }
                        StaggeredPageEntry("petugas-tugas-$selectedWorkArea", 2) {
                            PetugasSectionHeading(
                                eyebrow = "POS OPERASIONAL",
                                title = workAreaHeading.first,
                                subtitle = workAreaHeading.second,
                                icon =
                                    when (selectedWorkArea) {
                                        "laporan" -> Icons.Filled.Inbox
                                        "aktif" -> Icons.Rounded.TaskAlt
                                        "armada" -> Icons.Filled.LocalShipping
                                        "perbaikan" -> Icons.Filled.Build
                                        else -> Icons.Filled.Place
                                    },
                            )
                        }
                        StaggeredPageEntry("petugas-tugas-$selectedWorkArea", 3) {
                            PetugasFilterGrid(
                                title = "Area kerja",
                                choices =
                                    listOf(
                                        PetugasFilterOption("laporan", "Masuk", newReports.size),
                                        PetugasFilterOption("aktif", "Aktif", activeTasks.size),
                                        PetugasFilterOption("armada", "Armada", vehicles.size),
                                        PetugasFilterOption(
                                            "perbaikan",
                                            "Perbaikan",
                                            vehicleRepairs.count {
                                                it.status !in setOf("selesai", "ditolak")
                                            },
                                        ),
                                        PetugasFilterOption("tpa", "TPA", facilities.size),
                                    ),
                                selectedKey = selectedWorkArea,
                                onSelect = { selectedWorkArea = it },
                            )
                        }
                        when (selectedWorkArea) {
                            "laporan" -> {
                                StaggeredPageEntry("petugas-tugas-laporan", 4) {
                                    PetugasQueueSearch(
                                        query = incomingSearch,
                                        onQueryChange = { incomingSearch = it.take(100) },
                                        placeholder = "Cari kode, jenis, atau lokasi",
                                        suggestionSource =
                                            newReports.flatMap {
                                                listOf(it.code, it.type, it.location)
                                            },
                                    )
                                }
                                if (isInitialLoading && newReports.isEmpty()) {
                                    StaggeredPageEntry("petugas-tugas-laporan", 5) {
                                        PetugasLoadingState(
                                            "Memuat laporan",
                                            "Mengambil antrean terbaru dari server.",
                                        )
                                    }
                                } else if (newReports.isEmpty() && hasLoadError("Laporan masuk")) {
                                    StaggeredPageEntry("petugas-tugas-laporan", 5) {
                                        PetugasLoadFailure("Laporan belum dapat dimuat.", onRefresh)
                                    }
                                } else if (newReports.isEmpty()) {
                                    StaggeredPageEntry("petugas-tugas-laporan", 5) {
                                        PetugasEmptyState(
                                            "Tidak ada laporan baru",
                                            "Laporan warga yang menunggu penanganan akan tampil di sini.",
                                        )
                                    }
                                } else if (filteredIncomingReports.isEmpty()) {
                                    StaggeredPageEntry("petugas-tugas-laporan", 5) {
                                        PetugasEmptyState(
                                            "Laporan tidak ditemukan",
                                            "Coba kata kunci lain atau hapus pencarian.",
                                        )
                                    }
                                    TextButton(
                                        onClick = { incomingSearch = "" },
                                        modifier = Modifier.align(Alignment.CenterHorizontally),
                                    ) {
                                        Text(
                                            "Hapus pencarian",
                                            color = LoginAccent,
                                            fontWeight = FontWeight.SemiBold,
                                        )
                                    }
                                } else {
                                    filteredIncomingReports.forEachIndexed { index, report ->
                                        StaggeredPageEntry("petugas-tugas-laporan", 5 + index) {
                                            AdminReportCard(
                                                report = report,
                                                onOpenPhoto = onOpenPhoto,
                                                onReject = {
                                                    rejectionNote = ""
                                                    rejectingReport = report
                                                },
                                                onAssign = { assigningReport = report },
                                                compact = true,
                                                assignmentLabel = "Terima & tugaskan",
                                                petugasStyle = true,
                                            )
                                        }
                                    }
                                }
                            }

                            "aktif" -> {
                                StaggeredPageEntry("petugas-tugas-aktif", 4) {
                                    PetugasQueueSearch(
                                        query = activeTaskSearch,
                                        onQueryChange = { activeTaskSearch = it.take(100) },
                                        placeholder = "Cari kode, lokasi, armada, atau TPA",
                                        suggestionSource =
                                            activeTasks.flatMap {
                                                listOf(
                                                    it.code,
                                                    it.location,
                                                    it.vehiclePlate,
                                                    it.tpaName,
                                                )
                                            },
                                    )
                                }
                                StaggeredPageEntry("petugas-tugas-aktif", 5) {
                                    PetugasFilterGrid(
                                        title = "Status tugas",
                                        choices =
                                            listOf(
                                                PetugasFilterOption(
                                                    "semua",
                                                    "Semua",
                                                    activeTasks.size,
                                                ),
                                                PetugasFilterOption(
                                                    "perbaikan",
                                                    "Perbaikan",
                                                    tasksNeedingRevision.size,
                                                ),
                                                PetugasFilterOption(
                                                    "siap",
                                                    "Siap angkut",
                                                    tasksReadyForTransport.size,
                                                ),
                                                PetugasFilterOption(
                                                    "rute",
                                                    "Dalam rute",
                                                    tasksInRoute.size,
                                                ),
                                                PetugasFilterOption(
                                                    "admin",
                                                    "Menunggu Admin",
                                                    tasksWaitingForAdmin.size,
                                                ),
                                            ),
                                        selectedKey = activeTaskFilter,
                                        onSelect = { activeTaskFilter = it },
                                    )
                                }
                                if (isInitialLoading && activeTasks.isEmpty()) {
                                    StaggeredPageEntry("petugas-tugas-aktif", 6) {
                                        PetugasLoadingState(
                                            "Memuat tugas",
                                            "Mengambil penugasan akun ini dari server.",
                                        )
                                    }
                                } else if (activeTasks.isEmpty() && hasLoadError("Penugasan")) {
                                    StaggeredPageEntry("petugas-tugas-aktif", 6) {
                                        PetugasLoadFailure("Tugas belum dapat dimuat.", onRefresh)
                                    }
                                } else if (activeTasks.isEmpty()) {
                                    StaggeredPageEntry("petugas-tugas-aktif", 6) {
                                        PetugasEmptyState(
                                            "Belum ada tugas aktif",
                                            "Tugas yang sudah diterima akan muncul di sini.",
                                        )
                                    }
                                } else if (filteredActiveTasks.isEmpty()) {
                                    StaggeredPageEntry("petugas-tugas-aktif", 6) {
                                        PetugasEmptyState(
                                            if (activeTaskSearch.isNotBlank())
                                                "Tugas tidak ditemukan"
                                            else "Tidak ada tugas pada filter ini",
                                            "Ubah filter atau kata kunci untuk melihat penugasan lain.",
                                        )
                                    }
                                    TextButton(
                                        onClick = {
                                            activeTaskFilter = "semua"
                                            activeTaskSearch = ""
                                        },
                                        modifier = Modifier.align(Alignment.CenterHorizontally),
                                    ) {
                                        Text(
                                            "Tampilkan semua tugas",
                                            color = LoginAccent,
                                            fontWeight = FontWeight.SemiBold,
                                        )
                                    }
                                } else {
                                    filteredActiveTasks.forEachIndexed { index, task ->
                                        StaggeredPageEntry("petugas-tugas-aktif", 6 + index) {
                                            PetugasTaskCard(
                                                report = task,
                                                onOpenPhoto = onOpenPhoto,
                                                onMarkTransported = {
                                                    confirmingTransportTask = it
                                                },
                                                isTransporting = transportingTaskId == task.id,
                                                isActionEnabled = transportingTaskId == null,
                                                onComplete = ::requestCompletionProof,
                                                onSendLocationEvidence = ::requestLocationPhoto,
                                                isSendingLocationEvidence =
                                                    sendingLocationTaskId == task.id,
                                            )
                                        }
                                    }
                                }
                            }

                            "perbaikan" -> {
                                PetugasVehicleRepairQueue(
                                    repairs = vehicleRepairs,
                                    showingHistory = showingRepairHistory,
                                    isInitialLoading = isInitialLoading,
                                    hasLoadError = hasLoadError("Perbaikan armada"),
                                    onRefresh = onRefresh,
                                    onHistoryChange = { showingRepairHistory = it },
                                    onRepairAction = { repair, action ->
                                        vehicleRepairAction = repair to action
                                    },
                                )
                            }

                            "armada" -> {
                                PetugasFleetList(
                                    vehicles = vehicles,
                                    repairs = vehicleRepairs,
                                    clearanceRequests = capacityClearanceRequests,
                                    isInitialLoading = isInitialLoading,
                                    hasLoadError = hasLoadError("Armada"),
                                    onRefresh = onRefresh,
                                    onCreateVehicle = { showVehicleForm = true },
                                    onEditVehicle = { vehicle ->
                                        editingVehicle = vehicle
                                        showVehicleForm = true
                                    },
                                    onReportDamage = { reportingDamageVehicle = it },
                                    onOpenRepairQueue = { selectedWorkArea = "perbaikan" },
                                    onOpenClearance = { type, id, label ->
                                        capacityClearanceError = ""
                                        capacityClearancePhoto = null
                                        capacityClearanceReason = ""
                                        capacityClearanceTarget = Triple(type, id, label)
                                    },
                                )
                            }

                            "tpa" -> {
                                PetugasFacilityList(
                                    facilities = facilities,
                                    clearanceRequests = capacityClearanceRequests,
                                    isInitialLoading = isInitialLoading,
                                    hasLoadError = hasLoadError("TPA"),
                                    onRefresh = onRefresh,
                                    onCreateFacility = { showFacilityForm = true },
                                    onEditFacility = { facility ->
                                        editingFacility = facility
                                        showFacilityForm = true
                                    },
                                    onOpenClearance = { type, id, label ->
                                        capacityClearanceError = ""
                                        capacityClearancePhoto = null
                                        capacityClearanceReason = ""
                                        capacityClearanceTarget = Triple(type, id, label)
                                    },
                                )
                            }
                        }
                    }

                    "riwayat" -> {
                        val historyAnimationKey =
                            "petugas-history-$showingTaskArchive-$historyFilter"
                        StaggeredPageEntry(historyAnimationKey, 2) {
                            PetugasSectionHeading(
                                eyebrow = "CATATAN SHIFT",
                                title =
                                    if (showingTaskArchive) "Arsip pekerjaan"
                                    else "Riwayat pekerjaan",
                                subtitle =
                                    if (showingTaskArchive)
                                        "Catatan tersimpan dan dapat dipulihkan."
                                    else
                                        "${completedTasks.size} selesai · ${rejectedTasks.size} ditolak",
                                icon = Icons.Rounded.History,
                            )
                        }
                        StaggeredPageEntry(historyAnimationKey, 3) {
                            PetugasHistoryFilterBar(
                                showingArchive = showingTaskArchive,
                                historyCount = historyTasks.size,
                                archiveCount = archivedTasks.size,
                                statusChoices =
                                    listOf(
                                        PetugasFilterOption(
                                            "semua",
                                            "Semua",
                                            historyFilterRows.size,
                                        ),
                                        PetugasFilterOption(
                                            "selesai",
                                            "Selesai",
                                            historyFilterRows.count { it.status == WasteFlow.DONE },
                                        ),
                                        PetugasFilterOption(
                                            "ditolak",
                                            "Ditolak",
                                            historyFilterRows.count {
                                                it.status == WasteFlow.REJECTED
                                            },
                                        ),
                                    ),
                                selectedStatus = historyFilter,
                                onSelectArchive = {
                                    showingTaskArchive = it
                                    historyFilter = "semua"
                                    archiveActionError = ""
                                },
                                onSelectStatus = { historyFilter = it },
                            )
                        }
                        StaggeredPageEntry(historyAnimationKey, 4) {
                            PetugasQueueSearch(
                                query = historySearch,
                                onQueryChange = { historySearch = it.take(100) },
                                placeholder =
                                    if (showingTaskArchive) "Cari arsip kode, lokasi, atau TPA"
                                    else "Cari kode, lokasi, atau TPA",
                                suggestionSource =
                                    (historyFilterRows + archivedTasks.map { it.report }).flatMap {
                                        listOf(it.code, it.location, it.vehiclePlate, it.tpaName)
                                    },
                            )
                        }
                        if (archiveActionError.isNotBlank())
                            StaggeredPageEntry(historyAnimationKey, 5) {
                                InlineError(archiveActionError)
                            }
                        if (showingTaskArchive) {
                            when {
                                isInitialLoading && archivedTasks.isEmpty() ->
                                    StaggeredPageEntry(historyAnimationKey, 6) {
                                        PetugasLoadingState(
                                            "Memuat arsip",
                                            "Mengambil catatan yang tersimpan.",
                                        )
                                    }
                                archivedTasks.isEmpty() && hasLoadError("Arsip Petugas") ->
                                    StaggeredPageEntry(historyAnimationKey, 6) {
                                        PetugasLoadFailure("Arsip belum dapat dimuat.", onRefresh)
                                    }
                                filteredArchivedHistory.isEmpty() && archivedTasks.isEmpty() ->
                                    StaggeredPageEntry(historyAnimationKey, 6) {
                                        PetugasEmptyState(
                                            "Arsip masih kosong",
                                            "Arsipkan tugas selesai atau laporan ditolak dari riwayat.",
                                        )
                                    }
                                filteredArchivedHistory.isEmpty() ->
                                    StaggeredPageEntry(historyAnimationKey, 6) {
                                        PetugasEmptyState(
                                            "Arsip tidak ditemukan",
                                            "Coba kata kunci atau filter status yang lain.",
                                        )
                                    }
                            }
                            filteredArchivedHistory.forEachIndexed { index, archived ->
                                StaggeredPageEntry(historyAnimationKey, 7 + index) {
                                    Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
                                        PetugasTaskCard(
                                            archived.report,
                                            onOpenPhoto,
                                            compactHistory = true,
                                        )
                                        Column(
                                            modifier =
                                                Modifier.fillMaxWidth().padding(horizontal = 4.dp),
                                            verticalArrangement = Arrangement.spacedBy(2.dp),
                                        ) {
                                            Text(
                                                "Disimpan ${archived.archivedAt.toDisplayDateTime()} · " +
                                                    "${archived.archivedBy.displayPersonName()}",
                                                color = AppTextMuted,
                                                fontSize = 10.sp,
                                                maxLines = 1,
                                                overflow = TextOverflow.Ellipsis,
                                            )
                                            Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalArrangement = Arrangement.End,
                                                verticalAlignment = Alignment.CenterVertically,
                                            ) {
                                                TextButton(
                                                    onClick = {
                                                        restoringHistoryTask = archived.report
                                                    },
                                                    enabled = !archiveActionRunning,
                                                    contentPadding =
                                                        androidx.compose.foundation.layout
                                                            .PaddingValues(
                                                                horizontal = 8.dp,
                                                                vertical = 2.dp,
                                                            ),
                                                ) {
                                                    Text(
                                                        "Pulihkan",
                                                        color = LoginAccent,
                                                        fontSize = 11.sp,
                                                    )
                                                }
                                                TextButton(
                                                    onClick = {
                                                        deletingArchivedTask = archived.report
                                                    },
                                                    enabled = !archiveActionRunning,
                                                    contentPadding =
                                                        androidx.compose.foundation.layout
                                                            .PaddingValues(
                                                                horizontal = 8.dp,
                                                                vertical = 2.dp,
                                                            ),
                                                ) {
                                                    Text(
                                                        "Hapus permanen",
                                                        color = RoleRed,
                                                        fontSize = 11.sp,
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        } else {
                            when {
                                isInitialLoading && historyTasks.isEmpty() ->
                                    StaggeredPageEntry(historyAnimationKey, 6) {
                                        PetugasLoadingState(
                                            "Memuat riwayat",
                                            "Mengambil pekerjaan yang sudah selesai.",
                                        )
                                    }
                                historyTasks.isEmpty() && hasLoadError("Penugasan") ->
                                    StaggeredPageEntry(historyAnimationKey, 6) {
                                        PetugasLoadFailure("Riwayat belum dapat dimuat.", onRefresh)
                                    }
                                historyTasks.isEmpty() ->
                                    StaggeredPageEntry(historyAnimationKey, 6) {
                                        PetugasEmptyState(
                                            "Belum ada riwayat",
                                            "Tugas selesai dan laporan yang ditolak akan tersimpan di sini.",
                                        )
                                    }
                                filteredHistoryTasks.isEmpty() ->
                                    StaggeredPageEntry(historyAnimationKey, 6) {
                                        PetugasEmptyState(
                                            if (historySearch.isNotBlank())
                                                "Riwayat tidak ditemukan"
                                            else "Belum ada hasil pada filter ini",
                                            "Ubah filter atau kata kunci untuk melihat catatan lain.",
                                        )
                                    }
                            }
                            filteredHistoryTasks.forEachIndexed { index, task ->
                                StaggeredPageEntry(historyAnimationKey, 7 + index) {
                                    PetugasTaskCard(
                                        task,
                                        onOpenPhoto,
                                        compactHistory = true,
                                        onArchive = { archivingHistoryTask = task },
                                    )
                                }
                            }
                        }
                    }

                    "profil" ->
                        AccountProfileSettings(
                            account,
                            onLogout,
                            onUpdateProfile,
                            onUpdateProfilePhoto,
                            onOpenPhoto,
                        )
                }
            }
        }
    }
    capacityClearanceTarget?.let { target ->
        val targetLabel = if (target.first == "tpa") "TPA" else "armada"
        AlertDialog(
            onDismissRequest = { if (!capacityClearanceSending) capacityClearanceTarget = null },
            title = {
                Text(
                    "Ajukan pengosongan $targetLabel",
                    color = LoginInk,
                    fontWeight = FontWeight.Bold,
                )
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(target.third, color = AppText, fontWeight = FontWeight.SemiBold)
                    Text(
                        "Pastikan aset benar-benar sudah dikosongkan. " +
                            "Kapasitas tetap terkunci sampai Admin memeriksa foto " +
                            "dan menyetujui permintaan.",
                        color = AppTextMuted,
                        fontSize = 12.sp,
                        lineHeight = 17.sp,
                    )
                    TextButton(
                        enabled = !capacityClearanceSending,
                        onClick = { capacityClearancePicker.launch(arrayOf("image/*")) },
                        contentPadding =
                            androidx.compose.foundation.layout.PaddingValues(
                                horizontal = 0.dp,
                                vertical = 0.dp,
                            ),
                    ) {
                        Text(
                            if (capacityClearancePhoto == null) "Pilih foto kondisi kosong"
                            else "Ganti foto bukti",
                            color = LoginAccent,
                            fontWeight = FontWeight.SemiBold,
                        )
                    }
                    if (capacityClearancePhoto != null) {
                        Text("Foto bukti sudah dipilih", color = LoginAccent, fontSize = 11.sp)
                    }
                    OutlinedTextField(
                        value = capacityClearanceReason,
                        onValueChange = { capacityClearanceReason = it.take(500) },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Catatan proses pengosongan") },
                        placeholder = { Text("Jelaskan pemindahan muatan dan lokasi tujuan") },
                        minLines = 2,
                        maxLines = 4,
                        enabled = !capacityClearanceSending,
                    )
                    if (capacityClearanceError.isNotBlank()) InlineError(capacityClearanceError)
                }
            },
            confirmButton = {
                TextButton(
                    enabled =
                        capacityClearancePhoto != null &&
                            capacityClearanceReason.trim().length >= 8 &&
                            !capacityClearanceSending,
                    onClick = {
                        val photo = capacityClearancePhoto ?: return@TextButton
                        scope.launch {
                            capacityClearanceSending = true
                            capacityClearanceError = ""
                            try {
                                onRequestCapacityClearance(
                                    target.first,
                                    target.second,
                                    photo,
                                    capacityClearanceReason.trim(),
                                )
                                capacityClearanceTarget = null
                                capacityClearancePhoto = null
                                capacityClearanceReason = ""
                            } catch (cancelled: CancellationException) {
                                throw cancelled
                            } catch (error: Exception) {
                                capacityClearanceError =
                                    error.message ?: "Permintaan pengosongan gagal dikirim."
                            } finally {
                                capacityClearanceSending = false
                            }
                        }
                    },
                ) {
                    Text(
                        if (capacityClearanceSending) "Mengirim…" else "Kirim ke Admin",
                        color = LoginAccent,
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { capacityClearanceTarget = null },
                    enabled = !capacityClearanceSending,
                ) {
                    Text("Batal", color = LoginMuted)
                }
            },
            containerColor = AppSurface,
        )
    }
    assigningReport?.let { report ->
        PetugasAssignmentDialog(
            report = report,
            vehicles = vehicles,
            facilities = facilities,
            onDismiss = { assigningReport = null },
            onAssign = { vehicleId, tpaId ->
                onAssignReport(report, vehicleId, tpaId)
                assigningReport = null
                reportActionError = ""
            },
        )
    }
    rejectingReport?.let { report ->
        AlertDialog(
            onDismissRequest = { if (!processingReportAction) rejectingReport = null },
            title = { Text("Tolak laporan ini?", color = LoginInk, fontWeight = FontWeight.Bold) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("${report.code} · ${report.type}", color = AppTextMuted, fontSize = 11.sp)
                    Text(
                        "Petugas menolak laporan setelah meninjaunya. " +
                            "Admin tetap dapat melihat catatan dan statusnya.",
                        color = AppText,
                        fontSize = 11.sp,
                    )
                    OutlinedTextField(
                        value = rejectionNote,
                        onValueChange = { rejectionNote = it.take(500) },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Alasan penolakan") },
                        minLines = 2,
                        maxLines = 3,
                        enabled = !processingReportAction,
                    )
                    Text(
                        "Alasan wajib diisi agar keputusan bisa ditinjau admin.",
                        color = AppTextMuted,
                        fontSize = 10.sp,
                    )
                }
            },
            confirmButton = {
                TextButton(
                    enabled = !processingReportAction && rejectionNote.isNotBlank(),
                    onClick = {
                        scope.launch {
                            processingReportAction = true
                            reportActionError = ""
                            try {
                                onRejectReport(report, rejectionNote.trim())
                                rejectingReport = null
                            } catch (cancelled: CancellationException) {
                                throw cancelled
                            } catch (error: Exception) {
                                reportActionError = error.message ?: "Laporan gagal ditolak."
                            } finally {
                                processingReportAction = false
                            }
                        }
                    },
                ) {
                    Text(
                        if (processingReportAction) "Menyimpan…" else "Tolak laporan",
                        color = RoleRed,
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { rejectingReport = null },
                    enabled = !processingReportAction,
                ) {
                    Text("Batal", color = LoginMuted)
                }
            },
            containerColor = AppSurface,
        )
    }
    confirmingTransportTask?.let { task ->
        AlertDialog(
            onDismissRequest = { if (transportingTaskId == null) confirmingTransportTask = null },
            title = { Text("Mulai pengangkutan?", color = LoginInk, fontWeight = FontWeight.Bold) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(7.dp)) {
                    Text(
                        "${task.code} · ${task.weightKg.pretty()} kg",
                        color = AppText,
                        fontWeight = FontWeight.SemiBold,
                    )
                    Text(
                        "Kendaraan ${task.vehiclePlate.ifBlank { "belum ditentukan" }} akan memuat " +
                            "sampah dari ${task.location}. Tujuan: " +
                            "${task.tpaName.ifBlank { "TPA belum ditentukan" }}.",
                        color = AppTextMuted,
                        fontSize = 12.sp,
                        lineHeight = 17.sp,
                    )
                    Text(
                        "Pastikan sampah sudah dimuat sebelum mengubah status.",
                        color = AppTextMuted,
                        fontSize = 11.sp,
                    )
                }
            },
            confirmButton = {
                TextButton(
                    enabled = transportingTaskId == null,
                    onClick = {
                        confirmingTransportTask = null
                        startTransport(task)
                    },
                ) {
                    Text("Ya, mulai angkut", color = LoginAccent)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { confirmingTransportTask = null },
                    enabled = transportingTaskId == null,
                ) {
                    Text("Kembali", color = LoginMuted)
                }
            },
            containerColor = AppSurface,
        )
    }
    archivingHistoryTask?.let { task ->
        AlertDialog(
            onDismissRequest = { if (!archiveActionRunning) archivingHistoryTask = null },
            title = {
                Text("Arsipkan catatan tugas?", color = LoginInk, fontWeight = FontWeight.Bold)
            },
            text = {
                Text(
                    "${task.code} dan catatan pekerjaannya dipindahkan ke Arsip Petugas. " +
                        "Data tetap tersimpan dan bisa dipulihkan kapan saja.",
                    color = AppTextMuted,
                    fontSize = 12.sp,
                    lineHeight = 18.sp,
                )
            },
            confirmButton = {
                TextButton(
                    enabled = !archiveActionRunning,
                    onClick = {
                        scope.launch {
                            archiveActionRunning = true
                            archiveActionError = ""
                            try {
                                onArchiveTask(task)
                                archivingHistoryTask = null
                                showingTaskArchive = true
                                historyFilter = "semua"
                                historySearch = ""
                            } catch (cancelled: CancellationException) {
                                throw cancelled
                            } catch (error: Exception) {
                                archiveActionError =
                                    error.message ?: "Catatan tugas gagal diarsipkan."
                            } finally {
                                archiveActionRunning = false
                            }
                        }
                    },
                ) {
                    Text(
                        if (archiveActionRunning) "Menyimpan…" else "Arsipkan",
                        color = LoginAccent,
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { archivingHistoryTask = null },
                    enabled = !archiveActionRunning,
                ) {
                    Text("Batal", color = LoginMuted)
                }
            },
            containerColor = AppSurface,
        )
    }
    restoringHistoryTask?.let { task ->
        AlertDialog(
            onDismissRequest = { if (!archiveActionRunning) restoringHistoryTask = null },
            title = {
                Text("Pulihkan catatan tugas?", color = LoginInk, fontWeight = FontWeight.Bold)
            },
            text = {
                Text(
                    "${task.code} akan kembali ke riwayat pekerjaan Petugas.",
                    color = AppTextMuted,
                    fontSize = 12.sp,
                    lineHeight = 18.sp,
                )
            },
            confirmButton = {
                TextButton(
                    enabled = !archiveActionRunning,
                    onClick = {
                        scope.launch {
                            archiveActionRunning = true
                            archiveActionError = ""
                            try {
                                onRestoreArchivedTask(task)
                                restoringHistoryTask = null
                                showingTaskArchive = false
                                historyFilter = "semua"
                                historySearch = ""
                            } catch (cancelled: CancellationException) {
                                throw cancelled
                            } catch (error: Exception) {
                                archiveActionError =
                                    error.message ?: "Catatan tugas gagal dipulihkan."
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
                    onClick = { restoringHistoryTask = null },
                    enabled = !archiveActionRunning,
                ) {
                    Text("Kembali", color = LoginMuted)
                }
            },
            containerColor = AppSurface,
        )
    }
    deletingArchivedTask?.let { task ->
        AlertDialog(
            onDismissRequest = { if (!archiveActionRunning) deletingArchivedTask = null },
            title = {
                Text("Hapus tugas permanen?", color = LoginInk, fontWeight = FontWeight.Bold)
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        "${task.code}, foto, bukti, dan riwayatnya akan dihapus dari database. " +
                            "Laporan ini juga hilang dari akun warga dan Admin serta tidak bisa dipulihkan.",
                        color = AppTextMuted,
                        fontSize = 12.sp,
                        lineHeight = 18.sp,
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
                                onDeleteArchivedTask(task)
                                deletingArchivedTask = null
                                historySearch = ""
                            } catch (cancelled: CancellationException) {
                                throw cancelled
                            } catch (error: Exception) {
                                archiveActionError =
                                    error.message ?: "Tugas gagal dihapus permanen."
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
                    onClick = { deletingArchivedTask = null },
                ) {
                    Text("Batal", color = LoginMuted)
                }
            },
            containerColor = AppSurface,
        )
    }
    if (showProofSourceDialog && selectedCompletionTask != null && selectedProofUri == null) {
        val task = selectedCompletionTask!!
        AlertDialog(
            onDismissRequest = { clearProofSelection() },
            title = { Text("Lampirkan bukti TPA", color = LoginInk, fontWeight = FontWeight.Bold) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(7.dp)) {
                    Text(
                        "${task.code} · ${task.tpaName.ifBlank { "TPA tujuan" }}",
                        color = AppText,
                        fontWeight = FontWeight.SemiBold,
                    )
                    Text(
                        "Ambil foto saat muatan sudah diterima di TPA, atau pilih foto yang tersimpan di perangkat.",
                        color = AppTextMuted,
                        fontSize = 12.sp,
                        lineHeight = 17.sp,
                    )
                    if (proofError.isNotBlank()) InlineError(proofError)
                }
            },
            confirmButton = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    TextButton(
                        onClick = {
                            cameraEvidenceFile?.delete()
                            cameraEvidencePath = null
                            selectedCameraUri = null
                            showProofSourceDialog = false
                            proofPicker.launch(arrayOf("image/*"))
                        },
                    ) {
                        Text("Pilih galeri", color = LoginMuted)
                    }
                    TextButton(onClick = ::launchEvidenceCamera) {
                        Text("Ambil foto", color = LoginAccent, fontWeight = FontWeight.SemiBold)
                    }
                }
            },
            dismissButton = {
                TextButton(onClick = { clearProofSelection() }) {
                    Text("Batal", color = LoginMuted)
                }
            },
            containerColor = AppSurface,
        )
    }
    val taskForProof = selectedCompletionTask
    val proofUri = selectedProofUri
    if (taskForProof != null && proofUri != null) {
        AlertDialog(
            onDismissRequest = { if (!sendingProof) clearProofSelection() },
            title = {
                Text(
                    "Kirim foto sesudah penanganan",
                    color = LoginInk,
                    fontWeight = FontWeight.Bold,
                )
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(9.dp)) {
                    Text(
                        "${taskForProof.code} · ${taskForProof.weightKg.pretty()} kg",
                        color = AppTextMuted,
                        fontSize = 11.sp,
                    )
                    Text(
                        "Ambil foto akhir setelah tugas ditangani dan sampah diterima " +
                            "di ${taskForProof.tpaName.ifBlank { "TPA tujuan" }}. " +
                            "Admin membandingkannya dengan foto awal warga " +
                            "sebelum menyetujui penyelesaian.",
                        color = AppText,
                        fontSize = 11.sp,
                    )
                    OutlinedTextField(
                        value = proofNote,
                        onValueChange = { proofNote = it.take(500) },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Catatan untuk admin (opsional)") },
                        minLines = 2,
                        maxLines = 4,
                        enabled = !sendingProof,
                    )
                    if (proofError.isNotBlank()) InlineError(proofError)
                }
            },
            confirmButton = {
                TextButton(
                    enabled = !sendingProof,
                    onClick = {
                        scope.launch {
                            sendingProof = true
                            proofError = ""
                            try {
                                onSubmitProof(taskForProof, proofUri, proofNote.trim())
                                clearProofSelection()
                                proofNote = ""
                            } catch (cancelled: CancellationException) {
                                throw cancelled
                            } catch (error: Exception) {
                                proofError = error.message ?: "Bukti gagal dikirim. Coba lagi."
                            } finally {
                                sendingProof = false
                            }
                        }
                    },
                ) {
                    Text(if (sendingProof) "Mengirim…" else "Kirim foto akhir", color = LoginAccent)
                }
            },
            dismissButton = {
                TextButton(onClick = { clearProofSelection() }, enabled = !sendingProof) {
                    Text("Batal", color = LoginMuted)
                }
            },
            containerColor = AppSurface,
        )
    }
    if (
        showLocationPhotoSourceDialog &&
            selectedLocationTask != null &&
            selectedLocationPhotoUri == null
    ) {
        val task = selectedLocationTask!!
        AlertDialog(
            onDismissRequest = ::clearLocationPhotoSelection,
            title = { Text("Foto di lokasi", color = LoginInk, fontWeight = FontWeight.Bold) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(7.dp)) {
                    Text(task.code, color = AppText, fontWeight = FontWeight.SemiBold)
                    Text(
                        task.location.ifBlank { "Lokasi belum dicatat" },
                        color = AppTextMuted,
                        fontSize = 12.sp,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Text(
                        "Kirim satu foto kondisi di titik laporan agar Admin bisa melihatnya. " +
                            "Bukti ini terpisah dari foto penerimaan TPA.",
                        color = AppText,
                        fontSize = 12.sp,
                        lineHeight = 17.sp,
                    )
                    if (locationPhotoError.isNotBlank()) InlineError(locationPhotoError)
                }
            },
            confirmButton = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    TextButton(
                        onClick = {
                            locationCameraFile?.delete()
                            locationCameraPath = null
                            selectedLocationCameraUri = null
                            showLocationPhotoSourceDialog = false
                            locationPhotoPicker.launch(arrayOf("image/*"))
                        },
                    ) {
                        Text("Galeri", color = LoginMuted)
                    }
                    TextButton(onClick = ::launchLocationPhotoCamera) {
                        Icon(
                            Icons.Filled.PhotoCamera,
                            contentDescription = null,
                            tint = LoginAccent,
                        )
                        Spacer(Modifier.width(5.dp))
                        Text("Kamera", color = LoginAccent, fontWeight = FontWeight.SemiBold)
                    }
                }
            },
            dismissButton = {
                TextButton(onClick = ::clearLocationPhotoSelection) {
                    Text("Batal", color = LoginMuted)
                }
            },
            containerColor = AppSurface,
        )
    }
    val taskForLocationEvidence = selectedLocationTask
    val locationPhotoUri = selectedLocationPhotoUri
    if (taskForLocationEvidence != null && locationPhotoUri != null) {
        AlertDialog(
            onDismissRequest = {
                if (sendingLocationTaskId == null) clearLocationPhotoSelection()
            },
            title = {
                Text("Kirim foto ke Admin?", color = LoginInk, fontWeight = FontWeight.Bold)
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(7.dp)) {
                    Text(
                        taskForLocationEvidence.code,
                        color = AppText,
                        fontWeight = FontWeight.SemiBold,
                    )
                    Text(
                        taskForLocationEvidence.location.ifBlank { "Lokasi belum dicatat" },
                        color = AppTextMuted,
                        fontSize = 12.sp,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Text(
                        "Foto akan tersimpan bersama waktu pengiriman dan tampil di halaman Bukti kerja Admin.",
                        color = AppText,
                        fontSize = 12.sp,
                        lineHeight = 17.sp,
                    )
                    if (locationPhotoError.isNotBlank()) InlineError(locationPhotoError)
                }
            },
            confirmButton = {
                TextButton(
                    enabled = sendingLocationTaskId == null,
                    onClick = {
                        scope.launch {
                            sendingLocationTaskId = taskForLocationEvidence.id
                            locationPhotoError = ""
                            try {
                                onSubmitLocationEvidence(taskForLocationEvidence, locationPhotoUri)
                                clearLocationPhotoSelection()
                            } catch (cancelled: CancellationException) {
                                throw cancelled
                            } catch (error: Exception) {
                                locationPhotoError =
                                    error.message ?: "Foto lokasi gagal dikirim. Coba lagi."
                            } finally {
                                sendingLocationTaskId = null
                            }
                        }
                    },
                ) {
                    Text(
                        if (sendingLocationTaskId != null) "Mengirim…" else "Kirim foto",
                        color = LoginAccent,
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = ::clearLocationPhotoSelection,
                    enabled = sendingLocationTaskId == null,
                ) {
                    Text("Batal", color = LoginMuted)
                }
            },
            containerColor = AppSurface,
        )
    }
    reportingDamageVehicle?.let { vehicle ->
        VehicleDamageDialog(
            vehicle = vehicle,
            onDismiss = { reportingDamageVehicle = null },
            onSubmit = { cause, details ->
                onReportVehicleDamage(vehicle, cause, details)
                reportingDamageVehicle = null
            },
        )
    }
    vehicleRepairAction?.let { (repair, action) ->
        VehicleRepairProcessDialog(
            repair = repair,
            action = action,
            onDismiss = { vehicleRepairAction = null },
            onSubmit = { note ->
                when (action) {
                    "mulai" -> onStartVehicleRepair(repair, note)
                    "progres" -> onUpdateVehicleRepairProgress(repair, note)
                    else -> onSubmitVehicleRepairForInspection(repair, note)
                }
                vehicleRepairAction = null
            },
        )
    }
    if (showVehicleForm) {
        VehicleFormDialog(
            vehicle = editingVehicle,
            onDismiss = {
                showVehicleForm = false
                editingVehicle = null
            },
            onSave = { plate, capacity, route ->
                val vehicle = editingVehicle
                if (vehicle == null) onCreateVehicle(plate, capacity, route)
                else
                    onUpdateVehicle(
                        vehicle.copy(plate = plate, capacityKg = capacity, route = route),
                    )
                showVehicleForm = false
                editingVehicle = null
            },
        )
    }
    if (showFacilityForm) {
        FacilityFormDialog(
            facility = editingFacility,
            onDismiss = {
                showFacilityForm = false
                editingFacility = null
            },
            onSave = { name, address, capacity ->
                val facility = editingFacility
                if (facility == null) onCreateFacility(name, address, capacity)
                else
                    onUpdateFacility(
                        facility.copy(name = name, address = address, capacityTon = capacity),
                    )
                showFacilityForm = false
                editingFacility = null
            },
        )
    }
}
