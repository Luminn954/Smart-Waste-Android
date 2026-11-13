package com.uoi.pratikumwithbunafla.ui.admin

import android.content.Intent
import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ScrollState
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
import androidx.compose.foundation.selection.selectable
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
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Inbox
import androidx.compose.material.icons.filled.Archive
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.rounded.NightsStay
import androidx.compose.material.icons.rounded.WbSunny
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material.icons.filled.SwitchAccount
import androidx.compose.material.icons.rounded.History
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.TaskAlt
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.semantics.Role
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
import com.uoi.pratikumwithbunafla.ui.theme.LocalSmartWastePalette
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import kotlin.math.PI
import kotlin.math.ceil
import kotlin.math.cos
import kotlin.math.floor
import kotlin.math.log10
import kotlin.math.pow
import kotlin.math.sin
import java.io.File

private val LoginInk: Color
    @Composable get() = MaterialTheme.colorScheme.onBackground
private val LoginMuted: Color
    @Composable get() = MaterialTheme.colorScheme.onSurfaceVariant
private val LoginAccent: Color
    @Composable get() = MaterialTheme.colorScheme.primary
private val LocalIsAdminWorkspace = staticCompositionLocalOf { false }
private val IsNightAdmin: Boolean
    @Composable get() = LocalIsAdminWorkspace.current && LocalSmartWasteAppearance.current.darkMode
private val RoleAccentSurface: Color
    @Composable get() = if (IsNightAdmin) Color(0xFF33443A) else WasteBlue
private val RoleRed: Color
    @Composable get() = if (IsNightAdmin) Color(0xFFD89A93) else WasteRed
private val RoleOrange: Color
    @Composable get() = if (IsNightAdmin) Color(0xFFD1B878) else WasteOrange
private val RolePurple: Color
    @Composable get() = if (IsNightAdmin) Color(0xFFB5A8C2) else WastePurple
private val RoleTeal: Color
    @Composable get() = if (IsNightAdmin) Color(0xFF8EB7A9) else WasteTeal
private val RoleGreen: Color
    @Composable get() = if (IsNightAdmin) Color(0xFF9BC29C) else WasteGreen
private val RoleGreenDark: Color
    @Composable get() = if (IsNightAdmin) Color(0xFF9BC29C) else WasteGreenDark
private val RoleNavy: Color
    @Composable get() = if (IsNightAdmin) Color(0xFFAAB8CC) else WasteNavy

private val AdminNightPalette = DarkSmartWastePalette.copy(
    background = Color(0xFF14241C),
    surface = Color(0xFF1D3025),
    surfaceMuted = Color(0xFF293D31),
    border = Color(0xFF3B5645),
    text = Color(0xFFE7EEE8),
    textMuted = Color(0xFFB3C1B6)
)

private val AdminNightColorScheme = darkColorScheme(
    primary = Color(0xFF9DBDA4),
    onPrimary = Color(0xFF17231A),
    primaryContainer = Color(0xFF2A4032),
    onPrimaryContainer = Color(0xFFD7E6DA),
    secondary = Color(0xFF8FB5A5),
    onSecondary = Color(0xFF17241D),
    secondaryContainer = Color(0xFF293E34),
    onSecondaryContainer = Color(0xFFD5E3DA),
    tertiary = Color(0xFFC2AD79),
    onTertiary = Color(0xFF282114),
    tertiaryContainer = Color(0xFF443921),
    onTertiaryContainer = Color(0xFFEBDDB8),
    background = AdminNightPalette.background,
    onBackground = AdminNightPalette.text,
    surface = AdminNightPalette.surface,
    onSurface = AdminNightPalette.text,
    surfaceVariant = AdminNightPalette.surfaceMuted,
    onSurfaceVariant = AdminNightPalette.textMuted,
    outline = AdminNightPalette.border,
    error = Color(0xFFE0A09A),
    onError = Color(0xFF321B19),
    errorContainer = Color(0xFF4B2A27),
    onErrorContainer = Color(0xFFFFDAD5)
)

private val TaskAmber = Color(0xFFB86B1D)
private val TaskBlue = Color(0xFF456FB5)
private val TaskPurple = Color(0xFF7952A0)
private val TaskTeal = Color(0xFF237E7B)
private val TaskGreen = Color(0xFF43814C)
private val TaskCoral = Color(0xFFC25556)
private val TaskIndigo = Color(0xFF535DAA)
private val TaskRose = Color(0xFFA64F75)

