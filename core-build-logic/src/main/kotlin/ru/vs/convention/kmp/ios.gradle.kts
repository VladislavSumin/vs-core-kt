package ru.vs.convention.kmp

plugins {
    id("ru.vs.convention.kmp.common")
}

kotlin {
    ios()
    iosSimulatorArm64()
    iosArm64()
}
