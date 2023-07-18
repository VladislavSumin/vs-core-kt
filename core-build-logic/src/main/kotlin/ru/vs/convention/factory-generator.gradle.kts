package ru.vs.convention

import org.gradle.accessors.dm.LibrariesForCoreLibs

/**
 * Default setup for factory-generator
 */

plugins {
    id("kotlin-multiplatform")
    id("ru.vs.convention.ksp-kmp-hack")
}

if (project.name != "gradle-kotlin-dsl-accessors") {
    val coreLibs = rootProject.the<LibrariesForCoreLibs>()

    kotlin {
        sourceSets {
            named("commonMain") {
                dependencies {
                    implementation(coreLibs.vs.core.factoryGenerator.api)
                }
            }
        }
    }

    dependencies {
        add("kspCommonMainMetadata", coreLibs.vs.core.factoryGenerator.ksp)
    }
}
