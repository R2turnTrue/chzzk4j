plugins {
    id("java")
    `maven-publish`
    `java-library`
}

group = "xyz.r2turntrue"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    implementation("org.jetbrains:annotations:24.1.0")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("org.java-websocket:Java-WebSocket:1.5.5")
}

tasks.test {
    useJUnitPlatform()
}

java {
    withJavadocJar()
    withSourcesJar()
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            artifactId = "chzzk4j"
            groupId = "xyz.r2turntrue"
            version = "1.0-SNAPSHOT"

            from(components["java"])
        }
    }
}

afterEvaluate {
    publishing {
        publications {


            register("release", MavenPublication::class) {
                groupId = "com.github.R2turnTrue"

                artifactId = "chzzk4j"

                version = "1.0.0"


            }
        }
    }
}