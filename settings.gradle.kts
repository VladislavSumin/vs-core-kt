rootProject.name = "vs-core-kt"

pluginManagement {
    includeBuild("core-build-logic")

    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        maven("https://maven.pkg.jetbrains.space/kotlin/p/wasm/experimental")
    }
}

dependencyResolutionManagement {
    // KMP ios add custom repositories
    // repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        maven("https://maven.pkg.jetbrains.space/kotlin/p/wasm/experimental")
    }

    versionCatalogs {
        create("coreLibs") {
            from(files("core-libs.versions.toml"))
        }
    }
}

include(":core:compose")
include(":core:coroutines")
include(":core:database")
include(":core:decompose")
include(":core:di")
include(":core:ktor-server")
include(":core:logging")
include(":core:mvi")
include(":core:utils")
