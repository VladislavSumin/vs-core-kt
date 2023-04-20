package ru.vs.build_logic.utils

import org.gradle.api.tasks.JavaExec
import org.gradle.api.tasks.bundling.Jar
import org.gradle.configurationcache.extensions.capitalized
import org.gradle.kotlin.dsl.register
import org.jetbrains.kotlin.gradle.targets.jvm.KotlinJvmTarget

/**
 * Creates tasks buildFarJar[flavor] and runJvm[flavor] for current [KotlinJvmTarget]
 * Jar file will be stored at build/libs/[jarName].jar
 *
 * @param mainClass - main class for store in manifest inside jar and for running with jvmRun[flavor]
 * @param flavor - flavor name (default is main)
 * @param jarName - name for jar archive
 */
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

            val dependencies = main.runtimeDependencyFiles.map { if (it.isDirectory) it else project.zipTree(it) }
            from(main.output.classesDirs, dependencies)

            exclude("META-INF/LICENSE")
            exclude("META-INF/DEPENDENCIES")
            exclude("META-INF/NOTICE")
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
