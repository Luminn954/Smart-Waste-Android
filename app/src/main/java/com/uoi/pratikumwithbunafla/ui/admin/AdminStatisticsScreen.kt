package com.uoi.pratikumwithbunafla.ui.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.uoi.pratikumwithbunafla.data.model.*
import com.uoi.pratikumwithbunafla.ui.components.*
import com.uoi.pratikumwithbunafla.ui.petugas.*
import com.uoi.pratikumwithbunafla.ui.theme.AppBorder
import com.uoi.pratikumwithbunafla.ui.theme.AppSurface
import com.uoi.pratikumwithbunafla.ui.theme.AppText
import com.uoi.pratikumwithbunafla.ui.theme.AppTextMuted
import kotlin.math.roundToInt

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
    onRefresh: () -> Unit = {},
) {
    val periodCount = dailyReports.sumOf { it.totalWaste }
    val periodWeight = dailyReports.sumOf { it.weightKg }
    val monthlyBars = stats.monthly.take(6).reversed()
    val totalTypeWeight = stats.byType.sumOf { it.weightKg }.coerceAtLeast(0.0)
    val chartColors = listOf(LoginAccent, RoleTeal, RolePurple, RoleOrange, RoleGreenDark, RoleNavy)

    AdminPage(
        "stats",
        account,
        onNavigate,
        onLogout,
        isInitialLoading = isInitialLoading,
        isRefreshing = isRefreshing,
        onRefresh = onRefresh,
    ) {
        StaggeredPageEntry("admin-stats", 0) {
            AdminSectionHeading(
                "STATISTIK",
                "Ringkasan laporan",
                "Jumlah, jenis, dan status laporan.",
            )
        }
        if (errorMessage.isNotBlank())
            StaggeredPageEntry("admin-stats", 1) { InlineError(errorMessage) }
        Spacer(modifier = Modifier.height(18.dp))
        StaggeredPageEntry("admin-stats", 2) {
            SectionTitle("Laporan selesai", "Pilih rentang waktu")
        }
        StaggeredPageEntry("admin-stats", 3) {
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                listOf(7, 30, 90).forEach { days ->
                    TextButton(
                        onClick = { onDailyRange(days) },
                        colors =
                            ButtonDefaults.textButtonColors(
                                contentColor =
                                    if (days == dailyRangeDays) LoginAccent else LoginMuted,
                                containerColor =
                                    if (days == dailyRangeDays) RoleAccentSurface else AppSurface,
                            ),
                        shape = RoundedCornerShape(12.dp),
                    ) {
                        Text("$days hari", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                    }
                }
            }
        }
        StaggeredPageEntry("admin-stats", 4) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                SummaryCard(
                    stats.completedReports.toString(),
                    "Selesai total",
                    RoleGreenDark,
                    Modifier.weight(1f),
                    icon = Icons.Filled.CheckCircle,
                    iconBackgroundColor =
                        if (IsNightAdmin) Color(0xFF354A3A) else Color(0xFFE5EAD6),
                )
                SummaryCard(
                    periodCount.toString(),
                    "Selesai $dailyRangeDays hari",
                    LoginAccent,
                    Modifier.weight(1f),
                    icon = Icons.AutoMirrored.Filled.Assignment,
                    iconBackgroundColor =
                        if (IsNightAdmin) Color(0xFF334843) else Color(0xFFDDEBE4),
                )
            }
        }
        StaggeredPageEntry("admin-stats", 5) {
            AppCard(
                modifier = Modifier.fillMaxWidth(),
                containerColor = AppSurface,
                borderColor = AppBorder,
                accentColor = RoleTeal,
            ) {
                Row(
                    modifier =
                        Modifier.fillMaxWidth().padding(horizontal = 14.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            "Berat selesai",
                            color = AppTextMuted,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                        )
                        Text(
                            "${periodWeight.pretty()} kg",
                            color = AppText,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                        )
                        Text(
                            "Dalam ${dailyRangeDays} hari terakhir",
                            color = AppTextMuted,
                            fontSize = 11.sp,
                        )
                    }
                }
            }
        }

        val monthlyRange =
            monthlyBars.firstOrNull()?.let { first ->
                val latest = monthlyBars.last()
                "Enam bulan penuh · ${monthTitle(first.period)}–${monthTitle(latest.period)}"
            } ?: "Enam bulan penuh terakhir"
        StaggeredPageEntry("admin-stats", 6) { SectionTitle("Laporan per bulan", monthlyRange) }
        StaggeredPageEntry("admin-stats", 7) {
            if (monthlyBars.isEmpty()) EmptyAdminState("Belum ada data bulanan.")
            else {
                MonthlyReportsChart(monthlyBars)
            }
        }

        StaggeredPageEntry("admin-stats", 8) {
            SectionTitle("Komposisi jenis sampah", "Porsi berat dari total tercatat")
        }
        if (stats.byType.isEmpty()) {
            StaggeredPageEntry("admin-stats", 9) { EmptyAdminState("Belum ada data jenis sampah.") }
        } else {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                stats.byType
                    .sortedByDescending { it.weightKg }
                    .take(6)
                    .forEachIndexed { index, item ->
                        val accent = chartColors[index % chartColors.size]
                        val share =
                            if (totalTypeWeight > 0.0) {
                                (item.weightKg.coerceAtLeast(0.0) / totalTypeWeight).toFloat()
                            } else {
                                0f
                            }
                        val sharePercent = (share * 100).roundToInt()
                        StaggeredPageEntry("admin-stats", 9 + index) {
                            AppCard(
                                modifier =
                                    Modifier.fillMaxWidth().shadow(4.dp, RoundedCornerShape(18.dp)),
                                containerColor = Color.White,
                                borderColor = null,
                                elevation = 0.dp,
                                shape = RoundedCornerShape(18.dp),
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth().padding(12.dp),
                                    verticalAlignment = Alignment.Top,
                                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                                ) {
                                    Box(
                                        modifier =
                                            Modifier.size(42.dp)
                                                .clip(RoundedCornerShape(13.dp))
                                                .background(
                                                    accent.copy(
                                                        alpha = if (IsNightAdmin) 0.24f else 0.26f,
                                                    ),
                                                ),
                                        contentAlignment = Alignment.Center,
                                    ) {
                                        Icon(
                                            imageVector = wasteTypeIcon(item.type),
                                            contentDescription = null,
                                            tint = accent,
                                            modifier = Modifier.size(21.dp),
                                        )
                                    }
                                    Column(
                                        modifier = Modifier.weight(1f),
                                        verticalArrangement = Arrangement.spacedBy(5.dp),
                                    ) {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Text(
                                                item.type,
                                                modifier = Modifier.weight(1f),
                                                color = AppText,
                                                fontSize = 13.sp,
                                                fontWeight = FontWeight.SemiBold,
                                                maxLines = 1,
                                                overflow = TextOverflow.Ellipsis,
                                            )
                                            Text(
                                                "${item.total} laporan",
                                                color = AppTextMuted,
                                                fontSize = 11.sp,
                                            )
                                        }
                                        Text(
                                            "${item.weightKg.pretty()} kg",
                                            color = AppText,
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Medium,
                                        )
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                                        ) {
                                            DataProgress(
                                                share,
                                                accent,
                                                modifier = Modifier.weight(1f),
                                            )
                                            Text(
                                                "$sharePercent%",
                                                modifier = Modifier.widthIn(min = 32.dp),
                                                color = AppTextMuted,
                                                fontSize = 11.sp,
                                                fontWeight = FontWeight.SemiBold,
                                                textAlign = TextAlign.End,
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
            }
        }

        StaggeredPageEntry("admin-stats", 16) {
            SectionTitle("Aktivitas harian", "Laporan selesai per tanggal")
        }
        StaggeredPageEntry("admin-stats", 17) {
            if (dailyReports.isEmpty()) {
                EmptyAdminState("Belum ada laporan selesai dalam rentang ini.")
            } else {
                AppCard(
                    modifier = Modifier.fillMaxWidth(),
                    containerColor = AppSurface,
                    borderColor = AppBorder,
                ) {
                    Column(
                        modifier =
                            Modifier.fillMaxWidth().padding(horizontal = 13.dp, vertical = 4.dp),
                    ) {
                        dailyReports.take(10).forEach { item ->
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(vertical = 9.dp),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Text(
                                    item.date,
                                    modifier = Modifier.weight(1f),
                                    color = AppText,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.SemiBold,
                                )
                                Text(
                                    buildString {
                                        append(
                                            "${item.totalWaste} laporan · ${item.weightKg.pretty()} kg",
                                        )
                                    },
                                    color = LoginAccent,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
