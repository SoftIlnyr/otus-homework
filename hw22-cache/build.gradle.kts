plugins {
    id("java")
}

group = "com.softi"

repositories {
    mavenCentral()
}

dependencies {

    implementation("ch.qos.logback:logback-classic")
    implementation("org.ehcache:ehcache")

    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}