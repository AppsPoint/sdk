package ru.appspoint.sdk


abstract class ImageSource {

    companion object {
        fun remote(url: String): ImageSource = RemoteImageSource(url)
        fun local(name: String): ImageSource = LocalImageSource(name)
    }
}

class RemoteImageSource(val url: String) : ImageSource()

class LocalImageSource(val name: String) : ImageSource()