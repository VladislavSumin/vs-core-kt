package ru.vs.build_logic

import org.gradle.api.Project
import org.gradle.api.initialization.Settings
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.findByType
import ru.vs.build_logic.utils.Configuration
import ru.vs.build_logic.utils.PropertyProvider

/**
 * Project configuration class
 * proxies all external configuration (by properties or by environment variables)
 */
@Suppress("UnnecessaryAbstractClass")
open class CoreProjectConfiguration(propertyProvider: PropertyProvider) :
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

        val macosX64 = PlatformConfig("macosX64")
        val macosArm64 = PlatformConfig("macosArm64")
        val macos = MergedPlatformConfig(macosX64, macosArm64)

        val iosX64 = PlatformConfig("iosX64")
        val iosArm64 = PlatformConfig("iosArm64")
        val ios = MergedPlatformConfig(iosX64, iosArm64)

        inner class PlatformConfig(platformName: String) : Configuration(platformName, this) {
            val isEnabled = property("enabled", true)
        }

        inner class MergedPlatformConfig(vararg platforms: PlatformConfig) {
            val isEnabled = platforms.any { it.isEnabled }
        }
    }
}

// TODO make normal plugin or convention for access to CoreProjectConfiguration
// We can't use rootProject as holder because when we add plugins to settings.gradle.kts then gradle evaluate this
// module and evaluate all convention plugin (to generate dsl), but when evaluating for settings we're getting error
// when trying to resolve rootProject. See https://github.com/gradle/gradle/issues/16532
val Project.coreConfiguration: CoreProjectConfiguration
    get() = extensions.findByType()
        ?: extensions.create(
            CoreProjectConfiguration::class.java.simpleName,
            PropertyProvider { project.findProperty(it)?.toString() }
        )

// TODO make normal plugin or convention for access to CoreProjectConfiguration
fun Settings.createCoreConfiguration(): CoreProjectConfiguration {
    return CoreProjectConfiguration { this.providers.gradleProperty(it).orNull }
}
