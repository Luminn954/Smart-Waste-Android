package com.uoi.pratikumwithbunafla.ui.components

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.fadeIn
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Eco
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import kotlin.math.PI
import kotlin.math.sin
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL
import com.uoi.pratikumwithbunafla.R
import com.uoi.pratikumwithbunafla.ui.theme.AppBackground
import com.uoi.pratikumwithbunafla.ui.theme.AppBorder
import com.uoi.pratikumwithbunafla.ui.theme.AppSurface
import com.uoi.pratikumwithbunafla.ui.theme.AppText
import com.uoi.pratikumwithbunafla.ui.theme.AppTextMuted
import com.uoi.pratikumwithbunafla.ui.theme.WasteGreen
import com.uoi.pratikumwithbunafla.ui.theme.WasteGreenDark
import com.uoi.pratikumwithbunafla.ui.theme.WasteMint
import com.uoi.pratikumwithbunafla.ui.theme.WasteBlue
import com.uoi.pratikumwithbunafla.ui.theme.WasteAmber
import com.uoi.pratikumwithbunafla.ui.theme.WasteCoral
import com.uoi.pratikumwithbunafla.ui.theme.WastePurple
import com.uoi.pratikumwithbunafla.ui.theme.WasteTeal
import com.uoi.pratikumwithbunafla.ui.theme.InterFont

@Composable
fun AppScreen(content: @Composable () -> Unit) {
    val ambientMotion = rememberInfiniteTransition(label = "ambient-motion")
    val topOrbOffset by ambientMotion.animateFloat(
        initialValue = -78f,
        targetValue = -70f,
        animationSpec = infiniteRepeatable(
            animation = tween(7200, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "top-orb-offset"
    )
    val bottomOrbOffset by ambientMotion.animateFloat(
        initialValue = 88f,
        targetValue = 80f,
        animationSpec = infiniteRepeatable(
            animation = tween(8200, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "bottom-orb-offset"
    )
    Box(modifier = Modifier.fillMaxSize().background(AppBackground)) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            WasteMint.copy(alpha = 0.30f),
                            WasteBlue.copy(alpha = 0.14f),
                            AppBackground,
                            WasteCoral.copy(alpha = 0.16f)
                        )
                    )
                )
        )
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .offset {
                    IntOffset(
                        x = 72.dp.roundToPx(),
                        y = topOrbOffset.dp.roundToPx()
                    )
                }
                .size(210.dp)
                .clip(CircleShape)
                .background(WasteBlue.copy(alpha = 0.24f))
        )
        Box(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .offset {
                    IntOffset(
                        x = (-82).dp.roundToPx(),
                        y = bottomOrbOffset.dp.roundToPx()
                    )
                }
                .size(190.dp)
                .clip(CircleShape)
                .background(WasteAmber.copy(alpha = 0.20f))
        )
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .offset(x = 16.dp, y = 20.dp)
                .size(92.dp)
                .clip(CircleShape)
                .background(WastePurple.copy(alpha = 0.04f))
        )
        AnimatedVisibility(
            visible = true,
            modifier = Modifier.fillMaxSize(),
            enter = fadeIn(tween(250)) + slideInVertically(
                initialOffsetY = { it / 24 },
                animationSpec = tween(250)
            )
        ) {
            content()
        }
    }
}

private val SplashForest = Color(0xFF1B714C)
private val SplashLeaf = Color(0xFF4A9B68)
private val SplashArcGreen = Color(0xFF0A9660)

