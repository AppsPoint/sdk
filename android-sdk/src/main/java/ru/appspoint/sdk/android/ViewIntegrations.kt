package ru.appspoint.sdk.android

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import ru.appspoint.sdk.ViewModel
import ru.appspoint.sdk.cast
import kotlin.reflect.KType
import kotlin.reflect.typeOf

interface ViewIntegration

class ViewIntegrationContext @PublishedApi internal constructor(
    val viewModel: ViewModel,
    val listItems: Array<*>
)

@PublishedApi
internal val viewIntegrationsFactories =
    mutableMapOf<KType, (ViewIntegrationContext) -> ViewIntegration>()

object ViewIntegrations {
    inline fun <reified T : ViewIntegration> put(
        noinline factory: (ViewIntegrationContext) -> T
    ) {
        viewIntegrationsFactories[typeOf<T>()] = factory
    }

    inline fun <reified T : ViewIntegration> get(
        viewModel: ViewModel,
        vararg listItems: Any?,
        defaultFactory: (ViewIntegrationContext) -> T
    ): T = viewIntegrationsFactories[typeOf<T>()]?.invoke(
        ViewIntegrationContext(viewModel, listItems)
    ) as? T ?: defaultFactory(ViewIntegrationContext(viewModel, listItems))
}

@Composable
inline fun <reified T : ViewIntegration> rememberViewIntegration(
    viewModel: ViewModel,
    vararg listItems: Any?,
    noinline defaultFactory: (ViewIntegrationContext) -> T
) = remember(viewModel, *listItems) {
    ViewIntegrations.get(viewModel, *listItems, defaultFactory = defaultFactory)
}