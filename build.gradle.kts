import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.nio.file.Files.copy
import java.nio.file.Paths.get
import java.nio.file.StandardCopyOption.REPLACE_EXISTING

plugins {
    kotlin("jvm") version "1.3.10"
    application
    id("com.github.johnrengelman.shadow") version "4.0.3"
}

group = "io.futz"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val cdkVersion = "0.19.0"
val sdkVersion = "2.1.3"

dependencies {
    compile(kotlin("stdlib-jdk8"))

    // cdk
    implementation("software.amazon.awscdk:cdk:${cdkVersion}")
    implementation("software.amazon.awscdk:iam:${cdkVersion}")
    implementation("software.amazon.awscdk:lambda:${cdkVersion}")
    implementation("software.amazon.awscdk:s3:${cdkVersion}")
    implementation("software.amazon.awscdk:stepfunctions:${cdkVersion}")

    // sdk
    implementation("software.amazon.awssdk:lambda:${sdkVersion}")

    // test
    testImplementation("junit:junit:4.12")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClassName = "io.futz.MyAppKt"
}

tasks.withType<ShadowJar> {
    baseName = "app"
    classifier = ""
    version = ""
    doLast {
        copy(get("build/libs/app.jar"), get("build/libs/app.zip"), REPLACE_EXISTING)
    }
}

