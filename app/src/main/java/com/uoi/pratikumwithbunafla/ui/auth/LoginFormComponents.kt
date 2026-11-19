package com.uoi.pratikumwithbunafla.ui.auth

import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.uoi.pratikumwithbunafla.data.model.UserRole
import com.uoi.pratikumwithbunafla.ui.components.*

@Composable
internal fun LoginGlassPanel(
    palette: LoginPalette,
    darkMode: Boolean,
    content: @Composable ColumnScope.() -> Unit,
) {
    val shape = RoundedCornerShape(24.dp)
    val glassTop by
        animateColorAsState(
            targetValue =
                if (darkMode) {
                    Color(0xFFA5C5AA).copy(alpha = 0.15f)
                } else {
                    Color(0xFFF5F5E8).copy(alpha = 0.94f)
                },
            animationSpec = tween(800),
            label = "login-glass-top",
        )
    val glassMiddle by
        animateColorAsState(
            targetValue =
                if (darkMode) {
                    palette.surface.copy(alpha = 0.48f)
                } else {
                    Color(0xFFDFEBDD).copy(alpha = 0.95f)
                },
            animationSpec = tween(800),
            label = "login-glass-middle",
        )
    val glassBottom by
        animateColorAsState(
            targetValue =
                if (darkMode) {
                    Color(0xFF152019).copy(alpha = 0.82f)
                } else {
                    Color(0xFFD7E6D3).copy(alpha = 0.92f)
                },
            animationSpec = tween(800),
            label = "login-glass-bottom",
        )
    val glassElevation by
        animateDpAsState(
            targetValue = if (darkMode) 10.dp else 5.dp,
            animationSpec = tween(800),
            label = "login-glass-elevation",
        )

    Box(
        modifier =
            Modifier.fillMaxWidth()
                .shadow(
                    elevation = glassElevation,
                    shape = shape,
                    ambientColor = palette.surface,
                    spotColor = palette.accent.copy(alpha = if (darkMode) 0.34f else 0.09f),
                )
                .clip(shape)
                .background(Brush.verticalGradient(listOf(glassTop, glassMiddle, glassBottom)))
                .padding(horizontal = 18.dp, vertical = 16.dp),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            content = content,
        )
    }
}

@Composable
internal fun LoginRoleSelector(
    selectedRole: UserRole?,
    onSelectRole: (UserRole) -> Unit,
    palette: LoginPalette,
    darkMode: Boolean,
    compact: Boolean,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(if (compact) 6.dp else 10.dp),
    ) {
        UserRole.entries.forEach { role ->
            val isSelected = role == selectedRole
            val roleStyle = loginRoleStyle(role, darkMode)
            val roleColor by
                animateColorAsState(
                    targetValue =
                        if (isSelected) {
                            roleStyle.accent
                        } else {
                            palette.text.copy(alpha = if (darkMode) 0.82f else 1f)
                        },
                    animationSpec = tween(260),
                    label = "${role.name.lowercase()}-role-color",
                )
            val roleFill by
                animateColorAsState(
                    targetValue =
                        if (isSelected) {
                            roleStyle.soft.copy(alpha = 0.84f)
                        } else {
                            roleStyle.soft.copy(alpha = if (darkMode) 0.20f else 0.46f)
                        },
                    animationSpec = tween(260),
                    label = "${role.name.lowercase()}-role-fill",
                )
            val roleShape = RoundedCornerShape(22.dp)
            Column(
                modifier =
                    Modifier.weight(1f)
                        .heightIn(min = 88.dp)
                        .clip(roleShape)
                        .background(roleFill, roleShape)
                        .border(
                            width = if (isSelected) 2.dp else 1.dp,
                            color =
                                if (isSelected) roleColor else roleStyle.border.copy(alpha = 0.78f),
                            shape = roleShape,
                        )
                        .selectable(
                            selected = isSelected,
                            role = Role.RadioButton,
                            onClick = { onSelectRole(role) },
                        )
                        .padding(
                            horizontal = if (compact) 8.dp else 11.dp,
                            vertical = 10.dp,
                        ),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(if (compact) 8.dp else 10.dp),
                ) {
                    Box(
                        modifier =
                            Modifier.size(if (compact) 30.dp else 32.dp)
                                .clip(CircleShape)
                                .background(
                                    roleStyle.soft.copy(alpha = if (isSelected) 0.92f else 0.62f),
                                ),
                        contentAlignment = Alignment.Center,
                    ) {
                        Icon(
                            imageVector = roleStyle.icon,
                            contentDescription = null,
                            modifier = Modifier.size(if (compact) 18.dp else 20.dp),
                            tint = roleColor,
                        )
                    }
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.spacedBy(3.dp),
                    ) {
                        Text(
                            text = role.label,
                            color = roleColor,
                            fontFamily = LoginFont,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = if (compact) 14.sp else 15.sp,
                            lineHeight = 19.sp,
                            maxLines = 1,
                        )
                        Text(
                            text = roleStyle.hint,
                            color = palette.muted,
                            fontFamily = LoginFont,
                            fontWeight = FontWeight.Medium,
                            fontSize = 12.sp,
                            lineHeight = 16.sp,
                            maxLines = 2,
                        )
                    }
                }
            }
        }
    }
}

