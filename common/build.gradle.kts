plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.ksp)
    alias(libs.plugins.googleServices)
}

android {
    namespace = "com.a1danz.common"
    compileSdk = 34

    defaultConfig {
        minSdk = 21

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.splashScreen)
    implementation(libs.dataStore)

    implementation(platform(libs.firebase))
    implementation(libs.firebaseAnalytics)

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

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}