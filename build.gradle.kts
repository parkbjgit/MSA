// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    id("com.google.gms.google-services") version "4.3.13" apply false
}

buildscript {
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
        maven("https://repository.map.naver.com/archive/maven")
    }
    dependencies {
        classpath(libs.google.services)
    }
}

allprojects {
    repositories {
        //maven { url = uri("https://naver.jfrog.io/artifactory/maven/") }
    }
}