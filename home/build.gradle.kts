plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.google.devtools.ksp)
    alias(libs.plugins.google.dagger.hilt)
}

val javaVersion = rootProject.extra["javaVersion"] as JavaVersion
val minSdkVersion = rootProject.extra["minSdkVersion"] as Int
val rootcompileSdk = rootProject.extra["compileSdk"] as Int

android {
    namespace = "me.lokmvne.home"
    compileSdk = rootcompileSdk

    defaultConfig {
        minSdk = minSdkVersion

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = javaVersion
        targetCompatibility = javaVersion
    }
    kotlinOptions {
        jvmTarget = javaVersion.toString()
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(project(":common"))
    implementation(project(":auth"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //viewmodel
    implementation(libs.androidx.viewmodel.compose)
    implementation(libs.androidx.viewmodel.ktx)
    //Dagger - Hilt
    implementation(libs.google.dagger.hilt.android)
    implementation(libs.androidx.hilt.navigation.compose)
    ksp(libs.google.dagger.hilt.android.compiler)
    ksp(libs.androidx.dagger.hilt.compiler)

    //Retrofit
    implementation(libs.retrofit)
    implementation(libs.squareup.retrofit2.gson)
    //Okhttp
    implementation(libs.squareup.okhttp3.logging.interceptor)
    implementation(libs.okhttp)
    //coil
    implementation(libs.io.coil)

}