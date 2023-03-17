package ru.vs.convention.kmp

plugins {
    id("ru.vs.convention.kmp.common")
}

kotlin {
    wasm {
        browser()
        nodejs()
        d8()
    }
}
