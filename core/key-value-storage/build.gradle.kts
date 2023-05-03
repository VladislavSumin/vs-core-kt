import ru.vs.build_logic.utils.android

plugins {
    id("ru.vs.convention.kmp.all")
}

android {
    namespace = "ru.vs.core.key_value_storage"
}

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
                api(coreLibs.multiplatformSettings.core)
                api(coreLibs.multiplatformSettings.coroutines)

                implementation(projects.core.coroutines)
                implementation(projects.core.di)
            }
        }
    }
}
