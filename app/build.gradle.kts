plugins {
    alias(libs.plugins.androidApplication)
}

android {
    namespace = "com.example.msa"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.msa"
        minSdk = 31
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

}

dependencies {
    implementation(files("libs/libDaumMapAndroid.jar"))
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)

    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // 네이버 지도 SDK
    implementation("com.naver.maps:map-sdk:3.19.1")

    implementation("androidx.recyclerview:recyclerview:1.1.0")
    implementation("androidx.cardview:cardview:1.0.0")
    //implementation ("com.github.pedroSG94:AutoPermission:1.0.3")

    implementation ("com.akexorcist:RoundCornerProgressBar:2.0.3")

}