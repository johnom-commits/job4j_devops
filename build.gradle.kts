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

tasks.register("checkJarSize") {
    group = "verification"
    description = "Checks the size of the generated JAR file."

    dependsOn("jar")

    doLast {
        val jarFile = file("build/libs/${project.name}-${project.version}.jar")
        if (jarFile.exists()) {
            val sizeInMB = jarFile.length() / (1024 * 1024)
            if (sizeInMB > 5) {
                println("WARNING: JAR file exceeds the size limit of 5 MB. Current size: ${sizeInMB} MB")
            } else {
                println("JAR file is within the acceptable size limit. Current size: ${sizeInMB} MB")
            }
        } else {
            println("JAR file not found. Please make sure the build process completed successfully.")
        }
    }
}

tasks.named("jar").configure {
    finalizedBy("checkJarSize")
}

tasks.register<Zip>("archiveResources") {
    group = "custom optimization"
    description = "Archives the resources folder into a ZIP file"

    val inputDir = file("src/main/resources")
    val outputDir = layout.buildDirectory.dir("archives")

    inputs.dir(inputDir) // Входные данные для инкрементальной сборки
    outputs.file(outputDir.map { it.file("resources.zip") }) // Выходной файл

    from(inputDir)
    destinationDirectory.set(outputDir)
    archiveFileName.set("resources.zip")

    doLast {
        println("Resources archived successfully at ${outputDir.get().asFile.absolutePath}")
    }
}

tasks.named("build") {
    dependsOn("archiveResources")
}