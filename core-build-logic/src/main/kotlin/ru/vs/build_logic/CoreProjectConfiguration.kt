package ru.vs.build_logic

import org.gradle.api.Project
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.findByType
import ru.vs.build_logic.utils.booleanProperty
import ru.vs.build_logic.utils.stringProperty

abstract class CoreProjectConfiguration constructor(private val project: Project) {
    val jvmVersion = project.stringProperty("ru.vs.core.jvmVersion", "17")
    val ci = CI()

    inner class CI {
        val isCI = project.booleanProperty("ru.vs.core.ci", false)
        val isGithubCi = project.booleanProperty("ru.vs.core.ci.github", false)
    }
}

val Project.coreConfiguration: CoreProjectConfiguration
    get() = extensions.findByType() ?: extensions.create(CoreProjectConfiguration::class.java.simpleName)
