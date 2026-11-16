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
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.StrokeCap
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

private val SplashNight = Color(0xFF07131F)
private val SplashArcGreen = Color(0xFF19D98B)

@Composable
fun SmartWasteSplash() {
    val compactScreen = LocalConfiguration.current.screenWidthDp < 360

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(SplashNight),
        contentAlignment = Alignment.Center
    ) {
        SplashLogoAssembly(compactScreen)
    }
}

@Composable
private fun SplashLogoAssembly(compactScreen: Boolean) {
    val logoSize = if (compactScreen) 152.dp else 172.dp
    val arcProgress = remember { Animatable(0f) }
    val logoProgress = remember { Animatable(0f) }
    val nameProgress = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        arcProgress.animateTo(1f, tween(1_250, easing = FastOutSlowInEasing))
        logoProgress.animateTo(1f, tween(520, easing = FastOutSlowInEasing))
        delay(100)
        nameProgress.animateTo(1f, tween(400, easing = FastOutSlowInEasing))
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
                            color = SplashArcGreen.copy(alpha = 0.12f * arcAlpha),
                            startAngle = startAngle,
                            sweepAngle = sweep,
                            useCenter = false,
                            topLeft = arcBounds,
                            size = arcSize,
                            style = glowStroke
                        )
                        drawArc(
                            color = SplashArcGreen.copy(alpha = 0.30f * arcAlpha),
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
                            color = SplashArcGreen.copy(alpha = 0.38f * arcAlpha),
                            radius = 9.dp.toPx(),
                            center = tip
                        )
                        drawCircle(
                            color = Color.White.copy(alpha = 0.96f * arcAlpha),
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
            color = Color(0xFFF4F7FA),
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
