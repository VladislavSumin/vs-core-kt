package ru.vs.core.logging

object Log {
    inline fun i(tag: String, message: String, e: Throwable? = null) = log(LogLevel.INFO, tag, message, e)
    inline fun i(tag: String, crossinline message: () -> String, e: Throwable? = null) = log(LogLevel.INFO, tag, message, e)

    inline fun log(level: LogLevel, tag: String, crossinline message: () -> String, e: Throwable? = null) {
        log(level, tag, message(), e)
    }

    fun log(level: LogLevel, tag: String, message: String, e: Throwable? = null) {
        LogConfig.loggers.forEach { it.log(level, tag, message, e) }
    }
}
