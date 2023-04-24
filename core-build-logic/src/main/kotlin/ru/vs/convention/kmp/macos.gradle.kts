package ru.vs.convention.kmp

plugins {
    id("ru.vs.convention.kmp.common")
}

kotlin {
    macosX64()
    macosArm64()

    sourceSets {
        val commonMain by getting {}

        val macosMain by creating {
            dependsOn(commonMain)
        }

        val macosX64Main by getting {
            dependsOn(macosMain)
        }

        val macosArm64Main by getting {
            dependsOn(macosMain)
        }
    }
}
