import ru.vs.build_logic.coreConfiguration

plugins {
    id("ru.vs.convention.kmp.android-library")
    id("ru.vs.convention.kmp.jvm")
    id("ru.vs.convention.kmp.ios")
    id("ru.vs.convention.kmp.js")
    // id("ru.vs.convention.kmp.windows") compose (for desktop) don't support windows now
    // id("ru.vs.convention.kmp.linux") compose (for desktop) don't support linux now
    id("ru.vs.convention.kmp.macos")

    // KMM Test library don't support wasm now
    // id("ru.vs.convention.kmp.wasm")

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
