plugins {
    id("ru.vs.convention.kmp.android-library")
    id("ru.vs.convention.kmp.jvm")
    id("ru.vs.convention.kmp.ios")
    // id("ru.vs.convention.kmp.js") compose (for desktop) don't support js now
    // id("ru.vs.convention.kmp.windows") compose (for desktop) don't support windows now
    // id("ru.vs.convention.kmp.linux") compose (for desktop) don't support linux now
    id("ru.vs.convention.kmp.macos")

    // KMM Test library don't support wasm now
    // id("ru.vs.convention.kmp.wasm")

    id("org.jetbrains.compose")
}

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
                api(coreLibs.decompose.core)
                api(coreLibs.decompose.jetbrains)

                // api(coreLibs.kodein.compose)

                implementation(projects.core.compose)
            }
        }
        named("androidMain") {
            dependencies {
                api(coreLibs.decompose.android)
            }
        }
    }
}