@Composable
fun SmartWasteSplash() {
    val compactScreen = LocalConfiguration.current.screenWidthDp < 360
    val backgroundMotion = rememberInfiniteTransition(label = "splash-background-motion")
    val leafDrift by backgroundMotion.animateFloat(
        initialValue = -1f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(5_600, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "splash-leaf-drift"
    )
    val leafPass by backgroundMotion.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(3_800, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "splash-leaf-pass"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color(0xFFD6E8DB),
                        Color(0xFFE1EDDE),
                        Color(0xFFF0F0E3),
                        Color(0xFFD8E7D3),
                        Color(0xFFBBD3BF)
                    )
                )
            )
    ) {
        SplashEcoBackdrop(leafDrift, Modifier.fillMaxSize())
        SplashLeafFlight(leafPass, Modifier.fillMaxSize())

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            SplashLogoAssembly(compactScreen)
        }
    }
}

@Composable
private fun SplashLogoAssembly(compactScreen: Boolean) {
    val logoSize = if (compactScreen) 152.dp else 172.dp
    val arcProgress = remember { Animatable(0f) }
    val logoProgress = remember { Animatable(0f) }
    val nameProgress = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        arcProgress.animateTo(1f, tween(1_700, easing = FastOutSlowInEasing))
        logoProgress.animateTo(1f, tween(680, easing = FastOutSlowInEasing))
        delay(150)
        nameProgress.animateTo(1f, tween(480, easing = FastOutSlowInEasing))
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier.size(logoSize),
            contentAlignment = Alignment.Center
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val progress = arcProgress.value.coerceIn(0f, 1f)
                val arcAlpha = (1f - logoProgress.value).coerceIn(0f, 1f)
                val center = Offset(size.width / 2f, size.height / 2f)
                val inset = 13.dp.toPx()
                val arcBounds = Offset(inset, inset)
                val arcSize = Size(size.width - inset * 2f, size.height - inset * 2f)
                val sweep = 18f + progress * 142f
                val rotation = (1f - progress) * 28f
                val glowStroke = Stroke(width = 15.dp.toPx(), cap = StrokeCap.Round)
                val softStroke = Stroke(width = 9.dp.toPx(), cap = StrokeCap.Round)
                val coreStroke = Stroke(width = 4.5.dp.toPx(), cap = StrokeCap.Round)

                fun drawSweep(startAngle: Float, turn: Float) {
                    rotate(turn, center) {
                        drawArc(
                            color = SplashForest.copy(alpha = 0.15f * arcAlpha),
                            startAngle = startAngle,
                            sweepAngle = sweep,
                            useCenter = false,
                            topLeft = arcBounds,
                            size = arcSize,
                            style = glowStroke
                        )
                        drawArc(
                            color = SplashArcGreen.copy(alpha = 0.34f * arcAlpha),
                            startAngle = startAngle,
                            sweepAngle = sweep,
                            useCenter = false,
                            topLeft = arcBounds,
                            size = arcSize,
                            style = softStroke
                        )
                        drawArc(
                            color = SplashArcGreen.copy(alpha = arcAlpha),
                            startAngle = startAngle,
                            sweepAngle = sweep,
                            useCenter = false,
                            topLeft = arcBounds,
                            size = arcSize,
                            style = coreStroke
                        )

                        val tipAngle = Math.toRadians((startAngle + sweep).toDouble())
                        val tip = Offset(
                            x = center.x + arcSize.width / 2f * kotlin.math.cos(tipAngle).toFloat(),
                            y = center.y + arcSize.height / 2f * kotlin.math.sin(tipAngle).toFloat()
                        )
                        drawCircle(
                            color = SplashForest.copy(alpha = 0.46f * arcAlpha),
                            radius = 9.dp.toPx(),
                            center = tip
                        )
                        drawCircle(
                            color = Color.White.copy(alpha = 0.98f * arcAlpha),
                            radius = 3.2.dp.toPx(),
                            center = tip
                        )
                    }
                }

                drawSweep(-162f, -rotation)
                drawSweep(18f, rotation)
            }

            Image(
                painter = painterResource(R.drawable.login_recycling_mark),
                contentDescription = "Logo daur ulang Smart Waste",
                colorFilter = SplashLogoFilter,
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        alpha = logoProgress.value
                        val scale = 0.82f + 0.18f * logoProgress.value
                        scaleX = scale
                        scaleY = scale
                    }
            )
        }

        Spacer(modifier = Modifier.height(if (compactScreen) 15.dp else 19.dp))
        Text(
            text = "Smart Waste",
            color = SplashForest,
            fontSize = if (compactScreen) 21.sp else 23.sp,
            fontFamily = InterFont,
            fontWeight = FontWeight.SemiBold,
            letterSpacing = 0.1.sp,
            modifier = Modifier.graphicsLayer {
                alpha = nameProgress.value
                translationY = (1f - nameProgress.value) * 8.dp.toPx()
            }
        )
    }
}

