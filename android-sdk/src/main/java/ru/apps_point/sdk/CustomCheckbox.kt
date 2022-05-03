package ru.apps_point.sdk

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.triStateToggleable
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.unit.dp

@Composable
fun CustomCheckbox(
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
    uncheckedState: @Composable () -> Unit,
    checkedState: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    uncheckedDisabledState: @Composable () -> Unit = uncheckedState,
    checkedDisabledState: @Composable () -> Unit = checkedState,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    val state = remember { ToggleableState(checked) }
    val onClick = if (onCheckedChange != null) {
        { onCheckedChange(!checked) }
    } else null
    val toggleableModifier =
        if (onClick != null) {
            Modifier.triStateToggleable(
                state = state,
                onClick = onClick,
                enabled = enabled,
                role = Role.Checkbox,
                interactionSource = interactionSource,
                indication = rememberRipple(
                    bounded = false,
                    radius = 24.dp
                )
            )
        } else {
            Modifier
        }
    Box(
        modifier = modifier
            .size(20.dp)
            .then(toggleableModifier)
    ) {
        when {
            !checked && enabled -> uncheckedState()
            checked && enabled -> checkedState()
            !checked -> uncheckedDisabledState()
            checked -> checkedDisabledState()
        }
    }
}