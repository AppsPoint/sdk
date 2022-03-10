package ru.apps_point.sdk

import kotlin.reflect.KClass
import kotlin.reflect.KType
import kotlin.reflect.full.isSubtypeOf
import kotlin.reflect.typeOf

interface Integration
interface UiIntegration : Integration
interface ModelIntegration : Integration

abstract class Integrations {

    internal abstract val integrationType: KType

    @PublishedApi
    internal val factories = HashMap<String, (params: Array<*>) -> Integration>()

    @PublishedApi
    internal inline fun <reified T : Integration> getInternal(vararg params: Any?): T? =
        factories[T::class.key]?.invoke(params) as T?

    @PublishedApi
    internal inline fun <reified T : Integration> putInternal(noinline factory: (params: Array<*>) -> T) {
        factories[T::class.key] = factory
    }

    @PublishedApi
    internal val KClass<*>.key
        get() = if (supertypes.contains(integrationType))
            qualifiedName!!
        else
            (supertypes.first { it.isSubtypeOf(integrationType) }.classifier as KClass<*>).qualifiedName!!
}

object UiIntegrations : Integrations() {
    override val integrationType = typeOf<UiIntegration>()

    inline fun <reified T : UiIntegration> put(noinline factory: (params: Array<*>) -> T) {
        putInternal(factory)
    }

    inline fun <reified T : UiIntegration> get(vararg params: Any?): T? = getInternal(*params)
}

object ModelIntegrations : Integrations() {
    override val integrationType = typeOf<ModelIntegration>()

    inline fun <reified T : ModelIntegration> put(noinline factory: (params: Array<*>) -> T) {
        putInternal(factory)
    }

    inline fun <reified T : ModelIntegration> get(vararg params: Any?): T? = getInternal(*params)
}