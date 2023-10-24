plugins {
    id("java")
}

group = "com.softi"

repositories {
    mavenCentral()
}

dependencies {
    implementation ("org.springframework.boot:spring-boot-starter-web")
    implementation ("org.springframework.boot:spring-boot-starter-data-jdbc")
    implementation ("org.springframework.boot:spring-boot-starter-thymeleaf")

    implementation("org.postgresql:postgresql")
    implementation("org.flywaydb:flyway-core")

    implementation ("org.projectlombok:lombok")
    annotationProcessor ("org.projectlombok:lombok")

    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}