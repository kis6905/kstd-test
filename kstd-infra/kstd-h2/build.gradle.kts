plugins {
//    id("com.ewerk.gradle.plugins.querydsl") version "1.0.10"
}

tasks.getByName("bootJar") {
    enabled = false
}
tasks.getByName("jar") {
    enabled = true
}

dependencies {
    implementation(project(":kstd-common"))
    implementation(project(":kstd-domain"))

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    runtimeOnly("com.h2database:h2:${Constants.Version.H2}")
    implementation("com.querydsl:querydsl-jpa:${Constants.Version.QUERY_DSL}:jakarta")
    annotationProcessor("com.querydsl:querydsl-apt:${Constants.Version.QUERY_DSL}:jakarta")
    annotationProcessor("jakarta.annotation:jakarta.annotation-api")
    annotationProcessor("jakarta.persistence:jakarta.persistence-api")
    kapt("com.querydsl:querydsl-apt:${Constants.Version.QUERY_DSL}:jakarta")
    kapt("org.springframework.boot:spring-boot-configuration-processor")
}

val generated = file("src/main/generated")

tasks.withType<JavaCompile> {
    options.generatedSourceOutputDirectory.set(generated)
}

sourceSets {
    main {
        kotlin.srcDirs += generated
    }
}

tasks.named("clean") {
    doLast {
        generated.deleteRecursively()
    }
}

kapt {
    generateStubs = true
}
