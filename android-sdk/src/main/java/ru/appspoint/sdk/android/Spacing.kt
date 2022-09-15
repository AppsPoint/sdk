package ru.appspoint.sdk.android

import androidx.compose.ui.unit.Dp

sealed class Spacing {

    companion object {
        fun fixed(space: Dp): Spacing = FixedSpacing(space)
        val SpaceBetween: Spacing = SpaceBetweenSpacing
    }
}

internal class FixedSpacing(val space: Dp) : Spacing()
internal object SpaceBetweenSpacing : Spacing()
