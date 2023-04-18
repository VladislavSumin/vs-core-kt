package ru.vs.build_logic.utils

import org.gradle.api.Project

fun Project.stringProperty(name: String, defaultValue: String): String = findProperty(name)?.toString() ?: defaultValue
fun Project.stringProperty(name: String): String =
    findProperty(name)?.toString() ?: error("property $name not found in ${project.name}")

fun Project.booleanProperty(name: String, defaultValue: Boolean): Boolean =
    findProperty(name)?.toString()?.toBoolean() ?: defaultValue
