package com.uoi.pratikumwithbunafla.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

internal data class RoleNavigationDestination(
    val route: String,
    val label: String,
    val icon: ImageVector,
)

@Composable
internal fun RoleNavigationBar(
    selectedRoute: String,
    destinations: List<RoleNavigationDestination>,
    containerColor: Color,
    selectedColor: Color,
    selectedContentColor: Color,
    inactiveContentColor: Color,
    onDestinationSelected: (String) -> Unit,
) {
    val selectedIndex = destinations.indexOfFirst { it.route == selectedRoute }.coerceAtLeast(0)
    val navigationSurface by
        animateColorAsState(
            targetValue = containerColor,
            animationSpec = tween(320, easing = FastOutSlowInEasing),
            label = "role-nav-surface",
        )
    val activeSurface by
        animateColorAsState(
            targetValue = selectedColor,
            animationSpec = tween(360, easing = FastOutSlowInEasing),
            label = "role-nav-active",
        )
    val navigationBarInset = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()

    Column(modifier = Modifier.fillMaxWidth().background(navigationSurface)) {
        BoxWithConstraints(
            modifier = Modifier.fillMaxWidth().height(70.dp).padding(horizontal = 8.dp),
        ) {
            val itemWidth = maxWidth / destinations.size
            val activeOffset by
                animateDpAsState(
                    targetValue = itemWidth * selectedIndex + 4.dp,
                    animationSpec = tween(480, easing = FastOutSlowInEasing),
                    label = "role-nav-indicator",
                )
            Box(
                modifier =
                    Modifier.align(Alignment.CenterStart)
                        .offset(x = activeOffset)
                        .width(itemWidth - 8.dp)
                        .height(52.dp)
                        .clip(RoundedCornerShape(17.dp))
                        .background(activeSurface),
            )
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                destinations.forEachIndexed { index, destination ->
                    val selected = index == selectedIndex
                    val contentColor by
                        animateColorAsState(
                            targetValue =
                                if (selected) selectedContentColor else inactiveContentColor,
                            animationSpec = tween(320, easing = FastOutSlowInEasing),
                            label = "role-nav-content-${destination.route}",
                        )
                    Column(
                        modifier =
                            Modifier.weight(1f)
                                .fillMaxHeight()
                                .selectable(
                                    selected = selected,
                                    interactionSource = null,
                                    indication = null,
                                    role = Role.Tab,
                                    onClick = { onDestinationSelected(destination.route) },
                                ),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Icon(
                            imageVector = destination.icon,
                            contentDescription = null,
                            tint = contentColor,
                            modifier = Modifier.size(20.dp),
                        )
                        Text(
                            text = destination.label,
                            color = contentColor,
                            fontSize = 10.sp,
                            lineHeight = 13.sp,
                            fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Medium,
                            maxLines = 1,
                        )
                    }
                }
            }
        }
        Spacer(
            modifier = Modifier.fillMaxWidth().height(navigationBarInset).background(navigationSurface),
        )
    }
}
