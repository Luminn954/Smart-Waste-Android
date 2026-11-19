package com.uoi.pratikumwithbunafla.ui.petugas

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.SwitchAccount
import androidx.compose.material.icons.rounded.NightsStay
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.WbSunny
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import com.uoi.pratikumwithbunafla.ui.admin.*
import com.uoi.pratikumwithbunafla.ui.components.*
import com.uoi.pratikumwithbunafla.ui.theme.AppSurface
import com.uoi.pratikumwithbunafla.ui.theme.AppText
import com.uoi.pratikumwithbunafla.ui.theme.AppTextMuted
import com.uoi.pratikumwithbunafla.ui.theme.LocalSmartWasteAppearance
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch

@Composable
internal fun AccountProfileSettings(
    account: UserAccount,
    onLogout: () -> Unit,
    onUpdateProfile: suspend (String) -> UserAccount,
    onUpdateProfilePhoto: (suspend (Uri) -> UserAccount)? = null,
    onOpenPhoto: ((String) -> Unit)? = null,
) {
    val appearance = LocalSmartWasteAppearance.current
    val isDark = appearance.darkMode
    var showNameDialog by rememberSaveable(account.username) { mutableStateOf(false) }
    var nameDraft by rememberSaveable(account.username) { mutableStateOf(account.name) }
    var nameError by remember { mutableStateOf("") }
    var savingName by remember { mutableStateOf(false) }
    var photoError by remember { mutableStateOf("") }
    var savingPhoto by rememberSaveable(account.username) { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val profilePhotoPicker =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
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
        val profileAnimationKey = "profile-${account.username}"
        StaggeredPageEntry(profileAnimationKey, 0) {
            if (account.role == UserRole.ADMIN) {
                AdminSectionHeading(
                    eyebrow = "AKUN DAN PREFERENSI",
                    title = "Profil ${account.role.label.lowercase()}",
                    subtitle = "Informasi akun dan preferensi aplikasi.",
                )
            } else {
                PetugasSectionHeading(
                    eyebrow = "AKUN LAPANGAN",
                    title = "Profil petugas",
                    subtitle = null,
                    icon = Icons.Rounded.Person,
                    photoName = account.name,
                    photoUrl = account.profilePhotoUrl,
                )
            }
        }

        StaggeredPageEntry(profileAnimationKey, 1) {
            AppCard(modifier = Modifier.fillMaxWidth(), containerColor = AppSurface) {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(14.dp),
                    ) {
                        val profilePhotoUrl = account.profilePhotoUrl?.takeIf(String::isNotBlank)
                        UserAvatar(
                            account.name,
                            Modifier.size(58.dp).clickable(
                                enabled = profilePhotoUrl != null && onOpenPhoto != null,
                                role = Role.Button,
                            ) {
                                profilePhotoUrl?.let { onOpenPhoto?.invoke(it) }
                            },
                            profilePhotoUrl,
                        )
                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(4.dp),
                        ) {
                            Text(
                                account.name.displayPersonName(),
                                color = AppText,
                                fontSize = 19.sp,
                                fontWeight = FontWeight.Bold,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                            )
                            Text(
                                account.role.description,
                                color = AppTextMuted,
                                fontSize = 13.sp,
                                lineHeight = 18.sp,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis,
                            )
                        }
                    }
                    AppCardDivider()
                    ProfileInfoRow("Username", "@${account.username}")
                    ProfileInfoRow("Peran", account.role.label)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        if (onUpdateProfilePhoto != null) {
                            TextButton(
                                onClick = { profilePhotoPicker.launch("image/*") },
                                enabled = !savingPhoto && !savingName,
                            ) {
                                if (savingPhoto) {
                                    Text(
                                        "Menyimpan…",
                                        color = LoginAccent,
                                        fontWeight = FontWeight.SemiBold,
                                    )
                                } else {
                                    Icon(
                                        Icons.Filled.PhotoCamera,
                                        contentDescription = null,
                                        tint = LoginAccent,
                                        modifier = Modifier.size(16.dp),
                                    )
                                    Spacer(Modifier.width(5.dp))
                                    Text(
                                        "Ganti foto",
                                        color = LoginAccent,
                                        fontWeight = FontWeight.SemiBold,
                                    )
                                }
                            }
                        }
                        TextButton(
                            onClick = {
                                nameDraft = account.name
                                nameError = ""
                                showNameDialog = true
                            },
                            enabled = !savingName && !savingPhoto,
                        ) {
                            Text(
                                "Ubah nama profil",
                                color = LoginAccent,
                                fontWeight = FontWeight.SemiBold,
                            )
                        }
                    }
                    if (photoError.isNotBlank()) {
                        Text(photoError, color = RoleRed, fontSize = 11.sp)
                    }
                }
            }
        }

        StaggeredPageEntry(profileAnimationKey, 2) {
            AppCard(
                modifier = Modifier.fillMaxWidth().clickable(onClick = appearance.onToggleTheme),
                containerColor = AppSurface,
            ) {
                Row(
                    modifier =
                        Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    Box(
                        modifier =
                            Modifier.size(38.dp)
                                .clip(RoundedCornerShape(13.dp))
                                .background(LoginAccent.copy(alpha = 0.12f)),
                        contentAlignment = Alignment.Center,
                    ) {
                        Icon(
                            imageVector =
                                if (isDark) Icons.Rounded.NightsStay else Icons.Rounded.WbSunny,
                            contentDescription = null,
                            tint = LoginAccent,
                            modifier = Modifier.size(21.dp),
                        )
                    }
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(3.dp),
                    ) {
                        Text(
                            "Tampilan aplikasi",
                            color = AppText,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                        )
                        Text(
                            if (isDark) "Mode malam aktif" else "Mode terang aktif",
                            color = AppTextMuted,
                            fontSize = 12.sp,
                        )
                    }
                    TextButton(onClick = appearance.onToggleTheme) {
                        Text(
                            if (isDark) "Terang" else "Malam",
                            color = LoginAccent,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                }
            }
        }

        StaggeredPageEntry(profileAnimationKey, 3) {
            TextButton(
                onClick = onLogout,
                modifier =
                    Modifier.fillMaxWidth()
                        .clip(RoundedCornerShape(18.dp))
                        .background(if (isDark) Color(0xFF3A2927) else Color(0xFFF3E8E3))
                        .padding(vertical = 5.dp),
            ) {
                Icon(Icons.Filled.SwitchAccount, contentDescription = null, tint = RoleRed)
                Spacer(Modifier.width(8.dp))
                Text("Keluar dari akun", color = RoleRed, fontWeight = FontWeight.Bold)
            }
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
                        modifier = Modifier.fillMaxWidth(),
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
                    },
                ) {
                    Text(if (savingName) "Menyimpan…" else "Simpan", color = LoginAccent)
                }
            },
            dismissButton = {
                TextButton(onClick = { showNameDialog = false }, enabled = !savingName) {
                    Text("Batal", color = AppTextMuted)
                }
            },
        )
    }
}

