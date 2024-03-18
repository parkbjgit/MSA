// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
}

buildscript {
    repositories {
        maven { url = uri("https://jitpack.io") }
    }
}

allprojects {
    repositories {

    }
}