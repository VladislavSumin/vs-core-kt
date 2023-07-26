package ru.vs.core.analytic.sentry

import io.sentry.kotlin.multiplatform.OptionsConfiguration


interface SentryInitializer {
    /**
     * @param dsn sentry project identifier
     */
    fun init(dsn: String)
}

internal class SentryInitializerImpl(
    private val platformSentryInitializer: PlatformSentryInitializer,
) : SentryInitializer {
    override fun init(dsn: String) {
        val configuration: OptionsConfiguration = { options ->
            options.dsn = dsn
        }
        platformSentryInitializer.init(configuration)
    }
}
