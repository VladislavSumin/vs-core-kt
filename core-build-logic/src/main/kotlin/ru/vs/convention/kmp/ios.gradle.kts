package ru.vs.convention.kmp

import ru.vs.build_logic.coreConfiguration

plugins {
    id("ru.vs.convention.kmp.native")
}

if (coreConfiguration.kmp.ios.isEnabled) kotlin {
    // TODO мапинг названий нужен что бы композ догадался где что искарть, ищет он по таким именам а поменять нельзя
    if (coreConfiguration.kmp.iosX64.isEnabled) iosX64("uikitX64")
    if (coreConfiguration.kmp.iosArm64.isEnabled) iosArm64("uikitArm64")
    // iosSimulatorArm64("uikitSimulatorArm64")

    @Suppress("UnusedPrivateMember")
    sourceSets {
        val nativeMain by getting {}

        val uikitMain by creating {
            dependsOn(nativeMain)
        }

        if (coreConfiguration.kmp.iosX64.isEnabled) {
            val uikitX64Main by getting {
                dependsOn(uikitMain)
            }
        }

        if (coreConfiguration.kmp.iosArm64.isEnabled) {
            val uikitArm64Main by getting {
                dependsOn(uikitMain)
            }
        }
    }
}
