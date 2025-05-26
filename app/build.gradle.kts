plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.googleService)
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.23"// Plugin para Google Services (google-services.json)
}

android {
    namespace = "com.example.inktextil"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.inktextil"
        minSdk = 26
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        compose = true
    }
}

dependencies {
    // AndroidX + Jetpack Compose
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.navigation.runtime.android)
    implementation(libs.coil.compose)

    // Firebase BoM
    implementation(platform("com.google.firebase:firebase-bom:33.13.0"))


    implementation("com.google.firebase:firebase-firestore-ktx")
    implementation("io.coil-kt:coil-compose:2.4.0")
    implementation("com.google.firebase:firebase-storage-ktx")
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation("com.google.firebase:firebase-firestore-ktx")

    implementation("io.coil-kt:coil-compose:2.4.0")

    // Firebase productos específicos
    implementation(libs.firebase.analytics)
    implementation(libs.google.firebase.auth.ktx)
    implementation(libs.firebase.firestore.ktx)
    implementation(libs.firebase.storage.ktx)

    // Google Sign-In
    implementation(libs.play.services.auth)

    // Retrofit + OkHttp + Kotlinx Serialization
    implementation(libs.retrofit)
    implementation("com.squareup.okhttp3:okhttp:4.11.0")
    implementation(libs.retrofit2.kotlinx.serialization.converter)
    implementation(libs.kotlinx.serialization.json)

    // Exif (opcional para imágenes desde almacenamiento)
    implementation(libs.androidx.exifinterface)

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}

// Necesario para que funcione google-services.json
apply(plugin = "com.google.gms.google-services")
