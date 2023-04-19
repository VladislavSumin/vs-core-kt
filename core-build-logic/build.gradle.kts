plugins {
    `kotlin-dsl`
    `maven-publish`
}

group = "ru.vs.core"
version = "0.0.2"

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/vladislavsumin/vs-core-kt")
            credentials {
                username = System.getenv("GITHUB_ACTOR")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }
}

dependencies {
    // TODO подождать пока эта фича появится в гредле
    // а пока костыль вот отсюда https://github.com/gradle/gradle/issues/15383
    api(files(coreLibs.javaClass.superclass.protectionDomain.codeSource.location))

    api(coreLibs.gradlePlugins.kotlin.core)
    api(coreLibs.gradlePlugins.android)
    api(coreLibs.gradlePlugins.jb.compose)
    api(coreLibs.gradlePlugins.checkUpdates)
    api(coreLibs.gradlePlugins.detekt)
    api(coreLibs.gradlePlugins.buildConfig)
}