package ru.apps_point.sdk

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlin.reflect.KClass
import kotlin.reflect.KType
import kotlin.reflect.full.createType
import kotlin.reflect.full.isSubtypeOf

abstract class ViewModel {
    val scope = CoroutineScope(Dispatchers.IO)
}

interface Integration

interface ViewIntegration : Integration

class ViewIntegrationContext @PublishedApi internal constructor(
    val viewModel: ViewModel
)

interface ModelIntegration : Integration

interface DataSource : Integration

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

object ViewIntegrations : Integrations() {
    override val integrationType = ViewIntegration::class.createType()

    inline fun <reified T : ViewIntegration> put(noinline factory: ViewIntegrationContext.() -> T) {
        putInternal { factory(it[0].cast()) }
    }

    inline fun <reified T : ViewIntegration> get(viewModel: ViewModel): T? = getInternal(viewModel)

    inline fun <reified T : ViewIntegration> get(
        viewModel: ViewModel,
        defaultFactory: ViewIntegrationContext.() -> T
    ): T = get(viewModel) ?: defaultFactory(ViewIntegrationContext(viewModel))
}

object ModelIntegrations : Integrations() {
    override val integrationType = ModelIntegration::class.createType()

    inline fun <reified T : ModelIntegration> put(noinline factory: (args: Array<*>) -> T) {
        putInternal(factory)
    }

    inline fun <reified T : ModelIntegration> get(vararg args: Any?): T? = getInternal(*args)
}

object DataSources : Integrations() {
    override val integrationType = DataSource::class.createType()

    inline fun <reified T : DataSource> put(noinline factory: (args: Array<*>) -> T) {
        putInternal(factory)
    }

    inline fun <reified T : DataSource> get(vararg args: Any?): T? = getInternal(*args)
}