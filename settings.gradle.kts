import java.net.URI

include(":feature-telegram-publisher")


include(":feature-vk-api")


include(":feature-vk-publisher")


include(":feature-post-publisher-api")

include(":feature-create-post")


include(":feature-telegram-bot")


include(":feature-user-configurer")



include(":feature-initialize")


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
        maven {
            url = URI("https://artifactory-external.vkpartner.ru/artifactory/vkid-sdk-andorid/")
        }
    }
}

rootProject.name = "PostingApp"
include(":app")
include(":common")
include(":feature-authorization")
include(":feature-settings")
