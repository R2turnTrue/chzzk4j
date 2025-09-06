import java.io.BufferedInputStream
import java.io.InputStream
import java.lang.IllegalStateException
import java.util.Properties

plugins {
    id("java")
    id("eu.kakde.gradle.sonatype-maven-central-publisher") version "1.0.6"
    `java-library`
}

group = "io.github.R2turnTrue"
version = "0.1.2"

val publishProps = Properties()
publishProps.load(
    File("publish.properties").inputStream())

ext["signing.keyId"] = publishProps["signing.keyId"]
ext["signing.password"] = publishProps["signing.password"]
ext["signing.secretKeyRingFile"] = publishProps["signing.secretKeyRingFile"]

val sonatypeUsername = publishProps["nexusUsername"] as String
val sonatypePassword = publishProps["nexusPassword"] as String

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
    implementation("org.seleniumhq.selenium:selenium-java:4.26.0")
    implementation("io.socket:socket.io-client:1.0.2")
}

tasks.test {
    useJUnitPlatform()
}

tasks.compileJava {
    options.encoding = "UTF-8"
}

tasks.javadoc {
    options.encoding = "UTF-8"
}

java {
    withJavadocJar()
    withSourcesJar()
}

object Meta {
    val COMPONENT_TYPE = "java" // "java" or "versionCatalog"
    val GROUP = "io.github.R2turnTrue"
    val ARTIFACT_ID = "chzzk4j"
    val VERSION = "0.1.2"
    val PUBLISHING_TYPE = "AUTOMATIC" // USER_MANAGED or AUTOMATIC
    val SHA_ALGORITHMS = listOf("SHA-256", "SHA-512") // sha256 and sha512 are supported but not mandatory. Only sha1 is mandatory but it is supported by default.
    val DESC = "Unofficial Java API library of CHZZK (치지직, the video streaming service of Naver)"
    val LICENSE = "MIT License"
    val LICENSE_URL = "https://opensource.org/license/mit/"
    val GITHUB_REPO = "R2turnTrue/chzzk4j.git"
    val DEVELOPER_ID = "R2turnTrue"
    val DEVELOPER_NAME = "R2turnTrue"
    val DEVELOPER_ORGANIZATION = "R2turnTrue"
    val DEVELOPER_ORGANIZATION_URL = "https://github.com/R2turnTrue"
}

sonatypeCentralPublishExtension {
    // Set group ID, artifact ID, version, and other publication details
    groupId.set(Meta.GROUP)
    artifactId.set(Meta.ARTIFACT_ID)
    version.set(Meta.VERSION)
    componentType.set(Meta.COMPONENT_TYPE) // "java" or "versionCatalog"
    publishingType.set(Meta.PUBLISHING_TYPE) // USER_MANAGED or AUTOMATIC

    // Set username and password for Sonatype repository
    username.set(System.getenv("SONATYPE_USERNAME") ?: sonatypeUsername)
    password.set(System.getenv("SONATYPE_PASSWORD") ?: sonatypePassword)

    // Configure POM metadata
    pom {
        name.set(Meta.ARTIFACT_ID)
        description.set(Meta.DESC)
        url.set("https://github.com/${Meta.GITHUB_REPO}")
        licenses {
            license {
                name.set(Meta.LICENSE)
                url.set(Meta.LICENSE_URL)
            }
        }
        developers {
            developer {
                id.set(Meta.DEVELOPER_ID)
                name.set(Meta.DEVELOPER_NAME)
                organization.set(Meta.DEVELOPER_ORGANIZATION)
                organizationUrl.set(Meta.DEVELOPER_ORGANIZATION_URL)
            }
        }
        scm {
            url.set("https://github.com/${Meta.GITHUB_REPO}")
            connection.set("scm:git:https://github.com/${Meta.GITHUB_REPO}")
            developerConnection.set("scm:git:https://github.com/${Meta.GITHUB_REPO}")
        }
        issueManagement {
            system.set("GitHub")
            url.set("https://github.com/${Meta.GITHUB_REPO}/issues")
        }
    }
}