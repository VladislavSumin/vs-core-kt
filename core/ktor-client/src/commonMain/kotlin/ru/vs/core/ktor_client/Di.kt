package ru.vs.core.ktor_client

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import ru.vs.core.di.Modules
import ru.vs.core.di.i
import ru.vs.core.ktor_client.service.HttpClientFactory
import ru.vs.core.ktor_client.service.createHttpClientFactory

fun Modules.coreKtorClient() = DI.Module("core-ktor-client") {
    bindSingleton { createHttpClientFactory() }
    bindSingleton { i<HttpClientFactory>().createHttpClientDefault() }
}
