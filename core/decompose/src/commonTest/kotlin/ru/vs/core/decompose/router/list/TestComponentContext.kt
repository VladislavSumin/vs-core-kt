package ru.vs.core.decompose.router.list

import com.arkivanov.decompose.ComponentContext

internal class TestComponentContext<T : Any>(
    val data: T,
    context: ComponentContext
) : ComponentContext by context