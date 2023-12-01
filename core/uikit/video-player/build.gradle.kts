plugins {
    id("ru.vs.convention.kmp.all-compose")
    id("ru.vs.convention.compose")
}

android {
    namespace = "ru.vs.core.uikit.video_player"
}

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
                implementation(projects.core.compose)
                implementation(projects.core.logging)
            }
        }
        named("jvmMain") {
            dependencies {
                implementation(coreLibs.vlcj)
            }
        }
    }
}
