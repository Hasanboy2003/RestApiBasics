plugins {
    id("java")
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