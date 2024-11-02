import org.gradle.internal.extensions.core.extra
// Top-level build file where you can add configuration options common to all sub-projects/modules.

extra["javaVersion"] = JavaVersion.VERSION_17
extra["minSdkVersion"] = 28
extra["compileSdk"] = 35
extra["targetSdk"] = 35

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.google.devtools.ksp) apply false
    alias(libs.plugins.google.dagger.hilt) apply false
    alias(libs.plugins.google.services) apply false
}