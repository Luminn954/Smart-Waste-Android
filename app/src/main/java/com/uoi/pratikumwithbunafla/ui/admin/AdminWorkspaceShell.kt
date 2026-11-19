package com.uoi.pratikumwithbunafla.ui.admin

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.SwitchAccount
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.NightsStay
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.WbSunny
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.uoi.pratikumwithbunafla.R
import com.uoi.pratikumwithbunafla.data.model.*
import com.uoi.pratikumwithbunafla.ui.components.*
import com.uoi.pratikumwithbunafla.ui.petugas.*
import com.uoi.pratikumwithbunafla.ui.theme.AppBorder
import com.uoi.pratikumwithbunafla.ui.theme.AppSurface
import com.uoi.pratikumwithbunafla.ui.theme.AppText
import com.uoi.pratikumwithbunafla.ui.theme.AppTextMuted
import com.uoi.pratikumwithbunafla.ui.theme.DarkSmartWastePalette
import com.uoi.pratikumwithbunafla.ui.theme.InterFont
import com.uoi.pratikumwithbunafla.ui.theme.LightSmartWastePalette
import com.uoi.pratikumwithbunafla.ui.theme.LocalSmartWasteAppearance
import com.uoi.pratikumwithbunafla.ui.theme.LocalSmartWastePalette
import com.uoi.pratikumwithbunafla.ui.theme.SmartWastePalette
import com.uoi.pratikumwithbunafla.ui.theme.WasteBlue
import com.uoi.pratikumwithbunafla.ui.theme.WasteGreen
import com.uoi.pratikumwithbunafla.ui.theme.WasteGreenDark
import com.uoi.pratikumwithbunafla.ui.theme.WasteNavy
import com.uoi.pratikumwithbunafla.ui.theme.WasteOrange
import com.uoi.pratikumwithbunafla.ui.theme.WastePurple
import com.uoi.pratikumwithbunafla.ui.theme.WasteRed
import com.uoi.pratikumwithbunafla.ui.theme.WasteTeal
import kotlinx.coroutines.launch

internal val LoginInk: Color
    @Composable get() = MaterialTheme.colorScheme.onBackground
internal val LoginMuted: Color
    @Composable get() = MaterialTheme.colorScheme.onSurfaceVariant
internal val LoginAccent: Color
    @Composable get() = MaterialTheme.colorScheme.primary
internal val LocalIsAdminWorkspace = staticCompositionLocalOf { false }

@Stable
class AdminNavigationMotionState {
    internal val activePosition = Animatable(0f)
    internal var targetIndex by mutableIntStateOf(-1)
}

val LocalAdminNavigationMotionState = staticCompositionLocalOf<AdminNavigationMotionState?> { null }

internal val IsNightAdmin: Boolean
    @Composable get() = LocalIsAdminWorkspace.current && LocalSmartWasteAppearance.current.darkMode
internal val RoleAccentSurface: Color
    @Composable get() = if (IsNightAdmin) Color(0xFF33443A) else WasteBlue
internal val RoleRed: Color
    @Composable get() = if (IsNightAdmin) Color(0xFFC78882) else WasteRed
internal val RoleOrange: Color
    @Composable get() = if (IsNightAdmin) Color(0xFFBBA46F) else WasteOrange
internal val RolePurple: Color
    @Composable get() = if (IsNightAdmin) Color(0xFFA89AAF) else WastePurple
internal val RoleTeal: Color
    @Composable get() = if (IsNightAdmin) Color(0xFF7FA99B) else WasteTeal
internal val RoleGreen: Color
    @Composable get() = if (IsNightAdmin) Color(0xFF88A988) else WasteGreen
internal val RoleGreenDark: Color
    @Composable get() = if (IsNightAdmin) Color(0xFF7FA27F) else WasteGreenDark
internal val RoleNavy: Color
    @Composable get() = if (IsNightAdmin) Color(0xFF91A2B6) else WasteNavy

internal val AdminNightPalette =
    DarkSmartWastePalette.copy(
        background = Color(0xFF161F1A),
        surface = Color(0xFF202B24),
        surfaceMuted = Color(0xFF2A352E),
        border = Color(0xFF3D4D42),
        text = Color(0xFFE0E7E1),
        textMuted = Color(0xFFA5B2A8),
    )

