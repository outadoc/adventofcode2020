plugins {
    application
    kotlin("jvm")
    id("org.jetbrains.kotlinx.dataframe") version "0.14.1"
}

version = "1.0"

tasks.withType<JavaCompile>().configureEach {
    javaCompiler = javaToolchains.compilerFor {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

application {
    mainClass = "fr.outadoc.aoc.twentytwentyfour.MainKt"
}

dependencies {
    implementation("org.jetbrains.kotlinx:dataframe:0.14.1")
    implementation("org.jetbrains.kotlinx:kotlinx-collections-immutable:0.3.8")
}
