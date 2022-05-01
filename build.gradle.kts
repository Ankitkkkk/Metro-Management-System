import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.5.10"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	kotlin("jvm") version "1.5.32"
	kotlin("plugin.spring") version "1.5.32"
}

group = "com.mms"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	// added dependencies
	// Dev Dependency
	// https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-devtools
	// implementation("org.springframework.boot:spring-boot-devtools:2.6.6")
	// /Dev Dependency
	// https://mvnrepository.com/artifact/org.springframework.data/spring-data-mongodb
	implementation("org.springframework.data:spring-data-mongodb:3.2.9")
	// https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-data-mongodb
	implementation("org.springframework.boot:spring-boot-starter-data-mongodb:2.6.3")
	// https://mvnrepository.com/artifact/com.google.code.gson/gson
	implementation("com.google.code.gson:gson:2.8.9")
	// spring security
	// https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-security
	implementation("org.springframework.boot:spring-boot-starter-security:2.6.6")
	// https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-test
	testImplementation("org.springframework.boot:spring-boot-starter-test:2.6.6")
	// https://mvnrepository.com/artifact/io.jsonwebtoken/jjwt
	implementation("io.jsonwebtoken:jjwt:0.9.1")
	// https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-data-jpa
	implementation("org.springframework.boot:spring-boot-starter-data-jpa:2.6.6")
	// https://mvnrepository.com/artifact/mysql/mysql-connector-java
	implementation("mysql:mysql-connector-java:8.0.28")





}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
