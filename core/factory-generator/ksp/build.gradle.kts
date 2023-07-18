plugins {
    kotlin("jvm")
}

dependencies {
    implementation(projects.core.factoryGenerator.api)
    implementation(coreLibs.ksp)
    implementation(coreLibs.kotlinpoet.core)
    implementation(coreLibs.kotlinpoet.ksp)
}