@Composable
internal fun LoginPillField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    leadingIcon: ImageVector,
    keyboardType: KeyboardType,
    imeAction: ImeAction,
    palette: LoginPalette,
    darkMode: Boolean,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onImeAction: (() -> Unit)? = null,
    trailingContent: (@Composable () -> Unit)? = null,
) {
    var focused by remember { mutableStateOf(false) }
    val shape = RoundedCornerShape(50.dp)
    val fieldText = palette.text

    Row(
        modifier =
            Modifier.fillMaxWidth()
                .heightIn(min = 56.dp)
                .shadow(
                    elevation = if (focused) 11.dp else 7.dp,
                    shape = shape,
                    ambientColor = palette.surface,
                    spotColor = palette.accent.copy(alpha = if (focused) 0.20f else 0.10f),
                )
                .clip(shape)
                .background(palette.field.copy(alpha = if (darkMode) 0.70f else 0.76f))
                .border(
                    width = if (focused) 1.5.dp else 1.dp,
                    color = if (focused) palette.accent else palette.border,
                    shape = shape,
                )
                .padding(start = 15.dp, end = 9.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = leadingIcon,
            contentDescription = null,
            modifier = Modifier.size(17.dp),
            tint = fieldText,
        )
        Spacer(modifier = Modifier.width(13.dp))
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.weight(1f).onFocusChanged { focused = it.isFocused }.focusable(),
            singleLine = true,
            textStyle =
                TextStyle(
                    color = fieldText,
                    fontFamily = LoginFont,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                ),
            cursorBrush = SolidColor(palette.accent),
            visualTransformation = visualTransformation,
            keyboardOptions =
                KeyboardOptions(
                    keyboardType = keyboardType,
                    imeAction = imeAction,
                ),
            keyboardActions =
                KeyboardActions(
                    onNext = {},
                    onDone = { onImeAction?.invoke() },
                ),
            decorationBox = { innerTextField ->
                Box(contentAlignment = Alignment.CenterStart) {
                    if (value.isEmpty()) {
                        Text(
                            text = placeholder,
                            color = fieldText.copy(alpha = if (darkMode) 0.86f else 1f),
                            fontFamily = LoginFont,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            maxLines = 1,
                        )
                    }
                    innerTextField()
                }
            },
        )
        trailingContent?.invoke()
    }
}

