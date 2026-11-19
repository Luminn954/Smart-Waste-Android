package com.uoi.pratikumwithbunafla.ui.auth

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.uoi.pratikumwithbunafla.data.model.UserRole
import com.uoi.pratikumwithbunafla.ui.components.*
import com.uoi.pratikumwithbunafla.ui.theme.InterFont
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

internal val LoginFont = InterFont

internal fun roleUsernamePreferenceKey(role: UserRole): String =
    when (role) {
        UserRole.ADMIN -> "admin_username"
        UserRole.PETUGAS -> "petugas_username"
    }

internal data class LoginPalette(
    val background: Color,
    val backgroundTop: Color,
    val backgroundMiddle: Color,
    val backgroundBottom: Color,
    val surface: Color,
    val field: Color,
    val text: Color,
    val muted: Color,
    val accent: Color,
    val accentDark: Color,
    val accentSoft: Color,
    val border: Color,
    val error: Color,
)

internal val LightLoginPalette =
    LoginPalette(
        background = Color(0xFFDCE7E0),
        backgroundTop = Color(0xFFC5DDE5),
        backgroundMiddle = Color(0xFFE8E3D5),
        backgroundBottom = Color(0xFFC1D3BF),
        surface = Color(0xFFEEF0E5),
        field = Color(0xFFE3E9DD),
        text = Color(0xFF1D3527),
        muted = Color(0xFF53695B),
        accent = Color(0xFF287A56),
        accentDark = Color(0xFF20563D),
        accentSoft = Color(0xFFD5E4D2),
        border = Color(0xFF91AF94),
        error = Color(0xFFC34457),
    )

internal val DarkLoginPalette =
    LoginPalette(
        background = Color(0xFF111B15),
        backgroundTop = Color(0xFF1D3023),
        backgroundMiddle = Color(0xFF18271D),
        backgroundBottom = Color(0xFF101912),
        surface = Color(0xFF23352A),
        field = Color(0xFF2B3D31),
        text = Color(0xFFF0F4EF),
        muted = Color(0xFFB8C9BA),
        accent = Color(0xFF79CA8F),
        accentDark = Color(0xFF9ED8AA),
        accentSoft = Color(0xFF304A37),
        border = Color(0xFF55755C),
        error = Color(0xFFFF9AA8),
    )

internal data class LoginRoleStyle(
    val accent: Color,
    val soft: Color,
    val border: Color,
    val button: Color,
    val icon: ImageVector,
    val hint: String,
)

internal fun loginRoleStyle(role: UserRole, darkMode: Boolean): LoginRoleStyle =
    when (role) {
        UserRole.ADMIN ->
            if (darkMode) {
                LoginRoleStyle(
                    accent = Color(0xFF9BD9A7),
                    soft = Color(0xFF304A37),
                    border = Color(0xFF5C8A65),
                    button = Color(0xFF2E8050),
                    icon = Icons.Filled.Lock,
                    hint = "Kelola sistem",
                )
            } else {
                LoginRoleStyle(
                    accent = Color(0xFF276746),
                    soft = Color(0xFFD2E2C9),
                    border = Color(0xFF7EA58A),
                    button = Color(0xFF287A56),
                    icon = Icons.Filled.Lock,
                    hint = "Kelola sistem",
                )
            }

        UserRole.PETUGAS ->
            if (darkMode) {
                LoginRoleStyle(
                    accent = Color(0xFF85D3B0),
                    soft = Color(0xFF21483A),
                    border = Color(0xFF478C70),
                    button = Color(0xFF267A5D),
                    icon = Icons.Filled.Person,
                    hint = "Tugas lapangan",
                )
            } else {
                LoginRoleStyle(
                    accent = Color(0xFF286B5A),
                    soft = Color(0xFFD3E5DB),
                    border = Color(0xFF83AE9E),
                    button = Color(0xFF347F69),
                    icon = Icons.Filled.Person,
                    hint = "Tugas lapangan",
                )
            }
    }

