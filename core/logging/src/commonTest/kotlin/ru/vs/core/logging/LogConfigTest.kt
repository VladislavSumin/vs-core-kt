package ru.vs.core.logging

import kotlin.test.Test
import kotlin.test.assertEquals

class LogConfigTest {

    @Test
    fun addLogger() {
        val logger = Logger { _, _, _, _ -> }
        LogConfig.addLogger(logger)
        assertEquals(logger, LogConfig.loggers[0])
    }
}