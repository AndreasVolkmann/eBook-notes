
plugins {
    val kotlinVersion = "1.5.21"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.serialization") version kotlinVersion
    id("com.github.ben-manes.versions") version "0.38.0"
}

allprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")

    group = "me.avo"
    version = "1.0-SNAPSHOT"

    repositories {
        mavenCentral()
    }

    dependencies {
        implementation("com.apurebase", "arkenv-yaml", "3.3.2")
        testImplementation(kotlin("test"))
        testImplementation("io.strikt:strikt-core:0.31.0")
        testImplementation("io.mockk:mockk:1.11.0")
    }

    tasks.test {
        useJUnitPlatform()
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "13"
    }
}