@Composable
internal fun animateLoginPalette(darkMode: Boolean): LoginPalette {
    val progress by
        animateFloatAsState(
            targetValue = if (darkMode) 1f else 0f,
            animationSpec = tween(durationMillis = 760, easing = FastOutSlowInEasing),
            label = "login-palette-transition",
        )
    fun blend(light: Color, dark: Color) = lerp(light, dark, progress)

    return LoginPalette(
        background = blend(LightLoginPalette.background, DarkLoginPalette.background),
        backgroundTop = blend(LightLoginPalette.backgroundTop, DarkLoginPalette.backgroundTop),
        backgroundMiddle =
            blend(LightLoginPalette.backgroundMiddle, DarkLoginPalette.backgroundMiddle),
        backgroundBottom =
            blend(LightLoginPalette.backgroundBottom, DarkLoginPalette.backgroundBottom),
        surface = blend(LightLoginPalette.surface, DarkLoginPalette.surface),
        field = blend(LightLoginPalette.field, DarkLoginPalette.field),
        text = blend(LightLoginPalette.text, DarkLoginPalette.text),
        muted = blend(LightLoginPalette.muted, DarkLoginPalette.muted),
        accent = blend(LightLoginPalette.accent, DarkLoginPalette.accent),
        accentDark = blend(LightLoginPalette.accentDark, DarkLoginPalette.accentDark),
        accentSoft = blend(LightLoginPalette.accentSoft, DarkLoginPalette.accentSoft),
        border = blend(LightLoginPalette.border, DarkLoginPalette.border),
        error = blend(LightLoginPalette.error, DarkLoginPalette.error),
    )
}

internal val RecyclingLogoFilter =
    ColorFilter.colorMatrix(
        ColorMatrix(
            floatArrayOf(
                0f,
                0f,
                0f,
                0f,
                35f,
                0f,
                0f,
                0f,
                0f,
                132f,
                0f,
                0f,
                0f,
                0f,
                90f,
                -0.299f,
                -0.587f,
                -0.114f,
                0f,
                255f,
            ),
        ),
    )

@Composable
internal fun SmartWasteBackdrop(
    darkMode: Boolean,
    modifier: Modifier = Modifier,
) {
    val transition = rememberInfiniteTransition(label = "eco-backdrop-motion")
    val skyMotion by
        transition.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec =
                infiniteRepeatable(
                    animation = tween(durationMillis = 6500),
                    repeatMode = RepeatMode.Reverse,
                ),
            label = "night-star-twinkle",
        )

    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height
        val farHill =
            if (darkMode) {
                Color(0xFF273B2F).copy(alpha = 0.88f)
            } else {
                LightLoginPalette.background
            }
        val nearHill =
            if (darkMode) {
                Color(0xFF17251C).copy(alpha = 0.96f)
            } else {
                LightLoginPalette.backgroundBottom
            }
        drawPath(
            path =
                Path().apply {
                    moveTo(0f, h * 0.42f)
                    cubicTo(w * 0.18f, h * 0.18f, w * 0.34f, h * 0.52f, w * 0.56f, h * 0.32f)
                    cubicTo(w * 0.75f, h * 0.14f, w * 0.85f, h * 0.43f, w, h * 0.24f)
                    lineTo(w, h)
                    lineTo(0f, h)
                    close()
                },
            color = farHill,
        )
        drawPath(
            path =
                Path().apply {
                    moveTo(0f, h * 0.62f)
                    cubicTo(w * 0.2f, h * 0.37f, w * 0.38f, h * 0.74f, w * 0.62f, h * 0.52f)
                    cubicTo(w * 0.8f, h * 0.37f, w * 0.9f, h * 0.62f, w, h * 0.46f)
                    lineTo(w, h)
                    lineTo(0f, h)
                    close()
                },
            color = nearHill,
        )

        if (darkMode) {
            val skyPhase = skyMotion * 2f * PI.toFloat()
            val stars =
                listOf(
                    Offset(w * 0.18f, h * 0.18f),
                    Offset(w * 0.47f, h * 0.12f),
                    Offset(w * 0.68f, h * 0.34f),
                    Offset(w * 0.91f, h * 0.43f),
                )
            stars.forEachIndexed { index, star ->
                val twinkle =
                    0.92f +
                        0.08f * (0.5f + 0.5f * sin((skyPhase + index * 1.4f).toDouble()).toFloat())
                drawCircle(
                    Color.White.copy(alpha = twinkle),
                    (1.4f + index % 2 * 0.5f).dp.toPx(),
                    star,
                )
            }
        }
    }
}

