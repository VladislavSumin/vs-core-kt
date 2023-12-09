job("Build && Test") {
    gradlew(
        image = "openjdk:21",

        "ci",
        "--continue",
        "--stacktrace",
        "--no-configuration-cache",
    )
}
