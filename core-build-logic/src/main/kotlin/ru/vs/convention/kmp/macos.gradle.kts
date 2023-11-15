package ru.vs.convention.kmp

import ru.vs.build_logic.coreConfiguration

plugins {
    id("ru.vs.convention.kmp.common")
}

if (coreConfiguration.kmp.macos.isEnabled) kotlin {
    if (coreConfiguration.kmp.macosX64.isEnabled) macosX64()
    if (coreConfiguration.kmp.macosArm64.isEnabled) macosArm64()
}
