package ru.vs.convention

/**
 * Default setup for jb-compose
 */

plugins {
    id("org.jetbrains.compose")
}

compose {
    kotlinCompilerPlugin.set(dependencies.compiler.forKotlin("1.9.20"))
    kotlinCompilerPluginArgs.add("suppressKotlinVersionCompatibilityCheck=1.9.20")
}