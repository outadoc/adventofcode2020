plugins {
    kotlin("jvm")
    id("org.jetbrains.kotlinx.dataframe") version "0.14.1"
}

version = "1.0"

dependencies {
    implementation(project(":core"))
    implementation("org.jetbrains.kotlinx:dataframe:0.14.1")
    implementation("org.jetbrains.kotlinx:kotlinx-collections-immutable:0.3.8")

    testImplementation(project(":core-test"))
    testImplementation(kotlin("test-junit"))
}
