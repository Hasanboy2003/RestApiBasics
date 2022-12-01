plugins {
    id("java")
    id("org.sonarqube").version("3.4.0.2513")
}


group = "com.epam"
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
    implementation(project(":repository"))
    implementation(project(":api"))
    implementation(project(":config"))
    implementation(project(":service"))
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}