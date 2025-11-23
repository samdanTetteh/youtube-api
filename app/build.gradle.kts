plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("kotlin-parcelize")
    id("kotlin-kapt")
    alias(libs.plugins.safe.args.plugin)
}

android {
    namespace = "com.ijikod.gmbn_youtube"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.ijikod.gmbn_youtube"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false // Use isMinifyEnabled in .kts files
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }


    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlin {
        jvmToolchain(17)
    }


    buildFeatures {
        dataBinding = true
        viewBinding = true
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation (libs.androidx.core.ktx)
    implementation (libs.androidx.appcompat)
    implementation (libs.androidx.constraint.layout)
    implementation(libs.androidx.swiperefreshlayout)
    testImplementation (libs.junit)
    androidTestImplementation (libs.androidx.espresso.core)

    // architecture components
    implementation (libs.androidx.lifecycle.extensions)
    implementation (libs.androidx.lifecycle.runtime.ktx)
    implementation (libs.androidx.lifecycle.viewmodel.ktx)
    implementation (libs.androidx.lifecycle.livedate.ktx)
    implementation (libs.androidx.room.runtime)
    implementation (libs.androidx.room.ktx)
    implementation (libs.androidx.paging.runtime)
    kapt (libs.androidx.room.compiler)

    // Navigation
    implementation (libs.androidx.fragment.navigation)
    implementation (libs.androidx.navigation.ui)

    //Retrofit
    implementation (libs.squareup.retrofit2)
    implementation (libs.squareup.retrofit2.gson.converter)
    implementation (libs.squareup.retrofit2.moshi.converter)

    //Glide
    implementation (libs.bumptech.glide)
    kapt (libs.bumptech.glide.compiler)

    // Assertions
    androidTestImplementation (libs.androidx.junit)
    androidTestImplementation (libs.androidx.test.truth)
    androidTestImplementation (libs.google.truth)

    // Espresso
    androidTestImplementation (libs.androidx.espresso.core)
    androidTestImplementation (libs.androidx.espresso.contrib)

    // AndroidJUnitRunner and JUnit Rules
    androidTestImplementation (libs.androidx.test.runner)
    androidTestImplementation (libs.androidx.test.rules)

    // Mockito
    androidTestImplementation (libs.mockito.core)
    androidTestImplementation (libs.mockito.android)
    androidTestImplementation (libs.androidx.arch.core)

    // Fragment
    implementation (libs.androidx.fragment.ktx)

    // Fragment Tests
    debugImplementation  (libs.androidx.fragment.testing)
}