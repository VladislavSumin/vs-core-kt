package ru.vs.convention.kmp

import ru.vs.build_logic.coreConfiguration

plugins {
    id("ru.vs.convention.kmp.native")
}

if (coreConfiguration.kmp.macos.isEnabled) kotlin {
    if (coreConfiguration.kmp.macosX64.isEnabled) macosX64()
    if (coreConfiguration.kmp.macosArm64.isEnabled) macosArm64()

    @Suppress("UnusedPrivateMember")
    sourceSets {
        val nativeMain by getting {}

        val macosMain by creating {
            dependsOn(nativeMain)
        }

        if (coreConfiguration.kmp.macosX64.isEnabled) {
            val macosX64Main by getting {
                dependsOn(macosMain)
            }
        }

        if (coreConfiguration.kmp.macosArm64.isEnabled) {
            val macosArm64Main by getting {
                dependsOn(macosMain)
            }
        }
    }
}
