package com.uoi.pratikumwithbunafla.ui.admin

import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Inbox
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.uoi.pratikumwithbunafla.R
import com.uoi.pratikumwithbunafla.data.model.*
import com.uoi.pratikumwithbunafla.domain.WasteFlow
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
internal fun DashboardHeroBanner(
    summary: AdminDashboardSummary,
    pendingProofCount: Int,
    account: UserAccount,
    onLogout: () -> Unit,
    onOpenProfile: () -> Unit,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    onClick: (String) -> Unit,
) {
    val darkMode = LocalSmartWasteAppearance.current.darkMode
    val statusBarInset = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()
    val heroHeaderHeight = statusBarInset + 64.dp
    val heroHeight = 340.dp + heroHeaderHeight
    val bannerText = Color(0xFFF5F4E8)
    val bannerMuted = Color(0xFFE2E9D9)
    val heroStatusSurface = Color(0xFF102D22).copy(alpha = if (darkMode) 0.72f else 0.64f)
    val heroContentWidth = if (LocalConfiguration.current.screenWidthDp < 360) 0.74f else 0.68f
    val focusHeading: String
    val focusDetail: String
    val focusAction: String
    val focusIcon: ImageVector
    val focusColor: Color
    val focusRoute: String
    when {
        pendingProofCount > 0 -> {
            focusHeading = "Bukti petugas menunggu"
            focusDetail = "$pendingProofCount hasil kerja perlu diperiksa"
            focusAction = "Tinjau kinerja"
            focusIcon = Icons.Filled.CheckCircle
            focusColor = if (darkMode) RoleTeal else Color(0xFFB9DED0)
            focusRoute = "ops:bukti"
        }
        summary.waiting > 0 -> {
            focusHeading = "Menunggu verifikasi"
            focusDetail = "${summary.waiting} laporan perlu diperiksa"
            focusAction = "Buka antrean"
            focusIcon = Icons.Filled.Inbox
            focusColor = if (darkMode) RoleOrange else Color(0xFFFFD28A)
            focusRoute = "reports"
        }
        summary.assigned > 0 -> {
            focusHeading = "Sedang di lapangan"
            focusDetail = "${summary.assigned} laporan ditangani petugas"
            focusAction = "Pantau penugasan"
            focusIcon = Icons.Filled.LocalShipping
            focusColor = if (darkMode) RoleTeal else Color(0xFFB9DED0)
            focusRoute = "ops:petugas"
        }
        summary.processing > 0 -> {
            focusHeading = "Dalam penanganan"
            focusDetail = "${summary.processing} laporan sedang diproses"
            focusAction = "Lihat progres"
            focusIcon = Icons.Filled.Sync
            focusColor = if (darkMode) RoleTeal else Color(0xFFB9DED0)
            focusRoute = "reports"
        }
        summary.totalReports == 0 -> {
            focusHeading = "Belum ada laporan"
            focusDetail = "Laporan warga akan tampil di sini"
            focusAction = "Buka laporan"
            focusIcon = Icons.Filled.Inbox
            focusColor = if (darkMode) RoleGreen else Color(0xFFE2E9D9)
            focusRoute = "reports"
        }
        summary.completed > 0 -> {
            focusHeading = "Pekerjaan tuntas"
            focusDetail = "${summary.completed} laporan telah selesai"
            focusAction = "Lihat laporan selesai"
            focusIcon = Icons.Filled.CheckCircle
            focusColor = if (darkMode) RoleTeal else Color(0xFFB9DED0)
            focusRoute = "status:selesai"
        }
        else -> {
            focusHeading = "Semua laporan terpantau"
            focusDetail = "${summary.totalReports} laporan tercatat"
            focusAction = "Buka seluruh laporan"
            focusIcon = Icons.Filled.Inbox
            focusColor = if (darkMode) RoleGreen else Color(0xFFE2E9D9)
            focusRoute = "status:semua"
        }
    }

    Box(
        modifier =
            Modifier.fillMaxWidth()
                .height(heroHeight)
                .clip(HeroBottomCurveShape)
                .background(if (darkMode) Color(0xFF1C3025) else Color(0xFF286B4D))
                .clickable { onClick(focusRoute) },
    ) {
        val illustrationPainter = painterResource(R.drawable.hero_recycling_crew)
        Image(
            painter = illustrationPainter,
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier =
                Modifier.align(Alignment.BottomEnd)
                    .height(heroHeight)
                    .aspectRatio(260f / 280f)
                    .graphicsLayer { compositingStrategy = CompositingStrategy.Offscreen }
                    .drawWithContent {
                        drawContent()
                        drawRect(
                            brush = Brush.horizontalGradient(*heroFadeStops(0f, 0.28f)),
                            blendMode = BlendMode.DstIn,
                        )
                    },
        )

        val textSideShade = if (darkMode) Color(0xFF17271E) else Color(0xFF103F30)
        Box(
            modifier =
                Modifier.fillMaxSize()
                    .background(
                        Brush.horizontalGradient(
                            0f to textSideShade.copy(alpha = 0.82f),
                            0.48f to textSideShade.copy(alpha = 0.60f),
                            0.76f to Color.Transparent,
                            1f to Color.Transparent,
                        ),
                    ),
        )

        Box(
            modifier =
                Modifier.fillMaxWidth()
                    .align(Alignment.TopCenter)
                    .windowInsetsPadding(WindowInsets.statusBars)
                    .padding(horizontal = 20.dp, vertical = 11.dp),
        ) {
            AdminTopBar(
                account = account,
                onLogout = onLogout,
                onOpenProfile = onOpenProfile,
                onOpenNotifications = { onClick("notifications") },
                isOnHero = true,
            )
        }

        Column(
            modifier =
                Modifier.fillMaxWidth(heroContentWidth)
                    .align(Alignment.TopStart)
                    .padding(start = 18.dp, top = heroHeaderHeight + 14.dp, bottom = 24.dp),
        ) {
            Column(
                modifier =
                    Modifier.fillMaxWidth()
                        .clip(RoundedCornerShape(18.dp))
                        .background(heroStatusSurface)
                        .clickable { onClick(focusRoute) }
                        .padding(horizontal = 14.dp, vertical = 13.dp),
                verticalArrangement = Arrangement.spacedBy(9.dp),
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(9.dp),
                ) {
                    Box(
                        modifier =
                            Modifier.size(34.dp)
                                .clip(CircleShape)
                                .background(focusColor.copy(alpha = 0.18f)),
                        contentAlignment = Alignment.Center,
                    ) {
                        Icon(
                            focusIcon,
                            contentDescription = null,
                            tint = focusColor,
                            modifier = Modifier.size(19.dp),
                        )
                    }
                    Text(
                        focusHeading,
                        modifier = Modifier.weight(1f),
                        color = bannerText,
                        fontSize = 14.sp,
                        lineHeight = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
                Text(
                    focusDetail,
                    color = bannerMuted,
                    fontSize = 12.sp,
                    lineHeight = 17.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        focusAction,
                        color = focusColor,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = null,
                        tint = focusColor,
                        modifier = Modifier.size(17.dp),
                    )
                }
            }
        }
    }
}

