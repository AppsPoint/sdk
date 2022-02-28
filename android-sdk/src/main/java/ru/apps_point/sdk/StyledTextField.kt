package ru.apps_point.sdk

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp


class StyledTextFieldStyles internal constructor(
    val textStyle: TextStyle,
    val initialLabelStyle: TextStyle,
    val focusedLabelStyle: TextStyle,
    val placeholderStyle: TextStyle,
    val errorStyle: TextStyle,
) {
    fun labelStyle(focused: Boolean, valueIsEmpty: Boolean) =
        if (!focused && valueIsEmpty) initialLabelStyle else focusedLabelStyle
}

object StyledTextFieldDefaults {

    @Composable
    fun styles(
        textStyle: TextStyle = LocalTextStyle.current,
        initialLabelStyle: TextStyle = LocalTextStyle.current,
        focusedLabelStyle: TextStyle = LocalTextStyle.current,
        placeholderStyle: TextStyle = LocalTextStyle.current,
        errorStyle: TextStyle = LocalTextStyle.current,
    ) = StyledTextFieldStyles(
        textStyle,
        initialLabelStyle,
        focusedLabelStyle,
        placeholderStyle,
        errorStyle
    )
}

@Composable
fun StyledTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    styles: StyledTextFieldStyles = StyledTextFieldDefaults.styles(),
    label: String? = null,
    placeholder: String? = null,
    error: String? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    clearIcon: @Composable (() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions(),
    singleLine: Boolean = false,
    maxLines: Int = Int.MAX_VALUE,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape =
        MaterialTheme.shapes.small.copy(bottomEnd = ZeroCornerSize, bottomStart = ZeroCornerSize),
    colors: TextFieldColors = TextFieldDefaults.textFieldColors()
) {
    var focused by remember { mutableStateOf(false) }
    Column(modifier = modifier) {
        TextField(
            value,
            onValueChange,
            Modifier.onFocusChanged { focused = it.isFocused },
            enabled,
            readOnly,
            styles.textStyle,
            label?.let {
                @Composable { Text(text = it, style = styles.labelStyle(focused, value.isEmpty())) }
            },
            placeholder?.let {
                @Composable { Text(text = it, style = styles.placeholderStyle) }
            },
            leadingIcon,
            clearIcon?.let { if (value.isNotEmpty()) it else null } ?: trailingIcon,
            error != null,
            visualTransformation,
            keyboardOptions,
            keyboardActions,
            singleLine,
            maxLines,
            interactionSource,
            shape,
            colors
        )
        error?.let {
            Text(
                modifier = Modifier.padding(start = 16.dp, top = 4.dp),
                text = it,
                style = styles.errorStyle.merge(
                    TextStyle(
                        color = colors.indicatorColor(
                            enabled = enabled,
                            isError = true,
                            interactionSource = interactionSource
                        ).value
                    )
                )
            )
        }
    }
}