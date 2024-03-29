package ru.vs.convention.kmp

import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.withType

plugins {
    id("kotlin-multiplatform")
}

kotlin {
    sourceSets {
        named("commonTest") {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
