import ru.vs.build_logic.coreConfiguration

plugins {
    id("ru.vs.convention.kmp.all")
}

android {
    namespace = "ru.vs.core.di"
}

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
                api(coreLibs.kodein.core)
            }
        }
        if (project.coreConfiguration.kmp.android.isEnabled) named("androidMain") {
            dependencies {
                api(coreLibs.kodein.android)
            }
        }
    }
}
