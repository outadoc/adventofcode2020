plugins {
    kotlin("multiplatform")
}

version = "1.0"

kotlin {
    jvm()
    macosX64()
    linuxX64()

    js {
        nodejs {
            testTask {
                useMocha {
                    timeout = "36000"
                }
            }
        }
    }

    sourceSets {
        val commonMain by getting

        val posixMain by creating {
            dependsOn(commonMain)
        }

        val linuxX64Main by getting {
            dependsOn(posixMain)
        }

        val macosX64Main by getting {
            dependsOn(posixMain)
        }
    }
}