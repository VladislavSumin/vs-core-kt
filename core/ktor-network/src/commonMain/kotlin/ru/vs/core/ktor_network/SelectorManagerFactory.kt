package ru.vs.core.ktor_network

import io.ktor.network.selector.SelectorManager
import kotlinx.coroutines.Dispatchers

internal interface SelectorManagerFactory {
    fun createSelectorManager(): SelectorManager
}

internal class SelectorManagerFactoryImpl() : SelectorManagerFactory {
    override fun createSelectorManager(): SelectorManager {
        return SelectorManager(Dispatchers.Default)
    }
}
