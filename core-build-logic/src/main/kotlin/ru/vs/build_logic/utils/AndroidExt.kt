package ru.vs.build_logic.utils

import com.android.build.gradle.BaseExtension
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions

val Project.android: BaseExtension
    get() = extensions.getByType()

fun Project.android(block: BaseExtension.() -> Unit) = android.block()

val BaseExtension.kotlinOptions: KotlinJvmOptions
    get() = (this as ExtensionAware).extensions.getByType()

fun BaseExtension.kotlinOptions(block: KotlinJvmOptions.() -> Unit) = kotlinOptions.block()
