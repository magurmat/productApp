import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  id("org.springframework.boot") version "3.1.5"
  id("io.spring.dependency-management") version "1.1.3"
  kotlin("jvm") version "1.8.22"
  kotlin("plugin.spring") version "1.8.22"
}

group = "cz.cleevio"
version = "0.0.1-SNAPSHOT"

java {
  sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
  mavenCentral()
}

dependencies {
  implementation("org.springframework.boot:spring-boot-starter")
  implementation("org.jetbrains.kotlin:kotlin-reflect")
  implementation("org.springframework.boot:spring-boot-starter-web")
  implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
  implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.1")
  api("io.github.microutils:kotlin-logging-jvm:2.1.21")
  api("org.springframework.boot:spring-boot-starter-data-mongodb")

  implementation("org.springframework.boot:spring-boot-starter-test")

  testImplementation("org.mockito:mockito-core:3.3.3")
  testRuntimeOnly("com.h2database:h2")
  testCompileOnly("org.junit.jupiter:junit-jupiter:5.7.0")


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
