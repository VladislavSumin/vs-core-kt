import ru.vs.build_logic.coreConfiguration

plugins {
    id("ru.vs.convention.kmp.all")
}

android {
    namespace = "ru.vs.core.analytic.sentry"
}

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
                implementation(projects.core.di)
            }
        }

        named("jvmMain") {
            dependencies {
                implementation(coreLibs.sentry)
            }
        }

        if (project.coreConfiguration.kmp.android.isEnabled) named("androidMain") {
            dependencies {
                implementation(coreLibs.sentry)
            }
        }
    }
}
