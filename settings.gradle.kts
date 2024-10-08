import java.net.URI

include(":core:data")


pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        jcenter()
        maven {
            url = URI("https://jitpack.io")
        }
        maven { url = URI("https://jcenter.bintray.com") }
        maven {
            url = URI("https://artifactory-external.vkpartner.ru/artifactory/vkid-sdk-andorid/")
        }
    }
}

rootProject.name = "PostingApp"
include(":app")
include(":core:common")
include(":feature:authorization")
include(":feature:settings")
include(":feature:places-info")
include(":feature:posts-feed")
include(":feature:telegram-publisher")
include(":feature:vk-api")
include(":feature:vk-publisher")
include(":feature:post-publisher-api")
include(":feature:create-post")
include(":feature:user-configurer")
include(":feature:create-post")
include(":feature:initialize")