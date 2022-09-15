package ru.appspoint.sdk

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import platform.Foundation.NSUUID
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

object KotlinObservableObjects {
    internal val updates = MutableStateFlow(0)
    fun observeAll(observer: () -> Unit): Job {
        val job = Job()
        updates.onEach { observer() }.launchIn(CoroutineScope(Dispatchers.Main))
        return job
    }
}

actual abstract class ViewModel actual constructor() : KotlinObservableObject {
    actual val scope = CoroutineScope(Dispatchers.Main)
}

actual fun <R : KotlinObservableObject, T> state(value: T) =
    object : ReadWriteProperty<R, T> {
        var value = value

        override fun getValue(thisRef: R, property: KProperty<*>) =
            this.value

        override fun setValue(
            thisRef: R,
            property: KProperty<*>,
            value: T
        ) {
            this.value = value
            KotlinObservableObjects.updates.value++
        }
    }


actual fun randomUUID(): String = NSUUID().UUIDString