package ru.appspoint.sdk

import kotlinx.coroutines.CoroutineScope
import kotlin.properties.PropertyDelegateProvider
import kotlin.properties.ReadWriteProperty


expect abstract class ViewModel() : KotlinObservableObject {
    val scope: CoroutineScope
}

expect fun <R : KotlinObservableObject, T> state(value: T): ReadWriteProperty<R, T>

expect fun randomUUID(): String
