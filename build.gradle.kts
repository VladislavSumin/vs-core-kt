import io.gitlab.arturbosch.detekt.Detekt

plugins {
    id("ru.vs.convention.check-updates")
    `maven-publish`
}

val coreVersion: String = providers.gradleProperty("ru.vs.core.version").getOrElse("0.0.1")
version = coreVersion

allprojects {
    // Setup group for all projects
    val path = mutableListOf<String>()
    var project = this.parent
    while (project != null && project != project.rootProject) {
        path += project.name
        project = project.parent
    }
    val subpackage = path.reversed().joinToString(separator = ".")

    group = if (subpackage.isBlank()) "ru.vs"
    else "ru.vs.$subpackage"

    // Set version for all projects
    version = coreVersion

    apply { plugin("maven-publish") }
    publishing {
        repositories {
            maven {
                name = "respyMaven"
                url = uri(providers.gradleProperty("ru.vs.core.mvn.url").get())
                credentials {
                    username = providers.gradleProperty("ru.vs.core.mvn.user").get()
                    password = System.getenv("MAVEN_TOKEN")
            }
        }
    }
}

// Setup detekt for all projects
allprojects {
    apply { plugin("ru.vs.convention.analyze.detekt") }
}

// Add additional configuration to check core-build-logic
val detektBuildLogic = tasks.register<Detekt>("detektBuildLogic") {
    source = fileTree(project.rootDir).matching {
        include("core-build-logic/src/**/*.kt", "core-build-logic/**/*.gradle.kts")
        exclude("**/build/**")
    }
}
tasks.named("detekt").configure { dependsOn(detektBuildLogic) }
