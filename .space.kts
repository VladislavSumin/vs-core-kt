job("Build && Test") {
    gradlew(
        image = "azul/zulu-openjdk:21",

        "ci",
        "--continue",
        "--stacktrace",
        "--no-configuration-cache",
    )
}
