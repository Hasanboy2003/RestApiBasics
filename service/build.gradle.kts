plugins {
    id("java")
    id("org.sonarqube").version("3.4.0.2513")
}

group = "com.epam.esm"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

sonarqube {
    properties {
        property("sonar.projectName","RestApiBasics")
        property("sonar.host.url","http://localhost:9000")
        property("sonar.login","sqp_26731be87f68b24b256d6c8845854608f3e70445")
    }
}

dependencies {
    implementation("org.springframework:spring-context:5.3.23")
    implementation("com.fasterxml.jackson.core:jackson-annotations:2.14.0")
    implementation("org.springframework:spring-web:5.3.23")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
    testImplementation("org.mockito:mockito-junit-jupiter:4.9.0")
    implementation(project(":repository"))
    implementation(project(":config"))
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}