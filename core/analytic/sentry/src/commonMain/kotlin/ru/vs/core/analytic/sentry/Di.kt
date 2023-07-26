package ru.vs.core.analytic.sentry

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import ru.vs.core.di.Modules
import ru.vs.core.di.i

fun Modules.coreAnalyticSentry() = DI.Module("core-analytic-sentry") {
    bindSingleton<PlatformSentryInitializer> { createPlatformSentryInitializer() }
    bindSingleton<SentryInitializer> { SentryInitializerImpl(i()) }
}
