package ru.vs.convention.android

import ru.vs.build_logic.coreConfiguration
import ru.vs.build_logic.utils.android

android {
    setCompileSdkVersion(34)

    defaultConfig {
        minSdk = 24
        targetSdk = 34
    }

    compileOptions {
        val version = JavaVersion.toVersion(project.coreConfiguration.jvmVersion)
        sourceCompatibility = version
        targetCompatibility = version
    }
}
