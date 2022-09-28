package ru.appspoint.sdk.android

import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun APLazyVerticalGrid(
    columns: GridCells,
    modifier: Modifier = Modifier,
    state: LazyGridState = rememberLazyGridState(),
    contentPadding: PaddingValues = PaddingValues(0.dp),
    reverseLayout: Boolean = false,
    alignment: Alignment = Alignment.TopStart,
    verticalSpacing: Spacing? = null,
    horizontalSpacing: Spacing? = null,
    flingBehavior: FlingBehavior = ScrollableDefaults.flingBehavior(),
    userScrollEnabled: Boolean = true,
    content: LazyGridScope.() -> Unit
) {
    LazyVerticalGrid(
        columns,
        modifier,
        state,
        contentPadding,
        reverseLayout,
        when (verticalSpacing) {
            is FixedSpacing -> Arrangement.spacedBy(
                verticalSpacing.space,
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
        horizontalArrangement = when (horizontalSpacing) {
            is FixedSpacing -> Arrangement.spacedBy(
                horizontalSpacing.space,
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
        flingBehavior,
        userScrollEnabled,
        content
    )
}