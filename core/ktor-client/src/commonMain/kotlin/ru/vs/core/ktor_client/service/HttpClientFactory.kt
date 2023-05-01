package ru.vs.core.ktor_client.service

import io.ktor.client.HttpClient
import org.kodein.di.DirectDI

// TODO все фабрики похожи, позможно стоит вынести только engine, остальное сделать в commonMain
interface HttpClientFactory {
    fun createHttpClientDefault(): HttpClient
}

internal expect fun DirectDI.createHttpClientFactory(): HttpClientFactory
