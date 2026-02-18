plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.paperroll_123"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.paperroll_123"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    // ⭐ NUEVO: Flavors para Standard y Clover
    flavorDimensions += "platform"

    productFlavors {
        create("standard") {
            dimension = "platform"
            applicationId = "com.example.paperroll_123"
            versionNameSuffix = "-standard"
        }
        create("clover") {
            dimension = "platform"
            applicationId = "com.example.paperroll_123.clover"
            versionNameSuffix = "-clover"
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

    // Core + Lifecycle + Activity Compose
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    // viewmodel + compose
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.0")

    // Compose BOM
    implementation(platform(libs.androidx.compose.bom))

    // Compose UI
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)

    // Material3
    implementation(libs.androidx.material3)

    // AppCompat
    implementation(libs.androidx.appcompat)

    // Accompanist
    implementation(libs.accompanist.swiperefresh)

    // ViewBinding interop
    implementation(libs.androidx.ui.viewbinding)
    implementation(libs.ui)

    // Splash
    implementation("androidx.core:core-splashscreen:1.0.1")

    // Navigation
    implementation("androidx.navigation:navigation-compose:2.7.7")

    // DataStore
    implementation("androidx.datastore:datastore-preferences:1.1.1")

    // Tests
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)

    // Debug
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
