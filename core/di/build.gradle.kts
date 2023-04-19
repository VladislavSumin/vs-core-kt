import ru.vs.build_logic.utils.android

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
        named("androidMain") {
            dependencies {
                api(coreLibs.kodein.android)
            }
        }
    }
}