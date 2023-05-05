package ru.vs.build_logic.utils

import org.gradle.api.file.DuplicatesStrategy
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
 * @param duplicatesStrategy - strategy for copy spec, sometime we may change it for some reason
 */
fun KotlinJvmTarget.fatJar(
    mainClass: String,
    flavor: String = "main",
    jarName: String = "${project.name}-fat",
    duplicatesStrategy: DuplicatesStrategy = DuplicatesStrategy.FAIL,
) {
    this.compilations.apply {
        val main = getByName(flavor)
        // See https://stackoverflow.com/questions/57168853/create-fat-jar-from-kotlin-multiplatform-project
        project.tasks.register<Jar>("buildFatJar${flavor.capitalized()}") {
            group = "application"

            manifest {
                attributes["Main-Class"] = mainClass
            }

            archiveBaseName.set(jarName)

            from(main.output.classesDirs)

            this.duplicatesStrategy = duplicatesStrategy

            // doFirst is workaround
            // from can`t accept provider as argument, see https://github.com/gradle/gradle/issues/22637
            // we need to calculate dependencies before tusk run (not at configuration time)
            doFirst {
                val dependencies = main.runtimeDependencyFiles.map {
                    check(it.exists()) { "${it.absolutePath} not exists" }
                    if (it.isDirectory) it else project.zipTree(it)
                }
                from(dependencies)
            }

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
