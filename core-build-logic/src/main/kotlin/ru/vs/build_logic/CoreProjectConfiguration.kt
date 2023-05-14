package ru.vs.build_logic

import org.gradle.api.Project
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.findByType
import ru.vs.build_logic.utils.booleanProperty
import ru.vs.build_logic.utils.stringProperty

/**
 * Project configuration class
 * proxies all external configuration (by properties or by environment variables
 */
@Suppress("UnnecessaryAbstractClass")
abstract class CoreProjectConfiguration(private val project: Project) {
    val jvmVersion = project.stringProperty("ru.vs.core.jvmVersion", "17")

    val ci = CI()
    val kmp = KMP()

    inner class CI {
        val isCI = project.booleanProperty("ru.vs.core.ci", false)
        val isGithubCi = project.booleanProperty("ru.vs.core.ci.github", false)
    }

    inner class KMP {
        val mingwX64 = PlatformConfig("mingwX64")

        inner class PlatformConfig(private val platformName: String) {
            val isEnabled = project.booleanProperty("ru.vs.core.kmp.$platformName.enabled", true)
        }
    }
}

val Project.coreConfiguration: CoreProjectConfiguration
    get() = rootProject.extensions.findByType()
        ?: rootProject.extensions.create(CoreProjectConfiguration::class.java.simpleName)
