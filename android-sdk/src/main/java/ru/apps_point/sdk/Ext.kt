package ru.apps_point.sdk

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import coil.ImageLoader
import coil.compose.rememberImagePainter
import coil.decode.SvgDecoder

var imageLoader: ImageLoader? = null

@Composable
fun rememberSvgPainter(url: String): Painter {
    imageLoader ?: run {
        imageLoader = ImageLoader.Builder(LocalContext.current)
            .componentRegistry { add(SvgDecoder(LocalContext.current)) }
            .build()
    }
    return rememberImagePainter(data = url, imageLoader = imageLoader!!)
}

fun <T> Any?.cast() = this as T