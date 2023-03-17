package ru.vs.convention.kmp

plugins {
    id("ru.vs.convention.kmp.common")
    id("com.android.library")
    id("ru.vs.convention.android.base")
}

kotlin {
    android()

    sourceSets {
        named("androidUnitTest") {
            dependencies {
                implementation(kotlin("test-junit5"))
            }
        }
    }
}

android {
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
}