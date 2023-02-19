package ru.vs.convention.android

import ru.vs.build_logic.utils.android

android {
    setCompileSdkVersion(33)

    defaultConfig {
        minSdk = 24
        targetSdk = 33
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}