package ru.vs.core.network

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import ru.vs.core.di.Modules
import ru.vs.core.network.service.NetworkService
import ru.vs.core.network.service.NetworkServiceImpl

fun Modules.coreNetwork() = DI.Module("core-network") {
    bindSingleton<NetworkService> { NetworkServiceImpl() }
}
