plugins {
    kotlin("jvm") version "1.5.10"
    java
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    testImplementation("io.kotest:kotest-runner-junit5:5.3.1")
    testImplementation("io.kotest:kotest-property:5.3.1")
    testImplementation("io.kotest:kotest-framework-datatest:5.3.1")
    testImplementation("io.kotest.extensions:kotest-property-arbs:2.1.2")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}