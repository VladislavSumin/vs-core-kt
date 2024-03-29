import ru.vs.build_logic.coreConfiguration
import ru.vs.build_logic.utils.android

plugins {
    id("ru.vs.convention.kmp.all")
    id("app.cash.sqldelight")
}

android {
    namespace = "ru.vs.core.database"
}

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
                implementation(projects.core.coroutines)
                implementation(projects.core.di)
            }
        }
        if (project.coreConfiguration.kmp.android.isEnabled) named("androidMain") {
            dependencies {
                implementation(coreLibs.sqldelight.android)
            }
        }
        named("jvmMain") {
            dependencies {
                implementation(coreLibs.sqldelight.sqlite)
            }
        }
        if (project.coreConfiguration.kmp.native.isEnabled) named("nativeMain") {
            dependencies {
                implementation(coreLibs.sqldelight.native)
            }
        }
        if (project.coreConfiguration.kmp.js.isEnabled) named("jsMain") {
            dependencies {
                // implementation(coreLibs.sqldelight.sqljs)
            }
        }
    }
}
