package ru.vs.core.decompose.router.list

import com.arkivanov.decompose.Child
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.children.*
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.operator.map
import com.arkivanov.essenty.parcelable.ParcelableContainer
import ru.vs.core.decompose.router.navigation.asNavigationSource

/**
 * Creates list with any items has our own lifecycle
 * For equals [C] keep created [T] and reuse it
 *
 * @param C - configuration type
 * @param T - resulting component type
 */
fun <C : Any, T : ComponentContext> ComponentContext.childList(
    state: Value<List<C>>,
    key: String = "DefaultChildList",
    childFactory: (configuration: C, ComponentContext) -> T,
): Value<List<T>> = children(
    source = state.asNavigationSource(),
    key = key,
    initialState = { ListNavState(configurations = state.value) },
    saveState = {
        // We need to any state
        // If we return null here when instanceKeeper insurances will be forgotten
        ParcelableContainer()
    },
    restoreState = { ListNavState(state.value) },
    navTransformer = { _, event -> ListNavState(configurations = event) },
    stateMapper = { _, children ->
        @Suppress("UNCHECKED_CAST")
        children as List<Child.Created<C, T>>
    },
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
