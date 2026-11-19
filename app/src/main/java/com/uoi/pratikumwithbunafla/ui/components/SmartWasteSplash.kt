package com.uoi.pratikumwithbunafla.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.uoi.pratikumwithbunafla.R
import com.uoi.pratikumwithbunafla.ui.theme.InterFont
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import kotlinx.coroutines.delay

private val SplashLogoGreen = Color(0xFF23845A)
private val SplashForest = Color(0xFF1B714C)
private val SplashLeaf = Color(0xFF4A9B68)

@Composable
fun SmartWasteSplash() {
    val compactScreen = LocalConfiguration.current.screenWidthDp < 360
    val backgroundMotion = rememberInfiniteTransition(label = "splash-background-motion")
    val leafDrift by
        backgroundMotion.animateFloat(
            initialValue = -1f,
            targetValue = 1f,
            animationSpec =
                infiniteRepeatable(
                    animation = tween(5_600, easing = LinearEasing),
                    repeatMode = RepeatMode.Reverse,
                ),
            label = "splash-leaf-drift",
        )
    val leafPass by
        backgroundMotion.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec =
                infiniteRepeatable(
                    animation = tween(3_800, easing = LinearEasing),
                    repeatMode = RepeatMode.Reverse,
                ),
            label = "splash-leaf-pass",
        )

    Box(
        modifier =
            Modifier.fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color(0xFFD6E8DB),
                            Color(0xFFE1EDDE),
                            Color(0xFFF0F0E3),
                            Color(0xFFD8E7D3),
                            Color(0xFFBBD3BF),
                        ),
                    ),
                ),
    ) {
        SplashEcoBackdrop(leafDrift, Modifier.fillMaxSize())
        SplashLeafFlight(leafPass, Modifier.fillMaxSize())

        Column(
            modifier = Modifier.fillMaxSize().padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            SplashLogoAssembly(compactScreen)
        }
    }
}

@Composable
internal fun SplashLogoAssembly(compactScreen: Boolean) {
    val logoSize = if (compactScreen) 152.dp else 172.dp
    val assemblyProgress = remember { Animatable(0f) }
    val logoProgress = remember { Animatable(0f) }
    val textProgress = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        assemblyProgress.animateTo(1f, tween(2_250, easing = FastOutSlowInEasing))
        logoProgress.animateTo(1f, tween(400, easing = FastOutSlowInEasing))
        delay(60)
        textProgress.animateTo(1f, tween(290, easing = FastOutSlowInEasing))
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier.size(logoSize),
            contentAlignment = Alignment.Center,
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val progress = assemblyProgress.value.coerceIn(0f, 1f)
                val logoReveal = ((progress - 0.82f) / 0.18f).coerceIn(0f, 1f)
                val fragmentAlpha = 1f - logoReveal
                val center = Offset(size.width / 2f, size.height / 2f)
                val radius = size.minDimension * 0.34f
                val strokeWidth = 7.dp.toPx()

                if (fragmentAlpha > 0.001f) {
                    val ringProgress =
                        FastOutSlowInEasing.transform((progress / 0.78f).coerceIn(0f, 1f))
                    val endpointAlpha = (1f - ringProgress) * fragmentAlpha
                    val ringRotation = (1f - ringProgress) * 22f
                    val ringTopLeft = Offset(center.x - radius, center.y - radius)
                    val ringSize = Size(radius * 2f, radius * 2f)

                    fun ringPoint(angle: Float): Offset {
                        val radians = (angle + ringRotation) * (PI.toFloat() / 180f)
                        return Offset(
                            x = center.x + cos(radians) * radius,
                            y = center.y + sin(radians) * radius,
                        )
                    }

                    fun drawArcSegment(sweepAngle: Float) {
                        rotate(ringRotation, center) {
                            drawArc(
                                color = SplashLogoGreen.copy(alpha = fragmentAlpha),
                                startAngle = -90f,
                                sweepAngle = sweepAngle,
                                useCenter = false,
                                topLeft = ringTopLeft,
                                size = ringSize,
                                style = Stroke(width = strokeWidth, cap = StrokeCap.Round),
                            )
                        }

                        val endpoint = ringPoint(-90f + sweepAngle)
                        drawCircle(
                            color = SplashLogoGreen.copy(alpha = 0.32f * endpointAlpha),
                            radius = 8.dp.toPx(),
                            center = endpoint,
                        )
                        drawCircle(
                            color = SplashLogoGreen.copy(alpha = endpointAlpha),
                            radius = 3.dp.toPx(),
                            center = endpoint,
                        )
                    }

                    val sweep = 180f * ringProgress
                    drawArcSegment(sweep)
                    drawArcSegment(-sweep)

                    val startPoint = ringPoint(-90f)
                    drawCircle(
                        color = SplashLogoGreen.copy(alpha = 0.32f * endpointAlpha),
                        radius = 8.dp.toPx(),
                        center = startPoint,
                    )
                    drawCircle(
                        color = SplashLogoGreen.copy(alpha = endpointAlpha),
                        radius = 3.dp.toPx(),
                        center = startPoint,
                    )
                }
            }

            Image(
                painter = painterResource(R.drawable.login_recycling_mark),
                contentDescription = "Logo daur ulang Smart Waste",
                colorFilter = SplashLogoFilter,
                modifier =
                    Modifier.fillMaxSize().graphicsLayer {
                        alpha = logoProgress.value
                        val scale = 0.88f + 0.12f * logoProgress.value
                        scaleX = scale
                        scaleY = scale
                    },
            )
        }

        Spacer(modifier = Modifier.height(if (compactScreen) 16.dp else 19.dp))
        Text(
            text = "Smart Waste",
            color = SplashLogoGreen,
            fontSize = if (compactScreen) 21.sp else 23.sp,
            fontFamily = InterFont,
            fontWeight = FontWeight.SemiBold,
            letterSpacing = 0.1.sp,
            modifier =
                Modifier.graphicsLayer {
                    alpha = textProgress.value
                    translationY = (1f - textProgress.value) * 8.dp.toPx()
                },
        )
    }
}

