package ru.vs.convention.test

import org.gradle.api.tasks.testing.Test
import org.gradle.api.tasks.testing.TestReport
import org.gradle.kotlin.dsl.withType

/**
 * This convention applies only to a root project and merge all gradle test report from
 * all modules into one report located at `rootProject/build/reports/mergedTestReport`
 */

check(project === project.rootProject) { "This convention allow apply only to a root project" }

val generateMergedTestReport by tasks.registering(TestReport::class) {
    // Set report output directory
    destinationDirectory.set(layout.buildDirectory.dir("reports/mergedTestReport"))
}

// Run for all projects and find all tests tasks with it report dirs
allprojects {
    val testTasks = tasks.withType<Test>()

    // Don't do this: testResults.from(testTasks.map { it.binaryResultsDirectory })
    // We can't directly add all binaryResultsDirectory into testResults collection
    // because it's trigger it tasks execution. We need add tasks to report lazily, only if
    // this task triggered to execute by another way.
    testTasks.configureEach {
        doLast {
            generateMergedTestReport.configure { testResults.from(binaryResultsDirectory) }
        }
        finalizedBy(generateMergedTestReport)
    }

    generateMergedTestReport.configure { mustRunAfter(testTasks) }
}
