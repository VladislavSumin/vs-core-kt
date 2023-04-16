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
    val subpackage = path.joinToString(separator = ".")

    group = if (subpackage.isBlank()) "ru.vs"
    else "ru.vs.$subpackage"
}

// Setup detekt for all projects
allprojects {
    apply { plugin("ru.vs.convention.analyze.detekt") }
}
