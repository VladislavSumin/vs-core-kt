plugins {
    id("ru.vs.convention.kmp.all")
}

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
                api(coreLibs.kodein.core)
            }
        }
    }
}