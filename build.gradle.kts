import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  kotlin("jvm") version "1.7.10"
}

group = "me.soshin"
version = "1.0-SNAPSHOT"

repositories {
  mavenCentral()
}

val exposedVersion = "0.39.2"
val postgresqlVersion = "42.7.2"
dependencies {
  implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
  implementation("org.postgresql:postgresql:$postgresqlVersion")
  testImplementation(kotlin("test"))
}

tasks.test {
  useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
  kotlinOptions.jvmTarget = "1.8"
}