internal val HeroBottomCurveShape =
    androidx.compose.foundation.shape.GenericShape { size, _ ->
        moveTo(0f, 0f)
        lineTo(size.width, 0f)
        lineTo(size.width, size.height)
        quadraticTo(size.width * 0.5f, size.height * 0.82f, 0f, size.height)
        close()
    }

internal fun heroFadeStops(start: Float, end: Float): Array<Pair<Float, Color>> =
    (0..32)
        .map { step ->
            val position = step / 32f
            val progress = ((position - start) / (end - start)).coerceIn(0f, 1f)
            val smoothAlpha = progress * progress * (3f - 2f * progress)
            position to Color.White.copy(alpha = smoothAlpha)
        }
        .toTypedArray()

@Composable
fun AdminProfileScreen(
    account: UserAccount,
    onNavigate: (String) -> Unit,
    onLogout: () -> Unit,
    onUpdateProfile: suspend (String) -> UserAccount,
    onUpdateProfilePhoto: suspend (Uri) -> UserAccount,
    isInitialLoading: Boolean = false,
    isRefreshing: Boolean = false,
    onRefresh: () -> Unit = {},
) {
    BackHandler { onNavigate("dashboard") }

    AdminPage(
        current = "profile",
        account = account,
        onNavigate = onNavigate,
        onLogout = onLogout,
        isInitialLoading = isInitialLoading,
        isRefreshing = isRefreshing,
        onRefresh = onRefresh,
    ) {
        IconButton(
            onClick = { onNavigate("dashboard") },
            modifier = Modifier.size(40.dp),
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Kembali ke Beranda",
                tint = LoginAccent,
                modifier = Modifier.size(21.dp),
            )
        }
        AccountProfileSettings(account, onLogout, onUpdateProfile, onUpdateProfilePhoto)
    }
}

