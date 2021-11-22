plugins {
    kotlin("multiplatform")
}

version = "1.0"

kotlin {
    jvm {
        val main by compilations.getting {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }

        val test by compilations.getting {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }

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
        val commonMain by sourceSets.getting

        val posixMain by creating {
            dependsOn(commonMain)
        }

        val linuxX64Main by getting {
            dependsOn(posixMain)
        }

        val macosX64Main by getting {
            dependsOn(posixMain)
        }

        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }

        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
            }
        }

        val jsTest by getting {
            dependencies {
                implementation(kotlin("test-js"))
            }
        }
    }
}