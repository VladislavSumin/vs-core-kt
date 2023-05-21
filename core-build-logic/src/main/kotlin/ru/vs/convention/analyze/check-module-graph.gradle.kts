package ru.vs.convention.analyze

/**
 * Convention for default setup Module Graph Assert
 * See https://github.com/jraska/modules-graph-assert
 */

plugins {
    id("com.jraska.module.graph.assertion")
}

moduleGraphAssert {
    // TODO request feature to define configurations as regexp
    configurations += setOf("commonMainImplementation", "commonMainApi")

    allowed = arrayOf(
        ".* -> :core:.*",
        ".*-api -> .*-api",
        ".*-impl -> .*-api",
    )
}