private val SplashLogoFilter = ColorFilter.colorMatrix(
    ColorMatrix(
        floatArrayOf(
            0f, 0f, 0f, 0f, 35f,
            0f, 0f, 0f, 0f, 132f,
            0f, 0f, 0f, 0f, 90f,
            -0.299f, -0.587f, -0.114f, 0f, 255f
        )
    )
)

@Composable
private fun SplashEcoBackdrop(drift: Float, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height
        val treeTone = Color(0xFF456A53).copy(alpha = 0.30f)
        val hillTone = Color(0xFF66836A).copy(alpha = 0.30f)

        drawPath(
            path = Path().apply {
                moveTo(0f, h * 0.69f)
                cubicTo(w * 0.22f, h * 0.56f, w * 0.34f, h * 0.72f, w * 0.52f, h * 0.64f)
                cubicTo(w * 0.71f, h * 0.55f, w * 0.85f, h * 0.72f, w, h * 0.61f)
                lineTo(w, h)
                lineTo(0f, h)
                close()
            },
            color = Color(0xFF91AE91).copy(alpha = 0.32f)
        )
        drawPath(
            path = Path().apply {
                moveTo(0f, h * 0.81f)
                cubicTo(w * 0.2f, h * 0.7f, w * 0.43f, h * 0.88f, w * 0.62f, h * 0.76f)
                cubicTo(w * 0.79f, h * 0.67f, w * 0.9f, h * 0.83f, w, h * 0.74f)
                lineTo(w, h)
                lineTo(0f, h)
                close()
            },
            color = hillTone
        )

        drawSplashTree(w * 0.04f, h * 0.98f, w * 0.32f, treeTone)
        drawSplashTree(w * 0.98f, h * 0.98f, w * 0.40f, treeTone.copy(alpha = 0.12f))
        drawSplashTree(w * 0.22f, h * 0.97f, w * 0.19f, treeTone.copy(alpha = 0.11f))
        drawSplashHouse(w * 0.62f, h * 0.91f, w * 0.14f, h * 0.16f, treeTone)

        val sway = sin(drift * PI).toFloat()
        drawSplashLeaf(Offset(w * 0.13f + sway * 13.dp.toPx(), h * 0.22f), 10.dp.toPx(), SplashLeaf.copy(alpha = 0.30f), -28f)
        drawSplashLeaf(Offset(w * 0.82f - sway * 10.dp.toPx(), h * 0.34f), 8.dp.toPx(), SplashForest.copy(alpha = 0.24f), 24f)
        drawSplashLeaf(Offset(w * 0.91f + sway * 8.dp.toPx(), h * 0.53f), 6.dp.toPx(), SplashLeaf.copy(alpha = 0.25f), 42f)
    }
}

