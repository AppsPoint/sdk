package ru.apps_point.sdk

import kotlin.reflect.KClass
import kotlin.reflect.KType
import kotlin.reflect.full.isSubtypeOf
import kotlin.reflect.typeOf

interface Integration
interface UiIntegration : Integration
class UiIntegrationContext @PublishedApi internal constructor(
    val initialState: Any?,
    val parentIntegration: UiIntegration?
)

interface ModelIntegration : Integration

abstract class Integrations {

    internal abstract val integrationType: KType

    @PublishedApi
    internal val factories = HashMap<String, (args: Array<*>) -> Integration>()

    @PublishedApi
    internal inline fun <reified T : Integration> getInternal(vararg args: Any?): T? =
        factories[T::class.key]?.invoke(args) as T?

    @PublishedApi
    internal inline fun <reified T : Integration> putInternal(noinline factory: (args: Array<*>) -> T) {
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

    inline fun <reified T : UiIntegration> put(noinline factory: UiIntegrationContext.() -> T) {
        putInternal { factory(it[0].cast()) }
    }

    inline fun <reified T : UiIntegration> get(initialState: Any?, parentIntegration: UiIntegration?): T? =
        getInternal(UiIntegrationContext(initialState, parentIntegration))

    inline fun <reified T : UiIntegration> get(
        initialState: Any?,
        parentIntegration: UiIntegration?,
        defaultFactory: UiIntegrationContext.() -> T
    ): T = get(initialState, parentIntegration)
        ?: defaultFactory(UiIntegrationContext(initialState, parentIntegration))
}

object ModelIntegrations : Integrations() {
    override val integrationType = typeOf<ModelIntegration>()

    inline fun <reified T : ModelIntegration> put(noinline factory: (args: Array<*>) -> T) {
        putInternal(factory)
    }

    inline fun <reified T : ModelIntegration> get(vararg args: Any?): T? = getInternal(*args)
}