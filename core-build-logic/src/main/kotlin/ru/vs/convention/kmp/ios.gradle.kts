package ru.vs.convention.kmp

plugins {
    id("ru.vs.convention.kmp.native")
}

kotlin {
    // TODO мапинг названий нужен что бы композ догадался где что искарть, ищет он по таким именам а поменять нельзя
    iosX64("uikitX64")
    iosArm64("uikitArm64")
    // iosSimulatorArm64("uikitSimulatorArm64")

    @Suppress("UnusedPrivateMember")
    sourceSets {
        val nativeMain by getting {}

        val uikitMain by creating {
            dependsOn(nativeMain)
        }

        val uikitX64Main by getting {
            dependsOn(uikitMain)
        }

        val uikitArm64Main by getting {
            dependsOn(uikitMain)
        }
    }
}
