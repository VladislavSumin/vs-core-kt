package ru.vs.core.decompose.router.list

import com.arkivanov.decompose.Child
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.children.*
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.operator.map
import com.arkivanov.essenty.lifecycle.doOnDestroy
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
 *
 * TODO write performance tests to it
 */
fun <C : Any, ID : Any, T : ComponentContext> ComponentContext.childListWithState(
    source: NavigationSource<List<C>>,
    idSelector: (C) -> ID,
    initialStack: () -> List<C> = { emptyList() },
    key: String = "DefaultChildList",
    childFactory: (state: StateFlow<C>, ComponentContext) -> T,
): Value<List<T>> {
    val cache = mutableMapOf<ID, MutableStateFlow<C>>()

    fun updateCache(configurations: List<C>) {
        configurations.forEach { configuration ->
            cache.compute(idSelector(configuration)) { _, state ->
                state?.also { it.value = configuration } ?: MutableStateFlow(configuration)
            }
        }
    }

    fun createInitialState(): ListWithStateNavState<ID> {
        val stack = initialStack()
        updateCache(stack)
        return ListWithStateNavState(stack.map(idSelector))
    }

    return children(
        source = source,
        key = key,
        initialState = { createInitialState() },
        saveState = { null },
        restoreState = { createInitialState() },
        navTransformer = { _, event ->
            updateCache(event)
            ListWithStateNavState(configurations = event.map(idSelector))
        },
        stateMapper = { _, children ->
            @Suppress("UNCHECKED_CAST")
            children as List<Child.Created<C, T>>
        },
        onEventComplete = { _, _, _ -> /* no action */ },
        backTransformer = { _ -> null },
        childFactory = { id, context ->
            val stateFlow = cache[id]!!
            childFactory(stateFlow, context).apply {
                lifecycle.doOnDestroy {
                    cache.remove(id)
                }
            }
        },
    ).map { childList -> childList.map { it.instance } }
}


private data class ListWithStateNavState<ID : Any>(
    val configurations: List<ID>
) : NavState<ID> {
    override val children: List<ChildNavState<ID>> =
        configurations.map { configuration ->
            SimpleChildNavState(
                configuration = configuration,
                status = ChildNavState.Status.ACTIVE,
            )
        }
}
