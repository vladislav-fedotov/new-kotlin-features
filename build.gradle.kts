plugins {
    kotlin("jvm") version "2.0.20-Beta1"
}

group = "example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.6.0")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.8.0")

    implementation("io.arrow-kt:arrow-fx-coroutines:2.0.0-alpha.1")
    implementation("io.arrow-kt:arrow-autoclose:2.0.0-alpha.1")
    implementation("io.arrow-kt:arrow-core:2.0.0-alpha.1")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
    compilerOptions {
        freeCompilerArgs.addAll(
            "-Xmulti-dollar-interpolation",
            "-Xwhen-guards",
            "-Xuse-experimental",
            "-Xcontext-receivers",
            "-Xconsistent-data-class-copy-visibility"
        )
    }
}