package ru.apps_point.sdk

sealed class LoadingState<T> {
    class Success<T>(val result: T) : LoadingState<T>()
    class Failure<T>(val cause: Exception) : LoadingState<T>()
    class Loading<T> : LoadingState<T>()
}