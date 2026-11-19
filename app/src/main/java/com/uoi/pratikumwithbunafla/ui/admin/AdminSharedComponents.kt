package com.uoi.pratikumwithbunafla.ui.admin

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Devices
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.Inbox
import androidx.compose.material.icons.filled.LocalDrink
import androidx.compose.material.icons.filled.Recycling
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.uoi.pratikumwithbunafla.data.model.*
import com.uoi.pratikumwithbunafla.domain.WasteFlow
import com.uoi.pratikumwithbunafla.ui.components.*
import com.uoi.pratikumwithbunafla.ui.petugas.*
import com.uoi.pratikumwithbunafla.ui.theme.AppBorder
import com.uoi.pratikumwithbunafla.ui.theme.AppSurface
import com.uoi.pratikumwithbunafla.ui.theme.AppText
import com.uoi.pratikumwithbunafla.ui.theme.AppTextMuted
import com.uoi.pratikumwithbunafla.ui.theme.InterFont
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.log10
import kotlin.math.pow

@Composable
internal fun FormField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    enabled: Boolean = true,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        enabled = enabled,
        modifier = Modifier.fillMaxWidth(),
        label = { Text(label, fontSize = 11.sp) },
        singleLine = true,
        shape = RoundedCornerShape(13.dp),
        colors =
            OutlinedTextFieldDefaults.colors(
                focusedTextColor = LoginInk,
                unfocusedTextColor = LoginInk,
                focusedContainerColor = AppSurface,
                unfocusedContainerColor = AppSurface,
                focusedBorderColor = LoginAccent,
                unfocusedBorderColor = AppBorder,
                focusedLabelColor = LoginAccent,
                unfocusedLabelColor = LoginMuted,
                cursorColor = LoginAccent,
            ),
    )
}

@Composable
internal fun StatusPill(status: String, color: Color) {
    val textColor =
        when {
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
        modifier =
            Modifier.clip(RoundedCornerShape(50.dp))
                .background(color.copy(alpha = 0.12f))
                .padding(horizontal = 9.dp, vertical = 6.dp),
    ) {
        Text(
            WasteFlow.label(status),
            color = textColor,
            fontFamily = InterFont,
            fontSize = 12.sp,
            lineHeight = 15.sp,
            fontWeight = FontWeight.SemiBold,
        )
    }
}

@Composable
internal fun reportAccent(status: String): Color =
    when (status) {
        WasteFlow.WAITING -> RoleOrange
        WasteFlow.ASSIGNED -> RolePurple
        WasteFlow.TRANSPORTED -> RoleTeal
        WasteFlow.PROCESSING -> RoleNavy
        WasteFlow.DONE -> RoleGreen
        else -> RoleRed
    }

@Composable
internal fun SectionTitle(title: String, subtitle: String? = null) {
    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Text(
            title,
            color = AppText,
            fontSize = 18.sp,
            lineHeight = 24.sp,
            fontWeight = FontWeight.SemiBold,
        )
        if (!subtitle.isNullOrBlank()) {
            Text(
                subtitle,
                color = AppTextMuted,
                fontSize = 13.sp,
                lineHeight = 19.sp,
            )
        }
    }
}

@Composable
internal fun SoftProgress(progress: Float) {
    Box(
        modifier =
            Modifier.fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(50.dp))
                .background(RoleAccentSurface),
    ) {
        Box(
            modifier =
                Modifier.fillMaxWidth(progress.coerceIn(0f, 1f))
                    .height(8.dp)
                    .background(LoginAccent),
        )
    }
}

@Composable
internal fun wasteTypeIcon(type: String): ImageVector =
    when (type.trim().lowercase()) {
        "organik" -> Icons.Filled.Eco
        "kertas" -> Icons.Filled.Description
        "kaca" -> Icons.Filled.LocalDrink
        "plastik" -> Icons.Filled.Recycling
        "elektronik" -> Icons.Filled.Devices
        else -> Icons.Filled.Category
    }

