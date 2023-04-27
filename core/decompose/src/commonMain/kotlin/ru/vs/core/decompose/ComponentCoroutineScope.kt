package ru.vs.core.decompose

import com.arkivanov.essenty.lifecycle.Lifecycle
import com.arkivanov.essenty.lifecycle.doOnDestroy
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlin.coroutines.CoroutineContext

private fun ComponentCoroutineScope(
    context: CoroutineContext = Dispatchers.Main.immediate,
    lifecycle: Lifecycle
): CoroutineScope {
    val scope = CoroutineScope(context)
    lifecycle.doOnDestroy(scope::cancel)
    return scope
}

fun Lifecycle.createCoroutineScope(context: CoroutineContext = Dispatchers.Main.immediate) =
    ComponentCoroutineScope(context, this)

