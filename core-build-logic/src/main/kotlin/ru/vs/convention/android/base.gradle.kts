package ru.vs.convention.android

import ru.vs.build_logic.coreConfiguration
import ru.vs.build_logic.utils.android

val configuration = project.coreConfiguration

android {
    setCompileSdkVersion(configuration.android.compileSdk)

    defaultConfig {
        minSdk = configuration.android.minSdk
        targetSdk = configuration.android.targetSdk
    }

    compileOptions {
        val version = JavaVersion.toVersion(project.coreConfiguration.jvmVersion)
        sourceCompatibility = version
        targetCompatibility = version
    }
}
