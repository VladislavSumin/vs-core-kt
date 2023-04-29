import ru.vs.build_logic.utils.android

plugins {
    id("ru.vs.convention.kmp.all")
    kotlin("plugin.serialization")
}

android {
    namespace = "ru.vs.core.serialization.core"
}

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
                api(coreLibs.kotlin.serialization.json)
            }
        }
    }
}
