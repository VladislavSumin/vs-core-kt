package ru.vs.convention.kmp

plugins {
    id("kotlin-multiplatform")
}

kotlin {
    ios()
    iosSimulatorArm64()
    iosArm64()
}