@Composable
internal fun LoginActionButton(
    isLoading: Boolean,
    selectedRole: UserRole?,
    palette: LoginPalette,
    darkMode: Boolean,
    onClick: () -> Unit,
) {
    val shape = RoundedCornerShape(50.dp)
    val roleStyle = selectedRole?.let { loginRoleStyle(it, darkMode) }
    val buttonColor by
        animateColorAsState(
            targetValue = roleStyle?.button ?: palette.accent,
            animationSpec = tween(700),
            label = "login-button-role-color",
        )
    Row(
        modifier =
            Modifier.fillMaxWidth()
                .heightIn(min = 56.dp)
                .shadow(
                    elevation = 14.dp,
                    shape = shape,
                    ambientColor = palette.surface,
                    spotColor = (roleStyle?.accent ?: palette.accent).copy(alpha = 0.28f),
                )
                .clip(shape)
                .background(buttonColor)
                .clickable(enabled = !isLoading, role = Role.Button, onClick = onClick)
                .padding(horizontal = 11.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text =
                when {
                    isLoading -> "Memeriksa akun..."
                    selectedRole == null -> "Pilih peran untuk masuk"
                    else -> "Masuk sebagai ${selectedRole.label}"
                },
            color = Color.White,
            fontFamily = LoginFont,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            maxLines = 2,
            textAlign = TextAlign.Center,
            lineHeight = 17.sp,
        )
        Spacer(modifier = Modifier.weight(1f))
        Box(
            modifier =
                Modifier.size(32.dp)
                    .shadow(5.dp, CircleShape, spotColor = Color(0xFF183324).copy(alpha = 0.2f))
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.18f)),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = null,
                modifier = Modifier.size(19.dp),
                tint = Color.White,
            )
        }
    }
}

@Composable
internal fun NeomorphicModeToggle(
    darkMode: Boolean,
    palette: LoginPalette,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val shape = RoundedCornerShape(50.dp)
    val toggleTopColor by
        animateColorAsState(
            targetValue =
                if (darkMode) Color(0xFF2C4634).copy(alpha = 0.92f)
                else Color(0xFFF0F3E4).copy(alpha = 0.96f),
            animationSpec = tween(650),
            label = "mode-toggle-top",
        )
    val toggleBottomColor by
        animateColorAsState(
            targetValue =
                if (darkMode) Color(0xFF15241A).copy(alpha = 0.86f)
                else Color(0xFFD7E9D4).copy(alpha = 0.94f),
            animationSpec = tween(650),
            label = "mode-toggle-bottom",
        )
    val iconRotation by
        animateFloatAsState(
            targetValue = if (darkMode) 180f else 0f,
            animationSpec = tween(520),
            label = "mode-toggle-rotation",
        )
    val toggleElevation by
        animateDpAsState(
            targetValue = if (darkMode) 12.dp else 8.dp,
            animationSpec = tween(650),
            label = "mode-toggle-elevation",
        )
    val toggleGlow by
        animateFloatAsState(
            targetValue = if (darkMode) 0.30f else 0.16f,
            animationSpec = tween(650),
            label = "mode-toggle-glow",
        )
    Box(
        modifier =
            modifier
                .size(48.dp)
                .shadow(
                    toggleElevation,
                    shape,
                    ambientColor = palette.surface,
                    spotColor = palette.accent.copy(alpha = toggleGlow),
                )
                .clip(shape)
                .background(
                    Brush.verticalGradient(listOf(toggleTopColor, toggleBottomColor)),
                )
                .clickable(role = Role.Button, onClick = onClick)
                .semantics { role = Role.Button },
        contentAlignment = Alignment.Center,
    ) {
        Crossfade(targetState = darkMode, label = "theme-toggle-icon") { isDark ->
            Icon(
                imageVector = if (isDark) Icons.Filled.LightMode else Icons.Filled.DarkMode,
                contentDescription = if (isDark) "Aktifkan mode terang" else "Aktifkan mode gelap",
                modifier = Modifier.size(20.dp).graphicsLayer { rotationZ = iconRotation },
                tint = palette.accentDark,
            )
        }
    }
}
