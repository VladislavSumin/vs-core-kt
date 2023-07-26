package ru.vs.core.network.service

import io.ktor.network.selector.SelectorManager
import io.ktor.network.sockets.Socket
import io.ktor.network.sockets.aSocket
import io.ktor.network.sockets.openReadChannel
import io.ktor.network.sockets.openWriteChannel
import io.ktor.utils.io.ByteReadChannel
import io.ktor.utils.io.ByteWriteChannel

/**
 * Primary interface to **any** network interaction
 */
interface NetworkService {
    /**
     * Open raw tcp socket
     *
     * See overload extension functions below
     *
     * @param hostname remote host hostname
     * @param port remote host port
     */
    suspend fun openTcpSocket(hostname: String, port: Int): Socket
}

suspend inline fun NetworkService.openTcpSocket(
    hostname: String,
    port: Int,
    block: (socket: Socket) -> Unit,
) = openTcpSocket(hostname, port).use { block(it) }

suspend inline fun NetworkService.openTcpSocket(
    hostname: String,
    port: Int,
    block: (
        readChannel: ByteReadChannel,
        writeChannel: ByteWriteChannel,
    ) -> Unit,
) = openTcpSocket(hostname, port) { socket: Socket ->
    val readChannel: ByteReadChannel = socket.openReadChannel()
    val writeChannel: ByteWriteChannel = socket.openWriteChannel()
    block(readChannel, writeChannel)
}

internal class NetworkServiceImpl : NetworkService {
    private val selector = SelectorManager()

    override suspend fun openTcpSocket(hostname: String, port: Int): Socket {
        return aSocket(selector)
            .tcp()
            .connect(hostname, port)
    }
}
