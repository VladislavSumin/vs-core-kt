rootProject.name = "vs-core-kt"

pluginManagement {
    includeBuild("core-build-logic")
}

dependencyResolutionManagement {

    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
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