import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.8.22"
    id("org.openjfx.javafxplugin") version "0.1.0"
}

group = "dev.donam"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-debug:1.7.3")

    implementation("io.vertx:vertx-core:4.5.1")
    implementation("io.vertx:vertx-lang-kotlin:4.5.1")
    implementation("io.vertx:vertx-web:4.5.1")

    implementation("io.vertx:vertx-lang-kotlin-coroutines:4.5.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.2")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "17"
}

javafx {
    version = "21.0.2"
    modules = listOf("javafx.controls")
}