internal val SplashLogoFilter =
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
internal fun SplashEcoBackdrop(drift: Float, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height
        val treeTone = Color(0xFF456A53).copy(alpha = 0.30f)
        val hillTone = Color(0xFF66836A).copy(alpha = 0.30f)

        drawPath(
            path =
                Path().apply {
                    moveTo(0f, h * 0.69f)
                    cubicTo(w * 0.22f, h * 0.56f, w * 0.34f, h * 0.72f, w * 0.52f, h * 0.64f)
                    cubicTo(w * 0.71f, h * 0.55f, w * 0.85f, h * 0.72f, w, h * 0.61f)
                    lineTo(w, h)
                    lineTo(0f, h)
                    close()
                },
            color = Color(0xFF91AE91).copy(alpha = 0.32f),
        )
        drawPath(
            path =
                Path().apply {
                    moveTo(0f, h * 0.81f)
                    cubicTo(w * 0.2f, h * 0.7f, w * 0.43f, h * 0.88f, w * 0.62f, h * 0.76f)
                    cubicTo(w * 0.79f, h * 0.67f, w * 0.9f, h * 0.83f, w, h * 0.74f)
                    lineTo(w, h)
                    lineTo(0f, h)
                    close()
                },
            color = hillTone,
        )

        drawSplashTree(w * 0.04f, h * 0.98f, w * 0.32f, treeTone)
        drawSplashTree(w * 0.98f, h * 0.98f, w * 0.40f, treeTone.copy(alpha = 0.12f))
        drawSplashTree(w * 0.22f, h * 0.97f, w * 0.19f, treeTone.copy(alpha = 0.11f))
        drawSplashHouse(w * 0.62f, h * 0.91f, w * 0.14f, h * 0.16f, treeTone)

        val sway = sin(drift * PI).toFloat()
        drawSplashLeaf(
            Offset(w * 0.13f + sway * 13.dp.toPx(), h * 0.22f),
            10.dp.toPx(),
            SplashLeaf.copy(alpha = 0.30f),
            -28f,
        )
        drawSplashLeaf(
            Offset(w * 0.82f - sway * 10.dp.toPx(), h * 0.34f),
            8.dp.toPx(),
            SplashForest.copy(alpha = 0.24f),
            24f,
        )
        drawSplashLeaf(
            Offset(w * 0.91f + sway * 8.dp.toPx(), h * 0.53f),
            6.dp.toPx(),
            SplashLeaf.copy(alpha = 0.25f),
            42f,
        )
    }
}

