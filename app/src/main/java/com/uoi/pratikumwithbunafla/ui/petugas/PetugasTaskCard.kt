package com.uoi.pratikumwithbunafla.ui.petugas

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.uoi.pratikumwithbunafla.data.model.*
import com.uoi.pratikumwithbunafla.domain.WasteFlow
import com.uoi.pratikumwithbunafla.ui.admin.*
import com.uoi.pratikumwithbunafla.ui.components.*
import com.uoi.pratikumwithbunafla.ui.theme.AppBorder
import com.uoi.pratikumwithbunafla.ui.theme.AppSurface
import com.uoi.pratikumwithbunafla.ui.theme.AppSurfaceMuted
import com.uoi.pratikumwithbunafla.ui.theme.AppText
import com.uoi.pratikumwithbunafla.ui.theme.AppTextMuted
import com.uoi.pratikumwithbunafla.ui.theme.InterFont
import com.uoi.pratikumwithbunafla.ui.theme.LocalSmartWasteAppearance

@Composable
internal fun PetugasTaskCard(
    report: WasteRecord,
    onOpenPhoto: (String) -> Unit,
    compactHistory: Boolean = false,
    dense: Boolean = false,
    onMarkTransported: ((WasteRecord) -> Unit)? = null,
    isTransporting: Boolean = false,
    isActionEnabled: Boolean = true,
    onComplete: ((WasteRecord) -> Unit)? = null,
    onSendLocationEvidence: ((WasteRecord) -> Unit)? = null,
    isSendingLocationEvidence: Boolean = false,
    onArchive: ((WasteRecord) -> Unit)? = null,
) {
    val isComplete = report.status == WasteFlow.DONE
    val isRejected = report.status == WasteFlow.REJECTED
    val isDark = LocalSmartWasteAppearance.current.darkMode
    val needsRevision = report.completionStatus.equals("perlu_perbaikan", ignoreCase = true)
    val waitingForAdmin = report.completionStatus.equals("menunggu", ignoreCase = true)
    val statusAccent =
        when (report.status) {
            WasteFlow.DONE -> if (isDark) Color(0xFF91C59B) else RoleGreen
            WasteFlow.REJECTED -> if (isDark) Color(0xFFE3A19A) else RoleRed
            WasteFlow.TRANSPORTED -> if (isDark) Color(0xFF82C7B9) else RoleTeal
            else -> if (isDark) Color(0xFFC2CF91) else LoginAccent
        }
    val guidanceAccent =
        when {
            needsRevision -> if (isDark) Color(0xFFE3A19A) else RoleRed
            waitingForAdmin -> if (isDark) Color(0xFFE0BB76) else RoleOrange
            else -> statusAccent
        }
    val guidanceTitle =
        when {
            needsRevision -> "Perlu perbaikan"
            waitingForAdmin -> "Menunggu pemeriksaan Admin"
            isComplete -> "Pekerjaan selesai"
            isRejected -> "Laporan ditolak"
            else -> "Langkah berikutnya"
        }
    val guidanceMessage =
        when {
            needsRevision ->
                "${report.completionReviewNote.ifBlank { "Admin meminta foto bukti diperbaiki." }} " +
                    "Kirim ulang setelah diperbaiki."
            waitingForAdmin -> "Foto terkirim. Tunggu persetujuan Admin."
            isComplete -> "Disetujui Admin. Tugas selesai."
            isRejected ->
                report.workerDecisionNote.ifBlank {
                    "Laporan tidak diteruskan ke proses pengangkutan."
                }
            report.status == WasteFlow.TRANSPORTED -> "Antar ke TPA, lalu kirim foto bukti."
            else -> "Muat sampah, lalu mulai angkut."
        }
    val hasEvidencePhotos =
        !report.photoUrl.isNullOrBlank() ||
            !report.locationEvidencePhotoUrl.isNullOrBlank() ||
            !report.completionProofPhotoUrl.isNullOrBlank()
    val canSendLocationEvidence =
        !compactHistory &&
            (report.status == WasteFlow.ASSIGNED || report.status == WasteFlow.TRANSPORTED) &&
            report.locationEvidencePhotoUrl.isNullOrBlank() &&
            onSendLocationEvidence != null

    AppCard(
        modifier = Modifier.fillMaxWidth(),
        containerColor = AppSurface,
        borderColor = AppBorder,
        accentColor = statusAccent,
    ) {
        Column(
            modifier =
                Modifier.fillMaxWidth()
                    .padding(
                        horizontal = if (compactHistory || dense) 14.dp else 16.dp,
                        vertical =
                            when {
                                compactHistory -> 13.dp
                                dense -> 12.dp
                                else -> 15.dp
                            },
                    ),
            verticalArrangement =
                Arrangement.spacedBy(
                    when {
                        compactHistory -> 8.dp
                        dense -> 7.dp
                        else -> 10.dp
                    },
                ),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                Box(
                    modifier =
                        Modifier.size(if (dense) 32.dp else 36.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(statusAccent.copy(alpha = 0.12f)),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        Icons.Filled.LocalShipping,
                        contentDescription = null,
                        tint = statusAccent,
                        modifier = Modifier.size(if (dense) 16.dp else 18.dp),
                    )
                }
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(2.dp),
                ) {
                    Text(
                        report.code,
                        color = AppText,
                        fontSize = 14.sp,
                        lineHeight = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                    )
                    Text(
                        "${report.type} · ${report.weightKg.pretty()} kg",
                        color = AppTextMuted,
                        fontSize = 12.sp,
                        lineHeight = 16.sp,
                    )
                }
                StatusPill(report.status, statusAccent)
            }
            Row(
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Icon(
                    Icons.Filled.Place,
                    contentDescription = null,
                    tint = statusAccent,
                    modifier = Modifier.size(15.dp),
                )
                Text(
                    report.location.ifBlank { "Lokasi belum dicatat" },
                    modifier = Modifier.weight(1f),
                    color = AppTextMuted,
                    fontSize = 12.sp,
                    lineHeight = 17.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
            }
            if (!isRejected && dense) {
                val routeSummary =
                    "${report.vehiclePlate.ifBlank { "Armada belum ditetapkan" }}  →  " +
                        report.tpaName.ifBlank { "TPA belum ditetapkan" }
                Text(
                    routeSummary,
                    color = AppTextMuted,
                    fontSize = 12.sp,
                    lineHeight = 17.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            } else if (!isRejected) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(2.dp),
                    ) {
                        Text(
                            "Armada",
                            color = AppTextMuted,
                            fontSize = 11.sp,
                            lineHeight = 14.sp,
                            fontWeight = FontWeight.Medium,
                        )
                        Text(
                            report.vehiclePlate.ifBlank { "Belum ditetapkan" },
                            color = AppText,
                            fontSize = 12.sp,
                            lineHeight = 17.sp,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(2.dp),
                    ) {
                        Text(
                            "TPA",
                            color = AppTextMuted,
                            fontSize = 11.sp,
                            lineHeight = 14.sp,
                            fontWeight = FontWeight.Medium,
                        )
                        Text(
                            report.tpaName.ifBlank { "Belum ditetapkan" },
                            color = AppText,
                            fontSize = 12.sp,
                            lineHeight = 17.sp,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                }
            }
            if (!isRejected && !dense) AppCardDivider()
            if (compactHistory && !isComplete) {
                val historyNote =
                    when {
                        report.workerDecisionNote.isNotBlank() -> report.workerDecisionNote
                        else -> "Laporan tidak diteruskan ke pengangkutan"
                    }
                Text(
                    historyNote,
                    color = if (isComplete) statusAccent else AppTextMuted,
                    fontSize = 13.sp,
                    lineHeight = 19.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
            } else if (!compactHistory && (!dense || needsRevision)) {
                if (dense) {
                    Text(
                        report.completionReviewNote.ifBlank {
                            "Perbaiki foto bukti sebelum dikirim ulang."
                        },
                        color = guidanceAccent,
                        fontSize = 12.sp,
                        lineHeight = 17.sp,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                    )
                } else {
                    Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
                        Text(
                            guidanceTitle,
                            color = guidanceAccent,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                        )
                        Text(
                            guidanceMessage,
                            color = AppTextMuted,
                            fontSize = 12.sp,
                            lineHeight = 17.sp,
                            maxLines = 3,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                }
            }
            if (hasEvidencePhotos || canSendLocationEvidence) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(if (dense) 1.dp else 3.dp),
                ) {
                    if (!dense) {
                        Text(
                            if (compactHistory) "Foto" else "Foto bukti",
                            color = AppTextMuted,
                            fontSize = 11.sp,
                            lineHeight = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                        )
                    }
                    if (hasEvidencePhotos) {
                        report.locationEvidenceSubmittedAt
                            .takeIf {
                                !dense &&
                                    it.isNotBlank() &&
                                    !report.locationEvidencePhotoUrl.isNullOrBlank()
                            }
                            ?.let {
                                Text(
                                    "Progres · ${it.toDisplayDateTime()}",
                                    color = AppTextMuted,
                                    fontSize = 11.sp,
                                    lineHeight = 14.sp,
                                    fontFamily = InterFont,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                )
                            }
                        Row(
                            modifier =
                                Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                        ) {
                            if (!report.photoUrl.isNullOrBlank()) {
                                TextButton(
                                    onClick = { onOpenPhoto(report.photoUrl) },
                                    contentPadding =
                                        androidx.compose.foundation.layout.PaddingValues(
                                            horizontal = 6.dp,
                                            vertical = 0.dp,
                                        ),
                                ) {
                                    Text(
                                        if (dense) "Foto warga" else "Awal · warga",
                                        color = statusAccent,
                                        fontSize = 12.sp,
                                    )
                                }
                            }
                            if (!report.locationEvidencePhotoUrl.isNullOrBlank()) {
                                TextButton(
                                    onClick = { onOpenPhoto(report.locationEvidencePhotoUrl) },
                                    contentPadding =
                                        androidx.compose.foundation.layout.PaddingValues(
                                            horizontal = 6.dp,
                                            vertical = 0.dp,
                                        ),
                                ) {
                                    Text(
                                        if (dense) "Foto lokasi" else "Lokasi · petugas",
                                        color = LoginAccent,
                                        fontSize = 12.sp,
                                        fontFamily = InterFont,
                                    )
                                }
                            }
                            if (!report.completionProofPhotoUrl.isNullOrBlank()) {
                                TextButton(
                                    onClick = { onOpenPhoto(report.completionProofPhotoUrl) },
                                    contentPadding =
                                        androidx.compose.foundation.layout.PaddingValues(
                                            horizontal = 6.dp,
                                            vertical = 0.dp,
                                        ),
                                ) {
                                    Text(
                                        if (dense) "Foto petugas" else "Akhir · petugas",
                                        color = if (isDark) Color(0xFF82C7B9) else RoleTeal,
                                        fontSize = 12.sp,
                                    )
                                }
                            }
                        }
                    }
                    if (canSendLocationEvidence) {
                        Row(
                            modifier =
                                Modifier.fillMaxWidth()
                                    .heightIn(min = 44.dp)
                                    .clip(RoundedCornerShape(13.dp))
                                    .background(AppSurfaceMuted)
                                    .clickable(
                                        enabled = !isSendingLocationEvidence,
                                        role = Role.Button,
                                        onClick = { onSendLocationEvidence(report) },
                                    )
                                    .padding(horizontal = 12.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            Icon(
                                Icons.Filled.PhotoCamera,
                                contentDescription = null,
                                tint = LoginAccent,
                                modifier = Modifier.size(16.dp),
                            )
                            Text(
                                if (isSendingLocationEvidence) "Mengirim foto…"
                                else "Kirim foto di lokasi",
                                color = LoginAccent,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                fontFamily = InterFont,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                            )
                        }
                    }
                }
            }
            if (compactHistory && onArchive != null) {
                TextButton(
                    onClick = { onArchive(report) },
                    modifier = Modifier.align(Alignment.End),
                    contentPadding =
                        androidx.compose.foundation.layout.PaddingValues(
                            horizontal = 8.dp,
                            vertical = 1.dp,
                        ),
                ) {
                    Text(
                        "Arsipkan",
                        color = LoginAccent,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                    )
                }
            }
            if (!compactHistory && (onMarkTransported != null || onComplete != null)) {
                AppCardDivider()
            }
            when {
                report.status == WasteFlow.ASSIGNED && onMarkTransported != null ->
                    Button(
                        onClick = { onMarkTransported(report) },
                        modifier = Modifier.fillMaxWidth().height(46.dp),
                        enabled = isActionEnabled && !isTransporting && !isSendingLocationEvidence,
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = LoginAccent),
                    ) {
                        Text(
                            if (isTransporting) "Memperbarui status…" else "Mulai pengangkutan",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                        )
                    }

                report.status == WasteFlow.TRANSPORTED && onComplete != null && waitingForAdmin ->
                    Text(
                        "Bukti sudah dikirim. Menunggu pemeriksaan Admin.",
                        color = guidanceAccent,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                    )

                report.status == WasteFlow.TRANSPORTED && onComplete != null ->
                    Button(
                        onClick = { onComplete(report) },
                        modifier = Modifier.fillMaxWidth().height(46.dp),
                        enabled = isActionEnabled && !isSendingLocationEvidence,
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = RoleGreenDark),
                    ) {
                        Text(
                            if (needsRevision) "Kirim ulang bukti TPA"
                            else "Konfirmasi tiba di TPA",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                        )
                    }
            }
        }
    }
}
