plugins {
    id("java-library")
    alias(libs.plugins.jetbrainsKotlinJvm)
    alias(libs.plugins.ksp)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation(project(":feature-post-publisher-api"))

    implementation(libs.coroutinesCore)
    implementation(libs.retrofit)
    implementation(libs.retrofitGsonConverter)
    implementation(libs.dagger)
    ksp(libs.daggerCompiler)
}