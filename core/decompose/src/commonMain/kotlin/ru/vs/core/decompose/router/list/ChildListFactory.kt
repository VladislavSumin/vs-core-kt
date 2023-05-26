package ru.vs.core.decompose.router.list

import com.arkivanov.decompose.Child
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.children.*
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.operator.map
import com.arkivanov.essenty.parcelable.ParcelableContainer

/**
 * Creates list with any items has our own lifecycle
 * For equals [C] keep created [T] and reuse it
 *
 * @param C - configuration type
 * @param T - resulting component type
 */
fun <C : Any, T : ComponentContext> ComponentContext.childList(
    source: NavigationSource<List<C>>,
    initialState: () -> List<C> = { emptyList() },
    key: String = "DefaultChildList",
    childFactory: (configuration: C, ComponentContext) -> T,
): Value<List<T>> = children(
    source = source,
    key = key,
    initialState = { ListNavState(configurations = initialState()) },
    saveState = {
        // We need to any state
        // If we return null here when instanceKeeper insurances will be forgotten
        ParcelableContainer()
    },
    restoreState = { ListNavState(initialState()) },
    navTransformer = { _, event -> ListNavState(configurations = event) },
    stateMapper = { _, children ->
        @Suppress("UNCHECKED_CAST")
        children as List<Child.Created<C, T>>
    },
    onEventComplete = { _, _, _ -> /* no action */ },
    backTransformer = { _ -> null },
    childFactory = childFactory,
).map { childList -> childList.map { it.instance } }

private data class ListNavState<out C : Any>(val configurations: List<C>) : NavState<C> {
    override val children: List<SimpleChildNavState<C>> = configurations.map { configuration ->
        SimpleChildNavState(
            configuration = configuration,
            status = ChildNavState.Status.ACTIVE,
        )
    }
}
