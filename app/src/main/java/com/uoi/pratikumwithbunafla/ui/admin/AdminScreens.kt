package com.uoi.pratikumwithbunafla.ui.admin

import android.net.Uri
import androidx.core.content.FileProvider
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Inbox
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.rounded.NightsStay
import androidx.compose.material.icons.rounded.WbSunny
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.filled.ReportProblem
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material.icons.filled.SwitchAccount
import androidx.compose.material.icons.rounded.History
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.TaskAlt
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.LocalTextStyle
import androidx.compose.ui.text.TextStyle
import com.uoi.pratikumwithbunafla.ui.theme.AppBorder
import com.uoi.pratikumwithbunafla.ui.theme.AppBackground
import com.uoi.pratikumwithbunafla.R
import com.uoi.pratikumwithbunafla.ui.theme.AppSurface
import com.uoi.pratikumwithbunafla.ui.theme.AppSurfaceMuted
import com.uoi.pratikumwithbunafla.ui.theme.AppText
import com.uoi.pratikumwithbunafla.ui.theme.AppTextMuted
import com.uoi.pratikumwithbunafla.ui.theme.WasteBlue
import com.uoi.pratikumwithbunafla.ui.theme.WasteGreen
import com.uoi.pratikumwithbunafla.ui.theme.WasteGreenDark
import com.uoi.pratikumwithbunafla.ui.theme.WasteMint
import com.uoi.pratikumwithbunafla.ui.theme.WasteNavy
import com.uoi.pratikumwithbunafla.ui.theme.WasteOrange
import com.uoi.pratikumwithbunafla.ui.theme.WastePurple
import com.uoi.pratikumwithbunafla.ui.theme.WasteRed
import com.uoi.pratikumwithbunafla.ui.theme.WasteTeal
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch
import com.uoi.pratikumwithbunafla.data.model.*
import com.uoi.pratikumwithbunafla.data.remote.SmartWasteApi
import com.uoi.pratikumwithbunafla.domain.WasteFlow
import com.uoi.pratikumwithbunafla.ui.components.*
import com.uoi.pratikumwithbunafla.ui.theme.InterFont
import com.uoi.pratikumwithbunafla.ui.theme.DarkSmartWastePalette
import com.uoi.pratikumwithbunafla.ui.theme.LightSmartWastePalette
import com.uoi.pratikumwithbunafla.ui.theme.LocalSmartWasteAppearance
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import java.io.File

private val LoginInk: Color
    @Composable get() = MaterialTheme.colorScheme.onBackground
private val LoginMuted: Color
    @Composable get() = MaterialTheme.colorScheme.onSurfaceVariant
private val LoginAccent: Color
    @Composable get() = MaterialTheme.colorScheme.primary
private val TaskAmber = Color(0xFFB86B1D)
private val TaskBlue = Color(0xFF456FB5)
private val TaskPurple = Color(0xFF7952A0)
private val TaskTeal = Color(0xFF237E7B)
private val TaskGreen = Color(0xFF43814C)
private val TaskCoral = Color(0xFFC25556)
private val TaskIndigo = Color(0xFF535DAA)
private val TaskRose = Color(0xFFA64F75)

@Composable
private fun ScrollableDialogContent(
    spacing: Dp = 6.dp,
    content: @Composable ColumnScope.() -> Unit
) {
    val maxHeight = (LocalConfiguration.current.screenHeightDp.dp - 300.dp)
        .coerceIn(120.dp, 360.dp)
    Column(
        modifier = Modifier.heightIn(max = maxHeight).verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(spacing),
        content = content
    )
}

@Composable
private fun AnimatedAdminPageBackground() {
    val dark = LocalSmartWasteAppearance.current.darkMode
    val background by animateColorAsState(
        targetValue = if (dark) DarkSmartWastePalette.background else LightSmartWastePalette.background,
        animationSpec = tween(durationMillis = 820, easing = FastOutSlowInEasing),
        label = "admin-page-background"
    )
    Box(Modifier.fillMaxSize().background(background))
}

@Composable
private fun AdminThemeTransitionWave() {
    val dark = LocalSmartWasteAppearance.current.darkMode
    val modeProgress by animateFloatAsState(
        targetValue = if (dark) 1f else 0f,
        animationSpec = tween(durationMillis = 860, easing = FastOutSlowInEasing),
        label = "admin-theme-wave"
    )
    val waveProgress = if (dark) modeProgress else 1f - modeProgress

    Canvas(Modifier.fillMaxSize()) {
        val waveStrength = sin(waveProgress * PI).toFloat().coerceIn(0f, 1f)
        if (waveStrength > 0.015f) {
            val center = Offset(size.width * 0.96f, size.height * 0.08f)
            val radius = size.maxDimension * (0.04f + waveProgress * 1.18f)
            val tint = if (dark) Color(0xFFB8D2B9) else Color(0xFF789D7B)
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        tint.copy(alpha = waveStrength * 0.19f),
                        tint.copy(alpha = waveStrength * 0.07f),
                        Color.Transparent
                    ),
                    center = center,
                    radius = radius
                ),
                radius = radius,
                center = center
            )
            drawCircle(
                color = tint.copy(alpha = waveStrength * 0.30f),
                radius = radius * 0.82f,
                center = center,
                style = Stroke(width = 18.dp.toPx())
            )
            drawCircle(
                color = tint.copy(alpha = waveStrength * 0.62f),
                radius = radius * 0.82f,
                center = center,
                style = Stroke(width = 1.6.dp.toPx())
            )
        }
    }
}

@Composable
private fun AdminPage(
    current: String,
    account: UserAccount,
    onNavigate: (String) -> Unit,
    onLogout: () -> Unit,
    isInitialLoading: Boolean = false,
    isRefreshing: Boolean = false,
    onRefresh: () -> Unit = {},
    bottomBar: (@Composable () -> Unit)? = null,
    backgroundContent: @Composable BoxScope.() -> Unit = {},
    fullBleedContent: (@Composable ColumnScope.() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val horizontalPadding = when {
        screenWidth < 350.dp -> 14.dp
        screenWidth >= 600.dp -> 28.dp
        else -> 20.dp
    }
    Box(modifier = Modifier.fillMaxSize()) {
        val isAdmin = account.role == UserRole.ADMIN
        if (isAdmin) AnimatedAdminPageBackground()
        backgroundContent()
        if (isAdmin) AdminThemeTransitionWave()
        CompositionLocalProvider(
            LocalTextStyle provides LocalTextStyle.current.merge(
                TextStyle(
                    fontFamily = InterFont
                )
            )
        ) {
            AnimatedVisibility(
                visible = true,
                modifier = Modifier.fillMaxSize(),
                enter = fadeIn(tween(260)) + slideInVertically(
                    initialOffsetY = { it / 36 },
                    animationSpec = tween(260)
                )
            ) {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Box(
                        modifier = Modifier.weight(1f).fillMaxWidth(),
                        contentAlignment = Alignment.TopCenter
                    ) {
                        Column(
                            modifier = Modifier.widthIn(max = 720.dp).fillMaxHeight().fillMaxWidth()
                                .windowInsetsPadding(WindowInsets.statusBars)
                                .verticalScroll(rememberScrollState())
                        ) {
                            Box(
                                modifier = Modifier.fillMaxWidth()
                                    .padding(horizontal = horizontalPadding, vertical = 11.dp)
                            ) {
                                AdminTopBar(
                                    account = account,
                                    onLogout = onLogout,
                                    onOpenProfile = { onNavigate("profile") },
                                    isRefreshing = isRefreshing,
                                    onRefresh = onRefresh
                                )
                            }
                            if (isInitialLoading) {
                                AdminLoadingState()
                            } else {
                                fullBleedContent?.invoke(this)
                                Column(
                                    modifier = Modifier.fillMaxWidth()
                                        .padding(horizontal = horizontalPadding)
                                        .padding(top = 11.dp, bottom = 11.dp)
                                ) {
                                    content()
                                    Spacer(modifier = Modifier.height(12.dp))
                                }
                            }
                        }
                    }
                    when {
                        bottomBar != null -> bottomBar()
                        account.role == UserRole.ADMIN -> AdminNavigationBar(current, onNavigate)
                    }
                }
            }
        }
    }
}

