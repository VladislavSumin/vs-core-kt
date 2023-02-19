dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("../core-libs.versions.toml"))
        }
    }
}