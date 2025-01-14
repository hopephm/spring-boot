val mockkVersion: String by project

plugins {
    val kotlinVersion = "1.9.25"
    val springBootVersion = "3.4.1"
    val ktLintVersion = "12.1.1"

    id("org.springframework.boot") version springBootVersion
    id("org.jlleitschuh.gradle.ktlint") version ktLintVersion

    kotlin("jvm") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion
    kotlin("plugin.jpa") version kotlinVersion
    kotlin("kapt") version kotlinVersion apply true
}

allprojects {
    repositories {
        mavenCentral()
    }
}

ktlint {
    filter {
        exclude { it.file.absolutePath.contains("build/") }
    }
}

subprojects {
    if (project.childProjects.isNotEmpty()) {
        return@subprojects
    }

    group = "kr.hope.spring-boot"
    version = "0.0.1-SNAPSHOT"

    apply {
        plugin("kotlin")
        plugin("kotlin-kapt")
        plugin("kotlin-spring")
        plugin("kotlin-jpa")
        plugin("org.springframework.boot")
        plugin("io.spring.dependency-management")
    }

    java {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    tasks {
        jar { enabled = true }
        bootJar { enabled = false }
        test { useJUnitPlatform() }
    }

    dependencies {
        implementation(kotlin("reflect"))
        implementation("org.springframework.boot:spring-boot-starter")

        testImplementation("org.junit.jupiter:junit-jupiter")
        testImplementation("org.springframework.boot:spring-boot-starter-test")
        testImplementation("io.mockk:mockk:$mockkVersion")
    }
}

tasks.bootJar.get().enabled = false
