package ru.vs.convention.kmp

plugins {
    id("kotlin-multiplatform")
    kotlin("native.cocoapods")
}

kotlin {
    cocoapods {
        framework {
            isStatic = true
        }
    }
}
