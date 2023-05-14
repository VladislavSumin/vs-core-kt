package ru.vs.build_logic

import org.gradle.api.Project
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.findByType
import ru.vs.build_logic.utils.Configuration
import ru.vs.build_logic.utils.PropertyProvider

/**
 * Project configuration class
 * proxies all external configuration (by properties or by environment variables
 */
@Suppress("UnnecessaryAbstractClass")
abstract class CoreProjectConfiguration(propertyProvider: PropertyProvider) :
    Configuration("ru.vs.core", propertyProvider) {

    val jvmVersion = property("jvmVersion", "17")

    val ci = CI()
    val kmp = KMP()

    inner class CI : Configuration("ci", this) {
        val isCI = property("", false)
        val isGithubCi = property("github", false)
    }

    inner class KMP : Configuration("kmp", this) {
        val js = PlatformConfig("js")
        val linuxX64 = PlatformConfig("linuxX64")
        val mingwX64 = PlatformConfig("mingwX64")

        inner class PlatformConfig(platformName: String) : Configuration(platformName, this) {
            val isEnabled = property("enabled", true)
        }
    }
}

val Project.coreConfiguration: CoreProjectConfiguration
    get() = rootProject.extensions.findByType()
        ?: rootProject.extensions.create(
            CoreProjectConfiguration::class.java.simpleName,
            PropertyProvider { project.findProperty(it)?.toString() }
        )

