package ru.vs.convention.kmp

plugins {
    id("ru.vs.convention.kmp.native")
}

kotlin {
    mingwX64()

    @Suppress("UnusedPrivateMember")
    sourceSets {
        val nativeMain by getting {}

        val mingwX64Main by getting {
            dependsOn(nativeMain)
        }
    }
}
