package ru.vs.core.decompose.router.list

import com.arkivanov.decompose.Child
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.children.*
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.operator.map
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * Creates list with any items has our own lifecycle and state
 * For different [C] with equal [ID] keep created [T], reuse it and update it state to new [C]
 *
 * @param C - item state type
 * @param ID - unique id type
 * @param T - resulting component type
 *
 * @param idSelector - get unique id for any source list element
 */
fun <C : Any, ID : Any, T : ComponentContext> ComponentContext.childListWithState(
    source: NavigationSource<List<C>>,
    idSelector: (C) -> ID,
    initialStack: () -> List<C> = { emptyList() },
    key: String = "DefaultChildList",
    childFactory: (state: StateFlow<C>, ComponentContext) -> T,
): Value<List<T>> = children(
    source = source,
    key = key,
    initialState = { ListWithStateNavState(initialStack(), idSelector) },
    saveState = { null },
    restoreState = { ListWithStateNavState(initialStack(), idSelector) },
    navTransformer = { oldState, event ->
        val oldStateMap = oldState.configurations.associateBy { it.id }
        val newState = event.map { item ->
            val id = idSelector(item)
            val state = oldStateMap[id]
            if (state != null) {
                // if we found old item with same id
                // then just update it internal state
                state.state.value = item
                state
            } else {
                // otherwise it's new item
                // create configuration for it
                ListWithStateConfiguration(id, item)
            }

            // we don't need to delete old items, because we mapped new configuration list
            // decompose internals cleanup deleted items lifecycle automatically
        }
        ListWithStateNavState(configurations = newState)
    },
    stateMapper = { _, children ->
        @Suppress("UNCHECKED_CAST")
        children as List<Child.Created<C, T>>
    },
    onEventComplete = { _, _, _ -> /* no action */ },
    backTransformer = { _ -> null },
    childFactory = { state, context -> childFactory(state.state, context) },
).map { childList -> childList.map { it.instance } }

/**
 * Data class with override equals and hashcode to check only [id] field and skip [state] field
 */
private data class ListWithStateConfiguration<C : Any, ID : Any>(
    val id: ID,
    val state: MutableStateFlow<C>,
) {
    constructor(id: ID, state: C) : this(id, MutableStateFlow(state))

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ListWithStateConfiguration<*, *>

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}

private data class ListWithStateNavState<C : Any, ID : Any>(
    val configurations: List<ListWithStateConfiguration<C, ID>>
) : NavState<ListWithStateConfiguration<C, ID>> {
    override val children: List<SimpleChildNavState<ListWithStateConfiguration<C, ID>>> =
        configurations.map { configuration ->
            SimpleChildNavState(
                configuration = configuration,
                status = ChildNavState.Status.ACTIVE,
            )
        }

    constructor(configurations: List<C>, idSelector: (C) -> ID) : this(
        configurations.map { ListWithStateConfiguration(idSelector(it), it) }
    )
}
