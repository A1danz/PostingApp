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
            "VKIDRedirectScheme" to (project.properties["vkRedirectScheme"] ?: throw IllegalStateException("redirect scheme doesn't initalized in properties")), // обычно vk{ID приложения}
            "VKIDClientID" to (project.properties["vkClientId"] ?: throw IllegalStateException("vk client id doesn't initalized in properties")),
            "VKIDClientSecret" to (project.properties["vkClientSecret"] ?: throw IllegalStateException("vk client secret doesn't initalized in properties"))
        ))
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
    implementation(project(":feature-places-info"))

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

    implementation(libs.esafirmImagePicker)

    coreLibraryDesugaring(libs.desugarJdkLibs)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}