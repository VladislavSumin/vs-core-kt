package ru.vs.core.analytic.sentry

import io.sentry.kotlin.multiplatform.OptionsConfiguration
import org.kodein.di.DirectDI

internal interface PlatformSentryInitializer {
    fun init(configuration: OptionsConfiguration)
}

internal expect fun DirectDI.createPlatformSentryInitializer(): PlatformSentryInitializer
