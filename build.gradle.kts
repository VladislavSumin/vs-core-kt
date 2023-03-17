plugins {
    id("ru.vs.convention.check-updates")
}

allprojects {
    apply { plugin("ru.vs.convention.analyze.detekt") }
}
