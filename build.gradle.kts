import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  kotlin("jvm") version "1.7.10"
  application
}

repositories {
  mavenCentral()
}

dependencies {
  implementation("org.jetbrains.exposed:exposed-jdbc:0.39.2")
  implementation("org.postgresql:postgresql:42.7.2")
  testImplementation(kotlin("test"))
}

tasks.test {
  useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
  kotlinOptions.jvmTarget = "11"
}

application {
  mainClass.set("MainKt")
}
