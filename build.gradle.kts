buildscript {
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
    dependencies {
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.51")
        classpath("com.google.gms:google-services:4.3.13")
        // Removed Kotlin Gradle plugin from here
        val nav_version = "2.5.3"
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:$nav_version")
    }
}

plugins {
    id("com.android.application") version "8.7.2" apply false
    id("com.android.library") version "8.7.2" apply false
    id("org.jetbrains.kotlin.android") version "1.9.10" apply false // Correct plugin ID and version
    id("androidx.navigation.safeargs.kotlin") version "2.5.3" apply false
}

tasks.register<Delete>("clean") {
    delete(rootProject.buildDir)
}