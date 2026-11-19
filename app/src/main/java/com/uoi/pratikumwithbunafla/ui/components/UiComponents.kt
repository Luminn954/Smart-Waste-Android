package com.uoi.pratikumwithbunafla.ui.components

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.LruCache
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.uoi.pratikumwithbunafla.ui.theme.AppBackground
import com.uoi.pratikumwithbunafla.ui.theme.AppBorder
import com.uoi.pratikumwithbunafla.ui.theme.AppCardSurface
import com.uoi.pratikumwithbunafla.ui.theme.AppSurface
import com.uoi.pratikumwithbunafla.ui.theme.AppText
import com.uoi.pratikumwithbunafla.ui.theme.AppTextMuted
import com.uoi.pratikumwithbunafla.ui.theme.WasteAmber
import com.uoi.pratikumwithbunafla.ui.theme.WasteBlue
import com.uoi.pratikumwithbunafla.ui.theme.WasteCoral
import com.uoi.pratikumwithbunafla.ui.theme.WasteGreen
import com.uoi.pratikumwithbunafla.ui.theme.WasteGreenDark
import com.uoi.pratikumwithbunafla.ui.theme.WasteMint
import com.uoi.pratikumwithbunafla.ui.theme.WastePurple
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private const val AvatarMaxDownloadBytes = 4 * 1024 * 1024
private const val AvatarMaxDecodeDimension = 192
private val avatarBitmapCache =
    object : LruCache<String, Bitmap>(4 * 1024) {
        override fun sizeOf(key: String, value: Bitmap): Int =
            (value.byteCount / 1024).coerceAtLeast(1)
    }

@Composable
fun AppScreen(content: @Composable () -> Unit) {
    val ambientMotion = rememberInfiniteTransition(label = "ambient-motion")
    val topOrbOffset by
        ambientMotion.animateFloat(
            initialValue = -78f,
            targetValue = -70f,
            animationSpec =
                infiniteRepeatable(
                    animation = tween(7200, easing = LinearEasing),
                    repeatMode = RepeatMode.Reverse,
                ),
            label = "top-orb-offset",
        )
    val bottomOrbOffset by
        ambientMotion.animateFloat(
            initialValue = 88f,
            targetValue = 80f,
            animationSpec =
                infiniteRepeatable(
                    animation = tween(8200, easing = LinearEasing),
                    repeatMode = RepeatMode.Reverse,
                ),
            label = "bottom-orb-offset",
        )
    Box(modifier = Modifier.fillMaxSize().background(AppBackground)) {
        Box(
            modifier =
                Modifier.fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors =
                                listOf(
                                    WasteMint.copy(alpha = 0.30f),
                                    WasteBlue.copy(alpha = 0.14f),
                                    AppBackground,
                                    WasteCoral.copy(alpha = 0.16f),
                                ),
                        ),
                    ),
        )
        Box(
            modifier =
                Modifier.align(Alignment.TopEnd)
                    .offset {
                        IntOffset(
                            x = 72.dp.roundToPx(),
                            y = topOrbOffset.dp.roundToPx(),
                        )
                    }
                    .size(210.dp)
                    .clip(CircleShape)
                    .background(WasteBlue.copy(alpha = 0.24f)),
        )
        Box(
            modifier =
                Modifier.align(Alignment.BottomStart)
                    .offset {
                        IntOffset(
                            x = (-82).dp.roundToPx(),
                            y = bottomOrbOffset.dp.roundToPx(),
                        )
                    }
                    .size(190.dp)
                    .clip(CircleShape)
                    .background(WasteAmber.copy(alpha = 0.20f)),
        )
        Box(
            modifier =
                Modifier.align(Alignment.BottomEnd)
                    .offset(x = 16.dp, y = 20.dp)
                    .size(92.dp)
                    .clip(CircleShape)
                    .background(WastePurple.copy(alpha = 0.04f)),
        )
        AnimatedVisibility(
            visible = true,
            modifier = Modifier.fillMaxSize(),
            enter =
                fadeIn(tween(250)) +
                    slideInVertically(
                        initialOffsetY = { it / 24 },
                        animationSpec = tween(250),
                    ),
        ) {
            content()
        }
    }
}

@Composable
fun AppHeader(compact: Boolean = false) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier =
                Modifier.size(if (compact) 34.dp else 38.dp)
                    .clip(RoundedCornerShape(13.dp))
                    .background(WasteGreenDark),
            contentAlignment = Alignment.Center,
        ) {
            androidx.compose.material3.Icon(
                imageVector = Icons.Filled.Eco,
                contentDescription = "Smart Waste",
                tint = Color.White,
                modifier = Modifier.size(if (compact) 19.dp else 21.dp),
            )
        }
        Spacer(modifier = Modifier.width(if (compact) 8.dp else 10.dp))
        Column {
            Text(
                text = "Smart Waste",
                color = AppText,
                fontSize = if (compact) 15.sp else 17.sp,
                fontWeight = FontWeight.Bold,
            )
            if (!compact)
                Text(
                    text = "Panel operasional",
                    color = AppTextMuted,
                    fontSize = 12.sp,
                )
        }
    }
}

