plugins {
	checkstyle
	java
	jacoco
	id("org.springframework.boot") version libs.versions.spring.boot.get()
	id("io.spring.dependency-management") version libs.versions.spring.dependency.management.get()
}

group = "ru.job4j.devops"
version = libs.versions.version.get()

tasks.jacocoTestCoverageVerification {
    violationRules {
        rule {
            limit {
                minimum = "0.8".toBigDecimal()
            }
        }

        rule {
            isEnabled = false
            element = "CLASS"
            includes = listOf("org.gradle.*")

            limit {
                counter = "LINE"
                value = "TOTALCOUNT"
                maximum = "0.3".toBigDecimal()
            }
        }
    }
}

repositories {
	mavenCentral()
}

dependencies {
	compileOnly(libs.lombok)
	annotationProcessor(libs.lombok)
	implementation(libs.spring.starter.web)
	testImplementation(libs.spring.starter.test)
	testRuntimeOnly(libs.junit.launcher)
	testImplementation(libs.junit.jupiter)
	testImplementation(libs.assertj)
}

tasks.withType<Test> {
	useJUnitPlatform()
}
