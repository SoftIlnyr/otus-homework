rootProject.name = "otus-homework"
include("hw01-gradle")
include("hw04-containers")
include("hw06-reflection")
include("hw08-garbage-collector")
include("hw12-cash-machine")
include("hw15-structural-patterns")
include("hw18-jdbc")
include("hw18-jdbc:demo")
include("hw18-jdbc:homework")
include("hw21-jpql")
include("hw22-cache")
include("hw24-webServer")
include("hw25-di")
include("hw28-sprint-boot")
include("hw31-executors")
include("hw32-concurrentCollections")

pluginManagement {
    val jgitver: String by settings
    val dependencyManagement: String by settings
    val springframeworkBoot: String by settings
    val johnrengelmanShadow: String by settings
    val jib: String by settings
    val protobufVer: String by settings
    val sonarlint: String by settings
    val spotless: String by settings

    plugins {
        id("fr.brouillard.oss.gradle.jgitver") version jgitver
        id("io.spring.dependency-management") version dependencyManagement
        id("org.springframework.boot") version springframeworkBoot
        id("com.github.johnrengelman.shadow") version johnrengelmanShadow
        id("com.google.cloud.tools.jib") version jib
        id("com.google.protobuf") version protobufVer
        id("name.remal.sonarlint") version sonarlint
        id("com.diffplug.spotless") version spotless
    }
}

