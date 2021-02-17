plugins {
    kotlin("jvm") version "1.4.30"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.2")
    testImplementation(kotlin("test"))
    testImplementation(kotlin("test-junit"))
}
