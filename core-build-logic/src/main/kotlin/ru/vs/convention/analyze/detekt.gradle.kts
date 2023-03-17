package ru.vs.convention.analyze

import io.gitlab.arturbosch.detekt.Detekt
import org.gradle.accessors.dm.LibrariesForCoreLibs
import ru.vs.build_logic.tasks.CopyFromResourceTask

plugins {
    id("io.gitlab.arturbosch.detekt")
}

val libs = the<LibrariesForCoreLibs>()

val detektConfigFile = project.buildDir.resolve("detektConfig").resolve("detekt.yml")

val copyDetektConfigTaskProvider = tasks.register<CopyFromResourceTask>("copyDetektConfig") {
    from.set("ru/vs/build_logic/detekt.yml")
    to.set(detektConfigFile)
}

// Конфигурирем на уровне тасок, а не на уровне плагина, так как таски созданные в ручную
// не подтягивают дефолтные значения из конфигурации плагина
tasks.withType<Detekt>().configureEach {
    autoCorrect = true
    parallel = true
    buildUponDefaultConfig = true
    config.setFrom(files(detektConfigFile))

    dependsOn(copyDetektConfigTaskProvider)
}

// Исправляем путь к файлам только для дефолтных detekt тасок
tasks.named<Detekt>("detekt").configure {
    source = fileTree(project.projectDir) {
        include("src/**/*")
        include("build.gradle.kts")
        include("settings.gradle.kts")
    }
}

dependencies {
    detektPlugins(libs.detekt.formatting)
}