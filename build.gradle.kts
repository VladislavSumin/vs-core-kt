import io.gitlab.arturbosch.detekt.Detekt

plugins {
    id("ru.vs.convention.check-updates")
}

// Setup group for all projects
allprojects {
    val path = mutableListOf<String>()
    var project = this.parent
    while (project != null && project != project.rootProject) {
        path += project.name
        project = project.parent
    }
    val subpackage = path.reversed().joinToString(separator = ".")

    group = if (subpackage.isBlank()) "ru.vs"
    else "ru.vs.$subpackage"
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
