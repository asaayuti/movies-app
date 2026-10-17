import org.gradle.testing.jacoco.plugins.JacocoPluginExtension

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.google.dagger.hilt.android) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.android.dynamic.feature) apply false
    alias(libs.plugins.google.devtools.ksp) apply false
    id("jacoco")
}

configure<JacocoPluginExtension> {
    toolVersion = "0.8.11"
}

tasks.register<JacocoReport>("jacocoRootReport") {
    group = "Reporting"
    description = "Generates aggregated JaCoCo coverage report for all subprojects."

    dependsOn(subprojects.map { "${it.path}:testDebugUnitTest" })

    reports {
        xml.required.set(true)
        html.required.set(true)
        csv.required.set(false)
        html.outputLocation.set(layout.buildDirectory.dir("reports/jacoco/jacocoRootReport/html"))
        xml.outputLocation.set(layout.buildDirectory.file("reports/jacoco/jacocoRootReport/jacocoRootReport.xml"))
    }

    val fileFilter = listOf(
        "**/R.class",
        "**/R\$*.class",
        "**/BuildConfig.*",
        "**/Manifest*.*",
        "**/*Test*.*",
        "android/**/*",
        "**/*\$ViewInjector*.*",
        "**/*\$ViewBinder*.*",
        "**/*_MembersInjector.class",
        "**/Dagger*Component*.class",
        "**/Dagger*Subcomponent*.class",
        "**/*Subcomponent\$Builder.class",
        "**/*Module_*Factory.class",
        "**/*_Factory*.*",
        "**/*_HiltModules*.*",
        "**/Hilt_*.*",
        "**/databinding/*",
        "**/android/databinding/*",
        "**/androidx/databinding/*",
        "**/BR/*"
    )

    val sourceDirs = files(subprojects.map { "${it.projectDir}/src/main/java" })
    val classDirs = files(subprojects.map {
        fileTree("${it.layout.buildDirectory.get()}/tmp/kotlin-classes/debug") {
            exclude(fileFilter)
        }
    })
    val execData = files(subprojects.map {
        fileTree(it.layout.buildDirectory.get()) {
            include(
                "jacoco/testDebugUnitTest.exec",
                "outputs/unit_test_code_coverage/debugUnitTest/testDebugUnitTest.exec"
            )
        }
    })

    sourceDirectories.setFrom(sourceDirs)
    classDirectories.setFrom(classDirs)
    executionData.setFrom(execData)
}