@Composable
internal fun MoonlitNightAtmosphere(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val width = size.width
        val height = size.height
        val moonlight = Color(0xFFE2EEE5)

        drawRect(
            brush =
                Brush.radialGradient(
                    colors =
                        listOf(
                            moonlight.copy(alpha = 0.26f),
                            moonlight.copy(alpha = 0.14f),
                            moonlight.copy(alpha = 0.04f),
                            Color.Transparent,
                        ),
                    center = Offset(width * 0.92f, height * 0.03f),
                    radius = width * 0.82f,
                ),
            size = size,
        )
        drawRect(
            brush =
                Brush.linearGradient(
                    colors =
                        listOf(
                            Color.Transparent,
                            moonlight.copy(alpha = 0.035f),
                            moonlight.copy(alpha = 0.11f),
                            moonlight.copy(alpha = 0.035f),
                            Color.Transparent,
                        ),
                    start = Offset(width * 0.96f, height * 0.02f),
                    end = Offset(width * 0.18f, height * 0.58f),
                ),
            size = size,
        )
    }
}

@Composable
internal fun MorningAtmosphere(modifier: Modifier = Modifier) {
    val morningMotion = rememberInfiniteTransition(label = "soft-morning-light")
    val dawnBreath by
        morningMotion.animateFloat(
            initialValue = 0.20f,
            targetValue = 0.30f,
            animationSpec =
                infiniteRepeatable(
                    animation = tween(durationMillis = 12000),
                    repeatMode = RepeatMode.Reverse,
                ),
            label = "morning-dawn-breath",
        )
    val morningAir by
        morningMotion.animateFloat(
            initialValue = 0.11f,
            targetValue = 0.17f,
            animationSpec =
                infiniteRepeatable(
                    animation = tween(durationMillis = 14500),
                    repeatMode = RepeatMode.Reverse,
                ),
            label = "soft-morning-air",
        )
    val cloudDrift by
        morningMotion.animateFloat(
            initialValue = -1f,
            targetValue = 1f,
            animationSpec =
                infiniteRepeatable(
                    animation = tween(durationMillis = 18000, easing = FastOutSlowInEasing),
                    repeatMode = RepeatMode.Reverse,
                ),
            label = "morning-cloud-drift",
        )
    val birdFlight by
        morningMotion.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec =
                infiniteRepeatable(
                    animation = tween(durationMillis = 15000, easing = FastOutSlowInEasing),
                    repeatMode = RepeatMode.Reverse,
                ),
            label = "morning-birds",
        )
    val butterflyFlight by
        morningMotion.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec =
                infiniteRepeatable(
                    animation = tween(durationMillis = 11000, easing = FastOutSlowInEasing),
                    repeatMode = RepeatMode.Reverse,
                ),
            label = "morning-butterflies",
        )

    Canvas(modifier = modifier) {
        // A broad, cool morning sky with a soft warm horizon; no circular light source.
        drawRect(
            brush =
                Brush.verticalGradient(
                    colors =
                        listOf(
                            Color(0xFFB6D5E0).copy(alpha = 0.50f),
                            Color(0xFFD7E5E0).copy(alpha = 0.34f),
                            Color(0xFFEBD6B9).copy(alpha = 0.26f + dawnBreath * 0.12f),
                            Color(0xFFD2E0D0).copy(alpha = 0.18f),
                            Color.Transparent,
                        ),
                    startY = 0f,
                    endY = size.height * 0.90f,
                ),
            size = size,
        )
        val cloudShift = cloudDrift * 18.dp.toPx()
        // Extend beyond both canvas edges so the slow drift never exposes a seam.
        val cloudBleed = 28.dp.toPx()
        val cloudLeft = -cloudBleed - cloudShift
        val cloudRight = size.width + cloudBleed - cloudShift

        val morningCloud =
            Path().apply {
                moveTo(cloudLeft, size.height * 0.16f)
                cubicTo(
                    size.width * 0.22f - cloudShift,
                    size.height * 0.11f,
                    size.width * 0.38f - cloudShift,
                    size.height * 0.21f,
                    size.width * 0.62f - cloudShift,
                    size.height * 0.14f,
                )
                cubicTo(
                    size.width * 0.78f - cloudShift,
                    size.height * 0.09f,
                    size.width * 0.89f - cloudShift,
                    size.height * 0.15f,
                    cloudRight,
                    size.height * 0.10f,
                )
                lineTo(cloudRight, size.height * 0.21f)
                cubicTo(
                    size.width * 0.76f - cloudShift,
                    size.height * 0.25f,
                    size.width * 0.42f - cloudShift,
                    size.height * 0.22f,
                    cloudLeft,
                    size.height * 0.26f,
                )
                close()
            }
        drawPath(morningCloud, Color(0xFFF5F4E8).copy(alpha = 0.28f))

        val passingCloud =
            Path().apply {
                moveTo(size.width * 0.05f, size.height * 0.36f)
                cubicTo(
                    size.width * 0.16f,
                    size.height * 0.31f,
                    size.width * 0.27f,
                    size.height * 0.39f,
                    size.width * 0.39f,
                    size.height * 0.34f,
                )
                cubicTo(
                    size.width * 0.33f,
                    size.height * 0.40f,
                    size.width * 0.16f,
                    size.height * 0.40f,
                    size.width * 0.05f,
                    size.height * 0.36f,
                )
                close()
            }
        drawPath(passingCloud, Color(0xFFF5F4E8).copy(alpha = 0.18f))

        // A small, slow-moving flock gives the light theme its own morning cue.
        val flightPhase = birdFlight * PI.toFloat()
        val flockX = size.width * 0.13f + sin(flightPhase) * 22.dp.toPx()
        val flockY = size.height * 0.30f + cos(flightPhase) * 5.dp.toPx()
        val birdColor = Color(0xFF426B50).copy(alpha = 0.46f)
        drawMorningBird(Offset(flockX, flockY), 7.dp.toPx(), birdColor)
        drawMorningBird(
            Offset(flockX + 24.dp.toPx(), flockY - 8.dp.toPx()),
            5.5.dp.toPx(),
            birdColor.copy(alpha = 0.78f),
        )
        drawMorningBird(
            Offset(flockX + 44.dp.toPx(), flockY + 4.dp.toPx()),
            4.5.dp.toPx(),
            birdColor.copy(alpha = 0.62f),
        )

        drawRect(
            brush =
                Brush.horizontalGradient(
                    colors =
                        listOf(
                            Color(0xFFB8D7C0).copy(alpha = morningAir * 0.62f),
                            Color.Transparent,
                            Color(0xFFE4D9C1).copy(alpha = morningAir * 0.42f),
                        ),
                ),
            size = size,
        )

        val butterflyPhase = butterflyFlight * 2f * PI.toFloat()
        val firstBeat = 0.5f + 0.5f * sin(butterflyPhase * 7f)
        val secondBeat = 0.5f + 0.5f * sin(butterflyPhase * 7f + PI.toFloat() / 2f)
        val firstButterfly =
            Offset(
                x = size.width * 0.14f + sin(butterflyPhase) * 14.dp.toPx(),
                y = size.height * 0.13f + cos(butterflyPhase) * 6.dp.toPx(),
            )
        val secondButterfly =
            Offset(
                x = size.width * 0.86f + sin(butterflyPhase + PI.toFloat()) * 12.dp.toPx(),
                y = size.height * 0.19f + cos(butterflyPhase + PI.toFloat()) * 5.dp.toPx(),
            )
        drawSoftButterfly(
            center = firstButterfly,
            span = 18.dp.toPx(),
            wingBeat = firstBeat,
            tilt = sin(butterflyPhase) * 5f,
            color = Color(0xFF4E8B65).copy(alpha = 0.54f),
        )
        drawSoftButterfly(
            center = secondButterfly,
            span = 15.dp.toPx(),
            wingBeat = secondBeat,
            tilt = sin(butterflyPhase + PI.toFloat()) * 4f,
            color = Color(0xFF9C805A).copy(alpha = 0.48f),
        )
    }
}

