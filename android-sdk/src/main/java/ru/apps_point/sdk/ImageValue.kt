package ru.apps_point.sdk

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import coil.compose.rememberImagePainter
import kotlinx.datetime.LocalDateTime

val v = LocalDateTime(1, 1, 1, 1, 1)
sealed class ImageValue {

    class Url(val value: String) : ImageValue()

    class Resource(val value: Int) : ImageValue()

    class Vector(val value: ImageVector) : ImageValue()

    @Composable
    fun toPainter(): Painter = when (this) {
        is Resource -> painterResource(value)
        is Url -> rememberImagePainter(value)
        is Vector -> rememberVectorPainter(value)
    }
}