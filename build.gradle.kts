plugins {
    id("java")
    id("io.freefair.lombok") version "8.4"
}

group = "ru.netology"
version = "1.0-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_21
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("com.codeborne:selenide:7.0.4")
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.0")
    testImplementation("com.github.javafaker:javafaker:1.0.2")
    compileOnly("org.projectlombok:lombok:1.18.30")
    annotationProcessor("org.projectlombok:lombok:1.18.30")
    testCompileOnly("org.projectlombok:lombok:1.18.30")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.30")
}

tasks.test {
    useJUnitPlatform()
}