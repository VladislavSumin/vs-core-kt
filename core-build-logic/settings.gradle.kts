dependencyResolutionManagement {

    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        google()
        gradlePluginPortal()
    }

    versionCatalogs {
        create("coreLibs") {
            from(files("../core-libs.versions.toml"))
        }
    }
}

rootProject.name = "core-build-logic"
