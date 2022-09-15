package ru.appspoint.sdk

sealed class LoadingState<T> {
    class Success<T>(val result: T) : LoadingState<T>()
    class Failure<T>(val cause: Throwable) : LoadingState<T>()
    class Loading<T> : LoadingState<T>()

    companion object {
        suspend fun <T> of(block: suspend () -> T) = try {
            Success(block())
        } catch (e: Exception) {
            Failure(e)
        }
    }
}