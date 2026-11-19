package com.uoi.pratikumwithbunafla.ui.admin

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.uoi.pratikumwithbunafla.data.model.*
import com.uoi.pratikumwithbunafla.ui.components.*
import com.uoi.pratikumwithbunafla.ui.petugas.*
import com.uoi.pratikumwithbunafla.ui.theme.AppBorder
import com.uoi.pratikumwithbunafla.ui.theme.AppCardSurface
import com.uoi.pratikumwithbunafla.ui.theme.AppSurface
import com.uoi.pratikumwithbunafla.ui.theme.AppSurfaceMuted
import com.uoi.pratikumwithbunafla.ui.theme.AppText
import com.uoi.pratikumwithbunafla.ui.theme.AppTextMuted
import com.uoi.pratikumwithbunafla.ui.theme.InterFont
import com.uoi.pratikumwithbunafla.ui.theme.LocalSmartWasteAppearance
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch

@Composable
fun AdminOperationsScreen(
    reports: List<WasteRecord>,
    workers: List<AdminWorker>,
    completionReviews: List<TaskCompletionReview>,
    vehicleRepairs: List<VehicleRepair>,
    capacityClearanceRequests: List<CapacityClearanceRequest>,
    errorMessage: String,
    account: UserAccount,
    onCreateWorker: suspend (String, String, String, String, String) -> Unit,
    onUpdateWorker: suspend (AdminWorker, Boolean) -> Unit,
    onDeleteWorker: suspend (AdminWorker, Int?) -> Unit,
    onArchiveWorker: suspend (AdminWorker) -> Unit,
    onPermanentlyDeleteWorker: suspend (AdminWorker) -> Unit,
    onUploadWorkerPhoto: suspend (AdminWorker, Uri) -> Unit,
    onOpenPhoto: (String) -> Unit,
    onReviewCompletion: suspend (TaskCompletionReview, String, String) -> Unit,
    onReviewVehicleRepair: suspend (VehicleRepair, String, String) -> Unit,
    onReviewCapacityClearance: suspend (CapacityClearanceRequest, String, String) -> Unit,
    onNavigate: (String) -> Unit,
    onLogout: () -> Unit,
    initialTab: String = "petugas",
    onTabSelected: (String) -> Unit,
    isInitialLoading: Boolean = false,
    isRefreshing: Boolean = false,
    onRefresh: () -> Unit = {},
) {
    var tab by
        rememberSaveable(account.role, initialTab) {
            mutableStateOf(
                when (initialTab) {
                    "bukti",
                    "catatan",
                    "catatan-bukti" -> "bukti"
                    "armada" -> "armada"
                    "pengosongan" -> "pengosongan"
                    else -> "petugas"
                },
            )
        }
    var adding by rememberSaveable { mutableStateOf(false) }
    var editingWorker by remember { mutableStateOf<AdminWorker?>(null) }
    var deletingWorker by remember { mutableStateOf<AdminWorker?>(null) }
    var archivingWorker by remember { mutableStateOf<AdminWorker?>(null) }
    var permanentlyDeletingWorker by remember { mutableStateOf<AdminWorker?>(null) }
    var revisionReview by remember { mutableStateOf<TaskCompletionReview?>(null) }
    var vehicleRepairReview by remember { mutableStateOf<Pair<VehicleRepair, String>?>(null) }
    var capacityClearanceReview by remember {
        mutableStateOf<Pair<CapacityClearanceRequest, String>?>(null)
    }
    var capacityReviewNote by rememberSaveable { mutableStateOf("") }
    var capacityReviewError by remember { mutableStateOf("") }
    var capacityReviewSending by remember { mutableStateOf(false) }
    var showingClearanceHistory by rememberSaveable { mutableStateOf(false) }
    var revisionNote by rememberSaveable { mutableStateOf("") }
    var revisionError by remember { mutableStateOf("") }
    var revisionSending by remember { mutableStateOf(false) }
    var processingCompletionId by remember { mutableStateOf<Int?>(null) }
    var showingRepairHistory by rememberSaveable(account.role) { mutableStateOf(false) }
    var proofSearch by rememberSaveable { mutableStateOf("") }
    var actionError by rememberSaveable { mutableStateOf("") }
    var workerSearch by rememberSaveable { mutableStateOf("") }
    var workerFilter by rememberSaveable { mutableStateOf("semua") }
    var updatingWorkerId by remember { mutableStateOf<Int?>(null) }
    var photoTarget by remember { mutableStateOf<AdminWorker?>(null) }
    var uploadingPhotoWorkerId by remember { mutableStateOf<Int?>(null) }
    val scope = rememberCoroutineScope()
    val workerPhotoPicker =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            val worker = photoTarget
            photoTarget = null
            if (uri != null && worker != null) {
                scope.launch {
                    uploadingPhotoWorkerId = worker.id
                    actionError = ""
                    try {
                        onUploadWorkerPhoto(worker, uri)
                    } catch (cancelled: CancellationException) {
                        throw cancelled
                    } catch (error: Exception) {
                        actionError = error.message ?: "Foto profil petugas gagal disimpan."
                    } finally {
                        uploadingPhotoWorkerId = null
                    }
                }
            }
        }
    val workerSearchQuery = workerSearch.trim().lowercase()
    val workerSearchSuggestions =
        remember(workers) {
            workers.flatMap { worker -> listOf(worker.name, worker.username, worker.zone) }
        }
    val workerFilters =
        listOf(
            "semua" to "Semua · ${workers.count { !it.isArchived }}",
            "aktif" to "Aktif · ${workers.count { !it.isArchived && it.active && it.hasAccount }}",
            "arsip" to "Arsip · ${workers.count { it.isArchived }}",
        )
    val activeWorkerFilter =
        workerFilters.firstOrNull { it.first == workerFilter }?.first ?: "semua"
    val visibleWorkers =
        workers
            .asSequence()
            .filter { worker ->
                workerSearchQuery.isBlank() ||
                    listOf(worker.name, worker.username, worker.zone).any {
                        it.lowercase().contains(workerSearchQuery)
                    }
            }
            .filter { worker ->
                when (activeWorkerFilter) {
                    "arsip" -> worker.isArchived
                    "aktif" -> !worker.isArchived && worker.active && worker.hasAccount
                    else -> !worker.isArchived
                }
            }
            .sortedWith(
                compareByDescending<AdminWorker> { it.pendingConfirmations }
                    .thenByDescending { it.activeTasks }
                    .thenBy { it.name.lowercase() },
            )
            .toList()
    AdminPage(
        "ops",
        account,
        onNavigate,
        onLogout,
        isInitialLoading = isInitialLoading,
        isRefreshing = isRefreshing,
        onRefresh = onRefresh,
    ) {
        val operationsTabs =
            listOf(
                "petugas" to "Petugas",
                "armada" to
                    "Perbaikan · ${vehicleRepairs.count { it.status !in setOf("selesai", "ditolak") }}",
                "pengosongan" to
                    "Pengosongan · ${capacityClearanceRequests.count { it.isPending }}",
                "bukti" to
                    "Bukti kerja · " +
                        "${completionReviews.size + reports.count { !it.locationEvidencePhotoUrl.isNullOrBlank() }}",
            )
        StaggeredPageEntry("admin-ops-$tab", 0) {
            AdminSectionHeading(
                when (tab) {
                    "bukti" -> "ADMIN • KINERJA"
                    "armada" -> "ADMIN • ARMADA"
                    "pengosongan" -> "ADMIN • PERSETUJUAN"
                    else -> "ADMIN • OPERASIONAL"
                },
                when (tab) {
                    "bukti" -> "Bukti kerja petugas"
                    "armada" -> "Perbaikan armada"
                    "pengosongan" -> "Pengosongan aset"
                    else -> "Kelola petugas"
                },
                when (tab) {
                    "bukti" ->
                        "Tinjau foto hasil kerja, minta perbaikan, atau setujui penyelesaian."
                    "armada" ->
                        "Setujui perbaikan dan periksa kendaraan sebelum kembali beroperasi."
                    "pengosongan" ->
                        "Petugas mengirim foto setelah aset dikosongkan. " +
                            "Tinjau bukti lalu setujui atau tolak permintaannya."
                    else -> "Atur akun petugas dan pantau pembagian tugas."
                },
            )
        }
        if (errorMessage.isNotBlank())
            StaggeredPageEntry("admin-ops-$tab", 1) { InlineError(errorMessage) }
        if (actionError.isNotBlank())
            StaggeredPageEntry("admin-ops-$tab", 2) { InlineError(actionError) }
        val operationsListState = rememberLazyListState()
        val operationsTabPositions = remember { mutableStateMapOf<String, Pair<Float, Float>>() }
        var operationsRowRootX by remember { mutableStateOf(0f) }
        val selectedOperationsIndex = operationsTabs.indexOfFirst { it.first == tab }
        LaunchedEffect(selectedOperationsIndex) {
            if (selectedOperationsIndex >= 0) {
                val selectedItem =
                    operationsListState.layoutInfo.visibleItemsInfo.firstOrNull {
                        it.index == selectedOperationsIndex
                    }
                val isFullyVisible =
                    selectedItem != null &&
                        selectedItem.offset >= operationsListState.layoutInfo.viewportStartOffset &&
                        selectedItem.offset + selectedItem.size <=
                            operationsListState.layoutInfo.viewportEndOffset
                if (!isFullyVisible) {
                    operationsListState.animateScrollToItem(selectedOperationsIndex)
                }
            }
        }
        val activeTabBounds = operationsTabPositions[tab]
        val activeTabAccent =
            when (tab) {
                "petugas" -> RoleGreen
                "armada" -> RoleOrange
                "pengosongan" -> RoleTeal
                else -> RolePurple
            }
        val indicatorColor by
            animateColorAsState(
                targetValue = activeTabAccent.copy(alpha = 0.38f),
                animationSpec = tween(360, easing = FastOutSlowInEasing),
                label = "operations-tab-indicator-color",
            )
        val indicatorX by
            animateFloatAsState(
                targetValue = activeTabBounds?.first ?: 0f,
                animationSpec = tween(360, easing = FastOutSlowInEasing),
                label = "operations-tab-indicator-x",
            )
        val indicatorWidth by
            animateFloatAsState(
                targetValue = activeTabBounds?.second ?: 0f,
                animationSpec = tween(360, easing = FastOutSlowInEasing),
                label = "operations-tab-indicator-width",
            )
        Box(
            modifier =
                Modifier.fillMaxWidth().onGloballyPositioned {
                    operationsRowRootX = it.positionInRoot().x
                },
        ) {
            LazyRow(
                state = operationsListState,
                modifier =
                    Modifier.fillMaxWidth().drawBehind {
                        if (indicatorWidth > 0f) {
                            drawRoundRect(
                                color = indicatorColor,
                                topLeft = Offset(indicatorX, 0f),
                                size = Size(indicatorWidth, size.height),
                                cornerRadius = CornerRadius(13.dp.toPx()),
                            )
                        }
                    },
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                contentPadding = androidx.compose.foundation.layout.PaddingValues(end = 12.dp),
            ) {
                itemsIndexed(operationsTabs, key = { _, item -> item.first }) { _, (key, label)
                    ->
                    val selected = tab == key
                    val tabAccent =
                        when (key) {
                            "petugas" -> RoleGreen
                            "armada" -> RoleOrange
                            "pengosongan" -> RoleTeal
                            else -> RolePurple
                        }
                    val tabContent by
                        animateColorAsState(
                            targetValue = if (selected) LoginAccent else LoginMuted,
                            animationSpec = tween(220, easing = FastOutSlowInEasing),
                            label = "operations-tab-$key-content",
                        )
                    val tabSurface by
                        animateColorAsState(
                            targetValue = tabAccent.copy(alpha = if (selected) 0f else 0.08f),
                            animationSpec = tween(240, easing = FastOutSlowInEasing),
                            label = "operations-tab-$key-surface",
                        )
                    TextButton(
                        onClick = {
                            tab = key
                            onTabSelected(key)
                        },
                        modifier =
                            Modifier.onGloballyPositioned { coordinates ->
                                val bounds =
                                    (coordinates.positionInRoot().x - operationsRowRootX) to
                                        coordinates.size.width.toFloat()
                                if (operationsTabPositions[key] != bounds) {
                                    operationsTabPositions[key] = bounds
                                }
                            },
                        colors =
                            ButtonDefaults.textButtonColors(
                                contentColor = tabContent,
                                containerColor = tabSurface,
                            ),
                        shape = RoundedCornerShape(13.dp),
                    ) {
                        Text(label, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
        when (tab) {
            "pengosongan" -> {
                val visibleRequests = capacityClearanceRequests.filter {
                    it.isPending != showingClearanceHistory
                }
                StaggeredPageEntry("admin-clearance-$showingClearanceHistory", 4) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                    ) {
                        listOf(
                                false to
                                    "Menunggu · ${capacityClearanceRequests.count { it.isPending }}",
                                true to
                                    "Riwayat · ${capacityClearanceRequests.count { !it.isPending }}",
                            )
                            .forEach { (history, label) ->
                                TextButton(
                                    onClick = { showingClearanceHistory = history },
                                    colors =
                                        ButtonDefaults.textButtonColors(
                                            contentColor =
                                                if (showingClearanceHistory == history) LoginAccent
                                                else LoginMuted,
                                            containerColor =
                                                if (showingClearanceHistory == history)
                                                    RoleAccentSurface
                                                else Color.Transparent,
                                        ),
                                    shape = RoundedCornerShape(13.dp),
                                ) {
                                    Text(label, fontSize = 10.sp, fontWeight = FontWeight.SemiBold)
                                }
                            }
                    }
                }
                if (visibleRequests.isEmpty()) {
                    StaggeredPageEntry("admin-clearance-$showingClearanceHistory", 5) {
                        EmptyAdminState(
                            if (showingClearanceHistory) "Belum ada keputusan pengosongan."
                            else "Belum ada permintaan pengosongan yang menunggu persetujuan.",
                        )
                    }
                }
                visibleRequests.forEachIndexed { index, request ->
                    StaggeredPageEntry("admin-clearance-$showingClearanceHistory", 6 + index) {
                        AppCard(
                            modifier = Modifier.fillMaxWidth(),
                            containerColor = AppSurface,
                            accentColor = if (request.isPending) RoleOrange else LoginAccent,
                        ) {
                            Column(
                                modifier = Modifier.fillMaxWidth().padding(13.dp),
                                verticalArrangement = Arrangement.spacedBy(6.dp),
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Column(
                                        Modifier.weight(1f),
                                        verticalArrangement = Arrangement.spacedBy(2.dp),
                                    ) {
                                        Text(
                                            "${request.targetLabel} · ${request.targetName}",
                                            color = AppText,
                                            fontSize = 13.sp,
                                            fontWeight = FontWeight.Bold,
                                        )
                                        Text(
                                            "${request.requesterName} · ${request.createdAt.toDisplayDateTime()}",
                                            color = AppTextMuted,
                                            fontSize = 10.sp,
                                        )
                                    }
                                    StatusPill(
                                        if (request.isPending) "menunggu" else request.status,
                                        if (request.isPending) RoleOrange else LoginAccent,
                                    )
                                }
                                Text(
                                    request.reason,
                                    color = AppText,
                                    fontSize = 11.sp,
                                    lineHeight = 16.sp,
                                )
                                TextButton(
                                    onClick = { onOpenPhoto(request.evidencePhotoUrl) },
                                    contentPadding =
                                        androidx.compose.foundation.layout.PaddingValues(
                                            horizontal = 0.dp,
                                            vertical = 0.dp,
                                        ),
                                ) {
                                    Text("Lihat bukti foto", color = LoginAccent, fontSize = 11.sp)
                                }
                                if (request.adminNote.isNotBlank()) {
                                    Text(
                                        "Keputusan Admin: ${request.adminNote}",
                                        color = AppTextMuted,
                                        fontSize = 10.sp,
                                    )
                                }
                                if (request.isPending) {
                                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                        TextButton(
                                            onClick = {
                                                capacityReviewNote = ""
                                                capacityReviewError = ""
                                                capacityClearanceReview = request to "tolak"
                                            },
                                        ) {
                                            Text(
                                                "Tolak",
                                                color = RoleRed,
                                                fontWeight = FontWeight.SemiBold,
                                            )
                                        }
                                        TextButton(
                                            onClick = {
                                                capacityReviewNote = ""
                                                capacityReviewError = ""
                                                capacityClearanceReview = request to "setujui"
                                            },
                                        ) {
                                            Text(
                                                "Setujui & kosongkan",
                                                color = LoginAccent,
                                                fontWeight = FontWeight.SemiBold,
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            "petugas" -> {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(14.dp),
                ) {
                    StaggeredPageEntry("admin-ops-$tab", 4) { SectionTitle("Data petugas") }
                    StaggeredPageEntry("admin-ops-$tab", 5) {
                        AppButton(
                            "＋ Tambah akun petugas",
                            { adding = true },
                            containerColor = LoginAccent,
                        )
                    }
                    StaggeredPageEntry("admin-ops-$tab", 6) {
                        SmartWasteSearchField(
                            value = workerSearch,
                            onValueChange = { workerSearch = it.take(80) },
                            placeholder = "Cari nama, username, atau zona",
                            suggestionSource = workerSearchSuggestions,
                            modifier = Modifier.padding(top = 6.dp),
                        )
                    }
                    StaggeredPageEntry("admin-ops-$tab", 7) {
                        Row(
                            modifier =
                                Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            workerFilters.forEach { (filter, label) ->
                                val selected = activeWorkerFilter == filter
                                TextButton(
                                    onClick = { workerFilter = filter },
                                    contentPadding =
                                        androidx.compose.foundation.layout.PaddingValues(
                                            horizontal = 12.dp,
                                            vertical = 6.dp,
                                        ),
                                    colors =
                                        ButtonDefaults.textButtonColors(
                                            contentColor =
                                                if (selected) LoginAccent else AppTextMuted,
                                            containerColor =
                                                if (selected) RoleAccentSurface
                                                else AppSurfaceMuted,
                                        ),
                                    shape = RoundedCornerShape(12.dp),
                                ) {
                                    Text(label, fontSize = 10.sp, fontWeight = FontWeight.SemiBold)
                                }
                            }
                        }
                    }
                    if (workers.isEmpty() || visibleWorkers.isEmpty()) {
                        StaggeredPageEntry("admin-ops-$tab", 8) {
                            EmptyAdminState(
                                when {
                                    workers.isEmpty() -> "Belum ada data petugas."
                                    workerSearchQuery.isNotBlank() -> "Petugas tidak ditemukan."
                                    activeWorkerFilter == "arsip" -> "Belum ada petugas di arsip."
                                    else -> "Tidak ada petugas pada filter ini."
                                },
                            )
                        }
                    }
                    visibleWorkers.forEachIndexed { index, item ->
                        val isDarkMode = LocalSmartWasteAppearance.current.darkMode
                        val workerCardColor = AppCardSurface
                        val workerTextColor = if (isDarkMode) AppText else Color(0xFF202820)
                        val workerDetailColor = AppTextMuted
                        val workerActionColor = if (isDarkMode) LoginAccent else Color(0xFF176B49)
                        val workerWarningColor = if (isDarkMode) RoleRed else Color(0xFF963E45)
                        val accountState =
                            when {
                                item.isArchived -> "Diarsipkan"
                                !item.hasAccount -> "Tanpa akun"
                                item.active -> "Aktif"
                                else -> "Nonaktif"
                            }
                        val accountStateColor =
                            when {
                                item.isArchived -> workerDetailColor
                                !item.hasAccount -> workerWarningColor
                                item.active -> if (isDarkMode) RoleGreen else Color(0xFF236B49)
                                else -> workerWarningColor
                            }
                        StaggeredPageEntry("admin-ops-$tab", 9 + index) {
                            AppCard(
                                modifier = Modifier.fillMaxWidth(),
                                containerColor = workerCardColor,
                                borderColor = AppBorder,
                                accentColor =
                                    if (item.active && item.hasAccount) LoginAccent else RoleRed,
                            ) {
                                Column(
                                    modifier =
                                        Modifier.fillMaxWidth()
                                            .padding(horizontal = 18.dp, vertical = 18.dp),
                                    verticalArrangement = Arrangement.spacedBy(14.dp),
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(14.dp),
                                    ) {
                                        val canEditWorkerPhoto =
                                            account.role == UserRole.ADMIN && item.hasAccount
                                        val openWorkerPhotoPicker: () -> Unit = {
                                            photoTarget = item
                                            workerPhotoPicker.launch("image/*")
                                        }
                                        Box(
                                            modifier =
                                                Modifier.size(56.dp)
                                                    .clickable(
                                                        enabled =
                                                            canEditWorkerPhoto &&
                                                                uploadingPhotoWorkerId == null,
                                                        role = Role.Button,
                                                        onClick = openWorkerPhotoPicker,
                                                    ),
                                        ) {
                                            UserAvatar(
                                                item.name.displayPersonName(),
                                                modifier = Modifier.fillMaxSize(),
                                                photoUrl = item.profilePhotoUrl,
                                            )
                                            if (canEditWorkerPhoto) {
                                                IconButton(
                                                    onClick = openWorkerPhotoPicker,
                                                    enabled = uploadingPhotoWorkerId == null,
                                                    modifier =
                                                        Modifier.align(Alignment.BottomEnd)
                                                            .offset(x = 6.dp, y = 6.dp)
                                                            .size(22.dp),
                                                ) {
                                                    if (uploadingPhotoWorkerId == item.id) {
                                                        CircularProgressIndicator(
                                                            modifier = Modifier.size(15.dp),
                                                            strokeWidth = 2.dp,
                                                            color = LoginAccent,
                                                        )
                                                    } else {
                                                        Icon(
                                                            imageVector = Icons.Filled.PhotoCamera,
                                                            contentDescription =
                                                                "Ganti foto profil ${item.name}",
                                                            tint =
                                                                if (isDarkMode) Color.White
                                                                else LoginAccent,
                                                            modifier = Modifier.size(17.dp),
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                        Column(
                                            modifier = Modifier.weight(1f),
                                            verticalArrangement = Arrangement.spacedBy(5.dp),
                                        ) {
                                            Text(
                                                item.name.displayPersonName(),
                                                color = AppText,
                                                fontSize = 16.sp,
                                                lineHeight = 22.sp,
                                                fontWeight = FontWeight.Bold,
                                            )
                                            Text(
                                                when {
                                                    item.isArchived -> "Profil disimpan di arsip"
                                                    item.hasAccount -> "@${item.username}"
                                                    else -> "Akun Android sudah dihapus"
                                                },
                                                color =
                                                    if (item.hasAccount || item.isArchived)
                                                        workerDetailColor
                                                    else workerWarningColor,
                                                fontSize = 13.sp,
                                                lineHeight = 18.sp,
                                                maxLines = 1,
                                                overflow = TextOverflow.Ellipsis,
                                            )
                                        }
                                        Box(
                                            modifier =
                                                Modifier.clip(RoundedCornerShape(50.dp))
                                                    .background(
                                                        accountStateColor.copy(alpha = 0.12f),
                                                    )
                                                    .padding(horizontal = 10.dp, vertical = 6.dp),
                                        ) {
                                            Text(
                                                accountState,
                                                color = accountStateColor,
                                                fontSize = 11.sp,
                                                lineHeight = 14.sp,
                                                fontWeight = FontWeight.SemiBold,
                                            )
                                        }
                                    }

                                    Text(
                                        "Zona · ${item.zone.ifBlank { "Belum ditentukan" }}",
                                        color = workerDetailColor,
                                        fontSize = 12.sp,
                                        lineHeight = 17.sp,
                                        maxLines = 2,
                                        overflow = TextOverflow.Ellipsis,
                                    )

                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.spacedBy(20.dp),
                                    ) {
                                        Column(
                                            modifier = Modifier.weight(1f),
                                            verticalArrangement = Arrangement.spacedBy(6.dp),
                                        ) {
                                            Text(
                                                "Disetujui",
                                                color = workerDetailColor,
                                                fontSize = 12.sp,
                                            )
                                            Text(
                                                "${item.completedTasks} tugas · ${item.completedWeightKg.pretty()} kg",
                                                color = workerTextColor,
                                                fontSize = 14.sp,
                                                lineHeight = 19.sp,
                                                fontWeight = FontWeight.SemiBold,
                                                maxLines = 2,
                                                overflow = TextOverflow.Ellipsis,
                                            )
                                        }
                                        Column(
                                            modifier = Modifier.weight(1f),
                                            verticalArrangement = Arrangement.spacedBy(6.dp),
                                        ) {
                                            Text(
                                                "Aktif · bukti menunggu",
                                                color = workerDetailColor,
                                                fontSize = 12.sp,
                                            )
                                            Text(
                                                "${item.activeTasks} tugas · ${item.pendingConfirmations} bukti",
                                                color = workerTextColor,
                                                fontSize = 14.sp,
                                                lineHeight = 19.sp,
                                                fontWeight = FontWeight.SemiBold,
                                                maxLines = 2,
                                                overflow = TextOverflow.Ellipsis,
                                            )
                                        }
                                    }

                                    AppCardDivider()

                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically,
                                    ) {
                                        if (item.isArchived) {
                                            Text(
                                                "Profil di arsip",
                                                color = workerDetailColor,
                                                fontSize = 12.sp,
                                            )
                                        } else {
                                            TextButton(onClick = { editingWorker = item }) {
                                                Text(
                                                    "Ubah zona",
                                                    color = workerActionColor,
                                                    fontSize = 13.sp,
                                                )
                                            }
                                        }
                                        Spacer(modifier = Modifier.weight(1f))
                                        when {
                                            item.isArchived -> {
                                                TextButton(
                                                    onClick = { permanentlyDeletingWorker = item },
                                                ) {
                                                    Text(
                                                        "Hapus permanen",
                                                        color = RoleRed,
                                                        fontSize = 13.sp,
                                                    )
                                                }
                                            }
                                            item.hasAccount -> {
                                                TextButton(
                                                    onClick = {
                                                        scope.launch {
                                                            if (updatingWorkerId != null)
                                                                return@launch
                                                            updatingWorkerId = item.id
                                                            actionError = ""
                                                            try {
                                                                onUpdateWorker(item, !item.active)
                                                            } catch (
                                                                cancelled: CancellationException,) {
                                                                throw cancelled
                                                            } catch (error: Exception) {
                                                                actionError =
                                                                    error.message
                                                                        ?: "Status petugas gagal diperbarui."
                                                            } finally {
                                                                updatingWorkerId = null
                                                            }
                                                        }
                                                    },
                                                    enabled = updatingWorkerId == null,
                                                ) {
                                                    Text(
                                                        when {
                                                            updatingWorkerId == item.id ->
                                                                "Menyimpan…"
                                                            item.active -> "Nonaktifkan"
                                                            else -> "Aktifkan"
                                                        },
                                                        color =
                                                            if (item.active) workerWarningColor
                                                            else workerActionColor,
                                                        fontSize = 13.sp,
                                                    )
                                                }
                                                IconButton(
                                                    onClick = { deletingWorker = item },
                                                    modifier = Modifier.size(48.dp),
                                                ) {
                                                    Icon(
                                                        Icons.Filled.Delete,
                                                        contentDescription = "Hapus akun petugas",
                                                        tint = RoleRed,
                                                        modifier = Modifier.size(18.dp),
                                                    )
                                                }
                                            }
                                            else -> {
                                                TextButton(onClick = { archivingWorker = item }) {
                                                    Text(
                                                        "Arsipkan",
                                                        color = workerActionColor,
                                                        fontSize = 13.sp,
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            "armada" -> {
                val waitingApproval = vehicleRepairs.count { it.status == "menunggu_persetujuan" }
                val waitingInspection = vehicleRepairs.count { it.status == "menunggu_pemeriksaan" }
                val activeRepairs = vehicleRepairs.filter {
                    it.status !in setOf("selesai", "ditolak")
                }
                val repairHistory = vehicleRepairs.filter {
                    it.status in setOf("selesai", "ditolak")
                }
                val visibleRepairs = if (showingRepairHistory) repairHistory else activeRepairs
                StaggeredPageEntry("admin-ops-$tab", 4) {
                    SectionTitle(
                        "Perlu perhatian",
                        "$waitingApproval menunggu keputusan · $waitingInspection menunggu pemeriksaan hasil",
                    )
                }
                StaggeredPageEntry("admin-ops-$tab", 5) {
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        listOf(
                                false to "Berjalan · ${activeRepairs.size}",
                                true to "Riwayat · ${repairHistory.size}",
                            )
                            .forEach { (history, label) ->
                                TextButton(
                                    onClick = { showingRepairHistory = history },
                                    colors =
                                        ButtonDefaults.textButtonColors(
                                            contentColor =
                                                if (showingRepairHistory == history) LoginAccent
                                                else LoginMuted,
                                            containerColor =
                                                if (showingRepairHistory == history)
                                                    RoleAccentSurface
                                                else AppSurface,
                                        ),
                                    shape = RoundedCornerShape(13.dp),
                                ) {
                                    Text(label, fontSize = 10.sp, fontWeight = FontWeight.SemiBold)
                                }
                            }
                    }
                }
                if (vehicleRepairs.isEmpty() && isInitialLoading) {
                    StaggeredPageEntry("admin-ops-$tab", 6) {
                        PetugasLoadingState(
                            "Memuat perbaikan",
                            "Mengambil laporan kerusakan armada.",
                        )
                    }
                } else if (vehicleRepairs.isEmpty() && errorMessage.contains("Perbaikan armada:")) {
                    StaggeredPageEntry("admin-ops-$tab", 6) {
                        PetugasLoadFailure("Data perbaikan belum dapat dimuat.", onRefresh)
                    }
                } else if (vehicleRepairs.isEmpty()) {
                    StaggeredPageEntry("admin-ops-$tab", 6) {
                        EmptyAdminState("Belum ada laporan kerusakan armada.")
                    }
                } else if (visibleRepairs.isEmpty()) {
                    StaggeredPageEntry("admin-ops-$tab", 6) {
                        EmptyAdminState(
                            if (showingRepairHistory) "Belum ada riwayat perbaikan."
                            else "Tidak ada proses yang perlu ditinjau.",
                        )
                    }
                } else {
                    visibleRepairs.forEachIndexed { index, repair ->
                        StaggeredPageEntry("admin-ops-$tab", 6 + index) {
                            VehicleRepairCard(repair = repair) {
                                when (repair.status) {
                                    "menunggu_persetujuan" ->
                                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                            TextButton(
                                                onClick = {
                                                    vehicleRepairReview = repair to "tolak"
                                                },
                                            ) {
                                                Text(
                                                    "Tolak",
                                                    color = RoleRed,
                                                    fontWeight = FontWeight.SemiBold,
                                                )
                                            }
                                            TextButton(
                                                onClick = {
                                                    vehicleRepairReview = repair to "setujui"
                                                },
                                            ) {
                                                Text(
                                                    "Setujui perbaikan",
                                                    color = LoginAccent,
                                                    fontWeight = FontWeight.SemiBold,
                                                )
                                            }
                                        }
                                    "menunggu_pemeriksaan" ->
                                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                            TextButton(
                                                onClick = {
                                                    vehicleRepairReview = repair to "kembalikan"
                                                },
                                            ) {
                                                Text(
                                                    "Minta perbaikan lagi",
                                                    color = RoleOrange,
                                                    fontWeight = FontWeight.SemiBold,
                                                )
                                            }
                                            TextButton(
                                                onClick = {
                                                    vehicleRepairReview = repair to "selesai"
                                                },
                                            ) {
                                                Text(
                                                    "Setujui hasil",
                                                    color = LoginAccent,
                                                    fontWeight = FontWeight.SemiBold,
                                                )
                                            }
                                        }
                                }
                            }
                        }
                    }
                }
            }

            "bukti" -> {
                StaggeredPageEntry("admin-ops-$tab", 4) {
                    SmartWasteSearchField(
                        value = proofSearch,
                        onValueChange = { proofSearch = it.take(120) },
                        placeholder = "Kode laporan, petugas, atau lokasi",
                        suggestionSource =
                            reports
                                .filter { !it.locationEvidencePhotoUrl.isNullOrBlank() }
                                .flatMap { listOf(it.code, it.workerName, it.location) },
                    )
                }
                val query = proofSearch.trim().lowercase()
                val visibleLocationEvidence =
                    reports
                        .filter { !it.locationEvidencePhotoUrl.isNullOrBlank() }
                        .filter { report ->
                            query.isBlank() ||
                                listOf(report.code, report.workerName, report.location).any {
                                    it.lowercase().contains(query)
                                }
                        }
                        .sortedByDescending { it.locationEvidenceSubmittedAt }
                StaggeredPageEntry("admin-ops-$tab", 5) {
                    SectionTitle("Foto di lokasi", "Foto bukti dari petugas di lokasi laporan.")
                }
                if (visibleLocationEvidence.isEmpty()) {
                    if (query.isNotBlank()) {
                        StaggeredPageEntry("admin-ops-$tab", 6) {
                            Text(
                                "Foto lokasi yang cocok tidak ditemukan.",
                                color = AppTextMuted,
                                fontSize = 12.sp,
                                fontFamily = InterFont,
                            )
                        }
                    }
                } else {
                    StaggeredPageEntry("admin-ops-$tab", 6) {
                        AppCard(
                            modifier = Modifier.fillMaxWidth(),
                            containerColor = AppSurface,
                        ) {
                            Column(
                                modifier =
                                    Modifier.fillMaxWidth()
                                        .padding(horizontal = 14.dp, vertical = 5.dp),
                                verticalArrangement = Arrangement.spacedBy(3.dp),
                            ) {
                                visibleLocationEvidence.forEachIndexed { index, report ->
                                    StaggeredPageEntry("admin-location-evidence", index) {
                                        Row(
                                            modifier =
                                                Modifier.fillMaxWidth().padding(vertical = 9.dp),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                                        ) {
                                            Column(
                                                modifier = Modifier.weight(1f),
                                                verticalArrangement = Arrangement.spacedBy(3.dp),
                                            ) {
                                                Text(
                                                    report.code,
                                                    color = AppText,
                                                    fontSize = 13.sp,
                                                    fontWeight = FontWeight.SemiBold,
                                                    fontFamily = InterFont,
                                                )
                                                Text(
                                                    "${report.workerName.ifBlank { "Petugas" }} · ${report.location}",
                                                    color = AppTextMuted,
                                                    fontSize = 12.sp,
                                                    maxLines = 1,
                                                    overflow = TextOverflow.Ellipsis,
                                                    fontFamily = InterFont,
                                                )
                                                report.locationEvidenceSubmittedAt
                                                    .takeIf { it.isNotBlank() }
                                                    ?.let {
                                                        Text(
                                                            "Dikirim ${it.toDisplayDateTime()}",
                                                            color = AppTextMuted,
                                                            fontSize = 11.sp,
                                                            fontFamily = InterFont,
                                                        )
                                                    }
                                            }
                                            TextButton(
                                                onClick = {
                                                    report.locationEvidencePhotoUrl?.let(
                                                        onOpenPhoto,
                                                    )
                                                },
                                                enabled =
                                                    !report.locationEvidencePhotoUrl
                                                        .isNullOrBlank(),
                                            ) {
                                                Text(
                                                    "Lihat foto",
                                                    color = LoginAccent,
                                                    fontSize = 12.sp,
                                                    fontFamily = InterFont,
                                                )
                                            }
                                        }
                                    }
                                    if (index < visibleLocationEvidence.lastIndex)
                                        Spacer(Modifier.height(3.dp))
                                }
                            }
                        }
                    }
                }

                val visibleReviews = completionReviews.filter { review ->
                    query.isBlank() ||
                        listOf(
                                review.report.code,
                                review.report.type,
                                review.report.location,
                                review.report.description,
                                review.workerName,
                                review.workerNote,
                            )
                            .any { it.lowercase().contains(query) }
                }
                visibleReviews.forEachIndexed { index, review ->
                    val report = review.report
                    StaggeredPageEntry("admin-ops-$tab", 7 + index) {
                        AppCard(
                            modifier = Modifier.fillMaxWidth(),
                            containerColor = AppSurface,
                            accentColor = RoleOrange,
                        ) {
                            Column(
                                modifier = Modifier.fillMaxWidth().padding(13.dp),
                                verticalArrangement = Arrangement.spacedBy(7.dp),
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Column(Modifier.weight(1f)) {
                                        Text(
                                            report.code,
                                            color = AppText,
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold,
                                        )
                                        Text(
                                            "${report.type} · ${report.weightKg.pretty()} kg",
                                            color = AppTextMuted,
                                            fontSize = 10.sp,
                                        )
                                    }
                                    StatusPill("Menunggu tinjauan", RoleOrange)
                                }
                                Text(
                                    "${review.workerName.displayPersonName()} · ${report.location}",
                                    color = AppTextMuted,
                                    fontSize = 10.sp,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                )
                                if (review.submittedAt.isNotBlank()) {
                                    Text(
                                        "Dikirim ${review.submittedAt}",
                                        color = AppTextMuted,
                                        fontSize = 9.sp,
                                    )
                                }
                                if (review.workerNote.isNotBlank()) {
                                    Text(
                                        review.workerNote,
                                        color = AppText,
                                        fontSize = 11.sp,
                                        maxLines = 3,
                                        overflow = TextOverflow.Ellipsis,
                                    )
                                }
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    if (!report.photoUrl.isNullOrBlank()) {
                                        TextButton(
                                            modifier = Modifier.weight(1f),
                                            onClick = { onOpenPhoto(report.photoUrl) },
                                        ) {
                                            Text(
                                                "Sebelum · warga",
                                                color = LoginAccent,
                                                fontSize = 10.sp,
                                                fontFamily = InterFont,
                                            )
                                        }
                                    } else {
                                        Text(
                                            "Foto awal warga belum tersedia",
                                            modifier = Modifier.weight(1f),
                                            color = AppTextMuted,
                                            fontSize = 10.sp,
                                            fontFamily = InterFont,
                                        )
                                    }
                                    TextButton(
                                        modifier = Modifier.weight(1f),
                                        enabled = review.proofPhotoUrl.isNotBlank(),
                                        onClick = { onOpenPhoto(review.proofPhotoUrl) },
                                    ) {
                                        Text(
                                            "Sesudah · petugas",
                                            color = LoginAccent,
                                            fontSize = 10.sp,
                                            fontFamily = InterFont,
                                        )
                                    }
                                }
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                                ) {
                                    TextButton(
                                        modifier = Modifier.weight(1f),
                                        enabled = processingCompletionId == null,
                                        onClick = {
                                            revisionNote = ""
                                            revisionError = ""
                                            revisionReview = review
                                        },
                                    ) {
                                        Text(
                                            "Minta perbaikan",
                                            color = RoleOrange,
                                            fontSize = 10.sp,
                                        )
                                    }
                                    TextButton(
                                        modifier = Modifier.weight(1f),
                                        enabled =
                                            processingCompletionId == null &&
                                                review.proofPhotoUrl.isNotBlank(),
                                        onClick = {
                                            scope.launch {
                                                if (processingCompletionId != null) return@launch
                                                processingCompletionId = review.id
                                                actionError = ""
                                                try {
                                                    onReviewCompletion(review, "approve", "")
                                                } catch (cancelled: CancellationException) {
                                                    throw cancelled
                                                } catch (error: Exception) {
                                                    actionError =
                                                        error.message
                                                            ?: "Pemeriksaan bukti gagal disimpan."
                                                } finally {
                                                    processingCompletionId = null
                                                }
                                            }
                                        },
                                    ) {
                                        Text(
                                            if (processingCompletionId == review.id) "Menyetujui…"
                                            else "Setujui",
                                            color = RoleGreen,
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Bold,
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    if (adding && tab == "petugas") {
        StaffAccountFormDialog(
            onDismiss = { adding = false },
            onSave = { name, username, email, password, zone ->
                onCreateWorker(name, username, email, password, zone)
                adding = false
            },
        )
    }
    editingWorker?.let { item ->
        SingleFieldDialog(
            "Ubah zona tugas",
            item.zone,
            "Zona tugas",
            { editingWorker = null },
        ) { zone ->
            onUpdateWorker(item.copy(zone = zone), item.active)
            editingWorker = null
        }
    }
    deletingWorker?.let { item ->
        DeleteWorkerAccountDialog(
            worker = item,
            replacementWorkers = workers.filter { it.id != item.id && it.active && it.hasAccount },
            onDismiss = { deletingWorker = null },
            onDelete = onDeleteWorker,
        )
    }
    archivingWorker?.let { item ->
        ConfirmWorkerActionDialog(
            worker = item,
            title = "Arsipkan petugas?",
            detail = "Profil dipindahkan ke Arsip. Data laporan dan riwayat kerja tetap tersimpan.",
            confirmLabel = "Arsipkan",
            confirmColor = LoginAccent,
            onDismiss = { archivingWorker = null },
            onConfirm = onArchiveWorker,
        )
    }
    permanentlyDeletingWorker?.let { item ->
        ConfirmWorkerActionDialog(
            worker = item,
            title = "Hapus permanen?",
            detail =
                "Profil dihapus permanen. Laporan dan bukti kerja tetap disimpan dengan nama petugas.",
            confirmLabel = "Hapus permanen",
            confirmColor = RoleRed,
            onDismiss = { permanentlyDeletingWorker = null },
            onConfirm = onPermanentlyDeleteWorker,
        )
    }
    revisionReview?.let { review ->
        AlertDialog(
            onDismissRequest = { if (!revisionSending) revisionReview = null },
            title = {
                Text("Minta perbaikan bukti", color = LoginInk, fontWeight = FontWeight.Bold)
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        "Tugas ${review.report.code} · ${review.workerName.displayPersonName()}",
                        color = AppTextMuted,
                        fontSize = 11.sp,
                    )
                    if (revisionError.isNotBlank()) InlineError(revisionError)
                    OutlinedTextField(
                        value = revisionNote,
                        onValueChange = { revisionNote = it.take(500) },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Catatan perbaikan") },
                        placeholder = { Text("Jelaskan bagian bukti yang perlu diperbaiki") },
                        minLines = 3,
                        maxLines = 5,
                        enabled = !revisionSending,
                    )
                }
            },
            confirmButton = {
                TextButton(
                    enabled = revisionNote.isNotBlank() && !revisionSending,
                    onClick = {
                        scope.launch {
                            revisionSending = true
                            processingCompletionId = review.id
                            revisionError = ""
                            try {
                                onReviewCompletion(review, "revision", revisionNote.trim())
                                revisionReview = null
                                revisionNote = ""
                            } catch (cancelled: CancellationException) {
                                throw cancelled
                            } catch (error: Exception) {
                                revisionError = error.message ?: "Catatan perbaikan gagal dikirim."
                            } finally {
                                processingCompletionId = null
                                revisionSending = false
                            }
                        }
                    },
                ) {
                    Text(if (revisionSending) "Mengirim…" else "Kirim catatan", color = LoginAccent)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { revisionReview = null },
                    enabled = !revisionSending,
                ) {
                    Text("Batal", color = LoginMuted)
                }
            },
            containerColor = AppSurface,
        )
    }
    vehicleRepairReview?.let { (repair, action) ->
        VehicleRepairReviewDialog(
            repair = repair,
            action = action,
            onDismiss = { vehicleRepairReview = null },
            onSubmit = { note ->
                onReviewVehicleRepair(repair, action, note)
                vehicleRepairReview = null
            },
        )
    }
    capacityClearanceReview?.let { (request, action) ->
        AlertDialog(
            onDismissRequest = { if (!capacityReviewSending) capacityClearanceReview = null },
            title = {
                Text(
                    if (action == "setujui") "Setujui pengosongan?" else "Tolak permintaan?",
                    color = LoginInk,
                    fontWeight = FontWeight.Bold,
                )
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        "${request.targetLabel} · ${request.targetName}",
                        color = AppText,
                        fontWeight = FontWeight.SemiBold,
                    )
                    Text(
                        if (action == "setujui") {
                            "Setelah disetujui, kapasitas aset direset ke kosong. " +
                                "Server akan menolak persetujuan jika masih ada tugas pengiriman aktif."
                        } else {
                            "Permintaan ditolak dan angka kapasitas tetap seperti semula."
                        },
                        color = AppTextMuted,
                        fontSize = 12.sp,
                        lineHeight = 17.sp,
                    )
                    if (action == "tolak") {
                        OutlinedTextField(
                            value = capacityReviewNote,
                            onValueChange = { capacityReviewNote = it.take(500) },
                            modifier = Modifier.fillMaxWidth(),
                            label = { Text("Alasan penolakan") },
                            minLines = 2,
                            maxLines = 4,
                            enabled = !capacityReviewSending,
                        )
                    }
                    if (capacityReviewError.isNotBlank()) InlineError(capacityReviewError)
                }
            },
            confirmButton = {
                TextButton(
                    enabled =
                        !capacityReviewSending &&
                            (action != "tolak" || capacityReviewNote.trim().length >= 5),
                    onClick = {
                        scope.launch {
                            capacityReviewSending = true
                            capacityReviewError = ""
                            actionError = ""
                            try {
                                onReviewCapacityClearance(
                                    request,
                                    action,
                                    capacityReviewNote.trim(),
                                )
                                capacityClearanceReview = null
                                capacityReviewNote = ""
                            } catch (cancelled: CancellationException) {
                                throw cancelled
                            } catch (error: Exception) {
                                capacityReviewError =
                                    error.message ?: "Keputusan pengosongan gagal disimpan."
                            } finally {
                                capacityReviewSending = false
                            }
                        }
                    },
                ) {
                    Text(
                        if (capacityReviewSending) "Menyimpan…"
                        else if (action == "setujui") "Setujui & kosongkan" else "Tolak",
                        color = if (action == "setujui") LoginAccent else RoleRed,
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { capacityClearanceReview = null },
                    enabled = !capacityReviewSending,
                ) {
                    Text("Kembali", color = LoginMuted)
                }
            },
            containerColor = AppSurface,
        )
    }
}
