plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize") // For parcelable object
}

android {
    namespace = "com.jain.yourownbank"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.jain.yourownbank"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.15.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.7")
    implementation("androidx.activity:activity-compose:1.9.3")
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3-android:1.3.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    // Dependency for navigation
    implementation ("androidx.navigation:navigation-compose:2.8.6")
    // GSON dependency
    implementation ("com.google.code.gson:gson:2.11.0")
    // GSON converter dependency
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    // Lottie animation dependency
    implementation ("com.airbnb.android:lottie-compose:6.3.0")
    // Retrofit dependency
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    // OKHTTP logging interceptor dependency
    implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.11")
    // View model dependency
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.7")
    // Kotlin coroutine dependency
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.9.0")
    // Live date dependency
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.8.7")
    implementation ("androidx.compose.runtime:runtime-livedata:1.7.8")
    // Generating and Scanning QR related dependency
    implementation ("com.journeyapps:zxing-android-embedded:4.3.0")
    implementation ("com.google.zxing:core:3.5.2")
    implementation ("androidx.camera:camera-core:1.4.1")
    implementation ("androidx.camera:camera-camera2:1.4.1")
    implementation ("androidx.camera:camera-lifecycle:1.4.1")
    implementation ("androidx.camera:camera-view:1.4.1")



}