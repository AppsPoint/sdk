package ru.appspoint.sdk

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

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

        suspend fun <T> of(flow: Flow<T>, onStateChanged: suspend (LoadingState<T>) -> Unit) {
            flow
                .onEach { onStateChanged(Success(it)) }
                .catch { onStateChanged(Failure(it)) }
                .collect()
        }
    }
}