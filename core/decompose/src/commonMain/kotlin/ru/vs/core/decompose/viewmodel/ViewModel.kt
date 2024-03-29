package ru.vs.core.decompose.viewmodel

import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/**
 * Реализация паттерна MVVM, похожа на ViewModel от Google, но использует механизмы lifecycle из decompose.
 *
 * Так же обладает полезными функциями-расширениями для удобного решения типовых задач встречающихся в viewModel.
 */
abstract class ViewModel : InstanceKeeper.Instance {
    protected val viewModelScope = CoroutineScope(Dispatchers.Main.immediate)

    /**
     * Укороченная версия [stateIn] с использованием [viewModelScope] и [SharingStarted.Eagerly] по умолчанию.
     */
    protected fun <T> Flow<T>.stateIn(
        initialValue: T,
        started: SharingStarted = SharingStarted.Eagerly,
    ): StateFlow<T> {
        return stateIn(
            scope = viewModelScope,
            started = started,
            initialValue = initialValue,
        )
    }

    /**
     * Укороченная версия [CoroutineScope.launch] использующая в качестве скоупа [viewModelScope].
     *
     * Так же отличается тем что возвращает [Unit] вместо [Job] для возможности использования короткого синтаксиса:
     * ```
     * fun sampleFunction() = launch { ... }
     * ```
     */
    protected fun launch(
        context: CoroutineContext = EmptyCoroutineContext,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        block: suspend CoroutineScope.() -> Unit
    ) {
        viewModelScope.launch(context, start, block)
    }

    /**
     * Вызывается при уничтожении экземпляра [ViewModel]. Закрывает [CoroutineScope].
     *
     * Этот метод специально объявлен как final, если вам нужно завершить какие-либо процессы при уничтожении
     * [ViewModel], то используйте для этой задачи факт отмены [viewModelScope].
     */
    final override fun onDestroy() {
        viewModelScope.cancel()
    }
}
