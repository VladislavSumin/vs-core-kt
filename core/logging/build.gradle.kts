import ru.vs.build_logic.utils.android

plugins {
    id("ru.vs.convention.kmp.all")
}

android {
    namespace = "ru.vs.core.logging"
}

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
                implementation(coreLibs.kotlinLogging.core)
            }
        }
    }
}
