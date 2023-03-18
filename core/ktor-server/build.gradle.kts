import ru.vs.build_logic.utils.android

plugins {
    // Check supported platforms here: https://ktor.io/docs/supported-platforms.html
    id("ru.vs.convention.kmp.android-library")
    id("ru.vs.convention.kmp.jvm")
    id("ru.vs.convention.kmp.ios")
    // id("ru.vs.convention.kmp.windows") ktor not support windows for now
    id("ru.vs.convention.kmp.linux")
    id("ru.vs.convention.kmp.macos")
}

android {
    namespace = "ru.vs.core.ktor-server"
}

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
                api(coreLibs.ktor.server.core)
                api(coreLibs.ktor.server.cio)
            }
        }
    }
}
