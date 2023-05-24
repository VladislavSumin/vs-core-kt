package ru.vs.core.decompose

import com.arkivanov.decompose.router.children.NavigationSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

fun <T : Any> Flow<T>.asNavigationSource(scope: CoroutineScope): NavigationSource<T> {
    return FlowNavigationSource(this, scope)
}

private class FlowNavigationSource<T : Any>(
    private val parentFlow: Flow<T>,
    private val scope: CoroutineScope,
) : NavigationSource<T> {
    private val subscribers = mutableMapOf<(T) -> Unit, Job>()

    // TODO @Synchronized wait kotlin 1.9
    override fun subscribe(observer: (T) -> Unit) {
        check(!subscribers.contains(observer))
        subscribers[observer] = scope.launch {
            parentFlow.collect {
                observer(it)
            }
        }
    }

    // TODO @Synchronized wait korlin 1.9
    override fun unsubscribe(observer: (T) -> Unit) {
        val job = subscribers.remove(observer) ?: error("Observer not found")
        job.cancel()
    }
}