@Composable
private fun AdminSectionHeading(eyebrow: String, title: String, subtitle: String) {
    val compact = LocalConfiguration.current.screenWidthDp < 360
    Column(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 2.dp, vertical = 4.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Text(
            eyebrow,
            color = LoginAccent,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp
        )
        Text(
            title,
            color = LoginInk,
            fontSize = if (compact) 20.sp else 22.sp,
            lineHeight = if (compact) 25.sp else 28.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            subtitle,
            color = LoginMuted,
            fontSize = 12.sp,
            lineHeight = 18.sp,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun AdminLoadingState() {
    Column(
        modifier = Modifier.fillMaxWidth().padding(vertical = 44.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(24.dp),
            color = LoginAccent,
            strokeWidth = 2.dp
        )
        Text("Memuat ruang kerja…", color = AppText, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
        Text("Mengambil data terbaru dari server", color = AppTextMuted, fontSize = 11.sp)
    }
}

@Composable
private fun OperationalMetric(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier.clip(RoundedCornerShape(12.dp)).clickable(onClick = onClick)
            .padding(horizontal = 7.dp, vertical = 5.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Text(
            label,
            color = AppTextMuted,
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold,
            maxLines = 1
        )
        Text(value, color = LoginInk, fontSize = 15.sp, fontWeight = FontWeight.Bold, maxLines = 1)
    }
}

@Composable
private fun AdminTopBar(
    account: UserAccount,
    onLogout: () -> Unit,
    onOpenProfile: () -> Unit,
    isRefreshing: Boolean,
    onRefresh: () -> Unit
) {
    var profileMenuExpanded by remember(account.username) { mutableStateOf(false) }
    val appearance = LocalSmartWasteAppearance.current
    val compact = LocalConfiguration.current.screenWidthDp < 350
    val roleIconTint = if (appearance.darkMode) Color(0xFFA9B9AC) else Color(0xFF5D776C)
    val modeIconTint by animateColorAsState(
        targetValue = if (appearance.darkMode) Color(0xFFC2B48E) else Color(0xFF687F83),
        animationSpec = tween(520, easing = FastOutSlowInEasing),
        label = "admin-theme-icon-tint"
    )
    val modeIconSurface by animateColorAsState(
        targetValue = if (appearance.darkMode) Color(0xFF29372F) else Color(0xFFE3EBE5),
        animationSpec = tween(520, easing = FastOutSlowInEasing),
        label = "admin-theme-icon-surface"
    )
    val modeIconBorder by animateColorAsState(
        targetValue = if (appearance.darkMode) Color(0xFF405247) else Color(0xFFC6D4C9),
        animationSpec = tween(520, easing = FastOutSlowInEasing),
        label = "admin-theme-icon-border"
    )
    val roleIcon = if (account.role == UserRole.ADMIN) {
        Icons.Filled.AdminPanelSettings
    } else {
        Icons.Rounded.Person
    }
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                imageVector = roleIcon,
                contentDescription = null,
                tint = roleIconTint,
                modifier = Modifier.size(if (compact) 19.dp else 21.dp)
            )
            Text(
                text = account.role.label,
                color = AppText,
                fontSize = if (compact) 15.sp else 16.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        PetugasRefreshButton(isRefreshing, onRefresh)
        Box {
            IconButton(onClick = { profileMenuExpanded = true }, modifier = Modifier.size(if (compact) 38.dp else 42.dp)) {
                UserAvatar(account.name, modifier = Modifier.size(if (compact) 30.dp else 34.dp))
            }
            DropdownMenu(
                expanded = profileMenuExpanded,
                onDismissRequest = { profileMenuExpanded = false }) {
                Column(
                    modifier = Modifier.width(220.dp).padding(horizontal = 14.dp, vertical = 10.dp),
                    verticalArrangement = Arrangement.spacedBy(3.dp)
                ) {
                    Text("Akun aktif", color = AppTextMuted, fontSize = 11.sp)
                    Text(
                        account.name,
                        color = AppText,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        "@${account.username} · ${account.role.label}",
                        color = LoginMuted,
                        fontSize = 11.sp
                    )
                }
                if (account.role == UserRole.ADMIN) {
                    DropdownMenuItem(
                        text = { Text("Pengaturan profil") },
                        leadingIcon = { Icon(Icons.Rounded.Person, contentDescription = null) },
                        onClick = {
                            profileMenuExpanded = false
                            onOpenProfile()
                        }
                    )
                }
                DropdownMenuItem(
                    text = { Text(if (account.role == UserRole.ADMIN) "Pindah ke akun Petugas" else "Pindah akun") },
                    leadingIcon = { Icon(Icons.Filled.SwitchAccount, contentDescription = null) },
                    onClick = {
                        profileMenuExpanded = false
                        onLogout()
                    }
                )
            }
        }
        Spacer(modifier = Modifier.width(if (compact) 2.dp else 6.dp))
        IconButton(onClick = appearance.onToggleTheme, modifier = Modifier.size(if (compact) 36.dp else 40.dp)) {
            Box(
                modifier = Modifier.size(if (compact) 30.dp else 34.dp)
                    .clip(CircleShape)
                    .background(modeIconSurface)
                    .border(1.dp, modeIconBorder, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Crossfade(
                    targetState = appearance.darkMode,
                    animationSpec = tween(380),
                    label = "admin-theme-icon"
                ) { dark ->
                    Icon(
                        imageVector = if (dark) Icons.Rounded.WbSunny else Icons.Rounded.NightsStay,
                        contentDescription = if (dark) "Aktifkan mode terang" else "Aktifkan mode gelap",
                        tint = modeIconTint,
                        modifier = Modifier.size(if (compact) 16.dp else 18.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun AdminNavigationBar(
    current: String,
    onNavigate: (String) -> Unit
) {
    val destinations = listOf(
        Triple("dashboard", "Beranda", Icons.Filled.Home),
        Triple("reports", "Laporan", Icons.AutoMirrored.Filled.Assignment),
        Triple("ops", "Petugas", Icons.Filled.Build),
        Triple("stats", "Statistik", Icons.Filled.BarChart)
    )
    FloatingRoleNavigationBar(current, destinations, onNavigate)
}

@Composable
private fun FloatingRoleNavigationBar(
    current: String,
    destinations: List<Triple<String, String, ImageVector>>,
    onNavigate: (String) -> Unit
) {
    val darkMode = LocalSmartWasteAppearance.current.darkMode
    val selectedIndex = destinations.indexOfFirst { it.first == current }.coerceAtLeast(0)
    val barColor by animateColorAsState(
        targetValue = if (darkMode) Color(0xFF14231B) else Color(0xFF244638),
        animationSpec = tween(460, easing = FastOutSlowInEasing),
        label = "role-navbar-surface"
    )
    val activeColor by animateColorAsState(
        targetValue = if (darkMode) Color(0xFF89C965) else Color(0xFFA5DA73),
        animationSpec = tween(460, easing = FastOutSlowInEasing),
        label = "role-navbar-active"
    )
    val inactiveColor = if (darkMode) Color(0xFFB4C1B8) else Color(0xFFD5E0D8)
    val navigationBarInset = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()

    Column(modifier = Modifier.fillMaxWidth()) {
        BoxWithConstraints(
            modifier = Modifier.fillMaxWidth()
                .padding(top = 5.dp)
                .height(82.dp)
        ) {
        val horizontalInset = 24.dp
        val itemWidth = (maxWidth - horizontalInset - horizontalInset) / destinations.size
        val targetCenter = horizontalInset + itemWidth * (selectedIndex + 0.5f)
        val activeCenter by animateDpAsState(
            targetValue = targetCenter,
            animationSpec = tween(520, easing = FastOutSlowInEasing),
            label = "role-navbar-indicator-position"
        )

        Canvas(
            modifier = Modifier.align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(64.dp)
        ) {
            val notchHalfWidth = 34.dp.toPx()
            val notchDepth = 30.dp.toPx()
            val centerX = activeCenter.toPx().coerceIn(
                notchHalfWidth,
                size.width - notchHalfWidth
            )
            val notchStart = centerX - notchHalfWidth
            val notchEnd = centerX + notchHalfWidth
            val path = Path().apply {
                moveTo(0f, 0f)
                lineTo(notchStart, 0f)
                cubicTo(
                    notchStart + 7.dp.toPx(), 0f,
                    centerX - notchHalfWidth * 0.68f, notchDepth,
                    centerX, notchDepth
                )
                cubicTo(
                    centerX + notchHalfWidth * 0.68f, notchDepth,
                    notchEnd - 7.dp.toPx(), 0f,
                    notchEnd, 0f
                )
                lineTo(size.width, 0f)
                lineTo(size.width, size.height)
                lineTo(0f, size.height)
                close()
            }
            drawPath(path, barColor)
        }

        Row(
            modifier = Modifier.align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(64.dp)
                .padding(horizontal = horizontalInset),
            verticalAlignment = Alignment.CenterVertically
        ) {
            destinations.forEachIndexed { index, (route, label, icon) ->
                val selected = index == selectedIndex
                Box(
                    modifier = Modifier.weight(1f).fillMaxHeight()
                        .clickable { onNavigate(route) },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = label,
                        tint = if (selected) Color.Transparent else inactiveColor,
                        modifier = Modifier.size(22.dp)
                    )
                }
            }
        }

        Box(
            modifier = Modifier.align(Alignment.TopStart)
                .offset(x = activeCenter - 25.dp)
                .size(50.dp)
                .shadow(8.dp, CircleShape)
                .background(activeColor, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = destinations[selectedIndex].third,
                contentDescription = null,
                tint = barColor,
                modifier = Modifier.size(22.dp)
            )
        }
        }
        Spacer(
            modifier = Modifier.fillMaxWidth()
                .height(navigationBarInset)
                .background(barColor)
        )
    }
}

@Composable
private fun DashboardHeroBanner(
    summary: AdminDashboardSummary,
    pendingProofCount: Int,
    onClick: (String) -> Unit
) {
    val darkMode = LocalSmartWasteAppearance.current.darkMode
    val bannerSky = if (darkMode) {
        listOf(Color(0xFF173B31), Color(0xFF1D513C), Color(0xFF315F45))
    } else {
        listOf(Color(0xFF175A42), Color(0xFF21674A), Color(0xFF477E5B))
    }
    val bannerText = Color(0xFFF5F4E8)
    val bannerMuted = Color(0xFFE2E9D9)
    val focusHeading: String
    val focusDetail: String
    val focusAction: String
    val focusIcon: ImageVector
    val focusColor: Color
    val focusRoute: String
    when {
        summary.hazardAlerts > 0 -> {
            focusHeading = "Laporan berisiko"
            focusDetail = "${summary.hazardAlerts} laporan B3 perlu perhatian"
            focusAction = "Tinjau laporan"
            focusIcon = Icons.Filled.ReportProblem
            focusColor = Color(0xFFFFD28A)
            focusRoute = "status:semua"
        }
        pendingProofCount > 0 -> {
            focusHeading = "Bukti petugas menunggu"
            focusDetail = "$pendingProofCount hasil kerja perlu diperiksa"
            focusAction = "Tinjau kinerja"
            focusIcon = Icons.Filled.CheckCircle
            focusColor = Color(0xFFB9DED0)
            focusRoute = "ops:catatan"
        }
        summary.waiting > 0 -> {
            focusHeading = "Menunggu verifikasi"
            focusDetail = "${summary.waiting} laporan perlu diperiksa"
            focusAction = "Buka antrean"
            focusIcon = Icons.Filled.Inbox
            focusColor = Color(0xFFFFD28A)
            focusRoute = "reports"
        }
        summary.assigned > 0 -> {
            focusHeading = "Sedang di lapangan"
            focusDetail = "${summary.assigned} laporan ditangani petugas"
            focusAction = "Pantau penugasan"
            focusIcon = Icons.Filled.LocalShipping
            focusColor = Color(0xFFB9DED0)
            focusRoute = "ops:petugas"
        }
        summary.processing > 0 -> {
            focusHeading = "Dalam penanganan"
            focusDetail = "${summary.processing} laporan sedang diproses"
            focusAction = "Lihat progres"
            focusIcon = Icons.Filled.Sync
            focusColor = Color(0xFFB9DED0)
            focusRoute = "reports"
        }
        summary.totalReports == 0 -> {
            focusHeading = "Belum ada laporan"
            focusDetail = "Laporan warga akan tampil di sini"
            focusAction = "Buka laporan"
            focusIcon = Icons.Filled.Inbox
            focusColor = Color(0xFFE2E9D9)
            focusRoute = "reports"
        }
        summary.completed > 0 -> {
            focusHeading = "Pekerjaan tuntas"
            focusDetail = "${summary.completed} laporan telah selesai"
            focusAction = "Lihat laporan selesai"
            focusIcon = Icons.Filled.CheckCircle
            focusColor = Color(0xFFB9DED0)
            focusRoute = "status:selesai"
        }
        else -> {
            focusHeading = "Semua laporan terpantau"
            focusDetail = "${summary.totalReports} laporan tercatat"
            focusAction = "Buka seluruh laporan"
            focusIcon = Icons.Filled.Inbox
            focusColor = Color(0xFFE2E9D9)
            focusRoute = "status:semua"
        }
    }

    Box(
        modifier = Modifier.fillMaxWidth()
            .height(340.dp)
            .clip(HeroBottomCurveShape)
            .background(Brush.horizontalGradient(bannerSky))
            .clickable { onClick(focusRoute) }
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        *heroSectionBlendStops(AppBackground, fadeEnd = 0.42f)
                    )
                )
        )

        val illustration = Modifier.align(Alignment.BottomEnd)
            .height(340.dp)
            .aspectRatio(260f / 280f)
        Image(
            painter = painterResource(R.drawable.hero_recycling_crew),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = illustration
                .graphicsLayer { compositingStrategy = CompositingStrategy.Offscreen }
                .drawWithContent {
                    drawContent()
                    drawRect(
                        brush = Brush.horizontalGradient(*heroFadeStops(0f, 0.23f)),
                        blendMode = BlendMode.DstIn
                    )
                }
        )

        Column(
            modifier = Modifier.fillMaxWidth(0.60f)
                .align(Alignment.CenterStart)
                .padding(start = 20.dp, top = 34.dp, bottom = 18.dp),
            verticalArrangement = Arrangement.spacedBy(11.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(7.dp),
                modifier = Modifier.clip(RoundedCornerShape(50.dp))
                    .background(Color.White.copy(alpha = 0.14f))
                    .padding(horizontal = 10.dp, vertical = 6.dp)
            ) {
                Icon(Icons.Filled.Inbox, contentDescription = null, tint = bannerText, modifier = Modifier.size(14.dp))
                Text(
                    "RINGKASAN LAPORAN",
                    color = bannerText,
                    fontSize = 9.sp,
                    letterSpacing = 0.8.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(9.dp)) {
                Text(
                    summary.totalReports.toString(),
                    color = bannerText,
                    fontSize = 48.sp,
                    lineHeight = 52.sp,
                    fontWeight = FontWeight.Bold
                )
                Column(verticalArrangement = Arrangement.spacedBy(1.dp)) {
                    Text("laporan", color = bannerText, fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
                    Text("terpantau", color = bannerMuted, fontSize = 10.sp, fontWeight = FontWeight.Medium)
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(7.dp),
                modifier = Modifier.clip(RoundedCornerShape(50.dp))
                    .background(Color.White.copy(alpha = 0.13f))
                    .padding(horizontal = 10.dp, vertical = 7.dp)
            ) {
                Icon(
                    Icons.Filled.LocalShipping,
                    contentDescription = null,
                    tint = Color(0xFFD8E7C8),
                    modifier = Modifier.size(15.dp)
                )
                Text(
                    "${summary.totalWeightKg.pretty()} kg",
                    color = bannerText,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
                Text("total tercatat", color = bannerMuted, fontSize = 10.sp, fontWeight = FontWeight.Medium)
            }
            Row(
                modifier = Modifier.fillMaxWidth()
                    .clip(RoundedCornerShape(17.dp))
                    .background(Color(0xFF103F30).copy(alpha = 0.72f))
                    .clickable { onClick(focusRoute) }
                    .padding(horizontal = 10.dp, vertical = 9.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier.size(32.dp).clip(CircleShape)
                        .background(focusColor.copy(alpha = 0.19f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(focusIcon, contentDescription = null, tint = focusColor, modifier = Modifier.size(17.dp))
                }
                Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
                    Text(
                        focusHeading,
                        color = bannerText,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        focusDetail,
                        color = bannerMuted,
                        fontSize = 9.sp,
                        lineHeight = 12.sp,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        "$focusAction  ›",
                        color = focusColor,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

private fun heroFadeStops(start: Float, end: Float): Array<Pair<Float, Color>> =
    (0..32).map { step ->
        val position = step / 32f
        val progress = ((position - start) / (end - start)).coerceIn(0f, 1f)
        val smoothAlpha = progress * progress * (3f - 2f * progress)
        position to Color.White.copy(alpha = smoothAlpha)
    }.toTypedArray()

private fun heroSectionBlendStops(
    pageBackground: Color,
    fadeEnd: Float
): Array<Pair<Float, Color>> =
    (0..32).map { step ->
        val position = step / 32f
        val progress = (position / fadeEnd).coerceIn(0f, 1f)
        val easedProgress = progress * progress * (3f - 2f * progress)
        position to pageBackground.copy(alpha = 1f - easedProgress)
    }.toTypedArray()

private val HeroBottomCurveShape = androidx.compose.foundation.shape.GenericShape { size, _ ->
    moveTo(0f, 0f)
    lineTo(size.width, 0f)
    lineTo(size.width, size.height)
    quadraticTo(size.width * 0.5f, size.height * 0.68f, 0f, size.height)
    close()
}

@Composable
private fun AdminDashboardBackdrop(modifier: Modifier = Modifier) {
    val dark = LocalSmartWasteAppearance.current.darkMode
    val motion = rememberInfiniteTransition(label = "admin-dashboard-ambience")
    val breeze by motion.animateFloat(
        initialValue = -1f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 18000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "dashboard-breeze"
    )
    val lightPulse by motion.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 7600, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "dashboard-light-pulse"
    )

    Canvas(modifier) {
        val width = size.width
        val height = size.height
        val drift = breeze * 18.dp.toPx()
        val pulse = 0.5f + 0.5f * sin(lightPulse * 2f * PI).toFloat()
        val upperGlow = Offset(width * 0.88f + drift * 0.35f, height * 0.12f)
        val upperColor = if (dark) Color(0xFF82B99A) else Color(0xFFE7D6A8)
        val upperRadius = width * 0.56f
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(
                    upperColor.copy(alpha = (if (dark) 0.12f else 0.16f) + pulse * 0.025f),
                    upperColor.copy(alpha = if (dark) 0.045f else 0.055f),
                    Color.Transparent
                ),
                center = upperGlow,
                radius = upperRadius
            ),
            radius = upperRadius,
            center = upperGlow
        )

        val lowerGlow = Offset(width * 0.10f - drift * 0.24f, height * 0.88f)
        val lowerColor = if (dark) Color(0xFF4C8D78) else Color(0xFF74A889)
        val lowerRadius = width * 0.48f
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(lowerColor.copy(alpha = if (dark) 0.10f else 0.085f), Color.Transparent),
                center = lowerGlow,
                radius = lowerRadius
            ),
            radius = lowerRadius,
            center = lowerGlow
        )

        val leafTint = if (dark) Color(0xFF8FB69A) else Color(0xFF4D8664)
        val leaves = listOf(
            Triple(0.07f, 0.27f, 0.72f),
            Triple(0.91f, 0.39f, 0.55f),
            Triple(0.13f, 0.70f, 0.62f),
            Triple(0.86f, 0.77f, 0.48f)
        )
        leaves.forEachIndexed { index, (xFraction, yFraction, scale) ->
            val sway = sin(lightPulse * 2f * PI + index * 1.7f).toFloat()
            val center = Offset(
                width * xFraction + drift * (if (index % 2 == 0) 0.28f else -0.24f),
                height * yFraction + sway * 5.dp.toPx()
            )
            val leafLength = 17.dp.toPx() * scale
            val leafHeight = 7.dp.toPx() * scale
            val leaf = Path().apply {
                moveTo(center.x - leafLength * 0.52f, center.y)
                cubicTo(
                    center.x - leafLength * 0.16f, center.y - leafHeight,
                    center.x + leafLength * 0.30f, center.y - leafHeight * 0.72f,
                    center.x + leafLength * 0.52f, center.y - leafHeight * 0.08f
                )
                cubicTo(
                    center.x + leafLength * 0.24f, center.y + leafHeight * 0.80f,
                    center.x - leafLength * 0.25f, center.y + leafHeight * 0.68f,
                    center.x - leafLength * 0.52f, center.y
                )
                close()
            }
            val alpha = (if (dark) 0.10f else 0.095f) + pulse * 0.025f
            drawPath(leaf, leafTint.copy(alpha = alpha))
            drawLine(
                color = leafTint.copy(alpha = alpha * 0.78f),
                start = Offset(center.x - leafLength * 0.38f, center.y),
                end = Offset(center.x + leafLength * 0.38f, center.y - leafHeight * 0.04f),
                strokeWidth = 0.8.dp.toPx(),
                cap = StrokeCap.Round
            )
        }
    }
}

@Composable
fun AdminProfileScreen(
    account: UserAccount,
    onNavigate: (String) -> Unit,
    onLogout: () -> Unit,
    isInitialLoading: Boolean = false,
    isRefreshing: Boolean = false,
    onRefresh: () -> Unit = {}
) {
    AdminPage(
        current = "profile",
        account = account,
        onNavigate = onNavigate,
        onLogout = onLogout,
        isInitialLoading = isInitialLoading,
        isRefreshing = isRefreshing,
        onRefresh = onRefresh,
        backgroundContent = { AdminDashboardBackdrop(Modifier.fillMaxSize()) }
    ) {
        AccountProfileSettings(account, onLogout)
    }
}

@Composable
fun AdminDashboardScreen(
    summary: AdminDashboardSummary,
    reports: List<WasteRecord>,
    errorMessage: String,
    account: UserAccount,
    onNavigate: (String) -> Unit,
    onLogout: () -> Unit,
    pendingProofCount: Int = 0,
    isInitialLoading: Boolean = false,
    isRefreshing: Boolean = false,
    onRefresh: () -> Unit = {}
) {
    AdminPage(
        current = "dashboard",
        account = account,
        onNavigate = onNavigate,
        onLogout = onLogout,
        isInitialLoading = isInitialLoading,
        isRefreshing = isRefreshing,
        onRefresh = onRefresh,
        backgroundContent = { AdminDashboardBackdrop(Modifier.fillMaxSize()) },
        fullBleedContent = {
            DashboardHeroBanner(summary, pendingProofCount) { route ->
                onNavigate(route)
            }
        }
    ) {
        if (errorMessage.isNotBlank()) InlineError(errorMessage)
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(3.dp)) {
                Text("Ruang kerja Admin", color = AppText, fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
                Text("Laporan, petugas, dan pemantauan kinerja", color = AppTextMuted, fontSize = 11.sp)
            }
            Text(
                "Semua laporan →",
                modifier = Modifier.clip(RoundedCornerShape(10.dp))
                    .clickable { onNavigate("status:semua") }.padding(6.dp),
                color = LoginAccent,
                fontSize = 10.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(7.dp)
            ) {
                AdminTaskTile("Laporan", Icons.AutoMirrored.Filled.Assignment, TaskAmber, Modifier.weight(1f)) {
                    onNavigate("reports")
                }
                AdminTaskTile("Petugas", Icons.Filled.AdminPanelSettings, TaskPurple, Modifier.weight(1f)) {
                    onNavigate("ops:petugas")
                }
                AdminTaskTile("Statistik", Icons.Filled.BarChart, TaskIndigo, Modifier.weight(1f)) {
                    onNavigate("stats")
                }
                AdminTaskTile("Catatan", Icons.Filled.History, TaskTeal, Modifier.weight(1f)) {
                    onNavigate("ops:catatan")
                }
            }
        }
        AppCard(
            modifier = Modifier.fillMaxWidth(),
            containerColor = Color.White,
            borderColor = AppBorder,
            accentColor = LoginAccent
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(9.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    OperationalMetric(
                        "Total laporan",
                        summary.totalReports.toString(),
                        Modifier.weight(1f)
                    ) { onNavigate("reports") }
                    OperationalMetric(
                        "Menunggu",
                        summary.waiting.toString(),
                        Modifier.weight(1f)
                    ) { onNavigate("reports") }
                    OperationalMetric(
                        "Petugas aktif",
                        summary.activeWorkers.toString(),
                        Modifier.weight(1f)
                    ) { onNavigate("ops:petugas") }
                }
                when {
                    summary.hazardAlerts == 0 && pendingProofCount == 0 -> Text(
                        "Pantau progres tugas petugas dari bukti penyelesaian dan statistik.",
                        color = AppTextMuted,
                        fontSize = 11.sp,
                        lineHeight = 16.sp
                    )
                    else -> Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        if (summary.hazardAlerts > 0) {
                            DashboardPriorityAction(
                                title = "${summary.hazardAlerts} laporan berisiko",
                                detail = "Periksa sebelum ditangani petugas",
                                icon = Icons.Filled.ReportProblem,
                                tint = WasteRed
                            ) { onNavigate("status:semua") }
                        }
                        if (pendingProofCount > 0) {
                            DashboardPriorityAction(
                                title = "$pendingProofCount bukti petugas menunggu",
                                detail = "Tinjau hasil kerja sebelum disetujui",
                                icon = Icons.Filled.CheckCircle,
                                tint = WasteGreenDark
                            ) { onNavigate("ops:catatan") }
                        }
                    }
                }
            }
        }
        SectionTitle("Laporan terbaru", "Ringkasan laporan warga")
        val latest = reports.take(3)
        if (latest.isEmpty()) {
            EmptyAdminState("Belum ada laporan dari warga.")
        } else {
            AppCard(
                modifier = Modifier.fillMaxWidth(),
                containerColor = Color.White,
                borderColor = AppBorder
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 13.dp, vertical = 4.dp)
                ) {
                    latest.forEach { report ->
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    report.code,
                                    color = AppText,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    "${report.type} · ${report.location}",
                                    color = AppTextMuted,
                                    fontSize = 11.sp,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                            StatusPill(report.status, reportAccent(report.status))
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun DashboardPriorityAction(
    title: String,
    detail: String,
    icon: ImageVector,
    tint: Color,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(icon, contentDescription = null, tint = tint, modifier = Modifier.size(17.dp))
        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(1.dp)) {
            Text(title, color = AppText, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
            Text(detail, color = AppTextMuted, fontSize = 10.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
        }
        Text("Buka", color = LoginAccent, fontSize = 10.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun AdminTaskTile(
    label: String,
    icon: ImageVector,
    accent: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier.height(78.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 3.dp, vertical = 7.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Box(
            modifier = Modifier.size(32.dp)
                .shadow(2.dp, CircleShape)
                .clip(CircleShape)
                .background(accent),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = null, tint = Color.White, modifier = Modifier.size(17.dp))
        }
        Text(
            label,
            color = AppText,
            fontSize = 11.sp,
            lineHeight = 12.sp,
            fontWeight = FontWeight.Medium,
            maxLines = 2,
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun AdminVerificationScreen(
    reports: List<WasteRecord>,
    archivedReports: List<ArchivedWasteRecord>,
    selectedStatus: String?,
    showAllReports: Boolean,
    showingArchive: Boolean,
    errorMessage: String,
    account: UserAccount,
    onOpenPhoto: (String) -> Unit,
    onArchiveViewChange: (Boolean) -> Unit,
    onArchiveReport: suspend (WasteRecord) -> Unit,
    onRestoreArchivedReport: suspend (WasteRecord) -> Unit,
    onReportViewChange: (Boolean) -> Unit,
    onFilterChange: (String?) -> Unit,
    onNavigate: (String) -> Unit,
    onLogout: () -> Unit,
    isInitialLoading: Boolean = false,
    isRefreshing: Boolean = false,
    onRefresh: () -> Unit = {}
) {
    var archivingReport by remember { mutableStateOf<WasteRecord?>(null) }
    var restoringReport by remember { mutableStateOf<WasteRecord?>(null) }
    var archiveActionError by remember { mutableStateOf("") }
    var archiveActionRunning by remember { mutableStateOf(false) }
    var reportSearch by rememberSaveable(showingArchive) { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val waitingReports = reports.filter { it.status == WasteFlow.WAITING }
    val activeFilter = if (showingArchive) "arsip" else when {
        !showAllReports || selectedStatus == WasteFlow.WAITING -> "baru"
        selectedStatus == null -> "semua"
        selectedStatus in setOf(WasteFlow.PROCESSING, WasteFlow.ASSIGNED, WasteFlow.TRANSPORTED) -> "proses"
        selectedStatus == WasteFlow.DONE -> "selesai"
        else -> "ditolak"
    }
    val visibleReports = if (showingArchive) emptyList() else when {
        !showAllReports -> waitingReports
        selectedStatus == null -> reports
        selectedStatus in setOf(WasteFlow.PROCESSING, WasteFlow.ASSIGNED, WasteFlow.TRANSPORTED) ->
            reports.filter { WasteFlow.isInProgress(it.status) }
        else -> reports.filter { it.status == selectedStatus }
    }
    val searchQuery = reportSearch.trim().lowercase()
    fun matchesSearch(report: WasteRecord): Boolean = searchQuery.isBlank() || listOf(
        report.code,
        report.type,
        report.location,
        report.description,
        report.reportedBy,
        report.workerName,
        WasteFlow.label(report.status)
    ).any { it.lowercase().contains(searchQuery) }
    val filteredReports = visibleReports.filter(::matchesSearch)
    val filteredArchivedReports = archivedReports.filter { matchesSearch(it.report) }
    val reportFilters = listOf(
        "baru" to "Baru",
        "proses" to "Proses",
        "selesai" to "Selesai",
        "ditolak" to "Ditolak",
        "semua" to "Semua"
    )
    AdminPage(
        "reports", account, onNavigate, onLogout,
        isInitialLoading = isInitialLoading,
        isRefreshing = isRefreshing,
        onRefresh = onRefresh
    ) {
        AdminSectionHeading(
            "INBOX • ADMIN",
            "Laporan warga",
            "Lihat laporan yang masuk dan pantau perkembangannya."
        )
        if (errorMessage.isNotBlank()) InlineError(errorMessage)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            TextButton(
                onClick = {
                    archiveActionError = ""
                    onArchiveViewChange(!showingArchive)
                },
                colors = ButtonDefaults.textButtonColors(contentColor = LoginAccent)
            ) {
                Text(
                    if (showingArchive) "← Kembali ke laporan" else "Arsip tersimpan · ${archivedReports.size}",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        if (!showingArchive) {
            Row(
                modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                reportFilters.forEach { (filter, label) ->
                    val active = activeFilter == filter
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
                        contentPadding = androidx.compose.foundation.layout.PaddingValues(
                            horizontal = 11.dp,
                            vertical = 6.dp
                        ),
                        colors = ButtonDefaults.textButtonColors(
                            contentColor = if (active) LoginAccent else AppTextMuted,
                            containerColor = if (active) WasteBlue else AppSurfaceMuted
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(label, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        OutlinedTextField(
            value = reportSearch,
            onValueChange = { reportSearch = it.take(100) },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Cari kode, jenis, lokasi, atau petugas") },
            singleLine = true,
            textStyle = TextStyle(fontFamily = InterFont, fontSize = 12.sp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = LoginAccent,
                unfocusedBorderColor = AppBorder,
                focusedContainerColor = AppSurface,
                unfocusedContainerColor = AppSurface
            )
        )

        SectionTitle(
            if (showingArchive) "${filteredArchivedReports.size} laporan tersimpan"
            else "${filteredReports.size} ${when (activeFilter) {
                    "baru" -> "laporan baru"
                    "proses" -> "laporan diproses"
                    "selesai" -> "laporan selesai"
                    "ditolak" -> "laporan ditolak"
                    else -> "laporan"
                }}",
            if (showingArchive) "Laporan tetap tersimpan di sini dan bisa dipulihkan kapan saja."
            else when (activeFilter) {
                    "baru" -> "Laporan baru menunggu ditangani petugas."
                    "proses" -> "Mencakup laporan ditugaskan dan sedang diangkut."
                    "selesai" -> "Laporan yang sudah selesai diterima di TPA."
                    "ditolak" -> "Laporan yang ditolak setelah ditinjau."
                    else -> "Pantau seluruh laporan warga."
                }
        )
        when {
            showingArchive && filteredArchivedReports.isEmpty() && reportSearch.isBlank() -> {
                EmptyAdminState("Arsip masih kosong.")
            }
            showingArchive && filteredArchivedReports.isEmpty() -> {
                EmptyAdminState("Tidak ada arsip yang cocok dengan pencarian.")
            }
            showingArchive -> {
                filteredArchivedReports.forEach { archived ->
                    AdminReportCard(
                        archived.report,
                        onOpenPhoto,
                        compact = true,
                        onRestore = { restoringReport = archived.report }
                    )
                    Text(
                        "Disimpan ${archived.archivedAt} · ${archived.archivedBy}",
                        color = AppTextMuted,
                        fontSize = 10.sp,
                        modifier = Modifier.padding(start = 12.dp, bottom = 5.dp)
                    )
                }
            }
            filteredReports.isEmpty() -> {
                EmptyAdminState(
                    when {
                        reportSearch.isNotBlank() -> "Tidak ada laporan yang cocok dengan pencarian."
                        showAllReports -> "Belum ada laporan pada status ini."
                        else -> "Semua laporan baru sudah ditinjau."
                    }
                )
            }
            else -> {
                filteredReports.forEach { report ->
                    AdminReportCard(
                        report,
                        onOpenPhoto,
                        compact = true,
                        onArchive = { archivingReport = report }
                    )
                }
            }
        }
    }

    archivingReport?.let { report ->
        AlertDialog(
            onDismissRequest = { if (!archiveActionRunning) archivingReport = null },
            title = { Text("Simpan laporan ke Arsip?", color = LoginInk, fontWeight = FontWeight.Bold) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        "${report.code} akan disembunyikan dari daftar utama. Data laporan, foto, riwayat, dan statistik tetap tersimpan; kamu bisa memulihkannya dari Arsip.",
                        color = AppTextMuted
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
                    }
                ) {
                    Text(if (archiveActionRunning) "Menyimpan…" else "Simpan ke Arsip", color = LoginAccent)
                }
            },
            dismissButton = {
                TextButton(
                    enabled = !archiveActionRunning,
                    onClick = { archivingReport = null }
                ) { Text("Batal", color = LoginMuted) }
            },
            containerColor = AppSurface
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
                        color = AppTextMuted
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
                    }
                ) {
                    Text(if (archiveActionRunning) "Memulihkan…" else "Pulihkan", color = LoginAccent)
                }
            },
            dismissButton = {
                TextButton(
                    enabled = !archiveActionRunning,
                    onClick = { restoringReport = null }
                ) { Text("Batal", color = LoginMuted) }
            },
            containerColor = AppSurface
        )
    }
}

@Composable
private fun AdminReportCard(
    report: WasteRecord,
    onOpenPhoto: (String) -> Unit,
    onReject: (() -> Unit)? = null,
    onAssign: (() -> Unit)? = null,
    compact: Boolean = false,
    assignmentLabel: String = "Tugaskan",
    onArchive: (() -> Unit)? = null,
    onRestore: (() -> Unit)? = null
) {
    var showDetails by rememberSaveable(report.id) { mutableStateOf(!compact) }
    val accent = reportAccent(report.status)
    val reportMeta = buildList {
        report.reportedBy.takeIf { it.isNotBlank() }?.let {
            add("Pelapor: ${it.reporterDisplayName()}")
        }
        report.reportedAt.takeIf { it.isNotBlank() }?.let {
            add(it.toDisplayDateTime())
        }
    }.joinToString("  ·  ")
    AppCard(
        modifier = Modifier.fillMaxWidth(),
        containerColor = Color.White,
        accentColor = accent,
        borderColor = AppBorder
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(7.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        report.code,
                        color = AppText,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                    if (reportMeta.isNotBlank()) {
                        Text(
                            reportMeta,
                            color = AppTextMuted,
                            fontSize = 10.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
                StatusPill(report.status, accent)
            }
            Text(
                "${report.type}${if (report.weightKg > 0) "  •  ${report.weightKg.pretty()} kg" else ""}",
                color = AppText, fontSize = 12.sp, fontWeight = FontWeight.SemiBold
            )
            if (report.location.isNotBlank() && report.location != "-") {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        Icons.Filled.Place,
                        contentDescription = null,
                        tint = LoginAccent,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        report.location.trim(),
                        color = AppTextMuted,
                        fontSize = 12.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
            if (showDetails) {
                if (report.hazardLevel.isNotBlank() && !report.hazardLevel.equals("RENDAH", true)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            Icons.Filled.ReportProblem,
                            contentDescription = null,
                            tint = WasteRed,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            "Bahaya ${report.hazardLevel}",
                            color = WasteRed,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
                val assignmentSummary = report.assignmentSummary()
                if (assignmentSummary.isNotBlank()) {
                    Text(
                        assignmentSummary,
                        color = LoginMuted,
                        fontSize = 10.sp,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                if (report.description.isNotBlank()) Text(
                    report.description,
                    color = AppTextMuted,
                    fontSize = 10.sp,
                    lineHeight = 15.sp,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
                if (!report.photoUrl.isNullOrBlank()) {
                    TextButton(onClick = { onOpenPhoto(report.photoUrl) }) {
                        Text(
                            "Lihat foto",
                            color = LoginAccent,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            } else if (compact) {
                TextButton(
                    onClick = { showDetails = true },
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(0.dp)
                ) {
                    Text(
                        "Lihat detail ↓",
                        color = LoginAccent,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            if (compact && showDetails) {
                TextButton(
                    onClick = { showDetails = false },
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(0.dp)
                ) {
                    Text(
                        "Tutup detail ↑",
                        color = LoginMuted,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            if (onReject != null || onAssign != null || onArchive != null || onRestore != null) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.weight(1f))
                    if (onReject != null) {
                        TextButton(
                            onClick = onReject,
                            colors = ButtonDefaults.textButtonColors(contentColor = WasteRed)
                        ) {
                            Text("Tolak", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                    if (onAssign != null) {
                        TextButton(
                            onClick = onAssign,
                            colors = ButtonDefaults.textButtonColors(contentColor = LoginAccent)
                        ) {
                            Text(assignmentLabel, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                    if (onArchive != null) {
                        TextButton(
                            onClick = onArchive,
                            colors = ButtonDefaults.textButtonColors(contentColor = LoginAccent)
                        ) {
                            Text("Arsipkan", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                    if (onRestore != null) {
                        TextButton(
                            onClick = onRestore,
                            colors = ButtonDefaults.textButtonColors(contentColor = LoginAccent)
                        ) {
                            Text("Pulihkan", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun AssignmentDialog(
    report: WasteRecord,
    workers: List<AdminWorker>,
    vehicles: List<WasteVehicle>,
    facilities: List<WasteFacility>,
    onDismiss: () -> Unit,
    onAssign: suspend (Int, Int, Int) -> Unit
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
    val canAssign = worker?.let(eligibleWorkers::contains) == true &&
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
                    fontSize = 11.sp
                )
                SelectionField(
                    "Petugas",
                    worker?.let { "${it.name} · ${it.zone.ifBlank { "tanpa zona" }}" },
                    eligibleWorkers,
                    { "${it.name} · ${it.zone.ifBlank { "tanpa zona" }}" }) { worker = it }
                SelectionField(
                    "Kendaraan",
                    vehicle?.let { "${it.plate} · sisa ${it.availableKg.pretty()} kg" },
                    eligibleVehicles,
                    { "${it.plate} · sisa ${it.availableKg.pretty()} kg" }) { vehicle = it }
                SelectionField(
                    "TPA tujuan",
                    facility?.let { "${it.name} · sisa ${it.remainingTon.pretty()} ton" },
                    eligibleFacilities,
                    { "${it.name} · sisa ${it.remainingTon.pretty()} ton" }) { facility = it }
                if (eligibleWorkers.isEmpty() || eligibleVehicles.isEmpty() || eligibleFacilities.isEmpty()) {
                    val unavailable = buildList {
                        if (eligibleWorkers.isEmpty()) add("petugas aktif yang memiliki akun Android")
                        if (eligibleVehicles.isEmpty()) add("kendaraan sehat dengan kapasitas cukup")
                        if (eligibleFacilities.isEmpty()) add("TPA dengan kapasitas cukup")
                    }.joinToString(", ")
                    Text(
                        "Belum bisa ditugaskan: tidak ada $unavailable. Periksa menu Operasional.",
                        color = WasteRed,
                        fontSize = 11.sp
                    )
                }
                if (saveError.isNotBlank()) InlineError(saveError)
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (canAssign && !saving) scope.launch {
                        saving = true
                        saveError = ""
                        try {
                            onAssign(worker!!.id, vehicle!!.id, facility!!.id)
                        } catch (cancelled: CancellationException) {
                            throw cancelled
                        } catch (error: Exception) {
                            saveError = error.message
                                ?: "Penugasan gagal disimpan. Periksa koneksi dan coba lagi."
                        } finally {
                            saving = false
                        }
                    }
                },
                enabled = canAssign && !saving
            ) {
                Text(
                    if (saving) "Menyimpan…" else "Tetapkan",
                    color = LoginAccent,
                    fontWeight = FontWeight.Bold
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss, enabled = !saving) {
                Text(
                    "Batal",
                    color = LoginMuted
                )
            }
        },
        containerColor = AppSurface
    )
}

@Composable
private fun <T> SelectionField(
    title: String,
    selectedLabel: String?,
    choices: List<T>,
    label: (T) -> String,
    onSelected: (T) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    Column {
        Text(title, color = LoginMuted, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
        Box {
            TextButton(
                onClick = { expanded = true },
                enabled = choices.isNotEmpty(),
                modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp)
                    .background(WasteBlue.copy(alpha = 0.56f), RoundedCornerShape(12.dp))
            ) {
                Text(
                    selectedLabel ?: "Pilih $title",
                    color = LoginInk,
                    fontSize = 11.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                choices.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(label(item), fontSize = 11.sp) },
                        onClick = { onSelected(item); expanded = false }
                    )
                }
            }
        }
    }
}

@Composable
private fun DeleteWorkerAccountDialog(
    worker: AdminWorker,
    replacementWorkers: List<AdminWorker>,
    onDismiss: () -> Unit,
    onDelete: suspend (AdminWorker, Int?) -> Unit
) {
    var selectedReplacementId by remember(worker.id) {
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
                    "${worker.name.displayPersonName()} tidak bisa login lagi. Profil dan riwayat kinerja tetap disimpan.",
                    color = AppTextMuted,
                    fontSize = 12.sp,
                    lineHeight = 17.sp
                )
                if (needsReplacement) {
                    Text(
                        "${worker.activeTasks} tugas aktif perlu dialihkan sebelum akun dihapus.",
                        color = AppText,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    if (replacementWorkers.isEmpty()) {
                        Text(
                            "Belum ada petugas lain yang aktif. Aktifkan atau tambahkan akun petugas pengganti terlebih dahulu.",
                            color = WasteRed,
                            fontSize = 11.sp
                        )
                    } else {
                        Box {
                            TextButton(onClick = { menuExpanded = true }, enabled = !deleting) {
                                Text(
                                    "Pengganti: ${selectedReplacement?.name?.displayPersonName() ?: "Pilih petugas"} ▾",
                                    color = LoginAccent
                                )
                            }
                            DropdownMenu(
                                expanded = menuExpanded,
                                onDismissRequest = { menuExpanded = false }
                            ) {
                                replacementWorkers.forEach { replacement ->
                                    DropdownMenuItem(
                                        text = {
                                            Text("${replacement.name.displayPersonName()} · @${replacement.username}")
                                        },
                                        onClick = {
                                            selectedReplacementId = replacement.id
                                            menuExpanded = false
                                        }
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
                colors = ButtonDefaults.textButtonColors(contentColor = WasteRed)
            ) {
                Text(if (deleting) "Menghapus…" else "Hapus akun", fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            TextButton(enabled = !deleting, onClick = onDismiss) {
                Text("Batal", color = LoginMuted)
            }
        },
        containerColor = AppSurface
    )
}

@Composable
fun AdminStatsScreen(
    stats: AdminStatistics,
    dailyReports: List<DailyReport>,
    dailyRangeDays: Int,
    errorMessage: String,
    account: UserAccount,
    onDailyRange: (Int) -> Unit,
    onNavigate: (String) -> Unit,
    onLogout: () -> Unit,
    isInitialLoading: Boolean = false,
    isRefreshing: Boolean = false,
    onRefresh: () -> Unit = {}
) {
    val periodCount = dailyReports.sumOf { it.totalWaste }
    val periodWeight = dailyReports.sumOf { it.weightKg }
    val periodB3 = dailyReports.sumOf { it.b3Alerts }
    val monthlyBars = stats.monthly.take(6).reversed()
    val maxMonthly = monthlyBars.maxOfOrNull { it.total }?.coerceAtLeast(1) ?: 1
    val maxTypeWeight = stats.byType.maxOfOrNull { it.weightKg }?.coerceAtLeast(1.0) ?: 1.0
    val chartColors =
        listOf(LoginAccent, WasteTeal, WastePurple, WasteOrange, WasteGreenDark, WasteNavy)

    AdminPage(
        "stats", account, onNavigate, onLogout,
        isInitialLoading = isInitialLoading,
        isRefreshing = isRefreshing,
        onRefresh = onRefresh
    ) {
        AdminSectionHeading(
            "ADMIN • STATISTIK",
            "Ringkasan dampak",
            "Lihat tren laporan, jenis sampah, dan pekerjaan yang selesai."
        )
        if (errorMessage.isNotBlank()) InlineError(errorMessage)
        SectionTitle("Rentang laporan selesai", "Pilih periode yang ingin dibandingkan")
        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            listOf(7, 30, 90).forEach { days ->
                TextButton(
                    onClick = { onDailyRange(days) },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = if (days == dailyRangeDays) LoginAccent else LoginMuted,
                        containerColor = if (days == dailyRangeDays) WasteBlue else AppSurface
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) { Text("$days hari", fontSize = 11.sp, fontWeight = FontWeight.Bold) }
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            SummaryCard(
                stats.completedReports.toString(),
                "Selesai total",
                WasteGreenDark,
                Modifier.weight(1f),
                icon = Icons.Filled.CheckCircle
            )
            SummaryCard(
                periodCount.toString(),
                "Selesai $dailyRangeDays hari",
                LoginAccent,
                Modifier.weight(1f),
                icon = Icons.AutoMirrored.Filled.Assignment
            )
        }
        AppCard(
            modifier = Modifier.fillMaxWidth(),
            containerColor = Color.White,
            borderColor = AppBorder,
            accentColor = WasteTeal
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 14.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        "Berat selesai",
                        color = AppTextMuted,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        "${periodWeight.pretty()} kg",
                        color = AppText,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        "Dalam ${dailyRangeDays} hari terakhir",
                        color = AppTextMuted,
                        fontSize = 11.sp
                    )
                }
                if (periodB3 > 0) {
                    Text(
                        "$periodB3 laporan B3",
                        color = WasteRed,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                } else {
                    Text(
                        "Aman",
                        color = WasteGreenDark,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        SectionTitle("Laporan per bulan", "Perbandingan enam bulan terakhir")
        if (monthlyBars.isEmpty()) EmptyAdminState("Belum ada data bulanan.") else {
            AppCard(
                modifier = Modifier.fillMaxWidth(),
                containerColor = Color.White,
                borderColor = AppBorder
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 13.dp),
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Text(
                        "Jumlah laporan",
                        color = AppTextMuted,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth().height(138.dp),
                        horizontalArrangement = Arrangement.spacedBy(5.dp),
                        verticalAlignment = Alignment.Bottom
                    ) {
                        monthlyBars.forEachIndexed { index, item ->
                            val ratio = item.total.toFloat() / maxMonthly
                            Column(
                                modifier = Modifier.weight(1f),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Bottom
                            ) {
                                Text(
                                    item.total.toString(),
                                    color = AppTextMuted,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth(0.58f)
                                        .height((22f + ratio * 67f).dp)
                                        .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                                        .background(chartColors[index % chartColors.size])
                                )
                                Spacer(modifier = Modifier.height(5.dp))
                                Text(
                                    shortMonth(item.period),
                                    color = AppTextMuted,
                                    fontSize = 11.sp,
                                    maxLines = 1
                                )
                            }
                        }
                    }
                }
            }
        }

        SectionTitle("Komposisi jenis sampah", "Perbandingan berdasarkan berat tercatat")
        if (stats.byType.isEmpty()) EmptyAdminState("Belum ada data jenis sampah.") else {
            AppCard(
                modifier = Modifier.fillMaxWidth(),
                containerColor = Color.White,
                borderColor = AppBorder
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    stats.byType.take(6).forEachIndexed { index, item ->
                        val accent = chartColors[index % chartColors.size]
                        Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    item.type,
                                    modifier = Modifier.weight(1f),
                                    color = AppText,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text("${item.total} laporan", color = AppTextMuted, fontSize = 11.sp)
                            }
                            DataProgress(item.weightKg.toFloat() / maxTypeWeight.toFloat(), accent)
                            Text(
                                "${item.weightKg.pretty()} kg",
                                color = accent,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }

        SectionTitle("Aktivitas harian", "Laporan selesai per tanggal")
        if (dailyReports.isEmpty()) {
            EmptyAdminState("Belum ada laporan selesai dalam rentang ini.")
        } else {
            AppCard(
                modifier = Modifier.fillMaxWidth(),
                containerColor = Color.White,
                borderColor = AppBorder
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 13.dp, vertical = 4.dp)
                ) {
                    dailyReports.take(10).forEach { item ->
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(vertical = 9.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                item.date,
                                modifier = Modifier.weight(1f),
                                color = AppText,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                buildString {
                                    append("${item.totalWaste} laporan · ${item.weightKg.pretty()} kg")
                                    if (item.b3Alerts > 0) append(" · B3 ${item.b3Alerts}")
                                },
                                color = if (item.b3Alerts > 0) WasteRed else LoginAccent,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AdminOperationsScreen(
    workers: List<AdminWorker>,
    completionReviews: List<TaskCompletionReview>,
    adminNotes: List<AdminNoteRecord>,
    archivedAdminNotes: List<ArchivedAdminNote>,
    errorMessage: String,
    account: UserAccount,
    onCreateWorker: suspend (String, String, String, String, String, String) -> Unit,
    onUpdateWorker: suspend (AdminWorker, Boolean) -> Unit,
    onDeleteWorker: suspend (AdminWorker, Int?) -> Unit,
    onOpenPhoto: (String) -> Unit,
    onReviewCompletion: suspend (TaskCompletionReview, String, String) -> Unit,
    onAddAdminNote: suspend (AdminNoteRecord, String) -> Unit,
    onArchiveAdminNote: suspend (AdminNoteRecord) -> Unit,
    onRestoreAdminNote: suspend (ArchivedAdminNote) -> Unit,
    onNavigate: (String) -> Unit,
    onLogout: () -> Unit,
    initialTab: String = "petugas",
    onTabSelected: (String) -> Unit,
    isInitialLoading: Boolean = false,
    isRefreshing: Boolean = false,
    onRefresh: () -> Unit = {}
) {
    var tab by rememberSaveable(account.role, initialTab) {
        mutableStateOf(initialTab.takeIf { it == "catatan" } ?: "petugas")
    }
    var adding by rememberSaveable { mutableStateOf(false) }
    var editingWorker by remember { mutableStateOf<AdminWorker?>(null) }
    var deletingWorker by remember { mutableStateOf<AdminWorker?>(null) }
    var revisionReview by remember { mutableStateOf<TaskCompletionReview?>(null) }
    var revisionNote by rememberSaveable { mutableStateOf("") }
    var revisionError by remember { mutableStateOf("") }
    var revisionSending by remember { mutableStateOf(false) }
    var processingCompletionId by remember { mutableStateOf<Int?>(null) }
    var showingNoteArchive by rememberSaveable { mutableStateOf(false) }
    var activeNoteSearch by rememberSaveable { mutableStateOf("") }
    var activeNoteFilter by rememberSaveable { mutableStateOf("semua") }
    var archiveSearch by rememberSaveable { mutableStateOf("") }
    var archivingAdminNote by remember { mutableStateOf<AdminNoteRecord?>(null) }
    var restoringAdminNote by remember { mutableStateOf<ArchivedAdminNote?>(null) }
    var writingAdminNote by remember { mutableStateOf<AdminNoteRecord?>(null) }
    var adminNoteDraft by rememberSaveable { mutableStateOf("") }
    var adminNoteError by remember { mutableStateOf("") }
    var adminNoteSending by remember { mutableStateOf(false) }
    var noteArchiveInProgress by remember { mutableStateOf(false) }
    var actionError by rememberSaveable { mutableStateOf("") }
    var workerSearch by rememberSaveable { mutableStateOf("") }
    var workerFilter by rememberSaveable { mutableStateOf("semua") }
    var updatingWorkerId by remember { mutableStateOf<Int?>(null) }
    val scope = rememberCoroutineScope()
    val workerSearchQuery = workerSearch.trim().lowercase()
    val workersNeedingAttention = workers.count { it.pendingConfirmations > 0 || it.activeTasks > 0 }
    val workerFilters = listOf(
        "semua" to "Semua · ${workers.size}",
        "aktif" to "Aktif · ${workers.count { it.active && it.hasAccount }}",
        "perhatian" to "Perhatian · $workersNeedingAttention",
        "tanpa-akun" to "Tanpa akun · ${workers.count { !it.hasAccount }}"
    )
    val visibleWorkers = workers.asSequence()
        .filter { worker ->
            workerSearchQuery.isBlank() || listOf(worker.name, worker.username, worker.zone)
                .any { it.lowercase().contains(workerSearchQuery) }
        }
        .filter { worker ->
            when (workerFilter) {
                "aktif" -> worker.active && worker.hasAccount
                "perhatian" -> worker.pendingConfirmations > 0 || worker.activeTasks > 0
                "tanpa-akun" -> !worker.hasAccount
                else -> true
            }
        }
        .sortedWith(
            compareByDescending<AdminWorker> { it.pendingConfirmations }
                .thenByDescending { it.activeTasks }
                .thenBy { it.name.lowercase() }
        )
        .toList()
    AdminPage(
        "ops", account, onNavigate, onLogout,
        isInitialLoading = isInitialLoading,
        isRefreshing = isRefreshing,
        onRefresh = onRefresh
    ) {
        val operationsTabs = listOf("petugas" to "Petugas", "catatan" to "Catatan Admin")
        AdminSectionHeading(
            if (tab == "catatan") "ADMIN • PEMANTAUAN" else "ADMIN • OPERASIONAL",
            if (tab == "catatan") "Catatan Admin" else "Kelola petugas",
            if (tab == "catatan") "Notifikasi bukti tugas dan catatan pekerjaan petugas."
            else "Atur akun petugas dan pantau pembagian tugas."
        )
        if (errorMessage.isNotBlank()) InlineError(errorMessage)
        if (actionError.isNotBlank()) InlineError(actionError)
        Row(
            modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            operationsTabs.forEach { (key, label) ->
                TextButton(
                    onClick = {
                        tab = key
                        onTabSelected(key)
                    },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = if (tab == key) LoginAccent else LoginMuted,
                        containerColor = if (tab == key) WasteBlue else AppSurface
                    ),
                    shape = RoundedCornerShape(13.dp)
                ) {
                    Text(
                        if (key == "catatan" && completionReviews.isNotEmpty()) {
                            "$label · ${completionReviews.size}"
                        } else label,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
        when (tab) {
            "petugas" -> {
                SectionTitle(
                    "Data petugas",
                    "Akun Android dibuat oleh admin, tidak melalui registrasi umum"
                )
                AppButton(
                    "＋ Tambah akun petugas",
                    { adding = true },
                    containerColor = LoginAccent
                )
                OutlinedTextField(
                    value = workerSearch,
                    onValueChange = { workerSearch = it.take(80) },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("Cari nama, username, atau zona") },
                    singleLine = true,
                    textStyle = TextStyle(fontFamily = InterFont, fontSize = 12.sp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = LoginAccent,
                        unfocusedBorderColor = AppBorder,
                        focusedContainerColor = AppSurface,
                        unfocusedContainerColor = AppSurface
                    )
                )
                Row(
                    modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    workerFilters.forEach { (filter, label) ->
                        val selected = workerFilter == filter
                        TextButton(
                            onClick = { workerFilter = filter },
                            contentPadding = androidx.compose.foundation.layout.PaddingValues(
                                horizontal = 10.dp,
                                vertical = 5.dp
                            ),
                            colors = ButtonDefaults.textButtonColors(
                                contentColor = if (selected) LoginAccent else AppTextMuted,
                                containerColor = if (selected) WasteBlue else AppSurfaceMuted
                            ),
                            shape = RoundedCornerShape(12.dp)
                        ) { Text(label, fontSize = 10.sp, fontWeight = FontWeight.SemiBold) }
                    }
                }
                if (workers.isEmpty()) EmptyAdminState("Belum ada data petugas.")
                else if (visibleWorkers.isEmpty()) {
                    EmptyAdminState(
                        if (workerSearchQuery.isNotBlank()) "Petugas tidak ditemukan."
                        else "Tidak ada petugas pada filter ini."
                    )
                }
                visibleWorkers.forEach { item ->
                    AppCard(
                        modifier = Modifier.fillMaxWidth(),
                        containerColor = Color.White,
                        borderColor = AppBorder,
                        accentColor = if (item.active && item.hasAccount) LoginAccent else WasteRed
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            UserAvatar(item.name.displayPersonName())
                            Spacer(modifier = Modifier.width(9.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    item.name.displayPersonName(),
                                    color = AppText,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    if (item.hasAccount) "@${item.username} · ${item.zone.ifBlank { "Tanpa zona" }}"
                                    else "Belum ada akun Android · ${item.zone.ifBlank { "Tanpa zona" }}",
                                    color = if (item.hasAccount) AppTextMuted else WasteRed,
                                    fontSize = 11.sp
                                )
                                Text(
                                    "${item.completedTasks} tugas disetujui · ${item.completedWeightKg.pretty()} kg",
                                    color = LoginAccent,
                                    fontSize = 11.sp
                                )
                                Text(
                                    "${item.activeTasks} tugas aktif · ${item.pendingConfirmations} bukti menunggu",
                                    color = AppTextMuted,
                                    fontSize = 10.sp
                                )
                            }
                            Column(horizontalAlignment = Alignment.End) {
                                TextButton(onClick = { editingWorker = item }) {
                                    Text(
                                        "Zona",
                                        color = LoginAccent,
                                        fontSize = 11.sp
                                    )
                                }
                                if (item.hasAccount) {
                                    TextButton(onClick = {
                                        scope.launch {
                                            if (updatingWorkerId != null) return@launch
                                            updatingWorkerId = item.id
                                            actionError = ""
                                            try {
                                                onUpdateWorker(item, !item.active)
                                            } catch (cancelled: CancellationException) {
                                                throw cancelled
                                            } catch (error: Exception) {
                                                actionError = error.message
                                                    ?: "Status petugas gagal diperbarui."
                                            } finally {
                                                updatingWorkerId = null
                                            }
                                        }
                                    }, enabled = updatingWorkerId == null) {
                                        Text(
                                            when {
                                                updatingWorkerId == item.id -> "Menyimpan…"
                                                item.active -> "Nonaktifkan"
                                                else -> "Aktifkan"
                                            },
                                            color = if (item.active) WasteRed else LoginAccent,
                                            fontSize = 11.sp
                                        )
                                    }
                                } else {
                                    Text("Akun sudah dihapus", color = AppTextMuted, fontSize = 10.sp)
                                }
                                if (item.hasAccount) {
                                    IconButton(
                                        onClick = { deletingWorker = item },
                                        modifier = Modifier.size(36.dp)
                                    ) {
                                        Icon(
                                            Icons.Filled.Delete,
                                            contentDescription = "Hapus akun petugas",
                                            tint = WasteRed,
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            "catatan" -> {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    listOf(
                        false to "Catatan aktif · ${adminNotes.size}",
                        true to "Arsip · ${archivedAdminNotes.size}"
                    ).forEach { (archive, label) ->
                        TextButton(
                            onClick = { showingNoteArchive = archive },
                            colors = ButtonDefaults.textButtonColors(
                                contentColor = if (showingNoteArchive == archive) LoginAccent else LoginMuted,
                                containerColor = if (showingNoteArchive == archive) WasteBlue else AppSurface
                            ),
                            shape = RoundedCornerShape(13.dp)
                        ) { Text(label, fontSize = 10.sp, fontWeight = FontWeight.Bold) }
                    }
                }

                if (showingNoteArchive) {
                    OutlinedTextField(
                        value = archiveSearch,
                        onValueChange = { archiveSearch = it.take(120) },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Cari arsip") },
                        placeholder = { Text("Kode, status, petugas, atau catatan") },
                        singleLine = true
                    )
                    val query = archiveSearch.trim().lowercase()
                    val filteredArchive = archivedAdminNotes.filter { archived ->
                        val note = archived.note
                        listOf(
                            note.report.code, note.report.status, WasteFlow.label(note.report.status), note.report.type,
                            note.report.location, note.report.reportedBy, note.report.workerName,
                            note.report.description, note.statusNote, note.workerNote,
                            note.adminNote, archived.archivedAt, archived.archivedBy
                        ).any { it.lowercase().contains(query) }
                    }
                    SectionTitle("Arsip catatan", "Laporan tetap tersimpan dan dapat dipulihkan kapan saja")
                    if (filteredArchive.isEmpty()) {
                        EmptyAdminState(if (query.isBlank()) "Belum ada catatan yang diarsipkan." else "Arsip yang cocok tidak ditemukan.")
                    }
                    filteredArchive.forEach { archived ->
                        val note = archived.note
                        AppCard(
                            modifier = Modifier.fillMaxWidth(),
                            containerColor = Color.White,
                            borderColor = AppBorder,
                            accentColor = reportAccent(note.report.status)
                        ) {
                            Column(
                                modifier = Modifier.fillMaxWidth().padding(13.dp),
                                verticalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Column(Modifier.weight(1f)) {
                                        Text(note.report.code, color = AppText, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                        Text("${note.report.type} · ${note.report.weightKg.pretty()} kg", color = AppTextMuted, fontSize = 10.sp)
                                    }
                                    StatusPill(note.report.status, reportAccent(note.report.status))
                                }
                                Text(note.report.location, color = AppTextMuted, fontSize = 10.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
                                val detail = note.statusNote.takeIf { it.startsWith("Catatan Admin:", true) }
                                    ?: note.adminNote.takeIf { it.isNotBlank() }
                                    ?: note.workerNote.takeIf { it.isNotBlank() }
                                    ?: note.statusNote.takeIf { it.isNotBlank() }
                                    ?: note.report.description
                                if (detail.isNotBlank()) {
                                    Text(detail, color = AppText, fontSize = 10.sp, maxLines = 3, overflow = TextOverflow.Ellipsis)
                                }
                                Text("Diarsipkan ${archived.archivedAt} · ${archived.archivedBy}", color = AppTextMuted, fontSize = 9.sp)
                                TextButton(
                                    enabled = !noteArchiveInProgress,
                                    onClick = { restoringAdminNote = archived }
                                ) { Text("Pulihkan catatan", color = LoginAccent, fontSize = 11.sp) }
                            }
                        }
                    }
                } else {
                    OutlinedTextField(
                        value = activeNoteSearch,
                        onValueChange = { activeNoteSearch = it.take(120) },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Cari catatan aktif") },
                        placeholder = { Text("Kode, jenis, lokasi, petugas, atau isi catatan") },
                        singleLine = true
                    )
                    val query = activeNoteSearch.trim().lowercase()
                    Row(
                        modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        listOf(
                            "semua" to "Semua",
                            "tinjau" to "Bukti · ${completionReviews.size}",
                            "perbaikan" to "Perbaikan · ${adminNotes.count { it.report.completionStatus.equals("perlu_perbaikan", true) }}",
                            "selesai" to "Selesai · ${adminNotes.count { it.report.status == WasteFlow.DONE }}",
                            "ditolak" to "Ditolak · ${adminNotes.count { it.report.status == WasteFlow.REJECTED }}"
                        ).forEach { (key, label) ->
                            TextButton(
                                onClick = { activeNoteFilter = key },
                                colors = ButtonDefaults.textButtonColors(
                                    contentColor = if (activeNoteFilter == key) LoginAccent else LoginMuted,
                                    containerColor = if (activeNoteFilter == key) WasteBlue else AppSurface
                                ),
                                shape = RoundedCornerShape(13.dp)
                            ) { Text(label, fontSize = 10.sp, fontWeight = FontWeight.SemiBold) }
                        }
                    }
                    val visibleReviews = if (activeNoteFilter == "semua" || activeNoteFilter == "tinjau") {
                        completionReviews.filter { review ->
                            query.isBlank() || listOf(
                                review.report.code,
                                review.report.type,
                                review.report.location,
                                review.report.description,
                                review.workerName,
                                review.workerNote
                            ).any { it.lowercase().contains(query) }
                        }
                    } else emptyList()
                    if ((activeNoteFilter == "semua" && visibleReviews.isNotEmpty()) || activeNoteFilter == "tinjau") {
                        SectionTitle(
                            "Bukti penyelesaian",
                            when {
                                visibleReviews.isNotEmpty() -> "Periksa foto dan catatan Petugas sebelum menyetujui tugas."
                                query.isNotBlank() -> "Tidak ada bukti yang cocok dengan pencarian."
                                else -> "Tidak ada bukti yang menunggu persetujuan."
                            }
                        )
                        visibleReviews.forEach { review ->
                            val report = review.report
                            AppCard(
                                modifier = Modifier.fillMaxWidth(),
                                containerColor = AppSurface,
                                accentColor = WasteOrange
                            ) {
                                Column(
                                    modifier = Modifier.fillMaxWidth().padding(13.dp),
                                    verticalArrangement = Arrangement.spacedBy(7.dp)
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Column(Modifier.weight(1f)) {
                                            Text(report.code, color = AppText, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                            Text(
                                                "${report.type} · ${report.weightKg.pretty()} kg",
                                                color = AppTextMuted,
                                                fontSize = 10.sp
                                            )
                                        }
                                        StatusPill("Menunggu tinjauan", WasteOrange)
                                    }
                                    Text(
                                        "${report.location} · ${review.workerName.displayPersonName()}",
                                        color = AppTextMuted,
                                        fontSize = 10.sp,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    if (review.submittedAt.isNotBlank()) {
                                        Text("Dikirim ${review.submittedAt}", color = AppTextMuted, fontSize = 9.sp)
                                    }
                                    if (review.workerNote.isNotBlank()) {
                                        Text(
                                            review.workerNote,
                                            color = AppText,
                                            fontSize = 11.sp,
                                            maxLines = 3,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    }
                                    TextButton(
                                        enabled = review.proofPhotoUrl.isNotBlank(),
                                        onClick = { onOpenPhoto(review.proofPhotoUrl) }
                                    ) { Text("Lihat foto bukti", color = LoginAccent, fontSize = 10.sp) }
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                                    ) {
                                        TextButton(
                                            modifier = Modifier.weight(1f),
                                            enabled = processingCompletionId == null,
                                            onClick = {
                                                revisionNote = ""
                                                revisionError = ""
                                                revisionReview = review
                                            }
                                        ) { Text("Minta perbaikan", color = WasteOrange, fontSize = 10.sp) }
                                        TextButton(
                                            modifier = Modifier.weight(1f),
                                            enabled = processingCompletionId == null && review.proofPhotoUrl.isNotBlank(),
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
                                                        actionError = error.message ?: "Pemeriksaan bukti gagal disimpan."
                                                    } finally {
                                                        processingCompletionId = null
                                                    }
                                                }
                                            }
                                        ) {
                                            Text(
                                                if (processingCompletionId == review.id) "Menyetujui…" else "Setujui",
                                                color = WasteGreen,
                                                fontSize = 10.sp,
                                                fontWeight = FontWeight.Bold
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }

                    val pendingReviewIds = visibleReviews.mapTo(mutableSetOf()) { it.report.id }
                    val filteredActiveNotes = adminNotes.filter { note ->
                        val report = note.report
                        val matchesQuery = query.isBlank() || listOf(
                            report.code, report.type, report.location, report.reportedBy,
                            report.workerName, report.description, note.statusNote,
                            note.workerNote, note.adminNote, WasteFlow.label(report.status)
                        ).any { it.lowercase().contains(query) }
                        val matchesFilter = when (activeNoteFilter) {
                            "tinjau" -> false
                            "perbaikan" -> report.completionStatus.equals("perlu_perbaikan", true)
                            "selesai" -> report.status == WasteFlow.DONE
                            "ditolak" -> report.status == WasteFlow.REJECTED
                            else -> report.id !in pendingReviewIds
                        }
                        matchesQuery && matchesFilter
                    }
                    if (activeNoteFilter != "tinjau") {
                        SectionTitle(
                            "Aktivitas laporan",
                            "Riwayat status, catatan Admin, dan catatan Petugas."
                        )
                        if (filteredActiveNotes.isEmpty()) {
                            EmptyAdminState(
                                when {
                                    adminNotes.isEmpty() -> "Belum ada aktivitas laporan."
                                    query.isNotBlank() -> "Tidak ada aktivitas yang cocok dengan pencarian."
                                    else -> "Belum ada aktivitas pada filter ini."
                                }
                            )
                        }
                    }
                    if (activeNoteFilter != "tinjau") filteredActiveNotes.forEach { note ->
                        val report = note.report
                        val detail = note.statusNote.takeIf { it.startsWith("Catatan Admin:", true) }
                            ?: note.adminNote.takeIf { it.isNotBlank() }
                            ?: note.workerNote.takeIf { it.isNotBlank() }
                            ?: note.statusNote.takeIf { it.isNotBlank() }
                            ?: report.description
                        AppCard(
                            modifier = Modifier.fillMaxWidth(),
                            containerColor = Color.White,
                            borderColor = AppBorder,
                            accentColor = reportAccent(report.status)
                        ) {
                            Column(
                                modifier = Modifier.fillMaxWidth().padding(13.dp),
                                verticalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Column(Modifier.weight(1f)) {
                                        Text(report.code, color = AppText, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                        Text("${report.type} · ${report.weightKg.pretty()} kg", color = AppTextMuted, fontSize = 10.sp)
                                    }
                                    StatusPill(report.status, reportAccent(report.status))
                                }
                                Text(report.location, color = AppTextMuted, fontSize = 10.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
                                if (report.workerName.isNotBlank()) {
                                    Text("Petugas: ${report.workerName.displayPersonName()}", color = AppTextMuted, fontSize = 10.sp)
                                }
                                if (detail.isNotBlank()) {
                                    Text(detail, color = AppText, fontSize = 10.sp, maxLines = 3, overflow = TextOverflow.Ellipsis)
                                }
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    TextButton(
                                        onClick = {
                                            adminNoteDraft = ""
                                            adminNoteError = ""
                                            writingAdminNote = note
                                        }
                                    ) { Text("Tambah catatan", color = LoginAccent, fontSize = 10.sp) }
                                    Spacer(Modifier.weight(1f))
                                    TextButton(
                                        enabled = !noteArchiveInProgress,
                                        onClick = { archivingAdminNote = note }
                                    ) { Text("Arsipkan", color = LoginMuted, fontSize = 10.sp) }
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
            allowAdminAccount = false,
            onDismiss = { adding = false },
            onSave = { name, username, email, password, zone, role ->
                onCreateWorker(name, username, email, password, zone, role)
                adding = false
            }
        )
    }
    editingWorker?.let { item ->
        SingleFieldDialog(
            "Ubah zona tugas",
            item.zone,
            "Zona tugas",
            { editingWorker = null }) { zone ->
            onUpdateWorker(item.copy(zone = zone), item.active); editingWorker = null
        }
    }
    deletingWorker?.let { item ->
        DeleteWorkerAccountDialog(
            worker = item,
            replacementWorkers = workers.filter { it.id != item.id && it.active && it.hasAccount },
            onDismiss = { deletingWorker = null },
            onDelete = onDeleteWorker
        )
    }
    revisionReview?.let { review ->
        AlertDialog(
            onDismissRequest = { if (!revisionSending) revisionReview = null },
            title = { Text("Minta perbaikan bukti", color = LoginInk, fontWeight = FontWeight.Bold) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Tugas ${review.report.code} · ${review.workerName.displayPersonName()}", color = AppTextMuted, fontSize = 11.sp)
                    if (revisionError.isNotBlank()) InlineError(revisionError)
                    OutlinedTextField(
                        value = revisionNote,
                        onValueChange = { revisionNote = it.take(500) },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Catatan perbaikan") },
                        placeholder = { Text("Jelaskan bagian bukti yang perlu diperbaiki") },
                        minLines = 3,
                        maxLines = 5,
                        enabled = !revisionSending
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
                    }
                ) {
                    Text(if (revisionSending) "Mengirim…" else "Kirim catatan", color = LoginAccent)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { revisionReview = null },
                    enabled = !revisionSending
                ) { Text("Batal", color = LoginMuted) }
            },
            containerColor = AppSurface
        )
    }
    writingAdminNote?.let { item ->
        val noteBytes = adminNoteDraft.toByteArray(Charsets.UTF_8).size
        AlertDialog(
            onDismissRequest = { if (!adminNoteSending) writingAdminNote = null },
            title = { Text("Catatan admin", color = LoginInk, fontWeight = FontWeight.Bold) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        "${item.report.code} · ${WasteFlow.label(item.report.status)}",
                        color = AppTextMuted,
                        fontSize = 11.sp
                    )
                    OutlinedTextField(
                        value = adminNoteDraft,
                        onValueChange = { value ->
                            if (value.toByteArray(Charsets.UTF_8).size <= 240) adminNoteDraft = value
                        },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Tulis catatan") },
                        placeholder = { Text("Contoh: lokasi sudah dikonfirmasi dengan petugas") },
                        minLines = 3,
                        maxLines = 5,
                        enabled = !adminNoteSending
                    )
                    Text("$noteBytes/240 byte", color = AppTextMuted, fontSize = 10.sp)
                    if (adminNoteError.isNotBlank()) InlineError(adminNoteError)
                }
            },
            confirmButton = {
                TextButton(
                    enabled = adminNoteDraft.isNotBlank() && !adminNoteSending,
                    onClick = {
                        scope.launch {
                            adminNoteSending = true
                            adminNoteError = ""
                            actionError = ""
                            try {
                                onAddAdminNote(item, adminNoteDraft.trim())
                                writingAdminNote = null
                                adminNoteDraft = ""
                            } catch (cancelled: CancellationException) {
                                throw cancelled
                            } catch (error: Exception) {
                                adminNoteError = error.message ?: "Catatan admin gagal disimpan."
                            } finally {
                                adminNoteSending = false
                            }
                        }
                    }
                ) {
                    Text(if (adminNoteSending) "Menyimpan…" else "Simpan catatan", color = LoginAccent)
                }
            },
            dismissButton = {
                TextButton(
                    enabled = !adminNoteSending,
                    onClick = { writingAdminNote = null }
                ) { Text("Batal", color = LoginMuted) }
            },
            containerColor = AppSurface
        )
    }
    archivingAdminNote?.let { item ->
        AlertDialog(
            onDismissRequest = { if (!noteArchiveInProgress) archivingAdminNote = null },
            title = {
                Text("Arsipkan catatan laporan?", color = LoginInk, fontWeight = FontWeight.Bold)
            },
            text = {
                Text(
                    "Catatan ${item.report.code} (${WasteFlow.label(item.report.status)}) dipindahkan ke Arsip. Laporan, foto, catatan, dan riwayat tetap tersimpan.",
                    color = AppTextMuted
                )
            },
            confirmButton = {
                TextButton(
                    enabled = !noteArchiveInProgress,
                    onClick = {
                        scope.launch {
                            noteArchiveInProgress = true
                            actionError = ""
                            try {
                                onArchiveAdminNote(item)
                                archivingAdminNote = null
                            } catch (cancelled: CancellationException) {
                                throw cancelled
                            } catch (error: Exception) {
                                actionError = error.message ?: "Catatan gagal diarsipkan."
                            } finally {
                                noteArchiveInProgress = false
                            }
                        }
                    }
                ) {
                    Text(
                        if (noteArchiveInProgress) "Menyimpan…" else "Arsipkan",
                        color = LoginAccent
                    )
                }
            },
            dismissButton = {
                TextButton(
                    enabled = !noteArchiveInProgress,
                    onClick = { archivingAdminNote = null }
                ) { Text("Batal", color = LoginMuted) }
            },
            containerColor = AppSurface
        )
    }
    restoringAdminNote?.let { item ->
        AlertDialog(
            onDismissRequest = { if (!noteArchiveInProgress) restoringAdminNote = null },
            title = { Text("Pulihkan catatan?", color = LoginInk, fontWeight = FontWeight.Bold) },
            text = {
                Text(
                    "Catatan ${item.note.report.code} akan kembali ke daftar Catatan Admin. Data laporan tetap utuh.",
                    color = AppTextMuted
                )
            },
            confirmButton = {
                TextButton(
                    enabled = !noteArchiveInProgress,
                    onClick = {
                        scope.launch {
                            noteArchiveInProgress = true
                            actionError = ""
                            try {
                                onRestoreAdminNote(item)
                                restoringAdminNote = null
                            } catch (cancelled: CancellationException) {
                                throw cancelled
                            } catch (error: Exception) {
                                actionError = error.message ?: "Catatan gagal dipulihkan."
                            } finally {
                                noteArchiveInProgress = false
                            }
                        }
                    }
                ) { Text(if (noteArchiveInProgress) "Memulihkan…" else "Pulihkan", color = LoginAccent) }
            },
            dismissButton = {
                TextButton(
                    enabled = !noteArchiveInProgress,
                    onClick = { restoringAdminNote = null }
                ) { Text("Batal", color = LoginMuted) }
            },
            containerColor = AppSurface
        )
    }
}

@Composable
fun PetugasTasksScreen(
    tasks: List<WasteRecord>,
    incomingReports: List<WasteRecord>,
    vehicles: List<WasteVehicle>,
    facilities: List<WasteFacility>,
    errorMessage: String,
    account: UserAccount,
    onOpenPhoto: (String) -> Unit,
    onAssignReport: suspend (WasteRecord, Int, Int) -> Unit,
    onRejectReport: suspend (WasteRecord, String) -> Unit,
    onMarkTransported: suspend (WasteRecord) -> Unit,
    onSubmitProof: suspend (WasteRecord, Uri, String) -> Unit,
    onCreateVehicle: suspend (String, Double, String, String) -> Unit,
    onUpdateVehicle: suspend (WasteVehicle) -> Unit,
    onCreateFacility: suspend (String, String, Double) -> Unit,
    onUpdateFacility: suspend (WasteFacility) -> Unit,
    isInitialLoading: Boolean,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    onLogout: () -> Unit
) {
    var selectedSection by rememberSaveable(account.username) { mutableStateOf("ringkasan") }
    var selectedWorkArea by rememberSaveable(account.username) { mutableStateOf("laporan") }
    var incomingSearch by rememberSaveable(account.username) { mutableStateOf("") }
    var activeTaskSearch by rememberSaveable(account.username) { mutableStateOf("") }
    var historySearch by rememberSaveable(account.username) { mutableStateOf("") }
    var activeTaskFilter by rememberSaveable(account.username) { mutableStateOf("semua") }
    var historyFilter by rememberSaveable(account.username) { mutableStateOf("semua") }
    var showVehicleForm by remember { mutableStateOf(false) }
    var editingVehicle by remember { mutableStateOf<WasteVehicle?>(null) }
    var showFacilityForm by remember { mutableStateOf(false) }
    var editingFacility by remember { mutableStateOf<WasteFacility?>(null) }
    var assigningReport by remember { mutableStateOf<WasteRecord?>(null) }
    var rejectingReport by remember { mutableStateOf<WasteRecord?>(null) }
    var rejectionNote by rememberSaveable { mutableStateOf("") }
    var reportActionError by remember { mutableStateOf("") }
    var processingReportAction by remember { mutableStateOf(false) }
    var transportingTaskId by remember { mutableStateOf<Int?>(null) }
    var selectedCompletionTask by remember { mutableStateOf<WasteRecord?>(null) }
    var selectedProofUri by remember { mutableStateOf<Uri?>(null) }
    var selectedCameraUri by remember { mutableStateOf<Uri?>(null) }
    var cameraEvidenceFile by remember { mutableStateOf<File?>(null) }
    var showProofSourceDialog by remember { mutableStateOf(false) }
    var proofNote by rememberSaveable { mutableStateOf("") }
    var proofError by remember { mutableStateOf("") }
    var sendingProof by remember { mutableStateOf(false) }
    var confirmingTransportTask by remember { mutableStateOf<WasteRecord?>(null) }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val proofPicker = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        selectedProofUri = uri
        if (uri == null) selectedCompletionTask = null
        showProofSourceDialog = false
    }
    val cameraPicker = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { captured ->
        selectedProofUri = if (captured) selectedCameraUri else null
        if (!captured) {
            cameraEvidenceFile?.delete()
            cameraEvidenceFile = null
            selectedCameraUri = null
            proofError = "Foto belum diambil. Kamu bisa mencoba lagi atau memilih dari galeri."
            showProofSourceDialog = true
        }
    }
    fun clearProofSelection() {
        cameraEvidenceFile?.delete()
        cameraEvidenceFile = null
        selectedCameraUri = null
        selectedProofUri = null
        selectedCompletionTask = null
        showProofSourceDialog = false
    }
    fun requestCompletionProof(task: WasteRecord) {
        cameraEvidenceFile?.delete()
        cameraEvidenceFile = null
        selectedCameraUri = null
        selectedCompletionTask = task
        selectedProofUri = null
        proofNote = ""
        proofError = ""
        showProofSourceDialog = true
    }
    fun launchEvidenceCamera() {
        val task = selectedCompletionTask ?: return
        val file = File(File(context.cacheDir, "evidence").apply { mkdirs() }, "bukti-${task.id}-${System.currentTimeMillis()}.jpg")
        runCatching {
            check(file.createNewFile()) { "File foto tidak dapat dibuat." }
            val uri = FileProvider.getUriForFile(
                context,
                "${context.packageName}.fileprovider",
                file
            )
            cameraEvidenceFile = file
            selectedCameraUri = uri
            showProofSourceDialog = false
            cameraPicker.launch(uri)
        }.onFailure { error ->
            file.delete()
            cameraEvidenceFile = null
            selectedCameraUri = null
            proofError = error.message ?: "Kamera tidak dapat dibuka. Pilih foto dari galeri."
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
    val currentSection = selectedSection.takeIf {
        it in setOf("ringkasan", "tugas", "riwayat", "profil")
    } ?: "ringkasan"
    val activeTasks = tasks
        .filter { it.status == WasteFlow.ASSIGNED || it.status == WasteFlow.TRANSPORTED }
        .sortedWith(
            compareBy<WasteRecord> {
                when {
                    it.completionStatus.equals("perlu_perbaikan", ignoreCase = true) -> 0
                    it.status == WasteFlow.TRANSPORTED && !it.completionStatus.equals("menunggu", ignoreCase = true) -> 1
                    it.status == WasteFlow.ASSIGNED -> 2
                    else -> 3
                }
            }.thenByDescending { !it.hazardLevel.equals("RENDAH", ignoreCase = true) }
                .thenBy { it.reportedAt }
        )
    val completedTasks = tasks
        .filter { it.status == WasteFlow.DONE }
        .sortedByDescending { it.reportedAt }
    val rejectedTasks = tasks
        .filter { it.status == WasteFlow.REJECTED }
        .sortedByDescending { it.reportedAt }
    val historyTasks = tasks
        .filter { it.status == WasteFlow.DONE || it.status == WasteFlow.REJECTED }
        .sortedByDescending { it.reportedAt }
    val newReports = incomingReports.sortedBy { it.reportedAt }
    val tasksNeedingRevision = activeTasks.filter {
        it.completionStatus.equals("perlu_perbaikan", ignoreCase = true)
    }
    val tasksReadyForTransport = activeTasks.filter { it.status == WasteFlow.ASSIGNED }
    val tasksWaitingForAdmin = activeTasks.filter {
        it.status == WasteFlow.TRANSPORTED && it.completionStatus.equals("menunggu", ignoreCase = true)
    }
    val tasksInRoute = activeTasks.filter {
        it.status == WasteFlow.TRANSPORTED &&
                !it.completionStatus.equals("menunggu", ignoreCase = true) &&
                !it.completionStatus.equals("perlu_perbaikan", ignoreCase = true)
    }

    fun matchesTaskSearch(report: WasteRecord, query: String): Boolean {
        val normalizedQuery = query.trim().lowercase()
        return normalizedQuery.isBlank() || listOf(
            report.code,
            report.type,
            report.location,
            report.description,
            report.vehiclePlate,
            report.tpaName
        ).any { it.lowercase().contains(normalizedQuery) }
    }

    val filteredIncomingReports = newReports.filter { matchesTaskSearch(it, incomingSearch) }
    val filteredActiveTasks = activeTasks.filter { task ->
        matchesTaskSearch(task, activeTaskSearch) && when (activeTaskFilter) {
            "perbaikan" -> task in tasksNeedingRevision
            "siap" -> task in tasksReadyForTransport
            "rute" -> task in tasksInRoute
            "admin" -> task in tasksWaitingForAdmin
            else -> true
        }
    }
    val filteredHistoryTasks = historyTasks.filter { task ->
        matchesTaskSearch(task, historySearch) && when (historyFilter) {
            "selesai" -> task.status == WasteFlow.DONE
            "ditolak" -> task.status == WasteFlow.REJECTED
            else -> true
        }
    }

    fun hasLoadError(label: String): Boolean =
        errorMessage.lineSequence().any { it.startsWith("$label:") }

    AdminPage(
        current = "tasks",
        account = account,
        onNavigate = {},
        onLogout = onLogout,
        bottomBar = { PetugasNavigationBar(currentSection) { selectedSection = it } },
        backgroundContent = { PetugasThemeBackdrop(Modifier.fillMaxSize()) }
    ) {
        if (currentSection == "ringkasan") {
            PetugasJourneyHero(
                name = account.name,
                tasks = tasks,
                isInitialLoading = isInitialLoading,
                isRefreshing = isRefreshing,
                onOpenTasks = { selectedSection = "tugas" },
                onRefresh = onRefresh
            )
        }
        if (currentSection != "profil") {
            if (errorMessage.isNotBlank()) InlineError(errorMessage)
            if (reportActionError.isNotBlank()) InlineError(reportActionError)
        }
        AnimatedContent(
            targetState = currentSection,
            modifier = Modifier.fillMaxWidth(),
            transitionSpec = {
                val sectionOrder = listOf("ringkasan", "tugas", "riwayat")
                val direction = if (sectionOrder.indexOf(targetState) >= sectionOrder.indexOf(initialState)) 1 else -1
                (slideInHorizontally(
                    animationSpec = tween(260, easing = FastOutSlowInEasing)
                ) { width -> width * direction * 18 / 100 } +
                    fadeIn(tween(200))) togetherWith
                    (slideOutHorizontally(
                        animationSpec = tween(220, easing = FastOutSlowInEasing)
                    ) { width -> -width * direction * 12 / 100 } +
                        fadeOut(tween(160)))
            },
            label = "petugas-section-slide"
        ) { section ->
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(13.dp)
            ) {
                when (section) {
            "ringkasan" -> {
                val nextTask = activeTasks.firstOrNull { it !in tasksWaitingForAdmin }
                val nextReport = newReports.firstOrNull()
                if (isInitialLoading) {
                    PetugasLoadingState("Menyiapkan ruang kerja", "Memuat laporan, tugas, dan armada.")
                } else {
                    PetugasOperationalOverview(tasks) { selectedSection = "tugas" }
                    when {
                        nextTask != null -> {
                            SectionTitle(
                                "Lanjutkan pekerjaan",
                                if (nextTask.completionStatus == "perlu_perbaikan") {
                                    "Admin meminta perbaikan bukti. Tugas ini perlu ditangani lebih dulu."
                                } else if (nextTask.status == WasteFlow.TRANSPORTED) {
                                    "Muatan sedang diangkut. Kirim bukti saat sudah diterima di TPA."
                                } else {
                                    "Tugas aktif didahulukan sebelum laporan baru."
                                }
                            )
                            PetugasTaskCard(
                                report = nextTask,
                                onOpenPhoto = onOpenPhoto,
                                onMarkTransported = { confirmingTransportTask = it },
                                isTransporting = transportingTaskId == nextTask.id,
                                isActionEnabled = transportingTaskId == null,
                                onComplete = ::requestCompletionProof
                            )
                            TextButton(
                                onClick = { selectedSection = "tugas" },
                                modifier = Modifier.align(Alignment.CenterHorizontally)
                            ) {
                                Text("Lihat semua tugas aktif", color = LoginAccent, fontWeight = FontWeight.SemiBold, fontSize = 12.sp)
                            }
                        }
                        nextReport != null -> {
                            SectionTitle("Laporan baru", "Tinjau laporan warga dan siapkan penanganannya.")
                            AdminReportCard(
                                report = nextReport,
                                onOpenPhoto = onOpenPhoto,
                                onReject = {
                                    rejectionNote = ""
                                    rejectingReport = nextReport
                                },
                                onAssign = { assigningReport = nextReport },
                                compact = true,
                                assignmentLabel = "Terima & tugaskan"
                            )
                        }
                        tasksWaitingForAdmin.isNotEmpty() -> {
                            SectionTitle(
                                "Bukti menunggu Admin",
                                "${tasksWaitingForAdmin.size} tugas sudah dikirim untuk diperiksa. Petugas tidak perlu mengirim ulang."
                            )
                            PetugasTaskCard(
                                report = tasksWaitingForAdmin.first(),
                                onOpenPhoto = onOpenPhoto
                            )
                            TextButton(
                                onClick = {
                                    selectedSection = "tugas"
                                    selectedWorkArea = "aktif"
                                    activeTaskFilter = "admin"
                                },
                                modifier = Modifier.align(Alignment.CenterHorizontally)
                            ) {
                                Text("Lihat bukti terkirim", color = LoginAccent, fontWeight = FontWeight.SemiBold, fontSize = 12.sp)
                            }
                        }
                        else -> PetugasEmptyState(
                            "Semua pekerjaan tertangani",
                            "Belum ada tugas aktif atau laporan baru. Perbarui data untuk cek antrean terbaru."
                        )
                    }
                }
            }

            "tugas" -> {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        modifier = Modifier.weight(1f).horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(3.dp)
                    ) {
                        listOf(
                            Triple("laporan", "Masuk", newReports.size),
                            Triple("aktif", "Aktif", activeTasks.size),
                            Triple("armada", "Armada", vehicles.size),
                            Triple("tpa", "TPA", facilities.size)
                        ).forEach { (key, label, count) ->
                            val selected = selectedWorkArea == key
                            TextButton(
                                onClick = { selectedWorkArea = key },
                                contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 10.dp, vertical = 6.dp),
                                colors = ButtonDefaults.textButtonColors(
                                    contentColor = if (selected) LoginAccent else LoginMuted,
                                    containerColor = if (selected) LoginAccent.copy(alpha = 0.12f) else Color.Transparent
                                ),
                                shape = RoundedCornerShape(14.dp)
                            ) { Text("$label · $count", fontSize = 10.sp, fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium) }
                        }
                    }
                    PetugasRefreshButton(isRefreshing, onRefresh)
                }
                when (selectedWorkArea) {
                    "laporan" -> {
                        SectionTitle("Laporan masuk", "Pilih laporan untuk menentukan armada dan TPA tujuan.")
                        PetugasQueueSearch(
                            query = incomingSearch,
                            onQueryChange = { incomingSearch = it.take(100) },
                            placeholder = "Cari kode, jenis, atau lokasi"
                        )
                        if (isInitialLoading && newReports.isEmpty()) {
                            PetugasLoadingState("Memuat laporan", "Mengambil antrean terbaru dari server.")
                        } else if (newReports.isEmpty() && hasLoadError("Laporan masuk")) {
                            PetugasLoadFailure("Laporan belum dapat dimuat.", onRefresh)
                        } else if (newReports.isEmpty()) {
                            PetugasEmptyState("Tidak ada laporan baru", "Laporan warga yang menunggu penanganan akan tampil di sini.")
                        } else if (filteredIncomingReports.isEmpty()) {
                            PetugasEmptyState("Laporan tidak ditemukan", "Coba kata kunci lain atau hapus pencarian.")
                            TextButton(onClick = { incomingSearch = "" }, modifier = Modifier.align(Alignment.CenterHorizontally)) {
                                Text("Hapus pencarian", color = LoginAccent, fontWeight = FontWeight.SemiBold)
                            }
                        } else {
                            filteredIncomingReports.forEach { report ->
                                AdminReportCard(
                                    report = report,
                                    onOpenPhoto = onOpenPhoto,
                                    onReject = {
                                        rejectionNote = ""
                                        rejectingReport = report
                                    },
                                    onAssign = { assigningReport = report },
                                    compact = true,
                                    assignmentLabel = "Terima & tugaskan"
                                )
                            }
                        }
                    }

                    "aktif" -> {
                        SectionTitle("Tugas aktif", "Antrean diprioritaskan berdasarkan langkah kerja berikutnya.")
                        PetugasQueueSearch(
                            query = activeTaskSearch,
                            onQueryChange = { activeTaskSearch = it.take(100) },
                            placeholder = "Cari kode, lokasi, armada, atau TPA"
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
                            horizontalArrangement = Arrangement.spacedBy(3.dp)
                        ) {
                            listOf(
                                Triple("semua", "Semua", activeTasks.size),
                                Triple("perbaikan", "Perbaikan", tasksNeedingRevision.size),
                                Triple("siap", "Siap angkut", tasksReadyForTransport.size),
                                Triple("rute", "Dalam rute", tasksInRoute.size),
                                Triple("admin", "Menunggu Admin", tasksWaitingForAdmin.size)
                            ).forEach { (key, label, count) ->
                                val selected = activeTaskFilter == key
                                TextButton(
                                    onClick = { activeTaskFilter = key },
                                    contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 10.dp, vertical = 6.dp),
                                    colors = ButtonDefaults.textButtonColors(
                                        contentColor = if (selected) LoginAccent else LoginMuted,
                                        containerColor = if (selected) LoginAccent.copy(alpha = 0.12f) else Color.Transparent
                                    ),
                                    shape = RoundedCornerShape(14.dp)
                                ) {
                                    Text(
                                        "$label · $count",
                                        fontSize = 10.sp,
                                        fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium
                                    )
                                }
                            }
                        }
                        if (isInitialLoading && activeTasks.isEmpty()) {
                            PetugasLoadingState("Memuat tugas", "Mengambil penugasan akun ini dari server.")
                        } else if (activeTasks.isEmpty() && hasLoadError("Penugasan")) {
                            PetugasLoadFailure("Tugas belum dapat dimuat.", onRefresh)
                        } else if (activeTasks.isEmpty()) {
                            PetugasEmptyState("Belum ada tugas aktif", "Tugas yang sudah diterima akan muncul di sini.")
                        } else if (filteredActiveTasks.isEmpty()) {
                            PetugasEmptyState(
                                if (activeTaskSearch.isNotBlank()) "Tugas tidak ditemukan" else "Tidak ada tugas pada filter ini",
                                "Ubah filter atau kata kunci untuk melihat penugasan lain."
                            )
                            TextButton(
                                onClick = { activeTaskFilter = "semua"; activeTaskSearch = "" },
                                modifier = Modifier.align(Alignment.CenterHorizontally)
                            ) {
                                Text("Tampilkan semua tugas", color = LoginAccent, fontWeight = FontWeight.SemiBold)
                            }
                        } else {
                            filteredActiveTasks.forEach { task ->
                                PetugasTaskCard(
                                    report = task,
                                    onOpenPhoto = onOpenPhoto,
                                    onMarkTransported = { confirmingTransportTask = it },
                                    isTransporting = transportingTaskId == task.id,
                                    isActionEnabled = transportingTaskId == null,
                                    onComplete = ::requestCompletionProof
                                )
                            }
                        }
                    }

                    "armada" -> {
                        SectionTitle("Armada", "Periksa kapasitas dan kondisi sebelum menerima laporan.")
                        AppButton("＋ Tambah kendaraan", { showVehicleForm = true }, containerColor = LoginAccent)
                        if (isInitialLoading && vehicles.isEmpty()) {
                            PetugasLoadingState("Memuat armada", "Mengambil data kendaraan dari server.")
                        } else if (vehicles.isEmpty() && hasLoadError("Armada")) {
                            PetugasLoadFailure("Data armada belum dapat dimuat.", onRefresh)
                        } else if (vehicles.isEmpty()) PetugasEmptyState("Belum ada armada", "Tambahkan kendaraan agar laporan bisa ditugaskan.")
                        vehicles.forEach { vehicle ->
                            AppCard(modifier = Modifier.fillMaxWidth(), containerColor = AppSurface) {
                                Row(
                                    modifier = Modifier.fillMaxWidth().padding(14.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(3.dp)) {
                                        Text(vehicle.plate, color = AppText, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                                        Text(
                                            "${vehicle.loadKg.pretty()} / ${vehicle.capacityKg.pretty()} kg · ${vehicle.condition}",
                                            color = AppTextMuted,
                                            fontSize = 11.sp
                                        )
                                        Text("Rute: ${vehicle.route.ifBlank { "Belum ditentukan" }}", color = AppTextMuted, fontSize = 10.sp)
                                    }
                                    TextButton(onClick = { editingVehicle = vehicle; showVehicleForm = true }) {
                                        Text("Ubah", color = LoginAccent, fontSize = 11.sp)
                                    }
                                }
                            }
                        }
                    }

                    "tpa" -> {
                        SectionTitle("TPA", "Pantau kapasitas tujuan sebelum mengatur pengangkutan.")
                        AppButton("＋ Tambah TPA", { showFacilityForm = true }, containerColor = LoginAccent)
                        if (isInitialLoading && facilities.isEmpty()) {
                            PetugasLoadingState("Memuat data TPA", "Mengambil kapasitas tujuan dari server.")
                        } else if (facilities.isEmpty() && hasLoadError("TPA")) {
                            PetugasLoadFailure("Data TPA belum dapat dimuat.", onRefresh)
                        } else if (facilities.isEmpty()) PetugasEmptyState("Belum ada TPA", "Tambahkan TPA agar laporan bisa ditugaskan.")
                        facilities.forEach { facility ->
                            AppCard(modifier = Modifier.fillMaxWidth(), containerColor = AppSurface) {
                                Column(
                                    modifier = Modifier.fillMaxWidth().padding(14.dp),
                                    verticalArrangement = Arrangement.spacedBy(5.dp)
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Column(Modifier.weight(1f)) {
                                            Text(facility.name, color = AppText, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                                            Text(facility.address, color = AppTextMuted, fontSize = 10.sp, maxLines = 2, overflow = TextOverflow.Ellipsis)
                                        }
                                        TextButton(onClick = { editingFacility = facility; showFacilityForm = true }) {
                                            Text("Ubah", color = LoginAccent, fontSize = 11.sp)
                                        }
                                    }
                                    Text(
                                        "${facility.filledTon.pretty()} ton terisi · ${facility.reservedTon.pretty()} ton dicadangkan dari ${facility.capacityTon.pretty()} ton",
                                        color = AppTextMuted,
                                        fontSize = 10.sp
                                    )
                                    SoftProgress(
                                        if (facility.capacityTon > 0) {
                                            ((facility.filledTon + facility.reservedTon) / facility.capacityTon).toFloat()
                                        } else 0f
                                    )
                                }
                            }
                        }
                    }
                }
            }

            "riwayat" -> {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Column(Modifier.weight(1f)) {
                        Text("Riwayat pekerjaan", color = AppText, fontSize = 17.sp, fontWeight = FontWeight.SemiBold)
                        Text(
                            "${completedTasks.size} selesai · ${rejectedTasks.size} ditolak",
                            color = AppTextMuted,
                            fontSize = 12.sp
                        )
                    }
                    PetugasRefreshButton(isRefreshing, onRefresh)
                }
                Row(
                    modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(3.dp)
                ) {
                    listOf(
                        Triple("semua", "Semua", historyTasks.size),
                        Triple("selesai", "Selesai", completedTasks.size),
                        Triple("ditolak", "Ditolak", rejectedTasks.size)
                    ).forEach { (key, label, count) ->
                        val selected = historyFilter == key
                        TextButton(
                            onClick = { historyFilter = key },
                            contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 10.dp, vertical = 6.dp),
                            colors = ButtonDefaults.textButtonColors(
                                contentColor = if (selected) LoginAccent else LoginMuted,
                                containerColor = if (selected) LoginAccent.copy(alpha = 0.12f) else Color.Transparent
                            ),
                            shape = RoundedCornerShape(14.dp)
                        ) {
                            Text(
                                "$label · $count",
                                fontSize = 10.sp,
                                fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium
                            )
                        }
                    }
                }
                PetugasQueueSearch(
                    query = historySearch,
                    onQueryChange = { historySearch = it.take(100) },
                    placeholder = "Cari kode, lokasi, atau TPA"
                )
                if (isInitialLoading && historyTasks.isEmpty()) {
                    PetugasLoadingState("Memuat riwayat", "Mengambil pekerjaan yang sudah selesai.")
                } else if (historyTasks.isEmpty() && hasLoadError("Penugasan")) {
                    PetugasLoadFailure("Riwayat belum dapat dimuat.", onRefresh)
                } else if (historyTasks.isEmpty()) {
                    PetugasEmptyState("Belum ada riwayat", "Tugas selesai dan laporan yang ditolak akan tersimpan di sini.")
                } else if (filteredHistoryTasks.isEmpty()) {
                    PetugasEmptyState(
                        if (historySearch.isNotBlank()) "Riwayat tidak ditemukan" else "Belum ada hasil pada filter ini",
                        "Ubah filter atau kata kunci untuk melihat catatan lain."
                    )
                    TextButton(
                        onClick = { historyFilter = "semua"; historySearch = "" },
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        Text("Tampilkan semua riwayat", color = LoginAccent, fontWeight = FontWeight.SemiBold)
                    }
                }
                filteredHistoryTasks.forEach { task -> PetugasTaskCard(task, onOpenPhoto) }
            }

            "profil" -> AccountProfileSettings(account, onLogout)
                }
            }
        }
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
            }
        )
    }
    rejectingReport?.let { report ->
        AlertDialog(
            onDismissRequest = { if (!processingReportAction) rejectingReport = null },
            title = { Text("Tolak laporan ini?", color = LoginInk, fontWeight = FontWeight.Bold) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("${report.code} · ${report.type}", color = AppTextMuted, fontSize = 11.sp)
                    Text("Petugas menolak laporan setelah meninjaunya. Admin tetap dapat melihat catatan dan statusnya.", color = AppText, fontSize = 11.sp)
                    OutlinedTextField(
                        value = rejectionNote,
                        onValueChange = { rejectionNote = it.take(500) },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Alasan penolakan") },
                        minLines = 2,
                        maxLines = 3,
                        enabled = !processingReportAction
                    )
                    Text(
                        "Alasan wajib diisi agar keputusan bisa ditinjau admin.",
                        color = AppTextMuted,
                        fontSize = 10.sp
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
                    }
                ) { Text(if (processingReportAction) "Menyimpan…" else "Tolak laporan", color = WasteRed) }
            },
            dismissButton = {
                TextButton(onClick = { rejectingReport = null }, enabled = !processingReportAction) {
                    Text("Batal", color = LoginMuted)
                }
            },
            containerColor = AppSurface
        )
    }
    confirmingTransportTask?.let { task ->
        AlertDialog(
            onDismissRequest = { if (transportingTaskId == null) confirmingTransportTask = null },
            title = { Text("Mulai pengangkutan?", color = LoginInk, fontWeight = FontWeight.Bold) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(7.dp)) {
                    Text("${task.code} · ${task.weightKg.pretty()} kg", color = AppText, fontWeight = FontWeight.SemiBold)
                    Text(
                        "Kendaraan ${task.vehiclePlate.ifBlank { "belum ditentukan" }} akan memuat sampah dari ${task.location}. Tujuan: ${task.tpaName.ifBlank { "TPA belum ditentukan" }}.",
                        color = AppTextMuted,
                        fontSize = 12.sp,
                        lineHeight = 17.sp
                    )
                    Text("Pastikan sampah sudah dimuat sebelum mengubah status.", color = AppTextMuted, fontSize = 11.sp)
                }
            },
            confirmButton = {
                TextButton(
                    enabled = transportingTaskId == null,
                    onClick = {
                        confirmingTransportTask = null
                        startTransport(task)
                    }
                ) { Text("Ya, mulai angkut", color = LoginAccent) }
            },
            dismissButton = {
                TextButton(onClick = { confirmingTransportTask = null }, enabled = transportingTaskId == null) {
                    Text("Kembali", color = LoginMuted)
                }
            },
            containerColor = AppSurface
        )
    }
    if (showProofSourceDialog && selectedCompletionTask != null && selectedProofUri == null) {
        val task = selectedCompletionTask!!
        AlertDialog(
            onDismissRequest = { clearProofSelection() },
            title = { Text("Lampirkan bukti TPA", color = LoginInk, fontWeight = FontWeight.Bold) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(7.dp)) {
                    Text("${task.code} · ${task.tpaName.ifBlank { "TPA tujuan" }}", color = AppText, fontWeight = FontWeight.SemiBold)
                    Text("Ambil foto saat muatan sudah diterima di TPA, atau pilih foto yang tersimpan di perangkat.", color = AppTextMuted, fontSize = 12.sp, lineHeight = 17.sp)
                    if (proofError.isNotBlank()) InlineError(proofError)
                }
            },
            confirmButton = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    TextButton(
                        onClick = {
                            cameraEvidenceFile?.delete()
                            cameraEvidenceFile = null
                            selectedCameraUri = null
                            showProofSourceDialog = false
                            proofPicker.launch("image/*")
                        }
                    ) { Text("Pilih galeri", color = LoginMuted) }
                    TextButton(onClick = ::launchEvidenceCamera) {
                        Text("Ambil foto", color = LoginAccent, fontWeight = FontWeight.SemiBold)
                    }
                }
            },
            dismissButton = {
                TextButton(onClick = { clearProofSelection() }) { Text("Batal", color = LoginMuted) }
            },
            containerColor = AppSurface
        )
    }
    val taskForProof = selectedCompletionTask
    val proofUri = selectedProofUri
    if (taskForProof != null && proofUri != null) {
        AlertDialog(
            onDismissRequest = { if (!sendingProof) clearProofSelection() },
            title = { Text("Kirim bukti penyelesaian", color = LoginInk, fontWeight = FontWeight.Bold) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(9.dp)) {
                    Text("${taskForProof.code} · ${taskForProof.weightKg.pretty()} kg", color = AppTextMuted, fontSize = 11.sp)
                    Text(
                        "Unggah foto saat sampah sudah diterima di ${taskForProof.tpaName.ifBlank { "TPA tujuan" }}. Setelah admin menyetujui bukti, muatan dicatat di TPA dan dikeluarkan dari kendaraan.",
                        color = AppText,
                        fontSize = 11.sp
                    )
                    OutlinedTextField(
                        value = proofNote,
                        onValueChange = { proofNote = it.take(500) },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Catatan untuk admin (opsional)") },
                        minLines = 2,
                        maxLines = 4,
                        enabled = !sendingProof
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
                    }
                ) { Text(if (sendingProof) "Mengirim…" else "Kirim bukti TPA", color = LoginAccent) }
            },
            dismissButton = {
                TextButton(onClick = { clearProofSelection() }, enabled = !sendingProof) {
                    Text("Batal", color = LoginMuted)
                }
            },
            containerColor = AppSurface
        )
    }
    if (showVehicleForm) {
        VehicleFormDialog(
            vehicle = editingVehicle,
            onDismiss = { showVehicleForm = false; editingVehicle = null },
            onSave = { plate, capacity, condition, route ->
                val vehicle = editingVehicle
                if (vehicle == null) onCreateVehicle(plate, capacity, condition, route)
                else onUpdateVehicle(vehicle.copy(plate = plate, capacityKg = capacity, condition = condition, route = route))
                showVehicleForm = false
                editingVehicle = null
            }
        )
    }
    if (showFacilityForm) {
        FacilityFormDialog(
            facility = editingFacility,
            onDismiss = { showFacilityForm = false; editingFacility = null },
            onSave = { name, address, capacity ->
                val facility = editingFacility
                if (facility == null) onCreateFacility(name, address, capacity)
                else onUpdateFacility(facility.copy(name = name, address = address, capacityTon = capacity))
                showFacilityForm = false
                editingFacility = null
            }
        )
    }
}

@Composable
private fun PetugasAssignmentDialog(
    report: WasteRecord,
    vehicles: List<WasteVehicle>,
    facilities: List<WasteFacility>,
    onDismiss: () -> Unit,
    onAssign: suspend (Int, Int) -> Unit
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
    val canAssign = selectedVehicle?.let(eligibleVehicles::contains) == true &&
            selectedFacility?.let(eligibleFacilities::contains) == true

    AlertDialog(
        onDismissRequest = { if (!saving) onDismiss() },
        title = { Text("Terima & tugaskan", color = LoginInk, fontWeight = FontWeight.Bold) },
        text = {
            ScrollableDialogContent(spacing = 8.dp) {
                Text("${report.code} · ${report.weightKg.pretty()} kg", color = LoginMuted, fontSize = 11.sp)
                Text("Pilih armada dan TPA. Laporan akan menjadi tugas milik akun Petugas yang sedang masuk.", color = AppText, fontSize = 11.sp)
                SelectionField(
                    "Kendaraan angkut",
                    selectedVehicle?.let { "${it.plate} · sisa ${it.availableKg.pretty()} kg" },
                    eligibleVehicles,
                    { "${it.plate} · sisa ${it.availableKg.pretty()} kg" }
                ) { selectedVehicle = it }
                SelectionField(
                    "TPA tujuan",
                    selectedFacility?.let { "${it.name} · sisa ${it.remainingTon.pretty()} ton" },
                    eligibleFacilities,
                    { "${it.name} · sisa ${it.remainingTon.pretty()} ton" }
                ) { selectedFacility = it }
                if (eligibleVehicles.isEmpty() || eligibleFacilities.isEmpty()) {
                    val unavailable = buildList {
                        if (eligibleVehicles.isEmpty()) add("kendaraan sehat dengan kapasitas cukup")
                        if (eligibleFacilities.isEmpty()) add("TPA yang kapasitasnya cukup")
                    }.joinToString(" dan ")
                    Text("Belum dapat diterima: tidak tersedia $unavailable.", color = WasteRed, fontSize = 10.sp)
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
                }
            ) { Text(if (saving) "Menyimpan…" else "Terima laporan", color = LoginAccent) }
        },
        dismissButton = {
            TextButton(onClick = onDismiss, enabled = !saving) { Text("Batal", color = LoginMuted) }
        },
        containerColor = AppSurface
    )
}

@Composable
private fun PetugasThemeBackdrop(modifier: Modifier = Modifier) {
    val darkMode = LocalSmartWasteAppearance.current.darkMode
    // Keep the full-screen canvas still between theme changes to avoid continuous GPU work.
    val ambientPhase = 0f
    val nightProgress by animateFloatAsState(
        targetValue = if (darkMode) 1f else 0f,
        animationSpec = tween(durationMillis = 760, easing = FastOutSlowInEasing),
        label = "petugas-theme-progress"
    )

    Canvas(modifier) {
            val width = size.width
            val height = size.height
            val breeze = sin(ambientPhase.toDouble()).toFloat()
            val drift = breeze * 12.dp.toPx()
            val dayProgress = 1f - nightProgress
            val sky = listOf(
                lerp(Color(0xFFC5DDE5), Color(0xFF111C2A), nightProgress),
                lerp(Color(0xFFE8E3D5), Color(0xFF18302F), nightProgress),
                lerp(Color(0xFFC1D3BF), Color(0xFF10221F), nightProgress)
            )
            drawRect(Brush.verticalGradient(sky), size = size)
            val nightPhase = ambientPhase

            run {
                val glowPulse = 0.5f + 0.5f * sin(nightPhase.toDouble()).toFloat()
                val moon = Offset(width * 0.81f + drift * 0.12f, height * 0.17f)
                val glowRadius = width * (0.34f + glowPulse * 0.03f)
                drawCircle(
                    Brush.radialGradient(
                        listOf(
                            Color(0xFFDDE8C9).copy(alpha = (0.22f + glowPulse * 0.06f) * nightProgress),
                            Color(0xFF8EB9A7).copy(alpha = 0.10f * nightProgress),
                            Color.Transparent
                        ),
                        center = moon,
                        radius = glowRadius
                    ),
                    radius = glowRadius,
                    center = moon
                )
                val moonRadius = width * 0.068f
                val crescent = Path().apply {
                    moveTo(moon.x + moonRadius * 0.72f, moon.y - moonRadius * 0.68f)
                    cubicTo(
                        moon.x - moonRadius * 0.18f, moon.y - moonRadius * 1.04f,
                        moon.x - moonRadius * 0.88f, moon.y - moonRadius * 0.38f,
                        moon.x - moonRadius * 0.69f, moon.y + moonRadius * 0.34f
                    )
                    cubicTo(
                        moon.x - moonRadius * 0.48f, moon.y + moonRadius * 1.04f,
                        moon.x + moonRadius * 0.34f, moon.y + moonRadius * 0.96f,
                        moon.x + moonRadius * 0.73f, moon.y + moonRadius * 0.38f
                    )
                    cubicTo(
                        moon.x + moonRadius * 0.34f, moon.y + moonRadius * 0.56f,
                        moon.x + moonRadius * 0.02f, moon.y + moonRadius * 0.27f,
                        moon.x + moonRadius * 0.03f, moon.y - moonRadius * 0.10f
                    )
                    cubicTo(
                        moon.x + moonRadius * 0.04f, moon.y - moonRadius * 0.40f,
                        moon.x + moonRadius * 0.31f, moon.y - moonRadius * 0.61f,
                        moon.x + moonRadius * 0.72f, moon.y - moonRadius * 0.68f
                    )
                    close()
                }
                drawPath(
                    crescent,
                    Brush.linearGradient(
                        listOf(
                            Color(0xFFF0F1DA).copy(alpha = nightProgress),
                            Color(0xFFC8D8BD).copy(alpha = nightProgress),
                            Color(0xFFAFC7B0).copy(alpha = nightProgress)
                        ),
                        start = Offset(moon.x - moonRadius, moon.y - moonRadius),
                        end = Offset(moon.x + moonRadius, moon.y + moonRadius)
                    )
                )
                listOf(
                    Offset(width * 0.13f, height * 0.14f),
                    Offset(width * 0.31f, height * 0.22f),
                    Offset(width * 0.48f, height * 0.12f),
                    Offset(width * 0.61f, height * 0.32f),
                    Offset(width * 0.22f, height * 0.43f),
                    Offset(width * 0.73f, height * 0.42f)
                ).forEachIndexed { index, star ->
                    val twinkle = 0.28f + 0.28f * (
                        0.5f + 0.5f * sin((nightPhase + index * 1.3f).toDouble()).toFloat()
                    )
                    drawCircle(Color(0xFFE5EEDC).copy(alpha = twinkle * nightProgress), (1.2f + index % 2 * 0.45f).dp.toPx(), star)
                }
                val horizonHaze = Brush.verticalGradient(
                    colors = listOf(
                        Color.Transparent,
                        Color(0xFF8EB9A7).copy(alpha = (0.08f + glowPulse * 0.035f) * nightProgress),
                        Color.Transparent
                    ),
                    startY = height * 0.43f,
                    endY = height * 0.82f
                )
                drawRect(horizonHaze, topLeft = Offset(0f, height * 0.43f), size = Size(width, height * 0.39f))
            }
            run {
                val morningGlow = Offset(width * 0.78f + drift * 0.12f, height * 0.22f)
                drawCircle(
                    Brush.radialGradient(
                        listOf(Color(0xFFF1E6CA).copy(alpha = 0.36f * dayProgress), Color.Transparent),
                        center = morningGlow,
                        radius = width * 0.36f
                    ),
                    radius = width * 0.36f,
                    center = morningGlow
                )
                // Soft morning clouds and a quiet breeze echo the login screen's light theme.
                val cloud = Path().apply {
                    moveTo(-width * 0.05f + drift, height * 0.21f)
                    cubicTo(width * 0.12f + drift, height * 0.14f, width * 0.25f + drift, height * 0.27f, width * 0.40f + drift, height * 0.19f)
                    cubicTo(width * 0.51f + drift, height * 0.14f, width * 0.61f + drift, height * 0.20f, width * 0.71f + drift, height * 0.17f)
                    lineTo(width * 0.71f + drift, height * 0.23f)
                    cubicTo(width * 0.47f + drift, height * 0.29f, width * 0.19f + drift, height * 0.25f, -width * 0.05f + drift, height * 0.27f)
                    close()
                }
                drawPath(cloud, Color(0xFFF5F4E8).copy(alpha = 0.28f * dayProgress))
            }

            // Wetland plants and a narrow stream keep the lower scene environmental
            // without repeating the hills and tree silhouettes from the login screen.
            val bank = Path().apply {
                moveTo(0f, height * 0.81f)
                cubicTo(width * 0.20f, height * 0.79f, width * 0.31f, height * 0.83f, width * 0.49f, height * 0.81f)
                cubicTo(width * 0.67f, height * 0.79f, width * 0.84f, height * 0.83f, width, height * 0.80f)
                lineTo(width, height)
                lineTo(0f, height)
                close()
            }
            drawPath(
                bank,
                lerp(
                    Color(0xFFAAC6AF).copy(alpha = 0.58f),
                    Color(0xFF203A31).copy(alpha = 0.82f),
                    nightProgress
                )
            )

            val water = Path().apply {
                moveTo(0f, height * 0.88f)
                cubicTo(width * 0.18f, height * 0.83f, width * 0.33f, height * 0.92f, width * 0.51f, height * 0.87f)
                cubicTo(width * 0.70f, height * 0.82f, width * 0.84f, height * 0.90f, width, height * 0.85f)
                lineTo(width, height)
                lineTo(0f, height)
                close()
            }
            val waterColors = listOf(
                lerp(Color(0xFFB4D2CF).copy(alpha = 0.66f), Color(0xFF2B5551).copy(alpha = 0.84f), nightProgress),
                lerp(Color(0xFF8FB8B5).copy(alpha = 0.60f), Color(0xFF142B2B).copy(alpha = 0.94f), nightProgress)
            )
            drawPath(water, Brush.verticalGradient(waterColors, startY = height * 0.83f, endY = height))
            val waterEdge = Path().apply {
                moveTo(0f, height * 0.88f)
                cubicTo(width * 0.18f, height * 0.83f, width * 0.33f, height * 0.92f, width * 0.51f, height * 0.87f)
                cubicTo(width * 0.70f, height * 0.82f, width * 0.84f, height * 0.90f, width, height * 0.85f)
            }
            drawPath(
                waterEdge,
                lerp(
                    Color(0xFFE6EEE0).copy(alpha = 0.48f),
                    Color(0xFFB1D1C3).copy(alpha = 0.40f),
                    nightProgress
                ),
                style = Stroke(width = 1.4.dp.toPx(), cap = StrokeCap.Round)
            )

            val waterPhase = ambientPhase
            val reflectionPulse = 0.5f + 0.5f * sin(waterPhase.toDouble()).toFloat()
            val reflectionAlpha = 0.12f + (0.05f + 0.02f * nightProgress) * reflectionPulse
            listOf(
                Triple(0.67f, 0.875f, 0.09f),
                Triple(0.76f, 0.91f, 0.12f),
                Triple(0.62f, 0.94f, 0.10f),
                Triple(0.82f, 0.96f, 0.07f)
            ).forEachIndexed { index, (x, y, length) ->
                val rippleShift = sin((waterPhase + index * 1.6f).toDouble()).toFloat() * 0.018f
                val rippleX = (x + rippleShift).coerceIn(0.02f, 0.96f - length)
                val ripple = Path().apply {
                    moveTo(width * rippleX, height * y)
                    cubicTo(
                        width * (rippleX + length * 0.3f), height * (y - 0.004f),
                        width * (rippleX + length * 0.65f), height * (y + 0.004f),
                        width * (rippleX + length), height * y
                    )
                }
                val reflectionColor = lerp(Color(0xFFF5F0D9), Color(0xFFD6E8D5), nightProgress)
                drawPath(
                    ripple,
                    reflectionColor.copy(alpha = reflectionAlpha * (1f - index * 0.12f)),
                    style = Stroke(width = (1.1f - index * 0.1f).dp.toPx(), cap = StrokeCap.Round)
                )
            }

            val lilyColor = lerp(
                Color(0xFF478565).copy(alpha = 0.56f),
                Color(0xFF91B59B).copy(alpha = 0.68f),
                nightProgress
            )
            val waterCut = lerp(
                Color(0xFFB4D2CF).copy(alpha = 0.65f),
                Color(0xFF2B5551).copy(alpha = 0.82f),
                nightProgress
            )
            val lilyPads = listOf(
                Triple(0.16f, 0.862f, 0.76f),
                Triple(0.39f, 0.878f, 1.00f),
                Triple(0.65f, 0.847f, 0.88f),
                Triple(0.85f, 0.862f, 0.70f)
            )
            lilyPads.forEachIndexed { index, (xFraction, yFraction, scale) ->
                val gentleBob = sin((waterPhase + index * 1.15f).toDouble()).toFloat() * 2.2.dp.toPx()
                val center = Offset(width * xFraction, height * yFraction + gentleBob)
                val radiusX = width * 0.030f * scale
                val radiusY = height * 0.011f * scale
                val pad = Path().apply {
                    moveTo(center.x, center.y)
                    cubicTo(
                        center.x + radiusX * 0.20f, center.y - radiusY * 0.70f,
                        center.x + radiusX * 0.86f, center.y - radiusY * 0.62f,
                        center.x + radiusX, center.y
                    )
                    cubicTo(
                        center.x + radiusX * 0.86f, center.y + radiusY * 0.95f,
                        center.x - radiusX * 0.86f, center.y + radiusY * 0.95f,
                        center.x - radiusX, center.y
                    )
                    cubicTo(
                        center.x - radiusX * 0.86f, center.y - radiusY * 0.62f,
                        center.x - radiusX * 0.20f, center.y - radiusY * 0.70f,
                        center.x, center.y
                    )
                    close()
                }
                drawPath(pad, lilyColor)
                drawLine(
                    waterCut,
                    center,
                    Offset(center.x + radiusX * 0.25f, center.y - radiusY * 0.36f),
                    strokeWidth = 1.15.dp.toPx()
                )
                drawLine(
                    lilyColor.copy(alpha = 0.72f),
                    Offset(center.x - radiusX * 0.72f, center.y + radiusY * 0.36f),
                    Offset(center.x, center.y + radiusY * 0.78f),
                    strokeWidth = 0.8.dp.toPx()
                )
            }

            val flowerColor = lerp(
                Color(0xFFF1E9D6).copy(alpha = 0.94f),
                Color(0xFFD9D8C2).copy(alpha = 0.86f),
                nightProgress
            )
            val flowerCenterColor = lerp(
                Color(0xFFC2A46B).copy(alpha = 0.88f),
                Color(0xFFC2AE7D).copy(alpha = 0.82f),
                nightProgress
            )
            listOf(0.28f to 0.851f, 0.73f to 0.842f).forEachIndexed { index, (xFraction, yFraction) ->
                val flowerBob = sin((waterPhase + index * 1.8f).toDouble()).toFloat() * 1.5.dp.toPx()
                val flower = Offset(width * xFraction, height * yFraction + flowerBob)
                val petalRadius = 4.dp.toPx()
                val petalDistance = 4.dp.toPx()
                repeat(6) { petal ->
                    val angle = (petal * 60f - 90f) * PI.toFloat() / 180f
                    val petalCenter = Offset(
                        flower.x + cos(angle) * petalDistance,
                        flower.y + sin(angle) * petalDistance
                    )
                    drawOval(
                        flowerColor,
                        topLeft = Offset(petalCenter.x - petalRadius, petalCenter.y - petalRadius * 0.72f),
                        size = Size(petalRadius * 2f, petalRadius * 1.44f)
                    )
                }
                drawCircle(flowerCenterColor, 1.7.dp.toPx(), flower)
            }
            if (nightProgress > 0.01f) {
                listOf(
                    Offset(width * 0.22f + drift * 0.4f, height * 0.70f),
                    Offset(width * 0.76f - drift * 0.3f, height * 0.75f),
                    Offset(width * 0.48f + drift * 0.25f, height * 0.81f)
                ).forEachIndexed { index, firefly ->
                    val shimmer = 0.22f + 0.20f * (
                        0.5f + 0.5f * sin((nightPhase + index * 2f).toDouble()).toFloat()
                    )
                    val fireflyGlow = width * 0.035f
                    drawCircle(
                        Brush.radialGradient(
                            listOf(Color(0xFFC8D8A2).copy(alpha = shimmer * 0.55f * nightProgress), Color.Transparent),
                            center = firefly,
                            radius = fireflyGlow
                        ),
                        radius = fireflyGlow,
                        center = firefly
                    )
                    drawCircle(Color(0xFFD5DDAA).copy(alpha = shimmer * nightProgress), 1.4.dp.toPx(), firefly)
                }
            }
    }
}

@Composable
private fun PetugasJourneyHero(
    name: String,
    tasks: List<WasteRecord>,
    isInitialLoading: Boolean,
    isRefreshing: Boolean,
    onOpenTasks: () -> Unit,
    onRefresh: () -> Unit
) {
    val isDark = LocalSmartWasteAppearance.current.darkMode
    val assigned = tasks.count { it.status == WasteFlow.ASSIGNED }
    val transported = tasks.count { it.status == WasteFlow.TRANSPORTED }
    val waitingAdmin = tasks.count {
        it.status == WasteFlow.TRANSPORTED && it.completionStatus.equals("menunggu", ignoreCase = true)
    }
    val needsRevision = tasks.count { it.completionStatus.equals("perlu_perbaikan", ignoreCase = true) }
    val inRoute = (transported - waitingAdmin - needsRevision).coerceAtLeast(0)
    val completed = tasks.count { it.status == WasteFlow.DONE }
    val total = tasks.count {
        it.status == WasteFlow.ASSIGNED || it.status == WasteFlow.TRANSPORTED || it.status == WasteFlow.DONE
    }
    val active = assigned + transported
    val heroStart = if (isDark) Color(0xFF142D36) else Color(0xFF2F704F)
    val heroEnd = if (isDark) Color(0xFF205E45) else Color(0xFF3D8961)
    val heroText = Color(0xFFF4F3E8)
    val heroMuted = Color(0xFFDCE9DC)
    val completion = if (total == 0) 0f else completed.toFloat() / total
    val shape = RoundedCornerShape(26.dp)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape)
            .background(Brush.linearGradient(listOf(heroStart, heroEnd)))
            .padding(horizontal = 16.dp, vertical = 14.dp)
    ) {
        Canvas(Modifier.fillMaxWidth().height(116.dp)) {
            val glowCenter = Offset(size.width * 0.96f, size.height * 0.04f)
            val glowRadius = size.width * 0.48f
            drawCircle(
                Brush.radialGradient(
                    listOf(Color.White.copy(alpha = if (isDark) 0.11f else 0.12f), Color.Transparent),
                    center = glowCenter,
                    radius = glowRadius
                ),
                radius = glowRadius,
                center = glowCenter
            )
        }
        Column(verticalArrangement = Arrangement.spacedBy(7.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier.size(36.dp).clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Filled.LocalShipping, contentDescription = null, tint = heroText, modifier = Modifier.size(19.dp))
                }
                Spacer(Modifier.width(10.dp))
                Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
                    Text(
                        "SMART WASTE  ·  PETUGAS",
                        color = heroMuted,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.65.sp
                    )
                    Text(
                        "Hai, $name",
                        color = heroText,
                        fontSize = 18.sp,
                        lineHeight = 22.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Text(
                    if (isInitialLoading) "Memuat" else "$active aktif",
                    color = heroText,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.15f))
                        .padding(horizontal = 11.dp, vertical = 7.dp)
                )
                IconButton(
                    onClick = onRefresh,
                    enabled = !isRefreshing,
                    modifier = Modifier.size(34.dp)
                ) {
                    if (isRefreshing) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(16.dp),
                            color = heroText,
                            strokeWidth = 2.dp
                        )
                    } else {
                        Icon(Icons.Filled.Sync, contentDescription = "Perbarui data", tint = heroText, modifier = Modifier.size(18.dp))
                    }
                }
            }
            Text(
                when {
                    isInitialLoading -> "Memuat tugas dan laporan terbaru."
                    active == 0 -> "Tidak ada penugasan aktif saat ini."
                    needsRevision > 0 -> "$needsRevision bukti perlu diperbaiki sebelum tugas ditutup."
                    inRoute > 0 && waitingAdmin > 0 -> "$inRoute muatan dalam rute · $waitingAdmin bukti menunggu Admin."
                    waitingAdmin > 0 && inRoute == 0 && assigned == 0 -> "$waitingAdmin bukti menunggu pemeriksaan Admin."
                    inRoute > 0 -> "$inRoute muatan sedang menuju TPA."
                    waitingAdmin > 0 -> "$waitingAdmin bukti menunggu pemeriksaan Admin."
                    else -> "$assigned penugasan siap untuk mulai diangkut."
                },
                color = heroMuted,
                fontSize = 11.sp,
                lineHeight = 15.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Canvas(Modifier.fillMaxWidth().height(8.dp)) {
                val centerY = size.height / 2f
                val start = 2.dp.toPx()
                val end = size.width - start
                drawLine(Color.White.copy(alpha = 0.34f), Offset(start, centerY), Offset(end, centerY), 2.dp.toPx())
                if (completion > 0f) {
                    val marker = start + (end - start) * completion
                    drawLine(Color.White.copy(alpha = 0.94f), Offset(start, centerY), Offset(marker, centerY), 2.dp.toPx())
                    drawCircle(Color.White, 4.dp.toPx(), Offset(marker, centerY))
                } else {
                    drawCircle(Color.White.copy(alpha = 0.70f), 3.dp.toPx(), Offset(start, centerY))
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    if (isInitialLoading) "Sedang menyinkronkan data…"
                    else "Siap angkut $assigned  ·  Dalam rute $inRoute  ·  Menunggu Admin $waitingAdmin",
                    color = heroMuted,
                    fontSize = 9.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(Modifier.weight(1f))
                TextButton(
                    onClick = onOpenTasks,
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 2.dp, vertical = 0.dp)
                ) {
                    Text(
                        if (isInitialLoading) "Memuat…" else "$completed/$total selesai  ·  Tugas →",
                        color = heroText,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

@Composable
private fun PetugasOperationalOverview(
    tasks: List<WasteRecord>,
    onOpenTasks: () -> Unit
) {
    val isDark = LocalSmartWasteAppearance.current.darkMode
    val activeTasks = tasks.filter {
        it.status == WasteFlow.ASSIGNED || it.status == WasteFlow.TRANSPORTED
    }
    val activeWeight = activeTasks.sumOf { it.weightKg }
    val activeTypes = activeTasks.map { it.type.trim() }.filter(String::isNotBlank).distinct().size
    val specialHandling = activeTasks.count { !it.hazardLevel.equals("RENDAH", ignoreCase = true) }
    val destination = activeTasks.firstOrNull { it.tpaName.isNotBlank() }?.tpaName
    val accent = if (isDark) Color(0xFFBFDBC6) else Color(0xFF397252)
    val shape = RoundedCornerShape(24.dp)
    val cardStart = if (isDark) Color(0xFF192B33) else AppSurface
    val cardEnd = if (isDark) Color(0xFF29473B) else Color(0xFFE8F1E4)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape)
            .background(Brush.linearGradient(listOf(cardStart, cardEnd)))
            .clickable(onClick = onOpenTasks)
            .padding(horizontal = 15.dp, vertical = 14.dp),
        verticalArrangement = Arrangement.spacedBy(11.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(1.dp)) {
                Text("PEKERJAAN AKTIF", color = accent, fontSize = 9.sp, fontWeight = FontWeight.Bold, letterSpacing = 0.8.sp)
                Text("Operasional saat ini", color = AppText, fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
            }
            Text("Lihat tugas  ›", color = accent, fontSize = 10.sp, fontWeight = FontWeight.SemiBold)
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top
        ) {
            PetugasOverviewCell(activeWeight.pretty(), "KG MUATAN", Icons.Filled.LocalShipping, accent, Modifier.weight(1f))
            PetugasOverviewCell(activeTypes.toString(), "JENIS", Icons.Filled.Inbox, accent, Modifier.weight(1f))
            PetugasOverviewCell(specialHandling.toString(), "PERLU PERHATIAN", Icons.Filled.ReportProblem, accent, Modifier.weight(1f))
        }

        Row(
            modifier = Modifier.fillMaxWidth().clip(CircleShape)
                .background(accent.copy(alpha = if (isDark) 0.15f else 0.10f))
                .padding(horizontal = 10.dp, vertical = 7.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(7.dp)
        ) {
            Icon(Icons.Filled.Place, contentDescription = null, tint = accent, modifier = Modifier.size(14.dp))
            Text("TUJUAN", color = AppTextMuted, fontSize = 9.sp, fontWeight = FontWeight.Bold, letterSpacing = 0.35.sp)
            Text(
                destination ?: "Belum ditentukan",
                modifier = Modifier.weight(1f),
                color = AppText,
                fontSize = 10.sp,
                fontWeight = FontWeight.Medium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
private fun PetugasOverviewCell(
    value: String,
    label: String,
    icon: ImageVector,
    accent: Color,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(horizontal = 5.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Box(
            modifier = Modifier.size(29.dp).clip(CircleShape)
                .background(accent.copy(alpha = 0.12f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = null, tint = accent, modifier = Modifier.size(15.dp))
        }
        Text(value, color = AppText, fontSize = 18.sp, fontWeight = FontWeight.Bold, maxLines = 1)
        Text(
            label,
            color = AppTextMuted,
            fontSize = 8.sp,
            lineHeight = 10.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun PetugasNavigationBar(selectedSection: String, onSectionSelected: (String) -> Unit) {
    val destinations = listOf(
        Triple("ringkasan", "Beranda", Icons.Rounded.Home),
        Triple("tugas", "Tugas", Icons.Rounded.TaskAlt),
        Triple("riwayat", "Riwayat", Icons.Rounded.History),
        Triple("profil", "Profil", Icons.Rounded.Person)
    )
    FloatingRoleNavigationBar(selectedSection, destinations, onSectionSelected)
}

@Composable
private fun AccountProfileSettings(account: UserAccount, onLogout: () -> Unit) {
    val appearance = LocalSmartWasteAppearance.current
    val isDark = appearance.darkMode
    val cardShape = RoundedCornerShape(24.dp)
    val surface = AppSurface.copy(alpha = if (isDark) 0.88f else 0.92f)

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        AdminSectionHeading(
            eyebrow = "AKUN DAN PREFERENSI",
            title = "Profil ${account.role.label.lowercase()}",
            subtitle = "Kelola tampilan aplikasi dan lihat informasi akun yang sedang digunakan."
        )

        Column(
            modifier = Modifier.fillMaxWidth()
                .clip(cardShape)
                .background(
                    Brush.horizontalGradient(
                        if (isDark) listOf(Color(0xFF213A36), Color(0xFF26363D))
                        else listOf(Color(0xFFE5EEE1), Color(0xFFF1EBDD))
                    )
                )
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(14.dp)) {
                UserAvatar(account.name, Modifier.size(58.dp))
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        account.name,
                        color = AppText,
                        fontSize = 19.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        account.role.description,
                        color = AppTextMuted,
                        fontSize = 13.sp,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
            ProfileInfoRow("Username", "@${account.username}")
            ProfileInfoRow("Peran", account.role.label)
        }

        Column(
            modifier = Modifier.fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(surface)
                .clickable(onClick = appearance.onToggleTheme)
                .padding(horizontal = 16.dp, vertical = 15.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Icon(
                    imageVector = if (isDark) Icons.Rounded.NightsStay else Icons.Rounded.WbSunny,
                    contentDescription = null,
                    tint = LoginAccent,
                    modifier = Modifier.size(22.dp)
                )
                Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(3.dp)) {
                    Text("Tampilan aplikasi", color = AppText, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                    Text(if (isDark) "Mode malam aktif" else "Mode terang aktif", color = AppTextMuted, fontSize = 12.sp)
                }
                TextButton(onClick = appearance.onToggleTheme) {
                    Text(if (isDark) "Terang" else "Malam", color = LoginAccent, fontWeight = FontWeight.Bold)
                }
            }
        }

        TextButton(
            onClick = onLogout,
            modifier = Modifier.fillMaxWidth()
                .clip(RoundedCornerShape(18.dp))
                .background(if (isDark) Color(0xFF3A2927) else Color(0xFFF3E8E3))
                .padding(vertical = 5.dp)
        ) {
            Icon(Icons.Filled.SwitchAccount, contentDescription = null, tint = WasteRed)
            Spacer(Modifier.width(8.dp))
            Text("Keluar dari akun", color = WasteRed, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun ProfileInfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label, color = AppTextMuted, fontSize = 13.sp)
        Text(value, color = AppText, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
private fun PetugasTaskCard(
    report: WasteRecord,
    onOpenPhoto: (String) -> Unit,
    onMarkTransported: ((WasteRecord) -> Unit)? = null,
    isTransporting: Boolean = false,
    isActionEnabled: Boolean = true,
    onComplete: ((WasteRecord) -> Unit)? = null
) {
    val isComplete = report.status == WasteFlow.DONE
    val isRejected = report.status == WasteFlow.REJECTED
    val isDark = LocalSmartWasteAppearance.current.darkMode
    val needsRevision = report.completionStatus.equals("perlu_perbaikan", ignoreCase = true)
    val waitingForAdmin = report.completionStatus.equals("menunggu", ignoreCase = true)
    val statusAccent = when (report.status) {
        WasteFlow.DONE -> if (isDark) Color(0xFF91C59B) else WasteGreen
        WasteFlow.REJECTED -> if (isDark) Color(0xFFE3A19A) else WasteRed
        WasteFlow.TRANSPORTED -> if (isDark) Color(0xFF82C7B9) else WasteTeal
        else -> if (isDark) Color(0xFFC2CF91) else LoginAccent
    }
    val shape = RoundedCornerShape(22.dp)
    val cardStart = if (isDark) Color(0xFF192B34) else AppSurface
    val cardEnd = if (isDark) Color(0xFF244638) else statusAccent.copy(alpha = 0.08f)
    val markerIcon = when (report.status) {
        WasteFlow.DONE -> Icons.Rounded.TaskAlt
        WasteFlow.REJECTED -> Icons.Filled.ReportProblem
        WasteFlow.TRANSPORTED -> Icons.Filled.LocalShipping
        else -> Icons.AutoMirrored.Filled.Assignment
    }
    val guidanceAccent = when {
        needsRevision -> WasteRed
        waitingForAdmin -> WasteOrange
        isComplete || isRejected -> statusAccent
        else -> statusAccent
    }
    val guidanceIcon = when {
        needsRevision -> Icons.Filled.ReportProblem
        waitingForAdmin -> Icons.Filled.Sync
        isComplete -> Icons.Filled.CheckCircle
        isRejected -> Icons.Filled.ReportProblem
        report.status == WasteFlow.TRANSPORTED -> Icons.Filled.Place
        else -> Icons.Filled.LocalShipping
    }
    val guidanceTitle = when {
        needsRevision -> "Perlu perbaikan"
        waitingForAdmin -> "Menunggu pemeriksaan Admin"
        isComplete -> "Pekerjaan selesai"
        isRejected -> "Laporan ditolak"
        else -> "Langkah berikutnya"
    }
    val guidanceMessage = when {
        needsRevision -> "${report.completionReviewNote.ifBlank { "Admin meminta foto bukti diperbaiki." }} Kirim ulang bukti setelah diperbaiki."
        waitingForAdmin -> "Bukti sudah terkirim. Tunggu Admin memeriksa foto sebelum tugas ditutup."
        isComplete -> "Bukti telah disetujui Admin dan pekerjaan tercatat selesai."
        isRejected -> report.workerDecisionNote.ifBlank { "Laporan tidak diteruskan ke proses pengangkutan." }
        report.status == WasteFlow.TRANSPORTED -> "Antar muatan ke ${report.tpaName.ifBlank { "TPA tujuan" }}, lalu kirim foto bukti penerimaan."
        else -> "Ambil muatan dengan ${report.vehiclePlate.ifBlank { "armada yang ditetapkan" }}, lalu mulai pengangkutan."
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape)
            .background(Brush.linearGradient(listOf(cardStart, cardEnd)))
    ) {
        Canvas(Modifier.fillMaxWidth().height(82.dp)) {
            val glowRadius = size.width * 0.32f
            drawCircle(
                Brush.radialGradient(
                    listOf(statusAccent.copy(alpha = if (isDark) 0.13f else 0.10f), Color.Transparent),
                    center = Offset(size.width, 0f),
                    radius = glowRadius
                ),
                radius = glowRadius,
                center = Offset(size.width, 0f)
            )
        }
        Column(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 13.dp),
            verticalArrangement = Arrangement.spacedBy(7.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(statusAccent.copy(alpha = if (isDark) 0.20f else 0.14f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(markerIcon, contentDescription = null, tint = statusAccent, modifier = Modifier.size(18.dp))
                }
                Spacer(Modifier.width(10.dp))
                Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(1.dp)) {
                    Text(report.code, color = AppText, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                    Text("${report.type}  ·  ${report.weightKg.pretty()} kg", color = AppTextMuted, fontSize = 10.sp)
                }
                StatusPill(report.status, statusAccent)
            }
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(7.dp)) {
                Box(
                    modifier = Modifier.size(25.dp).clip(CircleShape)
                        .background(statusAccent.copy(alpha = 0.11f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Filled.Place, contentDescription = null, tint = statusAccent, modifier = Modifier.size(13.dp))
                }
                Text(
                    report.location,
                    color = AppText,
                    fontSize = 10.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            if (!isRejected) Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(9.dp)
            ) {
                Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(3.dp)) {
                    Text("KENDARAAN", color = AppTextMuted, fontSize = 8.sp, fontWeight = FontWeight.Bold)
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                        Icon(Icons.Filled.LocalShipping, contentDescription = null, tint = statusAccent, modifier = Modifier.size(14.dp))
                        Text(
                            report.vehiclePlate.ifBlank { "Belum ditetapkan" },
                            color = AppText,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.SemiBold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
                Text("→", color = statusAccent, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(3.dp)) {
                    Text("TPA TUJUAN", color = AppTextMuted, fontSize = 8.sp, fontWeight = FontWeight.Bold)
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                        Icon(Icons.Filled.Place, contentDescription = null, tint = statusAccent, modifier = Modifier.size(14.dp))
                        Text(
                            report.tpaName.ifBlank { "Belum ditetapkan" },
                            color = AppText,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.SemiBold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
            if (!isComplete && !isRejected) {
                if (!report.hazardLevel.equals("RENDAH", true)) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                        Icon(Icons.Filled.ReportProblem, contentDescription = null, tint = WasteRed, modifier = Modifier.size(14.dp))
                        Text("Perlu penanganan khusus · ${report.hazardLevel}", color = WasteRed, fontSize = 10.sp, fontWeight = FontWeight.SemiBold)
                    }
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth()
                    .clip(RoundedCornerShape(13.dp))
                    .background(guidanceAccent.copy(alpha = if (isDark) 0.16f else 0.10f))
                    .padding(horizontal = 10.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(guidanceIcon, contentDescription = null, tint = guidanceAccent, modifier = Modifier.size(17.dp))
                Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
                    Text(guidanceTitle, color = guidanceAccent, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    Text(
                        guidanceMessage,
                        color = AppText,
                        fontSize = 10.sp,
                        lineHeight = 14.sp,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (!report.photoUrl.isNullOrBlank()) {
                    TextButton(onClick = { onOpenPhoto(report.photoUrl) }) {
                        Text("Foto laporan", color = statusAccent, fontSize = 10.sp)
                    }
                }
                if (!report.completionProofPhotoUrl.isNullOrBlank()) {
                    TextButton(onClick = { onOpenPhoto(report.completionProofPhotoUrl) }) {
                        Text("Bukti petugas", color = WasteTeal, fontSize = 10.sp)
                    }
                }
                Spacer(Modifier.weight(1f))
                when {
                    report.status == WasteFlow.ASSIGNED && onMarkTransported != null ->
                        AppButton(
                            if (isTransporting) "Memperbarui status…" else "Mulai pengangkutan",
                            { onMarkTransported(report) },
                            modifier = Modifier.widthIn(min = 132.dp, max = 172.dp).height(42.dp),
                            enabled = isActionEnabled && !isTransporting,
                            containerColor = LoginAccent
                        )

                    report.status == WasteFlow.TRANSPORTED && onComplete != null && waitingForAdmin ->
                        StatusPill("Menunggu admin", WasteOrange)

                    report.status == WasteFlow.TRANSPORTED && onComplete != null ->
                        AppButton(
                            if (needsRevision) "Kirim ulang bukti TPA" else "Konfirmasi tiba di TPA",
                            { onComplete(report) },
                            modifier = Modifier.widthIn(min = 148.dp, max = 196.dp).height(42.dp),
                            enabled = isActionEnabled,
                            containerColor = WasteGreenDark
                        )
                }
            }
        }
    }
}

@Composable
private fun PetugasRefreshButton(isRefreshing: Boolean, onRefresh: () -> Unit) {
    IconButton(
        onClick = onRefresh,
        enabled = !isRefreshing,
        modifier = Modifier.size(40.dp)
    ) {
        if (isRefreshing) {
            CircularProgressIndicator(
                modifier = Modifier.size(18.dp),
                color = LoginAccent,
                strokeWidth = 2.dp
            )
        } else {
            Icon(
                Icons.Filled.Sync,
                contentDescription = "Perbarui data",
                tint = LoginAccent,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
private fun PetugasQueueSearch(
    query: String,
    onQueryChange: (String) -> Unit,
    placeholder: String
) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(placeholder, fontSize = 12.sp, maxLines = 1, overflow = TextOverflow.Ellipsis) },
        leadingIcon = {
            Icon(Icons.Filled.Search, contentDescription = null, tint = AppTextMuted, modifier = Modifier.size(19.dp))
        },
        trailingIcon = if (query.isNotBlank()) {
            {
                IconButton(onClick = { onQueryChange("") }) {
                    Icon(Icons.Filled.Close, contentDescription = "Hapus pencarian", tint = AppTextMuted)
                }
            }
        } else null,
        singleLine = true,
        shape = RoundedCornerShape(16.dp),
        textStyle = TextStyle(fontFamily = InterFont, fontSize = 12.sp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = LoginAccent.copy(alpha = 0.55f),
            unfocusedBorderColor = AppBorder.copy(alpha = 0.55f),
            focusedContainerColor = AppSurface.copy(alpha = 0.92f),
            unfocusedContainerColor = AppSurface.copy(alpha = 0.82f)
        )
    )
}

@Composable
private fun PetugasLoadingState(title: String, message: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(21.dp),
            color = LoginAccent,
            strokeWidth = 2.dp
        )
        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Text(title, color = AppText, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
            Text(message, color = AppTextMuted, fontSize = 10.sp, lineHeight = 14.sp)
        }
    }
}

@Composable
private fun PetugasLoadFailure(message: String, onRefresh: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(WasteRed.copy(alpha = 0.08f))
            .padding(start = 12.dp, end = 7.dp, top = 8.dp, bottom = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Text("Belum tersinkron", color = AppText, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
            Text(message, color = AppTextMuted, fontSize = 10.sp, lineHeight = 14.sp)
        }
        TextButton(onClick = onRefresh) {
            Text("Coba lagi", color = LoginAccent, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
        }
    }
}

@Composable
private fun PetugasEmptyState(title: String, message: String) {
    val isDark = LocalSmartWasteAppearance.current.darkMode
    val accent = if (isDark) Color(0xFFA7D1B3) else Color(0xFF397252)
    val shape = RoundedCornerShape(22.dp)
    val start = if (isDark) Color(0xFF192B34) else AppSurface
    val end = if (isDark) Color(0xFF244638) else Color(0xFFE8F1E4)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape)
            .background(Brush.linearGradient(listOf(start, end)))
            .padding(horizontal = 15.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier.size(38.dp).clip(CircleShape).background(accent.copy(alpha = 0.15f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Filled.LocalShipping, contentDescription = null, tint = accent, modifier = Modifier.size(19.dp))
        }
        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Text(title, color = AppText, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
            Text(message, color = AppTextMuted, fontSize = 10.sp, lineHeight = 14.sp)
        }
    }
}

@Composable
private fun StaffAccountFormDialog(
    allowAdminAccount: Boolean,
    onDismiss: () -> Unit,
    onSave: suspend (String, String, String, String, String, String) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var zone by remember { mutableStateOf("") }
    var role by remember { mutableStateOf("petugas") }
    var saving by remember { mutableStateOf(false) }
    var saveError by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                if (allowAdminAccount) "Daftarkan akun staf" else "Tambah akun petugas",
                color = LoginInk,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            ScrollableDialogContent {
                Text(
                    "Akun staf masuk memakai username dan kata sandi yang dibuat di sini.",
                    color = LoginMuted,
                    fontSize = 11.sp
                )
                if (allowAdminAccount) {
                    SelectionField(
                        "Role akun",
                        role,
                        listOf("admin", "petugas"),
                        { if (it == "admin") "Admin" else "Petugas" }) { role = it }
                } else {
                    Text("Role akun · Petugas", color = AppTextMuted, fontSize = 11.sp)
                }
                FormField(name, { name = it }, "Nama lengkap")
                FormField(username, { username = it }, "Username")
                FormField(email, { email = it }, "Email akun")
                FormField(password, { password = it }, "Password awal (minimal 5 karakter)")
                if (role == "petugas") FormField(zone, { zone = it }, "Zona tugas")
                if (saveError.isNotBlank()) InlineError(saveError)
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (!saving) scope.launch {
                        saving = true
                        saveError = ""
                        try {
                            onSave(
                                name.trim(),
                                username.trim(),
                                email.trim(),
                                password,
                                zone.trim(),
                                role
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
                enabled = name.isNotBlank() &&
                        username.trim().length >= 3 &&
                        email.trim().matches(Regex("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")) &&
                        password.length >= 5 &&
                        (role == "admin" || zone.isNotBlank()) &&
                        !saving
            ) { Text(if (saving) "Menyimpan…" else "Simpan", color = LoginAccent) }
        },
        dismissButton = {
            TextButton(onClick = onDismiss, enabled = !saving) {
                Text(
                    "Batal",
                    color = LoginMuted
                )
            }
        },
        containerColor = AppSurface
    )
}

@Composable
private fun VehicleFormDialog(
    vehicle: WasteVehicle?,
    onDismiss: () -> Unit,
    onSave: suspend (String, Double, String, String) -> Unit
) {
    var plate by remember(vehicle?.id) { mutableStateOf(vehicle?.plate.orEmpty()) }
    var capacity by remember(vehicle?.id) {
        mutableStateOf(
            vehicle?.capacityKg?.toString().orEmpty()
        )
    }
    var condition by remember(vehicle?.id) { mutableStateOf(vehicle?.condition ?: "Baik") }
    var route by remember(vehicle?.id) { mutableStateOf(vehicle?.route.orEmpty()) }
    var saving by remember(vehicle?.id) { mutableStateOf(false) }
    var saveError by remember(vehicle?.id) { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                if (vehicle == null) "Tambah kendaraan" else "Ubah kendaraan",
                color = LoginInk,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            ScrollableDialogContent {
                FormField(plate, { plate = it }, "Nomor polisi")
                FormField(capacity, { capacity = it }, "Kapasitas (kg)")
                SelectionField(
                    "Kondisi kendaraan",
                    condition,
                    listOf("Baik", "Servis"),
                    { it }) { condition = it }
                FormField(route, { route = it }, "Rute")
                if (vehicle != null) Text(
                    "Terpakai ${vehicle.loadKg.pretty()} kg · dicadangkan ${vehicle.reservedKg.pretty()} kg",
                    color = LoginMuted,
                    fontSize = 11.sp
                )
                if (saveError.isNotBlank()) InlineError(saveError)
            }
        },
        confirmButton = {
            val proposedCapacity = capacity.toDoubleOrNull() ?: 0.0
            val canFitCurrentLoad =
                vehicle == null || proposedCapacity >= vehicle.loadKg + vehicle.reservedKg
            val canChangeCondition = vehicle == null || condition.equals(
                "baik",
                true
            ) || (vehicle.loadKg == 0.0 && vehicle.reservedKg == 0.0)
            TextButton(
                onClick = {
                    if (!saving) scope.launch {
                        saving = true
                        saveError = ""
                        try {
                            onSave(plate.trim(), proposedCapacity, condition.trim(), route.trim())
                        } catch (cancelled: CancellationException) {
                            throw cancelled
                        } catch (error: Exception) {
                            saveError = error.message ?: "Data kendaraan gagal disimpan."
                        } finally {
                            saving = false
                        }
                    }
                },
                enabled = plate.isNotBlank() && proposedCapacity > 0 && canFitCurrentLoad && canChangeCondition && !saving
            ) { Text(if (saving) "Menyimpan…" else "Simpan", color = LoginAccent) }
        },
        dismissButton = {
            TextButton(onClick = onDismiss, enabled = !saving) {
                Text(
                    "Batal",
                    color = LoginMuted
                )
            }
        },
        containerColor = AppSurface
    )
}

@Composable
private fun FacilityFormDialog(
    facility: WasteFacility?,
    onDismiss: () -> Unit,
    onSave: suspend (String, String, Double) -> Unit
) {
    var name by remember(facility?.id) { mutableStateOf(facility?.name.orEmpty()) }
    var address by remember(facility?.id) { mutableStateOf(facility?.address.orEmpty()) }
    var capacity by remember(facility?.id) {
        mutableStateOf(
            facility?.capacityTon?.toString().orEmpty()
        )
    }
    var saving by remember(facility?.id) { mutableStateOf(false) }
    var saveError by remember(facility?.id) { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                if (facility == null) "Tambah TPA" else "Ubah TPA",
                color = LoginInk,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            ScrollableDialogContent {
                FormField(name, { name = it }, "Nama TPA")
                FormField(address, { address = it }, "Alamat")
                FormField(capacity, { capacity = it }, "Kapasitas (ton)")
                if (facility != null) Text(
                    "Terisi: ${facility.filledTon.pretty()} ton",
                    color = LoginMuted,
                    fontSize = 11.sp
                )
                if (saveError.isNotBlank()) InlineError(saveError)
            }
        },
        confirmButton = {
            val proposedCapacity = capacity.toDoubleOrNull() ?: 0.0
            val minimumCapacity = (facility?.filledTon ?: 0.0) + (facility?.reservedTon ?: 0.0)
            TextButton(
                onClick = {
                    if (!saving) scope.launch {
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
                enabled = name.isNotBlank() && proposedCapacity >= minimumCapacity && proposedCapacity > 0 && !saving
            ) { Text(if (saving) "Menyimpan…" else "Simpan", color = LoginAccent) }
        },
        dismissButton = {
            TextButton(onClick = onDismiss, enabled = !saving) {
                Text(
                    "Batal",
                    color = LoginMuted
                )
            }
        },
        containerColor = AppSurface
    )
}

@Composable
private fun SingleFieldDialog(
    title: String,
    initial: String,
    label: String,
    onDismiss: () -> Unit,
    onSave: suspend (String) -> Unit
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
                    label
                ); if (saveError.isNotBlank()) InlineError(saveError)
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (!saving) scope.launch {
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
                enabled = value.isNotBlank() && !saving
            ) { Text(if (saving) "Menyimpan…" else "Simpan", color = LoginAccent) }
        },
        dismissButton = {
            TextButton(onClick = onDismiss, enabled = !saving) {
                Text(
                    "Batal",
                    color = LoginMuted
                )
            }
        },
        containerColor = AppSurface
    )
}

@Composable
private fun FormField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    enabled: Boolean = true
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        enabled = enabled,
        modifier = Modifier.fillMaxWidth(),
        label = { Text(label, fontSize = 11.sp) },
        singleLine = true,
        shape = RoundedCornerShape(13.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = LoginInk,
            unfocusedTextColor = LoginInk,
        focusedContainerColor = AppSurface,
        unfocusedContainerColor = AppSurface,
            focusedBorderColor = LoginAccent,
            unfocusedBorderColor = AppBorder,
            focusedLabelColor = LoginAccent,
            unfocusedLabelColor = LoginMuted,
            cursorColor = LoginAccent
        )
    )
}

@Composable
private fun StatusPill(status: String, color: Color) {
    Box(
        modifier = Modifier.clip(RoundedCornerShape(50.dp)).background(color.copy(alpha = 0.12f))
            .padding(horizontal = 8.dp, vertical = 5.dp)
    ) {
        Text(WasteFlow.label(status), color = color, fontSize = 11.sp, fontWeight = FontWeight.Bold)
    }
}

private fun reportAccent(status: String): Color = when (status) {
    WasteFlow.WAITING -> WasteOrange
    WasteFlow.ASSIGNED -> WastePurple
    WasteFlow.TRANSPORTED -> WasteTeal
    WasteFlow.PROCESSING -> WasteNavy
    WasteFlow.DONE -> WasteGreen
    else -> WasteRed
}

@Composable
private fun SectionTitle(title: String, subtitle: String) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(title, color = AppText, fontSize = 17.sp, fontWeight = FontWeight.SemiBold)
        Text(
            subtitle,
            color = AppTextMuted,
            fontSize = 12.sp,
            lineHeight = 18.sp
        )
    }
}

@Composable
private fun SoftProgress(progress: Float) {
    Box(
        modifier = Modifier.fillMaxWidth().height(8.dp).clip(RoundedCornerShape(50.dp))
            .background(WasteBlue)
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(progress.coerceIn(0f, 1f)).height(8.dp)
                .background(LoginAccent)
        )
    }
}

@Composable
private fun DataProgress(progress: Float, accent: Color) {
    Box(
        modifier = Modifier.fillMaxWidth().height(8.dp).clip(RoundedCornerShape(50.dp))
            .background(WasteBlue)
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(progress.coerceIn(0f, 1f)).height(8.dp)
                .clip(RoundedCornerShape(50.dp))
                .background(accent)
        )
    }
}

@Composable
private fun InlineError(message: String) {
    AppCard(
        modifier = Modifier.fillMaxWidth(),
        containerColor = WasteRed.copy(alpha = 0.08f),
        borderColor = WasteRed.copy(alpha = 0.24f),
        accentColor = WasteRed
    ) {
        Text(
            message,
            modifier = Modifier.padding(12.dp),
            color = WasteRed,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun EmptyAdminState(message: String) {
    AppCard(
        modifier = Modifier.fillMaxWidth(),
        containerColor = AppSurface,
        borderColor = AppBorder
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(23.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                Icons.Filled.Inbox,
                contentDescription = null,
                tint = LoginAccent,
                modifier = Modifier.size(28.dp)
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(message, color = AppTextMuted, fontSize = 11.sp)
        }
    }
}

private fun Double.pretty(): String {
    if (!isFinite()) return "0"
    val locale = java.util.Locale.forLanguageTag("id-ID")
    val symbols = java.text.DecimalFormatSymbols.getInstance(locale)
    return java.text.DecimalFormat("#,##0.#", symbols).format(this)
}

private fun String.displayPersonName(): String {
    val value = trim().replace(Regex("\\s+"), " ")
    if (value.isBlank()) return "—"
    val locale = java.util.Locale.forLanguageTag("id-ID")
    return value.split(' ').joinToString(" ") { word ->
        word.split('-').joinToString("-") { part ->
            part.lowercase(locale).replaceFirstChar { first -> first.titlecase(locale) }
        }
    }
}

private fun String.reporterDisplayName(): String = when (
    trim().lowercase(java.util.Locale.forLanguageTag("id-ID"))
) {
    "admin smart waste", "admin" -> "Admin"
    "user smart waste", "user" -> "Warga"
    else -> displayPersonName()
}

private fun WasteRecord.assignmentSummary(): String = buildList {
    workerName.trim().takeIf { it.isNotBlank() }?.let {
        add("Petugas ${it.displayPersonName()}")
    }
    vehiclePlate.trim().takeIf { it.isNotBlank() }?.let {
        add("Armada ${it.uppercase(java.util.Locale.forLanguageTag("id-ID"))}")
    }
    tpaName.trim().takeIf { it.isNotBlank() }?.let {
        add("TPA ${it.displayPersonName()}")
    }
}.joinToString("  ·  ")

private fun String.toDisplayDateTime(): String {
    val raw = trim()
    if (raw.isBlank()) return raw
    val patterns = listOf(
        "yyyy-MM-dd'T'HH:mm:ss.SSSXXX",
        "yyyy-MM-dd'T'HH:mm:ssXXX",
        "yyyy-MM-dd HH:mm:ss",
        "yyyy-MM-dd HH:mm",
        "yyyy-MM-dd"
    )
    val parsed = patterns.firstNotNullOfOrNull { pattern ->
        runCatching {
            java.text.SimpleDateFormat(pattern, java.util.Locale.US).apply {
                isLenient = false
            }.parse(raw)
        }.getOrNull()
    } ?: return raw
    val outputPattern = if (raw.length <= 10) "d MMM yyyy" else "d MMM yyyy, HH.mm"
    return java.text.SimpleDateFormat(
        outputPattern,
        java.util.Locale.forLanguageTag("id-ID")
    ).format(parsed)
}

private fun shortMonth(period: String): String {
    val month = period.substringAfter('-', "").toIntOrNull() ?: return period.takeLast(2)
    return listOf(
        "Jan",
        "Feb",
        "Mar",
        "Apr",
        "Mei",
        "Jun",
        "Jul",
        "Agu",
        "Sep",
        "Okt",
        "Nov",
        "Des"
    )
        .getOrNull(month - 1) ?: period.takeLast(2)
}
