plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "1.3.60"
}

group = "io.github.ordonteam"
version = "1.0-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    testImplementation("junit:junit:4.12")
    testImplementation("org.easytesting:fest-assert-core:2.0M10")
    testImplementation("com.nhaarman.mockitokotlin2:mockito-kotlin:2.2.0")
}