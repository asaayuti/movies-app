// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.google.devtools.ksp) apply false
    alias(libs.plugins.google.dagger.hilt.android) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.android.dynamic.feature) apply false
}

buildscript {
    extra.apply {
        set("core_ktx_version", "1.13.1")
        set("appcompat_version", "1.7.0")
        set("material_version", "1.12.0")
        set("junit_version", "4.13.2")
        set("androidx_junit_version", "1.2.1")
        set("espresso_core_version", "3.6.1")

        set("navigation_fragment_ktx_version", "2.6.0")
        set("navigation_ui_ktx_version", "2.6.0")

        set("glide_version", "4.16.0")
        set("blurview_version", "version-2.0.3")

        set("dagger_version", "2.48")
        set("hilt_android_version", "2.48")

    }
}