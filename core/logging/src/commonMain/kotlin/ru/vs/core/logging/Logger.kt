package ru.vs.core.logging

fun interface Logger {
    fun log(level: LogLevel, tag: String, message: String, e: Throwable?)
}