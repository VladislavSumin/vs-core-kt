package ru.vs.convention.test

import org.gradle.accessors.dm.LibrariesForCoreLibs


plugins {
    id("ru.vs.convention.kmp.common")
    id("org.jetbrains.kotlin.plugin.allopen")
    id("org.jetbrains.kotlinx.benchmark")
}

allOpen {
    annotation("org.openjdk.jmh.annotations.State")
}

if (project.name != "gradle-kotlin-dsl-accessors") {
    val coreLibs = rootProject.the<LibrariesForCoreLibs>()

    kotlin {
        jvm {
            this.compilations.create("benchmark") {
                println("AAAAA $defaultSourceSetName")
                associateWith(compilations.getByName("main"))
                dependencies {
                    implementation(coreLibs.kotlin.benchmark)
                }
            }
        }

        sourceSets {
            commonMain {
                dependencies {
                    implementation(coreLibs.kotlin.benchmark)
                }
            }
        }
    }

    benchmark {
        targets {
            register("jvm")
            // TODO в общем эта фигня пока не работает
            // https://github.com/Kotlin/kotlinx-benchmark/pull/112
            // ждем следующиего релиза плагина в который войдет пр выше
            register("jvmBenchmark")
        }
    }
}
