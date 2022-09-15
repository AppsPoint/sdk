package ru.appspoint.sdk.android

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

@Composable
fun APRow(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    alignment: Alignment = Alignment.TopStart,
    spacing: Spacing? = null,
    content: @Composable RowScope.() -> Unit
) {
    val spacingDp = (spacing as? FixedSpacing)?.space ?: 0.dp
    val canHaveStartContentPadding =
        contentPadding.calculateStartPadding(LayoutDirection.Ltr) - spacingDp > 0.dp
    val canHaveEndContentPadding =
        contentPadding.calculateEndPadding(LayoutDirection.Ltr) - spacingDp > 0.dp
    val modifierPadding = contentPadding.copy(
        start = if (canHaveStartContentPadding)
            0.dp
        else
            contentPadding.calculateStartPadding(LayoutDirection.Ltr),
        end = if (canHaveEndContentPadding)
            0.dp
        else
            contentPadding.calculateEndPadding(LayoutDirection.Ltr)
    )
    Row(
        modifier = if (modifierPadding.any { it > 0.dp }) modifier.padding(modifierPadding) else modifier,
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
        }
    ) {
        if (canHaveStartContentPadding)
            Spacer(
                modifier = Modifier.width(contentPadding.calculateStartPadding(LayoutDirection.Ltr) - spacingDp)
            )
        content()
        if (canHaveEndContentPadding)
            Spacer(
                modifier = Modifier.width(contentPadding.calculateEndPadding(LayoutDirection.Ltr) - spacingDp)
            )
    }
}