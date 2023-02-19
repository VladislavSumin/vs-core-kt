rootProject.name = "vs-core-kt"

pluginManagement {
    includeBuild("build-logic")
}

dependencyResolutionManagement {
    versionCatalogs {
        create("coreLibs") {
            from(files("core-libs.versions.toml"))
        }
    }
}