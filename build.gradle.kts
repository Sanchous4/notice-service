import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    kotlin("kapt") version "2.2.10"
    id("org.springframework.boot") version "3.4.4"
    id("io.spring.dependency-management") version "1.1.7"
    id("dev.detekt") version ("2.0.0-alpha.0")
    id("org.jetbrains.kotlinx.kover") version "0.8.3"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
    toolchain { languageVersion = JavaLanguageVersion.of(21) }
}

repositories {
    mavenCentral()
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.boot:spring-boot-dependencies:3.4.4")
    }
}

val vertxVersion = "4.5.14"
val hibernateReactiveVersion = "1.1.9.Final"
val mutinyReactorVersion = "2.8.0"
val csv = "1.11.0"

dependencies {
    // Flyway (official starter) and JDBC for migrations
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("org.flywaydb:flyway-core")
    implementation("org.flywaydb:flyway-database-postgresql")
    implementation("org.postgresql:postgresql")
    implementation("org.apache.commons:commons-csv:$csv")

    // Hibernate
    implementation("io.vertx:vertx-pg-client:$vertxVersion")                       // Vert.x reactive driver :contentReference[oaicite:0]{index=0}
    implementation("org.hibernate.reactive:hibernate-reactive-core-jakarta:$hibernateReactiveVersion") // Hibernate Reactive core :contentReference[oaicite:1]{index=1}
    implementation("io.smallrye.reactive:mutiny-reactor:$mutinyReactorVersion")     // Reactor↔Mutiny bridge :contentReference[oaicite:2]{index=2}
    kapt("org.hibernate:hibernate-jpamodelgen:6.2.7.Final") // JPA metamodel generator :contentReference[oaicite:3]{index=3}

    // Webflux
    implementation("org.springframework.boot:spring-boot-starter-webflux")

    // Other libraries
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll(
            "-Xjsr305=strict",
            "-Xcontext-receivers"
        )
        jvmTarget.set(JvmTarget.JVM_21)
    }
}

tasks.withType<Test> { useJUnitPlatform() }
