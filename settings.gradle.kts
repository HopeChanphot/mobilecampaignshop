pluginManagement {
    repositories {
        google()        // Google MUST be first
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()        // Google MUST be first
        mavenCentral()
    }
}

rootProject.name = "MobileCampaignShop"
include(":app")
