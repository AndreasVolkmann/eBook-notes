plugins {
    kotlin("jvm")
    application
}

dependencies {
    implementation(project(":core"))
    implementation(project(":infrastructure"))
}

application {
    mainClass.set("MainKt")
}