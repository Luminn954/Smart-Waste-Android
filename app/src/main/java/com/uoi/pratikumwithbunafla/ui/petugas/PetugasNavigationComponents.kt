package com.uoi.pratikumwithbunafla.ui.petugas

import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.SwitchAccount
import androidx.compose.material.icons.rounded.History
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.NightsStay
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.TaskAlt
import androidx.compose.material.icons.rounded.WbSunny
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.uoi.pratikumwithbunafla.R
import com.uoi.pratikumwithbunafla.data.model.*
import com.uoi.pratikumwithbunafla.domain.WasteFlow
import com.uoi.pratikumwithbunafla.ui.admin.*
import com.uoi.pratikumwithbunafla.ui.components.*
import com.uoi.pratikumwithbunafla.ui.theme.AppBackground
import com.uoi.pratikumwithbunafla.ui.theme.AppBorder
import com.uoi.pratikumwithbunafla.ui.theme.AppCardSurface
import com.uoi.pratikumwithbunafla.ui.theme.AppSurface
import com.uoi.pratikumwithbunafla.ui.theme.AppSurfaceMuted
import com.uoi.pratikumwithbunafla.ui.theme.AppText
import com.uoi.pratikumwithbunafla.ui.theme.AppTextMuted
import com.uoi.pratikumwithbunafla.ui.theme.InterFont
import com.uoi.pratikumwithbunafla.ui.theme.LocalSmartWasteAppearance

