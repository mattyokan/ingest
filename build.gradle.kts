import com.github.gradle.node.npm.task.NpmTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "3.0.0-SNAPSHOT"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	id("com.github.node-gradle.node") version "3.1.1"
	kotlin("jvm") version "1.6.21"
	kotlin("plugin.spring") version "1.6.21"
}

group = "me.yokan"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
	mavenCentral()
	maven { url = uri("https://repo.spring.io/milestone") }
	maven { url = uri("https://repo.spring.io/snapshot") }
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-webflux")

	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.1")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:1.6.1")
	implementation("rome:rome:1.0")
	implementation("com.chimbori.crux:crux:3.7.0")
	implementation("org.mariadb.jdbc:mariadb-java-client:2.1.2")
	implementation("com.zaxxer:HikariCP:5.0.1")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "17"
	}

	dependsOn("appNpmBuild", "appNpmInstall", "copyWebApp")
}

node {
	download.set(true)

	// Set the work directory for unpacking node
	workDir.set(file("${project.buildDir}/nodejs"))

	// Set the work directory for NPM
	npmWorkDir.set(file("${project.buildDir}/npm"))
}

tasks.create<NpmTask>("appNpmInstall") {
	description = "Installs all dependencies from package.json"
	workingDir.set(file("${project.projectDir}/src/main/webapp"))
	args.set(listOf("install"))
}

tasks.create<NpmTask>("appNpmBuild") {
	description = "Builds production version of the webapp"
	workingDir.set(file("${project.projectDir}/src/main/webapp"))
	args.set(listOf("run", "build"))
}

tasks.create<Copy>("copyWebApp") {
	from("src/main/webapp/build")
	into("build/resources/main/static/.")
}


