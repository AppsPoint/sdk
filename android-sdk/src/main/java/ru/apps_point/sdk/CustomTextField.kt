package ru.apps_point.sdk

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = Int.MAX_VALUE,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    styles: CustomTextFieldStyles = CustomTextFieldDefaults.styles(),
    colors: CustomTextFieldColors = CustomTextFieldDefaults.colors(),
    placeholder: String? = null,
    clearIcon: @Composable (() -> Unit) = {},
    error: String? = null,
    isError: Boolean = false,
    content: @Composable (innerTextField: @Composable () -> Unit, clearIcon: @Composable () -> Unit, error: @Composable () -> Unit) -> Unit
) {
//    var focused by remember { mutableStateOf(false) }
    BasicTextField(
        value,
        onValueChange,
        modifier,//.onFocusChanged { focused = it.isFocused },
        enabled,
        readOnly,
        styles.textStyle.merge(TextStyle(color = colors.textColor(enabled = enabled).value)),
        keyboardOptions,
        keyboardActions,
        singleLine,
        maxLines,
        visualTransformation,
        onTextLayout,
        interactionSource,
        SolidColor(colors.cursorColor(isError = isError).value)
    ) { innerTextField ->
        content(
            {
                Box {
                    innerTextField()
                    if (value.isEmpty() && placeholder != null)
                        Text(
                            text = placeholder, style = styles.placeholderStyle.merge(
                                TextStyle(color = colors.placeholderColor(enabled = true).value)
                            )
                        )
                }
            },
            { if (value.isNotEmpty()) clearIcon() },
            {
                error?.takeIf { isError }?.let {
                    Text(
                        text = it,
                        style = styles.errorStyle.merge(TextStyle(color = colors.errorColor().value))
                    )
                }
            }
        )
    }
}


private const val AnimationDuration = 150


class CustomTextFieldStyles internal constructor(
    val textStyle: TextStyle,
    val placeholderStyle: TextStyle,
    val errorStyle: TextStyle,
)

class CustomTextFieldColors internal constructor(
    private val textColor: Color,
    private val disabledTextColor: Color,
    private val errorColor: Color,
    private val cursorColor: Color,
    private val errorCursorColor: Color,
    private val focusedIndicatorColor: Color,
    private val unfocusedIndicatorColor: Color,
    private val errorIndicatorColor: Color,
    private val disabledIndicatorColor: Color,
    private val placeholderColor: Color,
    private val disabledPlaceholderColor: Color
) {


    @Composable
    fun indicatorColor(
        enabled: Boolean,
        isError: Boolean,
        interactionSource: InteractionSource
    ): State<Color> {
        val focused by interactionSource.collectIsFocusedAsState()

        val targetValue = when {
            !enabled -> disabledIndicatorColor
            isError -> errorIndicatorColor
            focused -> focusedIndicatorColor
            else -> unfocusedIndicatorColor
        }
        return if (enabled) {
            animateColorAsState(targetValue, tween(durationMillis = AnimationDuration))
        } else {
            rememberUpdatedState(targetValue)
        }
    }

    @Composable
    fun placeholderColor(enabled: Boolean): State<Color> {
        return rememberUpdatedState(if (enabled) placeholderColor else disabledPlaceholderColor)
    }

    @Composable
    fun textColor(enabled: Boolean): State<Color> {
        return rememberUpdatedState(if (enabled) textColor else disabledTextColor)
    }

    @Composable
    fun cursorColor(isError: Boolean): State<Color> {
        return rememberUpdatedState(if (isError) errorCursorColor else cursorColor)
    }

    @Composable
    fun errorColor(): State<Color> {
        return rememberUpdatedState(errorColor)
    }
}

object CustomTextFieldDefaults {

    @Composable
    fun styles(
        textStyle: TextStyle = LocalTextStyle.current,
        placeholderStyle: TextStyle = LocalTextStyle.current,
        errorStyle: TextStyle = LocalTextStyle.current,
    ) = CustomTextFieldStyles(
        textStyle,
        placeholderStyle,
        errorStyle
    )

    @Composable
    fun colors(
        textColor: Color = LocalContentColor.current.copy(LocalContentAlpha.current),
        disabledTextColor: Color = textColor.copy(ContentAlpha.disabled),
        errorColor: Color = MaterialTheme.colors.error,
        cursorColor: Color = MaterialTheme.colors.primary,
        errorCursorColor: Color = MaterialTheme.colors.error,
        focusedIndicatorColor: Color =
            MaterialTheme.colors.primary.copy(alpha = ContentAlpha.high),
        unfocusedIndicatorColor: Color =
            MaterialTheme.colors.onSurface.copy(alpha = TextFieldDefaults.UnfocusedIndicatorLineOpacity),
        disabledIndicatorColor: Color = unfocusedIndicatorColor.copy(alpha = ContentAlpha.disabled),
        errorIndicatorColor: Color = MaterialTheme.colors.error,
        placeholderColor: Color = MaterialTheme.colors.onSurface.copy(ContentAlpha.medium),
        disabledPlaceholderColor: Color = placeholderColor.copy(ContentAlpha.disabled)
    ) = CustomTextFieldColors(
        textColor = textColor,
        disabledTextColor = disabledTextColor,
        errorColor = errorColor,
        cursorColor = cursorColor,
        errorCursorColor = errorCursorColor,
        focusedIndicatorColor = focusedIndicatorColor,
        unfocusedIndicatorColor = unfocusedIndicatorColor,
        errorIndicatorColor = errorIndicatorColor,
        disabledIndicatorColor = disabledIndicatorColor,
        placeholderColor = placeholderColor,
        disabledPlaceholderColor = disabledPlaceholderColor
    )
}