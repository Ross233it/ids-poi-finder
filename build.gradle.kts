plugins {
    id("java")
    id("application")
    id("org.springframework.boot") version "3.4.2"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    implementation("mysql:mysql-connector-java:8.0.33")

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    implementation("org.hibernate.validator:hibernate-validator:6.2.0.Final")
//    implementation("javax.validation:javax.validation-api:2.0.1.Final")
}

tasks.test {
    useJUnitPlatform()
}
application {
    mainClass.set("org.poifinder.main.Main")
}