internal fun DrawScope.drawSoftButterfly(
    center: Offset,
    span: Float,
    wingBeat: Float,
    tilt: Float,
    color: Color,
) {
    val wingReach = span * (0.72f + wingBeat * 0.16f)
    val wingHeight = span * (0.50f + wingBeat * 0.10f)

    rotate(degrees = tilt, pivot = center) {
        val upperLeft =
            Path().apply {
                moveTo(center.x, center.y)
                cubicTo(
                    center.x - wingReach * 0.20f,
                    center.y - wingHeight * 0.92f,
                    center.x - wingReach * 0.86f,
                    center.y - wingHeight * 1.14f,
                    center.x - wingReach,
                    center.y - wingHeight * 0.30f,
                )
                cubicTo(
                    center.x - wingReach * 0.92f,
                    center.y + wingHeight * 0.12f,
                    center.x - wingReach * 0.30f,
                    center.y + wingHeight * 0.08f,
                    center.x,
                    center.y,
                )
                close()
            }
        val upperRight =
            Path().apply {
                moveTo(center.x, center.y)
                cubicTo(
                    center.x + wingReach * 0.20f,
                    center.y - wingHeight * 0.92f,
                    center.x + wingReach * 0.86f,
                    center.y - wingHeight * 1.14f,
                    center.x + wingReach,
                    center.y - wingHeight * 0.30f,
                )
                cubicTo(
                    center.x + wingReach * 0.92f,
                    center.y + wingHeight * 0.12f,
                    center.x + wingReach * 0.30f,
                    center.y + wingHeight * 0.08f,
                    center.x,
                    center.y,
                )
                close()
            }
        val lowerLeft =
            Path().apply {
                moveTo(center.x, center.y + wingHeight * 0.08f)
                cubicTo(
                    center.x - wingReach * 0.34f,
                    center.y + wingHeight * 0.02f,
                    center.x - wingReach * 0.70f,
                    center.y + wingHeight * 0.34f,
                    center.x - wingReach * 0.54f,
                    center.y + wingHeight * 0.78f,
                )
                cubicTo(
                    center.x - wingReach * 0.20f,
                    center.y + wingHeight * 0.80f,
                    center.x - wingReach * 0.10f,
                    center.y + wingHeight * 0.34f,
                    center.x,
                    center.y + wingHeight * 0.20f,
                )
                close()
            }
        val lowerRight =
            Path().apply {
                moveTo(center.x, center.y + wingHeight * 0.08f)
                cubicTo(
                    center.x + wingReach * 0.34f,
                    center.y + wingHeight * 0.02f,
                    center.x + wingReach * 0.70f,
                    center.y + wingHeight * 0.34f,
                    center.x + wingReach * 0.54f,
                    center.y + wingHeight * 0.78f,
                )
                cubicTo(
                    center.x + wingReach * 0.20f,
                    center.y + wingHeight * 0.80f,
                    center.x + wingReach * 0.10f,
                    center.y + wingHeight * 0.34f,
                    center.x,
                    center.y + wingHeight * 0.20f,
                )
                close()
            }

        drawPath(upperLeft, color)
        drawPath(upperRight, color)
        drawPath(lowerLeft, color.copy(alpha = color.alpha * 0.82f))
        drawPath(lowerRight, color.copy(alpha = color.alpha * 0.82f))
        drawLine(
            color = color.copy(alpha = color.alpha * 0.92f),
            start = Offset(center.x, center.y - wingHeight * 0.24f),
            end = Offset(center.x, center.y + wingHeight * 0.64f),
            strokeWidth = 0.8.dp.toPx(),
        )
    }
}

internal fun DrawScope.drawMorningBird(center: Offset, size: Float, color: Color) {
    val wings =
        Path().apply {
            moveTo(center.x - size, center.y)
            cubicTo(
                center.x - size * 0.62f,
                center.y - size * 0.62f,
                center.x - size * 0.28f,
                center.y - size * 0.34f,
                center.x,
                center.y + size * 0.08f,
            )
            cubicTo(
                center.x + size * 0.28f,
                center.y - size * 0.34f,
                center.x + size * 0.62f,
                center.y - size * 0.62f,
                center.x + size,
                center.y,
            )
        }
    drawPath(wings, color, style = Stroke(width = 1.5.dp.toPx()))
}
