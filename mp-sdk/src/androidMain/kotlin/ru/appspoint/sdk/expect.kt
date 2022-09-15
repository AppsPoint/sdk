package ru.appspoint.sdk

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import java.util.*
import kotlin.properties.PropertyDelegateProvider
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

actual abstract class ViewModel actual constructor() : ViewModel(), KotlinObservableObject {
    actual val scope = viewModelScope
}

actual fun <R: KotlinObservableObject, T> state(value: T) =
    object : ReadWriteProperty<R, T> {
        val value = mutableStateOf(value)

        override fun getValue(thisRef: R, property: KProperty<*>) =
            this.value.getValue(thisRef, property)

        override fun setValue(
            thisRef: R,
            property: KProperty<*>,
            value: T
        ) {
            this.value.setValue(thisRef, property, value)
        }
    }

actual fun randomUUID(): String = UUID.randomUUID().toString()