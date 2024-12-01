import org.gradle.api.tasks.testing.logging.TestExceptionFormat

plugins {
    kotlin("multiplatform") version "2.1.0" apply false
}

allprojects {
    repositories {
        mavenCentral()
    }

    tasks.withType<Test> {
        testLogging {
            events("passed", "skipped", "failed")
            showStandardStreams = true
            exceptionFormat = TestExceptionFormat.FULL
        }
    }
}
