import ru.vs.build_logic.utils.android

plugins {
    id("ru.vs.convention.kmp.all")
}

android {
    namespace = "ru.vs.core.logging"
}

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
                api(coreLibs.logging.kotlinLogging.core)
            }
        }

        named("jvmMain") {
            dependencies {
                api(coreLibs.logging.log4j.api)
                api(coreLibs.logging.log4j.core)
                api(coreLibs.logging.log4j.slf4j)
                api(coreLibs.logging.slf4j)
            }
        }
    }
}
