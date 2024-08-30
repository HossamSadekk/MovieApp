// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
}

buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        // Add Safe Args classpath
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.7.7") // Use the correct version
        // Other classpath dependencies if needed
    }
}
