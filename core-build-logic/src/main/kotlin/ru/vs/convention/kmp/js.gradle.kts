package ru.vs.convention.kmp

import ru.vs.build_logic.coreConfiguration

plugins {
    id("ru.vs.convention.kmp.common")
}

if (project.coreConfiguration.kmp.js.isEnabled) kotlin {
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