@Composable
internal fun PetugasThemeBackdrop(modifier: Modifier = Modifier) {
    val dark = LocalSmartWasteAppearance.current.darkMode
    val terrainColor = if (dark) Color(0xFF91AAA0) else Color(0xFF6D9480)
    val routeColor = if (dark) Color(0xFF8AAEAA) else Color(0xFF6D8F98)
    val warmColor = if (dark) Color(0xFFCDAE75) else Color(0xFF927044)
    val background by
        animateColorAsState(
            targetValue = AppBackground,
            animationSpec = tween(durationMillis = 820, easing = FastOutSlowInEasing),
            label = "petugas-page-background",
        )
    val routeProgress by
        rememberInfiniteTransition(label = "petugas-backdrop-route")
            .animateFloat(
                initialValue = 0f,
                targetValue = 1f,
                animationSpec =
                    infiniteRepeatable(
                        animation = tween(durationMillis = 8_400, easing = FastOutSlowInEasing),
                        repeatMode = RepeatMode.Reverse,
                    ),
                label = "petugas-backdrop-marker",
            )

    Box(modifier.background(background)) {
        Canvas(Modifier.fillMaxSize()) {
            val width = size.width
            val height = size.height

            // A very light field wash gives the work area a little depth without
            // competing with the task cards or the photo-led header.
            val upperWash =
                Path().apply {
                    moveTo(0f, 0f)
                    lineTo(width * 0.64f, 0f)
                    cubicTo(
                        width * 0.48f,
                        height * 0.07f,
                        width * 0.70f,
                        height * 0.14f,
                        width * 0.48f,
                        height * 0.19f,
                    )
                    cubicTo(
                        width * 0.28f,
                        height * 0.24f,
                        width * 0.16f,
                        height * 0.13f,
                        0f,
                        height * 0.20f,
                    )
                    close()
                }
            drawPath(
                path = upperWash,
                color = terrainColor.copy(alpha = if (dark) 0.075f else 0.045f),
            )

            val lowerWash =
                Path().apply {
                    moveTo(0f, height * 0.79f)
                    cubicTo(
                        width * 0.22f,
                        height * 0.73f,
                        width * 0.34f,
                        height * 0.89f,
                        width * 0.56f,
                        height * 0.84f,
                    )
                    cubicTo(
                        width * 0.76f,
                        height * 0.80f,
                        width * 0.84f,
                        height * 0.93f,
                        width,
                        height * 0.86f,
                    )
                    lineTo(width, height)
                    lineTo(0f, height)
                    close()
                }
            drawPath(
                path = lowerWash,
                color = terrainColor.copy(alpha = if (dark) 0.065f else 0.04f),
            )

            // Two edge-only contour lines hint at a route map while leaving the
            // reading area calm and open.
            val contourAlpha = if (dark) 0.075f else 0.055f
            val outerContour =
                Path().apply {
                    moveTo(width * 0.88f, height * 0.27f)
                    cubicTo(
                        width * 0.68f,
                        height * 0.40f,
                        width * 1.04f,
                        height * 0.50f,
                        width * 0.83f,
                        height * 0.63f,
                    )
                    cubicTo(
                        width * 0.69f,
                        height * 0.73f,
                        width * 1.02f,
                        height * 0.81f,
                        width * 0.84f,
                        height * 0.94f,
                    )
                }
            drawPath(
                path = outerContour,
                color = routeColor.copy(alpha = contourAlpha),
                style = Stroke(width = 1.15.dp.toPx(), cap = StrokeCap.Round),
            )
            val innerContour =
                Path().apply {
                    moveTo(width * 0.94f, height * 0.29f)
                    cubicTo(
                        width * 0.79f,
                        height * 0.42f,
                        width * 1.08f,
                        height * 0.52f,
                        width * 0.90f,
                        height * 0.65f,
                    )
                    cubicTo(
                        width * 0.77f,
                        height * 0.75f,
                        width * 1.05f,
                        height * 0.83f,
                        width * 0.91f,
                        height * 0.95f,
                    )
                }
            drawPath(
                path = innerContour,
                color = routeColor.copy(alpha = contourAlpha * 0.72f),
                style = Stroke(width = 1.dp.toPx(), cap = StrokeCap.Round),
            )

            val start = Offset(size.width * 0.70f, size.height * 0.035f)
            val firstControl = Offset(size.width * 0.96f, size.height * 0.015f)
            val secondControl = Offset(size.width * 0.56f, size.height * 0.105f)
            val end = Offset(size.width * 0.88f, size.height * 0.17f)
            val route =
                Path().apply {
                    moveTo(start.x, start.y)
                    cubicTo(
                        firstControl.x,
                        firstControl.y,
                        secondControl.x,
                        secondControl.y,
                        end.x,
                        end.y,
                    )
                }
            drawPath(
                path = route,
                color = routeColor.copy(alpha = if (dark) 0.10f else 0.085f),
                style = Stroke(width = 1.25.dp.toPx(), cap = StrokeCap.Round),
            )

            val t = routeProgress
            val inverse = 1f - t
            val marker =
                Offset(
                    x =
                        inverse * inverse * inverse * start.x +
                            3f * inverse * inverse * t * firstControl.x +
                            3f * inverse * t * t * secondControl.x +
                            t * t * t * end.x,
                    y =
                        inverse * inverse * inverse * start.y +
                            3f * inverse * inverse * t * firstControl.y +
                            3f * inverse * t * t * secondControl.y +
                            t * t * t * end.y,
                )
            drawCircle(
                color = routeColor.copy(alpha = 0.09f),
                radius = 7.dp.toPx(),
                center = marker,
            )
            drawCircle(
                color = warmColor.copy(alpha = if (dark) 0.32f else 0.25f),
                radius = 2.4.dp.toPx(),
                center = marker,
            )
        }
    }
}

