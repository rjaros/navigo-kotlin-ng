plugins {
    kotlin("js")
    id("maven-publish")
    id("signing")
    id("de.marcphilipp.nexus-publish")
}

repositories {
    mavenCentral()
    mavenLocal()
}

// Versions
val kotlinVersion: String by System.getProperties()

kotlin {
    js {
        compilations.all {
            kotlinOptions {
                moduleKind = "umd"
            }
        }
        browser {
            testTask {
                useKarma {
                    useChromeHeadless()
                }
            }
        }
    }
}

dependencies {
    implementation(npm("navigo", "8.8.12"))
}

val sourcesJar by tasks.registering(Jar::class) {
    archiveClassifier.set("sources")
    from(kotlin.sourceSets.main.get().kotlin)
}

publishing {
    publications {
        create<MavenPublication>("kotlin") {
            from(components["kotlin"])
            artifact(tasks["sourcesJar"])
            pom {
                defaultPom()
            }
        }
    }
}

setupSigning()
setupPublication()
