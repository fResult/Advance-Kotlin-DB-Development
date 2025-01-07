import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  kotlin("jvm") version "2.1.0"
  application
}

group = "me.soshin"
version = "1.0-SNAPSHOT"

repositories {
  mavenCentral()
}

val exposedVersion = "0.57.0"
val postgresqlVersion = "42.7.2"

dependencies {
  implementation(libs.exposed.core)
  implementation(libs.exposed.dao)
  implementation(libs.exposed.jdbc)
  implementation(libs.exposed.kotlin.datetime)

  implementation("org.postgresql:postgresql:$postgresqlVersion")
  implementation("ch.qos.logback:logback-classic:1.5.16")
  testImplementation(kotlin("test"))
}

tasks.test {
  useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
  compilerOptions({
    jvmTarget.set(JvmTarget.JVM_17)
  })
}

tasks.withType<JavaCompile> {
  options.release.set(17)
}

application {
  mainClass.set("MainKt")
}
