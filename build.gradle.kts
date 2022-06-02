import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val ktorVersion: String by project
val kotlinVersion: String by project
val logbackVersion: String by project
val exposedVersion: String by project
val postgresVersion: String by project

plugins {
    id("jvm-test-suite")
    id("org.jlleitschuh.gradle.ktlint") version "10.3.0"
    id("org.sonarqube") version "3.3"
    kotlin("jvm") version "1.7.0-RC2"
    kotlin("plugin.jpa") version "1.7.0-RC2"
    kotlin("plugin.serialization") version "1.7.0-RC2"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(18))
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
    implementation("io.ktor:ktor-server-auth:$ktorVersion")
    implementation("io.ktor:ktor-server-content-negotiation:$ktorVersion")
    implementation("io.ktor:ktor-server-sessions:$ktorVersion")
    implementation("io.ktor:ktor-server-http-redirect:$ktorVersion")
    implementation("io.ktor:ktor-server-netty:$ktorVersion")
    implementation("io.ktor:ktor-server-html-builder:$ktorVersion")
    implementation("io.ktor:ktor-server-cors:$ktorVersion")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
    implementation("ch.qos.logback:logback-classic:$logbackVersion")

    implementation("io.ktor:ktor-client-core:$ktorVersion")
    implementation("io.ktor:ktor-client-okhttp:$ktorVersion")
    implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")

    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-kotlin-datetime:$exposedVersion")
    implementation("org.postgresql:postgresql:$postgresVersion")

    testImplementation("io.ktor:ktor-server-tests:$ktorVersion")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlinVersion")
    testImplementation("io.ktor:ktor-server-tests-jvm:2.0.2")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "18"
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

testing {
    suites {
        val test by getting(JvmTestSuite::class) {
            useJUnitJupiter()
        }

        val integration by registering(JvmTestSuite::class) {
            dependencies {
                implementation(project)
            }

            targets {
                all {
                    testTask.configure {
                        shouldRunAfter(test)
                    }
                }
            }
        }
    }
}

val integrationImplementation: Configuration by configurations.getting {
    extendsFrom(configurations.testImplementation.get())
}

tasks.check {
    dependsOn(testing.suites.named("integration"))
}

tasks.compileKotlin {
    dependsOn(tasks.named("ktlintFormat"))
}

tasks.clean {
    dependsOn(tasks.named("ktlintFormat"))
}

sonarqube {
    properties {
        property("sonar.projectKey", "nba_backend")
        property("sonar.organization", "grzybolevsky")
        property("sonar.host.url", "https://sonarcloud.io")
    }
}