private data class PetugasAssistantChatMessage(
    val fromAssistant: Boolean,
    val text: String,
    val taskLinks: List<PetugasAssistantLink> = emptyList(),
    val nextSteps: List<String> = emptyList()
)

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
        targetValue = if (dark) AdminNightPalette.background else LocalSmartWastePalette.current.background,
        animationSpec = tween(durationMillis = 820, easing = FastOutSlowInEasing),
        label = "admin-page-background"
    )
    Box(Modifier.fillMaxSize().background(background))
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun AdminPage(
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
    showTopBar: Boolean = true,
    content: @Composable ColumnScope.() -> Unit
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val pageScrollState = remember(current, scrollKey) { ScrollState(0) }
    val horizontalPadding = when {
        screenWidth < 350.dp -> 14.dp
        screenWidth >= 600.dp -> 28.dp
        else -> 20.dp
    }
    val isAdmin = account.role == UserRole.ADMIN
    val isNightAdmin = isAdmin && LocalSmartWasteAppearance.current.darkMode
    val workspacePalette = if (isNightAdmin) AdminNightPalette else LocalSmartWastePalette.current
    val workspaceColorScheme = if (isNightAdmin) AdminNightColorScheme else MaterialTheme.colorScheme

    CompositionLocalProvider(
        LocalIsAdminWorkspace provides isAdmin,
        LocalSmartWastePalette provides workspacePalette
    ) {
        MaterialTheme(
            colorScheme = workspaceColorScheme,
            typography = MaterialTheme.typography,
            shapes = MaterialTheme.shapes
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                if (isAdmin) AnimatedAdminPageBackground()
                backgroundContent()
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
                                PullToRefreshBox(
                                    isRefreshing = isRefreshing,
                                    onRefresh = onRefresh,
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    Column(
                                        modifier = Modifier.widthIn(max = 720.dp).fillMaxHeight().fillMaxWidth()
                                            .then(
                                                if (showTopBar) Modifier.windowInsetsPadding(WindowInsets.statusBars)
                                                else Modifier
                                            )
                                            .verticalScroll(pageScrollState)
                                    ) {
                                        if (showTopBar) {
                                            Box(
                                                modifier = Modifier.fillMaxWidth()
                                                    .padding(horizontal = horizontalPadding, vertical = 11.dp)
                                            ) {
                                                AdminTopBar(
                                                    account = account,
                                                    onLogout = onLogout,
                                                    onOpenProfile = { onNavigate("profile") },
                                                    showNotificationsButton = showNotificationsButton,
                                                    onOpenNotifications = {
                                                        onOpenNotifications?.invoke()
                                                            ?: onNavigate("notifications")
                                                    }
                                                )
                                            }
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
                            }
                            if (showBottomBar) {
                                when {
                                    bottomBar != null -> bottomBar()
                                    account.role == UserRole.ADMIN -> AdminNavigationBar(current, onNavigate)
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
        modifier = modifier
            .clip(RoundedCornerShape(15.dp))
            .background(AppSurface)
            .clickable(onClick = onClick)
            .padding(horizontal = 9.dp, vertical = 11.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Text(
            label,
            color = AppTextMuted,
            fontSize = 10.sp,
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
    onOpenNotifications: () -> Unit,
    showNotificationsButton: Boolean = true,
    isOnHero: Boolean = false
) {
    var profileMenuExpanded by remember(account.username) { mutableStateOf(false) }
    val appearance = LocalSmartWasteAppearance.current
    val compact = LocalConfiguration.current.screenWidthDp < 350
    val roleIconTint = when {
        isOnHero -> Color(0xFFE2E9D9)
        appearance.darkMode -> Color(0xFFA9B9AC)
        else -> Color(0xFF5D776C)
    }
    val roleTextColor = if (isOnHero) Color(0xFFF5F4E8) else AppText
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
                color = roleTextColor,
                fontSize = if (compact) 15.sp else 16.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        if (showNotificationsButton) {
            IconButton(onClick = onOpenNotifications, modifier = Modifier.size(40.dp)) {
                Icon(
                    imageVector = Icons.Filled.Notifications,
                    contentDescription = "Buka notifikasi",
                    tint = if (isOnHero) Color(0xFFE2E9D9) else LoginAccent,
                    modifier = Modifier.size(21.dp)
                )
            }
        }
        Box {
            IconButton(onClick = { profileMenuExpanded = true }, modifier = Modifier.size(if (compact) 38.dp else 42.dp)) {
                UserAvatar(
                    account.name,
                    modifier = Modifier.size(if (compact) 30.dp else 34.dp),
                    photoUrl = account.profilePhotoUrl
                )
            }
            DropdownMenu(
                expanded = profileMenuExpanded,
                onDismissRequest = { profileMenuExpanded = false },
                shape = RoundedCornerShape(18.dp),
                containerColor = AppSurface,
                tonalElevation = 0.dp,
                shadowElevation = 8.dp
            ) {
                Column(
                    modifier = Modifier.width(220.dp).padding(horizontal = 14.dp, vertical = 10.dp),
                    verticalArrangement = Arrangement.spacedBy(3.dp)
                ) {
                    Text("Akun aktif", color = AppTextMuted, fontSize = 11.sp)
                    Text(
                        account.name.displayPersonName(),
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
        Triple("ops", "Operasional", Icons.Filled.Build),
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
        targetValue = when {
            IsNightAdmin -> Color(0xFF202923)
            darkMode -> Color(0xFF14231B)
            else -> Color(0xFF244638)
        },
        animationSpec = tween(460, easing = FastOutSlowInEasing),
        label = "role-navbar-surface"
    )
    val activeColor by animateColorAsState(
        targetValue = when {
            IsNightAdmin -> Color(0xFF9DBDA4)
            darkMode -> Color(0xFF89C965)
            else -> Color(0xFFA5DA73)
        },
        animationSpec = tween(460, easing = FastOutSlowInEasing),
        label = "role-navbar-active"
    )
    val inactiveColor = when {
        IsNightAdmin -> Color(0xFFAAB6AC)
        darkMode -> Color(0xFFB4C1B8)
        else -> Color(0xFFD5E0D8)
    }
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
    account: UserAccount,
    onLogout: () -> Unit,
    onOpenProfile: () -> Unit,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    onClick: (String) -> Unit
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
        modifier = Modifier.fillMaxWidth()
            .height(heroHeight)
            .clip(HeroBottomCurveShape)
            .background(if (darkMode) Color(0xFF1C3025) else Color(0xFF286B4D))
            .clickable { onClick(focusRoute) }
    ) {
        val illustrationBitmap = ImageBitmap.imageResource(R.drawable.hero_recycling_crew)
        val illustrationPainter = remember(illustrationBitmap) {
            BitmapPainter(illustrationBitmap, filterQuality = FilterQuality.High)
        }
        Image(
            painter = illustrationPainter,
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.align(Alignment.BottomEnd)
                .height(heroHeight)
                .aspectRatio(260f / 280f)
                .graphicsLayer { compositingStrategy = CompositingStrategy.Offscreen }
                .drawWithContent {
                    drawContent()
                    drawRect(
                        brush = Brush.horizontalGradient(*heroFadeStops(0f, 0.28f)),
                        blendMode = BlendMode.DstIn
                    )
                }
        )

        val textSideShade = if (darkMode) Color(0xFF17271E) else Color(0xFF103F30)
        Box(
            modifier = Modifier.fillMaxSize().background(
                Brush.horizontalGradient(
                    0f to textSideShade.copy(alpha = 0.82f),
                    0.48f to textSideShade.copy(alpha = 0.60f),
                    0.76f to Color.Transparent,
                    1f to Color.Transparent
                )
            )
        )

        Box(
            modifier = Modifier.fillMaxWidth()
                .align(Alignment.TopCenter)
                .windowInsetsPadding(WindowInsets.statusBars)
                .padding(horizontal = 20.dp, vertical = 11.dp)
        ) {
            AdminTopBar(
                account = account,
                onLogout = onLogout,
                onOpenProfile = onOpenProfile,
                onOpenNotifications = { onClick("notifications") },
                isOnHero = true
            )
        }

        Column(
            modifier = Modifier.fillMaxWidth(heroContentWidth)
                .align(Alignment.TopStart)
                .padding(start = 18.dp, top = heroHeaderHeight + 14.dp, bottom = 24.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
                    .clip(RoundedCornerShape(18.dp))
                    .background(heroStatusSurface)
                    .clickable { onClick(focusRoute) }
                    .padding(horizontal = 14.dp, vertical = 13.dp),
                verticalArrangement = Arrangement.spacedBy(9.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(9.dp)
                ) {
                    Box(
                        modifier = Modifier.size(34.dp).clip(CircleShape)
                            .background(focusColor.copy(alpha = 0.18f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(focusIcon, contentDescription = null, tint = focusColor, modifier = Modifier.size(19.dp))
                    }
                    Text(
                        focusHeading,
                        modifier = Modifier.weight(1f),
                        color = bannerText,
                        fontSize = 14.sp,
                        lineHeight = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Text(
                    focusDetail,
                    color = bannerMuted,
                    fontSize = 12.sp,
                    lineHeight = 17.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        focusAction,
                        color = focusColor,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = null,
                        tint = focusColor,
                        modifier = Modifier.size(17.dp)
                    )
                }
            }
        }
    }
}

private val HeroBottomCurveShape = androidx.compose.foundation.shape.GenericShape { size, _ ->
    moveTo(0f, 0f)
    lineTo(size.width, 0f)
    lineTo(size.width, size.height)
    quadraticTo(size.width * 0.5f, size.height * 0.82f, 0f, size.height)
    close()
}

private fun heroFadeStops(start: Float, end: Float): Array<Pair<Float, Color>> =
    (0..32).map { step ->
        val position = step / 32f
        val progress = ((position - start) / (end - start)).coerceIn(0f, 1f)
        val smoothAlpha = progress * progress * (3f - 2f * progress)
        position to Color.White.copy(alpha = smoothAlpha)
    }.toTypedArray()

@Composable
fun AdminProfileScreen(
    account: UserAccount,
    onNavigate: (String) -> Unit,
    onLogout: () -> Unit,
    onUpdateProfile: suspend (String) -> UserAccount,
    onUpdateProfilePhoto: suspend (Uri) -> UserAccount,
    isInitialLoading: Boolean = false,
    isRefreshing: Boolean = false,
    onRefresh: () -> Unit = {}
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
            modifier = Modifier.size(40.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Kembali ke Beranda",
                tint = LoginAccent,
                modifier = Modifier.size(21.dp)
            )
        }
        AccountProfileSettings(account, onLogout, onUpdateProfile, onUpdateProfilePhoto)
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
    pendingVehicleRepairActionCount: Int = 0,
    pendingCapacityClearanceCount: Int = 0,
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
        showTopBar = false,
        fullBleedContent = {
            DashboardHeroBanner(
                summary = summary,
                pendingProofCount = pendingProofCount,
                account = account,
                onLogout = onLogout,
                onOpenProfile = { onNavigate("profile") },
                isRefreshing = isRefreshing,
                onRefresh = onRefresh,
                onClick = onNavigate
            )
        }
    ) {
        if (errorMessage.isNotBlank()) InlineError(errorMessage)
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(3.dp)) {
                Text("Ruang kerja Admin", color = AppText, fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
                Text(
                    "Laporan, petugas, dan pemantauan kinerja",
                    color = if (LocalSmartWasteAppearance.current.darkMode) AppTextMuted else Color(0xFF26382D),
                    fontFamily = InterFont,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Normal
                )
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
                modifier = Modifier.fillMaxWidth().height(78.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
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
                AdminTaskTile("Bukti kerja", Icons.Filled.CheckCircle, TaskTeal, Modifier.weight(1f)) {
                    onNavigate("ops:bukti")
                }
            }
        }
        AppCard(
            modifier = Modifier.fillMaxWidth(),
            containerColor = AppSurfaceMuted
        ) {
            val pendingReportCount = reports.count { it.status == WasteFlow.WAITING }
            val totalPendingActions = pendingReportCount + pendingProofCount +
                pendingVehicleRepairActionCount + pendingCapacityClearanceCount
            Column(
                modifier = Modifier.fillMaxWidth().padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(9.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
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
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Perlu ditangani", color = AppText, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                    Text(
                        "$totalPendingActions tindakan",
                        color = AppTextMuted,
                        fontSize = 11.sp,
                        fontFamily = InterFont
                    )
                }
                if (totalPendingActions == 0) {
                    Text(
                        "Semua antrean sudah ditangani.",
                        modifier = Modifier.padding(horizontal = 3.dp),
                        color = AppTextMuted,
                        fontSize = 12.sp
                    )
                } else {
                    Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
                        if (pendingReportCount > 0) {
                            DashboardPriorityAction(
                                title = "$pendingReportCount laporan menunggu verifikasi",
                                detail = "Periksa laporan warga yang baru masuk",
                                icon = Icons.Filled.Inbox,
                                tint = RoleOrange
                            ) { onNavigate("status:${WasteFlow.WAITING}") }
                        }
                        if (pendingProofCount > 0) {
                            DashboardPriorityAction(
                                title = "$pendingProofCount bukti kerja menunggu",
                                detail = "Tinjau foto penyelesaian petugas",
                                icon = Icons.Filled.CheckCircle,
                                tint = RoleGreenDark
                            ) { onNavigate("ops:bukti") }
                        }
                        if (pendingVehicleRepairActionCount > 0) {
                            DashboardPriorityAction(
                                title = "$pendingVehicleRepairActionCount proses armada menunggu",
                                detail = "Tinjau laporan atau hasil perbaikan",
                                icon = Icons.Filled.Build,
                                tint = RoleOrange
                            ) { onNavigate("ops:armada") }
                        }
                        if (pendingCapacityClearanceCount > 0) {
                            DashboardPriorityAction(
                                title = "$pendingCapacityClearanceCount permintaan kapasitas",
                                detail = "Periksa bukti sebelum menyetujui reset",
                                icon = Icons.Filled.LocalShipping,
                                tint = RoleTeal
                            ) { onNavigate("ops:pengosongan") }
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
                containerColor = AppSurface,
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
            Text(title, color = AppText, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
            Text(detail, color = AppTextMuted, fontSize = 11.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
        }
        Text("Buka", color = LoginAccent, fontSize = 11.sp, fontWeight = FontWeight.Bold)
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
    val nightAdmin = IsNightAdmin
    val iconTint = if (nightAdmin) when (accent) {
        TaskAmber -> Color(0xFFD0B775)
        TaskPurple -> Color(0xFFB3A2C0)
        TaskIndigo -> Color(0xFFA6B2CE)
        TaskTeal -> Color(0xFF89B9AA)
        TaskGreen -> Color(0xFF9ABF96)
        TaskBlue -> Color(0xFF91ADC4)
        TaskCoral -> Color(0xFFD69A91)
        TaskRose -> Color(0xFFC99AAD)
        else -> Color(0xFF9DBDA4)
    } else Color.White
    val iconBackground = if (nightAdmin) iconTint.copy(alpha = 0.14f) else accent

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
                .shadow(if (nightAdmin) 0.dp else 2.dp, CircleShape)
                .clip(CircleShape)
                .background(iconBackground),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = null, tint = iconTint, modifier = Modifier.size(17.dp))
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
fun NotificationCenterScreen(
    account: UserAccount,
    summary: AdminDashboardSummary,
    reports: List<WasteRecord>,
    tasks: List<WasteRecord>,
    incomingReports: List<WasteRecord>,
    completionReviews: List<TaskCompletionReview>,
    vehicleRepairs: List<VehicleRepair>,
    capacityClearanceRequests: List<CapacityClearanceRequest>,
    onNavigate: (String) -> Unit,
    onBack: () -> Unit,
    onLogout: () -> Unit,
    isInitialLoading: Boolean,
    isRefreshing: Boolean,
    onRefresh: () -> Unit
) {
    BackHandler(onBack = onBack)

    val isAdmin = account.role == UserRole.ADMIN
    val notices = buildList {
        if (isAdmin) {
            val waitingReports = reports.filter { it.status == WasteFlow.WAITING }
            val latestWaitingReport = waitingReports.maxByOrNull { it.reportedAt }
            if (waitingReports.isNotEmpty()) add(
                WorkspaceNotice(
                    id = "admin_waiting_reports",
                    revision = "${waitingReports.size}:${latestWaitingReport?.id}:${latestWaitingReport?.reportedAt.orEmpty()}",
                    title = "Laporan menunggu verifikasi",
                    detail = "${waitingReports.size} laporan warga perlu diperiksa.",
                    count = waitingReports.size,
                    icon = Icons.Filled.Inbox,
                    accent = RoleOrange,
                    timestamp = latestWaitingReport?.reportedAt.orEmpty(),
                    destination = "status:${WasteFlow.WAITING}"
                )
            )

            if (completionReviews.isNotEmpty()) add(
                WorkspaceNotice(
                    id = "admin_completion_reviews",
                    revision = "${completionReviews.size}:${completionReviews.maxByOrNull { it.submittedAt }?.let { "${it.id}:${it.submittedAt}" }.orEmpty()}",
                    title = "Bukti kerja menunggu persetujuan",
                    detail = "Periksa foto dan hasil tugas sebelum menyetujui penyelesaian.",
                    count = completionReviews.size,
                    icon = Icons.Filled.CheckCircle,
                    accent = RoleTeal,
                    timestamp = completionReviews.maxByOrNull { it.submittedAt }?.submittedAt.orEmpty(),
                    destination = "ops:bukti"
                )
            )

            val locationEvidenceReports = reports.filter { !it.locationEvidencePhotoUrl.isNullOrBlank() }
            val latestLocationEvidence = locationEvidenceReports.maxByOrNull { it.locationEvidenceSubmittedAt }
            if (locationEvidenceReports.isNotEmpty()) add(
                WorkspaceNotice(
                    id = "admin_location_evidence",
                    revision = "${locationEvidenceReports.size}:${latestLocationEvidence?.id}:${latestLocationEvidence?.locationEvidenceSubmittedAt.orEmpty()}",
                    title = "Foto lokasi dari petugas",
                    detail = "Lihat bukti kondisi sampah di titik laporan.",
                    count = locationEvidenceReports.size,
                    icon = Icons.Filled.PhotoCamera,
                    accent = RoleTeal,
                    timestamp = latestLocationEvidence?.locationEvidenceSubmittedAt.orEmpty(),
                    destination = "ops:bukti"
                )
            )

            val repairsWaiting = vehicleRepairs.filter {
                it.status == "menunggu_persetujuan" || it.status == "menunggu_pemeriksaan"
            }
            val latestRepair = repairsWaiting.maxByOrNull { it.reportedAt }
            if (repairsWaiting.isNotEmpty()) add(
                WorkspaceNotice(
                    id = "admin_vehicle_repairs",
                    revision = "${repairsWaiting.size}:${latestRepair?.id}:${latestRepair?.reportedAt.orEmpty()}",
                    title = "Perbaikan armada perlu ditinjau",
                    detail = "Ada laporan kerusakan atau hasil perbaikan yang menunggu keputusan.",
                    count = repairsWaiting.size,
                    icon = Icons.Filled.Build,
                    accent = RoleOrange,
                    timestamp = latestRepair?.reportedAt.orEmpty(),
                    destination = "ops:armada"
                )
            )

            val clearancesWaiting = capacityClearanceRequests.filter { it.isPending }
            val latestClearance = clearancesWaiting.maxByOrNull { it.createdAt }
            if (clearancesWaiting.isNotEmpty()) add(
                WorkspaceNotice(
                    id = "admin_capacity_clearance",
                    revision = "${clearancesWaiting.size}:${latestClearance?.id}:${latestClearance?.createdAt.orEmpty()}",
                    title = "Permintaan kapasitas menunggu",
                    detail = "Tinjau bukti pengosongan TPA atau armada sebelum menyetujui reset.",
                    count = clearancesWaiting.size,
                    icon = Icons.Filled.LocalShipping,
                    accent = RolePurple,
                    timestamp = latestClearance?.createdAt.orEmpty(),
                    destination = "ops:pengosongan"
                )
            )
        } else {
            if (incomingReports.isNotEmpty()) add(
                WorkspaceNotice(
                    id = "worker_incoming_reports",
                    revision = "${incomingReports.size}:${incomingReports.maxByOrNull { it.reportedAt }?.let { "${it.id}:${it.reportedAt}" }.orEmpty()}",
                    title = "Laporan baru tersedia",
                    detail = "Pilih laporan yang siap ditangani dan cek tujuan pengangkutannya.",
                    count = incomingReports.size,
                    icon = Icons.Filled.Inbox,
                    accent = RoleOrange,
                    timestamp = incomingReports.maxByOrNull { it.reportedAt }?.reportedAt.orEmpty(),
                    destination = "tasks"
                )
            )

            val revisionsNeeded = tasks.filter { it.completionStatus.equals("perlu_perbaikan", ignoreCase = true) }
            val latestRevision = revisionsNeeded.maxByOrNull { it.completionSubmittedAt }
            if (revisionsNeeded.isNotEmpty()) add(
                WorkspaceNotice(
                    id = "worker_proof_revisions",
                    revision = "${revisionsNeeded.size}:${latestRevision?.id}:${latestRevision?.completionSubmittedAt.orEmpty()}",
                    title = "Bukti perlu diperbaiki",
                    detail = "Periksa catatan Admin lalu kirim ulang foto penyelesaian.",
                    count = revisionsNeeded.size,
                    icon = Icons.Filled.CheckCircle,
                    accent = RoleOrange,
                    timestamp = latestRevision?.completionSubmittedAt.orEmpty(),
                    destination = "tasks"
                )
            )

            val reviewsPending = tasks.filter {
                it.status == WasteFlow.TRANSPORTED && it.completionStatus.equals("menunggu", ignoreCase = true)
            }
            val latestPendingReview = reviewsPending.maxByOrNull { it.completionSubmittedAt }
            if (reviewsPending.isNotEmpty()) add(
                WorkspaceNotice(
                    id = "worker_proof_reviews",
                    revision = "${reviewsPending.size}:${latestPendingReview?.id}:${latestPendingReview?.completionSubmittedAt.orEmpty()}",
                    title = "Bukti sedang diperiksa",
                    detail = "Admin sedang meninjau foto dan catatan penyelesaian tugas.",
                    count = reviewsPending.size,
                    icon = Icons.Filled.CheckCircle,
                    accent = RoleTeal,
                    timestamp = latestPendingReview?.completionSubmittedAt.orEmpty(),
                    destination = "tasks"
                )
            )

            val activeTasks = tasks.filter { WasteFlow.isInProgress(it.status) }
            val latestActiveTask = activeTasks.maxByOrNull { it.reportedAt }
            if (activeTasks.isNotEmpty()) add(
                WorkspaceNotice(
                    id = "worker_active_tasks",
                    revision = "${activeTasks.size}:${latestActiveTask?.id}:${latestActiveTask?.reportedAt.orEmpty()}",
                    title = "Tugas masih berjalan",
                    detail = "Lanjutkan pengangkutan dan kirim bukti setelah pekerjaan selesai.",
                    count = activeTasks.size,
                    icon = Icons.Filled.LocalShipping,
                    accent = RolePurple,
                    timestamp = latestActiveTask?.reportedAt.orEmpty(),
                    destination = "tasks"
                )
            )
        }
    }
    val context = LocalContext.current
    val readStateKey = "${account.role.name}:${account.username.lowercase(java.util.Locale.ROOT)}"
    val readPreferences = remember(context) {
        context.getSharedPreferences("smart_waste_notification_reads", Context.MODE_PRIVATE)
    }
    val readRevisions = remember(readStateKey) { mutableStateMapOf<String, String>() }
    LaunchedEffect(readStateKey, notices.map { it.id to it.revision }) {
        notices.forEach { notice ->
            readPreferences.getString("$readStateKey:${notice.id}", null)?.let { savedRevision ->
                readRevisions[notice.id] = savedRevision
            }
        }
    }
    val markNoticeRead: (WorkspaceNotice) -> Unit = { notice ->
        readPreferences.edit()
            .putString("$readStateKey:${notice.id}", notice.revision)
            .apply()
        readRevisions[notice.id] = notice.revision
    }

    AdminPage(
        current = "notifications",
        account = account,
        onNavigate = onNavigate,
        onLogout = onLogout,
        isInitialLoading = isInitialLoading,
        isRefreshing = isRefreshing,
        onRefresh = onRefresh,
        showBottomBar = false,
        showNotificationsButton = false
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            IconButton(onClick = onBack, modifier = Modifier.size(40.dp)) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Kembali",
                    tint = LoginAccent
                )
            }
            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text("Notifikasi", color = AppText, fontSize = 22.sp, fontWeight = FontWeight.SemiBold)
                Text(
                    if (isAdmin) "Pembaruan laporan dan operasional" else "Pembaruan pekerjaan petugas",
                    color = AppTextMuted,
                    fontSize = 13.sp
                )
            }
        }

        if (isAdmin) {
            Row(
                modifier = Modifier.fillMaxWidth()
                    .clip(RoundedCornerShape(18.dp))
                    .background(AppSurface)
                    .padding(horizontal = 16.dp, vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Icon(Icons.Filled.Inbox, contentDescription = null, tint = LoginAccent, modifier = Modifier.size(22.dp))
                Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
                    Text("Ringkasan laporan", color = AppText, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                    Text(
                        "${summary.totalReports} laporan · ${summary.totalWeightKg.pretty()} kg tercatat",
                        color = AppTextMuted,
                        fontSize = 13.sp
                    )
                }
            }
        }

        SectionTitle(
            "Perlu ditangani",
            if (isAdmin) "Pembaruan yang membutuhkan perhatian Admin."
            else "Pembaruan tugas dan bukti pekerjaanmu."
        )

        if (notices.isEmpty()) {
            Row(
                modifier = Modifier.fillMaxWidth()
                    .clip(RoundedCornerShape(18.dp))
                    .background(AppSurface)
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Icon(Icons.Filled.Notifications, contentDescription = null, tint = LoginAccent, modifier = Modifier.size(22.dp))
                Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
                    Text("Belum ada notifikasi", color = AppText, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                    Text(
                        "Pembaruan berikutnya akan muncul di halaman ini.",
                        color = AppTextMuted,
                        fontSize = 13.sp
                    )
                }
            }
        } else {
            Column(
                modifier = Modifier.fillMaxWidth()
                    .clip(RoundedCornerShape(18.dp))
                    .background(AppSurface)
                    .padding(horizontal = 14.dp, vertical = 4.dp),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                notices.forEachIndexed { index, notice ->
                    val isRead = readRevisions[notice.id] == notice.revision
                    Row(
                        modifier = Modifier.fillMaxWidth()
                            .then(
                                if (notice.destination != null) Modifier.clickable {
                                    markNoticeRead(notice)
                                    onNavigate(notice.destination)
                                } else Modifier
                            )
                            .padding(vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Box(
                            modifier = Modifier.size(38.dp)
                                .clip(CircleShape)
                                .background(notice.accent.copy(alpha = 0.14f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(notice.icon, contentDescription = null, tint = notice.accent, modifier = Modifier.size(19.dp))
                        }
                        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(3.dp)) {
                            Text(notice.title, color = AppText, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                            Text(notice.detail, color = AppTextMuted, fontSize = 12.sp, lineHeight = 17.sp)
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    notice.timestamp.takeIf { it.isNotBlank() }?.toDisplayDateTime() ?: "Waktu belum tersedia",
                                    color = AppTextMuted,
                                    fontSize = 11.sp
                                )
                                Text(
                                    if (isRead) "Sudah dilihat" else "Baru",
                                    color = if (isRead) AppTextMuted else notice.accent,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                        Column(horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.spacedBy(3.dp)) {
                            Text(notice.count.toString(), color = notice.accent, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                            if (notice.destination != null) {
                                Text("Buka", color = LoginAccent, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                            }
                        }
                    }
                    if (index < notices.lastIndex) Spacer(Modifier.height(4.dp))
                }
            }
        }
    }
}

private data class WorkspaceNotice(
    val id: String,
    val revision: String,
    val title: String,
    val detail: String,
    val count: Int,
    val icon: ImageVector,
    val accent: Color,
    val timestamp: String = "",
    val destination: String? = null
)

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
    val effectiveSelectedStatus = selectedStatus.takeUnless { it == "bahaya" }
    val activeFilter = if (showingArchive) "arsip" else when {
        !showAllReports || effectiveSelectedStatus == WasteFlow.WAITING -> "baru"
        effectiveSelectedStatus == null -> "semua"
        effectiveSelectedStatus in setOf(WasteFlow.PROCESSING, WasteFlow.ASSIGNED, WasteFlow.TRANSPORTED) -> "proses"
        effectiveSelectedStatus == WasteFlow.DONE -> "selesai"
        else -> "ditolak"
    }
    val visibleReports = if (showingArchive) emptyList() else when {
        !showAllReports -> waitingReports
        effectiveSelectedStatus == null -> reports
        effectiveSelectedStatus in setOf(WasteFlow.PROCESSING, WasteFlow.ASSIGNED, WasteFlow.TRANSPORTED) ->
            reports.filter { WasteFlow.isInProgress(it.status) }
        else -> reports.filter { it.status == effectiveSelectedStatus }
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
    val reportLoadFailed = errorMessage.lineSequence().any { it.startsWith("Laporan:") }
    val archiveLoadFailed = errorMessage.lineSequence().any { it.startsWith("Arsip laporan:") }
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
            if (showingArchive) {
                TextButton(
                    onClick = {
                        archiveActionError = ""
                        onArchiveViewChange(false)
                    },
                    colors = ButtonDefaults.textButtonColors(contentColor = LoginAccent)
                ) {
                    Text("← Kembali ke laporan", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
            } else {
                IconButton(
                    onClick = {
                        archiveActionError = ""
                        onArchiveViewChange(true)
                    }
                ) {
                    Icon(
                        Icons.Filled.Archive,
                        contentDescription = "Buka arsip tersimpan, ${archivedReports.size} laporan",
                        tint = LoginAccent
                    )
                }
            }
        }
        if (!showingArchive) {
            val reportFilterPositions = remember { mutableStateMapOf<String, Pair<Float, Float>>() }
            val selectedFilterBounds = reportFilterPositions[activeFilter]
            val selectedFilterAccent = when (activeFilter) {
                "baru" -> RoleGreen
                "proses" -> RoleTeal
                "selesai" -> RoleGreenDark
                "ditolak" -> RoleRed
                else -> RoleNavy
            }
            val reportIndicatorColor by animateColorAsState(
                targetValue = selectedFilterAccent.copy(alpha = 0.38f),
                animationSpec = tween(360, easing = FastOutSlowInEasing),
                label = "report-filter-indicator-color"
            )
            val reportIndicatorX by animateFloatAsState(
                targetValue = selectedFilterBounds?.first ?: 0f,
                animationSpec = tween(360, easing = FastOutSlowInEasing),
                label = "report-filter-indicator-x"
            )
            val reportIndicatorWidth by animateFloatAsState(
                targetValue = selectedFilterBounds?.second ?: 0f,
                animationSpec = tween(360, easing = FastOutSlowInEasing),
                label = "report-filter-indicator-width"
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .drawBehind {
                        if (reportIndicatorWidth > 0f) {
                            drawRoundRect(
                                color = reportIndicatorColor,
                                topLeft = Offset(reportIndicatorX, 0f),
                                size = Size(reportIndicatorWidth, size.height),
                                cornerRadius = CornerRadius(12.dp.toPx())
                            )
                        }
                    },
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                reportFilters.forEach { (filter, label) ->
                    val active = activeFilter == filter
                    val filterAccent = when (filter) {
                        "baru" -> RoleGreen
                        "proses" -> RoleTeal
                        "selesai" -> RoleGreenDark
                        "ditolak" -> RoleRed
                        else -> RoleNavy
                    }
                    val filterSurface by animateColorAsState(
                        targetValue = filterAccent.copy(alpha = if (active) 0f else 0.08f),
                        animationSpec = tween(240, easing = FastOutSlowInEasing),
                        label = "report-filter-$filter-surface"
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
                        contentPadding = androidx.compose.foundation.layout.PaddingValues(
                            horizontal = 11.dp,
                            vertical = 8.dp
                        ),
                        modifier = Modifier.onGloballyPositioned { coordinates ->
                            val bounds = coordinates.positionInParent().x to coordinates.size.width.toFloat()
                            if (reportFilterPositions[filter] != bounds) {
                                reportFilterPositions[filter] = bounds
                            }
                        },
                        colors = ButtonDefaults.textButtonColors(
                            contentColor = if (active) LoginAccent else AppTextMuted,
                            containerColor = filterSurface
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(label, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                    }
                }
            }
        }

        OutlinedTextField(
            value = reportSearch,
            onValueChange = { reportSearch = it.take(100) },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Cari kode, jenis, lokasi, atau petugas") },
            leadingIcon = {
                Icon(Icons.Filled.Search, contentDescription = null, tint = AppTextMuted)
            },
            singleLine = true,
            shape = RoundedCornerShape(16.dp),
            textStyle = TextStyle(fontFamily = InterFont, fontSize = 12.sp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                focusedContainerColor = AppSurface.copy(alpha = 0.92f),
                unfocusedContainerColor = AppSurface.copy(alpha = 0.82f)
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
            showingArchive && archivedReports.isEmpty() && isInitialLoading -> {
                PetugasLoadingState("Memuat arsip", "Mengambil laporan tersimpan dari server.")
            }
            showingArchive && archivedReports.isEmpty() && archiveLoadFailed -> {
                PetugasLoadFailure("Arsip laporan belum dapat dimuat.", onRefresh)
            }
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
                if (reportLoadFailed) {
                    PetugasLoadFailure("Daftar laporan belum dapat dimuat.", onRefresh)
                } else {
                    EmptyAdminState(
                        when {
                            reportSearch.isNotBlank() -> "Tidak ada laporan yang cocok dengan pencarian."
                            showAllReports -> "Belum ada laporan pada status ini."
                            else -> "Semua laporan baru sudah ditinjau."
                        }
                    )
                }
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
    onRestore: (() -> Unit)? = null,
    petugasStyle: Boolean = false
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
        containerColor = AppSurface,
        accentColor = if (petugasStyle) null else accent,
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
                            "Lihat foto TKP",
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
                            colors = ButtonDefaults.textButtonColors(contentColor = RoleRed)
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
                        color = RoleRed,
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
                    .background(RoleAccentSurface.copy(alpha = 0.56f), RoundedCornerShape(12.dp))
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
                            color = RoleRed,
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
                colors = ButtonDefaults.textButtonColors(contentColor = RoleRed)
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
    val monthlyBars = stats.monthly.take(6).reversed()
    val maxTypeWeight = stats.byType.maxOfOrNull { it.weightKg }?.coerceAtLeast(1.0) ?: 1.0
    val chartColors =
        listOf(LoginAccent, RoleTeal, RolePurple, RoleOrange, RoleGreenDark, RoleNavy)

    AdminPage(
        "stats", account, onNavigate, onLogout,
        isInitialLoading = isInitialLoading,
        isRefreshing = isRefreshing,
        onRefresh = onRefresh
    ) {
        AdminSectionHeading(
            "STATISTIK",
            "Ringkasan laporan",
            "Jumlah, jenis, dan status laporan."
        )
        if (errorMessage.isNotBlank()) InlineError(errorMessage)
        Spacer(modifier = Modifier.height(18.dp))
        SectionTitle("Laporan selesai", "Pilih rentang waktu")
        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            listOf(7, 30, 90).forEach { days ->
                TextButton(
                    onClick = { onDailyRange(days) },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = if (days == dailyRangeDays) LoginAccent else LoginMuted,
                        containerColor = if (days == dailyRangeDays) RoleAccentSurface else AppSurface
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) { Text("$days hari", fontSize = 12.sp, fontWeight = FontWeight.SemiBold) }
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            SummaryCard(
                stats.completedReports.toString(),
                "Selesai total",
                RoleGreenDark,
                Modifier.weight(1f),
                icon = Icons.Filled.CheckCircle,
                iconBackgroundColor = if (IsNightAdmin) Color(0xFF354A3A) else Color(0xFFE5EAD6),
                iconBackgroundEndColor = if (IsNightAdmin) Color(0xFF435642) else Color(0xFFCDDDBB)
            )
            SummaryCard(
                periodCount.toString(),
                "Selesai $dailyRangeDays hari",
                LoginAccent,
                Modifier.weight(1f),
                icon = Icons.AutoMirrored.Filled.Assignment,
                iconBackgroundColor = if (IsNightAdmin) Color(0xFF334843) else Color(0xFFDDEBE4),
                iconBackgroundEndColor = if (IsNightAdmin) Color(0xFF425C55) else Color(0xFFC7DDD3)
            )
        }
        AppCard(
            modifier = Modifier.fillMaxWidth(),
            containerColor = AppSurface,
            borderColor = AppBorder,
            accentColor = RoleTeal
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
            }
        }

        val monthlyRange = monthlyBars.firstOrNull()?.let { first ->
            val latest = monthlyBars.last()
            "Enam bulan penuh · ${monthTitle(first.period)}–${monthTitle(latest.period)}"
        } ?: "Enam bulan penuh terakhir"
        SectionTitle("Laporan per bulan", monthlyRange)
        if (monthlyBars.isEmpty()) EmptyAdminState("Belum ada data bulanan.") else {
            MonthlyReportsChart(monthlyBars)
        }

        SectionTitle("Komposisi jenis sampah", "Perbandingan berdasarkan berat tercatat")
        if (stats.byType.isEmpty()) EmptyAdminState("Belum ada data jenis sampah.") else {
            AppCard(
                modifier = Modifier.fillMaxWidth(),
                containerColor = AppSurface,
                borderColor = AppBorder
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    stats.byType.sortedByDescending { it.weightKg }.take(6).forEachIndexed { index, item ->
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
                containerColor = AppSurface,
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
                                },
                                color = LoginAccent,
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
    onRefresh: () -> Unit = {}
) {
    var tab by rememberSaveable(account.role, initialTab) {
        mutableStateOf(
            when (initialTab) {
                "bukti", "catatan", "catatan-bukti" -> "bukti"
                "armada" -> "armada"
                "pengosongan" -> "pengosongan"
                else -> "petugas"
            }
        )
    }
    var adding by rememberSaveable { mutableStateOf(false) }
    var editingWorker by remember { mutableStateOf<AdminWorker?>(null) }
    var deletingWorker by remember { mutableStateOf<AdminWorker?>(null) }
    var revisionReview by remember { mutableStateOf<TaskCompletionReview?>(null) }
    var vehicleRepairReview by remember { mutableStateOf<Pair<VehicleRepair, String>?>(null) }
    var capacityClearanceReview by remember { mutableStateOf<Pair<CapacityClearanceRequest, String>?>(null) }
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
    val workerPhotoPicker = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
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
        val operationsTabs = listOf(
            "petugas" to "Petugas",
            "armada" to "Perbaikan · ${vehicleRepairs.count { it.status !in setOf("selesai", "ditolak") }}",
            "pengosongan" to "Kapasitas · ${capacityClearanceRequests.count { it.isPending }}",
            "bukti" to "Bukti kerja · ${completionReviews.size + reports.count { !it.locationEvidencePhotoUrl.isNullOrBlank() }}"
        )
        AdminSectionHeading(
            when (tab) {
                "bukti" -> "ADMIN • KINERJA"
                "armada" -> "ADMIN • ARMADA"
                "pengosongan" -> "ADMIN • KAPASITAS"
                else -> "ADMIN • OPERASIONAL"
            },
            when (tab) {
                "bukti" -> "Bukti kerja petugas"
                "armada" -> "Perbaikan armada"
                "pengosongan" -> "Pengosongan aset"
                else -> "Kelola petugas"
            },
            when (tab) {
                "bukti" -> "Tinjau foto hasil kerja, minta perbaikan, atau setujui penyelesaian."
                "armada" -> "Setujui perbaikan dan periksa kendaraan sebelum kembali beroperasi."
                "pengosongan" -> "Tinjau bukti foto sebelum mengatur kapasitas aset kembali kosong."
                else -> "Atur akun petugas dan pantau pembagian tugas."
            }
        )
        if (errorMessage.isNotBlank()) InlineError(errorMessage)
        if (actionError.isNotBlank()) InlineError(actionError)
        val operationsTabPositions = remember { mutableStateMapOf<String, Pair<Float, Float>>() }
        val activeTabBounds = operationsTabPositions[tab]
        val activeTabAccent = when (tab) {
            "petugas" -> RoleGreen
            "armada" -> RoleOrange
            "pengosongan" -> RoleTeal
            else -> RolePurple
        }
        val indicatorColor by animateColorAsState(
            targetValue = activeTabAccent.copy(alpha = 0.38f),
            animationSpec = tween(360, easing = FastOutSlowInEasing),
            label = "operations-tab-indicator-color"
        )
        val indicatorX by animateFloatAsState(
            targetValue = activeTabBounds?.first ?: 0f,
            animationSpec = tween(360, easing = FastOutSlowInEasing),
            label = "operations-tab-indicator-x"
        )
        val indicatorWidth by animateFloatAsState(
            targetValue = activeTabBounds?.second ?: 0f,
            animationSpec = tween(360, easing = FastOutSlowInEasing),
            label = "operations-tab-indicator-width"
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .drawBehind {
                    if (indicatorWidth > 0f) {
                        drawRoundRect(
                            color = indicatorColor,
                            topLeft = Offset(indicatorX, 0f),
                            size = Size(indicatorWidth, size.height),
                            cornerRadius = CornerRadius(13.dp.toPx())
                        )
                    }
                },
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            operationsTabs.forEach { (key, label) ->
                val selected = tab == key
                val tabAccent = when (key) {
                    "petugas" -> RoleGreen
                    "armada" -> RoleOrange
                    "pengosongan" -> RoleTeal
                    else -> RolePurple
                }
                val tabContent by animateColorAsState(
                    targetValue = if (selected) LoginAccent else LoginMuted,
                    animationSpec = tween(220, easing = FastOutSlowInEasing),
                    label = "operations-tab-$key-content"
                )
                val tabSurface by animateColorAsState(
                    targetValue = tabAccent.copy(alpha = if (selected) 0f else 0.08f),
                    animationSpec = tween(240, easing = FastOutSlowInEasing),
                    label = "operations-tab-$key-surface"
                )
                TextButton(
                    onClick = {
                        tab = key
                        onTabSelected(key)
                    },
                    modifier = Modifier.onGloballyPositioned { coordinates ->
                        val bounds = coordinates.positionInParent().x to coordinates.size.width.toFloat()
                        if (operationsTabPositions[key] != bounds) {
                            operationsTabPositions[key] = bounds
                        }
                    },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = tabContent,
                        containerColor = tabSurface
                    ),
                    shape = RoundedCornerShape(13.dp)
                ) {
                    Text(label, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
        when (tab) {
            "pengosongan" -> {
                val visibleRequests = capacityClearanceRequests.filter { it.isPending != showingClearanceHistory }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    listOf(
                        false to "Menunggu · ${capacityClearanceRequests.count { it.isPending }}",
                        true to "Riwayat · ${capacityClearanceRequests.count { !it.isPending }}"
                    ).forEach { (history, label) ->
                        TextButton(
                            onClick = { showingClearanceHistory = history },
                            colors = ButtonDefaults.textButtonColors(
                                contentColor = if (showingClearanceHistory == history) LoginAccent else LoginMuted,
                                containerColor = if (showingClearanceHistory == history) RoleAccentSurface else Color.Transparent
                            ),
                            shape = RoundedCornerShape(13.dp)
                        ) { Text(label, fontSize = 10.sp, fontWeight = FontWeight.SemiBold) }
                    }
                }
                if (visibleRequests.isEmpty()) {
                    EmptyAdminState(
                        if (showingClearanceHistory) "Belum ada keputusan pengosongan."
                        else "Belum ada permintaan kapasitas yang perlu ditinjau."
                    )
                }
                visibleRequests.forEach { request ->
                    AppCard(
                        modifier = Modifier.fillMaxWidth(),
                        containerColor = AppSurface,
                        accentColor = if (request.isPending) RoleOrange else LoginAccent
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth().padding(13.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
                                    Text("${request.targetLabel} · ${request.targetName}", color = AppText, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                                    Text("${request.requesterName} · ${request.createdAt.toDisplayDateTime()}", color = AppTextMuted, fontSize = 10.sp)
                                }
                                StatusPill(
                                    if (request.isPending) "menunggu" else request.status,
                                    if (request.isPending) RoleOrange else LoginAccent
                                )
                            }
                            Text(request.reason, color = AppText, fontSize = 11.sp, lineHeight = 16.sp)
                            TextButton(
                                onClick = { onOpenPhoto(request.evidencePhotoUrl) },
                                contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 0.dp, vertical = 0.dp)
                            ) { Text("Lihat bukti foto", color = LoginAccent, fontSize = 11.sp) }
                            if (request.adminNote.isNotBlank()) {
                                Text("Keputusan Admin: ${request.adminNote}", color = AppTextMuted, fontSize = 10.sp)
                            }
                            if (request.isPending) {
                                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    TextButton(onClick = {
                                        capacityReviewNote = ""
                                        capacityReviewError = ""
                                        capacityClearanceReview = request to "tolak"
                                    }) { Text("Tolak", color = RoleRed, fontWeight = FontWeight.SemiBold) }
                                    TextButton(onClick = {
                                        capacityReviewNote = ""
                                        capacityReviewError = ""
                                        capacityClearanceReview = request to "setujui"
                                    }) { Text("Setujui & kosongkan", color = LoginAccent, fontWeight = FontWeight.SemiBold) }
                                }
                            }
                        }
                    }
                }
            }
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
                    leadingIcon = {
                        Icon(Icons.Filled.Search, contentDescription = null, tint = AppTextMuted)
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(16.dp),
                    textStyle = TextStyle(fontFamily = InterFont, fontSize = 12.sp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                        focusedContainerColor = AppSurface.copy(alpha = 0.92f),
                        unfocusedContainerColor = AppSurface.copy(alpha = 0.82f)
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
                                containerColor = if (selected) RoleAccentSurface else AppSurfaceMuted
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
                    val isDarkMode = LocalSmartWasteAppearance.current.darkMode
                    val workerTextColor = if (isDarkMode) AppText else Color(0xFF202820)
                    val workerDetailColor = AppTextMuted
                    val workerActionColor = if (isDarkMode) LoginAccent else Color(0xFF176B49)
                    val workerWarningColor = if (isDarkMode) RoleRed else Color(0xFF963E45)
                    AppCard(
                        modifier = Modifier.fillMaxWidth(),
                        containerColor = AppSurface,
                        borderColor = AppBorder,
                        accentColor = if (item.active && item.hasAccount) LoginAccent else RoleRed
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 16.dp),
                            verticalArrangement = Arrangement.spacedBy(14.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Box(modifier = Modifier.size(56.dp)) {
                                    UserAvatar(
                                        item.name.displayPersonName(),
                                        modifier = Modifier.fillMaxSize(),
                                        photoUrl = item.profilePhotoUrl
                                    )
                                    if (account.role == UserRole.ADMIN && item.hasAccount) {
                                        IconButton(
                                            onClick = {
                                                photoTarget = item
                                                workerPhotoPicker.launch("image/*")
                                            },
                                            enabled = uploadingPhotoWorkerId == null,
                                            modifier = Modifier
                                                .align(Alignment.BottomEnd)
                                                .size(23.dp)
                                                .clip(CircleShape)
                                                .background(AppSurface)
                                        ) {
                                            if (uploadingPhotoWorkerId == item.id) {
                                                CircularProgressIndicator(
                                                    modifier = Modifier.size(13.dp),
                                                    strokeWidth = 2.dp,
                                                    color = LoginAccent
                                                )
                                            } else {
                                                Icon(
                                                    imageVector = Icons.Filled.PhotoCamera,
                                                    contentDescription = "Tambah atau ganti foto profil ${item.name}",
                                                    tint = LoginAccent,
                                                    modifier = Modifier.size(14.dp)
                                                )
                                            }
                                        }
                                    }
                                }
                                Column(
                                    modifier = Modifier.weight(1f),
                                    verticalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Text(
                                        item.name.displayPersonName(),
                                        color = AppText,
                                        fontSize = 16.sp,
                                        lineHeight = 22.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        if (item.hasAccount) "@${item.username} · ${item.zone.ifBlank { "Tanpa zona" }}"
                                        else "Belum ada akun Android · ${item.zone.ifBlank { "Tanpa zona" }}",
                                        color = if (item.hasAccount) workerDetailColor else workerWarningColor,
                                        fontSize = 14.sp,
                                        lineHeight = 20.sp
                                    )
                                }
                            }

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                Column(
                                    modifier = Modifier.weight(1f),
                                    verticalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Text("Disetujui", color = workerDetailColor, fontSize = 12.sp)
                                    Text(
                                        "${item.completedTasks} tugas · ${item.completedWeightKg.pretty()} kg",
                                        color = workerTextColor,
                                        fontSize = 14.sp,
                                        lineHeight = 19.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        maxLines = 2,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }
                                Column(
                                    modifier = Modifier.weight(1f),
                                    verticalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Text("Aktif · bukti menunggu", color = workerDetailColor, fontSize = 12.sp)
                                    Text(
                                        "${item.activeTasks} tugas · ${item.pendingConfirmations} bukti",
                                        color = workerTextColor,
                                        fontSize = 14.sp,
                                        lineHeight = 19.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        maxLines = 2,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }
                            }

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                TextButton(onClick = { editingWorker = item }) {
                                    Text("Ubah zona", color = workerActionColor, fontSize = 13.sp)
                                }
                                Spacer(modifier = Modifier.weight(1f))
                                if (item.hasAccount) {
                                    TextButton(
                                        onClick = {
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
                                        },
                                        enabled = updatingWorkerId == null
                                    ) {
                                        Text(
                                            when {
                                                updatingWorkerId == item.id -> "Menyimpan…"
                                                item.active -> "Nonaktifkan"
                                                else -> "Aktifkan"
                                            },
                                            color = if (item.active) workerWarningColor else workerActionColor,
                                            fontSize = 13.sp
                                        )
                                    }
                                    IconButton(
                                        onClick = { deletingWorker = item },
                                        modifier = Modifier.size(48.dp)
                                    ) {
                                        Icon(
                                            Icons.Filled.Delete,
                                            contentDescription = "Hapus akun petugas",
                                            tint = RoleRed,
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }
                                } else {
                                    Text("Akun sudah dihapus", color = workerDetailColor, fontSize = 12.sp)
                                }
                            }
                        }
                    }
                }
            }

            "armada" -> {
                val waitingApproval = vehicleRepairs.count { it.status == "menunggu_persetujuan" }
                val waitingInspection = vehicleRepairs.count { it.status == "menunggu_pemeriksaan" }
                val activeRepairs = vehicleRepairs.filter { it.status !in setOf("selesai", "ditolak") }
                val repairHistory = vehicleRepairs.filter { it.status in setOf("selesai", "ditolak") }
                val visibleRepairs = if (showingRepairHistory) repairHistory else activeRepairs
                SectionTitle(
                    "Perlu perhatian",
                    "$waitingApproval menunggu keputusan · $waitingInspection menunggu pemeriksaan hasil"
                )
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    listOf(
                        false to "Berjalan · ${activeRepairs.size}",
                        true to "Riwayat · ${repairHistory.size}"
                    ).forEach { (history, label) ->
                        TextButton(
                            onClick = { showingRepairHistory = history },
                            colors = ButtonDefaults.textButtonColors(
                                contentColor = if (showingRepairHistory == history) LoginAccent else LoginMuted,
                                containerColor = if (showingRepairHistory == history) RoleAccentSurface else AppSurface
                            ),
                            shape = RoundedCornerShape(13.dp)
                        ) { Text(label, fontSize = 10.sp, fontWeight = FontWeight.SemiBold) }
                    }
                }
                if (vehicleRepairs.isEmpty() && isInitialLoading) {
                    PetugasLoadingState("Memuat perbaikan", "Mengambil laporan kerusakan armada.")
                } else if (vehicleRepairs.isEmpty() && errorMessage.contains("Perbaikan armada:")) {
                    PetugasLoadFailure("Data perbaikan belum dapat dimuat.", onRefresh)
                } else if (vehicleRepairs.isEmpty()) {
                    EmptyAdminState("Belum ada laporan kerusakan armada.")
                } else if (visibleRepairs.isEmpty()) {
                    EmptyAdminState(if (showingRepairHistory) "Belum ada riwayat perbaikan." else "Tidak ada proses yang perlu ditinjau.")
                } else {
                    visibleRepairs.forEach { repair ->
                        VehicleRepairCard(repair = repair) {
                            when (repair.status) {
                                "menunggu_persetujuan" -> Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    TextButton(onClick = { vehicleRepairReview = repair to "tolak" }) {
                                        Text("Tolak", color = RoleRed, fontWeight = FontWeight.SemiBold)
                                    }
                                    TextButton(onClick = { vehicleRepairReview = repair to "setujui" }) {
                                        Text("Setujui perbaikan", color = LoginAccent, fontWeight = FontWeight.SemiBold)
                                    }
                                }
                                "menunggu_pemeriksaan" -> Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    TextButton(onClick = { vehicleRepairReview = repair to "kembalikan" }) {
                                        Text("Minta perbaikan lagi", color = RoleOrange, fontWeight = FontWeight.SemiBold)
                                    }
                                    TextButton(onClick = { vehicleRepairReview = repair to "selesai" }) {
                                        Text("Setujui hasil", color = LoginAccent, fontWeight = FontWeight.SemiBold)
                                    }
                                }
                            }
                        }
                    }
                }
            }

            "bukti" -> {
                OutlinedTextField(
                    value = proofSearch,
                    onValueChange = { proofSearch = it.take(120) },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Cari bukti kerja") },
                    placeholder = { Text("Kode laporan, petugas, atau lokasi") },
                    singleLine = true
                )
                val query = proofSearch.trim().lowercase()
                val visibleLocationEvidence = reports
                    .filter { !it.locationEvidencePhotoUrl.isNullOrBlank() }
                    .filter { report ->
                        query.isBlank() || listOf(report.code, report.workerName, report.location)
                            .any { it.lowercase().contains(query) }
                    }
                    .sortedByDescending { it.locationEvidenceSubmittedAt }
                SectionTitle(
                    "Foto di lokasi",
                    "Bukti kondisi sampah yang dikirim Petugas dari titik laporan."
                )
                if (visibleLocationEvidence.isEmpty()) {
                    Text(
                        if (query.isBlank()) "Belum ada foto lokasi dari petugas."
                        else "Foto lokasi yang cocok tidak ditemukan.",
                        color = AppTextMuted,
                        fontSize = 12.sp,
                        fontFamily = InterFont
                    )
                } else {
                    AppCard(
                        modifier = Modifier.fillMaxWidth(),
                        containerColor = AppSurface
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 14.dp, vertical = 5.dp),
                            verticalArrangement = Arrangement.spacedBy(3.dp)
                        ) {
                            visibleLocationEvidence.forEachIndexed { index, report ->
                                Row(
                                    modifier = Modifier.fillMaxWidth().padding(vertical = 9.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    Column(
                                        modifier = Modifier.weight(1f),
                                        verticalArrangement = Arrangement.spacedBy(3.dp)
                                    ) {
                                        Text(
                                            report.code,
                                            color = AppText,
                                            fontSize = 13.sp,
                                            fontWeight = FontWeight.SemiBold,
                                            fontFamily = InterFont
                                        )
                                        Text(
                                            "${report.workerName.ifBlank { "Petugas" }} · ${report.location}",
                                            color = AppTextMuted,
                                            fontSize = 12.sp,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis,
                                            fontFamily = InterFont
                                        )
                                        report.locationEvidenceSubmittedAt.takeIf { it.isNotBlank() }?.let {
                                            Text(
                                                "Dikirim ${it.toDisplayDateTime()}",
                                                color = AppTextMuted,
                                                fontSize = 11.sp,
                                                fontFamily = InterFont
                                            )
                                        }
                                    }
                                    TextButton(
                                        onClick = { report.locationEvidencePhotoUrl?.let(onOpenPhoto) },
                                        enabled = !report.locationEvidencePhotoUrl.isNullOrBlank()
                                    ) {
                                        Text("Lihat foto", color = LoginAccent, fontSize = 12.sp, fontFamily = InterFont)
                                    }
                                }
                                if (index < visibleLocationEvidence.lastIndex) Spacer(Modifier.height(3.dp))
                            }
                        }
                    }
                }

                val visibleReviews = completionReviews.filter { review ->
                    query.isBlank() || listOf(
                        review.report.code,
                        review.report.type,
                        review.report.location,
                        review.report.description,
                        review.workerName,
                        review.workerNote
                    ).any { it.lowercase().contains(query) }
                }
                SectionTitle(
                    "Pemeriksaan hasil kerja",
                    if (visibleReviews.isEmpty()) "Bukti yang menunggu persetujuan Admin akan tampil di sini."
                    else "${visibleReviews.size} bukti menunggu ditinjau."
                )
                if (visibleReviews.isEmpty()) {
                    EmptyAdminState(
                        if (query.isBlank()) "Belum ada bukti penyelesaian yang menunggu."
                        else "Bukti yang cocok tidak ditemukan."
                    )
                }
                visibleReviews.forEach { review ->
                    val report = review.report
                    AppCard(
                        modifier = Modifier.fillMaxWidth(),
                        containerColor = AppSurface,
                        accentColor = RoleOrange
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
                                StatusPill("Menunggu tinjauan", RoleOrange)
                            }
                            Text(
                                "${review.workerName.displayPersonName()} · ${report.location}",
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
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                if (!report.photoUrl.isNullOrBlank()) {
                                    TextButton(
                                        modifier = Modifier.weight(1f),
                                        onClick = { onOpenPhoto(report.photoUrl) }
                                    ) {
                                        Text("Sebelum · warga", color = LoginAccent, fontSize = 10.sp, fontFamily = InterFont)
                                    }
                                } else {
                                    Text(
                                        "Foto awal warga belum tersedia",
                                        modifier = Modifier.weight(1f),
                                        color = AppTextMuted,
                                        fontSize = 10.sp,
                                        fontFamily = InterFont
                                    )
                                }
                                TextButton(
                                    modifier = Modifier.weight(1f),
                                    enabled = review.proofPhotoUrl.isNotBlank(),
                                    onClick = { onOpenPhoto(review.proofPhotoUrl) }
                                ) {
                                    Text("Sesudah · petugas", color = LoginAccent, fontSize = 10.sp, fontFamily = InterFont)
                                }
                            }
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
                                ) { Text("Minta perbaikan", color = RoleOrange, fontSize = 10.sp) }
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
                                        color = RoleGreen,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold
                                    )
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
    vehicleRepairReview?.let { (repair, action) ->
        VehicleRepairReviewDialog(
            repair = repair,
            action = action,
            onDismiss = { vehicleRepairReview = null },
            onSubmit = { note ->
                onReviewVehicleRepair(repair, action, note)
                vehicleRepairReview = null
            }
        )
    }
    capacityClearanceReview?.let { (request, action) ->
        AlertDialog(
            onDismissRequest = { if (!capacityReviewSending) capacityClearanceReview = null },
            title = {
                Text(
                    if (action == "setujui") "Setujui pengosongan?" else "Tolak permintaan?",
                    color = LoginInk,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("${request.targetLabel} · ${request.targetName}", color = AppText, fontWeight = FontWeight.SemiBold)
                    Text(
                        if (action == "setujui") {
                            "Setelah disetujui, kapasitas aset direset ke kosong. Server akan menolak persetujuan jika masih ada tugas pengiriman aktif."
                        } else {
                            "Permintaan ditolak dan angka kapasitas tetap seperti semula."
                        },
                        color = AppTextMuted,
                        fontSize = 12.sp,
                        lineHeight = 17.sp
                    )
                    if (action == "tolak") {
                        OutlinedTextField(
                            value = capacityReviewNote,
                            onValueChange = { capacityReviewNote = it.take(500) },
                            modifier = Modifier.fillMaxWidth(),
                            label = { Text("Alasan penolakan") },
                            minLines = 2,
                            maxLines = 4,
                            enabled = !capacityReviewSending
                        )
                    }
                    if (capacityReviewError.isNotBlank()) InlineError(capacityReviewError)
                }
            },
            confirmButton = {
                TextButton(
                    enabled = !capacityReviewSending && (action != "tolak" || capacityReviewNote.trim().length >= 5),
                    onClick = {
                        scope.launch {
                            capacityReviewSending = true
                            capacityReviewError = ""
                            actionError = ""
                            try {
                                onReviewCapacityClearance(request, action, capacityReviewNote.trim())
                                capacityClearanceReview = null
                                capacityReviewNote = ""
                            } catch (cancelled: CancellationException) {
                                throw cancelled
                            } catch (error: Exception) {
                                capacityReviewError = error.message ?: "Keputusan pengosongan gagal disimpan."
                            } finally {
                                capacityReviewSending = false
                            }
                        }
                    }
                ) {
                    Text(
                        if (capacityReviewSending) "Menyimpan…" else if (action == "setujui") "Setujui & kosongkan" else "Tolak",
                        color = if (action == "setujui") LoginAccent else RoleRed
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = { capacityClearanceReview = null }, enabled = !capacityReviewSending) {
                    Text("Kembali", color = LoginMuted)
                }
            },
            containerColor = AppSurface
        )
    }

}

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
    onAskAssistant: suspend (String, List<PetugasAssistantTurn>) -> PetugasAssistantReply,
    onOpenPhoto: (String) -> Unit,
    onArchiveTask: suspend (WasteRecord) -> Unit,
    onRestoreArchivedTask: suspend (WasteRecord) -> Unit,
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
    onLogout: () -> Unit
) {
    var selectedSection by rememberSaveable(account.username) { mutableStateOf("ringkasan") }
    var selectedWorkArea by rememberSaveable(account.username) { mutableStateOf("laporan") }
    var incomingSearch by rememberSaveable(account.username) { mutableStateOf("") }
    var activeTaskSearch by rememberSaveable(account.username) { mutableStateOf("") }
    var historySearch by rememberSaveable(account.username) { mutableStateOf("") }
    var activeTaskFilter by rememberSaveable(account.username) { mutableStateOf("semua") }
    var historyFilter by rememberSaveable(account.username) { mutableStateOf("semua") }
    var showingTaskArchive by rememberSaveable(account.username) { mutableStateOf(false) }
    var showingRepairHistory by rememberSaveable(account.username) { mutableStateOf(false) }
    var archivingHistoryTask by remember { mutableStateOf<WasteRecord?>(null) }
    var restoringHistoryTask by remember { mutableStateOf<WasteRecord?>(null) }
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
    var selectedCompletionTaskId by rememberSaveable(account.username) { mutableStateOf<Int?>(null) }
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
    var selectedLocationPhotoUri by rememberSaveable(account.username) { mutableStateOf<Uri?>(null) }
    var selectedLocationCameraUri by rememberSaveable(account.username) { mutableStateOf<Uri?>(null) }
    var locationGalleryUri by rememberSaveable(account.username) { mutableStateOf<Uri?>(null) }
    var locationCameraPath by rememberSaveable(account.username) { mutableStateOf<String?>(null) }
    val locationCameraFile = locationCameraPath?.let(::File)
    var showLocationPhotoSourceDialog by remember { mutableStateOf(false) }
    var locationPhotoError by remember { mutableStateOf("") }
    var sendingLocationTaskId by remember { mutableStateOf<Int?>(null) }
    var confirmingTransportTask by remember { mutableStateOf<WasteRecord?>(null) }
    var showAssistantDialog by remember { mutableStateOf(false) }
    var assistantDraft by rememberSaveable(account.username) { mutableStateOf("") }
    var assistantSending by remember { mutableStateOf(false) }
    var assistantError by remember { mutableStateOf("") }
    var assistantMessages by remember(account.username) {
        mutableStateOf(emptyList<PetugasAssistantChatMessage>())
    }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val proofPicker = rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) { uri ->
        if (uri != null) {
            runCatching {
                context.contentResolver.takePersistableUriPermission(
                    uri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
            }
        }
        galleryEvidenceUri = uri
        selectedProofUri = uri
        if (uri == null) selectedCompletionTaskId = null
        showProofSourceDialog = false
    }
    val locationPhotoPicker = rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) { uri ->
        if (uri != null) {
            runCatching {
                context.contentResolver.takePersistableUriPermission(
                    uri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
            }
            locationGalleryUri = uri
            selectedLocationPhotoUri = uri
            showLocationPhotoSourceDialog = false
        } else {
            showLocationPhotoSourceDialog = true
        }
    }
    val capacityClearancePicker = rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) { uri ->
        if (uri != null) {
            runCatching {
                context.contentResolver.takePersistableUriPermission(
                    uri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
            }
        }
        capacityClearancePhoto = uri
    }
    val cameraPicker = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { captured ->
        selectedProofUri = if (captured) selectedCameraUri else null
        if (!captured) {
            cameraEvidenceFile?.delete()
            cameraEvidencePath = null
            selectedCameraUri = null
            proofError = "Foto belum diambil. Kamu bisa mencoba lagi atau memilih dari galeri."
            showProofSourceDialog = true
        }
    }
    val locationCameraPicker = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { captured ->
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
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
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
        val file = File(File(context.cacheDir, "evidence").apply { mkdirs() }, "bukti-${task.id}-${System.currentTimeMillis()}.jpg")
        runCatching {
            check(file.createNewFile()) { "File foto tidak dapat dibuat." }
            val uri = FileProvider.getUriForFile(
                context,
                "${context.packageName}.fileprovider",
                file
            )
            cameraEvidencePath = file.absolutePath
            selectedCameraUri = uri
            showProofSourceDialog = false
            cameraPicker.launch(uri)
        }.onFailure { error ->
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
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
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
        val file = File(
            File(context.cacheDir, "evidence").apply { mkdirs() },
            "lokasi-${task.id}-${System.currentTimeMillis()}.jpg"
        )
        runCatching {
            check(file.createNewFile()) { "File foto tidak dapat dibuat." }
            val uri = FileProvider.getUriForFile(
                context,
                "${context.packageName}.fileprovider",
                file
            )
            locationCameraPath = file.absolutePath
            selectedLocationCameraUri = uri
            showLocationPhotoSourceDialog = false
            locationCameraPicker.launch(uri)
        }.onFailure { error ->
            file.delete()
            locationCameraPath = null
            selectedLocationCameraUri = null
            locationPhotoError = error.message ?: "Kamera tidak dapat dibuka. Coba pilih foto dari galeri."
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
    fun sendAssistantQuestion(rawQuestion: String) {
        val question = rawQuestion.trim()
        if (question.isEmpty() || assistantSending) return
        val conversation = assistantMessages.takeLast(8).map { message ->
            val content = if (message.fromAssistant) buildString {
                append(message.text)
                message.taskLinks.takeIf { it.isNotEmpty() }?.let { links ->
                    append("\nTugas: ")
                    append(links.joinToString { "${it.code} (${it.statusLabel})" })
                }
                message.nextSteps.takeIf { it.isNotEmpty() }?.let { steps ->
                    append("\nSaran: ")
                    append(steps.joinToString("; "))
                }
            } else message.text
            PetugasAssistantTurn(if (message.fromAssistant) "assistant" else "user", content)
        }
        assistantDraft = ""
        assistantError = ""
        assistantMessages = (assistantMessages + PetugasAssistantChatMessage(
            fromAssistant = false,
            text = question
        )).takeLast(18)
        scope.launch {
            assistantSending = true
            try {
                val reply = onAskAssistant(question, conversation)
                assistantMessages = (assistantMessages + PetugasAssistantChatMessage(
                    fromAssistant = true,
                    text = reply.message,
                    taskLinks = reply.taskLinks,
                    nextSteps = reply.nextSteps
                )).takeLast(18)
            } catch (cancelled: CancellationException) {
                throw cancelled
            } catch (error: Exception) {
                assistantError = error.message ?: "Asisten belum bisa menjawab. Coba lagi sebentar."
            } finally {
                assistantSending = false
            }
        }
    }
    val currentSection = selectedSection.takeIf {
        it in setOf("ringkasan", "tugas", "riwayat", "profil")
    } ?: "ringkasan"
    BackHandler(enabled = currentSection == "profil") {
        selectedSection = "ringkasan"
    }
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
            }.thenBy { it.reportedAt }
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
    val historyFilterRows = if (showingTaskArchive) archivedTasks.map { it.report } else historyTasks
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
    val filteredArchivedHistory = archivedTasks.filter { archived ->
        matchesTaskSearch(archived.report, historySearch) && when (historyFilter) {
            "selesai" -> archived.report.status == WasteFlow.DONE
            "ditolak" -> archived.report.status == WasteFlow.REJECTED
            else -> true
        }
    }

    fun hasLoadError(label: String): Boolean =
        errorMessage.lineSequence().any { it.startsWith("$label:") }

    val sectionDataHasLoadError = when (currentSection) {
        "ringkasan" -> hasLoadError("Penugasan") || hasLoadError("Laporan masuk")
        "tugas" -> when (selectedWorkArea) {
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
    val sectionDataIsEmpty = when (currentSection) {
        "ringkasan" -> activeTasks.isEmpty() && newReports.isEmpty()
        "tugas" -> when (selectedWorkArea) {
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
        scrollKey = "$currentSection:$selectedWorkArea:$showingTaskArchive",
        account = account,
        onNavigate = {},
        onLogout = onLogout,
        onOpenNotifications = onOpenNotifications,
        bottomBar = { PetugasNavigationBar(currentSection) { selectedSection = it } },
        backgroundContent = { PetugasThemeBackdrop(Modifier.fillMaxSize()) }
    ) {
        if (currentSection == "ringkasan") {
            PetugasJourneyHero(
                name = account.name,
                tasks = tasks,
                incomingReportCount = newReports.size,
                isInitialLoading = isInitialLoading,
                onOpenTasks = {
                    selectedSection = "tugas"
                    selectedWorkArea = if (activeTasks.isNotEmpty()) "aktif" else "laporan"
                    activeTaskFilter = "semua"
                    activeTaskSearch = ""
                    incomingSearch = ""
                }
            )
        }
        if (currentSection == "ringkasan" || currentSection == "tugas") {
            PetugasAssistantEntry(onClick = { showAssistantDialog = true })
        }
        if (currentSection != "profil") {
            if (sectionDataHasLoadError && !sectionDataIsEmpty) {
                PetugasLoadFailure(
                    "Sebagian data belum diperbarui. Data tersimpan tetap ditampilkan.",
                    onRefresh
                )
            }
        }
        if (currentSection != "profil") {
            if (reportActionError.isNotBlank()) InlineError(reportActionError)
        }
        AnimatedContent(
            targetState = currentSection,
            modifier = Modifier.fillMaxWidth(),
            transitionSpec = {
                val sectionOrder = listOf("ringkasan", "tugas", "riwayat", "profil")
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
                    val summaryUnavailable = activeTasks.isEmpty() && newReports.isEmpty() &&
                            (hasLoadError("Penugasan") || hasLoadError("Laporan masuk"))
                    if (summaryUnavailable) {
                        PetugasLoadFailure("Ringkasan belum dapat dimuat.", onRefresh)
                    } else {
                        if (activeTasks.isNotEmpty()) PetugasOperationalOverview(tasks)
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
                                    onComplete = ::requestCompletionProof,
                                    onSendLocationEvidence = ::requestLocationPhoto,
                                    isSendingLocationEvidence = sendingLocationTaskId == nextTask.id
                                )
                                TextButton(
                                    onClick = {
                                        selectedSection = "tugas"
                                        selectedWorkArea = "aktif"
                                        activeTaskFilter = "semua"
                                        activeTaskSearch = ""
                                    },
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
                                    assignmentLabel = "Terima & tugaskan",
                                    petugasStyle = true
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
                                "Belum ada tugas aktif atau laporan baru. Tarik layar ke bawah untuk memeriksa antrean terbaru."
                            )
                        }
                    }
                }
            }

            "tugas" -> {
                PetugasFilterGrid(
                    choices = listOf(
                        PetugasFilterOption("laporan", "Masuk", newReports.size),
                        PetugasFilterOption("aktif", "Aktif", activeTasks.size),
                        PetugasFilterOption("armada", "Armada", vehicles.size),
                        PetugasFilterOption(
                            "perbaikan",
                            "Perbaikan",
                            vehicleRepairs.count { it.status !in setOf("selesai", "ditolak") }
                        ),
                        PetugasFilterOption("tpa", "TPA", facilities.size)
                    ),
                    selectedKey = selectedWorkArea,
                    onSelect = { selectedWorkArea = it }
                )
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
                                    assignmentLabel = "Terima & tugaskan",
                                    petugasStyle = true
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
                        PetugasFilterGrid(
                            choices = listOf(
                                PetugasFilterOption("semua", "Semua", activeTasks.size),
                                PetugasFilterOption("perbaikan", "Perbaikan", tasksNeedingRevision.size),
                                PetugasFilterOption("siap", "Siap angkut", tasksReadyForTransport.size),
                                PetugasFilterOption("rute", "Dalam rute", tasksInRoute.size),
                                PetugasFilterOption("admin", "Menunggu Admin", tasksWaitingForAdmin.size)
                            ),
                            selectedKey = activeTaskFilter,
                            onSelect = { activeTaskFilter = it }
                        )
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
                                    onComplete = ::requestCompletionProof,
                                    onSendLocationEvidence = ::requestLocationPhoto,
                                    isSendingLocationEvidence = sendingLocationTaskId == task.id
                                )
                            }
                        }
                    }

                    "perbaikan" -> {
                        val activeRepairs = vehicleRepairs.filter { it.status !in setOf("selesai", "ditolak") }
                        val repairHistory = vehicleRepairs.filter { it.status in setOf("selesai", "ditolak") }
                        val visibleRepairs = if (showingRepairHistory) repairHistory else activeRepairs
                        SectionTitle(
                            "Proses perbaikan",
                            "Catat penyebab, tulis progres, lalu kirim hasil untuk diperiksa Admin."
                        )
                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            listOf(
                                false to "Berjalan · ${activeRepairs.size}",
                                true to "Riwayat · ${repairHistory.size}"
                            ).forEach { (history, label) ->
                                TextButton(
                                    onClick = { showingRepairHistory = history },
                                    colors = ButtonDefaults.textButtonColors(
                                        contentColor = if (showingRepairHistory == history) LoginAccent else LoginMuted,
                                        containerColor = if (showingRepairHistory == history) RoleAccentSurface else AppSurface
                                    ),
                                    shape = RoundedCornerShape(13.dp)
                                ) { Text(label, fontSize = 10.sp, fontWeight = FontWeight.SemiBold) }
                            }
                        }
                        if (isInitialLoading && vehicleRepairs.isEmpty()) {
                            PetugasLoadingState("Memuat perbaikan", "Mengambil status laporan kerusakan.")
                        } else if (vehicleRepairs.isEmpty() && hasLoadError("Perbaikan armada")) {
                            PetugasLoadFailure("Data perbaikan belum dapat dimuat.", onRefresh)
                        } else if (vehicleRepairs.isEmpty()) {
                            PetugasEmptyState("Belum ada proses perbaikan", "Laporkan kerusakan dari tab Armada untuk memulai alur persetujuan.")
                        } else if (visibleRepairs.isEmpty()) {
                            PetugasEmptyState(if (showingRepairHistory) "Belum ada riwayat perbaikan" else "Semua proses perbaikan sudah selesai.", "Pilih Riwayat untuk melihat pekerjaan yang sudah ditutup.")
                        } else {
                            visibleRepairs.forEach { repair ->
                                VehicleRepairCard(repair = repair) {
                                    when (repair.status) {
                                        "disetujui", "perlu_perbaikan" -> TextButton(
                                            onClick = { vehicleRepairAction = repair to "mulai" }
                                        ) { Text("Mulai / lanjutkan perbaikan", color = LoginAccent, fontWeight = FontWeight.SemiBold) }
                                        "dalam_perbaikan" -> Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                            TextButton(onClick = { vehicleRepairAction = repair to "progres" }) {
                                                Text("Tambah progres", color = LoginAccent, fontWeight = FontWeight.SemiBold)
                                            }
                                            TextButton(onClick = { vehicleRepairAction = repair to "pemeriksaan" }) {
                                                Text("Minta pemeriksaan", color = LoginAccent, fontWeight = FontWeight.SemiBold)
                                            }
                                        }
                                        "menunggu_persetujuan" -> Text("Menunggu persetujuan Admin", color = AppTextMuted, fontSize = 11.sp)
                                        "menunggu_pemeriksaan" -> Text("Hasil dikirim · menunggu pemeriksaan Admin", color = AppTextMuted, fontSize = 11.sp)
                                    }
                                }
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
                            val openRepair = vehicleRepairs.firstOrNull {
                                it.vehicleId == vehicle.id && it.status !in setOf("selesai", "ditolak")
                            }
                            val isFull = vehicle.capacityKg > 0 && vehicle.availableKg <= 0.01
                            val emptyingRequest = capacityClearanceRequests.firstOrNull {
                                it.targetType == "kendaraan" && it.targetId == vehicle.id
                            }
                            AppCard(modifier = Modifier.fillMaxWidth(), containerColor = AppSurface) {
                                Column(
                                    modifier = Modifier.fillMaxWidth().padding(horizontal = 14.dp, vertical = 12.dp),
                                    verticalArrangement = Arrangement.spacedBy(5.dp)
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(3.dp)) {
                                            Text(vehicle.plate, color = AppText, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                                            Text(
                                                "${vehicle.loadKg.pretty()} kg terisi · ${vehicle.reservedKg.pretty()} kg dipesan dari ${vehicle.capacityKg.pretty()} kg · ${vehicle.condition}",
                                                color = AppTextMuted,
                                                fontSize = 10.sp
                                            )
                                            Text("Rute: ${vehicle.route.ifBlank { "Belum ditentukan" }}", color = AppTextMuted, fontSize = 11.sp)
                                        }
                                        TextButton(onClick = { editingVehicle = vehicle; showVehicleForm = true }) {
                                            Text("Ubah", color = LoginAccent, fontSize = 11.sp)
                                        }
                                    }
                                    if (isFull) {
                                        CapacityLimitNotice(
                                            title = "Kapasitas penuh · penugasan baru diblokir",
                                            detail = "Kosongkan muatan di TPA, lampirkan foto, lalu ajukan persetujuan Admin.",
                                            status = emptyingRequest?.let { clearanceStatusText(it) },
                                            actionLabel = if (emptyingRequest?.isPending == true) null else "Ajukan pengosongan",
                                            onAction = {
                                                capacityClearanceError = ""
                                                capacityClearancePhoto = null
                                                capacityClearanceReason = ""
                                                capacityClearanceTarget = Triple("kendaraan", vehicle.id, vehicle.plate)
                                            }
                                        )
                                    }
                                    if (openRepair != null) {
                                        TextButton(onClick = { selectedWorkArea = "perbaikan" }) {
                                            Text("Lihat proses · ${openRepair.statusLabel}  ›", color = RoleOrange, fontSize = 11.sp)
                                        }
                                    } else if (vehicle.condition.equals("baik", true) || vehicle.condition.equals("rusak", true)) {
                                        TextButton(onClick = { reportingDamageVehicle = vehicle }) {
                                            Text("Laporkan kerusakan", color = RoleRed, fontSize = 11.sp)
                                        }
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
                            val isFull = facility.capacityTon > 0 && facility.remainingTon <= 0.001
                            val emptyingRequest = capacityClearanceRequests.firstOrNull {
                                it.targetType == "tpa" && it.targetId == facility.id
                            }
                            AppCard(modifier = Modifier.fillMaxWidth(), containerColor = AppSurface) {
                                Column(
                                    modifier = Modifier.fillMaxWidth().padding(14.dp),
                                    verticalArrangement = Arrangement.spacedBy(5.dp)
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Column(Modifier.weight(1f)) {
                                            Text(facility.name, color = AppText, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                                            Text(facility.address, color = AppTextMuted, fontSize = 11.sp, maxLines = 2, overflow = TextOverflow.Ellipsis)
                                        }
                                        TextButton(onClick = { editingFacility = facility; showFacilityForm = true }) {
                                            Text("Ubah", color = LoginAccent, fontSize = 11.sp)
                                        }
                                    }
                                    Text(
                                        "${facility.filledTon.pretty()} ton terisi · ${facility.reservedTon.pretty()} ton dicadangkan dari ${facility.capacityTon.pretty()} ton",
                                        color = AppTextMuted,
                                        fontSize = 11.sp
                                    )
                                    SoftProgress(
                                        if (facility.capacityTon > 0) {
                                            ((facility.filledTon + facility.reservedTon) / facility.capacityTon).toFloat()
                                        } else 0f
                                    )
                                    if (isFull) {
                                        CapacityLimitNotice(
                                            title = "TPA penuh · tidak menerima sampah baru",
                                            detail = "Setelah area dikosongkan, kirim foto dan alasan. Admin akan memeriksa sebelum kapasitas direset.",
                                            status = emptyingRequest?.let { clearanceStatusText(it) },
                                            actionLabel = if (emptyingRequest?.isPending == true) null else "Ajukan pengosongan",
                                            onAction = {
                                                capacityClearanceError = ""
                                                capacityClearancePhoto = null
                                                capacityClearanceReason = ""
                                                capacityClearanceTarget = Triple("tpa", facility.id, facility.name)
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            "riwayat" -> {
                Column {
                    Text(
                        if (showingTaskArchive) "Arsip pekerjaan" else "Riwayat pekerjaan",
                        color = AppText,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        if (showingTaskArchive) "Catatan tersimpan dan dapat dipulihkan" else "${completedTasks.size} selesai · ${rejectedTasks.size} ditolak",
                        color = AppTextMuted,
                        fontSize = 12.sp
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    listOf(
                        false to "Riwayat · ${historyTasks.size}",
                        true to "Arsip · ${archivedTasks.size}"
                    ).forEach { (archive, label) ->
                        TextButton(
                            onClick = {
                                showingTaskArchive = archive
                                historyFilter = "semua"
                                archiveActionError = ""
                            },
                            contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                            colors = ButtonDefaults.textButtonColors(
                                contentColor = if (showingTaskArchive == archive) LoginAccent else LoginMuted,
                                containerColor = if (showingTaskArchive == archive) LoginAccent.copy(alpha = 0.12f) else Color.Transparent
                            ),
                            shape = RoundedCornerShape(14.dp)
                        ) {
                            Text(label, fontSize = 12.sp, fontWeight = if (showingTaskArchive == archive) FontWeight.SemiBold else FontWeight.Medium)
                        }
                    }
                }
                PetugasFilterGrid(
                    choices = listOf(
                        PetugasFilterOption("semua", "Semua", historyFilterRows.size),
                        PetugasFilterOption("selesai", "Selesai", historyFilterRows.count { it.status == WasteFlow.DONE }),
                        PetugasFilterOption("ditolak", "Ditolak", historyFilterRows.count { it.status == WasteFlow.REJECTED })
                    ),
                    selectedKey = historyFilter,
                    onSelect = { historyFilter = it }
                )
                PetugasQueueSearch(
                    query = historySearch,
                    onQueryChange = { historySearch = it.take(100) },
                    placeholder = if (showingTaskArchive) "Cari arsip kode, lokasi, atau TPA" else "Cari kode, lokasi, atau TPA"
                )
                if (archiveActionError.isNotBlank()) InlineError(archiveActionError)
                if (showingTaskArchive) {
                    when {
                        isInitialLoading && archivedTasks.isEmpty() ->
                            PetugasLoadingState("Memuat arsip", "Mengambil catatan yang tersimpan.")
                        archivedTasks.isEmpty() && hasLoadError("Arsip Petugas") ->
                            PetugasLoadFailure("Arsip belum dapat dimuat.", onRefresh)
                        filteredArchivedHistory.isEmpty() && archivedTasks.isEmpty() ->
                            PetugasEmptyState("Arsip masih kosong", "Arsipkan tugas selesai atau laporan ditolak dari riwayat.")
                        filteredArchivedHistory.isEmpty() ->
                            PetugasEmptyState("Arsip tidak ditemukan", "Coba kata kunci atau filter status yang lain.")
                    }
                    filteredArchivedHistory.forEach { archived ->
                        PetugasTaskCard(archived.report, onOpenPhoto, compactHistory = true)
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                "Disimpan ${archived.archivedAt.toDisplayDateTime()} · ${archived.archivedBy.displayPersonName()}",
                                modifier = Modifier.weight(1f),
                                color = AppTextMuted,
                                fontSize = 10.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            TextButton(
                                onClick = { restoringHistoryTask = archived.report },
                                enabled = !archiveActionRunning,
                                contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 8.dp, vertical = 2.dp)
                            ) { Text("Pulihkan", color = LoginAccent, fontSize = 11.sp) }
                        }
                    }
                } else {
                    when {
                        isInitialLoading && historyTasks.isEmpty() ->
                            PetugasLoadingState("Memuat riwayat", "Mengambil pekerjaan yang sudah selesai.")
                        historyTasks.isEmpty() && hasLoadError("Penugasan") ->
                            PetugasLoadFailure("Riwayat belum dapat dimuat.", onRefresh)
                        historyTasks.isEmpty() ->
                            PetugasEmptyState("Belum ada riwayat", "Tugas selesai dan laporan yang ditolak akan tersimpan di sini.")
                        filteredHistoryTasks.isEmpty() ->
                            PetugasEmptyState(
                                if (historySearch.isNotBlank()) "Riwayat tidak ditemukan" else "Belum ada hasil pada filter ini",
                                "Ubah filter atau kata kunci untuk melihat catatan lain."
                            )
                    }
                    filteredHistoryTasks.forEach { task ->
                        PetugasTaskCard(
                            task,
                            onOpenPhoto,
                            compactHistory = true,
                            onArchive = { archivingHistoryTask = task }
                        )
                    }
                }
            }

            "profil" -> AccountProfileSettings(account, onLogout, onUpdateProfile)
                }
            }
        }
    }
    capacityClearanceTarget?.let { target ->
        val targetLabel = if (target.first == "tpa") "TPA" else "armada"
        AlertDialog(
            onDismissRequest = { if (!capacityClearanceSending) capacityClearanceTarget = null },
            title = { Text("Ajukan pengosongan $targetLabel", color = LoginInk, fontWeight = FontWeight.Bold) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(target.third, color = AppText, fontWeight = FontWeight.SemiBold)
                    Text(
                        "Pastikan aset benar-benar sudah dikosongkan. Kapasitas tetap terkunci sampai Admin memeriksa foto dan menyetujui permintaan.",
                        color = AppTextMuted,
                        fontSize = 12.sp,
                        lineHeight = 17.sp
                    )
                    TextButton(
                        enabled = !capacityClearanceSending,
                        onClick = { capacityClearancePicker.launch(arrayOf("image/*")) },
                        contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 0.dp, vertical = 0.dp)
                    ) {
                        Text(
                            if (capacityClearancePhoto == null) "Pilih foto kondisi kosong" else "Ganti foto bukti",
                            color = LoginAccent,
                            fontWeight = FontWeight.SemiBold
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
                        enabled = !capacityClearanceSending
                    )
                    if (capacityClearanceError.isNotBlank()) InlineError(capacityClearanceError)
                }
            },
            confirmButton = {
                TextButton(
                    enabled = capacityClearancePhoto != null && capacityClearanceReason.trim().length >= 8 && !capacityClearanceSending,
                    onClick = {
                        val photo = capacityClearancePhoto ?: return@TextButton
                        scope.launch {
                            capacityClearanceSending = true
                            capacityClearanceError = ""
                            try {
                                onRequestCapacityClearance(target.first, target.second, photo, capacityClearanceReason.trim())
                                capacityClearanceTarget = null
                                capacityClearancePhoto = null
                                capacityClearanceReason = ""
                            } catch (cancelled: CancellationException) {
                                throw cancelled
                            } catch (error: Exception) {
                                capacityClearanceError = error.message ?: "Permintaan pengosongan gagal dikirim."
                            } finally {
                                capacityClearanceSending = false
                            }
                        }
                    }
                ) { Text(if (capacityClearanceSending) "Mengirim…" else "Kirim ke Admin", color = LoginAccent) }
            },
            dismissButton = {
                TextButton(
                    onClick = { capacityClearanceTarget = null },
                    enabled = !capacityClearanceSending
                ) { Text("Batal", color = LoginMuted) }
            },
            containerColor = AppSurface
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
                ) { Text(if (processingReportAction) "Menyimpan…" else "Tolak laporan", color = RoleRed) }
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
    archivingHistoryTask?.let { task ->
        AlertDialog(
            onDismissRequest = { if (!archiveActionRunning) archivingHistoryTask = null },
            title = { Text("Arsipkan catatan tugas?", color = LoginInk, fontWeight = FontWeight.Bold) },
            text = {
                Text(
                    "${task.code} dan catatan pekerjaannya dipindahkan ke Arsip Petugas. Data tetap tersimpan dan bisa dipulihkan kapan saja.",
                    color = AppTextMuted,
                    fontSize = 12.sp,
                    lineHeight = 18.sp
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
                                archiveActionError = error.message ?: "Catatan tugas gagal diarsipkan."
                            } finally {
                                archiveActionRunning = false
                            }
                        }
                    }
                ) { Text(if (archiveActionRunning) "Menyimpan…" else "Arsipkan", color = LoginAccent) }
            },
            dismissButton = {
                TextButton(
                    onClick = { archivingHistoryTask = null },
                    enabled = !archiveActionRunning
                ) { Text("Batal", color = LoginMuted) }
            },
            containerColor = AppSurface
        )
    }
    restoringHistoryTask?.let { task ->
        AlertDialog(
            onDismissRequest = { if (!archiveActionRunning) restoringHistoryTask = null },
            title = { Text("Pulihkan catatan tugas?", color = LoginInk, fontWeight = FontWeight.Bold) },
            text = {
                Text(
                    "${task.code} akan kembali ke riwayat pekerjaan Petugas.",
                    color = AppTextMuted,
                    fontSize = 12.sp,
                    lineHeight = 18.sp
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
                                archiveActionError = error.message ?: "Catatan tugas gagal dipulihkan."
                            } finally {
                                archiveActionRunning = false
                            }
                        }
                    }
                ) { Text(if (archiveActionRunning) "Memulihkan…" else "Pulihkan", color = LoginAccent) }
            },
            dismissButton = {
                TextButton(
                    onClick = { restoringHistoryTask = null },
                    enabled = !archiveActionRunning
                ) { Text("Kembali", color = LoginMuted) }
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
                            cameraEvidencePath = null
                            selectedCameraUri = null
                            showProofSourceDialog = false
                            proofPicker.launch(arrayOf("image/*"))
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
    if (showAssistantDialog) {
        PetugasAssistantDialog(
            messages = assistantMessages,
            draft = assistantDraft,
            isSending = assistantSending,
            errorMessage = assistantError,
            onDraftChange = { assistantDraft = it.take(600) },
            onSend = ::sendAssistantQuestion,
            onDismiss = { showAssistantDialog = false },
            onOpenTask = { link ->
                showAssistantDialog = false
                when (link.pageKey) {
                    "laporan" -> {
                        selectedSection = "tugas"
                        selectedWorkArea = "laporan"
                        incomingSearch = link.code
                    }
                    "aktif" -> {
                        selectedSection = "tugas"
                        selectedWorkArea = "aktif"
                        activeTaskFilter = "semua"
                        activeTaskSearch = link.code
                    }
                    "riwayat" -> {
                        selectedSection = "riwayat"
                        showingTaskArchive = false
                        historyFilter = "semua"
                        historySearch = link.code
                    }
                }
            }
        )
    }
    val taskForProof = selectedCompletionTask
    val proofUri = selectedProofUri
    if (taskForProof != null && proofUri != null) {
        AlertDialog(
            onDismissRequest = { if (!sendingProof) clearProofSelection() },
            title = { Text("Kirim foto sesudah penanganan", color = LoginInk, fontWeight = FontWeight.Bold) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(9.dp)) {
                    Text("${taskForProof.code} · ${taskForProof.weightKg.pretty()} kg", color = AppTextMuted, fontSize = 11.sp)
                    Text(
                        "Ambil foto akhir setelah tugas ditangani dan sampah diterima di ${taskForProof.tpaName.ifBlank { "TPA tujuan" }}. Admin membandingkannya dengan foto awal warga sebelum menyetujui penyelesaian.",
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
                ) { Text(if (sendingProof) "Mengirim…" else "Kirim foto akhir", color = LoginAccent) }
            },
            dismissButton = {
                TextButton(onClick = { clearProofSelection() }, enabled = !sendingProof) {
                    Text("Batal", color = LoginMuted)
                }
            },
            containerColor = AppSurface
        )
    }
    if (showLocationPhotoSourceDialog && selectedLocationTask != null && selectedLocationPhotoUri == null) {
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
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        "Kirim satu foto kondisi di titik laporan agar Admin bisa melihatnya. Bukti ini terpisah dari foto penerimaan TPA.",
                        color = AppText,
                        fontSize = 12.sp,
                        lineHeight = 17.sp
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
                        }
                    ) { Text("Galeri", color = LoginMuted) }
                    TextButton(onClick = ::launchLocationPhotoCamera) {
                        Icon(Icons.Filled.PhotoCamera, contentDescription = null, tint = LoginAccent)
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
            containerColor = AppSurface
        )
    }
    val taskForLocationEvidence = selectedLocationTask
    val locationPhotoUri = selectedLocationPhotoUri
    if (taskForLocationEvidence != null && locationPhotoUri != null) {
        AlertDialog(
            onDismissRequest = {
                if (sendingLocationTaskId == null) clearLocationPhotoSelection()
            },
            title = { Text("Kirim foto ke Admin?", color = LoginInk, fontWeight = FontWeight.Bold) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(7.dp)) {
                    Text(taskForLocationEvidence.code, color = AppText, fontWeight = FontWeight.SemiBold)
                    Text(
                        taskForLocationEvidence.location.ifBlank { "Lokasi belum dicatat" },
                        color = AppTextMuted,
                        fontSize = 12.sp,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        "Foto akan tersimpan bersama waktu pengiriman dan tampil di halaman Bukti kerja Admin.",
                        color = AppText,
                        fontSize = 12.sp,
                        lineHeight = 17.sp
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
                                locationPhotoError = error.message ?: "Foto lokasi gagal dikirim. Coba lagi."
                            } finally {
                                sendingLocationTaskId = null
                            }
                        }
                    }
                ) {
                    Text(
                        if (sendingLocationTaskId != null) "Mengirim…" else "Kirim foto",
                        color = LoginAccent
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = ::clearLocationPhotoSelection,
                    enabled = sendingLocationTaskId == null
                ) { Text("Batal", color = LoginMuted) }
            },
            containerColor = AppSurface
        )
    }
    reportingDamageVehicle?.let { vehicle ->
        VehicleDamageDialog(
            vehicle = vehicle,
            onDismiss = { reportingDamageVehicle = null },
            onSubmit = { cause, details ->
                onReportVehicleDamage(vehicle, cause, details)
                reportingDamageVehicle = null
            }
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
            }
        )
    }
    if (showVehicleForm) {
        VehicleFormDialog(
            vehicle = editingVehicle,
            onDismiss = { showVehicleForm = false; editingVehicle = null },
            onSave = { plate, capacity, route ->
                val vehicle = editingVehicle
                if (vehicle == null) onCreateVehicle(plate, capacity, route)
                else onUpdateVehicle(vehicle.copy(plate = plate, capacityKg = capacity, route = route))
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
private fun PetugasAssistantEntry(onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 48.dp)
            .clickable(onClick = onClick)
            .padding(horizontal = 2.dp, vertical = 5.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(9.dp)
    ) {
        Icon(Icons.Filled.Search, contentDescription = null, tint = LoginAccent, modifier = Modifier.size(19.dp))
        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Text("Butuh arahan?", color = AppText, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
            Text("Tanya tugas dan langkah berikutnya", color = AppTextMuted, fontSize = 11.sp)
        }
        Text("Buka  ›", color = LoginAccent, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
private fun PetugasAssistantDialog(
    messages: List<PetugasAssistantChatMessage>,
    draft: String,
    isSending: Boolean,
    errorMessage: String,
    onDraftChange: (String) -> Unit,
    onSend: (String) -> Unit,
    onDismiss: () -> Unit,
    onOpenTask: (PetugasAssistantLink) -> Unit
) {
    val chatMaxHeight = (LocalConfiguration.current.screenHeightDp.dp - 360.dp)
        .coerceIn(120.dp, 320.dp)
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text("Asisten Petugas", color = LoginInk, fontWeight = FontWeight.Bold)
                Text("Data tugas terkini · hanya baca", color = AppTextMuted, fontSize = 11.sp)
            }
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Column(
                    modifier = Modifier.fillMaxWidth().heightIn(max = chatMaxHeight)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(9.dp)
                ) {
                    if (messages.isEmpty()) {
                        Text(
                            "Tanyakan prioritas kerja, laporan masuk, atau status tugas. Tombol pada jawaban akan membuka tab laporan yang sesuai.",
                            color = AppTextMuted,
                            fontSize = 12.sp,
                            lineHeight = 17.sp
                        )
                        TextButton(
                            enabled = !isSending,
                            onClick = { onSend("Tampilkan daftar tugas saya dan urutkan berdasarkan prioritas.") }
                        ) { Text("Tampilkan daftar tugas saya", color = LoginAccent) }
                        TextButton(
                            enabled = !isSending,
                            onClick = { onSend("Apa yang harus saya kerjakan lebih dulu?") }
                        ) { Text("Apa prioritas saya sekarang?", color = LoginAccent) }
                        TextButton(
                            enabled = !isSending,
                            onClick = { onSend("Cek laporan masuk yang perlu saya tinjau.") }
                        ) { Text("Cek laporan masuk", color = LoginAccent) }
                    }
                    messages.forEach { message ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = if (message.fromAssistant) Arrangement.Start else Arrangement.End
                        ) {
                            Column(
                                modifier = Modifier.widthIn(max = 310.dp)
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(
                                        if (message.fromAssistant) AppSurfaceMuted.copy(alpha = 0.85f)
                                        else LoginAccent.copy(alpha = 0.11f)
                                    )
                                    .padding(horizontal = 12.dp, vertical = 10.dp),
                                verticalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Text(
                                    message.text,
                                    color = AppText,
                                    fontSize = 12.sp,
                                    lineHeight = 17.sp
                                )
                                if (message.nextSteps.isNotEmpty()) {
                                    Text("Langkah berikutnya", color = LoginAccent, fontSize = 10.sp, fontWeight = FontWeight.SemiBold)
                                    message.nextSteps.forEachIndexed { index, step ->
                                        Text("${index + 1}. $step", color = AppTextMuted, fontSize = 11.sp, lineHeight = 15.sp)
                                    }
                                }
                                message.taskLinks.forEach { link ->
                                    TextButton(
                                        onClick = { onOpenTask(link) },
                                        contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 2.dp, vertical = 2.dp)
                                    ) {
                                        Text(
                                            "${link.actionLabel}: ${link.code}  ·  ${link.pageLabel}",
                                            color = LoginAccent,
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                    }
                                }
                            }
                        }
                    }
                    if (isSending) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            CircularProgressIndicator(modifier = Modifier.size(16.dp), strokeWidth = 2.dp, color = LoginAccent)
                            Text("Mengecek tugas terbaru…", color = AppTextMuted, fontSize = 11.sp)
                        }
                    }
                    if (errorMessage.isNotBlank()) InlineError(errorMessage)
                }
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    OutlinedTextField(
                        value = draft,
                        onValueChange = onDraftChange,
                        modifier = Modifier.weight(1f),
                        placeholder = { Text("Tulis pertanyaan…", fontSize = 12.sp) },
                        enabled = !isSending,
                        maxLines = 3,
                        shape = RoundedCornerShape(16.dp)
                    )
                    TextButton(
                        enabled = draft.isNotBlank() && !isSending,
                        onClick = { onSend(draft) }
                    ) { Text(if (isSending) "…" else "Kirim", color = LoginAccent, fontWeight = FontWeight.SemiBold) }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) { Text("Tutup", color = LoginMuted) }
        },
        containerColor = AppSurface
    )
}

@Composable
private fun CapacityLimitNotice(
    title: String,
    detail: String,
    status: String?,
    actionLabel: String?,
    onAction: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(13.dp))
            .background(RoleOrange.copy(alpha = 0.11f))
            .padding(horizontal = 10.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(3.dp)
    ) {
        Text(title, color = RoleOrange, fontSize = 11.sp, fontWeight = FontWeight.Bold)
        Text(detail, color = AppTextMuted, fontSize = 10.sp, lineHeight = 14.sp)
        if (!status.isNullOrBlank()) {
            Text(status, color = AppText, fontSize = 10.sp, fontWeight = FontWeight.Medium)
        }
        if (actionLabel != null) {
            TextButton(
                onClick = onAction,
                contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 0.dp, vertical = 0.dp)
            ) { Text(actionLabel, color = LoginAccent, fontSize = 11.sp, fontWeight = FontWeight.SemiBold) }
        }
    }
}

private fun clearanceStatusText(request: CapacityClearanceRequest): String = when (request.status.lowercase()) {
    "menunggu" -> "Menunggu pemeriksaan Admin"
    "disetujui" -> "Disetujui · kapasitas sudah dikosongkan"
    "ditolak" -> "Ditolak${request.adminNote.takeIf { it.isNotBlank() }?.let { ": $it" }.orEmpty()}"
    else -> request.status.replace('_', ' ').replaceFirstChar { it.uppercase() }
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
                    Text("Belum dapat diterima: tidak tersedia $unavailable.", color = RoleRed, fontSize = 10.sp)
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
    val compact = LocalConfiguration.current.screenWidthDp < 600
    // Keep the full-screen canvas still between theme changes to avoid continuous GPU work.
    val ambientPhase = 0f
    val nightProgress by animateFloatAsState(
        targetValue = if (darkMode) 1f else 0f,
        animationSpec = tween(durationMillis = 760, easing = FastOutSlowInEasing),
        label = "petugas-theme-progress"
    )

    val sky = listOf(
        lerp(Color(0xFFC5DDE5), Color(0xFF111C2A), nightProgress),
        lerp(Color(0xFFE8E3D5), Color(0xFF18302F), nightProgress),
        lerp(Color(0xFFC1D3BF), Color(0xFF10221F), nightProgress)
    )
    val navigationAreaHeight = 87.dp + WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()

    Box(modifier = modifier.background(Brush.verticalGradient(sky))) {
        Canvas(
            modifier = Modifier.fillMaxSize().padding(bottom = navigationAreaHeight)
        ) {
            val width = size.width
            val height = size.height
            val breeze = sin(ambientPhase.toDouble()).toFloat()
            val drift = breeze * 12.dp.toPx()
            val mobileSceneShift = if (compact) 0.025f else 0f
            val dayProgress = 1f - nightProgress
            val nightPhase = ambientPhase

            run {
                val glowPulse = 0.5f + 0.5f * sin(nightPhase.toDouble()).toFloat()
                val moon = Offset(
                    width * (if (compact) 0.94f else 0.81f) + if (compact) 0f else drift * 0.12f,
                    height * (if (compact) 0.11f else 0.17f)
                )
                val glowRadius = width * if (compact) 0.25f else (0.34f + glowPulse * 0.03f)
                drawCircle(
                    Brush.radialGradient(
                        listOf(
                            Color(0xFFDDE8C9).copy(alpha = (if (compact) 0.12f else 0.22f + glowPulse * 0.06f) * nightProgress),
                            Color(0xFF8EB9A7).copy(alpha = (if (compact) 0.045f else 0.10f) * nightProgress),
                            Color.Transparent
                        ),
                        center = moon,
                        radius = glowRadius
                    ),
                    radius = glowRadius,
                    center = moon
                )
                if (!compact) {
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
                val morningGlow = Offset(
                    width * (if (compact) 0.94f else 0.78f) + if (compact) 0f else drift * 0.12f,
                    height * (if (compact) 0.10f else 0.22f)
                )
                val morningGlowRadius = width * if (compact) 0.24f else 0.36f
                drawCircle(
                    Brush.radialGradient(
                        listOf(Color(0xFFF1E6CA).copy(alpha = (if (compact) 0.20f else 0.36f) * dayProgress), Color.Transparent),
                        center = morningGlow,
                        radius = morningGlowRadius
                    ),
                    radius = morningGlowRadius,
                    center = morningGlow
                )
                // Soft morning clouds and a quiet breeze echo the login screen's light theme.
                if (!compact) {
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
            }

            // Wetland plants and a narrow stream keep the lower scene environmental
            // without repeating the hills and tree silhouettes from the login screen.
            val bank = Path().apply {
                moveTo(0f, height * (0.81f + mobileSceneShift))
                cubicTo(width * 0.20f, height * (0.79f + mobileSceneShift), width * 0.31f, height * (0.83f + mobileSceneShift), width * 0.49f, height * (0.81f + mobileSceneShift))
                cubicTo(width * 0.67f, height * (0.79f + mobileSceneShift), width * 0.84f, height * (0.83f + mobileSceneShift), width, height * (0.80f + mobileSceneShift))
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

            val shorelineStemColor = lerp(
                Color(0xFF557A61).copy(alpha = 0.78f),
                Color(0xFF8BA391).copy(alpha = 0.68f),
                nightProgress
            )
            listOf(0.28f to 0.851f, 0.73f to 0.842f).forEach { (xFraction, yFraction) ->
                val stemTop = Offset(width * xFraction, height * yFraction + 2.dp.toPx())
                val stemBottom = Offset(width * xFraction, height * (yFraction + 0.055f))
                val stem = Path().apply {
                    moveTo(stemTop.x, stemTop.y)
                    cubicTo(
                        stemTop.x - 1.5.dp.toPx(), stemTop.y + 6.dp.toPx(),
                        stemBottom.x + 1.5.dp.toPx(), stemBottom.y - 5.dp.toPx(),
                        stemBottom.x, stemBottom.y
                    )
                }
                drawPath(
                    stem,
                    shorelineStemColor,
                    style = Stroke(width = 1.2.dp.toPx(), cap = StrokeCap.Round)
                )
            }

            val water = Path().apply {
                moveTo(0f, height * (0.88f + mobileSceneShift))
                cubicTo(width * 0.18f, height * (0.83f + mobileSceneShift), width * 0.33f, height * (0.92f + mobileSceneShift), width * 0.51f, height * (0.87f + mobileSceneShift))
                cubicTo(width * 0.70f, height * (0.82f + mobileSceneShift), width * 0.84f, height * (0.90f + mobileSceneShift), width, height * (0.85f + mobileSceneShift))
                lineTo(width, height)
                lineTo(0f, height)
                close()
            }
            val waterColors = listOf(
                lerp(Color(0xFFB4D2CF).copy(alpha = 0.66f), Color(0xFF2B5551).copy(alpha = 0.84f), nightProgress),
                lerp(Color(0xFF8FB8B5).copy(alpha = 0.60f), Color(0xFF142B2B).copy(alpha = 0.94f), nightProgress)
            )
            drawPath(water, Brush.verticalGradient(waterColors, startY = height * (0.83f + mobileSceneShift), endY = height))
            val waterEdge = Path().apply {
                moveTo(0f, height * (0.88f + mobileSceneShift))
                cubicTo(width * 0.18f, height * (0.83f + mobileSceneShift), width * 0.33f, height * (0.92f + mobileSceneShift), width * 0.51f, height * (0.87f + mobileSceneShift))
                cubicTo(width * 0.70f, height * (0.82f + mobileSceneShift), width * 0.84f, height * (0.90f + mobileSceneShift), width, height * (0.85f + mobileSceneShift))
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
                Triple(0.67f, 0.875f + mobileSceneShift, 0.09f),
                Triple(0.76f, 0.91f + mobileSceneShift, 0.12f),
                Triple(0.62f, 0.94f + mobileSceneShift, 0.10f),
                Triple(0.82f, 0.96f + mobileSceneShift, 0.07f)
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
                Triple(0.16f, 0.862f + mobileSceneShift, 0.76f),
                Triple(0.39f, 0.878f + mobileSceneShift, 1.00f),
                Triple(0.65f, 0.847f + mobileSceneShift, 0.88f),
                Triple(0.85f, 0.862f + mobileSceneShift, 0.70f)
            )
            lilyPads.forEachIndexed { index, (xFraction, yFraction, scale) ->
                val gentleBob = sin((waterPhase + index * 1.15f).toDouble()).toFloat() * 2.2.dp.toPx()
                val center = Offset(width * xFraction, height * yFraction + gentleBob)
                val radiusX = width * 0.030f * scale
                val radiusY = height * 0.011f * scale
                val petioleEnd = Offset(center.x + radiusX * 0.24f, height * 0.985f)
                val petiole = Path().apply {
                    moveTo(center.x, center.y + radiusY * 0.20f)
                    cubicTo(
                        center.x - radiusX * 0.30f, center.y + radiusY * 2.8f,
                        petioleEnd.x + radiusX * 0.28f, petioleEnd.y - height * 0.10f,
                        petioleEnd.x, petioleEnd.y
                    )
                }
                drawPath(
                    petiole,
                    lerp(
                        Color(0xFF47765E).copy(alpha = 0.32f),
                        Color(0xFF9AB4A1).copy(alpha = 0.30f),
                        nightProgress
                    ),
                    style = Stroke(width = 1.05.dp.toPx(), cap = StrokeCap.Round)
                )
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
            listOf(
                0.28f to (0.851f + mobileSceneShift),
                0.73f to (0.842f + mobileSceneShift)
            ).forEachIndexed { index, (xFraction, yFraction) ->
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
}

@Composable
private fun PetugasJourneyHero(
    name: String,
    tasks: List<WasteRecord>,
    incomingReportCount: Int,
    isInitialLoading: Boolean,
    onOpenTasks: () -> Unit
) {
    val isDark = LocalSmartWasteAppearance.current.darkMode
    val assigned = tasks.count { it.status == WasteFlow.ASSIGNED }
    val transported = tasks.count { it.status == WasteFlow.TRANSPORTED }
    val waitingAdmin = tasks.count {
        it.status == WasteFlow.TRANSPORTED && it.completionStatus.equals("menunggu", ignoreCase = true)
    }
    val needsRevision = tasks.count {
        (it.status == WasteFlow.ASSIGNED || it.status == WasteFlow.TRANSPORTED) &&
                it.completionStatus.equals("perlu_perbaikan", ignoreCase = true)
    }
    val inRoute = tasks.count {
        it.status == WasteFlow.TRANSPORTED &&
                !it.completionStatus.equals("menunggu", ignoreCase = true) &&
                !it.completionStatus.equals("perlu_perbaikan", ignoreCase = true)
    }
    val completed = tasks.count { it.status == WasteFlow.DONE }
    val needsAttention = waitingAdmin + needsRevision
    val total = tasks.count {
        it.status == WasteFlow.ASSIGNED || it.status == WasteFlow.TRANSPORTED || it.status == WasteFlow.DONE
    }
    val active = assigned + transported
    val heroColors = if (isDark) {
        listOf(Color(0xFF26392F), Color(0xFF30443A))
    } else {
        listOf(Color(0xFF50735F), Color(0xFF426551))
    }
    val heroText = Color(0xFFF2F0E8)
    val heroMuted = Color(0xFFD8E1D8)
    val progress = if (total == 0) 0 else (completed * 100 / total)
    val displayName = name.displayPersonName().ifBlank { "Petugas" }
    val readyTint = if (isDark) Color(0xFFB7C99E) else Color(0xFF5C805E)
    val routeTint = if (isDark) Color(0xFF9FC1B6) else Color(0xFF4E7D73)
    val attentionTint = if (isDark) Color(0xFFD3BA8C) else Color(0xFF9A7746)
    val workSummary = when {
        isInitialLoading -> "Memuat tugas dan laporan terbaru."
        needsRevision > 0 -> "$needsRevision bukti perlu diperbaiki. Dahulukan pemeriksaan ini."
        waitingAdmin > 0 && inRoute > 0 -> "$inRoute muatan dalam rute · $waitingAdmin bukti menunggu Admin."
        waitingAdmin > 0 -> "$waitingAdmin bukti menunggu pemeriksaan Admin."
        inRoute > 0 -> "$inRoute muatan sedang menuju TPA."
        assigned > 0 && incomingReportCount > 0 ->
            "$assigned tugas siap angkut · $incomingReportCount laporan baru menunggu."
        assigned > 0 -> "$assigned tugas siap dibawa. Pilih yang paling dekat untuk dimulai."
        incomingReportCount > 0 -> "$incomingReportCount laporan baru menunggu penugasan."
        else -> "Belum ada tugas aktif. Cek antrean laporan saat ada waktu."
    }
    val actionLabel = when {
        active > 0 -> "Lanjutkan tugas"
        incomingReportCount > 0 -> "Tinjau laporan"
        else -> "Buka antrean"
    }

    val progressFraction = if (total > 0) (completed.toFloat() / total).coerceIn(0f, 1f) else 0f
    val detailText = AppText
    val detailMuted = AppTextMuted
    val progressTint = if (isDark) Color(0xFFABC49B) else Color(0xFF52765B)
    val progressTrack = if (isDark) Color(0xFF3A4A40) else Color(0xFFDCE4D8)

    Column(
        modifier = Modifier.fillMaxWidth()
            .clip(RoundedCornerShape(23.dp))
            .background(AppSurface)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
                .background(Brush.linearGradient(heroColors))
                .padding(horizontal = 17.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Text(
                    "RUANG KERJA · PETUGAS",
                    color = heroMuted,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 0.55.sp
                )
                Text(
                    "Halo, $displayName",
                    color = heroText,
                    fontSize = 20.sp,
                    lineHeight = 24.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    workSummary,
                    color = heroMuted,
                    fontSize = 10.sp,
                    lineHeight = 14.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Column(
                modifier = Modifier.size(76.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.10f)),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(active.toString(), color = heroText, fontSize = 27.sp, lineHeight = 30.sp, fontWeight = FontWeight.Bold)
                Text("tugas aktif", color = heroMuted, fontSize = 8.sp, fontWeight = FontWeight.Medium)
            }
        }

        Column(
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 13.dp),
            verticalArrangement = Arrangement.spacedBy(11.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                PetugasHeroStat(assigned.toString(), "Siap angkut", readyTint, detailMuted, Modifier.weight(1f))
                PetugasHeroStat(inRoute.toString(), "Dalam rute", routeTint, detailMuted, Modifier.weight(1f))
                PetugasHeroStat(needsAttention.toString(), "Perlu dicek", attentionTint, detailMuted, Modifier.weight(1f))
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
                    Text("Penyelesaian", color = detailMuted, fontSize = 9.sp, fontWeight = FontWeight.Medium)
                    Text(
                        when {
                            isInitialLoading -> "Memperbarui antrean…"
                            total > 0 -> "$completed dari $total selesai · $progress%"
                            else -> "Belum ada pekerjaan yang selesai"
                        },
                        color = detailText,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                TextButton(
                    onClick = onOpenTasks,
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(start = 8.dp, end = 0.dp, top = 2.dp, bottom = 2.dp)
                ) {
                    Text("$actionLabel  ›", color = progressTint, fontSize = 10.sp, fontWeight = FontWeight.SemiBold)
                }
            }
            Box(
                modifier = Modifier.fillMaxWidth().height(4.dp)
                    .clip(CircleShape)
                    .background(progressTrack)
            ) {
                if (progressFraction > 0f) {
                    Box(
                        modifier = Modifier.fillMaxWidth(progressFraction).fillMaxHeight()
                            .background(progressTint)
                    )
                }
            }
        }
    }
}

@Composable
private fun PetugasHeroStat(
    value: String,
    label: String,
    tint: Color,
    labelColor: Color,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(2.dp)) {
        Text(value, color = tint, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(5.dp)) {
            Box(Modifier.size(6.dp).clip(CircleShape).background(tint))
            Text(label, color = labelColor, fontSize = 9.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
        }
    }
}

@Composable
private fun PetugasOperationalOverview(tasks: List<WasteRecord>) {
    val isDark = LocalSmartWasteAppearance.current.darkMode
    val activeTasks = tasks.filter {
        it.status == WasteFlow.ASSIGNED || it.status == WasteFlow.TRANSPORTED
    }
    val activeWeight = activeTasks.sumOf { it.weightKg }
    val activeTypes = activeTasks.map { it.type.trim() }.filter(String::isNotBlank).distinct().size
    val destination = activeTasks.firstOrNull { it.tpaName.isNotBlank() }?.tpaName
    val accent = if (isDark) Color(0xFFB9D4BE) else Color(0xFF286947)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(AppSurface)
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(1.dp)) {
                Text("Ringkasan muatan", color = AppText, fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
                Text("Pekerjaan yang sedang kamu tangani", color = AppTextMuted, fontSize = 11.sp)
            }
        }

        Row(verticalAlignment = Alignment.Bottom) {
            Text(activeWeight.pretty(), color = AppText, fontSize = 25.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.width(5.dp))
            Text("kg muatan aktif", color = AppTextMuted, fontSize = 11.sp, modifier = Modifier.padding(bottom = 3.dp))
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            PetugasSummaryFact(
                icon = Icons.Filled.Inbox,
                text = "$activeTypes jenis sampah",
                tint = if (isDark) Color(0xFF91CDBD) else Color(0xFF287B69),
                modifier = Modifier.weight(1f)
            )
            PetugasSummaryFact(
                icon = Icons.Filled.LocalShipping,
                text = "${activeTasks.size} tugas aktif",
                tint = if (isDark) Color(0xFF91CDBD) else Color(0xFF287B69),
                modifier = Modifier.weight(1f)
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            Icon(Icons.Filled.Place, contentDescription = null, tint = accent, modifier = Modifier.size(15.dp))
            Text("Tujuan", color = AppTextMuted, fontSize = 11.sp)
            Text(
                destination ?: "Belum ditentukan",
                modifier = Modifier.weight(1f),
                color = AppText,
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
private fun PetugasSummaryFact(icon: ImageVector, text: String, tint: Color, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Icon(icon, contentDescription = null, tint = tint, modifier = Modifier.size(15.dp))
        Text(text, color = AppTextMuted, fontSize = 11.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
    }
}

private data class PetugasFilterOption(
    val key: String,
    val label: String,
    val count: Int
)

@Composable
private fun PetugasFilterGrid(
    choices: List<PetugasFilterOption>,
    selectedKey: String,
    onSelect: (String) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        choices.chunked(3).forEach { rowChoices ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                val sideSpace = (3 - rowChoices.size) / 2f
                if (sideSpace > 0f) Spacer(modifier = Modifier.weight(sideSpace))
                rowChoices.forEach { choice ->
                    PetugasFilterChoice(
                        choice = choice,
                        selected = choice.key == selectedKey,
                        onSelect = onSelect,
                        modifier = Modifier.weight(1f)
                    )
                }
                if (sideSpace > 0f) Spacer(modifier = Modifier.weight(sideSpace))
            }
        }
    }
}

@Composable
private fun PetugasFilterChoice(
    choice: PetugasFilterOption,
    selected: Boolean,
    onSelect: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val shape = RoundedCornerShape(14.dp)
    val containerColor by animateColorAsState(
        targetValue = if (selected) {
            LoginAccent.copy(alpha = if (IsNightAdmin) 0.26f else 0.15f)
        } else {
            AppSurface.copy(alpha = if (IsNightAdmin) 0.68f else 0.74f)
        },
        animationSpec = tween(280, easing = FastOutSlowInEasing),
        label = "petugas-filter-${choice.key}-surface"
    )
    val titleColor by animateColorAsState(
        targetValue = if (selected) LoginAccent else AppText,
        animationSpec = tween(240, easing = FastOutSlowInEasing),
        label = "petugas-filter-${choice.key}-title"
    )
    val countColor by animateColorAsState(
        targetValue = if (selected) LoginAccent else AppTextMuted,
        animationSpec = tween(240, easing = FastOutSlowInEasing),
        label = "petugas-filter-${choice.key}-count"
    )

    Column(
        modifier = modifier
            .heightIn(min = 58.dp)
            .clip(shape)
            .background(containerColor)
            .selectable(
                selected = selected,
                role = Role.Tab,
                onClick = { onSelect(choice.key) }
            )
            .padding(horizontal = 9.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = choice.label,
            color = titleColor,
            fontFamily = InterFont,
            fontSize = 12.sp,
            lineHeight = 15.sp,
            fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Medium,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = "${choice.count} data",
            color = countColor,
            fontFamily = InterFont,
            fontSize = 11.sp,
            lineHeight = 14.sp,
            fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Medium
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
private fun AccountProfileSettings(
    account: UserAccount,
    onLogout: () -> Unit,
    onUpdateProfile: suspend (String) -> UserAccount,
    onUpdateProfilePhoto: (suspend (Uri) -> UserAccount)? = null
) {
    val appearance = LocalSmartWasteAppearance.current
    val isDark = appearance.darkMode
    val cardShape = RoundedCornerShape(24.dp)
    val surface = AppSurface.copy(alpha = if (isDark) 0.88f else 0.92f)
    var showNameDialog by rememberSaveable(account.username) { mutableStateOf(false) }
    var nameDraft by rememberSaveable(account.username) { mutableStateOf(account.name) }
    var nameError by remember { mutableStateOf("") }
    var savingName by remember { mutableStateOf(false) }
    var photoError by remember { mutableStateOf("") }
    var savingPhoto by rememberSaveable(account.username) { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val profilePhotoPicker = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        val updatePhoto = onUpdateProfilePhoto
        if (uri != null && updatePhoto != null) {
            scope.launch {
                savingPhoto = true
                photoError = ""
                try {
                    updatePhoto(uri)
                } catch (cancelled: CancellationException) {
                    throw cancelled
                } catch (error: Exception) {
                    photoError = error.message ?: "Foto profil gagal diperbarui."
                } finally {
                    savingPhoto = false
                }
            }
        }
    }

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        AdminSectionHeading(
            eyebrow = "AKUN DAN PREFERENSI",
            title = "Profil ${account.role.label.lowercase()}",
            subtitle = "Informasi akun dan preferensi aplikasi."
        )

        Column(
            modifier = Modifier.fillMaxWidth()
                .clip(cardShape)
                .background(surface)
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(14.dp)) {
                UserAvatar(account.name, Modifier.size(58.dp), account.profilePhotoUrl)
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        account.name.displayPersonName(),
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (onUpdateProfilePhoto != null) {
                    TextButton(
                        onClick = { profilePhotoPicker.launch("image/*") },
                        enabled = !savingPhoto && !savingName
                    ) {
                        if (savingPhoto) {
                            Text("Menyimpan…", color = LoginAccent, fontWeight = FontWeight.SemiBold)
                        } else {
                            Icon(
                                Icons.Filled.PhotoCamera,
                                contentDescription = null,
                                tint = LoginAccent,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(Modifier.width(5.dp))
                            Text("Ganti foto", color = LoginAccent, fontWeight = FontWeight.SemiBold)
                        }
                    }
                }
                TextButton(
                    onClick = {
                        nameDraft = account.name
                        nameError = ""
                        showNameDialog = true
                    },
                    enabled = !savingName && !savingPhoto
                ) {
                    Text("Ubah nama profil", color = LoginAccent, fontWeight = FontWeight.SemiBold)
                }
            }
            if (photoError.isNotBlank()) {
                Text(photoError, color = RoleRed, fontSize = 11.sp)
            }
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
            Icon(Icons.Filled.SwitchAccount, contentDescription = null, tint = RoleRed)
            Spacer(Modifier.width(8.dp))
            Text("Keluar dari akun", color = RoleRed, fontWeight = FontWeight.Bold)
        }
    }

    if (showNameDialog) {
        AlertDialog(
            onDismissRequest = { if (!savingName) showNameDialog = false },
            title = { Text("Ubah nama profil", color = AppText, fontWeight = FontWeight.Bold) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = nameDraft,
                        onValueChange = {
                            nameDraft = it.take(120)
                            nameError = ""
                        },
                        label = { Text("Nama") },
                        singleLine = true,
                        enabled = !savingName,
                        modifier = Modifier.fillMaxWidth()
                    )
                    if (nameError.isNotBlank()) {
                        Text(nameError, color = RoleRed, fontSize = 12.sp)
                    }
                }
            },
            confirmButton = {
                TextButton(
                    enabled = !savingName,
                    onClick = {
                        val newName = nameDraft.trim()
                        if (newName.isEmpty()) {
                            nameError = "Nama tidak boleh kosong."
                        } else {
                            scope.launch {
                                savingName = true
                                nameError = ""
                                try {
                                    val updated = onUpdateProfile(newName)
                                    nameDraft = updated.name
                                    showNameDialog = false
                                } catch (cancelled: CancellationException) {
                                    throw cancelled
                                } catch (error: Exception) {
                                    nameError = error.message ?: "Nama profil gagal diperbarui."
                                } finally {
                                    savingName = false
                                }
                            }
                        }
                    }
                ) {
                    Text(if (savingName) "Menyimpan…" else "Simpan", color = LoginAccent)
                }
            },
            dismissButton = {
                TextButton(onClick = { showNameDialog = false }, enabled = !savingName) {
                    Text("Batal", color = AppTextMuted)
                }
            }
        )
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
    compactHistory: Boolean = false,
    onMarkTransported: ((WasteRecord) -> Unit)? = null,
    isTransporting: Boolean = false,
    isActionEnabled: Boolean = true,
    onComplete: ((WasteRecord) -> Unit)? = null,
    onSendLocationEvidence: ((WasteRecord) -> Unit)? = null,
    isSendingLocationEvidence: Boolean = false,
    onArchive: ((WasteRecord) -> Unit)? = null
) {
    val isComplete = report.status == WasteFlow.DONE
    val isRejected = report.status == WasteFlow.REJECTED
    val isDark = LocalSmartWasteAppearance.current.darkMode
    val needsRevision = report.completionStatus.equals("perlu_perbaikan", ignoreCase = true)
    val waitingForAdmin = report.completionStatus.equals("menunggu", ignoreCase = true)
    val statusAccent = when (report.status) {
        WasteFlow.DONE -> if (isDark) Color(0xFF91C59B) else RoleGreen
        WasteFlow.REJECTED -> if (isDark) Color(0xFFE3A19A) else RoleRed
        WasteFlow.TRANSPORTED -> if (isDark) Color(0xFF82C7B9) else RoleTeal
        else -> if (isDark) Color(0xFFC2CF91) else LoginAccent
    }
    val guidanceAccent = when {
        needsRevision -> if (isDark) Color(0xFFE3A19A) else RoleRed
        waitingForAdmin -> if (isDark) Color(0xFFE0BB76) else RoleOrange
        else -> statusAccent
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
        report.status == WasteFlow.TRANSPORTED -> "Antar muatan ke ${report.tpaName.ifBlank { "TPA tujuan" }}, lalu kirim foto akhir setelah penanganan."
        else -> "Ambil muatan dengan ${report.vehiclePlate.ifBlank { "armada yang ditetapkan" }}, lalu mulai pengangkutan."
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(AppSurface)
    ) {
        Column(
            modifier = Modifier.padding(
                horizontal = if (compactHistory) 13.dp else 15.dp,
                vertical = if (compactHistory) 11.dp else 14.dp
            ),
            verticalArrangement = Arrangement.spacedBy(if (compactHistory) 6.dp else 9.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(1.dp)) {
                    Text(report.code, color = AppText, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                    Text("${report.type} · ${report.weightKg.pretty()} kg", color = AppTextMuted, fontSize = 11.sp)
                }
                StatusPill(report.status, statusAccent)
            }
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                Icon(Icons.Filled.Place, contentDescription = null, tint = statusAccent, modifier = Modifier.size(15.dp))
                Text(
                    report.location.ifBlank { "Lokasi belum dicatat" },
                    color = AppTextMuted,
                    fontSize = 11.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            if (!isRejected) Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(3.dp)) {
                    Text("ARMADA", color = AppTextMuted, fontSize = 10.sp, fontWeight = FontWeight.SemiBold)
                    Text(report.vehiclePlate.ifBlank { "Belum ditetapkan" }, color = AppText, fontSize = 11.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
                }
                Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(3.dp)) {
                    Text("TPA TUJUAN", color = AppTextMuted, fontSize = 10.sp, fontWeight = FontWeight.SemiBold)
                    Text(report.tpaName.ifBlank { "Belum ditetapkan" }, color = AppText, fontSize = 11.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
                }
            }
            if (compactHistory) {
                val historyNote = when {
                    isComplete -> "Bukti disetujui Admin"
                    report.workerDecisionNote.isNotBlank() -> report.workerDecisionNote
                    else -> "Laporan tidak diteruskan ke pengangkutan"
                }
                Text(
                    historyNote,
                    color = if (isComplete) statusAccent else AppTextMuted,
                    fontSize = 11.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            } else {
                Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                    Text(guidanceTitle, color = guidanceAccent, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                    Text(
                        guidanceMessage,
                        color = AppTextMuted,
                        fontSize = 11.sp,
                        lineHeight = 15.sp,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                if (!report.photoUrl.isNullOrBlank()) {
                    TextButton(
                        onClick = { onOpenPhoto(report.photoUrl) },
                        contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 7.dp, vertical = 4.dp)
                    ) {
                        Text("Foto awal · warga", color = statusAccent, fontSize = 11.sp)
                    }
                }
                if (!report.locationEvidencePhotoUrl.isNullOrBlank()) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        report.locationEvidenceSubmittedAt.takeIf { it.isNotBlank() }?.let {
                            Text(
                                "Foto lokasi · ${it.toDisplayDateTime()}",
                                modifier = Modifier.weight(1f),
                                color = AppTextMuted,
                                fontSize = 11.sp,
                                fontFamily = InterFont
                            )
                        }
                        TextButton(onClick = { onOpenPhoto(report.locationEvidencePhotoUrl) }) {
                            Text("Foto progres lokasi", color = LoginAccent, fontSize = 11.sp, fontFamily = InterFont)
                        }
                    }
                }
                if (!report.completionProofPhotoUrl.isNullOrBlank()) {
                    TextButton(
                        onClick = { onOpenPhoto(report.completionProofPhotoUrl) },
                        contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 7.dp, vertical = 4.dp)
                    ) {
                        Text("Foto akhir · petugas", color = if (isDark) Color(0xFF82C7B9) else RoleTeal, fontSize = 11.sp)
                    }
                }
            }
            if (
                !compactHistory &&
                (report.status == WasteFlow.ASSIGNED || report.status == WasteFlow.TRANSPORTED) &&
                report.locationEvidencePhotoUrl.isNullOrBlank() &&
                onSendLocationEvidence != null
            ) {
                TextButton(
                    onClick = { onSendLocationEvidence(report) },
                    modifier = Modifier.align(Alignment.Start),
                    enabled = !isSendingLocationEvidence,
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Icon(
                        Icons.Filled.PhotoCamera,
                        contentDescription = null,
                        tint = LoginAccent,
                        modifier = Modifier.size(17.dp)
                    )
                    Spacer(Modifier.width(6.dp))
                    Text(
                        if (isSendingLocationEvidence) "Mengirim foto…" else "Kirim foto di lokasi",
                        color = LoginAccent,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = InterFont
                    )
                }
            }
            if (compactHistory && onArchive != null) {
                TextButton(
                    onClick = { onArchive(report) },
                    modifier = Modifier.align(Alignment.End),
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 8.dp, vertical = 1.dp)
                ) {
                    Text("Arsipkan", color = LoginAccent, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                }
            }
            when {
                report.status == WasteFlow.ASSIGNED && onMarkTransported != null ->
                    Button(
                        onClick = { onMarkTransported(report) },
                        modifier = Modifier.fillMaxWidth().height(46.dp),
                        enabled = isActionEnabled && !isTransporting && !isSendingLocationEvidence,
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = LoginAccent)
                    ) {
                        Text(
                            if (isTransporting) "Memperbarui status…" else "Mulai pengangkutan",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                report.status == WasteFlow.TRANSPORTED && onComplete != null && waitingForAdmin ->
                    Text("Bukti sudah dikirim. Menunggu pemeriksaan Admin.", color = guidanceAccent, fontSize = 11.sp, fontWeight = FontWeight.Medium)

                report.status == WasteFlow.TRANSPORTED && onComplete != null ->
                    Button(
                        onClick = { onComplete(report) },
                        modifier = Modifier.fillMaxWidth().height(46.dp),
                        enabled = isActionEnabled && !isSendingLocationEvidence,
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = RoleGreenDark)
                    ) {
                        Text(
                            if (needsRevision) "Kirim ulang bukti TPA" else "Konfirmasi tiba di TPA",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
            }
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
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
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
            .background(RoleRed.copy(alpha = 0.08f))
            .padding(start = 12.dp, end = 7.dp, top = 8.dp, bottom = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Text("Belum tersinkron", color = AppText, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
            Text(message, color = AppTextMuted, fontSize = 11.sp, lineHeight = 15.sp)
        }
        TextButton(onClick = onRefresh) {
            Text("Coba lagi", color = LoginAccent, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
        }
    }
}

@Composable
private fun PetugasEmptyState(title: String, message: String) {
    val isDark = LocalSmartWasteAppearance.current.darkMode
    val accent = if (isDark) Color(0xFFB9D4BE) else Color(0xFF286947)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 2.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Icon(Icons.Filled.LocalShipping, contentDescription = null, tint = accent, modifier = Modifier.size(20.dp))
        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Text(title, color = AppText, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
            Text(message, color = AppTextMuted, fontSize = 11.sp, lineHeight = 15.sp)
        }
    }
}

@Composable
private fun StaffAccountFormDialog(
    onDismiss: () -> Unit,
    onSave: suspend (String, String, String, String, String) -> Unit
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
                Text("Role akun · Petugas", color = AppTextMuted, fontSize = 11.sp)
                FormField(name, { name = it }, "Nama lengkap")
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
                    if (!saving) scope.launch {
                        saving = true
                        saveError = ""
                        try {
                            onSave(
                                name.trim(),
                                username.trim(),
                                email.trim(),
                                password,
                                zone.trim()
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
                        password.length >= 8 &&
                        zone.isNotBlank() &&
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
private fun vehicleRepairAccent(status: String): Color = when (status) {
    "menunggu_persetujuan", "menunggu_pemeriksaan" -> RoleOrange
    "dalam_perbaikan", "disetujui" -> LoginAccent
    "perlu_perbaikan" -> RolePurple
    "selesai" -> RoleGreen
    else -> RoleRed
}

private fun vehicleRepairEventLabel(action: String): String = when (action) {
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
private fun VehicleRepairCard(
    repair: VehicleRepair,
    actions: @Composable (VehicleRepair) -> Unit
) {
    val timelineEvents = repair.timeline.filter { it.action != "dilaporkan" }
    var showFullTimeline by rememberSaveable(repair.id) { mutableStateOf(false) }
    AppCard(
        modifier = Modifier.fillMaxWidth(),
        containerColor = AppSurface,
        accentColor = vehicleRepairAccent(repair.status)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 14.dp, vertical = 13.dp),
            verticalArrangement = Arrangement.spacedBy(7.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
                    Text(repair.plate, color = AppText, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                    Text("Dilaporkan ${repair.reporterName.displayPersonName()} · ${repair.reportedAt}", color = AppTextMuted, fontSize = 10.sp)
                }
                Box(
                    modifier = Modifier.clip(RoundedCornerShape(50.dp))
                        .background(vehicleRepairAccent(repair.status).copy(alpha = 0.12f))
                        .padding(horizontal = 9.dp, vertical = 5.dp)
                ) {
                    Text(repair.statusLabel, color = vehicleRepairAccent(repair.status), fontSize = 10.sp, fontWeight = FontWeight.SemiBold)
                }
            }
            Text("Penyebab · ${repair.cause}", color = AppText, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
            Text(repair.details, color = AppTextMuted, fontSize = 11.sp, lineHeight = 16.sp, maxLines = 4, overflow = TextOverflow.Ellipsis)
            if (repair.activeTaskCount > 0) {
                Text(
                    "Ada ${repair.activeTaskCount} tugas aktif terkait armada ini; cek penugasannya sebelum kendaraan dipakai lagi.",
                    color = RoleRed,
                    fontSize = 10.sp,
                    lineHeight = 14.sp
                )
            }
            (if (showFullTimeline) timelineEvents else timelineEvents.takeLast(3)).forEach { event ->
                Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                    Text(
                        vehicleRepairEventLabel(event.action),
                        color = AppText,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        listOf(event.actorName.displayPersonName(), event.actorRole, event.createdAt)
                            .filter(String::isNotBlank)
                            .joinToString(" · "),
                        color = AppTextMuted,
                        fontSize = 9.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    if (event.note.isNotBlank()) {
                        Text(event.note, color = AppTextMuted, fontSize = 10.sp, lineHeight = 14.sp, maxLines = 3, overflow = TextOverflow.Ellipsis)
                    }
                }
            }
            if (timelineEvents.size > 3) {
                TextButton(onClick = { showFullTimeline = !showFullTimeline }) {
                    Text(
                        if (showFullTimeline) "Ringkas riwayat" else "Lihat semua ${timelineEvents.size} langkah",
                        color = LoginAccent,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
            if (repair.adminNote.isNotBlank() && repair.timeline.lastOrNull()?.note != repair.adminNote) {
                Text("Keputusan Admin · ${repair.adminNote}", color = RoleOrange, fontSize = 10.sp, lineHeight = 14.sp)
            }
            actions(repair)
        }
    }
}

@Composable
private fun VehicleDamageDialog(
    vehicle: WasteVehicle,
    onDismiss: () -> Unit,
    onSubmit: suspend (String, String) -> Unit
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
                Text("${vehicle.plate} · kendaraan akan dikunci dari penugasan baru sampai Admin memeriksa perbaikannya.", color = AppTextMuted, fontSize = 11.sp, lineHeight = 16.sp)
                OutlinedTextField(
                    value = cause,
                    onValueChange = { cause = it.take(180) },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Penyebab yang diketahui") },
                    placeholder = { Text("Contoh: ban bocor, rem, mesin") },
                    singleLine = true,
                    enabled = !sending
                )
                OutlinedTextField(
                    value = details,
                    onValueChange = { details = it.take(4000) },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Gejala dan kronologi") },
                    placeholder = { Text("Kapan terjadi, kondisi kendaraan, dan dampaknya") },
                    minLines = 3,
                    maxLines = 5,
                    enabled = !sending
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
                }
            ) { Text(if (sending) "Mengirim…" else "Kirim laporan", color = RoleRed) }
        },
        dismissButton = {
            TextButton(onClick = onDismiss, enabled = !sending) { Text("Batal", color = LoginMuted) }
        },
        containerColor = AppSurface
    )
}

@Composable
private fun VehicleRepairProcessDialog(
    repair: VehicleRepair,
    action: String,
    onDismiss: () -> Unit,
    onSubmit: suspend (String) -> Unit
) {
    val isInspection = action == "pemeriksaan"
    val isStarting = action == "mulai"
    var note by remember(repair.id, action) { mutableStateOf("") }
    var sending by remember(repair.id, action) { mutableStateOf(false) }
    var error by remember(repair.id, action) { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val title = when {
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
                                isInspection -> "Sebutkan tindakan, hasil uji, dan kondisi akhir kendaraan"
                                isStarting -> "Tuliskan langkah perbaikan pertama"
                                else -> "Sebutkan tindakan atau komponen yang ditangani"
                            }
                        )
                    },
                    minLines = 3,
                    maxLines = 5,
                    enabled = !sending
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
                }
            ) { Text(if (sending) "Menyimpan…" else if (isInspection) "Kirim ke Admin" else "Simpan", color = LoginAccent) }
        },
        dismissButton = {
            TextButton(onClick = onDismiss, enabled = !sending) { Text("Batal", color = LoginMuted) }
        },
        containerColor = AppSurface
    )
}

@Composable
private fun VehicleRepairReviewDialog(
    repair: VehicleRepair,
    action: String,
    onDismiss: () -> Unit,
    onSubmit: suspend (String) -> Unit
) {
    val requiresNote = action == "tolak" || action == "kembalikan"
    var note by remember(repair.id, action) { mutableStateOf("") }
    var sending by remember(repair.id, action) { mutableStateOf(false) }
    var error by remember(repair.id, action) { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val title = when (action) {
        "setujui" -> "Setujui perbaikan"
        "tolak" -> "Tolak laporan kerusakan"
        "kembalikan" -> "Minta perbaikan ulang"
        else -> "Sahkan kendaraan"
    }
    val confirmation = when (action) {
        "setujui" -> "Kendaraan tetap tidak tersedia sampai perbaikan selesai dan hasilnya disetujui Admin."
        "selesai" -> "Setelah pemeriksaan ini disetujui, kondisi kendaraan akan kembali Baik dan bisa dipilih untuk penugasan."
        "tolak" -> "Kendaraan akan tetap berstatus Rusak. Jelaskan alasan penolakan agar Petugas tahu tindak lanjutnya."
        else -> "Kendaraan tetap tidak tersedia. Jelaskan bagian yang masih perlu diperbaiki."
    }
    AlertDialog(
        onDismissRequest = { if (!sending) onDismiss() },
        title = { Text(title, color = LoginInk, fontWeight = FontWeight.Bold) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("${repair.plate} · ${repair.reporterName.displayPersonName()}", color = AppTextMuted, fontSize = 11.sp)
                Text(confirmation, color = AppText, fontSize = 12.sp, lineHeight = 17.sp)
                if (action == "tolak" || action == "kembalikan" || action == "selesai") {
                    OutlinedTextField(
                        value = note,
                        onValueChange = { note = it.take(800) },
                        modifier = Modifier.fillMaxWidth(),
                        label = {
                            Text(if (action == "selesai") "Catatan pemeriksaan (opsional)" else "Alasan keputusan")
                        },
                        placeholder = { Text("Tulis alasan atau hasil pemeriksaan") },
                        minLines = 2,
                        maxLines = 4,
                        enabled = !sending
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
                }
            ) { Text(if (sending) "Menyimpan…" else title, color = if (action == "tolak") RoleRed else LoginAccent) }
        },
        dismissButton = {
            TextButton(onClick = onDismiss, enabled = !sending) { Text("Batal", color = LoginMuted) }
        },
        containerColor = AppSurface
    )
}

@Composable
private fun VehicleFormDialog(
    vehicle: WasteVehicle?,
    onDismiss: () -> Unit,
    onSave: suspend (String, Double, String) -> Unit
) {
    var plate by remember(vehicle?.id) { mutableStateOf(vehicle?.plate.orEmpty()) }
    var capacity by remember(vehicle?.id) {
        mutableStateOf(
            vehicle?.capacityKg?.toString().orEmpty()
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
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            ScrollableDialogContent {
                FormField(plate, { plate = it }, "Nomor polisi")
                FormField(capacity, { capacity = it }, "Kapasitas (kg)")
                Text(
                    if (vehicle == null) "Kendaraan baru tercatat Baik. Kerusakan dilaporkan melalui alur persetujuan Admin."
                    else "Kondisi saat ini · ${vehicle.condition}. Perubahan kondisi mengikuti proses perbaikan Admin.",
                    color = AppTextMuted,
                    fontSize = 11.sp,
                    lineHeight = 16.sp
                )
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
            TextButton(
                onClick = {
                    if (!saving) scope.launch {
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
                enabled = plate.isNotBlank() && proposedCapacity > 0 && canFitCurrentLoad && !saving
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
        onDismissRequest = { if (!saving) onDismiss() },
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
    val textColor = when {
        IsNightAdmin -> color
        color == RoleOrange -> Color(0xFF80500E)
        color == RolePurple -> Color(0xFF46552E)
        color == RoleTeal -> Color(0xFF0D604F)
        color == RoleGreen -> Color(0xFF0D613D)
        color == RoleGreenDark -> Color(0xFF12472F)
        color == RoleNavy -> Color(0xFF2A4033)
        color == RoleRed -> Color(0xFF8C3041)
        else -> AppText
    }
    Box(
        modifier = Modifier.clip(RoundedCornerShape(50.dp)).background(color.copy(alpha = 0.12f))
            .padding(horizontal = 9.dp, vertical = 6.dp)
    ) {
        Text(
            WasteFlow.label(status),
            color = textColor,
            fontFamily = InterFont,
            fontSize = 12.sp,
            lineHeight = 15.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
private fun reportAccent(status: String): Color = when (status) {
    WasteFlow.WAITING -> RoleOrange
    WasteFlow.ASSIGNED -> RolePurple
    WasteFlow.TRANSPORTED -> RoleTeal
    WasteFlow.PROCESSING -> RoleNavy
    WasteFlow.DONE -> RoleGreen
    else -> RoleRed
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
            .background(RoleAccentSurface)
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
            .background(RoleAccentSurface)
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
        containerColor = RoleRed.copy(alpha = 0.08f),
        borderColor = RoleRed.copy(alpha = 0.24f),
        accentColor = RoleRed
    ) {
        Text(
            message,
            modifier = Modifier.padding(12.dp),
            color = RoleRed,
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

@Composable
private fun MonthlyReportsChart(months: List<MonthlyStat>) {
    val chartMax = months.maxOfOrNull { it.total }?.coerceAtLeast(1) ?: 1
    val (axisMax, axisStep) = monthlyAxisScale(chartMax)
    val highlightedIndex = months.lastIndex
    val highlighted = months[highlightedIndex]
    val previous = months.getOrNull(highlightedIndex - 1)
    val comparisonPercent = previous?.takeIf { it.total > 0 }?.let {
        (highlighted.total - it.total) * 100.0 / it.total
    }
    val hasMonthlyReports = months.any { it.total > 0 }
    val localeId = java.util.Locale.forLanguageTag("id-ID")
    val changeLabel = comparisonPercent?.let { String.format(localeId, "%+.1f%%", it) }
    val trendLabel = when {
        !hasMonthlyReports -> "Belum ada data"
        comparisonPercent == null && previous == null -> "—"
        comparisonPercent == null && highlighted.total > 0 -> "Baru"
        comparisonPercent == null -> "Baru"
        comparisonPercent > 0 -> "Naik"
        comparisonPercent < 0 -> "Turun"
        else -> "Tetap"
    }
    val comparisonDetail = when {
        !hasMonthlyReports -> "Enam bulan terakhir"
        previous == null -> "Belum ada pembanding"
        previous.total == 0 && highlighted.total > 0 -> "Bulan lalu 0 laporan"
        previous.total == 0 -> "Bulan lalu 0 laporan"
        else -> "Dibanding ${monthTitle(previous.period)}"
    }
    val night = IsNightAdmin
    val mutedBar = if (night) Color(0xFF4F6F5E) else Color(0xFFA7C9B6)
    val activeBar = if (night) Color(0xFF398A65) else Color(0xFF087D50)
    val trendColor = when {
        !hasMonthlyReports -> AppTextMuted
        comparisonPercent != null && comparisonPercent < 0 -> RoleRed
        comparisonPercent == null && previous?.total == 0 && highlighted.total == 0 -> AppTextMuted
        else -> activeBar
    }
    val chartHeight = 142.dp
    val plotTop = 36.dp
    val plotHeight = chartHeight - plotTop
    val gridColor = AppTextMuted.copy(alpha = if (night) 0.28f else 0.20f)

    AppCard(
        modifier = Modifier.fillMaxWidth(),
        containerColor = AppSurface
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 14.dp, vertical = 14.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                "Laporan masuk",
                color = AppTextMuted,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        monthTitle(highlighted.period),
                        color = AppText,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        "${highlighted.total} laporan",
                        color = AppTextMuted,
                        fontSize = 12.sp
                    )
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        trendLabel,
                        color = trendColor,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        comparisonDetail,
                        color = AppTextMuted,
                        fontSize = 11.sp,
                        textAlign = TextAlign.End
                    )
                }
            }
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.Top) {
                Column(modifier = Modifier.width(34.dp).height(chartHeight)) {
                    Spacer(modifier = Modifier.height(plotTop - 6.dp))
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        (5 downTo 0).forEach { tick ->
                            Text(
                                (axisStep * tick).toChartAxisLabel(),
                                color = AppTextMuted,
                                fontSize = 11.sp,
                                lineHeight = 13.sp,
                                maxLines = 1
                            )
                        }
                    }
                }
                Box(
                    modifier = Modifier.weight(1f).height(chartHeight)
                ) {
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        val top = plotTop.toPx()
                        val interval = plotHeight.toPx() / 5f
                        (0..5).forEach { index ->
                            val y = top + interval * index
                            drawLine(
                                color = gridColor,
                                start = Offset(0f, y),
                                end = Offset(size.width, y),
                                strokeWidth = 1.dp.toPx(),
                                pathEffect = PathEffect.dashPathEffect(floatArrayOf(5.dp.toPx(), 5.dp.toPx()))
                            )
                        }
                    }
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        months.forEachIndexed { index, month ->
                            val ratio = (month.total.toFloat() / axisMax).coerceIn(0f, 1f)
                            val barHeight = plotHeight * ratio
                            val isHighlighted = index == highlightedIndex
                            val barColor = if (isHighlighted) activeBar else mutedBar
                            Box(
                                modifier = Modifier.weight(1f).fillMaxHeight()
                            ) {
                                if (isHighlighted && changeLabel != null) {
                                    Column(
                                        modifier = Modifier
                                            .align(Alignment.TopCenter)
                                            .offset(y = (chartHeight - barHeight - 34.dp).coerceAtLeast(0.dp)),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Text(
                                            changeLabel,
                                            color = Color.White,
                                            fontSize = 10.sp,
                                            lineHeight = 12.sp,
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier
                                                .clip(CircleShape)
                                                .background(activeBar)
                                                .padding(horizontal = 8.dp, vertical = 4.dp)
                                        )
                                        Spacer(modifier = Modifier.height(2.dp))
                                        Box(
                                            modifier = Modifier
                                                .size(11.dp)
                                                .clip(CircleShape)
                                                .background(activeBar)
                                                .border(2.dp, AppSurface, CircleShape)
                                        )
                                    }
                                }
                                if (month.total > 0 || isHighlighted) {
                                    Box(
                                        modifier = Modifier
                                            .align(Alignment.BottomCenter)
                                            .widthIn(max = 40.dp)
                                            .fillMaxWidth(0.64f)
                                            .height((if (barHeight > 0.dp) barHeight else 3.dp).coerceAtLeast(3.dp))
                                            .clip(CircleShape)
                                            .background(barColor)
                                            .drawWithContent {
                                                drawContent()
                                                val spacing = 10.dp.toPx()
                                                val stroke = 1.dp.toPx()
                                                var startX = -size.height
                                                while (startX < size.width) {
                                                    drawLine(
                                                        color = Color.White.copy(alpha = if (isHighlighted) 0.20f else 0.30f),
                                                        start = Offset(startX, size.height),
                                                        end = Offset(startX + size.height, 0f),
                                                        strokeWidth = stroke
                                                    )
                                                    startX += spacing
                                                }
                                            }
                                    )
                                }
                            }
                        }
                    }
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Spacer(modifier = Modifier.width(34.dp))
                months.forEach { month ->
                    Text(
                        shortMonth(month.period).uppercase(java.util.Locale.forLanguageTag("id-ID")),
                        modifier = Modifier.weight(1f),
                        color = AppTextMuted,
                        fontSize = 11.sp,
                        textAlign = TextAlign.Center,
                        maxLines = 1
                    )
                }
            }
            if (!hasMonthlyReports) {
                Text(
                    "Belum ada laporan pada enam bulan penuh ini.",
                    color = AppTextMuted,
                    fontSize = 12.sp
                )
            }
        }
    }
}

private fun monthlyAxisScale(value: Int): Pair<Int, Int> {
    val rawStep = ceil(value.coerceAtLeast(1) / 5.0).toInt()
    val magnitude = 10.0.pow(floor(log10(rawStep.toDouble()))).toInt().coerceAtLeast(1)
    val normalized = rawStep.toDouble() / magnitude
    val step = when {
        normalized <= 1.0 -> magnitude
        normalized <= 2.0 -> 2 * magnitude
        normalized <= 5.0 -> 5 * magnitude
        else -> 10 * magnitude
    }
    return step * 5 to step
}

private fun Int.toChartAxisLabel(): String = when {
    this >= 1_000 && this % 1_000 == 0 -> "${this / 1_000}k"
    this >= 1_000 -> String.format(java.util.Locale.US, "%.1fk", this / 1_000.0)
    else -> toString()
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

private fun monthTitle(period: String): String =
    "${shortMonth(period)} ${period.substringBefore('-', period)}"
