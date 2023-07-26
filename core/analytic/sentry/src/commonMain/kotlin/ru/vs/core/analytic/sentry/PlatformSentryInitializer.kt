package ru.vs.core.analytic.sentry

import org.kodein.di.DirectDI

internal interface PlatformSentryInitializer {
    fun init(dsn:String)
}

internal expect fun DirectDI.createPlatformSentryInitializer(): PlatformSentryInitializer