@Composable
internal fun SplashLeafFlight(progress: Float, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val firstPhase = progress * PI * 2.0
        val firstSway = sin(firstPhase).toFloat()
        drawSplashLeaf(
            center =
                Offset(
                    x = size.width * (0.15f + progress * 0.10f) + firstSway * 9.dp.toPx(),
                    y = size.height * (0.55f - progress * 0.10f),
                ),
            radius = 7.dp.toPx(),
            color = SplashLeaf.copy(alpha = 0.30f),
            angle = -24f + firstSway * 12f,
        )

        val secondProgress = (progress + 0.48f) % 1f
        val secondPhase = secondProgress * PI * 2.0
        val secondSway = sin(secondPhase).toFloat()
        drawSplashLeaf(
            center =
                Offset(
                    x = size.width * (0.82f - secondProgress * 0.08f) - secondSway * 7.dp.toPx(),
                    y = size.height * (0.44f + secondProgress * 0.12f),
                ),
            radius = 6.dp.toPx(),
            color = SplashForest.copy(alpha = 0.20f),
            angle = 28f - secondSway * 10f,
        )
    }
}

internal fun DrawScope.drawSplashTree(x: Float, groundY: Float, treeSize: Float, color: Color) {
    val trunkWidth = treeSize * 0.10f
    drawRoundRect(
        color = color.copy(alpha = color.alpha * 0.8f),
        topLeft = Offset(x - trunkWidth / 2f, groundY - treeSize * 0.65f),
        size = Size(trunkWidth, treeSize * 0.67f),
        cornerRadius = CornerRadius(trunkWidth / 2f),
    )
    drawCircle(color, treeSize * 0.30f, Offset(x, groundY - treeSize * 0.79f))
    drawCircle(
        color.copy(alpha = color.alpha * 0.85f),
        treeSize * 0.24f,
        Offset(x - treeSize * 0.2f, groundY - treeSize * 0.63f),
    )
    drawCircle(
        color.copy(alpha = color.alpha * 0.85f),
        treeSize * 0.24f,
        Offset(x + treeSize * 0.2f, groundY - treeSize * 0.63f),
    )
}

internal fun DrawScope.drawSplashHouse(
    x: Float,
    groundY: Float,
    houseWidth: Float,
    houseHeight: Float,
    color: Color,
) {
    val top = groundY - houseHeight * 0.82f
    drawRoundRect(
        color = color,
        topLeft = Offset(x, top + houseHeight * 0.26f),
        size = Size(houseWidth, houseHeight * 0.56f),
        cornerRadius = CornerRadius(5.dp.toPx()),
    )
    drawPath(
        path =
            Path().apply {
                moveTo(x - houseWidth * 0.08f, top + houseHeight * 0.28f)
                lineTo(x + houseWidth * 0.5f, top)
                lineTo(x + houseWidth * 1.08f, top + houseHeight * 0.28f)
                close()
            },
        color = color.copy(alpha = color.alpha * 1.12f),
    )
    drawRoundRect(
        color = Color(0xFFFFE8C2).copy(alpha = 0.88f),
        topLeft = Offset(x + houseWidth * 0.14f, top + houseHeight * 0.39f),
        size = Size(houseWidth * 0.2f, houseHeight * 0.15f),
        cornerRadius = CornerRadius(2.dp.toPx()),
    )
    drawRoundRect(
        color = Color(0xFFFFE8C2).copy(alpha = 0.88f),
        topLeft = Offset(x + houseWidth * 0.66f, top + houseHeight * 0.39f),
        size = Size(houseWidth * 0.2f, houseHeight * 0.15f),
        cornerRadius = CornerRadius(2.dp.toPx()),
    )
}

internal fun DrawScope.drawSplashLeaf(center: Offset, radius: Float, color: Color, angle: Float) {
    rotate(angle, center) {
        val leaf =
            Path().apply {
                moveTo(center.x, center.y - radius)
                quadraticTo(center.x + radius, center.y, center.x, center.y + radius)
                quadraticTo(center.x - radius, center.y, center.x, center.y - radius)
                close()
            }
        drawPath(leaf, color)
        drawLine(
            color = color.copy(alpha = color.alpha * 0.68f),
            start = Offset(center.x, center.y - radius * 0.62f),
            end = Offset(center.x, center.y + radius * 0.62f),
            strokeWidth = 1.dp.toPx(),
        )
    }
}
