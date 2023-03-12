package ru.vs.convention.android

import ru.vs.build_logic.configuration
import ru.vs.build_logic.utils.android

android {
    setCompileSdkVersion(33)

    defaultConfig {
        minSdk = 24
        targetSdk = 33
    }

    compileOptions {
        val version = JavaVersion.toVersion(project.configuration.jvmVersion)
        sourceCompatibility = version
        targetCompatibility = version
    }
}