import org.asciidoctor.gradle.jvm.AsciidoctorTask
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent

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

dependencies {
    implementation(libs.bundles.web)
    implementation(libs.bundles.data)
    // implementation(libs.bundles.security)
    implementation(libs.hibernate.validator)
    implementation(libs.guava)
    implementation(libs.spring.actuator)
    // developmentOnly(libs.bundles.develop)
    runtimeOnly(libs.bundles.runtime)

    // test
    // testImplementation(libs.bundles.test)
    testImplementation(libs.spring.test)
    testImplementation(libs.spring.restdocs)

    // annotation processor
    kapt(libs.spring.processor)
    annotationProcessor(libs.spring.processor)
    testAnnotationProcessor(libs.spring.processor)

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
    withType<AsciidoctorTask>().named("asciidoctor") {
        dependsOn(test)
    }
    withType<Jar> {
        duplicatesStrategy = DuplicatesStrategy.INCLUDE
    }
}
