package ru.vs.core.analytic.sentry

import android.content.Context
import io.sentry.kotlin.multiplatform.OptionsConfiguration
import io.sentry.kotlin.multiplatform.Sentry
import org.kodein.di.DirectDI
import org.kodein.di.instance

internal actual fun DirectDI.createPlatformSentryInitializer(): PlatformSentryInitializer {
    return PlatformSentryInitializerImpl(instance())
}

private class PlatformSentryInitializerImpl(
    private val context: Context
) : PlatformSentryInitializer {
    override fun init(configuration: OptionsConfiguration) {
        Sentry.init(context, configuration)
    }
}
