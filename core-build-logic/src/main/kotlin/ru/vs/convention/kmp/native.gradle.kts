package ru.vs.convention.kmp

/**
 * Creating nativeMain - source sets shared for all native targets
 */

plugins {
    id("ru.vs.convention.kmp.common")
}

kotlin {

    @Suppress("UnusedPrivateMember")
    sourceSets {
        val commonMain by getting {}
        val nativeMain by creating {
            dependsOn(commonMain)
        }
    }
}