@Composable
internal fun ColumnScope.RoleEntrySection(
    entryKey: Int,
    order: Int,
    verticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(0.dp),
    animateLift: Boolean = true,
    content: @Composable ColumnScope.() -> Unit,
) {
    var revealed by rememberSaveable(entryKey, order, animateLift) { mutableStateOf(false) }
    LaunchedEffect(entryKey, order, animateLift) {
        if (animateLift) delay(order * 150L)
        revealed = true
    }
    val progress by
        animateFloatAsState(
            targetValue = if (revealed) 1f else 0f,
            animationSpec =
                tween(
                    durationMillis = if (animateLift) 520 else 180,
                    easing = FastOutSlowInEasing,
                ),
            label = "role-entry-section-$order",
        )
    val entryOffsetPx =
        with(LocalDensity.current) {
            if (animateLift) 40.dp.toPx() else 0f
        }

    Box(
        modifier =
            Modifier.fillMaxWidth().graphicsLayer {
                alpha = progress
                translationY = if (animateLift) entryOffsetPx * (1f - progress) else 0f
                scaleX = if (animateLift) 0.96f + 0.04f * progress else 1f
                scaleY = if (animateLift) 0.96f + 0.04f * progress else 1f
            },
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = verticalArrangement,
        ) {
            content()
        }
    }
}

