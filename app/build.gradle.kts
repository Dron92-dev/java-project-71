plugins {
    id("java")
    id("application")
    id("com.github.ben-manes.versions") version "0.53.0"
}

group = "hexlet.code"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    implementation("info.picocli:picocli:4.7.7")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.17.2")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

application {
    mainClass.set("hexlet.code.App")
}

tasks.test {
    useJUnitPlatform()
}