package ru.vs.core.ktor_client.service

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.serialization.kotlinx.KotlinxWebsocketSerializationConverter
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.kodein.di.DirectDI
import org.kodein.di.instance

private class HttpClientFactoryImpl(private val json: Json) : HttpClientFactory {
    override fun createHttpClientDefault(): HttpClient {
        return HttpClient(CIO) {
            install(ContentNegotiation) {
                json(json)
            }
            install(WebSockets) {
                contentConverter = KotlinxWebsocketSerializationConverter(json)
            }
        }
    }
}

internal actual fun DirectDI.createHttpClientFactory(): HttpClientFactory {
    return HttpClientFactoryImpl(instance())
}
