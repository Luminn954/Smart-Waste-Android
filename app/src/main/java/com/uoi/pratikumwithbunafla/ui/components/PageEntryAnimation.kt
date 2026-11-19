package com.uoi.pratikumwithbunafla.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
internal fun StaggeredPageEntry(
    pageKey: String,
    index: Int,
    content: @Composable ColumnScope.() -> Unit,
) {
    val isPetugasEntry = pageKey.startsWith("petugas-")
    var visible by remember(pageKey, index) { mutableStateOf(false) }
    val maxOffsetPx = with(LocalDensity.current) { 34.dp.roundToPx() }

    LaunchedEffect(pageKey, index, isPetugasEntry) {
        if (!isPetugasEntry) {
            delay(index.coerceAtMost(18) * 52L)
        }
        visible = true
    }

    val entryTransition =
        if (isPetugasEntry) {
            fadeIn(tween(180, easing = FastOutSlowInEasing))
        } else {
            fadeIn(tween(230, easing = FastOutSlowInEasing)) +
                slideInVertically(
                    initialOffsetY = { height -> -(height / 5).coerceAtMost(maxOffsetPx) },
                    animationSpec = tween(360, easing = FastOutSlowInEasing),
                )
        }

    AnimatedVisibility(
        visible = visible,
        modifier = Modifier.fillMaxWidth(),
        enter = entryTransition,
    ) {
        Column(modifier = Modifier.fillMaxWidth(), content = content)
    }
}
