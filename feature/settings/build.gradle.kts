plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.a1danz.feature_settings"
    compileSdk = 34

    defaultConfig {
        minSdk = 21

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

    }

    buildTypes {
        release {
            isMinifyEnabled = true
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8

        isCoreLibraryDesugaringEnabled = true
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":feature:user-configurer"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    implementation(platform(libs.firebase))
    implementation(libs.firebaseAnalytics)
    implementation(libs.firebaseAuth)
    implementation(libs.firebaseFireStore)

    implementation(libs.navigationUi)
    implementation(libs.navigationFragment)

    implementation(libs.dagger)
    ksp(libs.daggerCompiler)

    implementation(libs.lifecycle)
    implementation(libs.coroutinesCore)
    implementation(libs.coroutinesAndroid)

    implementation(libs.roomKtx)
    implementation(libs.roomRuntime)
    ksp(libs.roomCompiler)

    implementation(libs.retrofit)
    implementation(libs.retrofitGsonConverter)
    implementation(libs.gson)

    implementation(libs.viewModelLifecycle)

    implementation(libs.workManager)

    implementation(libs.vkSdkBtn)
    implementation(libs.facebookLogin)
    implementation(libs.facebookSdk)

    implementation(libs.glide)

    implementation(libs.viewBindingDelegate)

    implementation(libs.customSwitch)

    implementation(libs.shimmerLayout)

    coreLibraryDesugaring(libs.desugarJdkLibs)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}