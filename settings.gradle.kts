pluginManagement {
    repositories {
        // Required for Android Gradle plugin & Kotlin plugin
        google()
        mavenCentral()
        gradlePluginPortal()

        // Add JitPack here too for plugin resolution
        maven { url = uri("https://jitpack.io") }
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()

        // JitPack for normal library dependencies
        maven { url = uri("https://jitpack.io") }
    }
}

rootProject.name = "MyApplication5"
include(":app")
