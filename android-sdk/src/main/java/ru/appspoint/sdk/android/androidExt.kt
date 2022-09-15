package ru.appspoint.sdk.android

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import ru.appspoint.sdk.ImageSource


fun Modifier.outerShadow(
    color: Color = Color.Black.copy(alpha = 0.1f),
    shape: Shape = RectangleShape,
    offsetX: Dp = 0.dp,
    offsetY: Dp = 0.dp,
    blurRadius: Dp = 7.dp,
    spread: Dp = 0.dp,
) = this.drawBehind {
    val transparentColor = color.copy(alpha = 0.0f).toArgb()
    val shadowColor = color.toArgb()
    this.drawIntoCanvas {
        val paint = Paint()
        val frameworkPaint = paint.asFrameworkPaint()
        frameworkPaint.color = transparentColor
        frameworkPaint.setShadowLayer(
            blurRadius.toPx(),
            offsetX.toPx(),
            offsetY.toPx(),
            shadowColor
        )
        it.save()

        if (spread.value > 0) {
            fun calcSpreadScale(spread: Float, childSize: Float): Float {
                return 1f + ((spread / childSize) * 2f)
            }

            it.scale(
                calcSpreadScale(spread.toPx(), this.size.width),
                calcSpreadScale(spread.toPx(), this.size.height),
                this.center.x,
                this.center.y
            )
        }

        when (val outline = shape.createOutline(size, layoutDirection, this)) {
            is Outline.Rectangle ->
                it.drawRect(0f, 0f, this.size.width, this.size.height, paint)
            is Outline.Rounded -> {
                val roundRectPath = if (!outline.roundRect.hasSameCornerRadius())
                    Path().apply { addRoundRect(outline.roundRect) }
                else
                    null
                if (roundRectPath != null) {
                    it.drawPath(roundRectPath, paint)
                } else {
                    val radius = outline.roundRect.bottomLeftCornerRadius.x
                    it.drawRoundRect(
                        0f,
                        0f,
                        this.size.width,
                        this.size.height,
                        radius,
                        radius,
                        paint
                    )
                }
            }
            is Outline.Generic -> it.drawPath(outline.path, paint)
        }
        it.restore()
    }
}

private fun RoundRect.hasSameCornerRadius(): Boolean {
    val sameRadiusX = bottomLeftCornerRadius.x == bottomRightCornerRadius.x &&
            bottomRightCornerRadius.x == topRightCornerRadius.x &&
            topRightCornerRadius.x == topLeftCornerRadius.x
    val sameRadiusY = bottomLeftCornerRadius.y == bottomRightCornerRadius.y &&
            bottomRightCornerRadius.y == topRightCornerRadius.y &&
            topRightCornerRadius.y == topLeftCornerRadius.y
    return sameRadiusX && sameRadiusY
}


fun PaddingValues.any(
    layoutDirection: LayoutDirection = LayoutDirection.Ltr,
    predicate: (Dp) -> Boolean
): Boolean {
    if (predicate(calculateStartPadding(layoutDirection))) return true
    if (predicate(calculateTopPadding())) return true
    if (predicate(calculateEndPadding(layoutDirection))) return true
    if (predicate(calculateBottomPadding())) return true
    return false
}

fun PaddingValues.copy(
    layoutDirection: LayoutDirection = LayoutDirection.Ltr,
    start: Dp = calculateStartPadding(layoutDirection),
    top: Dp = calculateTopPadding(),
    end: Dp = calculateEndPadding(layoutDirection),
    bottom: Dp = calculateBottomPadding()
) = PaddingValues(start, top, end, bottom)

fun ImageSource.Companion.vector(imageVector: ImageVector): ImageSource =
    VectorImageSource(imageVector)

class VectorImageSource(val imageVector: ImageVector) : ImageSource()