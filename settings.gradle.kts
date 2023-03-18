rootProject.name = "vs-core-kt"

pluginManagement {
    includeBuild("core-build-logic")

    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    // KMP ios add custom repositories
    // repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }

    versionCatalogs {
        create("coreLibs") {
            from(files("core-libs.versions.toml"))
        }
    }
}

include(":core:coroutines")
include(":core:di")
include(":core:ktor-server")
include(":core:logging")