internal val PetugasDayPalette =
    LightSmartWastePalette.copy(
        background = Color(0xFFD7E5D8),
        surface = Color(0xFFE8ECEA),
        cardSurface = Color(0xFFFCFCFA),
        surfaceMuted = Color(0xFFE1E8E6),
        border = Color(0xFFC1CECB),
        text = Color(0xFF263735),
        textMuted = Color(0xFF566663),
    )

internal val PetugasNightPalette =
    DarkSmartWastePalette.copy(
        background = Color(0xFF1A2324),
        surface = Color(0xFF242E30),
        cardSurface = Color(0xFF2B3638),
        surfaceMuted = Color(0xFF333F41),
        border = Color(0xFF4A595A),
        text = Color(0xFFE8EFED),
        textMuted = Color(0xFFB5C2C1),
    )

internal val PetugasDayColorScheme =
    lightColorScheme(
        primary = Color(0xFF496F7A),
        onPrimary = Color.White,
        primaryContainer = Color(0xFFDCE8EA),
        onPrimaryContainer = Color(0xFF203C42),
        secondary = Color(0xFF927044),
        onSecondary = Color.White,
        secondaryContainer = Color(0xFFF0E6D6),
        onSecondaryContainer = Color(0xFF514027),
        tertiary = Color(0xFF58755E),
        onTertiary = Color.White,
        background = PetugasDayPalette.background,
        onBackground = PetugasDayPalette.text,
        surface = PetugasDayPalette.cardSurface,
        onSurface = PetugasDayPalette.text,
        surfaceVariant = PetugasDayPalette.surfaceMuted,
        onSurfaceVariant = PetugasDayPalette.textMuted,
        outline = PetugasDayPalette.border,
        error = Color(0xFFA75F50),
        onError = Color.White,
    )

internal val PetugasNightColorScheme =
    darkColorScheme(
        primary = Color(0xFF789FA1),
        onPrimary = Color(0xFF132829),
        primaryContainer = Color(0xFF314A4C),
        onPrimaryContainer = Color(0xFFDCE9E8),
        secondary = Color(0xFFCDAE75),
        onSecondary = Color(0xFF302514),
        secondaryContainer = Color(0xFF4A3B24),
        onSecondaryContainer = Color(0xFFE8D9B7),
        tertiary = Color(0xFF9BB69B),
        onTertiary = Color(0xFF1C2C21),
        background = PetugasNightPalette.background,
        onBackground = PetugasNightPalette.text,
        surface = PetugasNightPalette.cardSurface,
        onSurface = PetugasNightPalette.text,
        surfaceVariant = PetugasNightPalette.surfaceMuted,
        onSurfaceVariant = PetugasNightPalette.textMuted,
        outline = PetugasNightPalette.border,
        error = Color(0xFFD08F7E),
        onError = Color(0xFF351C18),
    )

internal val PetugasAccent: Color
    @Composable
    get() =
        if (LocalSmartWasteAppearance.current.darkMode) {
            Color(0xFF789FA1)
        } else {
            Color(0xFF496F7A)
        }

internal val PetugasAccentWarm: Color
    @Composable
    get() =
        if (LocalSmartWasteAppearance.current.darkMode) {
            Color(0xFFCDAE75)
        } else {
            Color(0xFF927044)
        }

internal val PetugasAccentAlert: Color
    @Composable
    get() =
        if (LocalSmartWasteAppearance.current.darkMode) {
            Color(0xFFD08F7E)
        } else {
            Color(0xFFA75F50)
        }

