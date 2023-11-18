plugins {
    `kotlin-dsl`
    `maven-publish`
    `java-gradle-plugin`
}

group = "ru.vs.core"
version = providers.gradleProperty("ru.vs.core.version").getOrElse("0.0.1")

publishing {
    repositories {
        maven {
            name = "respyMaven"
            url = uri(providers.gradleProperty("ru.vs.core.mvn.url").get())
            credentials {
                username = providers.gradleProperty("ru.vs.core.mvn.user").get()
                password = System.getenv("MAVEN_TOKEN")
            }
        }
    }
}

dependencies {
    // TODO подождать пока эта фича появится в гредле
    // а пока костыль вот отсюда https://github.com/gradle/gradle/issues/15383
    api(files(coreLibs.javaClass.superclass.protectionDomain.codeSource.location))

    api(coreLibs.gradlePlugins.kotlin.core)
    api(coreLibs.gradlePlugins.kotlin.serialization)
    api(coreLibs.gradlePlugins.kotlin.atomicfu)
    api(coreLibs.gradlePlugins.kotlin.allopen)
    api(coreLibs.gradlePlugins.kotlin.benchmark)
    api(coreLibs.gradlePlugins.kotlin.kover)
    api(coreLibs.gradlePlugins.ksp)
    api(coreLibs.gradlePlugins.android)
    api(coreLibs.gradlePlugins.jb.compose)
    api(coreLibs.gradlePlugins.checkUpdates)
    api(coreLibs.gradlePlugins.detekt)
    api(coreLibs.gradlePlugins.buildConfig)
    api(coreLibs.gradlePlugins.sqldelight)
    api(coreLibs.gradlePlugins.moko.resources)
    api(coreLibs.gradlePlugins.assertion)
}
