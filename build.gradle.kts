import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version Constants.Version.KOTLIN
    kotlin("plugin.spring") version Constants.Version.KOTLIN
    kotlin("plugin.jpa") version Constants.Version.KOTLIN
    id("org.springframework.boot") version Constants.Version.SPRING_BOOT
    id("io.kotest") version Constants.Version.KOTEST_PLUGIN
    id("io.spring.dependency-management") version Constants.Version.SPRING_DEPENDENCY_MANAGEMENT
}

allprojects {
    group = "com.kstd"
    version = "1.0-SNAPSHOT"

    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jetbrains.kotlin.kapt")
    apply(plugin = "org.jetbrains.kotlin.plugin.spring")
    apply(plugin = "org.jetbrains.kotlin.plugin.jpa")
    apply(plugin = "org.jetbrains.kotlin.plugin.noarg")
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")

    dependencies {
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${Constants.Version.COROUTINE}")

        implementation("org.springframework.boot:spring-boot-starter")
        implementation("org.springframework.boot:spring-boot-starter-web")

        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

        testImplementation(kotlin("test"))
        testImplementation("io.kotest:kotest-assertions-core-jvm:${Constants.Version.KOTEST}")
        testImplementation("io.kotest:kotest-framework-engine-jvm:${Constants.Version.KOTEST}")
        testImplementation("io.mockk:mockk:${Constants.Version.MOCKK}")
        testImplementation("com.appmattus.fixture:fixture:${Constants.Version.FIXTURE}")
    }

    tasks.getByName("bootJar") {
        enabled = false
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "21"
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}
