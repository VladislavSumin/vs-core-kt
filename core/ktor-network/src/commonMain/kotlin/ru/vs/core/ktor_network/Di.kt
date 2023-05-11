package ru.vs.core.ktor_network

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import ru.vs.core.di.Modules
import ru.vs.core.di.i

fun Modules.coreKtorNetwork() = DI.Module("core-ktor-network") {
    bindSingleton<SelectorManagerFactory> { SelectorManagerFactoryImpl() }
    bindSingleton { i<SelectorManagerFactory>().createSelectorManager() }
}