@Composable
fun AdminDashboardScreen(
    summary: AdminDashboardSummary,
    reports: List<WasteRecord>,
    workers: List<AdminWorker>,
    vehicles: List<WasteVehicle>,
    facilities: List<WasteFacility>,
    errorMessage: String,
    account: UserAccount,
    onNavigate: (String) -> Unit,
    onLogout: () -> Unit,
    onAssignReport: suspend (WasteRecord, Int, Int, Int) -> Unit,
    pendingProofCount: Int = 0,
    pendingVehicleRepairActionCount: Int = 0,
    pendingCapacityClearanceCount: Int = 0,
    isInitialLoading: Boolean = false,
    isRefreshing: Boolean = false,
    onRefresh: () -> Unit = {},
    entryKey: Int = 0,
) {
    var assigningReport by remember { mutableStateOf<WasteRecord?>(null) }
    var dashboardReportPage by rememberSaveable(account.username) { mutableStateOf(0) }
    val prioritizedDashboardReports =
        remember(reports) {
            reports.sortedBy { dashboardReportPriority(it.status) }
        }
    val dashboardReportPageCount = (prioritizedDashboardReports.size + 2) / 3
    val visibleDashboardReports = prioritizedDashboardReports.drop(dashboardReportPage * 3).take(3)

    LaunchedEffect(dashboardReportPageCount) {
        if (dashboardReportPage >= dashboardReportPageCount) {
            dashboardReportPage = (dashboardReportPageCount - 1).coerceAtLeast(0)
        }
    }

    AdminPage(
        current = "dashboard",
        account = account,
        onNavigate = onNavigate,
        onLogout = onLogout,
        isInitialLoading = isInitialLoading,
        isRefreshing = isRefreshing,
        onRefresh = onRefresh,
        showTopBar = false,
        fullBleedContent = {
            RoleEntrySection(entryKey = entryKey, order = 0) {
                DashboardHeroBanner(
                    summary = summary,
                    pendingProofCount = pendingProofCount,
                    account = account,
                    onLogout = onLogout,
                    onOpenProfile = { onNavigate("profile") },
                    isRefreshing = isRefreshing,
                    onRefresh = onRefresh,
                    onClick = onNavigate,
                )
            }
        },
    ) {
        RoleEntrySection(
            entryKey = entryKey,
            order = 1,
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            if (errorMessage.isNotBlank()) InlineError(errorMessage)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(3.dp),
                ) {
                    Text(
                        "Ruang kerja Admin",
                        color = AppText,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                    )
                    Text(
                        "Laporan, petugas, dan pemantauan kinerja",
                        color =
                            if (LocalSmartWasteAppearance.current.darkMode) AppTextMuted
                            else Color(0xFF26382D),
                        fontFamily = InterFont,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Normal,
                    )
                }
                Text(
                    "Semua laporan →",
                    modifier =
                        Modifier.clip(RoundedCornerShape(10.dp))
                            .clickable { onNavigate("status:semua") }
                            .padding(6.dp),
                    color = LoginAccent,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.SemiBold,
                )
            }
        }
        RoleEntrySection(
            entryKey = entryKey,
            order = 2,
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth().height(86.dp),
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                ) {
                    AdminTaskTile(
                        "Laporan",
                        Icons.AutoMirrored.Filled.Assignment,
                        TaskAmber,
                        Modifier.weight(1f),
                    ) {
                        onNavigate("reports")
                    }
                    AdminTaskTile(
                        "Petugas",
                        Icons.Filled.AdminPanelSettings,
                        TaskPurple,
                        Modifier.weight(1f),
                    ) {
                        onNavigate("ops:petugas")
                    }
                    AdminTaskTile(
                        "Statistik",
                        Icons.Filled.BarChart,
                        TaskIndigo,
                        Modifier.weight(1f),
                    ) {
                        onNavigate("stats")
                    }
                    AdminTaskTile(
                        "Bukti kerja",
                        Icons.Filled.CheckCircle,
                        TaskTeal,
                        Modifier.weight(1f),
                    ) {
                        onNavigate("ops:bukti")
                    }
                }
            }
        }
        RoleEntrySection(entryKey = entryKey, order = 3) {
            AppCard(
                modifier = Modifier.fillMaxWidth(),
                containerColor = AppSurfaceMuted,
                borderColor = null,
                elevation = 0.dp,
                shape = RoundedCornerShape(18.dp),
            ) {
                val totalPendingActions =
                    pendingProofCount +
                        pendingVehicleRepairActionCount +
                        pendingCapacityClearanceCount
                Column(
                    modifier = Modifier.fillMaxWidth().padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(9.dp),
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        OperationalMetric(
                            "Total laporan",
                            summary.totalReports.toString(),
                            Modifier.weight(1f),
                        ) {
                            onNavigate("reports")
                        }
                        OperationalMetric(
                            "Menunggu",
                            summary.waiting.toString(),
                            Modifier.weight(1f),
                        ) {
                            onNavigate("reports")
                        }
                        OperationalMetric(
                            "Petugas aktif",
                            summary.activeWorkers.toString(),
                            Modifier.weight(1f),
                        ) {
                            onNavigate("ops:petugas")
                        }
                    }
                    if (totalPendingActions > 0) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            Text(
                                "Perlu ditangani",
                                color = AppText,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold,
                            )
                            Text(
                                "$totalPendingActions tindakan",
                                color = AppTextMuted,
                                fontSize = 11.sp,
                                fontFamily = InterFont,
                            )
                        }
                        Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
                            if (pendingProofCount > 0) {
                                DashboardPriorityAction(
                                    title = "$pendingProofCount bukti kerja menunggu",
                                    detail = "Tinjau foto penyelesaian petugas",
                                    icon = Icons.Filled.CheckCircle,
                                    tint = RoleGreenDark,
                                ) {
                                    onNavigate("ops:bukti")
                                }
                            }
                            if (pendingVehicleRepairActionCount > 0) {
                                DashboardPriorityAction(
                                    title =
                                        "$pendingVehicleRepairActionCount proses armada menunggu",
                                    detail = "Tinjau laporan atau hasil perbaikan",
                                    icon = Icons.Filled.Build,
                                    tint = RoleOrange,
                                ) {
                                    onNavigate("ops:armada")
                                }
                            }
                            if (pendingCapacityClearanceCount > 0) {
                                DashboardPriorityAction(
                                    title = "$pendingCapacityClearanceCount permintaan kapasitas",
                                    detail = "Periksa bukti sebelum menyetujui reset",
                                    icon = Icons.Filled.LocalShipping,
                                    tint = RoleTeal,
                                ) {
                                    onNavigate("ops:pengosongan")
                                }
                            }
                        }
                    }
                }
            }
        }
        RoleEntrySection(
            entryKey = entryKey,
            order = 4,
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            SectionTitle(
                if (dashboardReportPage == 0) "Laporan terbaru" else "Laporan lama",
                if (dashboardReportPageCount > 1) {
                    "Halaman ${dashboardReportPage + 1} dari $dashboardReportPageCount"
                } else {
                    "Ringkasan laporan warga"
                },
            )
            if (reports.isEmpty()) {
                EmptyAdminState("Belum ada laporan dari warga.", useCompactCardStyle = true)
            } else {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    visibleDashboardReports.forEach { report ->
                        AdminLatestReportCard(
                            report = report,
                            onOpen = { onNavigate("status:${report.status}") },
                            onAssign = { assigningReport = report },
                        )
                    }
                }
                if (dashboardReportPageCount > 1) {
                    DashboardReportPagination(
                        currentPage = dashboardReportPage,
                        pageCount = dashboardReportPageCount,
                        onSelectPage = { dashboardReportPage = it },
                    )
                }
            }
        }
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
internal fun DashboardReportPagination(
    currentPage: Int,
    pageCount: Int,
    onSelectPage: (Int) -> Unit,
) {
    val visiblePageCount = minOf(5, pageCount)
    val firstVisiblePage =
        (currentPage - visiblePageCount / 2)
            .coerceAtLeast(0)
            .coerceAtMost((pageCount - visiblePageCount).coerceAtLeast(0))
    val visiblePages = firstVisiblePage until (firstVisiblePage + visiblePageCount)

    Row(
        modifier = Modifier.fillMaxWidth().padding(top = 2.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        DashboardReportArrowButton(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Halaman sebelumnya",
            enabled = currentPage > 0,
            onClick = { onSelectPage(currentPage - 1) },
        )
        visiblePages.forEach { page ->
            DashboardReportPageButton(page, selected = page == currentPage) {
                onSelectPage(page)
            }
        }
        DashboardReportArrowButton(
            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
            contentDescription = "Halaman berikutnya",
            enabled = currentPage < pageCount - 1,
            onClick = { onSelectPage(currentPage + 1) },
        )
    }
}

internal fun dashboardReportPriority(status: String): Int =
    when (status.trim().lowercase()) {
        WasteFlow.WAITING -> 0
        WasteFlow.PROCESSING,
        WasteFlow.ASSIGNED,
        WasteFlow.TRANSPORTED -> 1
        WasteFlow.DONE -> 2
        else -> 3
    }

@Composable
internal fun DashboardReportArrowButton(
    imageVector: androidx.compose.ui.graphics.vector.ImageVector,
    contentDescription: String,
    enabled: Boolean,
    onClick: () -> Unit,
) {
    val tint by
        animateColorAsState(
            targetValue = if (enabled) RoleGreenDark else AppTextMuted.copy(alpha = 0.45f),
            animationSpec = tween(200, easing = FastOutSlowInEasing),
            label = "dashboard-report-arrow-${contentDescription.filter(Char::isLetter)}-tint",
        )

    IconButton(
        onClick = onClick,
        enabled = enabled,
        modifier =
            Modifier.padding(horizontal = 4.dp)
                .size(38.dp)
                .clip(RoundedCornerShape(11.dp))
                .background(AppSurfaceMuted),
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = contentDescription,
            tint = tint,
            modifier = Modifier.size(18.dp),
        )
    }
}

