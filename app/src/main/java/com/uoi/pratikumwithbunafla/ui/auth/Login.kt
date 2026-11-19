package com.uoi.pratikumwithbunafla.ui.auth

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.edit
import com.uoi.pratikumwithbunafla.R
import com.uoi.pratikumwithbunafla.data.local.SavedLoginCredentials
import com.uoi.pratikumwithbunafla.data.local.SavedLoginCredentialsStore
import com.uoi.pratikumwithbunafla.data.model.UserAccount
import com.uoi.pratikumwithbunafla.data.model.UserRole
import com.uoi.pratikumwithbunafla.ui.components.*
import kotlin.math.PI
import kotlin.math.sin
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    onAuthenticate: suspend (String, String, UserRole) -> UserAccount?,
    onCheckConnection: suspend () -> String,
    onLoginSuccess: () -> Unit,
    initialDarkMode: Boolean = false,
    initialMessage: String = "",
    onThemeModeChange: (Boolean) -> Unit = {},
) {
    val configuration = LocalConfiguration.current
    val compactScreen = configuration.screenWidthDp < 380 || configuration.fontScale >= 1.2f
    val context = LocalContext.current
    val preferences =
        remember(context) {
            context.getSharedPreferences("smart_waste_admin_login", Context.MODE_PRIVATE)
        }
    val savedUsername =
        remember(preferences) {
            preferences.getString("admin_username", "").orEmpty()
        }
    var savedCredentials by
        remember(preferences) {
            mutableStateOf(SavedLoginCredentialsStore.load(context))
        }

    var rememberMe by rememberSaveable {
        mutableStateOf(
            preferences.getBoolean(
                "remember_credentials",
                preferences.getBoolean(
                    "remember_username",
                    preferences.getBoolean("remember_admin", true),
                ),
            ),
        )
    }
    var username by rememberSaveable {
        mutableStateOf(if (rememberMe) savedCredentials?.username ?: savedUsername else "")
    }
    var password by remember {
        mutableStateOf(if (rememberMe) savedCredentials?.password.orEmpty() else "")
    }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    var selectedRole by rememberSaveable {
        mutableStateOf(
            if (rememberMe)
                savedCredentials?.role?.let { roleName ->
                    UserRole.entries.firstOrNull { it.name == roleName }
                }
            else null,
        )
    }
    var isDarkMode by rememberSaveable(initialDarkMode) { mutableStateOf(initialDarkMode) }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf(initialMessage) }
    var showForgotDialog by remember { mutableStateOf(false) }
    var serverReachable by remember { mutableStateOf<Boolean?>(null) }
    val scope = rememberCoroutineScope()
    val modeRippleProgress = remember { Animatable(1f) }
    var modeRippleOrigin by remember { mutableStateOf(Offset.Zero) }
    val loginEntry = remember {
        MutableTransitionState(false)
    }
        .apply {
            targetState = true
        }
    val logoMotion = rememberInfiniteTransition(label = "login-logo-float")
    val logoDrift by
        logoMotion.animateFloat(
            initialValue = 0f,
            targetValue = -4f,
            animationSpec =
                infiniteRepeatable(
                    animation = tween(durationMillis = 2600, easing = FastOutSlowInEasing),
                    repeatMode = RepeatMode.Reverse,
                ),
            label = "login-logo-drift",
        )

    LaunchedEffect(initialMessage) {
        errorMessage = initialMessage
    }

    LaunchedEffect(Unit) {
        runCatching { onCheckConnection() }
            .onSuccess { serverReachable = true }
            .onFailure { serverReachable = false }
    }

    fun submitLogin() {
        val enteredUsername = username.trim()
        val enteredRole = selectedRole
        when {
            enteredRole == null -> errorMessage = "Pilih Admin atau Petugas terlebih dahulu."
            enteredUsername.isBlank() -> errorMessage = "Masukkan username atau email."
            password.isBlank() -> errorMessage = "Masukkan kata sandi."
            else ->
                scope.launch {
                    isLoading = true
                    errorMessage = ""
                    try {
                        val account = onAuthenticate(enteredUsername, password, enteredRole)
                        if (account == null) {
                            errorMessage = "Username atau kata sandi tidak sesuai."
                        } else {
                            if (rememberMe) {
                                runCatching {
                                    SavedLoginCredentialsStore.save(
                                        context,
                                        SavedLoginCredentials(
                                            enteredUsername,
                                            password,
                                            enteredRole.name,
                                        ),
                                    )
                                }
                                    .onSuccess {
                                        savedCredentials =
                                            SavedLoginCredentials(
                                                enteredUsername,
                                                password,
                                                enteredRole.name,
                                            )
                                    }
                                preferences.edit {
                                    putBoolean("remember_admin", true)
                                    putBoolean("remember_username", true)
                                    putBoolean("remember_credentials", true)
                                    putString(
                                        roleUsernamePreferenceKey(enteredRole),
                                        enteredUsername,
                                    )
                                }
                            } else {
                                SavedLoginCredentialsStore.clear(context)
                                savedCredentials = null
                                preferences.edit {
                                    putBoolean("remember_admin", false)
                                    putBoolean("remember_username", false)
                                    putBoolean("remember_credentials", false)
                                    remove("admin_username")
                                    remove("petugas_username")
                                }
                            }
                            onLoginSuccess()
                        }
                    } catch (error: Exception) {
                        val detail = error.message.orEmpty()
                        val connectionProblem =
                            listOf("connect", "timeout", "refused", "resolve").any {
                                detail.contains(it, ignoreCase = true)
                            }
                        errorMessage =
                            if (
                                connectionProblem || (serverReachable == false && detail.isBlank())
                            ) {
                                "Server belum terhubung. Pastikan Apache XAMPP aktif di port 8080, lalu coba lagi."
                            } else {
                                detail.ifBlank { "Login gagal. Coba lagi sebentar." }
                            }
                    } finally {
                        isLoading = false
                    }
                }
        }
    }

    val palette = animateLoginPalette(isDarkMode)
    val primaryText = palette.text
    val secondaryText = palette.muted
    val logoTopColor by
        animateColorAsState(
            targetValue = if (isDarkMode) Color(0xFF2B3B30) else Color(0xFFE3F0D4),
            animationSpec = tween(760),
            label = "login-logo-top",
        )
    val logoBottomColor by
        animateColorAsState(
            targetValue = if (isDarkMode) Color(0xFF19271E) else Color(0xFFAED3B2),
            animationSpec = tween(760),
            label = "login-logo-bottom",
        )
    val logoBorderColor by
        animateColorAsState(
            targetValue = Color.White.copy(alpha = if (isDarkMode) 0.40f else 0.30f),
            animationSpec = tween(760),
            label = "login-logo-border",
        )
    val logoElevation by
        animateDpAsState(
            targetValue = if (isDarkMode) 18.dp else 11.dp,
            animationSpec = tween(760),
            label = "login-logo-elevation",
        )
    Box(
        modifier =
            Modifier.fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            palette.backgroundTop,
                            palette.backgroundMiddle,
                            palette.backgroundBottom,
                        ),
                    ),
                ),
    ) {
        Crossfade(
            targetState = isDarkMode,
            animationSpec = tween(durationMillis = 760, easing = FastOutSlowInEasing),
            label = "sunset-night-background",
            modifier = Modifier.fillMaxSize(),
        ) { darkScene ->
            Box(modifier = Modifier.fillMaxSize()) {
                if (!darkScene) {
                    MorningAtmosphere(modifier = Modifier.fillMaxSize())
                } else {
                    MoonlitNightAtmosphere(modifier = Modifier.fillMaxSize())
                }
                SmartWasteBackdrop(
                    darkMode = darkScene,
                    modifier = Modifier.align(Alignment.BottomCenter).fillMaxWidth().height(300.dp),
                )
            }
        }

        Canvas(modifier = Modifier.fillMaxSize()) {
            val progress = modeRippleProgress.value
            if (progress < 1f && modeRippleOrigin != Offset.Zero) {
                val waveAlpha = sin((progress * PI.toFloat()).toDouble()).toFloat().coerceIn(0f, 1f)
                val rippleRadius = size.maxDimension * (0.025f + progress * 1.35f)
                val rippleColor =
                    if (isDarkMode) {
                        Color(0xFF70BF83)
                    } else {
                        Color(0xFFB5D9BA)
                    }
                drawCircle(
                    color = rippleColor.copy(alpha = waveAlpha * 0.12f),
                    radius = rippleRadius,
                    center = modeRippleOrigin,
                )
                drawCircle(
                    color = rippleColor.copy(alpha = waveAlpha * 0.18f),
                    radius = rippleRadius,
                    center = modeRippleOrigin,
                    style = Stroke(width = 26.dp.toPx()),
                )
                drawCircle(
                    color = rippleColor.copy(alpha = waveAlpha * 0.72f),
                    radius = rippleRadius,
                    center = modeRippleOrigin,
                    style = Stroke(width = 4.dp.toPx()),
                )
            }
        }

        Column(
            modifier =
                Modifier.widthIn(max = 520.dp)
                    .fillMaxSize()
                    .align(Alignment.TopCenter)
                    .windowInsetsPadding(WindowInsets.statusBars)
                    .imePadding()
                    .navigationBarsPadding()
                    .verticalScroll(rememberScrollState())
                    .padding(
                        start = if (compactScreen) 16.dp else 28.dp,
                        end = if (compactScreen) 16.dp else 28.dp,
                        top = 10.dp,
                        bottom = 18.dp,
                    ),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            AnimatedVisibility(
                visibleState = loginEntry,
                enter =
                    fadeIn(animationSpec = tween(850, easing = FastOutSlowInEasing)) +
                        slideInVertically(
                            initialOffsetY = { -it / 3 },
                            animationSpec = tween(850, easing = FastOutSlowInEasing),
                        ),
            ) {
                Box(
                    modifier =
                        Modifier.graphicsLayer { translationY = logoDrift.dp.toPx() }
                            .size(78.dp)
                            .shadow(
                                logoElevation,
                                CircleShape,
                                spotColor =
                                    palette.accent.copy(alpha = if (isDarkMode) 0.34f else 0.18f),
                            )
                            .clip(CircleShape)
                            .background(
                                Brush.verticalGradient(listOf(logoTopColor, logoBottomColor)),
                            )
                            .border(1.2.dp, logoBorderColor, CircleShape),
                    contentAlignment = Alignment.Center,
                ) {
                    Image(
                        painter = painterResource(R.drawable.login_recycling_mark),
                        contentDescription = "Logo daur ulang Smart Waste",
                        colorFilter = RecyclingLogoFilter,
                        modifier = Modifier.size(59.dp),
                    )
                }
            }
            Spacer(modifier = Modifier.height(12.dp))

            AnimatedVisibility(
                visibleState = loginEntry,
                enter =
                    fadeIn(
                        animationSpec = tween(850, delayMillis = 140, easing = FastOutSlowInEasing),
                    ) +
                        slideInVertically(
                            initialOffsetY = { -it / 4 },
                            animationSpec =
                                tween(850, delayMillis = 140, easing = FastOutSlowInEasing),
                        ),
            ) {
                Text(
                    text = "Masuk ke Smart Waste",
                    color = primaryText,
                    fontFamily = LoginFont,
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    lineHeight = 30.sp,
                    textAlign = TextAlign.Center,
                )
            }
            Spacer(modifier = Modifier.height(19.dp))
            AnimatedVisibility(
                visibleState = loginEntry,
                enter =
                    fadeIn(
                        animationSpec = tween(850, delayMillis = 240, easing = FastOutSlowInEasing),
                    ) +
                        slideInVertically(
                            initialOffsetY = { -it / 4 },
                            animationSpec =
                                tween(850, delayMillis = 240, easing = FastOutSlowInEasing),
                        ),
            ) {
                Crossfade(
                    targetState = isDarkMode,
                    animationSpec = tween(420),
                    label = "mode-caption",
                ) { darkMode ->
                    Text(
                        text =
                            if (darkMode) {
                                "Mode malam · operasional tetap terpantau"
                            } else {
                                "Pagi yang lebih bersih, dimulai dari sekitar"
                            },
                        color = secondaryText,
                        fontFamily = LoginFont,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center,
                    )
                }
            }
            Spacer(modifier = Modifier.height(6.dp))

            AnimatedVisibility(
                visibleState = loginEntry,
                enter =
                    fadeIn(
                        animationSpec = tween(850, delayMillis = 360, easing = FastOutSlowInEasing),
                    ) +
                        slideInVertically(
                            initialOffsetY = { -it / 4 },
                            animationSpec =
                                tween(850, delayMillis = 360, easing = FastOutSlowInEasing),
                        ),
            ) {
                LoginRoleSelector(
                    selectedRole = selectedRole,
                    onSelectRole = {
                        if (selectedRole != it) {
                            username =
                                if (rememberMe) {
                                    preferences
                                        .getString(roleUsernamePreferenceKey(it), "")
                                        .orEmpty()
                                        .ifBlank {
                                            savedCredentials
                                                ?.takeIf { credentials ->
                                                    credentials.role == it.name
                                                }
                                                ?.username
                                                ?: if (it == UserRole.ADMIN) savedUsername else ""
                                        }
                                } else ""
                            password =
                                if (rememberMe) {
                                    savedCredentials
                                        ?.takeIf { credentials -> credentials.role == it.name }
                                        ?.password
                                        .orEmpty()
                                } else ""
                        }
                        selectedRole = it
                        errorMessage = ""
                    },
                    palette = palette,
                    darkMode = isDarkMode,
                    compact = compactScreen,
                )
            }
            Spacer(modifier = Modifier.height(12.dp))

            AnimatedVisibility(
                visibleState = loginEntry,
                enter =
                    fadeIn(
                        animationSpec = tween(850, delayMillis = 500, easing = FastOutSlowInEasing),
                    ) +
                        slideInVertically(
                            initialOffsetY = { -it / 5 },
                            animationSpec =
                                tween(850, delayMillis = 500, easing = FastOutSlowInEasing),
                        ),
            ) {
                LoginGlassPanel(palette = palette, darkMode = isDarkMode) {
                    LoginPillField(
                        value = username,
                        onValueChange = {
                            username = it
                            errorMessage = ""
                        },
                        placeholder = "Username atau email",
                        leadingIcon = Icons.Filled.Person,
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next,
                        palette = palette,
                        darkMode = isDarkMode,
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    LoginPillField(
                        value = password,
                        onValueChange = {
                            password = it
                            errorMessage = ""
                        },
                        placeholder = "Kata sandi",
                        leadingIcon = Icons.Filled.Lock,
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done,
                        visualTransformation =
                            if (passwordVisible) {
                                VisualTransformation.None
                            } else {
                                PasswordVisualTransformation()
                            },
                        onImeAction = ::submitLogin,
                        palette = palette,
                        darkMode = isDarkMode,
                        trailingContent = {
                            Box(
                                modifier =
                                    Modifier.size(36.dp).clip(CircleShape).clickable {
                                        passwordVisible = !passwordVisible
                                    },
                                contentAlignment = Alignment.Center,
                            ) {
                                Icon(
                                    imageVector =
                                        if (passwordVisible) {
                                            Icons.Filled.Visibility
                                        } else {
                                            Icons.Filled.VisibilityOff
                                        },
                                    contentDescription =
                                        if (passwordVisible) "Sembunyikan kata sandi"
                                        else "Tampilkan kata sandi",
                                    modifier = Modifier.size(18.dp),
                                    tint = secondaryText,
                                )
                            }
                        },
                    )

                    Row(
                        modifier =
                            Modifier.fillMaxWidth().heightIn(min = 48.dp).padding(top = 2.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Row(
                            modifier =
                                Modifier.heightIn(min = 48.dp)
                                    .toggleable(
                                        value = rememberMe,
                                        role = Role.Checkbox,
                                        onValueChange = { checked ->
                                            rememberMe = checked
                                            if (!checked) {
                                                SavedLoginCredentialsStore.clear(context)
                                                savedCredentials = null
                                                preferences.edit {
                                                    putBoolean("remember_admin", false)
                                                    putBoolean("remember_username", false)
                                                    putBoolean("remember_credentials", false)
                                                    remove("admin_username")
                                                    remove("petugas_username")
                                                }
                                            }
                                        },
                                    )
                                    .semantics { role = Role.Checkbox },
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Box(
                                modifier =
                                    Modifier.size(18.dp)
                                        .shadow(
                                            elevation = if (rememberMe) 7.dp else 2.dp,
                                            shape = RoundedCornerShape(5.dp),
                                            spotColor = palette.accent.copy(alpha = 0.30f),
                                        )
                                        .clip(RoundedCornerShape(5.dp))
                                        .background(
                                            if (rememberMe) palette.accent else palette.surface,
                                        )
                                        .border(1.dp, palette.border, RoundedCornerShape(5.dp)),
                                contentAlignment = Alignment.Center,
                            ) {
                                if (rememberMe) {
                                    Icon(
                                        imageVector = Icons.Filled.Check,
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.size(14.dp),
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.width(7.dp))
                            Text(
                                text = "Ingat username & password",
                                color = primaryText,
                                fontFamily = LoginFont,
                                fontWeight = FontWeight.Medium,
                                fontSize = 12.sp,
                            )
                        }

                        TextButton(
                            onClick = { showForgotDialog = true },
                            contentPadding = PaddingValues(horizontal = 2.dp, vertical = 4.dp),
                        ) {
                            Text(
                                text = "Lupa kata sandi?",
                                color = palette.accentDark,
                                fontFamily = LoginFont,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 12.sp,
                                maxLines = 1,
                            )
                        }
                    }

                    AnimatedVisibility(
                        visible = errorMessage.isNotBlank(),
                        enter = fadeIn() + slideInVertically(initialOffsetY = { -it / 3 }),
                    ) {
                        Text(
                            text = errorMessage,
                            modifier = Modifier.fillMaxWidth().padding(bottom = 6.dp),
                            color = palette.error,
                            fontFamily = LoginFont,
                            fontSize = 12.sp,
                            lineHeight = 16.sp,
                            fontWeight = FontWeight.Medium,
                        )
                    }

                    Spacer(modifier = Modifier.height(3.dp))
                    LoginActionButton(
                        isLoading = isLoading,
                        selectedRole = selectedRole,
                        palette = palette,
                        darkMode = isDarkMode,
                        onClick = ::submitLogin,
                    )
                }
            }
        }

        AnimatedVisibility(
            visibleState = loginEntry,
            modifier =
                Modifier.align(Alignment.TopEnd)
                    .windowInsetsPadding(WindowInsets.statusBars)
                    .padding(top = 10.dp, end = 14.dp),
            enter =
                fadeIn(
                    animationSpec = tween(700, delayMillis = 120, easing = FastOutSlowInEasing),
                ) +
                    slideInVertically(
                        initialOffsetY = { -it / 3 },
                        animationSpec = tween(700, delayMillis = 120, easing = FastOutSlowInEasing),
                    ),
        ) {
            NeomorphicModeToggle(
                darkMode = isDarkMode,
                palette = palette,
                onClick = {
                    val nextDarkMode = !isDarkMode
                    isDarkMode = nextDarkMode
                    onThemeModeChange(nextDarkMode)
                    scope.launch {
                        modeRippleProgress.snapTo(0f)
                        modeRippleProgress.animateTo(
                            targetValue = 1f,
                            animationSpec =
                                tween(durationMillis = 860, easing = FastOutSlowInEasing),
                        )
                    }
                },
                modifier =
                    Modifier.onGloballyPositioned { coordinates ->
                        val position = coordinates.positionInRoot()
                        modeRippleOrigin =
                            Offset(
                                x = position.x + coordinates.size.width / 2f,
                                y = position.y + coordinates.size.height / 2f,
                            )
                    },
            )
        }
    }

    if (showForgotDialog) {
        AlertDialog(
            onDismissRequest = { showForgotDialog = false },
            title = {
                Text(
                    text = "Butuh bantuan masuk?",
                    color = palette.text,
                    fontFamily = LoginFont,
                    fontWeight = FontWeight.Bold,
                )
            },
            text = {
                Text(
                    text =
                        "Reset kata sandi dikelola admin Smart Waste. Hubungi admin untuk mengatur ulang akun.",
                    color = palette.muted,
                    fontFamily = LoginFont,
                    fontSize = 13.sp,
                )
            },
            confirmButton = {
                TextButton(onClick = { showForgotDialog = false }) {
                    Text("Mengerti", color = palette.accentDark, fontFamily = LoginFont)
                }
            },
            containerColor = palette.surface,
        )
    }
}
