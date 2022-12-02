plugins {
    id("java")
    id("org.sonarqube").version("3.4.0.2513")
}


group = "com.epam"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":repository"))
    implementation(project(":api"))
    implementation(project(":config"))
    implementation(project(":service"))
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}