@Composable
internal fun ProfileInfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(label, color = AppTextMuted, fontSize = 13.sp)
        Text(value, color = AppText, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
internal fun PetugasLoadingState(title: String, message: String) {
    AppCard(modifier = Modifier.fillMaxWidth(), containerColor = AppSurface) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(17.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(13.dp),
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(23.dp),
                color = LoginAccent,
                strokeWidth = 2.dp,
            )
            Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
                Text(title, color = AppText, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                Text(message, color = AppTextMuted, fontSize = 13.sp, lineHeight = 18.sp)
            }
        }
    }
}

@Composable
internal fun PetugasLoadFailure(message: String, onRefresh: () -> Unit) {
    AppCard(
        modifier = Modifier.fillMaxWidth(),
        containerColor = RoleRed.copy(alpha = 0.08f),
        borderColor = RoleRed.copy(alpha = 0.24f),
        accentColor = RoleRed,
    ) {
        Row(
            modifier =
                Modifier.fillMaxWidth()
                    .padding(start = 15.dp, end = 8.dp, top = 12.dp, bottom = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    "Belum tersinkron",
                    color = AppText,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                )
                Text(message, color = AppTextMuted, fontSize = 13.sp, lineHeight = 18.sp)
            }
            TextButton(onClick = onRefresh) {
                Text(
                    "Coba lagi",
                    color = LoginAccent,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                )
            }
        }
    }
}
