package ru.vs.core.uikit.video_player

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import uk.co.caprica.vlcj.media.callback.nonseekable.NonSeekableInputStreamMedia
import java.io.IOException
import java.io.InputStream
import java.io.PipedInputStream
import java.io.PipedOutputStream

internal class FlowOfByteArraysMedia(
    private val source: Playable.FlowOfByteArrays,
    private val scope: CoroutineScope,
) : NonSeekableInputStreamMedia() {
    private var job: Job? = null

    override fun onGetSize(): Long = 0

    override fun onOpenStream(): InputStream {
        val output = PipedOutputStream()
        val input = PipedInputStream(output, 1024 * 1024)
        println("QWQW: onOpenStream()")

        println("QWQW: scope - ${scope.isActive}")
        //TODO проверить че по потокам
        job = scope.launch(newSingleThreadContext("1")) {
            println("QWQW: launchStart()")
            try {
                source.data.collect {
                    var tryForTheWin = true
                    while (tryForTheWin) {
                        try {
                            output.write(it)
                            tryForTheWin = false
                        } catch (e: IOException) {
                            if (e.message != "Read end dead") throw e
                            println("QWQW: this shit are happen")
                            delay(1)
                        }
                    }
                }
            } finally {
                println("QWQW: closeJob")
                output.close()
            }
        }

        return input
    }

    override fun onCloseStream(inputStream: InputStream?) {
        println("QWQW: onCloseStream()")
        job!!.cancel()
        job = null
    }
}