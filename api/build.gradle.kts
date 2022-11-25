plugins {
    id("java")
}

group = "com.epam.esm"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework:spring-web:5.3.23")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
    implementation("org.springframework:spring-context:5.3.23")
    implementation(project(":service"))
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}