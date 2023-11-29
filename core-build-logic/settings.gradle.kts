dependencyResolutionManagement {

    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        google()
        gradlePluginPortal()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        maven("https://maven.pkg.jetbrains.space/kotlin/p/wasm/experimental")

        // kotlin dev builds repo
        // maven("https://maven.pkg.jetbrains.space/kotlin/p/kotlin/bootstrap")
    }

    versionCatalogs {
        create("coreLibs") {
            from(files("../core-libs.versions.toml"))
        }
    }
}

rootProject.name = "core-build-logic"
