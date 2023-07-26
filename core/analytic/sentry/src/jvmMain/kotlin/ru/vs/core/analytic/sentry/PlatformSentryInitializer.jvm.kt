package ru.vs.core.analytic.sentry

import io.sentry.kotlin.multiplatform.Sentry
import org.kodein.di.DirectDI

internal actual fun DirectDI.createPlatformSentryInitializer(): PlatformSentryInitializer {
    return PlatformSentryInitializerImpl()
}

private class PlatformSentryInitializerImpl : PlatformSentryInitializer {
    override fun init(dsn: String) {
        Sentry.init {
            it.dsn = dsn
        }
    }
}
