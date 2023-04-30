import ru.vs.build_logic.utils.android

plugins {
    id("ru.vs.convention.kmp.all")
    kotlin("plugin.serialization")
}

android {
    namespace = "ru.vs.core.serialization.json"
}

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
                api(projects.core.serialization.core)
                api(coreLibs.kotlin.serialization.json)

                implementation(projects.core.di)
            }
        }
    }
}
