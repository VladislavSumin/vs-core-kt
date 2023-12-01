import ru.vs.build_logic.coreConfiguration

plugins {
    id("ru.vs.convention.kmp.all-compose")
    id("ru.vs.convention.compose")
}

android {
    namespace = "ru.vs.core.decompose"
}

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
                implementation(projects.core.compose)
                implementation(projects.core.di)

                api(coreLibs.decompose.core)
                api(coreLibs.decompose.jetbrains)
            }
        }
        if (project.coreConfiguration.kmp.android.isEnabled) named("androidMain") {
            dependencies {
                api(coreLibs.decompose.android)
            }
        }
    }
}
