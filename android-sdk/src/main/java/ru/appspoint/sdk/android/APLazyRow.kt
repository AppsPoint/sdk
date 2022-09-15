package ru.appspoint.sdk.android

import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun APLazyRow(
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(),
    contentPadding: PaddingValues = PaddingValues(0.dp),
    alignment: Alignment = Alignment.TopStart,
    spacing: Spacing? = null,
    flingBehavior: FlingBehavior = ScrollableDefaults.flingBehavior(),
    userScrollEnabled: Boolean = true,
    content: LazyListScope.() -> Unit
) {
    LazyRow(
        modifier = modifier,
        state = state,
        contentPadding = contentPadding,
        reverseLayout = false,
        horizontalArrangement = when (spacing) {
            is FixedSpacing -> Arrangement.spacedBy(
                spacing.space,
                when (alignment) {
                    Alignment.TopEnd, Alignment.CenterEnd, Alignment.BottomEnd -> Alignment.End
                    Alignment.TopCenter, Alignment.Center, Alignment.BottomCenter -> Alignment.CenterHorizontally
                    else -> Alignment.Start
                }
            )
            is SpaceBetweenSpacing -> Arrangement.SpaceBetween
            null -> when (alignment) {
                Alignment.TopEnd, Alignment.CenterEnd, Alignment.BottomEnd -> Arrangement.End
                Alignment.TopCenter, Alignment.Center, Alignment.BottomCenter -> Arrangement.Center
                else -> Arrangement.Start
            }
        },
        verticalAlignment = when (alignment) {
            Alignment.CenterStart, Alignment.Center, Alignment.CenterEnd -> Alignment.CenterVertically
            Alignment.BottomStart, Alignment.BottomCenter, Alignment.BottomEnd -> Alignment.Bottom
            else -> Alignment.Top
        },
        flingBehavior = flingBehavior,
        userScrollEnabled = userScrollEnabled,
        content = content
    )
}