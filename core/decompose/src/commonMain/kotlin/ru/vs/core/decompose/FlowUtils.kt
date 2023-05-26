package ru.vs.core.decompose

import com.arkivanov.decompose.value.Value
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

fun <T : Any> StateFlow<T>.asValue(scope: CoroutineScope): Value<T> {
    return FlowAsValue(this, scope)
}

private class FlowAsValue<T : Any>(
    private val parentFlow: StateFlow<T>,
    private val scope: CoroutineScope,
) : Value<T>() {
    private val subscribers = mutableMapOf<(T) -> Unit, Job>()
    override val value: T get() = parentFlow.value

    // TODO @Synchronized wait kotlin 1.9
    override fun subscribe(observer: (T) -> Unit) {
        check(!subscribers.contains(observer))
        subscribers[observer] = scope.launch {
            parentFlow.collect {
                observer(it)
            }
        }
    }

    // TODO @Synchronized wait kotlin 1.9
    override fun unsubscribe(observer: (T) -> Unit) {
        val job = subscribers.remove(observer) ?: error("Observer not found")
        job.cancel()
    }
}