@Composable
internal fun PetugasSectionHeading(
    eyebrow: String,
    title: String,
    subtitle: String?,
    modifier: Modifier = Modifier,
    icon: ImageVector = Icons.Filled.LocalShipping,
    photoName: String? = null,
    photoUrl: String? = null,
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(5.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(9.dp),
        ) {
            Box(
                modifier = Modifier.size(34.dp),
            ) {
                if (!photoUrl.isNullOrBlank()) {
                    UserAvatar(photoName.orEmpty(), Modifier.fillMaxSize(), photoUrl)
                } else {
                    Box(
                        modifier =
                            Modifier.fillMaxSize()
                                .clip(RoundedCornerShape(11.dp))
                                .background(PetugasAccent.copy(alpha = 0.12f)),
                        contentAlignment = Alignment.Center,
                    ) {
                        Icon(
                            icon,
                            contentDescription = null,
                            tint = PetugasAccent,
                            modifier = Modifier.size(17.dp),
                        )
                    }
                }
            }
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(2.dp),
            ) {
                Text(
                    eyebrow,
                    color = PetugasAccent,
                    fontSize = 11.sp,
                    lineHeight = 14.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.65.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    title,
                    color = AppText,
                    fontSize = if (LocalConfiguration.current.screenWidthDp < 360) 17.sp else 19.sp,
                    lineHeight = 23.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
        if (!subtitle.isNullOrBlank()) {
            Text(
                subtitle,
                color = AppTextMuted,
                fontSize = 12.sp,
                lineHeight = 18.sp,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

@Composable
internal fun PetugasActionTile(
    label: String,
    count: Int,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    val accent = PetugasAccent
    val countLabel = if (count > 99) "99+" else count.toString()
    Column(
        modifier =
            modifier
                .clip(RoundedCornerShape(15.dp))
                .clickable(role = Role.Button, onClick = onClick)
                .padding(horizontal = 2.dp, vertical = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(5.dp),
    ) {
        Box(modifier = Modifier.size(38.dp), contentAlignment = Alignment.Center) {
            Box(
                modifier = Modifier.fillMaxSize().clip(CircleShape).background(AppSurfaceMuted),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    icon,
                    contentDescription = null,
                    tint = accent,
                    modifier = Modifier.size(19.dp),
                )
            }
            Box(
                modifier =
                    Modifier.align(Alignment.TopEnd)
                        .size(21.dp)
                        .clip(CircleShape)
                        .background(accent),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    countLabel,
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 9.sp,
                    lineHeight = 11.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                )
            }
        }
        Text(
            label,
            color = AppText,
            fontSize = 10.sp,
            lineHeight = 13.sp,
            fontWeight = FontWeight.SemiBold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Composable
internal fun PetugasTopBar(
    account: UserAccount,
    onOpenProfile: () -> Unit,
    onOpenNotifications: () -> Unit,
    onLogout: () -> Unit,
    homeHeaderMode: Boolean = false,
) {
    var profileMenuExpanded by remember(account.username) { mutableStateOf(false) }
    val appearance = LocalSmartWasteAppearance.current
    val compact = LocalConfiguration.current.screenWidthDp < 350
    val foreground = AppText
    val subdued = AppTextMuted
    val themeTint by
        animateColorAsState(
            targetValue = if (appearance.darkMode) Color(0xFFB8C8BA) else PetugasAccent,
            animationSpec = tween(380, easing = FastOutSlowInEasing),
            label = "petugas-theme-tint",
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
                        .clip(CircleShape)
                        .background(
                            if (appearance.darkMode) Color(0xFF304338) else Color(0xFFE4EBE1),
                        ),
                contentAlignment = Alignment.Center,
            ) {
                Image(
                    painter = painterResource(R.drawable.launcher_recycling_mark),
                    contentDescription = "Logo Smart Waste",
                    modifier = Modifier.size(if (compact) 22.dp else 25.dp),
                )
            }
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(1.dp),
            ) {
                Text(
                    "SMART WASTE",
                    color = subdued,
                    fontSize = 9.sp,
                    lineHeight = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.85.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    if (homeHeaderMode)
                        "Halo, ${account.name.displayPersonName().ifBlank { "Petugas" }}"
                    else account.name.displayPersonName().ifBlank { "Petugas" },
                    color = foreground,
                    fontSize =
                        if (homeHeaderMode) {
                            if (compact) 14.sp else 15.sp
                        } else if (compact) 13.sp else 14.sp,
                    lineHeight = if (homeHeaderMode) 20.sp else 18.sp,
                    fontWeight = if (homeHeaderMode) FontWeight.SemiBold else FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
        if (homeHeaderMode) {
            PetugasThemeToggleButton(appearance.onToggleTheme, appearance.darkMode, themeTint)
            PetugasNotificationButton(onOpenNotifications, prominent = true)
            IconButton(onClick = onLogout, modifier = Modifier.size(36.dp)) {
                Box(
                    modifier =
                        Modifier.size(30.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(AppSurfaceMuted),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        Icons.Filled.SwitchAccount,
                        contentDescription = "Ganti akun",
                        tint = PetugasAccent,
                        modifier = Modifier.size(19.dp),
                    )
                }
            }
        } else {
            PetugasNotificationButton(onOpenNotifications)
            Box {
                IconButton(
                    onClick = { profileMenuExpanded = true },
                    modifier = Modifier.size(38.dp),
                ) {
                    UserAvatar(
                        account.name,
                        modifier = Modifier.size(if (compact) 29.dp else 32.dp),
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
                        modifier =
                            Modifier.width(220.dp).padding(horizontal = 14.dp, vertical = 10.dp),
                        verticalArrangement = Arrangement.spacedBy(3.dp),
                    ) {
                        Text("Petugas aktif", color = AppTextMuted, fontSize = 11.sp)
                        Text(
                            account.name.displayPersonName(),
                            color = AppText,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                        )
                        Text("@${account.username}", color = AppTextMuted, fontSize = 11.sp)
                    }
                    DropdownMenuItem(
                        text = { Text("Profil petugas") },
                        leadingIcon = { Icon(Icons.Rounded.Person, contentDescription = null) },
                        onClick = {
                            profileMenuExpanded = false
                            onOpenProfile()
                        },
                    )
                    DropdownMenuItem(
                        text = { Text("Ganti akun") },
                        leadingIcon = {
                            Icon(Icons.Filled.SwitchAccount, contentDescription = null)
                        },
                        onClick = {
                            profileMenuExpanded = false
                            onLogout()
                        },
                    )
                }
            }
            PetugasThemeToggleButton(appearance.onToggleTheme, appearance.darkMode, themeTint)
        }
    }
}

@Composable
internal fun PetugasNotificationButton(onClick: () -> Unit, prominent: Boolean = false) {
    IconButton(onClick = onClick, modifier = Modifier.size(if (prominent) 40.dp else 36.dp)) {
        Box(
            modifier =
                Modifier.size(if (prominent) 36.dp else 30.dp)
                    .clip(if (prominent) CircleShape else RoundedCornerShape(10.dp))
                    .background(AppSurfaceMuted),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                Icons.Filled.Notifications,
                contentDescription = "Buka notifikasi",
                tint = PetugasAccent,
                modifier = Modifier.size(20.dp),
            )
        }
    }
}

@Composable
internal fun PetugasThemeToggleButton(onToggle: () -> Unit, darkMode: Boolean, tint: Color) {
    IconButton(onClick = onToggle, modifier = Modifier.size(36.dp)) {
        Box(
            modifier =
                Modifier.size(30.dp).clip(RoundedCornerShape(10.dp)).background(AppSurfaceMuted),
            contentAlignment = Alignment.Center,
        ) {
            Crossfade(
                targetState = darkMode,
                animationSpec = tween(340),
                label = "petugas-theme-icon",
            ) { dark ->
                Icon(
                    imageVector = if (dark) Icons.Rounded.WbSunny else Icons.Rounded.NightsStay,
                    contentDescription =
                        if (dark) "Aktifkan mode terang" else "Aktifkan mode gelap",
                    tint = tint,
                    modifier = Modifier.size(17.dp),
                )
            }
        }
    }
}

@Composable
internal fun PetugasJourneyHero(
    account: UserAccount,
    tasks: List<WasteRecord>,
    incomingReportCount: Int,
    searchQuery: String,
    searchSuggestions: List<String>,
    onSearchQueryChange: (String) -> Unit,
    onSubmitSearch: () -> Unit,
    onOpenTasks: () -> Unit,
    onOpenNotifications: () -> Unit,
    onOpenProfile: () -> Unit,
    onLogout: () -> Unit,
) {
    val assigned = tasks.count { it.status == WasteFlow.ASSIGNED }
    val transported = tasks.count { it.status == WasteFlow.TRANSPORTED }
    val waitingAdmin = tasks.count {
        it.status == WasteFlow.TRANSPORTED &&
            it.completionStatus.equals("menunggu", ignoreCase = true)
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
    val active = assigned + transported
    val needsAttention = waitingAdmin + needsRevision
    val total = tasks.count {
        it.status == WasteFlow.ASSIGNED ||
            it.status == WasteFlow.TRANSPORTED ||
            it.status == WasteFlow.DONE
    }
    val progressFraction = if (total > 0) (completed.toFloat() / total).coerceIn(0f, 1f) else 0f
    val actionLabel =
        when {
            active > 0 -> "Lanjutkan tugas"
            incomingReportCount > 0 -> "Tinjau laporan"
            else -> "Buka antrean"
        }
    val isCompact = LocalConfiguration.current.screenWidthDp < 360
    val isDarkMode = LocalSmartWasteAppearance.current.darkMode

    Column(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier =
                Modifier.fillMaxWidth()
                    .windowInsetsPadding(WindowInsets.statusBars)
                    .padding(horizontal = 16.dp, vertical = 8.dp),
        ) {
            PetugasTopBar(
                account = account,
                onOpenProfile = onOpenProfile,
                onOpenNotifications = onOpenNotifications,
                onLogout = onLogout,
                homeHeaderMode = true,
            )
        }

        val shiftCardShape = RoundedCornerShape(24.dp)
        Column(
            modifier =
                Modifier.fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .clip(shiftCardShape)
                    .background(AppCardSurface)
                    .border(1.dp, AppBorder.copy(alpha = 0.72f), shiftCardShape),
        ) {
            Box(
                modifier =
                    Modifier.fillMaxWidth()
                        .height(if (isCompact) 196.dp else 208.dp)
                        .clip(RoundedCornerShape(topStart = 23.dp, topEnd = 23.dp))
                        .background(AppSurfaceMuted),
            ) {
                Image(
                    painter = painterResource(R.drawable.petugas_header_background),
                    contentDescription = null,
                    modifier = Modifier.matchParentSize(),
                    contentScale = ContentScale.Crop,
                    alignment = BiasAlignment(horizontalBias = 0f, verticalBias = -0.16f),
                )
                Box(
                    Modifier.matchParentSize()
                        .background(
                            if (isDarkMode) Color(0xFF14241C).copy(alpha = 0.36f)
                            else Color(0xFFEAF1EA).copy(alpha = 0.24f),
                        ),
                )
                SmartWasteSearchField(
                    value = searchQuery,
                    onValueChange = onSearchQueryChange,
                    placeholder = "Cari tugas, lokasi, atau TPA",
                    suggestionSource = searchSuggestions,
                    modifier =
                        Modifier.align(Alignment.BottomCenter)
                            .fillMaxWidth()
                            .padding(
                                horizontal = if (isCompact) 14.dp else 16.dp,
                                vertical = 12.dp,
                            ),
                    onSearch = onSubmitSearch,
                )
            }

            Column(
                modifier =
                    Modifier.fillMaxWidth()
                        .padding(horizontal = if (isCompact) 14.dp else 16.dp, vertical = 13.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    PetugasShiftMetric(
                        assigned.toString(),
                        "Siap angkut",
                        Modifier.weight(1f),
                        PetugasAccent,
                    )
                    Box(
                        Modifier.width(1.dp)
                            .height(40.dp)
                            .background(AppBorder.copy(alpha = 0.72f)),
                    )
                    PetugasShiftMetric(
                        inRoute.toString(),
                        "Dalam rute",
                        Modifier.weight(1f),
                        PetugasAccentWarm,
                    )
                    Box(
                        Modifier.width(1.dp)
                            .height(40.dp)
                            .background(AppBorder.copy(alpha = 0.72f)),
                    )
                    PetugasShiftMetric(
                        needsAttention.toString(),
                        "Perlu dicek",
                        Modifier.weight(1f),
                        if (needsAttention > 0) PetugasAccentAlert else AppText,
                    )
                }

                Box(Modifier.fillMaxWidth().height(1.dp).background(AppBorder.copy(alpha = 0.56f)))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(6.dp),
                    ) {
                        Text(
                            if (total > 0)
                                "$completed dari $total selesai · ${completed * 100 / total}%"
                            else "Belum ada pekerjaan selesai",
                            color = AppTextMuted,
                            fontSize = 11.sp,
                            lineHeight = 14.sp,
                            fontWeight = FontWeight.Medium,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                        Box(
                            modifier =
                                Modifier.fillMaxWidth()
                                    .height(5.dp)
                                    .clip(CircleShape)
                                    .background(AppBorder.copy(alpha = 0.65f)),
                        ) {
                            if (progressFraction > 0f) {
                                Box(
                                    modifier =
                                        Modifier.fillMaxWidth(progressFraction)
                                            .fillMaxHeight()
                                            .clip(CircleShape)
                                            .background(PetugasAccent),
                                )
                            }
                        }
                    }
                    Row(
                        modifier =
                            Modifier.clip(RoundedCornerShape(14.dp))
                                .background(PetugasAccent)
                                .clickable(role = Role.Button, onClick = onOpenTasks)
                                .padding(horizontal = 12.dp, vertical = 11.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                    ) {
                        Text(
                            actionLabel,
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontSize = 11.sp,
                            lineHeight = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier.size(15.dp),
                        )
                    }
                }
            }
        }
    }
}

@Composable
internal fun PetugasShiftMetric(
    value: String,
    label: String,
    modifier: Modifier = Modifier,
    valueColor: Color,
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(3.dp)) {
        Text(
            value,
            color = valueColor,
            fontSize = 18.sp,
            lineHeight = 22.sp,
            fontWeight = FontWeight.SemiBold,
        )
        Text(
            label,
            color = AppTextMuted,
            fontSize = 11.sp,
            lineHeight = 14.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

internal data class PetugasFilterOption(
    val key: String,
    val label: String,
    val count: Int,
)

@Composable
internal fun PetugasFilterGrid(
    choices: List<PetugasFilterOption>,
    selectedKey: String,
    onSelect: (String) -> Unit,
    title: String? = null,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        if (!title.isNullOrBlank()) {
            Text(
                text = title,
                color = AppTextMuted,
                fontSize = 12.sp,
                lineHeight = 16.sp,
                fontWeight = FontWeight.SemiBold,
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            choices.forEach { choice ->
                PetugasFilterChoice(
                    choice = choice,
                    selected = choice.key == selectedKey,
                    onSelect = onSelect,
                )
            }
        }
    }
}

@Composable
internal fun PetugasFilterChoice(
    choice: PetugasFilterOption,
    selected: Boolean,
    onSelect: (String) -> Unit,
) {
    val shape = RoundedCornerShape(15.dp)
    val containerColor by
        animateColorAsState(
            targetValue = if (selected) PetugasAccent else AppSurfaceMuted,
            animationSpec = tween(280, easing = FastOutSlowInEasing),
            label = "petugas-filter-${choice.key}-surface",
        )
    val titleColor by
        animateColorAsState(
            targetValue = if (selected) MaterialTheme.colorScheme.onPrimary else AppText,
            animationSpec = tween(240, easing = FastOutSlowInEasing),
            label = "petugas-filter-${choice.key}-title",
        )

    Box(
        modifier =
            Modifier.heightIn(min = 42.dp)
                .widthIn(min = 82.dp)
                .clip(shape)
                .background(containerColor)
                .selectable(
                    selected = selected,
                    role = Role.Tab,
                    onClick = { onSelect(choice.key) },
                )
                .padding(horizontal = 11.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "${choice.label} · ${choice.count}",
            color = titleColor,
            fontFamily = InterFont,
            fontSize = 11.sp,
            lineHeight = 14.sp,
            fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Medium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Composable
internal fun PetugasHistoryFilterBar(
    showingArchive: Boolean,
    historyCount: Int,
    archiveCount: Int,
    statusChoices: List<PetugasFilterOption>,
    selectedStatus: String,
    onSelectArchive: (Boolean) -> Unit,
    onSelectStatus: (String) -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        PetugasFilterChoice(
            choice = PetugasFilterOption("riwayat", "Riwayat", historyCount),
            selected = !showingArchive,
            onSelect = { onSelectArchive(false) },
        )
        PetugasFilterChoice(
            choice = PetugasFilterOption("arsip", "Arsip", archiveCount),
            selected = showingArchive,
            onSelect = { onSelectArchive(true) },
        )
        Box(
            modifier =
                Modifier.padding(horizontal = 1.dp)
                    .size(width = 1.dp, height = 24.dp)
                    .clip(CircleShape)
                    .background(AppBorder),
        )
        statusChoices.forEach { choice ->
            PetugasFilterChoice(
                choice = choice,
                selected = choice.key == selectedStatus,
                onSelect = onSelectStatus,
            )
        }
    }
}

@Composable
internal fun PetugasNavigationBar(selectedSection: String, onSectionSelected: (String) -> Unit) {
    val destinations =
        listOf(
            RoleNavigationDestination("ringkasan", "Beranda", Icons.Rounded.Home),
            RoleNavigationDestination("tugas", "Tugas", Icons.Rounded.TaskAlt),
            RoleNavigationDestination("riwayat", "Riwayat", Icons.Rounded.History),
            RoleNavigationDestination("profil", "Profil", Icons.Rounded.Person),
        )
    RoleNavigationBar(
        selectedRoute = selectedSection,
        destinations = destinations,
        containerColor = AppSurface,
        selectedColor = PetugasAccent,
        selectedContentColor = MaterialTheme.colorScheme.onPrimary,
        inactiveContentColor = AppTextMuted,
        onDestinationSelected = onSectionSelected,
    )
}
