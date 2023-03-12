package ru.vs.build_logic.utils

import org.gradle.api.Project

fun Project.stringProperty(name: String, defaultValue: String): String = findProperty(name)?.toString() ?: defaultValue
