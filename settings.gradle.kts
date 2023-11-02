enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

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

include(":core:analytic:sentry")
include(":core:compose")
include(":core:coroutines")
include(":core:database")
include(":core:decompose")
include(":core:di")
include(":core:factory-generator:api")
include(":core:factory-generator:ksp")
include(":core:key-value-storage")
include(":core:ktor-client")
include(":core:ktor-network")
include(":core:ktor-server")
include(":core:logging")
include(":core:network")
include(":core:okio")
include(":core:serialization:core")
include(":core:serialization:json")
include(":core:uikit:auto-size-text")
include(":core:uikit:dropdown-menu")
include(":core:uikit:local-configuration")
include(":core:uikit:video-player")
include(":core:utils")
