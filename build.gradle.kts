import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.8.22"
    kotlin("plugin.allopen") version "1.8.22"
    kotlin("plugin.noarg") version "1.8.22"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "dev.donam"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

allprojects {
    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")

    dependencies {
        val vertxVersion = "4.5.3"
        implementation(platform("io.vertx:vertx-stack-depchain:$vertxVersion"))

        implementation("org.apache.logging.log4j:log4j-core:2.22.0")
        implementation("org.apache.logging.log4j:log4j-api-kotlin:1.3.0")
        implementation("com.lmax:disruptor:3.4.4")
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "17"
        }
    }
}

project(":application") {
    dependencies {
    }
}

project(":db") {
    apply(plugin = "org.jetbrains.kotlin.plugin.allopen")
    apply(plugin = "org.jetbrains.kotlin.plugin.noarg")

    allOpen {
        annotation("jakarta.persistence.Entity")
    }

    noArg {
        annotation("jakarta.persistence.Entity")
    }

    dependencies {
        api(project(":application"))

        implementation("org.hibernate.reactive:hibernate-reactive-core:2.2.2.Final")
        implementation("io.smallrye.reactive:mutiny-kotlin:2.5.3")

        runtimeOnly("io.vertx:vertx-pg-client")
        runtimeOnly("com.ongres.scram:client:2.1")
        implementation("io.vertx:vertx-pg-client:4.5.2")
    }
}

project(":api") {
    dependencies {
        api(project(":db"))
        api(project(":application"))

        implementation("io.vertx:vertx-web")
        implementation("io.vertx:vertx-core")
        implementation("io.vertx:vertx-lang-kotlin")
        implementation("io.vertx:vertx-lang-kotlin-coroutines")
    }

    apply(plugin = "com.github.johnrengelman.shadow")

    tasks.withType<ShadowJar> {
        manifest {
            attributes["Main-Class"] = "dev.donam.practice.api.LauncherKt"
        }
        mergeServiceFiles()
    }
}

project(":framework") {
    dependencies {
        implementation(project(":api"))

        implementation("io.vertx:vertx-core")
        implementation("io.vertx:vertx-lang-kotlin")
        implementation("io.vertx:vertx-lang-kotlin-coroutines")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")

        implementation("io.vertx:vertx-web")
        implementation("io.vertx:vertx-web-client")

        val koin_version = "3.5.3"

        implementation("io.insert-koin:koin-core:$koin_version")

        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
        implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")

        runtimeOnly("org.jetbrains.kotlinx:kotlinx-coroutines-debug:1.4.0")
    }
}
