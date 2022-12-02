plugins {
    id("java")
    id("org.sonarqube").version("3.4.0.2513")
}

group = "com.epam.esm"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}


dependencies {
    implementation("org.springframework:spring-context:5.3.23")
    implementation("com.fasterxml.jackson.core:jackson-annotations:2.14.0")
    implementation("org.springframework:spring-web:5.3.23")
    implementation("javax.validation:validation-api:2.0.1.Final")
    implementation("org.apache.commons:commons-lang3:3.12.0")
    implementation("org.springframework:spring-tx:5.3.24")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.14.1")


    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
    testImplementation("org.mockito:mockito-junit-jupiter:4.9.0")

    implementation(project(":repository"))
    implementation(project(":config"))
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}