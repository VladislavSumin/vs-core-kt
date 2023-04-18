package ru.vs.build_logic.utils

import org.gradle.api.Project

fun Project.stringProperty(name: String, defaultValue: String): String = findProperty(name)?.toString() ?: defaultValue
fun Project.booleanProperty(name: String, defaultValue: Boolean): Boolean =
    findProperty(name)?.toString()?.toBoolean() ?: defaultValue
