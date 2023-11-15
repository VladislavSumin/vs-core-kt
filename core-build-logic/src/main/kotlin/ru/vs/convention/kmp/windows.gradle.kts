package ru.vs.convention.kmp

import ru.vs.build_logic.coreConfiguration

plugins {
    id("ru.vs.convention.kmp.common")
}

if (project.coreConfiguration.kmp.mingwX64.isEnabled) kotlin {
    mingwX64()
}
