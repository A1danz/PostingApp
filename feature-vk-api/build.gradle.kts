plugins {
    id("java-library")
    alias(libs.plugins.jetbrainsKotlinJvm)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation(libs.coroutinesCore)
    implementation(libs.retrofit)
    implementation(libs.retrofitGsonConverter)
    implementation(libs.dagger)
}