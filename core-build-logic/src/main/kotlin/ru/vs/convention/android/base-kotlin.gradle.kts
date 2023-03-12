package ru.vs.convention.android

import ru.vs.build_logic.configuration
import ru.vs.build_logic.utils.android
import ru.vs.build_logic.utils.kotlinOptions

plugins {
    id("ru.vs.convention.android.base")
    kotlin("android")
}

android {
    kotlinOptions {
        jvmTarget = project.configuration.jvmVersion
    }
}