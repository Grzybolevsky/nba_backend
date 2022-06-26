import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val ktorVersion: String by project
val kotlinVersion: String by project
val logbackVersion: String by project
val exposedVersion: String by project
val postgresVersion: String by project
val javaVersion: String by project

plugins {
    application
    kotlin("jvm") version "1.7.0"
    kotlin("plugin.serialization") version "1.7.0"
}
group = "grzybolevsky"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(javaVersion))
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-core:$ktorVersion")
    implementation("io.ktor:ktor-server-netty:$ktorVersion")
    implementation("io.ktor:ktor-server-http-redirect:$ktorVersion")
    implementation("io.ktor:ktor-server-content-negotiation:$ktorVersion")
    implementation("io.ktor:ktor-server-html-builder:$ktorVersion")
    implementation("io.ktor:ktor-server-auth:$ktorVersion")
    implementation("io.ktor:ktor-server-sessions:$ktorVersion")
    implementation("io.ktor:ktor-server-cors:$ktorVersion")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
    implementation("ch.qos.logback:logback-classic:$logbackVersion")

    implementation("io.ktor:ktor-client-core:$ktorVersion")
    implementation("io.ktor:ktor-client-okhttp:$ktorVersion")
    implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")

    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
    implementation("org.postgresql:postgresql:$postgresVersion")

    testImplementation("io.ktor:ktor-server-tests:$ktorVersion")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlinVersion")
    testImplementation("io.ktor:ktor-server-tests-jvm:$ktorVersion")
}

application {
    mainClass.set("com.example.ApplicationKt")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = javaVersion
    }
}

tasks.register<Copy>("copyDependencies") {
    from(configurations.runtimeClasspath.get())
    into("$buildDir/lib")
}

val jar by tasks.getting(Jar::class) {
    dependsOn("copyDependencies")
    manifest {
        attributes["Main-Class"] = "com.example.ApplicationKt"
        attributes["Class-Path"] = configurations.runtimeClasspath.get().joinToString(" ") {
            "lib/" + it.name
        }
    }
}

tasks.register<Exec>("buildDocker") {
    dependsOn("jar")
    workingDir("$projectDir")
    commandLine(
        "docker", "build",
        "--rm", ".", "-t",
        "${project.group}/${project.name}:${project.version}"
    )
}

tasks.register<Exec>("runDocker") {
    dependsOn("buildDocker")
    workingDir("$projectDir")
    commandLine("docker", "run", "${project.name}:${project.version}")
}

tasks {
    create("stage").dependsOn("installDist")
}