@Composable
fun AppCard(
    modifier: Modifier = Modifier,
    containerColor: Color = AppCardSurface,
    borderColor: Color? = AppBorder,
    accentColor: Color? = null,
    elevation: Dp = 1.dp,
    shape: Shape = RoundedCornerShape(24.dp),
    content: @Composable () -> Unit,
) {
    val resolvedSurface =
        if (
            containerColor == Color.White ||
                containerColor == AppSurface ||
                containerColor == AppCardSurface
        ) {
            AppCardSurface
        } else {
            containerColor
        }
    Card(
        modifier = modifier,
        shape = shape,
        colors = CardDefaults.cardColors(containerColor = resolvedSurface),
        border =
            borderColor?.let {
                BorderStroke(
                    1.dp,
                    it.copy(alpha = it.alpha * if (accentColor == null) 0.36f else 0.42f),
                )
            },
        elevation = CardDefaults.cardElevation(defaultElevation = elevation),
    ) {
        content()
    }
}

@Composable
fun AppCardDivider(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxWidth().height(1.dp).background(AppBorder.copy(alpha = 0.38f)),
    )
}

@Composable
fun AppButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    containerColor: Color = WasteGreen,
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier =
            modifier
                .fillMaxWidth()
                .height(56.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(if (enabled) containerColor else AppBorder)
                .border(
                    width = 1.dp,
                    color = if (enabled) containerColor.copy(alpha = 0.30f) else AppBorder,
                    shape = RoundedCornerShape(16.dp),
                ),
        shape = RoundedCornerShape(16.dp),
        colors =
            ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                disabledContainerColor = Color.Transparent,
                disabledContentColor = AppTextMuted,
            ),
    ) {
        Text(
            text = text,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Composable
fun UserAvatar(
    name: String,
    modifier: Modifier = Modifier,
    photoUrl: String? = null,
) {
    val initial = name.trim().firstOrNull()?.uppercase() ?: "A"
    val photo by
        produceState<Bitmap?>(initialValue = null, key1 = photoUrl) {
            val url = photoUrl?.trim()?.takeIf(String::isNotEmpty)
            value = url?.let { imageUrl ->
                avatarBitmapCache.get(imageUrl)
                    ?: loadAvatarBitmap(imageUrl)?.also { bitmap ->
                        avatarBitmapCache.put(imageUrl, bitmap)
                    }
            }
        }
    Box(
        modifier =
            modifier
                .size(42.dp)
                .clip(CircleShape)
                .background(if (photo != null) AppSurface else WasteGreenDark),
        contentAlignment = Alignment.Center,
    ) {
        if (photo != null) {
            Image(
                bitmap = photo!!.asImageBitmap(),
                contentDescription = "Foto profil $name",
                contentScale = androidx.compose.ui.layout.ContentScale.Crop,
                modifier = Modifier.fillMaxSize(),
            )
        } else {
            Text(
                text = initial,
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}

internal suspend fun loadAvatarBitmap(url: String): Bitmap? =
    withContext(Dispatchers.IO) {
        var connection: HttpURLConnection? = null
        try {
            val activeConnection =
                (URL(url).openConnection() as HttpURLConnection).apply {
                    connectTimeout = 4_000
                    readTimeout = 5_000
                    instanceFollowRedirects = true
                    setRequestProperty("Accept", "image/*")
                    setRequestProperty("Connection", "close")
                }
            connection = activeConnection
            if (activeConnection.responseCode !in 200..299) return@withContext null
            val bytes =
                activeConnection.inputStream.use { input ->
                    val output = ByteArrayOutputStream()
                    val buffer = ByteArray(8 * 1024)
                    var totalBytes = 0
                    while (true) {
                        val count = input.read(buffer)
                        if (count < 0) break
                        totalBytes += count
                        if (totalBytes > AvatarMaxDownloadBytes)
                            throw IOException("Foto profil terlalu besar.")
                        output.write(buffer, 0, count)
                    }
                    output.toByteArray()
                }
            val bounds = BitmapFactory.Options().apply { inJustDecodeBounds = true }
            BitmapFactory.decodeByteArray(bytes, 0, bytes.size, bounds)
            if (bounds.outWidth <= 0 || bounds.outHeight <= 0) return@withContext null

            var sample = 1
            while (maxOf(bounds.outWidth, bounds.outHeight) / sample > AvatarMaxDecodeDimension) {
                sample *= 2
            }
            BitmapFactory.decodeByteArray(
                bytes,
                0,
                bytes.size,
                BitmapFactory.Options().apply { inSampleSize = sample },
            )
        } catch (cancelled: CancellationException) {
            throw cancelled
        } catch (_: Exception) {
            null
        } finally {
            connection?.disconnect()
        }
    }

internal sealed class PhotoPreviewState {
    data object Loading : PhotoPreviewState()

    data class Ready(val bitmap: Bitmap) : PhotoPreviewState()

    data object Failed : PhotoPreviewState()
}

@Composable
fun InAppPhotoViewerDialog(photoUrl: String, onDismiss: () -> Unit) {
    var retryKey by remember(photoUrl) { mutableStateOf(0) }
    val photoState by
        produceState<PhotoPreviewState>(
            initialValue = PhotoPreviewState.Loading,
            key1 = photoUrl,
            key2 = retryKey,
        ) {
            value = PhotoPreviewState.Loading
            value =
                withContext(Dispatchers.IO) {
                    try {
                        PhotoPreviewState.Ready(downloadPhotoForPreview(photoUrl))
                    } catch (cancelled: CancellationException) {
                        throw cancelled
                    } catch (_: Exception) {
                        PhotoPreviewState.Failed
                    }
                }
        }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false),
    ) {
        Surface(modifier = Modifier.fillMaxSize(), color = Color(0xFF101512)) {
            Box(modifier = Modifier.fillMaxSize()) {
                when (val state = photoState) {
                    PhotoPreviewState.Loading ->
                        Column(
                            modifier = Modifier.align(Alignment.Center),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(14.dp),
                        ) {
                            CircularProgressIndicator(color = WasteMint, strokeWidth = 2.5.dp)
                            Text("Memuat foto…", color = Color.White, fontSize = 14.sp)
                        }

                    is PhotoPreviewState.Ready ->
                        Image(
                            bitmap = state.bitmap.asImageBitmap(),
                            contentDescription = "Foto bukti laporan",
                            contentScale = androidx.compose.ui.layout.ContentScale.Fit,
                            modifier = Modifier.fillMaxSize().padding(top = 72.dp, bottom = 24.dp),
                        )

                    PhotoPreviewState.Failed ->
                        Column(
                            modifier = Modifier.align(Alignment.Center).padding(horizontal = 32.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            Text(
                                "Foto belum bisa dimuat",
                                color = Color.White,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                            )
                            Text(
                                "Periksa koneksi server, lalu coba lagi.",
                                color = Color(0xFFCBD3CE),
                                fontSize = 13.sp,
                            )
                            TextButton(onClick = { retryKey++ }) {
                                Text(
                                    "Coba lagi",
                                    color = WasteMint,
                                    fontWeight = FontWeight.SemiBold,
                                )
                            }
                        }
                }

                Row(
                    modifier =
                        Modifier.align(Alignment.TopCenter)
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.surface)
                            .statusBarsPadding()
                            .padding(horizontal = 8.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    IconButton(onClick = onDismiss) {
                        Icon(
                            imageVector = Icons.Rounded.Close,
                            contentDescription = "Tutup foto",
                            tint = MaterialTheme.colorScheme.onSurface,
                        )
                    }
                    Text(
                        text = "Foto bukti",
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                    )
                }
            }
        }
    }
}

internal fun downloadPhotoForPreview(photoUrl: String): Bitmap {
    val connection =
        (URL(photoUrl).openConnection() as HttpURLConnection).apply {
            connectTimeout = 8_000
            readTimeout = 20_000
            instanceFollowRedirects = true
            setRequestProperty("Accept", "image/*")
            setRequestProperty("Connection", "close")
        }
    try {
        val responseCode = connection.responseCode
        if (responseCode !in 200..299) throw IOException("Foto tidak tersedia ($responseCode).")
        val bytes =
            connection.inputStream.use { input ->
                val output = ByteArrayOutputStream()
                val buffer = ByteArray(8 * 1024)
                var total = 0
                while (true) {
                    val count = input.read(buffer)
                    if (count < 0) break
                    total += count
                    if (total > 16 * 1024 * 1024) throw IOException("Ukuran foto terlalu besar.")
                    output.write(buffer, 0, count)
                }
                output.toByteArray()
            }
        val bounds = BitmapFactory.Options().apply { inJustDecodeBounds = true }
        BitmapFactory.decodeByteArray(bytes, 0, bytes.size, bounds)
        if (bounds.outWidth <= 0 || bounds.outHeight <= 0)
            throw IOException("File bukan foto yang valid.")
        var sample = 1
        while (maxOf(bounds.outWidth, bounds.outHeight) / sample > 2560) sample *= 2
        return BitmapFactory.decodeByteArray(
            bytes,
            0,
            bytes.size,
            BitmapFactory.Options().apply { inSampleSize = sample },
        ) ?: throw IOException("Foto tidak dapat dibaca.")
    } finally {
        connection.disconnect()
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
    onClick: (() -> Unit)? = null,
) {
    AppCard(
        modifier = modifier.then(onClick?.let { Modifier.clickable(onClick = it) } ?: Modifier),
        containerColor = containerColor,
        borderColor = accent.copy(alpha = 0.28f),
        accentColor = accent,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 15.dp, vertical = 14.dp),
            verticalArrangement = Arrangement.spacedBy(11.dp),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(9.dp),
            ) {
                Box(
                    modifier =
                        Modifier.size(36.dp)
                            .clip(RoundedCornerShape(13.dp))
                            .background(iconBackgroundColor),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = accent,
                        modifier = Modifier.size(18.dp),
                    )
                }
                Text(
                    text = label,
                    modifier = Modifier.weight(1f),
                    color = AppText,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
            }
            AppCardDivider()
            Text(
                text = value,
                color = accent,
                fontSize = 23.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 27.sp,
            )
        }
    }
}
