import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.2.1"
    id("io.spring.dependency-management") version "1.1.4"
    kotlin("jvm") version "1.9.21"
    kotlin("plugin.spring") version "1.9.21"
}

group = "org.example"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
    google()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    compileOnly("org.projectlombok:lombok")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    annotationProcessor("org.projectlombok:lombok")
    //security
    implementation("org.springframework.security:spring-security-core:6.0.4")
    implementation("org.springframework.boot:spring-boot-starter-security:3.2.1")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    //embedded database
    implementation("com.h2database:h2:2.2.224")
    runtimeOnly("com.h2database:h2")

    //jwt token
    implementation("io.jsonwebtoken:jjwt-api:0.12.3")
    testImplementation("org.springframework.security:spring-security-test:6.1.4")

    //serializable plugin
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:3.2.0")

    //validation
    implementation("jakarta.validation:jakarta.validation-api:3.0.2")


}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
