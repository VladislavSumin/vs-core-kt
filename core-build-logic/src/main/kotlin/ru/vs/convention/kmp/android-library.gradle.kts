package ru.vs.convention.kmp

plugins {
    id("ru.vs.convention.kmp.common")
    id("com.android.library")
    id("ru.vs.convention.android.base")
}

kotlin {
    androidTarget()

    sourceSets {
        // TODO мониторить нюанс
        // Кажется что если использовать этот конвеншен внутри другого конфеншена в другом билде, то гредл 2 раза прогоняет
        // generatePrecompiledScriptPluginAccessors для этого конвеншена.
        // Но что самое интересное при втором прогоне похоже не пробрасываются переменные из gradle.properties.
        // А без переменной kotlin.mpp.androidSourceSetLayoutVersion=2 мы падаем при попытке обратиться к androidUnitTest
        // Никакой внятной ишуи на эту тему я не нашел, возможно позже, если не поправят стоит ее завести
        // Как временный фикс игнорируем блок ниже если определили прогон generatePrecompiledScriptPluginAccessors
        if (project.name == "gradle-kotlin-dsl-accessors") return@sourceSets

        named("androidUnitTest") {
            dependencies {
                implementation(kotlin("test-junit5"))
            }
        }
    }
}

android {
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
}
