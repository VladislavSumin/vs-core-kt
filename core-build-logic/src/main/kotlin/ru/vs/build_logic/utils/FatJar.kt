package ru.vs.build_logic.utils

import org.gradle.api.tasks.JavaExec
import org.gradle.api.tasks.bundling.Jar
import org.gradle.configurationcache.extensions.capitalized
import org.gradle.kotlin.dsl.register
import org.jetbrains.kotlin.gradle.targets.jvm.KotlinJvmTarget

fun KotlinJvmTarget.fatJar(mainClass: String, flavor: String = "main", jarName: String = "${project.name}-fat") {
    this.compilations.apply {
        val main = getByName(flavor)
        // See https://stackoverflow.com/questions/57168853/create-fat-jar-from-kotlin-multiplatform-project
        project.tasks.register<Jar>("buildFatJar${flavor.capitalized()}") {
            group = "application"
            manifest {
                attributes["Main-Class"] = mainClass
            }
            archiveBaseName.set(jarName)

            val dependencies = main.runtimeDependencyFiles.map { project.zipTree(it) }
            from(main.output.classesDirs, dependencies)

            exclude("META-INF/versions/**")
            exclude("META-INF/*.kotlin_module")
        }

        project.tasks.register<JavaExec>("runJvm${flavor.capitalized()}") {
            group = "application"
            getMainClass().set(mainClass)
            classpath = main.output.classesDirs
            classpath += main.runtimeDependencyFiles
        }
    }
}