@Composable
internal fun DashboardReportPageButton(
    page: Int,
    selected: Boolean,
    onClick: () -> Unit,
) {
    val background by
        animateColorAsState(
            targetValue = if (selected) RoleGreenDark else AppSurfaceMuted,
            animationSpec = tween(240, easing = FastOutSlowInEasing),
            label = "dashboard-report-page-${page + 1}-background",
        )
    val content by
        animateColorAsState(
            targetValue = if (selected) Color.White else AppText,
            animationSpec = tween(200, easing = FastOutSlowInEasing),
            label = "dashboard-report-page-${page + 1}-content",
        )

    Box(
        modifier =
            Modifier.padding(horizontal = 4.dp)
                .size(38.dp)
                .clip(RoundedCornerShape(11.dp))
                .background(background)
                .clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = (page + 1).toString(),
            color = content,
            fontFamily = InterFont,
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
        )
    }
}

internal enum class AdminReportStepState {
    COMPLETE,
    CURRENT,
    UPCOMING,
    REJECTED,
}

internal data class AdminReportProgressStep(
    val label: String,
    val state: AdminReportStepState,
)

internal data class AdminReportProgress(
    val steps: List<AdminReportProgressStep>,
    val completedCount: Int,
)

internal fun adminReportProgress(report: WasteRecord): AdminReportProgress {
    val proofStatus = report.completionStatus.trim().lowercase()
    val rejected = report.status == WasteFlow.REJECTED
    val completedCount =
        when {
            rejected || report.status == WasteFlow.WAITING -> 1
            report.status == WasteFlow.PROCESSING || report.status == WasteFlow.ASSIGNED -> 2
            report.status == WasteFlow.TRANSPORTED && proofStatus == "menunggu" -> 4
            report.status == WasteFlow.TRANSPORTED -> 3
            report.status == WasteFlow.DONE -> 5
            else -> 1
        }
    val currentIndex = if (rejected) 1 else completedCount.takeIf { it < 5 } ?: -1
    val labels =
        listOf(
            "Laporan masuk",
            when {
                rejected -> "Laporan ditolak"
                report.status == WasteFlow.WAITING -> "Menunggu verifikasi Admin"
                else -> "Petugas ditugaskan"
            },
            if (report.status == WasteFlow.TRANSPORTED || report.status == WasteFlow.DONE) {
                "Petugas berangkat"
            } else {
                "Menunggu petugas berangkat"
            },
            when {
                proofStatus == "perlu_perbaikan" -> "Bukti perlu diperbaiki"
                proofStatus == "menunggu" || report.status == WasteFlow.DONE ->
                    "Bukti akhir dikirim"
                else -> "Proses penanganan"
            },
            when {
                report.status == WasteFlow.DONE -> "Disetujui Admin"
                report.status == WasteFlow.TRANSPORTED && proofStatus == "menunggu" ->
                    "Menunggu persetujuan Admin"
                else -> "Persetujuan Admin"
            },
        )

    return AdminReportProgress(
        steps =
            labels.mapIndexed { index, label ->
                val state =
                    when {
                        rejected && index == 1 -> AdminReportStepState.REJECTED
                        index < completedCount -> AdminReportStepState.COMPLETE
                        index == currentIndex -> AdminReportStepState.CURRENT
                        else -> AdminReportStepState.UPCOMING
                    }
                AdminReportProgressStep(label, state)
            },
        completedCount = completedCount,
    )
}

