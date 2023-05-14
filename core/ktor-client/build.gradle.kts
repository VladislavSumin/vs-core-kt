import ru.vs.build_logic.coreConfiguration

plugins {
    id("ru.vs.convention.kmp.all")
}

android {
    namespace = "ru.vs.core.ktor_client"
}

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
                implementation(projects.core.di)
                implementation(projects.core.serialization.json)

                api(coreLibs.ktor.serialization.json)
                api(coreLibs.ktor.client.core)
                api(coreLibs.ktor.client.contentNegotiation)
                api(coreLibs.ktor.client.websocket)
            }
        }

        named("jvmMain") {
            dependencies {
                implementation(coreLibs.ktor.client.cio)
            }
        }

        named("androidMain") {
            dependencies {
                implementation(coreLibs.ktor.client.cio)
            }
        }

        named("macosMain") {
            dependencies {
                implementation(coreLibs.ktor.client.cio)
            }
        }

        named("uikitMain") {
            dependencies {
                implementation(coreLibs.ktor.client.cio)
            }
        }

        if (project.coreConfiguration.kmp.linuxX64.isEnabled) named("linuxX64Main") {
            dependencies {
                implementation(coreLibs.ktor.client.cio)
            }
        }

        if (project.coreConfiguration.kmp.js.isEnabled) named("jsMain") {
            dependencies {
                implementation(coreLibs.ktor.client.js)
            }
        }

        if (project.coreConfiguration.kmp.mingwX64.isEnabled) named("mingwX64Main") {
            dependencies {
                implementation(coreLibs.ktor.client.winhttp)
            }
        }
    }
}