@Composable
private fun SplashLeafFlight(progress: Float, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val firstPhase = progress * PI * 2.0
        val firstSway = sin(firstPhase).toFloat()
        drawSplashLeaf(
            center = Offset(
                x = size.width * (0.15f + progress * 0.10f) + firstSway * 9.dp.toPx(),
                y = size.height * (0.55f - progress * 0.10f)
            ),
            radius = 7.dp.toPx(),
            color = SplashLeaf.copy(alpha = 0.30f),
            angle = -24f + firstSway * 12f
        )

        val secondProgress = (progress + 0.48f) % 1f
        val secondPhase = secondProgress * PI * 2.0
        val secondSway = sin(secondPhase).toFloat()
        drawSplashLeaf(
            center = Offset(
                x = size.width * (0.82f - secondProgress * 0.08f) - secondSway * 7.dp.toPx(),
                y = size.height * (0.44f + secondProgress * 0.12f)
            ),
            radius = 6.dp.toPx(),
            color = SplashForest.copy(alpha = 0.20f),
            angle = 28f - secondSway * 10f
        )
    }
}

private fun DrawScope.drawSplashTree(x: Float, groundY: Float, treeSize: Float, color: Color) {
    val trunkWidth = treeSize * 0.10f
    drawRoundRect(
        color = color.copy(alpha = color.alpha * 0.8f),
        topLeft = Offset(x - trunkWidth / 2f, groundY - treeSize * 0.65f),
        size = Size(trunkWidth, treeSize * 0.67f),
        cornerRadius = CornerRadius(trunkWidth / 2f)
    )
    drawCircle(color, treeSize * 0.30f, Offset(x, groundY - treeSize * 0.79f))
    drawCircle(color.copy(alpha = color.alpha * 0.85f), treeSize * 0.24f, Offset(x - treeSize * 0.2f, groundY - treeSize * 0.63f))
    drawCircle(color.copy(alpha = color.alpha * 0.85f), treeSize * 0.24f, Offset(x + treeSize * 0.2f, groundY - treeSize * 0.63f))
}

private fun DrawScope.drawSplashHouse(x: Float, groundY: Float, houseWidth: Float, houseHeight: Float, color: Color) {
    val top = groundY - houseHeight * 0.82f
    drawRoundRect(
        color = color,
        topLeft = Offset(x, top + houseHeight * 0.26f),
        size = Size(houseWidth, houseHeight * 0.56f),
        cornerRadius = CornerRadius(5.dp.toPx())
    )
    drawPath(
        path = Path().apply {
            moveTo(x - houseWidth * 0.08f, top + houseHeight * 0.28f)
            lineTo(x + houseWidth * 0.5f, top)
            lineTo(x + houseWidth * 1.08f, top + houseHeight * 0.28f)
            close()
        },
        color = color.copy(alpha = color.alpha * 1.12f)
    )
    drawRoundRect(
        color = Color(0xFFFFE8C2).copy(alpha = 0.88f),
        topLeft = Offset(x + houseWidth * 0.14f, top + houseHeight * 0.39f),
        size = Size(houseWidth * 0.2f, houseHeight * 0.15f),
        cornerRadius = CornerRadius(2.dp.toPx())
    )
    drawRoundRect(
        color = Color(0xFFFFE8C2).copy(alpha = 0.88f),
        topLeft = Offset(x + houseWidth * 0.66f, top + houseHeight * 0.39f),
        size = Size(houseWidth * 0.2f, houseHeight * 0.15f),
        cornerRadius = CornerRadius(2.dp.toPx())
    )
}

private fun DrawScope.drawSplashLeaf(center: Offset, radius: Float, color: Color, angle: Float) {
    rotate(angle, center) {
        val leaf = Path().apply {
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
            strokeWidth = 1.dp.toPx()
        )
    }
}
@Composable
fun AppHeader(compact: Boolean = false) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(if (compact) 34.dp else 38.dp)
                .clip(RoundedCornerShape(13.dp))
                .background(WasteGreenDark),
            contentAlignment = Alignment.Center
        ) {
            androidx.compose.material3.Icon(
                imageVector = Icons.Filled.Eco,
                contentDescription = "Smart Waste",
                tint = Color.White,
                modifier = Modifier.size(if (compact) 19.dp else 21.dp)
            )
        }
        Spacer(modifier = Modifier.width(if (compact) 8.dp else 10.dp))
        Column {
            Text(
                text = "Smart Waste",
                color = AppText,
                fontSize = if (compact) 15.sp else 17.sp,
                fontWeight = FontWeight.Bold
            )
            if (!compact) Text(
                text = "Panel operasional",
                color = AppTextMuted,
                fontSize = 12.sp
            )
        }
    }
}