@Composable
internal fun AdminLatestReportCard(
    report: WasteRecord,
    onOpen: () -> Unit,
    onAssign: () -> Unit,
) {
    val accent = reportAccent(report.status)
    val progress = adminReportProgress(report)
    val reportCardColor =
        if (LocalSmartWasteAppearance.current.darkMode) {
            AppSurfaceMuted
        } else {
            AppSurfaceMuted.copy(alpha = 0.72f)
        }

    AppCard(
        modifier = Modifier.fillMaxWidth(),
        containerColor = reportCardColor,
        borderColor = null,
        elevation = 0.dp,
        shape = RoundedCornerShape(18.dp),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(13.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.Top,
            ) {
                Box(
                    modifier =
                        Modifier.size(38.dp)
                            .clip(RoundedCornerShape(11.dp))
                            .background(accent.copy(alpha = 0.12f)),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        Icons.AutoMirrored.Filled.Assignment,
                        contentDescription = null,
                        tint = accent,
                        modifier = Modifier.size(19.dp),
                    )
                }
                Column(
                    modifier =
                        Modifier.weight(1f)
                            .clip(RoundedCornerShape(8.dp))
                            .clickable(onClick = onOpen),
                    verticalArrangement = Arrangement.spacedBy(3.dp),
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                    ) {
                        Text(
                            report.code,
                            modifier = Modifier.weight(1f),
                            color = AppText,
                            fontFamily = InterFont,
                            fontSize = 12.sp,
                            lineHeight = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                        StatusPill(report.status, accent)
                    }
                    Text(
                        "${report.type} · ${report.weightKg.pretty()} kg",
                        color = AppTextMuted,
                        fontFamily = InterFont,
                        fontSize = 11.sp,
                        lineHeight = 15.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Text(
                        report.location,
                        color = AppTextMuted,
                        fontFamily = InterFont,
                        fontSize = 11.sp,
                        lineHeight = 15.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }

            Column(
                modifier =
                    Modifier.fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(AppCardSurface)
                        .padding(11.dp),
                verticalArrangement = Arrangement.spacedBy(7.dp),
            ) {
                Text(
                    "Tahapan penanganan",
                    color = AppTextMuted,
                    fontFamily = InterFont,
                    fontSize = 11.sp,
                    lineHeight = 14.sp,
                    fontWeight = FontWeight.Medium,
                )
                Column(verticalArrangement = Arrangement.spacedBy(0.dp)) {
                    progress.steps.forEachIndexed { index, step ->
                        val markerColor =
                            when (step.state) {
                                AdminReportStepState.COMPLETE -> LoginAccent
                                AdminReportStepState.CURRENT -> LoginAccent
                                AdminReportStepState.UPCOMING -> AppTextMuted
                                AdminReportStepState.REJECTED -> RoleRed
                            }
                        val markerBackground =
                            when (step.state) {
                                AdminReportStepState.COMPLETE -> LoginAccent
                                AdminReportStepState.CURRENT -> LoginAccent.copy(alpha = 0.14f)
                                AdminReportStepState.UPCOMING -> AppBorder.copy(alpha = 0.55f)
                                AdminReportStepState.REJECTED -> RoleRed.copy(alpha = 0.14f)
                            }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier =
                                    Modifier.size(19.dp)
                                        .clip(CircleShape)
                                        .background(markerBackground),
                                contentAlignment = Alignment.Center,
                            ) {
                                Text(
                                    text =
                                        when (step.state) {
                                            AdminReportStepState.COMPLETE -> "✓"
                                            AdminReportStepState.REJECTED -> "×"
                                            else -> (index + 1).toString()
                                        },
                                    color =
                                        if (step.state == AdminReportStepState.COMPLETE) {
                                            Color.White
                                        } else {
                                            markerColor
                                        },
                                    fontFamily = InterFont,
                                    fontSize = 10.sp,
                                    lineHeight = 12.sp,
                                    fontWeight = FontWeight.SemiBold,
                                )
                            }
                            Spacer(Modifier.width(9.dp))
                            Text(
                                text = step.label,
                                modifier = Modifier.weight(1f),
                                color =
                                    when (step.state) {
                                        AdminReportStepState.COMPLETE -> AppTextMuted
                                        AdminReportStepState.CURRENT -> AppText
                                        AdminReportStepState.UPCOMING -> AppTextMuted
                                        AdminReportStepState.REJECTED -> RoleRed
                                    },
                                fontFamily = InterFont,
                                fontSize = 11.sp,
                                lineHeight = 15.sp,
                                fontWeight =
                                    if (
                                        step.state == AdminReportStepState.CURRENT ||
                                            step.state == AdminReportStepState.REJECTED
                                    )
                                        FontWeight.SemiBold
                                    else FontWeight.Normal,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                            )
                        }
                        if (index < progress.steps.lastIndex) {
                            Spacer(
                                Modifier.padding(start = 8.dp)
                                    .width(2.dp)
                                    .height(6.dp)
                                    .clip(CircleShape)
                                    .background(
                                        if (index < progress.completedCount) {
                                            LoginAccent.copy(alpha = 0.55f)
                                        } else {
                                            AppBorder
                                        },
                                    ),
                            )
                        }
                    }
                }
            }

            if (report.status == WasteFlow.WAITING) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    TextButton(
                        onClick = onAssign,
                        contentPadding =
                            androidx.compose.foundation.layout.PaddingValues(
                                horizontal = 8.dp,
                                vertical = 0.dp,
                            ),
                    ) {
                        Text(
                            "Setujui & tugaskan",
                            color = LoginAccent,
                            fontFamily = InterFont,
                            fontSize = 11.sp,
                            lineHeight = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                        )
                    }
                }
            }
        }
    }
}

