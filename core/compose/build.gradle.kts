import ru.vs.build_logic.coreConfiguration

plugins {
    id("ru.vs.convention.kmp.all-compose")
    id("ru.vs.convention.compose")
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
                api(compose.material3)
                api(compose.materialIconsExtended)

//                api(compose.uiTooling)
//                api(compose.preview)
            }
        }
        named("jvmMain") {
            dependencies {
                api(compose.desktop.currentOs)
                implementation(coreLibs.kotlin.coroutines.swing)
            }
        }
        if (project.coreConfiguration.kmp.android.isEnabled) named("androidMain") {
            dependencies {
                api(coreLibs.android.activity.compose)
            }
        }
    }
}

configurations.all {
    // We add @Preview annotation manually for all platform
    // This need to use @Preview in common code
    // For avoid class duplication exclude original dependency
    exclude("org.jetbrains.compose.ui", "ui-tooling-preview-desktop")
}