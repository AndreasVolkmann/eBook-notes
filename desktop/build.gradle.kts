plugins {
    kotlin("jvm")
    application
}

repositories {
    jcenter()
}

dependencies {
    implementation(project(":core"))
    implementation("io.ktor:ktor-server-netty:1.5.2")
    implementation("io.ktor:ktor-html-builder:1.5.2")
    implementation("org.jetbrains.kotlinx:kotlinx-html-jvm:0.7.2")
}

application {
    mainClass.set("ServerKt")
}