internal val AdminNightColorScheme =
    darkColorScheme(
        primary = Color(0xFF87A790),
        onPrimary = Color(0xFF17231A),
        primaryContainer = Color(0xFF2A3A30),
        onPrimaryContainer = Color(0xFFD0DED3),
        secondary = Color(0xFF7F9E91),
        onSecondary = Color(0xFF17241D),
        secondaryContainer = Color(0xFF293832),
        onSecondaryContainer = Color(0xFFCFDBD4),
        tertiary = Color(0xFFB5A273),
        onTertiary = Color(0xFF282114),
        tertiaryContainer = Color(0xFF3B3425),
        onTertiaryContainer = Color(0xFFDED2B2),
        background = AdminNightPalette.background,
        onBackground = AdminNightPalette.text,
        surface = AdminNightPalette.surface,
        onSurface = AdminNightPalette.text,
        surfaceVariant = AdminNightPalette.surfaceMuted,
        onSurfaceVariant = AdminNightPalette.textMuted,
        outline = AdminNightPalette.border,
        error = Color(0xFFCA8882),
        onError = Color(0xFF321B19),
        errorContainer = Color(0xFF402A28),
        onErrorContainer = Color(0xFFE8C2BD),
    )

internal val TaskAmber = Color(0xFFB86B1D)
internal val TaskBlue = Color(0xFF456FB5)
internal val TaskPurple = Color(0xFF7952A0)
internal val TaskTeal = Color(0xFF237E7B)
internal val TaskGreen = Color(0xFF43814C)
internal val TaskCoral = Color(0xFFC25556)
internal val TaskIndigo = Color(0xFF535DAA)
internal val TaskRose = Color(0xFFA64F75)

@Composable
internal fun ScrollableDialogContent(
    spacing: Dp = 8.dp,
    content: @Composable ColumnScope.() -> Unit,
) {
    val maxHeight = (LocalConfiguration.current.screenHeightDp.dp - 300.dp).coerceIn(120.dp, 360.dp)
    Column(
        modifier = Modifier.heightIn(max = maxHeight).verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(spacing),
        content = content,
    )
}

