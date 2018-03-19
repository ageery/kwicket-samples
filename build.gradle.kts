import org.jetbrains.kotlin.gradle.dsl.Coroutines.ENABLE
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    val kotlinVersion = "1.2.30"
    val bootVersion = "2.0.0.RELEASE"
    val dependencyManagementVersion = "1.0.4.RELEASE"
    id("org.jetbrains.kotlin.jvm") version kotlinVersion
    id("io.spring.dependency-management") version dependencyManagementVersion apply false
    id("org.jetbrains.kotlin.plugin.spring") version kotlinVersion apply false
    id("org.springframework.boot") version bootVersion apply false
}

allprojects {
    group = "org.kwicket.samples"
    version = "1.0.0-SNAPSHOT"
}

subprojects {

    plugins.apply("org.jetbrains.kotlin.jvm")
    plugins.apply("io.spring.dependency-management")
    plugins.apply("org.jetbrains.kotlin.plugin.spring")

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
