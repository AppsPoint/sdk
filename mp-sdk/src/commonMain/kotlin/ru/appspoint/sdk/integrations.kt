package ru.appspoint.sdk

import kotlin.reflect.KType
import kotlin.reflect.typeOf


interface KotlinObservableObject

interface ModelIntegration : KotlinObservableObject

interface DataSource

@PublishedApi
internal val modelIntegrationsFactories =
    mutableMapOf<KType, (args: Array<*>) -> ModelIntegration>()

@PublishedApi
internal val dataSourceFactories = mutableMapOf<KType, () -> DataSource>()

object ModelIntegrations {
    inline fun <reified T : ModelIntegration> put(noinline factory: (args: Array<*>) -> T) {
        modelIntegrationsFactories[typeOf<T>()] = factory
    }

    inline fun <reified T : ModelIntegration> get(vararg args: Any?): T? =
        modelIntegrationsFactories[typeOf<T>()]?.invoke(args) as? T
}

object DataSources {
    inline fun <reified T : DataSource> put(noinline factory: () -> T) {
        dataSourceFactories[typeOf<T>()] = factory
    }

    inline fun <reified T : DataSource> get(): T? =
        dataSourceFactories[typeOf<T>()]?.invoke() as? T
}