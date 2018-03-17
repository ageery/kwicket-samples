import org.jetbrains.kotlin.gradle.dsl.Coroutines.ENABLE
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val x: String by extra { "hello" }

plugins {
    val kotlinVersion = "1.2.30"
    //id("org.jetbrains.kotlin.jvm") version kotlinVersion
    //val kotlinVersion = "1.2.30"
    val bootVersion = "2.0.0.RELEASE"
    val dependencyManagementVersion = "1.0.4.RELEASE"
    id("org.springframework.boot") version bootVersion
    id("org.jetbrains.kotlin.jvm") version kotlinVersion
    id("org.jetbrains.kotlin.plugin.spring") version kotlinVersion
    id("io.spring.dependency-management") version dependencyManagementVersion

}

// version = "1.0.0-SNAPSHOT"
//
//java.sourceSets["main"].resources {
//    srcDir("src/main/kotlin")
//}

//kotlin {
//    experimental.coroutines = ENABLE
//}

repositories {
    mavenCentral()
    jcenter()
    maven(url = "https://oss.jfrog.org/artifactory/oss-snapshot-local")
}

allprojects {

    group = "org.kwicket.samples"
    version = "1.0.0-SNAPSHOT"

}

subprojects {

    tasks.withType<KotlinCompile> {
        kotlin {
            experimental.coroutines = ENABLE
        }
        kotlinOptions {
            jvmTarget = "1.8"
            freeCompilerArgs = listOf("-Xjsr305=strict")
        }
    }

    tasks.withType<JavaCompile> {
        java.sourceSets["main"].resources {
            srcDir("src/main/kotlin")
        }
    }

    repositories {
        mavenLocal()
        mavenCentral()
        jcenter()
        maven(url = "https://oss.jfrog.org/artifactory/oss-snapshot-local")
    }

}
