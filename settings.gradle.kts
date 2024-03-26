rootProject.name = "V4Demo"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        google()
        gradlePluginPortal()
        mavenCentral()
        maven {
            url = uri("https://artifactory.2gis.dev/sdk-maven-release")
        }
    }

    dependencyResolutionManagement {
        repositories {
            google()
            mavenCentral()
            maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
            maven {
                url = uri("https://artifactory.2gis.dev/sdk-maven-release")
            }
        }
    }

    include(":composeApp")
}