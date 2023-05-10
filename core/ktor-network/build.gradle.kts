plugins {
    id("ru.vs.convention.kmp.android-library")
    id("ru.vs.convention.kmp.jvm")
    id("ru.vs.convention.kmp.ios")
    // id("ru.vs.convention.kmp.js") ktor-network not support js target
    // id("ru.vs.convention.kmp.windows") ktor-network not support windows target
    id("ru.vs.convention.kmp.linux")
    id("ru.vs.convention.kmp.macos")
}

android {
    namespace = "ru.vs.core.ktor_network"
}

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
                implementation(projects.core.di)

                api(coreLibs.ktor.network)
            }
        }
    }
}
