package ru.vs.core.decompose.router.list

import com.arkivanov.decompose.Child
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.children.*
import com.arkivanov.decompose.value.Value

fun <C : Any, T : Any> ComponentContext.childList(
        source: NavigationSource<List<C>>,
        initialStack: () -> List<C> = { emptyList() },
        key: String = "DefaultChildList",
        childFactory: (configuration: C, ComponentContext) -> T,
): Value<ChildList<C, T>> =
        children(
                source = source,
                key = key,
                initialState = { ListNavState(configurations = initialStack()) },
                saveState = { null },
                restoreState = { ListNavState(initialStack()) },
                navTransformer = { _, event -> ListNavState(configurations = event) },
                stateMapper = { _, children ->
                    @Suppress("UNCHECKED_CAST")
                    val createdChildren = children as List<Child.Created<C, T>>

                    ChildList(items = createdChildren)
                },
                onEventComplete = { _, _, _ -> /* no action */ },
                backTransformer = { _ -> null },
                childFactory = childFactory,
        )

private data class ListNavState<out C : Any>(val configurations: List<C>) : NavState<C> {
    override val children: List<SimpleChildNavState<C>> = configurations.map { configuration ->
        SimpleChildNavState(
                configuration = configuration,
                status = ChildNavState.Status.ACTIVE,
        )
    }
}

data class ChildList<out C : Any, out T : Any>(val items: List<Child.Created<C, T>>)
