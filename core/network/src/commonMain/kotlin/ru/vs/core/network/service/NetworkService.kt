package ru.vs.core.network.service

import io.ktor.network.selector.SelectorManager
import io.ktor.network.sockets.Socket
import io.ktor.network.sockets.aSocket

/**
 * Primary interface to **any** network interaction
 */
interface NetworkService {
    /**
     * Open raw tcp socket
     * @param hostname remote host hostname
     * @param port remote host port
     */
    suspend fun openTcpSocket(hostname: String, port: Int): Socket
}

internal class NetworkServiceImpl : NetworkService {
    private val selector = SelectorManager()

    override suspend fun openTcpSocket(hostname: String, port: Int): Socket {
        return aSocket(selector)
            .tcp()
            .connect(hostname, port)
    }
}
