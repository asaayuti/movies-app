plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.google.dagger.hilt.android)
    alias(libs.plugins.google.devtools.ksp)
    id("kotlin-parcelize")
    id("kotlin-kapt")
}
apply(from = "../shared_dependencies.gradle")

android {
    namespace = "com.example.core"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        buildConfigField("String", "API_KEY", "\"ca6da6d30ad6a9105dd5053fe25975cb\"")
        buildConfigField(
            "String",
            "CERTIFICATE_KEY",
            "\"sha256/k1Hdw5sdSn5kh/gemLVSQD/P4i4IBQEY1tW4WNxh9XM=\""
        )

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
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
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

    implementation(libs.android.database.sqlcipher)
    implementation(libs.androidx.sqlite.ktx)
}