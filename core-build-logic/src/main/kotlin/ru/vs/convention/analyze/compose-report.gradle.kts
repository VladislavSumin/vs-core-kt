package ru.vs.convention.analyze

import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

/**
 * Включает отчеты compose о стабильности объектов и типе composable функций
 */

@Suppress("PropertyName")
val COMPOSE_COMPILER_PLUGIN = "plugin:androidx.compose.compiler.plugins.kotlin"

fun KotlinJvmOptions.addCompilerProperty(
    name: String,
    value: String,
) {
    freeCompilerArgs += listOf("-P", "${name}=${value}")
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        addCompilerProperty(
            name = "${COMPOSE_COMPILER_PLUGIN}:reportsDestination",
            value = "${project.buildDir.absolutePath}/compose_compiler"
        )
        addCompilerProperty(
            name = "${COMPOSE_COMPILER_PLUGIN}:metricsDestination",
            value = "${project.buildDir.absolutePath}/compose_compiler"
        )
    }
}
