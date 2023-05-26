package ru.vs.core.decompose.router.list

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.arkivanov.essenty.instancekeeper.getOrCreate

private var counter = 0

internal class TestComponentContext<T : Any>(
    val data: T,
    context: ComponentContext
) : ComponentContext by context {
    val keepInstanceUniqueValue = instanceKeeper.getOrCreate { RandomValue() }.value

    private class RandomValue : InstanceKeeper.Instance {
        val value = counter++
        override fun onDestroy() {
            // no action
        }
    }
}