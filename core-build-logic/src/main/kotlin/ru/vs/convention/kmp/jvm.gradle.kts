package ru.vs.convention.kmp

plugins {
    id("ru.vs.convention.kmp.common")
}

kotlin {
    jvm()

    sourceSets {
        named("jvmTest") {
            dependencies {
                implementation(kotlin("test-junit5"))
            }
        }
    }
}
