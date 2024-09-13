// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
}

buildscript {
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
        maven("https://repository.map.naver.com/archive/maven")
    }
}

allprojects {
    repositories {
        //maven { url = uri("https://naver.jfrog.io/artifactory/maven/") }
    }
}