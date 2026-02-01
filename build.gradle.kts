import com.github.spotbugs.snom.SpotBugsTask

plugins {
	checkstyle
	java
	jacoco
	id("org.springframework.boot") version libs.versions.spring.boot.get()
	id("io.spring.dependency-management") version libs.versions.spring.dependency.management.get()
    alias(libs.plugins.spotbugs)
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

spotbugs {
    toolVersion.set(libs.versions.spotbugs.tool.get())
    ignoreFailures.set(false)

    dependencies {
        spotbugsPlugins(libs.findsecbugs.plugin)
    }
}

dependencies {
	compileOnly(libs.lombok)
	annotationProcessor(libs.lombok)
	implementation(libs.spring.starter.web)
	testImplementation(libs.spring.starter.test)
	testRuntimeOnly(libs.junit.launcher)
	testImplementation(libs.junit.jupiter)
	testImplementation(libs.assertj)
    compileOnly(libs.spotbugs.annotations)
}

tasks.withType<SpotBugsTask>().configureEach {
    reports.create("html") {
        required.set(true)
        outputLocation.set(layout.buildDirectory.file("reports/spotbugs/spotbugs.html"))
    }
}

tasks.withType<Test> {
	useJUnitPlatform()
    finalizedBy(tasks.spotbugsMain)
}

tasks.register<Zip>("zipJavaDoc") {
    group = "documentation" // Группа, в которой будет отображаться задача
    description = "Packs the generated Javadoc into a zip archive"

    dependsOn("javadoc") // Указываем, что задача зависит от выполнения javadoc

    from("build/docs/javadoc") // Исходная папка для упаковки
    archiveFileName.set("javadoc.zip") // Имя создаваемого архива
    destinationDirectory.set(layout.buildDirectory.dir("archives")) // Директория, куда будет сохранен архив
}
