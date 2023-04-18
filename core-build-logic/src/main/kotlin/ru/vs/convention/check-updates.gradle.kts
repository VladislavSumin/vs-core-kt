package ru.vs.convention

import com.github.benmanes.gradle.versions.reporter.result.Result
import ru.vs.build_logic.coreConfiguration
import ru.vs.build_logic.github.GithubActionLogger

plugins {
    id("com.github.ben-manes.versions")
}

tasks.dependencyUpdates.configure {
    fun isNonStable(version: String): Boolean {
        val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.toUpperCase().contains(it) }
        val regex = "^[0-9,.v-]+(-r)?$".toRegex()
        val isStable = stableKeyword || regex.matches(version)
        return !isStable
    }
    rejectVersionIf {
        isNonStable(candidate.version) && !isNonStable(currentVersion)
    }

    // Setup special logging format for GitHub
    // library updates will be print as waring in GutHub Action build
    if (project.coreConfiguration.ci.isGithubCi) {
        outputFormatter = closureOf<Result> {
            outdated.dependencies.forEach {
                GithubActionLogger.w(
                    "Library outdated: ${it.group}:${it.name} [${it.version} -> ${it.available.milestone}]"
                )
            }
        }
    }
}