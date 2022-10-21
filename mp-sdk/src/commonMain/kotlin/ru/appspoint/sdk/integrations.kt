package ru.appspoint.sdk

import kotlin.reflect.KType
import kotlin.reflect.typeOf


interface KotlinObservableObject

interface ModelIntegration : KotlinObservableObject

interface DataSource

@PublishedApi
internal val modelIntegrationFactories =
    mutableMapOf<KType, (args: Array<*>) -> ModelIntegration>()

@PublishedApi
internal val dataSourceFactories = mutableMapOf<KType, () -> DataSource>()

@PublishedApi
internal val dataSources = mutableMapOf<KType, DataSource>()

object ModelIntegrations {
    inline fun <reified T : ModelIntegration> put(noinline factory: (args: Array<*>) -> T) {
        modelIntegrationFactories[typeOf<T>()] = factory
    }

    inline fun <reified T : ModelIntegration> get(vararg args: Any?): T? =
        modelIntegrationFactories[typeOf<T>()]?.invoke(args) as? T
}

object DataSources {
    inline fun <reified T : DataSource> put(noinline factory: () -> T) {
        dataSourceFactories[typeOf<T>()] = factory
    }

    inline fun <reified T : DataSource> get(): T? {
        val type = typeOf<T>()
        return dataSources[type] as? T
            ?: (dataSourceFactories[type]?.invoke() as? T)?.also {
                dataSources[type] = it
            }
    }
}