package ru.apps_point.sdk

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.TextFieldDefaults.indicatorLine
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun EnhancedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    styles: TextFieldStyles = EnhancedTextFieldDefaults.styles(),
    label: String? = null,
    placeholder: String? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    clearIcon: @Composable (() -> Unit)? = null,
    error: String? = null,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions(),
    singleLine: Boolean = false,
    maxLines: Int = Int.MAX_VALUE,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = TextFieldDefaults.TextFieldShape,
    colors: TextFieldColors = TextFieldDefaults.textFieldColors(),
    horizontalPadding: Dp? = null
) {
    var focused by remember { mutableStateOf(false) }
    Column(modifier = modifier) {
        Box {
            EnhancedTextFieldImpl(
                value,
                onValueChange,
                modifier.onFocusChanged { focused = it.isFocused },
                enabled,
                readOnly,
                styles.textStyle,
                label?.let {
                    @Composable {
                        Text(
                            text = it,
                            style = styles.labelStyle(focused, value.isEmpty())
                        )
                    }
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
                colors,
                if (label == null)
                    horizontalPadding?.let {
                        TextFieldDefaults.textFieldWithoutLabelPadding(start = it, end = it)
                    } ?: TextFieldDefaults.textFieldWithoutLabelPadding()
                else
                    horizontalPadding?.let {
                        TextFieldDefaults.textFieldWithLabelPadding(start = it, end = it)
                    } ?: TextFieldDefaults.textFieldWithoutLabelPadding()
            )
        }
        error?.takeIf { isError }?.let {
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun EnhancedTextFieldImpl(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = LocalTextStyle.current,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions(),
    singleLine: Boolean = false,
    maxLines: Int = Int.MAX_VALUE,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = TextFieldDefaults.TextFieldShape,
    colors: TextFieldColors = TextFieldDefaults.textFieldColors(),
    contentPadding: PaddingValues =
        if (label == null) {
            TextFieldDefaults.textFieldWithoutLabelPadding()
        } else {
            TextFieldDefaults.textFieldWithLabelPadding()
        }
) {
    // If color is not provided via the text style, use content color as a default
    val textColor = textStyle.color.takeOrElse {
        colors.textColor(enabled).value
    }
    val mergedTextStyle = textStyle.merge(TextStyle(color = textColor))

    var textFieldValueState by remember { mutableStateOf(TextFieldValue(text = value)) }
    val textFieldValue = textFieldValueState.copy(text = value)

    @OptIn(ExperimentalMaterialApi::class)
    (BasicTextField(
        value = textFieldValue,
        modifier = modifier
            .background(colors.backgroundColor(enabled).value, shape)
            .indicatorLine(enabled, isError, interactionSource, colors)
            .defaultMinSize(
                minWidth = TextFieldDefaults.MinWidth,
                minHeight = TextFieldDefaults.MinHeight
            ),
        onValueChange = {
            textFieldValueState = it
            if (value != it.text) {
                onValueChange(it.text)
            }
        },
        enabled = enabled,
        readOnly = readOnly,
        textStyle = mergedTextStyle,
        cursorBrush = SolidColor(colors.cursorColor(isError).value),
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        interactionSource = interactionSource,
        singleLine = singleLine,
        maxLines = maxLines,
        decorationBox = @Composable { innerTextField ->
            // places leading icon, text field with label and placeholder, trailing icon
            TextFieldDefaults.TextFieldDecorationBox(
                value = textFieldValue.text,
                visualTransformation = visualTransformation,
                innerTextField = innerTextField,
                placeholder = placeholder,
                label = label,
                leadingIcon = leadingIcon,
                trailingIcon = trailingIcon,
                singleLine = singleLine,
                enabled = enabled,
                isError = isError,
                interactionSource = interactionSource,
                colors = colors,
                contentPadding = contentPadding
            )
        }
    ))
}

class TextFieldStyles internal constructor(
    val textStyle: TextStyle,
    val initialLabelStyle: TextStyle,
    val focusedLabelStyle: TextStyle,
    val placeholderStyle: TextStyle,
    val errorStyle: TextStyle,
) {
    fun labelStyle(focused: Boolean, valueIsEmpty: Boolean) =
        if (!focused && valueIsEmpty) initialLabelStyle else focusedLabelStyle
}

object EnhancedTextFieldDefaults {

    @Composable
    fun styles(
        textStyle: TextStyle = LocalTextStyle.current,
        initialLabelStyle: TextStyle = LocalTextStyle.current,
        focusedLabelStyle: TextStyle = LocalTextStyle.current,
        placeholderStyle: TextStyle = LocalTextStyle.current,
        errorStyle: TextStyle = LocalTextStyle.current,
    ) = TextFieldStyles(
        textStyle,
        initialLabelStyle,
        focusedLabelStyle,
        placeholderStyle,
        errorStyle
    )
}