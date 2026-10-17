import java.io.FileInputStream
import java.util.Properties
import org.gradle.testing.jacoco.plugins.JacocoPluginExtension

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.google.dagger.hilt.android)
    alias(libs.plugins.google.devtools.ksp)
    id("kotlin-parcelize")
    id("kotlin-kapt")
    id("jacoco")
}

configure<JacocoPluginExtension> {
    toolVersion = "0.8.11"
}
apply(from = "../shared_dependencies.gradle")

val properties = Properties().apply {
    val localPropertiesFile = rootProject.file("local.properties")
    if (localPropertiesFile.exists()) {
        load(FileInputStream(localPropertiesFile))
    }
}

android {
    namespace = "com.example.core"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        buildConfigField("String", "HOSTNAME", "\"${properties["HOSTNAME"] ?: "api.themoviedb.org"}\"")
        buildConfigField("String", "API_KEY", "\"${properties["API_KEY"] ?: ""}\"")
        buildConfigField("String", "BASE_URL", "\"${properties["BASE_URL"] ?: "https://api.themoviedb.org/3/"}\"")
        buildConfigField("String", "IMAGE_URL", "\"${properties["IMAGE_URL"] ?: "https://image.tmdb.org/t/p/w500"}\"")
        buildConfigField("String", "CERTIFICATE_KEY", "\"${properties["CERTIFICATE_KEY"] ?: ""}\"")

    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            isMinifyEnabled = true
            enableUnitTestCoverage = true
            enableAndroidTestCoverage = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
}

dependencies {

    implementation(libs.androidx.room.runtime)
    ksp(libs.room.compiler)

    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.logging.interceptor)

    api(libs.rxjava)
    api(libs.rxandroid)
    implementation(libs.adapter.rxjava2)
    implementation(libs.androidx.room.rxjava2)
    api(libs.androidx.lifecycle.reactivestreams.ktx)
    api(libs.rxbinding)

    api(libs.androidx.activity.ktx)
    api(libs.androidx.fragment.ktx)

    implementation(libs.sqlcipher.android)
    implementation(libs.androidx.sqlite.ktx)
}

tasks.register<JacocoReport>("jacocoTestReport") {
    dependsOn("testDebugUnitTest")

    reports {
        xml.required.set(true)
        html.required.set(true)
        csv.required.set(false)
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

    val debugTree = fileTree("${layout.buildDirectory.get()}/tmp/kotlin-classes/debug") {
        exclude(fileFilter)
    }
    val mainSrc = "${projectDir}/src/main/java"

    sourceDirectories.setFrom(files(mainSrc))
    classDirectories.setFrom(files(debugTree))
    executionData.setFrom(fileTree(layout.buildDirectory.get()) {
        include(
            "jacoco/testDebugUnitTest.exec",
            "outputs/unit_test_code_coverage/debugUnitTest/testDebugUnitTest.exec"
        )
    })
}

tasks.matching { it.name == "testDebugUnitTest" }.configureEach {
    finalizedBy("jacocoTestReport")
}