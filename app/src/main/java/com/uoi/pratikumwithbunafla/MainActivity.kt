package com.uoi.pratikumwithbunafla

import android.graphics.Color as AndroidColor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.core.net.toUri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.uoi.pratikumwithbunafla.data.model.*
import com.uoi.pratikumwithbunafla.data.remote.LanAppUpdate
import com.uoi.pratikumwithbunafla.data.remote.LanAppUpdater
import com.uoi.pratikumwithbunafla.data.remote.SmartWasteApi
import com.uoi.pratikumwithbunafla.domain.WasteFlow
import com.uoi.pratikumwithbunafla.ui.admin.*
import com.uoi.pratikumwithbunafla.ui.auth.LoginScreen
import com.uoi.pratikumwithbunafla.ui.components.SmartWasteSplash
import com.uoi.pratikumwithbunafla.ui.theme.SmartWasteTheme
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.io.File
import java.util.Calendar
import java.util.Date
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.statusBarColor = AndroidColor.TRANSPARENT
        window.navigationBarColor = AndroidColor.TRANSPARENT
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            window.isNavigationBarContrastEnforced = false
        }
        WindowCompat.getInsetsController(window, window.decorView).apply {
            isAppearanceLightStatusBars = true
            isAppearanceLightNavigationBars = true
        }

        setContent {
            val appearancePreferences = remember {
                getSharedPreferences("smart_waste_appearance", MODE_PRIVATE)
            }
            var isDarkMode by rememberSaveable {
                mutableStateOf(appearancePreferences.getBoolean("dark_mode", false))
            }
            var activeAccount by remember { mutableStateOf<UserAccount?>(null) }
            SideEffect {
                WindowCompat.getInsetsController(window, window.decorView).apply {
                    isAppearanceLightStatusBars = !isDarkMode
                    isAppearanceLightNavigationBars = activeAccount == null && !isDarkMode
                }
            }
            SmartWasteTheme(
                darkTheme = isDarkMode,
                onToggleTheme = {
                    isDarkMode = !isDarkMode
                    appearancePreferences.edit().putBoolean("dark_mode", isDarkMode).apply()
                }
            ) {
                val navController = rememberNavController()
                val scope = rememberCoroutineScope()
                var showSplash by rememberSaveable { mutableStateOf(true) }
                var summary by remember { mutableStateOf(AdminDashboardSummary()) }
                var reports by remember { mutableStateOf(emptyList<WasteRecord>()) }
                var archivedReports by remember { mutableStateOf(emptyList<ArchivedWasteRecord>()) }
                var incomingReports by remember { mutableStateOf(emptyList<WasteRecord>()) }
                var tasks by remember { mutableStateOf(emptyList<WasteRecord>()) }
                var users by remember { mutableStateOf(emptyList<AdminUser>()) }
                var workers by remember { mutableStateOf(emptyList<AdminWorker>()) }
                var completionReviews by remember { mutableStateOf(emptyList<TaskCompletionReview>()) }
                var adminNotes by remember { mutableStateOf(emptyList<AdminNoteRecord>()) }
                var archivedAdminNotes by remember { mutableStateOf(emptyList<ArchivedAdminNote>()) }
                var vehicles by remember { mutableStateOf(emptyList<WasteVehicle>()) }
                var facilities by remember { mutableStateOf(emptyList<WasteFacility>()) }
                var dailyReports by remember { mutableStateOf(emptyList<DailyReport>()) }
                var dailyRangeDays by rememberSaveable { mutableIntStateOf(30) }
                var statistics by remember { mutableStateOf(AdminStatistics()) }
                var errorMessage by remember { mutableStateOf("") }
                var selectedStatus by remember { mutableStateOf<String?>(null) }
                var selectedReportView by rememberSaveable { mutableStateOf("verify") }
                var selectedOpsTab by rememberSaveable { mutableStateOf("petugas") }
                var availableUpdate by remember { mutableStateOf<LanAppUpdate?>(null) }
                var dismissedUpdateCode by rememberSaveable { mutableIntStateOf(0) }
                var downloadingUpdate by remember { mutableStateOf(false) }
                var downloadedUpdate by remember { mutableStateOf<File?>(null) }
                var updateMessage by remember { mutableStateOf("") }

                suspend fun refreshWorkspace() {
                    val account = activeAccount ?: return
                    val loadErrors = mutableListOf<String>()
                    if (account.role == UserRole.ADMIN) {
                        val calendar = Calendar.getInstance()
                            .apply { add(Calendar.DAY_OF_YEAR, -(dailyRangeDays - 1)) }
                        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.US)
                        val from = formatter.format(calendar.time)
                        val to = formatter.format(Date())
                        summary = loadOrKeep(
                            "Ringkasan",
                            summary,
                            loadErrors
                        ) { SmartWasteApi.dashboard() }
                        reports =
                            loadOrKeep("Laporan", reports, loadErrors) { SmartWasteApi.reports() }
                        archivedReports = loadOrKeep(
                            "Arsip laporan",
                            archivedReports,
                            loadErrors
                        ) { SmartWasteApi.archivedReports() }
                        statistics = loadOrKeep(
                            "Statistik",
                            statistics,
                            loadErrors
                        ) { SmartWasteApi.statistics() }
                        workers = loadOrKeep(
                            "Data petugas",
                            workers,
                            loadErrors
                        ) { SmartWasteApi.workers() }
                        completionReviews = loadOrKeep(
                            "Bukti penyelesaian",
                            completionReviews,
                            loadErrors
                        ) { SmartWasteApi.completionReviews() }
                        adminNotes = loadOrKeep(
                            "Catatan Admin",
                            adminNotes,
                            loadErrors
                        ) { SmartWasteApi.adminNotes() }
                        archivedAdminNotes = loadOrKeep(
                            "Arsip catatan",
                            archivedAdminNotes,
                            loadErrors
                        ) { SmartWasteApi.archivedAdminNotes() }
                        dailyReports = loadOrKeep("Aktivitas harian", dailyReports, loadErrors) {
                            SmartWasteApi.dailyReports(from, to)
                        }
                    } else {
                        tasks = loadOrKeep(
                            "Penugasan",
                            tasks,
                            loadErrors
                        ) { SmartWasteApi.workerTasks() }
                        incomingReports = loadOrKeep(
                            "Laporan masuk",
                            incomingReports,
                            loadErrors
                        ) { SmartWasteApi.workerIncomingReports() }
                        vehicles = loadOrKeep("Armada", vehicles, loadErrors) { SmartWasteApi.vehicles() }
                        facilities = loadOrKeep("TPA", facilities, loadErrors) { SmartWasteApi.facilities() }
                    }
                    errorMessage = loadErrors.take(4).joinToString("\n") +
                            if (loadErrors.size > 4) "\n+ ${loadErrors.size - 4} data lain gagal dimuat" else ""
                }

                fun signOut() {
                    scope.launch { SmartWasteApi.logout() }
                    activeAccount = null
                    reports = emptyList()
                    archivedReports = emptyList()
                    incomingReports = emptyList()
                    tasks = emptyList()
                    users = emptyList()
                    workers = emptyList()
                    completionReviews = emptyList()
                    adminNotes = emptyList()
                    archivedAdminNotes = emptyList()
                    vehicles = emptyList()
                    facilities = emptyList()
                    dailyReports = emptyList()
                    statistics = AdminStatistics()
                    summary = AdminDashboardSummary()
                    errorMessage = ""
                    selectedStatus = null
                    selectedReportView = "verify"
                    selectedOpsTab = "petugas"
                    navController.navigate("login") { popUpTo(0) { inclusive = true } }
                }

                fun navigateAdminRoute(route: String) {
                    when {
                        route.startsWith("status:") -> {
                            selectedStatus = route.substringAfter(':').takeUnless { it == "semua" }
                            selectedReportView = "all"
                            navController.navigate("reports") { launchSingleTop = true }
                        }

                        route.startsWith("ops:") -> {
                            selectedOpsTab = route.substringAfter(':')
                            navController.navigate("ops") { launchSingleTop = true }
                        }

                        route == "reports" -> {
                            selectedStatus = null
                            selectedReportView = "verify"
                            navController.navigate("reports") { launchSingleTop = true }
                        }

                        else -> {
                            navController.navigate(route) { launchSingleTop = true }
                        }
                    }
                }

                LaunchedEffect(Unit) {
                    delay(3000)
                    showSplash = false
                }

                LaunchedEffect(Unit) {
                    delay(5_000)
                    while (true) {
                        if (availableUpdate == null) {
                            runCatching { LanAppUpdater.check(this@MainActivity) }
                                .getOrNull()
                                ?.takeIf { it.versionCode > dismissedUpdateCode }
                                ?.let { availableUpdate = it }
                        }
                        delay(60_000L)
                    }
                }

                LaunchedEffect(activeAccount?.username) {
                    if (activeAccount != null) {
                        while (true) {
                            refreshWorkspace()
                            delay(15_000)
                        }
                    }
                }

                AnimatedVisibility(
                    visible = showSplash,
                    exit = fadeOut(animationSpec = tween(350))
                ) {
                    SmartWasteSplash()
                }

                if (!showSplash) {
                    NavHost(navController = navController, startDestination = "login") {
                        composable("login") {
                            LoginScreen(
                                initialDarkMode = isDarkMode,
                                onAuthenticate = { username, password, selectedRole ->
                                    val account = SmartWasteApi.login(username.trim(), password)
                                    if (account.role != selectedRole) {
                                        SmartWasteApi.logout()
                                        throw IllegalStateException(
                                            "Akun ini terdaftar sebagai ${account.role.label}. Pilih ${account.role.label} untuk masuk."
                                        )
                                    }
                                    activeAccount = account
                                    selectedStatus = null
                                    selectedReportView = "verify"
                                    account
                                },
                                onCheckConnection = { SmartWasteApi.checkConnection() },
                                onLoginSuccess = {
                                    val destination =
                                        if (activeAccount?.role == UserRole.PETUGAS) "tasks" else "dashboard"
                                    navController.navigate(destination) {
                                        popUpTo("login") { inclusive = true }
                                    }
                                },
                                onThemeModeChange = { darkMode ->
                                    isDarkMode = darkMode
                                    appearancePreferences.edit().putBoolean("dark_mode", darkMode).apply()
                                }
                            )
                        }
                        composable("dashboard") {
                            val account = activeAccount
                            if (account == null) RedirectToLogin { navController.navigate("login") }
                            else if (account.role != UserRole.ADMIN) RedirectToRoute {
                                navController.navigate(
                                    "tasks"
                                )
                            }
                            else AdminDashboardScreen(
                                summary = summary,
                                reports = reports,
                                errorMessage = errorMessage,
                                account = account,
                                onNavigate = ::navigateAdminRoute,
                                onLogout = ::signOut
                            )
                        }
                        composable("reports") {
                            val account = activeAccount
                            if (account == null) RedirectToLogin { navController.navigate("login") }
                            else if (account.role != UserRole.ADMIN) RedirectToRoute {
                                navController.navigate(
                                    "tasks"
                                )
                            }
                            else AdminVerificationScreen(
                                reports = reports,
                                archivedReports = archivedReports,
                                selectedStatus = selectedStatus,
                                showAllReports = selectedReportView == "all",
                                showingArchive = selectedReportView == "archive",
                                errorMessage = errorMessage,
                                account = account,
                                onOpenPhoto = { openPhoto(it) },
                                onArchiveViewChange = { showing ->
                                    selectedReportView = if (showing) "archive" else "all"
                                    selectedStatus = null
                                },
                                onArchiveReport = { report ->
                                    SmartWasteApi.archiveReport(report.id)
                                    refreshWorkspace()
                                },
                                onRestoreArchivedReport = { report ->
                                    SmartWasteApi.restoreArchivedReport(report.id)
                                    selectedReportView = "all"
                                    selectedStatus = null
                                    refreshWorkspace()
                                },
                                onReportViewChange = { showAll ->
                                    selectedReportView = if (showAll) "all" else "verify"
                                    selectedStatus = null
                                },
                                onFilterChange = { selectedStatus = it },
                                onNavigate = ::navigateAdminRoute,
                                onLogout = ::signOut
                            )
                        }
                        composable("ops") {
                            val account = activeAccount
                            if (account == null) RedirectToLogin { navController.navigate("login") }
                            else if (account.role != UserRole.ADMIN) RedirectToRoute {
                                navController.navigate(
                                    "tasks"
                                )
                            }
                            else AdminOperationsScreen(
                                users = users,
                                workers = workers,
                                completionReviews = completionReviews,
                                adminNotes = adminNotes,
                                archivedAdminNotes = archivedAdminNotes,
                                vehicles = vehicles,
                                facilities = facilities,
                                errorMessage = errorMessage,
                                account = account,
                                onCreateWorker = { name, username, email, password, zone, role ->
                                    SmartWasteApi.createStaffAccount(
                                        name,
                                        username,
                                        email,
                                        password,
                                        zone,
                                        role
                                    )
                                    refreshWorkspace()
                                },
                                onUpdateWorker = { worker, active ->
                                    SmartWasteApi.updateWorker(worker, active)
                                    refreshWorkspace()
                                },
                                onDeleteWorker = { worker, replacementWorkerId ->
                                    SmartWasteApi.deleteWorkerAccount(worker.id, replacementWorkerId)
                                    refreshWorkspace()
                                },
                                onCreateVehicle = { plate, capacity, condition, route ->
                                    SmartWasteApi.createVehicle(plate, capacity, condition, route)
                                    refreshWorkspace()
                                },
                                onUpdateVehicle = { vehicle ->
                                    SmartWasteApi.updateVehicle(vehicle)
                                    refreshWorkspace()
                                },
                                onCreateFacility = { name, address, capacity ->
                                    SmartWasteApi.createFacility(name, address, capacity)
                                    refreshWorkspace()
                                },
                                onUpdateFacility = { facility ->
                                    SmartWasteApi.updateFacility(facility)
                                    refreshWorkspace()
                                },
                                onToggleUser = { user ->
                                    SmartWasteApi.toggleUser(user.id, !user.active)
                                    refreshWorkspace()
                                },
                                onDeleteUser = { user ->
                                    SmartWasteApi.deleteUserAccount(user.id)
                                    refreshWorkspace()
                                },
                                onOpenPhoto = ::openPhoto,
                                onReviewCompletion = { review, action, note ->
                                    SmartWasteApi.reviewCompletion(review.id, action, note)
                                    refreshWorkspace()
                                },
                                onAddAdminNote = { note, text ->
                                    SmartWasteApi.addAdminNote(note.report.id, text)
                                    refreshWorkspace()
                                },
                                onArchiveAdminNote = { note ->
                                    SmartWasteApi.archiveAdminNote(note.report.id)
                                    refreshWorkspace()
                                },
                                onRestoreAdminNote = { note ->
                                    SmartWasteApi.restoreAdminNote(note.note.report.id)
                                    refreshWorkspace()
                                },
                                onNavigate = ::navigateAdminRoute,
                                initialTab = selectedOpsTab,
                                onTabSelected = { selectedOpsTab = it },
                                onLogout = ::signOut
                            )
                        }
                        composable("stats") {
                            val account = activeAccount
                            if (account == null) RedirectToLogin { navController.navigate("login") }
                            else if (account.role != UserRole.ADMIN) RedirectToRoute {
                                navController.navigate(
                                    "tasks"
                                )
                            }
                            else AdminStatsScreen(
                                stats = statistics,
                                dailyReports = dailyReports,
                                dailyRangeDays = dailyRangeDays,
                                errorMessage = errorMessage,
                                account = account,
                                onDailyRange = { days ->
                                    dailyRangeDays = days
                                    scope.launch { refreshWorkspace() }
                                },
                                onNavigate = ::navigateAdminRoute,
                                onLogout = ::signOut
                            )
                        }
                        composable("profile") {
                            val account = activeAccount
                            if (account == null) RedirectToLogin { navController.navigate("login") }
                            else if (account.role != UserRole.ADMIN) RedirectToRoute {
                                navController.navigate("tasks")
                            }
                            else AdminProfileScreen(
                                account = account,
                                onNavigate = ::navigateAdminRoute,
                                onLogout = ::signOut
                            )
                        }
                        composable("tasks") {
                            val account = activeAccount
                            if (account == null) RedirectToLogin { navController.navigate("login") }
                            else if (account.role != UserRole.PETUGAS) RedirectToRoute {
                                navController.navigate(
                                    "dashboard"
                                )
                            }
                            else PetugasTasksScreen(
                                tasks = tasks,
                                incomingReports = incomingReports,
                                vehicles = vehicles,
                                facilities = facilities,
                                errorMessage = errorMessage,
                                account = account,
                                onOpenPhoto = { openPhoto(it) },
                                onAssignReport = { report, vehicleId, tpaId ->
                                    SmartWasteApi.assignReport(report.id, vehicleId, tpaId)
                                    refreshWorkspace()
                                },
                                onRejectReport = { report, note ->
                                    SmartWasteApi.updateReportStatus(report.id, WasteFlow.REJECTED, note)
                                    refreshWorkspace()
                                },
                                onMarkTransported = { task ->
                                    SmartWasteApi.markTaskTransported(task.id)
                                    refreshWorkspace()
                                },
                                onSubmitProof = { task, photoUri, note ->
                                    val photoBytes = compressEvidence(photoUri)
                                    SmartWasteApi.submitCompletionProof(
                                        task.id,
                                        photoBytes,
                                        "bukti-${task.id}.jpg",
                                        note
                                    )
                                    refreshWorkspace()
                                },
                                onLogout = ::signOut
                            )
                        }
                    }
                }

                availableUpdate?.let { update ->
                    androidx.compose.material3.AlertDialog(
                        onDismissRequest = {
                            if (!downloadingUpdate) {
                                dismissedUpdateCode = update.versionCode
                                availableUpdate = null
                                updateMessage = ""
                            }
                        },
                        title = { androidx.compose.material3.Text("Pembaruan Smart Waste ${update.versionName}") },
                        text = {
                            androidx.compose.material3.Text(
                                if (updateMessage.isBlank()) update.notes
                                else "${update.notes}\n\n$updateMessage"
                            )
                        },
                        confirmButton = {
                            androidx.compose.material3.Button(
                                enabled = !downloadingUpdate,
                                onClick = {
                                    scope.launch {
                                        downloadingUpdate = true
                                        updateMessage = ""
                                        try {
                                            val apk = downloadedUpdate
                                                ?: LanAppUpdater.download(this@MainActivity, update)
                                                    .also { downloadedUpdate = it }
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O &&
                                                !packageManager.canRequestPackageInstalls()
                                            ) {
                                                updateMessage = "Unduhan siap. Izinkan pemasangan untuk Smart Waste, lalu tekan tombol ini lagi."
                                                startActivity(
                                                    Intent(
                                                        Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES,
                                                        android.net.Uri.parse("package:$packageName")
                                                    )
                                                )
                                            } else {
                                                val apkUri = androidx.core.content.FileProvider.getUriForFile(
                                                    this@MainActivity,
                                                    "$packageName.fileprovider",
                                                    apk
                                                )
                                                startActivity(
                                                    Intent(Intent.ACTION_VIEW).apply {
                                                        setDataAndType(
                                                            apkUri,
                                                            "application/vnd.android.package-archive"
                                                        )
                                                        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                                                    }
                                                )
                                            }
                                        } catch (error: Exception) {
                                            updateMessage = error.message ?: "Pembaruan gagal disiapkan."
                                        } finally {
                                            downloadingUpdate = false
                                        }
                                    }
                                }
                            ) {
                                androidx.compose.material3.Text(
                                    when {
                                        downloadingUpdate -> "Mengunduh…"
                                        downloadedUpdate != null -> "Pasang pembaruan"
                                        else -> "Unduh & pasang"
                                    }
                                )
                            }
                        },
                        dismissButton = {
                            androidx.compose.material3.TextButton(
                                enabled = !downloadingUpdate,
                                onClick = {
                                    dismissedUpdateCode = update.versionCode
                                    availableUpdate = null
                                    updateMessage = ""
                                }
                            ) {
                                androidx.compose.material3.Text("Nanti")
                            }
                        }
                    )
                }
            }
        }
    }

    private fun openPhoto(url: String) {
        if (url.isBlank()) return
        runCatching { startActivity(Intent(Intent.ACTION_VIEW, url.toUri())) }
            .onFailure { /* Laporan tetap bisa dibaca meski browser foto tidak tersedia. */ }
    }

    private suspend fun compressEvidence(uri: Uri): ByteArray = withContext(Dispatchers.IO) {
        val original = contentResolver.openInputStream(uri)?.use { input ->
            val output = ByteArrayOutputStream()
            val buffer = ByteArray(16 * 1024)
            var total = 0
            while (true) {
                val read = input.read(buffer)
                if (read < 0) break
                total += read
                if (total > 25 * 1024 * 1024) {
                    throw IllegalStateException("Ukuran foto terlalu besar. Pilih foto di bawah 25 MB.")
                }
                output.write(buffer, 0, read)
            }
            output.toByteArray()
        } ?: throw IllegalStateException("Foto tidak dapat dibaca dari perangkat.")

        if (original.size <= 4 * 1024 * 1024) return@withContext original
        val bounds = BitmapFactory.Options().apply { inJustDecodeBounds = true }
        BitmapFactory.decodeByteArray(original, 0, original.size, bounds)
        if (bounds.outWidth <= 0 || bounds.outHeight <= 0) {
            throw IllegalStateException("File yang dipilih bukan foto yang bisa dibaca.")
        }
        var sample = 1
        while (maxOf(bounds.outWidth, bounds.outHeight) / sample > 1800) sample *= 2
        val bitmap = BitmapFactory.decodeByteArray(
            original,
            0,
            original.size,
            BitmapFactory.Options().apply { inSampleSize = sample }
        ) ?: throw IllegalStateException("Foto gagal diproses. Coba pilih foto lain.")

        val oriented = runCatching {
            val orientation = original.inputStream().use { stream ->
                ExifInterface(stream).getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL
                )
            }
            val degrees = when (orientation) {
                ExifInterface.ORIENTATION_ROTATE_90 -> 90f
                ExifInterface.ORIENTATION_ROTATE_180 -> 180f
                ExifInterface.ORIENTATION_ROTATE_270 -> 270f
                else -> 0f
            }
            if (degrees == 0f) bitmap else {
                val matrix = Matrix().apply { postRotate(degrees) }
                Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
            }
        }.getOrElse { bitmap }

        val maxDimension = maxOf(oriented.width, oriented.height)
        val outputBitmap = if (maxDimension > 1800) {
            val scale = 1800f / maxDimension
            Bitmap.createScaledBitmap(
                oriented,
                (oriented.width * scale).toInt().coerceAtLeast(1),
                (oriented.height * scale).toInt().coerceAtLeast(1),
                true
            )
        } else oriented
        val compressed = ByteArrayOutputStream()
        var quality = 84
        do {
            compressed.reset()
            outputBitmap.compress(Bitmap.CompressFormat.JPEG, quality, compressed)
            quality -= 8
        } while (compressed.size() > 4 * 1024 * 1024 && quality >= 52)
        if (compressed.size() > 5 * 1024 * 1024) {
            throw IllegalStateException("Foto masih terlalu besar setelah dikompres. Pilih foto lain.")
        }
        if (outputBitmap !== oriented) outputBitmap.recycle()
        if (oriented !== bitmap) oriented.recycle()
        bitmap.recycle()
        compressed.toByteArray()
    }
}

private suspend fun <T> loadOrKeep(
    label: String,
    currentValue: T,
    errors: MutableList<String>,
    load: suspend () -> T
): T = try {
    load()
} catch (cancelled: CancellationException) {
    throw cancelled
} catch (error: Exception) {
    errors += "$label: ${error.message ?: "gagal dimuat."}"
    currentValue
}

@androidx.compose.runtime.Composable
private fun RedirectToLogin(onRedirect: () -> Unit) {
    LaunchedEffect(Unit) { onRedirect() }
}

@androidx.compose.runtime.Composable
private fun RedirectToRoute(onRedirect: () -> Unit) {
    LaunchedEffect(Unit) { onRedirect() }
}
