import ru.vs.build_logic.utils.android

plugins {
    id("ru.vs.convention.kmp.all")
}

android {
    namespace = "ru.vs.core.okio"
}

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
                api(coreLibs.okio)
            }
        }
    }
}
