plugins {
    kotlin("jvm")
}

dependencies {
    implementation(project(":core"))
    implementation("de.brudaswen.kotlinx.serialization:kotlinx-serialization-csv:2.0.0")
}