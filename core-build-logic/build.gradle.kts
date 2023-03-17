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
    implementation(files(coreLibs.javaClass.superclass.protectionDomain.codeSource.location))

    implementation(coreLibs.gradlePlugins.kotlin.core)
    implementation(coreLibs.gradlePlugins.android)
    implementation(coreLibs.gradlePlugins.checkUpdates)
    implementation(coreLibs.gradlePlugins.detekt)
}