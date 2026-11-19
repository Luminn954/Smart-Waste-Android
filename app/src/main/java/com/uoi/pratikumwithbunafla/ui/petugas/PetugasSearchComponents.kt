package com.uoi.pratikumwithbunafla.ui.petugas

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.uoi.pratikumwithbunafla.data.model.*
import com.uoi.pratikumwithbunafla.ui.admin.*
import com.uoi.pratikumwithbunafla.ui.components.*
import com.uoi.pratikumwithbunafla.ui.theme.AppText
import com.uoi.pratikumwithbunafla.ui.theme.InterFont
import com.uoi.pratikumwithbunafla.ui.theme.LocalSmartWasteAppearance

@Composable
internal fun PetugasQueueSearch(
    query: String,
    onQueryChange: (String) -> Unit,
    placeholder: String,
    suggestionSource: List<String>,
) {
    SmartWasteSearchField(
        value = query,
        onValueChange = onQueryChange,
        placeholder = placeholder,
        suggestionSource = suggestionSource,
    )
}

@Composable
internal fun SmartWasteSearchField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    suggestionSource: List<String>,
    modifier: Modifier = Modifier,
    onSearch: (() -> Unit)? = null,
) {
    var isFocused by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    val darkMode = LocalSmartWasteAppearance.current.darkMode
    val fieldSurface = if (darkMode) Color(0xFF26372D) else Color.White
    val hintColor = if (darkMode) Color(0xFF9EADA2) else Color(0xFF969E99)
    val iconTint = if (darkMode) Color(0xFFB2BEB5) else Color(0xFF68736D)
    val normalizedQuery = value.trim()
    val suggestions =
        remember(suggestionSource, normalizedQuery) {
            searchSuggestions(suggestionSource, normalizedQuery)
        }
    val showSuggestions = isFocused && normalizedQuery.length >= 2 && suggestions.isNotEmpty()

    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier =
                Modifier.fillMaxWidth()
                    .height(56.dp)
                    .shadow(4.dp, CircleShape)
                    .clip(CircleShape)
                    .background(fieldSurface)
                    .padding(start = 17.dp, end = 15.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                Icons.Filled.Search,
                contentDescription = null,
                tint = iconTint,
                modifier = Modifier.size(22.dp),
            )
            Spacer(Modifier.width(12.dp))
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.CenterStart,
            ) {
                if (value.isEmpty()) {
                    Text(
                        placeholder,
                        color = hintColor,
                        fontFamily = InterFont,
                        fontSize = 14.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
                BasicTextField(
                    value = value,
                    onValueChange = onValueChange,
                    modifier = Modifier.fillMaxWidth().onFocusChanged { isFocused = it.isFocused },
                    singleLine = true,
                    textStyle =
                        TextStyle(
                            color = AppText,
                            fontFamily = InterFont,
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp,
                            lineHeight = 20.sp,
                        ),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions =
                        KeyboardActions(
                            onSearch = {
                                onSearch?.invoke()
                                focusManager.clearFocus()
                            },
                        ),
                )
            }
            if (onSearch != null) {
                Box(
                    modifier =
                        Modifier.size(40.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .background(PetugasAccent)
                            .clickable(role = Role.Button) {
                                onSearch()
                                focusManager.clearFocus()
                            },
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        Icons.Filled.Search,
                        contentDescription = "Cari tugas",
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(20.dp),
                    )
                }
            }
        }

        AnimatedVisibility(
            visible = showSuggestions,
            enter =
                fadeIn(animationSpec = tween(150)) +
                    expandVertically(animationSpec = tween(190, easing = FastOutSlowInEasing)),
            exit =
                fadeOut(animationSpec = tween(110)) +
                    shrinkVertically(animationSpec = tween(150, easing = FastOutSlowInEasing)),
        ) {
            Column(
                modifier =
                    Modifier.fillMaxWidth()
                        .padding(top = 10.dp)
                        .shadow(7.dp, RoundedCornerShape(24.dp))
                        .clip(RoundedCornerShape(24.dp))
                        .background(fieldSurface)
                        .padding(vertical = 7.dp),
            ) {
                suggestions.forEach { suggestion ->
                    val matchStart = suggestion.indexOf(normalizedQuery, ignoreCase = true)
                    val suggestionText = buildAnnotatedString {
                        if (matchStart >= 0) {
                            val matchEnd = matchStart + normalizedQuery.length
                            append(suggestion.substring(0, matchStart))
                            withStyle(SpanStyle(color = hintColor)) {
                                append(suggestion.substring(matchStart, matchEnd))
                            }
                            append(suggestion.substring(matchEnd))
                        } else {
                            append(suggestion)
                        }
                    }
                    Row(
                        modifier =
                            Modifier.fillMaxWidth()
                                .heightIn(min = 44.dp)
                                .clickable {
                                    onValueChange(suggestion)
                                    focusManager.clearFocus()
                                }
                                .padding(horizontal = 18.dp, vertical = 9.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            Icons.Filled.Search,
                            contentDescription = null,
                            tint = hintColor,
                            modifier = Modifier.size(18.dp),
                        )
                        Spacer(Modifier.width(12.dp))
                        Text(
                            text = suggestionText,
                            color = AppText,
                            fontFamily = InterFont,
                            fontWeight = FontWeight.Medium,
                            fontSize = 13.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                }
            }
        }
    }
}

internal fun searchSuggestions(source: List<String>, query: String): List<String> {
    if (query.length < 2) return emptyList()

    return source
        .asSequence()
        .map { it.trim() }
        .filter { candidate ->
            candidate.length > query.length && candidate.contains(query, ignoreCase = true)
        }
        .distinctBy { it.lowercase() }
        .sortedWith(
            compareBy<String>(
                { if (it.startsWith(query, ignoreCase = true)) 0 else 1 },
                { it.length },
            ),
        )
        .take(4)
        .toList()
}
