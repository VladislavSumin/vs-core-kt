package ru.vs.convention.kmp

plugins {
    id("ru.vs.convention.kmp.common")
}

kotlin {
    js(IR) {
        browser()
        nodejs()
    }

    sourceSets {
        named("jsTest") {
            dependencies {
                implementation(kotlin("test-js"))
            }
        }
    }
}
