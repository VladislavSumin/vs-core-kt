package ru.vs.core.decompose.viewmodel

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * @param S state type, represents current state
 * @param I intent type, represents incoming intents
 * @param E events type, represents out-coming events
 */
abstract class MviViewModel<S, I, E> : ViewModel() {

    private val intentsChannel = Channel<I>()
    private val eventsChannel = Channel<E>()

    val events: ReceiveChannel<E> = eventsChannel

    val stateFlow: StateFlow<S>
    protected val state: S get() = stateFlow.value

    init {
        @Suppress("LeakingThis")
        val mutableStateFlow = MutableStateFlow(createInitialSate())
        stateFlow = mutableStateFlow
        viewModelScope.launch {
            for (intent in intentsChannel) {
                mutableStateFlow.value = (processIntent(intent))
            }
        }
    }

    protected abstract fun createInitialSate(): S
    protected abstract suspend fun processIntent(intent: I): S

    protected suspend fun receiveIntent(intent: I) {
        intentsChannel.send(intent)
    }

    protected suspend fun produceEvent(event: E) {
        eventsChannel.send(event)
    }
}