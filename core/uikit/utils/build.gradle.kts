plugins {
    id("ru.vs.convention.kmp.all-compose")
    id("ru.vs.convention.compose")
}

android {
    namespace = "ru.vs.core.uikit.utils"
}

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
                implementation(projects.core.compose)
            }
        }
    }
}
