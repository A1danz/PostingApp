plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.ksp)
    alias(libs.plugins.googleServices)
}

android {
    namespace = "com.a1danz.posting"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.a1danz.posting"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        addManifestPlaceholders(mapOf(
            "VKIDRedirectHost" to "vk.com", // обычно vk.com
            "VKIDRedirectScheme" to "vk_redirect_scheme", // обычно vk{ID приложения}
            "VKIDClientID" to "vk_id_client_id",
            "VKIDClientSecret" to "vk_id_client_secret"
        ))

        buildConfigField("String", "BOT_TOKEN", properties["botToken"].toString())
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
        isCoreLibraryDesugaringEnabled = true

    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
}

dependencies {
    implementation(project(":common"))
    implementation(project(":feature-authorization"))
    implementation(project(":feature-settings"))
    implementation(project(":feature-initialize"))
    implementation(project(":feature-user-configurer"))
    implementation(project(":feature-create-post"))
    implementation(project(":feature-post-publisher-api"))
    implementation(project(":feature-posts-feed-database"))
    implementation(project(":feature-posts-feed"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.splashScreen)
    implementation(libs.dataStore)

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

    implementation(libs.facebookLogin)
    implementation(libs.facebookSdk)

    implementation(libs.viewBindingDelegate)

    coreLibraryDesugaring(libs.desugarJdkLibs)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}