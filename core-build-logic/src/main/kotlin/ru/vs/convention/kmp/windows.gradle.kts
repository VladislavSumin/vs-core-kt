package ru.vs.convention.kmp

import ru.vs.build_logic.coreConfiguration

plugins {
    id("ru.vs.convention.kmp.native")
}

if (project.coreConfiguration.kmp.mingwX64.isEnabled) kotlin {
    mingwX64()

    @Suppress("UnusedPrivateMember")
    sourceSets {
        val nativeMain by getting {}

        val mingwX64Main by getting {
            dependsOn(nativeMain)
        }
    }
}
