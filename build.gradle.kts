import org.asciidoctor.gradle.jvm.AbstractAsciidoctorTask.JAVA_EXEC
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent

val asciidoctorExt: Configuration by configurations.creating
val snippetsDir by extra { file("build/generated-snippets/asciidoctor") }

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("java")
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.dependency)
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotiln.kapt)
    alias(libs.plugins.asciidoctor)
    alias(libs.plugins.epages.restdocs)
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

kotlin {
    jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

kapt {
    keepJavacAnnotationProcessors = true
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
    testCompileOnly {
        extendsFrom(configurations.testAnnotationProcessor.get())
    }
}

openapi3 {
    this.setServer("http://localhost:8080/api/v1")
    title = "Sakila REST API Service"
    description = "Sakila REST API Service (Sample Project)"
    version = getVersion().toString()
    format = "yaml"
}

postman {
    baseUrl = "http://localhost:8080/api/v1"
    title = "Sakila REST API Service"
    version = getVersion().toString()
}

dependencies {
    implementation(libs.bundles.web)
    implementation(libs.bundles.data)
    // implementation(libs.bundles.security)
    implementation(libs.hibernate.validator)
    implementation(libs.guava)
    implementation(libs.spring.actuator)
    // developmentOnly(libs.bundles.develop)
    runtimeOnly(libs.bundles.runtime)
    // testImplementation(libs.bundles.test)
    testImplementation(libs.spring.test)

    // annotation processor
    kapt(libs.spring.processor)
    annotationProcessor(libs.spring.processor)
    testAnnotationProcessor(libs.spring.processor)

    // restdocs
    testImplementation(libs.bundles.restdocs)
    asciidoctorExt(libs.spring.restdocs.asciidoctor)
    testImplementation(libs.epages.restdocs.mockmvc)

    // querydsl
    implementation(libs.bundles.querydsl)
    implementation(variantOf(libs.querydsl.jpa) { classifier("jakarta") })
    kapt(variantOf(libs.querydsl.apt) { classifier("jakarta") })

    // blaze-persistence
    implementation(libs.blaze.querydsl)
    compileOnly(libs.blaze.core.api)
    runtimeOnly(libs.blaze.core.impl)
    runtimeOnly(libs.blaze.hibernate)
    testCompileOnly(libs.blaze.core.api)
    testRuntimeOnly(libs.blaze.core.impl)
    testRuntimeOnly(libs.blaze.hibernate)

    // lombok
    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)
    annotationProcessor(libs.lombok.mapstruct)
    testCompileOnly(libs.lombok)
    testAnnotationProcessor(libs.lombok)

    // mapstruct
    implementation(libs.mapstruct)
    kapt(libs.mapstruct.processor)
}

tasks {
    withType<JavaCompile> {
        options.encoding = "UTF-8"
    }
    withType<Test> {
        useJUnitPlatform()
        outputs.dir(snippetsDir)
        testLogging {
            events(
                TestLogEvent.FAILED,
                TestLogEvent.PASSED,
                TestLogEvent.SKIPPED
            )
            debug {
                events(
                    TestLogEvent.FAILED,
                    TestLogEvent.PASSED,
                    TestLogEvent.SKIPPED,
                    TestLogEvent.STANDARD_OUT,
                    TestLogEvent.STANDARD_ERROR
                )
                showStackTraces = true
                exceptionFormat = TestExceptionFormat.FULL
            }
        }
    }
    asciidoctor {
        dependsOn(test)
        configurations(asciidoctorExt.name)
        inputs.dir(snippetsDir)
        inProcess = JAVA_EXEC
        forkOptions {
            jvmArgs(
                "--add-opens", "java.base/sun.nio.ch=ALL-UNNAMED",
                "--add-opens", "java.base/java.io=ALL-UNNAMED"
            )
        }
    }
    bootJar {
        dependsOn(asciidoctor)
        dependsOn("openapi3")
        dependsOn("postman")
    }
    processResources {
        duplicatesStrategy = DuplicatesStrategy.INCLUDE
    }
}
