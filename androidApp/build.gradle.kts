plugins {
    id("com.android.application")
    kotlin("android")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
}

dependencies {
    val composeVersion = "1.3.1"
    //Integration of compose with activities
    implementation("androidx.activity:activity-compose:$composeVersion")
    //Animation support for compose
    implementation("androidx.compose.animation:animation:1.0.1")
    //Material Design support for compose
    implementation("androidx.compose.material:material:1.0.1")
    //Android studio tooling support for compose. e.g previewing UI
    implementation("androidx.compose.ui:ui-tooling:1.0.1")
    //Integration of compose with ViewModels
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:1.0.0-alpha07")
    //Compose UI test library
    implementation("androidx.compose.ui:ui-test-junit4:1.0.1")
    implementation("androidx.appcompat:appcompat:1.3.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.0")
    implementation("com.google.android.material:material:1.4.0")
    //Google Maps Play Services
    implementation("com.google.android.libraries.maps:maps:3.1.0-beta")
    implementation(project(":shared"))
}

android {
    compileOptions {

    }

    compileSdk = 30

    buildFeatures {
        compose = true
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.0.1"
    }

    defaultConfig {
        applicationId = "com.stackconstruct.kmmplayground.android"
        minSdk = 21
        targetSdk = 30
        versionCode = 1
        versionName = "1.0"
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    packagingOptions {
        packagingOptions {
            resources {
                excludes.add("META-INF/AL2.0")
                excludes.add("META-INF/LGPL2.1")
            }
        }
    }
}