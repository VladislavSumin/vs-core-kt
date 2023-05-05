package ru.vs.convention.kmp

plugins {
    id("ru.vs.convention.kmp.native")
}

kotlin {
    macosX64()
    macosArm64()

    @Suppress("UnusedPrivateMember")
    sourceSets {
        val nativeMain by getting {}

        val macosMain by creating {
            dependsOn(nativeMain)
        }

        val macosX64Main by getting {
            dependsOn(macosMain)
        }

        val macosArm64Main by getting {
            dependsOn(macosMain)
        }
    }
}
