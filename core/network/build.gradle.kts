plugins {
    id("ru.vs.convention.kmp.android-library")
    id("ru.vs.convention.kmp.jvm")
    id("ru.vs.convention.kmp.linux")
}

android {
    namespace = "ru.vs.core.network"
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