@Composable
internal fun AnimatedAdminPageBackground() {
    val dark = LocalSmartWasteAppearance.current.darkMode
    val background by
        animateColorAsState(
            targetValue =
                if (dark) AdminNightPalette.background
                else LocalSmartWastePalette.current.background,
            animationSpec = tween(durationMillis = 820, easing = FastOutSlowInEasing),
            label = "admin-page-background",
        )
    Box(Modifier.fillMaxSize().background(background))
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
internal fun AdminPage(
    current: String,
    account: UserAccount,
    onNavigate: (String) -> Unit,
    onLogout: () -> Unit,
    scrollKey: String = current,
    isInitialLoading: Boolean = false,
    isRefreshing: Boolean = false,
    onRefresh: () -> Unit = {},
    onOpenNotifications: (() -> Unit)? = null,
    bottomBar: (@Composable () -> Unit)? = null,
    showBottomBar: Boolean = true,
    showNotificationsButton: Boolean = true,
    backgroundContent: @Composable BoxScope.() -> Unit = {},
    fullBleedContent: (@Composable ColumnScope.() -> Unit)? = null,
    topBarContent: (@Composable () -> Unit)? = null,
    workspacePaletteOverride: SmartWastePalette? = null,
    showTopBar: Boolean = true,
    content: @Composable ColumnScope.() -> Unit,
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val pageScrollState = remember(current, scrollKey) { ScrollState(0) }
    val horizontalPadding =
        when {
            screenWidth < 350.dp -> 14.dp
            screenWidth >= 600.dp -> 28.dp
            else -> 20.dp
        }
    val isAdmin = account.role == UserRole.ADMIN
    val isNightAdmin = isAdmin && LocalSmartWasteAppearance.current.darkMode
    val isPetugas = account.role == UserRole.PETUGAS
    val isDarkMode = LocalSmartWasteAppearance.current.darkMode
    val entersFromTop = isAdmin && current in setOf("stats", "ops", "reports")
    var targetPageVisible by remember(current) { mutableStateOf(!entersFromTop) }
    LaunchedEffect(current, entersFromTop) {
        if (entersFromTop) {
            withFrameNanos {}
            targetPageVisible = true
        }
    }
    val workspacePalette =
        workspacePaletteOverride
            ?: if (isNightAdmin) AdminNightPalette else LocalSmartWastePalette.current
    val workspaceColorScheme =
        when {
            isNightAdmin -> AdminNightColorScheme
            isPetugas && isDarkMode -> PetugasNightColorScheme
            isPetugas -> PetugasDayColorScheme
            else -> MaterialTheme.colorScheme
        }

    CompositionLocalProvider(
        LocalIsAdminWorkspace provides isAdmin,
        LocalSmartWastePalette provides workspacePalette,
    ) {
        MaterialTheme(
            colorScheme = workspaceColorScheme,
            typography = MaterialTheme.typography,
            shapes = MaterialTheme.shapes,
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                if (isAdmin) AnimatedAdminPageBackground()
                backgroundContent()
                CompositionLocalProvider(
                    LocalTextStyle provides
                        LocalTextStyle.current.merge(
                            TextStyle(
                                fontFamily = InterFont,
                            ),
                        ),
                ) {
                    AnimatedVisibility(
                        visible = targetPageVisible,
                        modifier = Modifier.fillMaxSize(),
                        enter = fadeIn(tween(180, easing = FastOutSlowInEasing)),
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                        ) {
                            Box(
                                modifier = Modifier.weight(1f).fillMaxWidth(),
                                contentAlignment = Alignment.TopCenter,
                            ) {
                                PullToRefreshBox(
                                    isRefreshing = isRefreshing,
                                    onRefresh = onRefresh,
                                    modifier = Modifier.fillMaxSize(),
                                ) {
                                    Column(
                                        modifier =
                                            Modifier.widthIn(max = 720.dp)
                                                .fillMaxHeight()
                                                .fillMaxWidth()
                                                .then(
                                                    if (showTopBar)
                                                        Modifier.windowInsetsPadding(
                                                            WindowInsets.statusBars,
                                                        )
                                                    else Modifier,
                                                )
                                                .verticalScroll(pageScrollState),
                                    ) {
                                        if (showTopBar) {
                                            Box(
                                                modifier =
                                                    Modifier.fillMaxWidth()
                                                        .padding(
                                                            horizontal = horizontalPadding,
                                                            vertical = 11.dp,
                                                        ),
                                            ) {
                                                if (topBarContent != null) {
                                                    topBarContent()
                                                } else {
                                                    AdminTopBar(
                                                        account = account,
                                                        onLogout = onLogout,
                                                        onOpenProfile = { onNavigate("profile") },
                                                        showNotificationsButton =
                                                            showNotificationsButton,
                                                        onOpenNotifications = {
                                                            onOpenNotifications?.invoke()
                                                                ?: onNavigate("notifications")
                                                        },
                                                    )
                                                }
                                            }
                                        }
                                        if (isInitialLoading) {
                                            AdminLoadingState()
                                        } else {
                                            fullBleedContent?.invoke(this)
                                            Column(
                                                modifier =
                                                    Modifier.fillMaxWidth()
                                                        .padding(horizontal = horizontalPadding)
                                                        .padding(top = 11.dp, bottom = 11.dp),
                                                verticalArrangement = Arrangement.spacedBy(8.dp),
                                            ) {
                                                content()
                                                Spacer(modifier = Modifier.height(4.dp))
                                            }
                                        }
                                    }
                                }
                            }
                            if (showBottomBar) {
                                when {
                                    bottomBar != null -> bottomBar()
                                    account.role == UserRole.ADMIN ->
                                        AdminNavigationBar(current, onNavigate)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
internal fun AdminSectionHeading(eyebrow: String, title: String, subtitle: String) {
    val compact = LocalConfiguration.current.screenWidthDp < 360
    Column(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 2.dp, vertical = 4.dp),
        verticalArrangement = Arrangement.spacedBy(7.dp),
    ) {
        Row(
            modifier =
                Modifier.clip(CircleShape)
                    .border(1.dp, AppBorder.copy(alpha = 0.78f), CircleShape)
                    .padding(horizontal = 10.dp, vertical = 5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(7.dp),
        ) {
            Box(
                modifier =
                    Modifier.size(9.dp)
                        .border(1.dp, LoginAccent, CircleShape)
                        .padding(2.dp)
                        .background(LoginAccent, CircleShape),
            )
            Text(
                eyebrow,
                color = LoginAccent,
                fontSize = 10.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 0.6.sp,
            )
        }
        Text(
            title,
            color = LoginInk,
            fontSize = if (compact) 20.sp else 22.sp,
            lineHeight = if (compact) 25.sp else 28.sp,
            fontWeight = FontWeight.Bold,
        )
        Text(
            subtitle,
            color = LoginMuted,
            fontSize = 12.sp,
            lineHeight = 18.sp,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Composable
internal fun AdminLoadingState() {
    Column(
        modifier = Modifier.fillMaxWidth().padding(vertical = 44.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(24.dp),
            color = LoginAccent,
            strokeWidth = 2.dp,
        )
        Text(
            "Memuat ruang kerja…",
            color = AppText,
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
        )
        Text("Mengambil data terbaru dari server", color = AppTextMuted, fontSize = 11.sp)
    }
}

@Composable
internal fun OperationalMetric(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Column(
        modifier =
            modifier
                .clip(RoundedCornerShape(15.dp))
                .background(AppSurface)
                .clickable(onClick = onClick)
                .padding(horizontal = 9.dp, vertical = 11.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp),
    ) {
        Text(
            label,
            color = AppTextMuted,
            fontSize = 10.sp,
            fontWeight = FontWeight.SemiBold,
            maxLines = 1,
        )
        Text(value, color = LoginInk, fontSize = 15.sp, fontWeight = FontWeight.Bold, maxLines = 1)
    }
}

@Composable
internal fun AdminTopBar(
    account: UserAccount,
    onLogout: () -> Unit,
    onOpenProfile: () -> Unit,
    onOpenNotifications: () -> Unit,
    showNotificationsButton: Boolean = true,
    isOnHero: Boolean = false,
) {
    var profileMenuExpanded by remember(account.username) { mutableStateOf(false) }
    val appearance = LocalSmartWasteAppearance.current
    val compact = LocalConfiguration.current.screenWidthDp < 350
    val logoSurface = Color(0xFFF8F8F0)
    val logoBorder = Color(0xFFB7CBB9)
    val roleTextColor = if (isOnHero) Color(0xFFF5F4E8) else AppText
    val modeIconTint by
        animateColorAsState(
            targetValue = if (appearance.darkMode) Color(0xFFB09F79) else Color(0xFF687F83),
            animationSpec = tween(520, easing = FastOutSlowInEasing),
            label = "admin-theme-icon-tint",
        )
    val modeIconSurface by
        animateColorAsState(
            targetValue = if (appearance.darkMode) Color(0xFF29372F) else Color(0xFFE3EBE5),
            animationSpec = tween(520, easing = FastOutSlowInEasing),
            label = "admin-theme-icon-surface",
        )
    val modeIconBorder by
        animateColorAsState(
            targetValue = if (appearance.darkMode) Color(0xFF405247) else Color(0xFFC6D4C9),
            animationSpec = tween(520, easing = FastOutSlowInEasing),
            label = "admin-theme-icon-border",
        )
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Box(
                modifier =
                    Modifier.size(if (compact) 34.dp else 38.dp)
                        .shadow(2.dp, CircleShape)
                        .clip(CircleShape)
                        .background(logoSurface)
                        .border(1.dp, logoBorder, CircleShape),
                contentAlignment = Alignment.Center,
            ) {
                Image(
                    painter = painterResource(R.drawable.launcher_recycling_mark),
                    contentDescription = "Logo Smart Waste",
                    modifier = Modifier.size(if (compact) 22.dp else 25.dp),
                )
            }
            Text(
                text = account.name.displayPersonName().takeIf { it != "—" } ?: "Admin",
                color = roleTextColor,
                fontSize = if (compact) 15.sp else 16.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
        if (showNotificationsButton) {
            IconButton(onClick = onOpenNotifications, modifier = Modifier.size(40.dp)) {
                Icon(
                    imageVector = Icons.Filled.Notifications,
                    contentDescription = "Buka notifikasi",
                    tint = if (isOnHero) Color(0xFFE2E9D9) else LoginAccent,
                    modifier = Modifier.size(21.dp),
                )
            }
        }
        Box {
            IconButton(
                onClick = { profileMenuExpanded = true },
                modifier = Modifier.size(if (compact) 38.dp else 42.dp),
            ) {
                UserAvatar(
                    account.name,
                    modifier = Modifier.size(if (compact) 30.dp else 34.dp),
                    photoUrl = account.profilePhotoUrl,
                )
            }
            DropdownMenu(
                expanded = profileMenuExpanded,
                onDismissRequest = { profileMenuExpanded = false },
                shape = RoundedCornerShape(18.dp),
                containerColor = AppSurface,
                tonalElevation = 0.dp,
                shadowElevation = 8.dp,
            ) {
                Column(
                    modifier = Modifier.width(220.dp).padding(horizontal = 14.dp, vertical = 10.dp),
                    verticalArrangement = Arrangement.spacedBy(3.dp),
                ) {
                    Text("Akun aktif", color = AppTextMuted, fontSize = 11.sp)
                    Text(
                        account.name.displayPersonName(),
                        color = AppText,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                    )
                    Text(
                        "@${account.username} · ${account.role.label}",
                        color = LoginMuted,
                        fontSize = 11.sp,
                    )
                }
                if (account.role == UserRole.ADMIN) {
                    DropdownMenuItem(
                        text = { Text("Pengaturan profil") },
                        leadingIcon = { Icon(Icons.Rounded.Person, contentDescription = null) },
                        onClick = {
                            profileMenuExpanded = false
                            onOpenProfile()
                        },
                    )
                }
                DropdownMenuItem(
                    text = {
                        Text(
                            if (account.role == UserRole.ADMIN) "Pindah ke akun Petugas"
                            else "Pindah akun",
                        )
                    },
                    leadingIcon = { Icon(Icons.Filled.SwitchAccount, contentDescription = null) },
                    onClick = {
                        profileMenuExpanded = false
                        onLogout()
                    },
                )
            }
        }
        Spacer(modifier = Modifier.width(if (compact) 2.dp else 6.dp))
        IconButton(
            onClick = appearance.onToggleTheme,
            modifier = Modifier.size(if (compact) 36.dp else 40.dp),
        ) {
            Box(
                modifier =
                    Modifier.size(if (compact) 30.dp else 34.dp)
                        .clip(CircleShape)
                        .background(modeIconSurface)
                        .border(1.dp, modeIconBorder, CircleShape),
                contentAlignment = Alignment.Center,
            ) {
                Crossfade(
                    targetState = appearance.darkMode,
                    animationSpec = tween(380),
                    label = "admin-theme-icon",
                ) { dark ->
                    Icon(
                        imageVector = if (dark) Icons.Rounded.WbSunny else Icons.Rounded.NightsStay,
                        contentDescription =
                            if (dark) "Aktifkan mode terang" else "Aktifkan mode gelap",
                        tint = modeIconTint,
                        modifier = Modifier.size(if (compact) 16.dp else 18.dp),
                    )
                }
            }
        }
    }
}

@Composable
internal fun AdminNavigationBar(
    current: String,
    onNavigate: (String) -> Unit,
) {
    val destinations =
        listOf(
            RoleNavigationDestination("dashboard", "Beranda", Icons.Filled.Home),
            RoleNavigationDestination("reports", "Laporan", Icons.AutoMirrored.Filled.Assignment),
            RoleNavigationDestination("ops", "Operasional", Icons.Filled.Build),
            RoleNavigationDestination("stats", "Statistik", Icons.Filled.BarChart),
        )
    val darkTheme = IsNightAdmin
    val navigationSurface = if (darkTheme) Color(0xFF1D2620) else Color(0xFF244638)
    val selectedSurface = if (darkTheme) Color(0xFF829D88) else Color(0xFFA5DA73)
    val selectedContent = if (darkTheme) Color(0xFF1D2620) else Color(0xFF244638)
    val inactiveContent = if (darkTheme) Color(0xFF9CA99F) else Color(0xFFD5E0D8)

    RoleNavigationBar(
        selectedRoute = current,
        destinations = destinations,
        containerColor = navigationSurface,
        selectedColor = selectedSurface,
        selectedContentColor = selectedContent,
        inactiveContentColor = inactiveContent,
        onDestinationSelected = onNavigate,
    )
}