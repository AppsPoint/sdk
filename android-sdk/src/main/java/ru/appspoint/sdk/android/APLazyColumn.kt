package ru.appspoint.sdk.android

import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun APLazyColumn(
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(),
    contentPadding: PaddingValues = PaddingValues(0.dp),
    alignment: Alignment = Alignment.TopStart,
    spacing: Spacing? = null,
    flingBehavior: FlingBehavior = ScrollableDefaults.flingBehavior(),
    userScrollEnabled: Boolean = true,
    content: LazyListScope.() -> Unit
) {
    LazyColumn(
        modifier = modifier,
        state = state,
        contentPadding = contentPadding,
        reverseLayout = false,
        verticalArrangement = when (spacing) {
            is FixedSpacing -> Arrangement.spacedBy(
                spacing.space,
                when (alignment) {
                    Alignment.BottomStart, Alignment.BottomCenter, Alignment.BottomEnd -> Alignment.Bottom
                    Alignment.CenterStart, Alignment.Center, Alignment.CenterEnd -> Alignment.CenterVertically
                    else -> Alignment.Top
                }
            )
            is SpaceBetweenSpacing -> Arrangement.SpaceBetween
            null -> when (alignment) {
                Alignment.BottomStart, Alignment.BottomCenter, Alignment.BottomEnd -> Arrangement.Bottom
                Alignment.CenterStart, Alignment.Center, Alignment.CenterEnd -> Arrangement.Center
                else -> Arrangement.Top
            }
        },
        horizontalAlignment = when (alignment) {
            Alignment.TopCenter, Alignment.Center, Alignment.BottomCenter -> Alignment.CenterHorizontally
            Alignment.TopEnd, Alignment.CenterEnd, Alignment.BottomEnd -> Alignment.End
            else -> Alignment.Start
        },
        flingBehavior = flingBehavior,
        userScrollEnabled = userScrollEnabled,
        content = content
    )
}