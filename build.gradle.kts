import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val ktorVersion: String by project
val kotlinVersion: String by project
val logbackVersion: String by project
val exposedVersion: String by project
val postgresVersion: String by project

plugins {
    application
    id("jvm-test-suite")
    id("org.jlleitschuh.gradle.ktlint") version "10.2.1"
    kotlin("jvm") version "1.6.21"
    kotlin("plugin.jpa") version "1.6.21"
    kotlin("plugin.serialization") version "1.6.21"
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
    implementation("io.ktor:ktor-server-core-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-auth-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-content-negotiation-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-sessions-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-http-redirect-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-netty-jvm:$ktorVersion")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm:$ktorVersion")

    implementation("io.ktor:ktor-client-core-jvm:$ktorVersion")
    implementation("io.ktor:ktor-client-apache-jvm:$ktorVersion")

    implementation("ch.qos.logback:logback-classic:$logbackVersion")

    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-kotlin-datetime:$exposedVersion")
    implementation("org.postgresql:postgresql:$postgresVersion")

    testImplementation("io.ktor:ktor-server-tests-jvm:$ktorVersion")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlinVersion")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "18"
    }
}

project.ext["development"] = true

application {
    mainClass.set("com.example.ApplicationKt")
    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
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
