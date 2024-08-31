plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.hilt.plugin)
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id("androidx.navigation.safeargs.kotlin")
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.example.moviedb"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.moviedb"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    hilt {
        enableAggregatingTask = false
    }
    buildFeatures {
        dataBinding = true
        viewBinding = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.core.ktx)
    implementation(libs.androidx.junit.ktx)
    implementation(libs.androidx.runner)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation("androidx.arch.core:core-testing:2.2.0")

    // DI
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    // Network
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging)
    implementation(libs.gson)
    // DB
    implementation(libs.room.runtime)
    kapt(libs.room.compiler)
    implementation(libs.room.ktx)
    // Logging
    implementation(libs.timber)
    // Pagination
    implementation(libs.pagingRuntime)
    testImplementation(libs.pagingCommon)
    // Navigation
    implementation(libs.navigationFragment)
    implementation(libs.navigationUI)
    // Rounded Image View
    implementation(libs.roundedImageView)
    implementation(libs.picasso)
    // Testing
    testImplementation(libs.mockk)
    testImplementation(libs.coroutines.test)
    testImplementation(libs.room.test)

}