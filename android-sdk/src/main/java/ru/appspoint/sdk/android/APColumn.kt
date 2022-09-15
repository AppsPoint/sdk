package ru.appspoint.sdk.android

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun APColumn(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    alignment: Alignment = Alignment.TopStart,
    spacing: Spacing? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    val spacingDp = (spacing as? FixedSpacing)?.space ?: 0.dp
    val canHaveTopContentPadding = contentPadding.calculateTopPadding() - spacingDp > 0.dp
    val canHaveBottomContentPadding = contentPadding.calculateBottomPadding() - spacingDp > 0.dp
    val modifierPadding = contentPadding.copy(
        top = if (canHaveTopContentPadding) 0.dp else contentPadding.calculateTopPadding(),
        bottom = if (canHaveBottomContentPadding) 0.dp else contentPadding.calculateBottomPadding()
    )
    Column(
        modifier = if (modifierPadding.any { it > 0.dp }) modifier.padding(modifierPadding) else modifier,
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
        }
    ) {
        if (canHaveTopContentPadding)
            Spacer(modifier = Modifier.height(contentPadding.calculateTopPadding() - spacingDp))
        content()
        if (canHaveBottomContentPadding)
            Spacer(modifier = Modifier.height(contentPadding.calculateBottomPadding() - spacingDp))
    }
}