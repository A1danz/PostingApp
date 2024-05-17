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
    implementation(libs.dagger)
    ksp(libs.daggerCompiler)

    implementation(libs.telegramClient)
}