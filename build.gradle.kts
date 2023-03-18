import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.asciidoctor.gradle.jvm.AsciidoctorTask

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("java")
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.dependency)
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotiln.kapt)
    alias(libs.plugins.asciidoctor)
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

ext {
    set("snippetsDir", file("build/generated-snippets"))
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
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

dependencies {
    kapt(libs.spring.annproc)
    kapt(variantOf(libs.querydsl.apt) { classifier("jakarta") })

    implementation(libs.bundles.web)
    implementation(libs.bundles.data)
    // implementation(libs.bundles.security)
    implementation(variantOf(libs.querydsl.jpa) { classifier("jakarta") })
    implementation(libs.bundles.querydsl)
    implementation(libs.guava)
    implementation(libs.spring.actuator)
    // developmentOnly(libs.bundles.develop)
    runtimeOnly(libs.bundles.runtime)
    // testImplementation(libs.bundles.test)
    testImplementation(libs.spring.test)
    testImplementation(libs.spring.restdocs)

    // blaze-persistence
    implementation(libs.blaze.core.api)
    implementation(libs.blaze.querydsl)
    implementation(libs.blaze.core.impl)
    implementation(libs.blaze.hibernate)

    // lombok
    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)
    testCompileOnly(libs.lombok)
    testAnnotationProcessor(libs.lombok)
}

tasks {
    withType<JavaCompile> {
        options.encoding = "UTF-8"
    }
    withType<Test> {
        useJUnitPlatform()
        outputs.dir("snippetsDir")
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
    named<AsciidoctorTask>("asciidoctor") {
        dependsOn(test)
        inputs.dir("snippetsDir")
    }
}