@Composable
internal fun AdminReportAssignmentDialog(
    report: WasteRecord,
    workers: List<AdminWorker>,
    vehicles: List<WasteVehicle>,
    facilities: List<WasteFacility>,
    onDismiss: () -> Unit,
    onAssign: suspend (Int, Int, Int) -> Unit,
) {
    val eligibleWorkers = workers.filter { it.active && it.hasAccount && !it.isArchived }
    val eligibleVehicles = vehicles.filter {
        it.condition.equals("baik", ignoreCase = true) && it.availableKg >= report.weightKg
    }
    val eligibleFacilities = facilities.filter { it.remainingTon * 1000 >= report.weightKg }
    var selectedWorker by remember(report.id) { mutableStateOf<AdminWorker?>(null) }
    var selectedVehicle by remember(report.id) { mutableStateOf<WasteVehicle?>(null) }
    var selectedFacility by remember(report.id) { mutableStateOf<WasteFacility?>(null) }
    var saving by remember(report.id) { mutableStateOf(false) }
    var errorMessage by remember(report.id) { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val canAssign =
        selectedWorker?.let(eligibleWorkers::contains) == true &&
            selectedVehicle?.let(eligibleVehicles::contains) == true &&
            selectedFacility?.let(eligibleFacilities::contains) == true

    AlertDialog(
        onDismissRequest = { if (!saving) onDismiss() },
        title = {
            Text("Setujui & tugaskan laporan", color = LoginInk, fontWeight = FontWeight.Bold)
        },
        text = {
            ScrollableDialogContent(spacing = 8.dp) {
                Text(
                    "${report.code} · ${report.type} · ${report.weightKg.pretty()} kg",
                    color = AppTextMuted,
                    fontSize = 11.sp,
                )
                SelectionField(
                    "Petugas aktif",
                    selectedWorker?.let { "${it.name} · ${it.zone}" },
                    eligibleWorkers,
                    { "${it.name} · ${it.zone}" },
                ) {
                    selectedWorker = it
                }
                SelectionField(
                    "Kendaraan",
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
                if (
                    eligibleWorkers.isEmpty() ||
                        eligibleVehicles.isEmpty() ||
                        eligibleFacilities.isEmpty()
                ) {
                    val unavailable = buildList {
                        if (eligibleWorkers.isEmpty()) add("petugas aktif dengan akun")
                        if (eligibleVehicles.isEmpty())
                            add("kendaraan sehat dengan kapasitas cukup")
                        if (eligibleFacilities.isEmpty()) add("TPA dengan kapasitas cukup")
                    }
                        .joinToString(", ")
                    Text(
                        "Belum dapat ditugaskan: tidak tersedia $unavailable.",
                        color = RoleRed,
                        fontSize = 10.sp,
                    )
                }
                if (errorMessage.isNotBlank()) InlineError(errorMessage)
            }
        },
        confirmButton = {
            TextButton(
                enabled = canAssign && !saving,
                onClick = {
                    val workerId = selectedWorker?.id ?: return@TextButton
                    val vehicleId = selectedVehicle?.id ?: return@TextButton
                    val tpaId = selectedFacility?.id ?: return@TextButton
                    scope.launch {
                        saving = true
                        errorMessage = ""
                        try {
                            onAssign(workerId, vehicleId, tpaId)
                        } catch (cancelled: CancellationException) {
                            throw cancelled
                        } catch (error: Exception) {
                            errorMessage = error.message ?: "Laporan gagal ditugaskan."
                        } finally {
                            saving = false
                        }
                    }
                },
            ) {
                Text(if (saving) "Menyimpan…" else "Setujui & tugaskan", color = LoginAccent)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss, enabled = !saving) {
                Text("Batal", color = LoginMuted)
            }
        },
        containerColor = AppSurface,
    )
}

@Composable
internal fun DashboardPriorityAction(
    title: String,
    detail: String,
    icon: ImageVector,
    tint: Color,
    onClick: () -> Unit,
) {
    Row(
        modifier =
            Modifier.fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .clickable(onClick = onClick)
                .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Icon(icon, contentDescription = null, tint = tint, modifier = Modifier.size(17.dp))
        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(1.dp)) {
            Text(title, color = AppText, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
            Text(
                detail,
                color = AppTextMuted,
                fontSize = 11.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
        Text("Buka", color = LoginAccent, fontSize = 11.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
internal fun AdminTaskTile(
    label: String,
    icon: ImageVector,
    accent: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    val nightAdmin = IsNightAdmin
    val iconTint =
        if (nightAdmin)
            when (accent) {
                TaskAmber -> Color(0xFFB79A62)
                TaskPurple -> Color(0xFF9D8FAE)
                TaskIndigo -> Color(0xFF919CB8)
                TaskTeal -> Color(0xFF789E94)
                TaskGreen -> Color(0xFF86A585)
                TaskBlue -> Color(0xFF829DAF)
                TaskCoral -> Color(0xFFC1877F)
                TaskRose -> Color(0xFFB88498)
                else -> Color(0xFF87A790)
            }
        else accent
    val iconBackground = iconTint.copy(alpha = if (nightAdmin) 0.20f else 0.22f)

    Column(
        modifier =
            modifier
                .height(86.dp)
                .clip(RoundedCornerShape(16.dp))
                .clickable(onClick = onClick)
                .padding(horizontal = 3.dp, vertical = 7.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Box(
            modifier =
                Modifier.size(42.dp).clip(RoundedCornerShape(13.dp)).background(iconBackground),
            contentAlignment = Alignment.Center,
        ) {
            Icon(icon, contentDescription = null, tint = iconTint, modifier = Modifier.size(21.dp))
        }
        Text(
            label,
            color = AppText,
            fontSize = 12.sp,
            lineHeight = 14.sp,
            fontWeight = FontWeight.Medium,
            maxLines = 2,
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Ellipsis,
        )
    }
}
