package ru.vs.convention.serialization

import org.gradle.accessors.dm.LibrariesForCoreLibs
import org.gradle.kotlin.dsl.kotlin
import org.gradle.kotlin.dsl.named
import org.gradle.kotlin.dsl.the

plugins {
    id("kotlin-multiplatform")
    kotlin("plugin.serialization")
}

if (project.name != "gradle-kotlin-dsl-accessors") {
    val coreLibs = rootProject.the<LibrariesForCoreLibs>()

    kotlin {
        sourceSets {
            named("commonMain") {
                dependencies {
                    implementation(coreLibs.vs.core.serialization.json)
                }
            }
        }
    }
}
