package ru.vs.convention

import org.gradle.accessors.dm.LibrariesForCoreLibs
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin
import org.gradle.kotlin.dsl.the

/**
 * Default preset for KSP code generator modules
 */

plugins {
    kotlin("jvm")
}

if (project.name != "gradle-kotlin-dsl-accessors") {
    val coreLibs = rootProject.the<LibrariesForCoreLibs>()

    dependencies {
        implementation(coreLibs.ksp)
        implementation(coreLibs.kotlinpoet.core)
        implementation(coreLibs.kotlinpoet.ksp)
    }
}
