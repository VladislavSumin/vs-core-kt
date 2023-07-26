plugins {
    id("ru.vs.convention.kmp.android-library")
    id("ru.vs.convention.kmp.jvm")
    id("ru.vs.convention.kmp.ios")
    // id("ru.vs.convention.kmp.js") sentry don't support js
    id("ru.vs.convention.kmp.windows")
    id("ru.vs.convention.kmp.linux")
    id("ru.vs.convention.kmp.macos")
}

android {
    namespace = "ru.vs.core.analytic.sentry"
}

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
                implementation(projects.core.di)
                implementation(coreLibs.sentry)
            }
        }
    }
}
