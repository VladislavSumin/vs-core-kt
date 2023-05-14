package ru.vs.convention.kmp

import ru.vs.build_logic.coreConfiguration

plugins {
    id("ru.vs.convention.kmp.native")
}

if (project.coreConfiguration.kmp.linuxX64.isEnabled) kotlin {
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