@Composable
fun AppCard(
    modifier: Modifier = Modifier,
    containerColor: Color = AppSurface,
    borderColor: Color = AppBorder,
    accentColor: Color? = null,
    elevation: Dp = 0.dp,
    content: @Composable () -> Unit
) {
    val glassSurface = if (containerColor == Color.White || containerColor == AppSurface) {
        AppSurface.copy(alpha = 0.98f)
    } else {
        containerColor
    }
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = glassSurface),
        border = null,
        elevation = CardDefaults.cardElevation(defaultElevation = elevation)
    ) {
        content()
    }
}

@Composable
fun AppButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    containerColor: Color = WasteGreen
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(if (enabled) containerColor else AppBorder)
            .border(
                width = 1.dp,
                color = if (enabled) containerColor.copy(alpha = 0.30f) else AppBorder,
                shape = RoundedCornerShape(16.dp)
            ),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            disabledContainerColor = Color.Transparent,
            disabledContentColor = AppTextMuted
        )
    ) {
        Text(
            text = text,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun UserAvatar(
    name: String,
    modifier: Modifier = Modifier,
    photoUrl: String? = null
) {
    val initial = name.trim().firstOrNull()?.uppercase() ?: "A"
    val photo by produceState<Bitmap?>(initialValue = null, key1 = photoUrl) {
        value = photoUrl?.let { url ->
            withContext(Dispatchers.IO) {
                runCatching {
                    val connection = (URL(url).openConnection() as HttpURLConnection).apply {
                        connectTimeout = 4_000
                        readTimeout = 5_000
                        instanceFollowRedirects = true
                    }
                    try {
                        connection.inputStream.use { input ->
                            val bytes = input.readBytes()
                            val bounds = BitmapFactory.Options().apply { inJustDecodeBounds = true }
                            BitmapFactory.decodeByteArray(bytes, 0, bytes.size, bounds)
                            var sample = 1
                            while (maxOf(bounds.outWidth, bounds.outHeight) / sample > 512) sample *= 2
                            BitmapFactory.decodeByteArray(
                                bytes,
                                0,
                                bytes.size,
                                BitmapFactory.Options().apply { inSampleSize = sample }
                            )
                        }
                    } finally {
                        connection.disconnect()
                    }
                }.getOrNull()
            }
        }
    }
    Box(
        modifier = modifier
            .size(42.dp)
            .clip(CircleShape)
            .background(WasteGreenDark),
        contentAlignment = Alignment.Center
    ) {
        if (photo != null) {
            Image(
                bitmap = photo!!.asImageBitmap(),
                contentDescription = "Foto profil $name",
                contentScale = androidx.compose.ui.layout.ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        } else {
            Text(
                text = initial,
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun SummaryCard(
    value: String,
    label: String,
    accent: Color,
    modifier: Modifier = Modifier,
    containerColor: Color = Color.White,
    icon: ImageVector,
    iconBackgroundColor: Color = accent.copy(alpha = 0.15f),
    iconBackgroundEndColor: Color = iconBackgroundColor,
    onClick: (() -> Unit)? = null
) {
    AppCard(
        modifier = modifier.then(onClick?.let { Modifier.clickable(onClick = it) } ?: Modifier),
        containerColor = containerColor,
        borderColor = accent.copy(alpha = 0.28f),
        accentColor = accent
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 15.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = value,
                    color = accent,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.weight(1f))
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(
                            Brush.linearGradient(
                                listOf(
                                    iconBackgroundColor,
                                    iconBackgroundEndColor
                                )
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = accent,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
            Text(
                text = label,
                color = AppTextMuted,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
