import ru.vs.build_logic.utils.android

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

    id("org.jetbrains.compose")
}

android {
    namespace = "ru.vs.core.compose"
}

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
                api(compose.runtime)
                api(compose.foundation)
                api(compose.material)

//                api(compose.uiTooling)
//                api(compose.preview)
            }
        }
        named("jvmMain") {
            dependencies {
                api(compose.desktop.currentOs)
            }
        }
        named("androidMain") {
            dependencies {
                api(coreLibs.android.activity.compose)
            }
        }
    }
}