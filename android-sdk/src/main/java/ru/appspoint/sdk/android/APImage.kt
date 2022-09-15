package ru.appspoint.sdk.android

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.SvgDecoder
import ru.appspoint.sdk.ImageSource
import ru.appspoint.sdk.LocalImageSource
import ru.appspoint.sdk.RemoteImageSource

var imageLoader: ImageLoader? = null

@Composable
fun APImage(
    value: ImageSource?,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Fit,
    alpha: Float = DefaultAlpha,
    colorFilter: ColorFilter? = null
) {
    if (value != null && value !is LocalImageSource && imageLoader == null)
        imageLoader = ImageLoader.Builder(LocalContext.current)
            .components { add(SvgDecoder.Factory()) }.build()
    val painter = when (value) {
        is LocalImageSource -> painterResource(
            LocalContext.current.resources.getIdentifier(
                value.name,
                "drawable",
                LocalContext.current.packageName
            )
        )
        is RemoteImageSource -> rememberAsyncImagePainter(value.url, imageLoader = imageLoader!!)
        is VectorImageSource -> rememberVectorPainter(image = value.imageVector)
        else -> return
    }
    Image(painter, contentDescription, modifier, alignment, contentScale, alpha, colorFilter)
}