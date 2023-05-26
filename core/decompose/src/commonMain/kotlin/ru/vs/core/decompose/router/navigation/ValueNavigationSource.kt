package ru.vs.core.decompose.router.navigation

import com.arkivanov.decompose.router.children.NavigationSource
import com.arkivanov.decompose.value.Value

/**
 * Map [Value] to [NavigationSource]
 */
internal fun <T : Any> Value<T>.asNavigationSource(): NavigationSource<T> {
    return ValueNavigationSource(this)
}

internal class ValueNavigationSource<T : Any>(private val parent: Value<T>) : NavigationSource<T> {
    override fun subscribe(observer: (T) -> Unit) {
        parent.subscribe(observer)
    }

    override fun unsubscribe(observer: (T) -> Unit) {
        parent.unsubscribe(observer)
    }
}
