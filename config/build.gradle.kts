plugins {
    id("java")
}

group = "com.epam.esm"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()

}

dependencies {
    implementation("org.springframework:spring-context:5.3.23")
    implementation("org.apache.commons:commons-dbcp2:2.9.0")
    implementation("org.springframework:spring-webmvc:5.3.23")
    implementation("org.springframework:spring-jdbc:5.3.23")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.14.0")
    compileOnly("javax.servlet:javax.servlet-api:4.0.1")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
    implementation("org.postgresql:postgresql:42.5.0")
    implementation("org.slf4j:slf4j-api:2.0.3")
    testImplementation("org.slf4j:slf4j-simple:2.0.3")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}