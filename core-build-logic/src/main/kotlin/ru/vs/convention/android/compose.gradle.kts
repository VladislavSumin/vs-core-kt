package ru.vs.convention.android

import org.gradle.accessors.dm.LibrariesForCoreLibs
import org.gradle.kotlin.dsl.the
import ru.vs.build_logic.utils.android

val libs = rootProject.the<LibrariesForCoreLibs>()

android {
    buildFeatures.apply {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.android.compose.compiler.get()
    }
}
