package ru.vs.convention.kmp

plugins {
    id("ru.vs.convention.kmp.native")
}

kotlin {
    linuxX64()
    // linuxArm64()

    @Suppress("UnusedPrivateMember")
    sourceSets {
        val nativeMain by getting {}

        val linuxX64Main by getting {
            dependsOn(nativeMain)
        }
    }
}
