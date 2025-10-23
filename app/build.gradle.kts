plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    id("org.jetbrains.kotlin.kapt")
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.google.services) apply false
//    alias(libs.plugins.firebase.crashlytics) apply false
}

android {
    namespace = "com.rhine.travelleandroid"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.rhine.travelleandroid"
        minSdk = 29
        targetSdk = 36
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
    flavorDimensions += "default"
    productFlavors {
        create("live") {
            applicationId = "app.guzoandroid"
            versionCode = 1
            versionName = "1.0.0"
            resValue("string", "app_name", "guzoandroid")
            buildConfigField(
                "String",
                "SERVER_URL",
                "\"https://dev.kodetechnologies.app/\""
            )
            dimension = "default"
        }

        create("dev") {
            applicationId = "com.kodetechnologies.guzoandroid"
            versionCode = 11
            versionName = "1.1.1"
            resValue("string", "app_name", "DEV guzoandroid")
            buildConfigField(
                "String",
                "SERVER_URL",
                "\"https://api.dev.guzo.kodetechnologies.app/\""
            )
            dimension = "default"
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}
hilt {
    enableAggregatingTask = false
}

kapt {
    correctErrorTypes = true
}

dependencies {
    // Android
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    // Compose LiveData interop
    implementation("androidx.compose.runtime:runtime-livedata")
    // Hilt
    implementation(libs.hilt.android.lib)
    implementation(libs.androidx.databinding.runtime)
    kapt(libs.hilt.compiler)
    implementation(libs.hilt.navigation.compose)
    // Room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)
    // Network
    implementation(libs.moshi)
    implementation(libs.moshi.kotlin)
    ksp(libs.moshi.kotlin.codegen)
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging)
    implementation(libs.retrofit)
    implementation(libs.retrofit.moshi)
    implementation(libs.retrofit.rxjava)
    implementation(libs.rxjava)
    implementation(libs.rxjava.android)

    // Firebase - uncomment when you have google-services.json
    // implementation(platform(libs.firebase.bom))
    // implementation(libs.firebase.analytics)
    // implementation(libs.firebase.crashlytics)
}