@Composable
internal fun DataProgress(progress: Float, accent: Color, modifier: Modifier = Modifier) {
    Box(
        modifier =
            modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(50.dp))
                .background(RoleAccentSurface),
    ) {
        Box(
            modifier =
                Modifier.fillMaxWidth(progress.coerceIn(0f, 1f))
                    .height(8.dp)
                    .clip(RoundedCornerShape(50.dp))
                    .background(accent),
        )
    }
}

@Composable
internal fun InlineError(message: String) {
    AppCard(
        modifier = Modifier.fillMaxWidth(),
        containerColor = RoleRed.copy(alpha = 0.08f),
        borderColor = RoleRed.copy(alpha = 0.24f),
        accentColor = RoleRed,
    ) {
        Text(
            message,
            modifier = Modifier.padding(12.dp),
            color = RoleRed,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Composable
internal fun EmptyAdminState(message: String, useCompactCardStyle: Boolean = false) {
    AppCard(
        modifier = Modifier.fillMaxWidth(),
        containerColor = if (useCompactCardStyle) AppSurface.copy(alpha = 0.98f) else AppSurface,
        borderColor = if (useCompactCardStyle) null else AppBorder,
        elevation = if (useCompactCardStyle) 0.dp else 1.dp,
        shape = if (useCompactCardStyle) RoundedCornerShape(18.dp) else RoundedCornerShape(24.dp),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(23.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(
                Icons.Filled.Inbox,
                contentDescription = null,
                tint = LoginAccent,
                modifier = Modifier.size(28.dp),
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(message, color = AppTextMuted, fontSize = 11.sp)
        }
    }
}

internal fun Double.pretty(): String {
    if (!isFinite()) return "0"
    val locale = java.util.Locale.forLanguageTag("id-ID")
    val symbols = java.text.DecimalFormatSymbols.getInstance(locale)
    return java.text.DecimalFormat("#,##0.#", symbols).format(this)
}

internal fun String.displayPersonName(): String {
    val value = trim().replace(Regex("\\s+"), " ")
    if (value.isBlank()) return "—"
    val locale = java.util.Locale.forLanguageTag("id-ID")
    return value.split(' ').joinToString(" ") { word ->
        word.split('-').joinToString("-") { part ->
            part.lowercase(locale).replaceFirstChar { first -> first.titlecase(locale) }
        }
    }
}

internal fun String.normalizePersonNameInput(): String {
    val locale = java.util.Locale.forLanguageTag("id-ID")
    return Regex("\\S+").replace(this) { match ->
        match.value.split('-').joinToString("-") { part ->
            part.lowercase(locale).replaceFirstChar { first -> first.titlecase(locale) }
        }
    }
}

internal fun String.reporterDisplayName(): String =
    when (trim().lowercase(java.util.Locale.forLanguageTag("id-ID"))) {
        "admin smart waste",
        "admin" -> "Admin"
        "user smart waste",
        "user" -> "Warga"
        else -> displayPersonName()
    }

internal fun WasteRecord.assignmentSummary(): String = buildList {
    workerName
        .trim()
        .takeIf { it.isNotBlank() }
        ?.let {
            add("Petugas ${it.displayPersonName()}")
        }
    vehiclePlate
        .trim()
        .takeIf { it.isNotBlank() }
        ?.let {
            add("Armada ${it.uppercase(java.util.Locale.forLanguageTag("id-ID"))}")
        }
    tpaName
        .trim()
        .takeIf { it.isNotBlank() }
        ?.let {
            add("TPA ${it.displayPersonName()}")
        }
}
    .joinToString("  ·  ")

internal fun String.toDisplayDateTime(): String {
    val raw = trim()
    if (raw.isBlank()) return raw
    val patterns =
        listOf(
            "yyyy-MM-dd'T'HH:mm:ss.SSSXXX",
            "yyyy-MM-dd'T'HH:mm:ssXXX",
            "yyyy-MM-dd HH:mm:ss",
            "yyyy-MM-dd HH:mm",
            "yyyy-MM-dd",
        )
    val parsed =
        patterns.firstNotNullOfOrNull { pattern ->
            runCatching {
                java.text
                    .SimpleDateFormat(pattern, java.util.Locale.US)
                    .apply {
                        isLenient = false
                    }
                    .parse(raw)
            }
                .getOrNull()
        } ?: return raw
    val outputPattern = if (raw.length <= 10) "d MMM yyyy" else "d MMM yyyy, HH.mm"
    return java.text
        .SimpleDateFormat(
            outputPattern,
            java.util.Locale.forLanguageTag("id-ID"),
        )
        .format(parsed)
}

@Composable
internal fun MonthlyReportsChart(months: List<MonthlyStat>) {
    val chartMax = months.maxOfOrNull { it.total }?.coerceAtLeast(1) ?: 1
    val (axisMax, axisStep) = monthlyAxisScale(chartMax)
    val highlightedIndex = months.lastIndex
    val highlighted = months[highlightedIndex]
    val previous = months.getOrNull(highlightedIndex - 1)
    val comparisonPercent =
        previous
            ?.takeIf { it.total > 0 }
            ?.let {
                (highlighted.total - it.total) * 100.0 / it.total
            }
    val hasMonthlyReports = months.any { it.total > 0 }
    val localeId = java.util.Locale.forLanguageTag("id-ID")
    val changeLabel = comparisonPercent?.let { String.format(localeId, "%+.1f%%", it) }
    val trendLabel =
        when {
            !hasMonthlyReports -> "Belum ada data"
            comparisonPercent == null && previous == null -> "—"
            comparisonPercent == null && highlighted.total > 0 -> "Baru"
            comparisonPercent == null -> "Baru"
            comparisonPercent > 0 -> "Naik"
            comparisonPercent < 0 -> "Turun"
            else -> "Tetap"
        }
    val comparisonDetail =
        when {
            !hasMonthlyReports -> "Enam bulan terakhir"
            previous == null -> "Belum ada pembanding"
            previous.total == 0 && highlighted.total > 0 -> "Bulan lalu 0 laporan"
            previous.total == 0 -> "Bulan lalu 0 laporan"
            else -> "Dibanding ${monthTitle(previous.period)}"
        }
    val night = IsNightAdmin
    val mutedBar = if (night) Color(0xFF4F6F5E) else Color(0xFFA7C9B6)
    val activeBar = if (night) Color(0xFF398A65) else Color(0xFF087D50)
    val trendColor =
        when {
            !hasMonthlyReports -> AppTextMuted
            comparisonPercent != null && comparisonPercent < 0 -> RoleRed
            comparisonPercent == null && previous?.total == 0 && highlighted.total == 0 ->
                AppTextMuted
            else -> activeBar
        }
    val chartHeight = 142.dp
    val plotTop = 36.dp
    val plotHeight = chartHeight - plotTop
    val gridColor = AppTextMuted.copy(alpha = if (night) 0.28f else 0.20f)

    AppCard(
        modifier = Modifier.fillMaxWidth(),
        containerColor = AppSurface,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 14.dp, vertical = 14.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                "Laporan masuk",
                color = AppTextMuted,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        monthTitle(highlighted.period),
                        color = AppText,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                    )
                    Text(
                        "${highlighted.total} laporan",
                        color = AppTextMuted,
                        fontSize = 12.sp,
                    )
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        trendLabel,
                        color = trendColor,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                    )
                    Text(
                        comparisonDetail,
                        color = AppTextMuted,
                        fontSize = 11.sp,
                        textAlign = TextAlign.End,
                    )
                }
            }
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.Top) {
                Column(modifier = Modifier.width(34.dp).height(chartHeight)) {
                    Spacer(modifier = Modifier.height(plotTop - 6.dp))
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.SpaceBetween,
                    ) {
                        (5 downTo 0).forEach { tick ->
                            Text(
                                (axisStep * tick).toChartAxisLabel(),
                                color = AppTextMuted,
                                fontSize = 11.sp,
                                lineHeight = 13.sp,
                                maxLines = 1,
                            )
                        }
                    }
                }
                Box(
                    modifier = Modifier.weight(1f).height(chartHeight),
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
                                pathEffect =
                                    PathEffect.dashPathEffect(
                                        floatArrayOf(5.dp.toPx(), 5.dp.toPx()),
                                    ),
                            )
                        }
                    }
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                    ) {
                        months.forEachIndexed { index, month ->
                            val ratio = (month.total.toFloat() / axisMax).coerceIn(0f, 1f)
                            val barHeight = plotHeight * ratio
                            val isHighlighted = index == highlightedIndex
                            val barColor = if (isHighlighted) activeBar else mutedBar
                            Box(
                                modifier = Modifier.weight(1f).fillMaxHeight(),
                            ) {
                                if (isHighlighted && changeLabel != null) {
                                    Column(
                                        modifier =
                                            Modifier.align(Alignment.TopCenter)
                                                .offset(
                                                    y =
                                                        (chartHeight - barHeight - 34.dp)
                                                            .coerceAtLeast(0.dp),
                                                ),
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                    ) {
                                        Text(
                                            changeLabel,
                                            color = Color.White,
                                            fontSize = 10.sp,
                                            lineHeight = 12.sp,
                                            fontWeight = FontWeight.Bold,
                                            modifier =
                                                Modifier.clip(CircleShape)
                                                    .background(activeBar)
                                                    .padding(horizontal = 8.dp, vertical = 4.dp),
                                        )
                                        Spacer(modifier = Modifier.height(2.dp))
                                        Box(
                                            modifier =
                                                Modifier.size(11.dp)
                                                    .clip(CircleShape)
                                                    .background(activeBar)
                                                    .border(2.dp, AppSurface, CircleShape),
                                        )
                                    }
                                }
                                if (month.total > 0 || isHighlighted) {
                                    Box(
                                        modifier =
                                            Modifier.align(Alignment.BottomCenter)
                                                .widthIn(max = 40.dp)
                                                .fillMaxWidth(0.64f)
                                                .height(
                                                    (if (barHeight > 0.dp) barHeight else 3.dp)
                                                        .coerceAtLeast(3.dp),
                                                )
                                                .clip(CircleShape)
                                                .background(barColor)
                                                .drawWithContent {
                                                    drawContent()
                                                    val spacing = 10.dp.toPx()
                                                    val stroke = 1.dp.toPx()
                                                    var startX = -size.height
                                                    while (startX < size.width) {
                                                        drawLine(
                                                            color =
                                                                Color.White.copy(
                                                                    alpha =
                                                                        if (isHighlighted) 0.20f
                                                                        else 0.30f,
                                                                ),
                                                            start = Offset(startX, size.height),
                                                            end = Offset(startX + size.height, 0f),
                                                            strokeWidth = stroke,
                                                        )
                                                        startX += spacing
                                                    }
                                                },
                                    )
                                }
                            }
                        }
                    }
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Spacer(modifier = Modifier.width(34.dp))
                months.forEach { month ->
                    Text(
                        shortMonth(month.period)
                            .uppercase(java.util.Locale.forLanguageTag("id-ID")),
                        modifier = Modifier.weight(1f),
                        color = AppTextMuted,
                        fontSize = 11.sp,
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                    )
                }
            }
            if (!hasMonthlyReports) {
                Text(
                    "Belum ada laporan pada enam bulan penuh ini.",
                    color = AppTextMuted,
                    fontSize = 12.sp,
                )
            }
        }
    }
}

internal fun monthlyAxisScale(value: Int): Pair<Int, Int> {
    val rawStep = ceil(value.coerceAtLeast(1) / 5.0).toInt()
    val magnitude = 10.0.pow(floor(log10(rawStep.toDouble()))).toInt().coerceAtLeast(1)
    val normalized = rawStep.toDouble() / magnitude
    val step =
        when {
            normalized <= 1.0 -> magnitude
            normalized <= 2.0 -> 2 * magnitude
            normalized <= 5.0 -> 5 * magnitude
            else -> 10 * magnitude
        }
    return step * 5 to step
}

internal fun Int.toChartAxisLabel(): String =
    when {
        this >= 1_000 && this % 1_000 == 0 -> "${this / 1_000}k"
        this >= 1_000 -> String.format(java.util.Locale.US, "%.1fk", this / 1_000.0)
        else -> toString()
    }

internal fun shortMonth(period: String): String {
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
            "Des",
        )
        .getOrNull(month - 1) ?: period.takeLast(2)
}

internal fun monthTitle(period: String): String =
    "${shortMonth(period)} ${period.substringBefore('-', period)}"
