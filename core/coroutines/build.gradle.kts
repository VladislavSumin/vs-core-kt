import ru.vs.build_logic.utils.android

plugins {
    id("ru.vs.convention.kmp.all")
}

android {
    namespace = "ru.vs.core.coroutines"
}

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
                api(coreLibs.kotlin.coroutines.core)
            }
        }
    }
}
