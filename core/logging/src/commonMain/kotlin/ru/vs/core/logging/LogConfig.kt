package ru.vs.core.logging

object LogConfig {
    // TODO добавить синхронизацию?
    private val loggers_ = mutableListOf<Logger>()

    internal val loggers: List<Logger> = loggers_

    fun addLogger(logger: Logger) {
        loggers_.add(logger)
    }
}

// expect fun LogConfig.setupDefault()