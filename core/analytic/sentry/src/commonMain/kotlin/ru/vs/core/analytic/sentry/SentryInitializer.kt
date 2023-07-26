package ru.vs.core.analytic.sentry

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
        platformSentryInitializer.init(dsn)
    }
}
