import java.io.BufferedInputStream
import java.io.InputStream
import java.lang.IllegalStateException
import java.util.Properties

plugins {
    id("java")
    `maven-publish`
    `java-library`
    signing
}

group = "io.github.R2turnTrue"
version = "0.0.1"

val publishProps = Properties()
publishProps.load(
    File("publish.properties").inputStream())

ext["signing.keyId"] = publishProps["signing.keyId"]
ext["signing.password"] = publishProps["signing.password"]
ext["signing.secretKeyRingFile"] = publishProps["signing.secretKeyRingFile"]

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
        create<MavenPublication>(rootProject.name) {
            artifactId = "chzzk4j"
            groupId = "io.github.R2turnTrue"

            version = "0.0.1"

            from(components["java"])

            repositories {
                maven {
                    name = "MavenCentral"
                    val releasesRepoUrl = "https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/"
                    //val snapshotsRepoUrl = "https://s01.oss.sonatype.org/content/repositories/snapshots/"
                    //url = uri(if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl)
                    url = uri(releasesRepoUrl)

                    credentials.runCatching {
                        username = publishProps["nexusUsername"] as String
                        password = publishProps["nexusPassword"] as String
                    }
                }
            }

            pom {
                name = "chzzk4j"
                description = "Unofficial Java API library of CHZZK (치지직, the video streaming service of Naver)"
                url = "https://github.com/R2turnTrue/chzzk4j"

                developers {
                    developer {
                        name = "R2turnTrue"
                        email = "r3turntrue@gmail.com"
                        url = "https://github.com/R2turnTrue"
                    }
                }

                scm {
                    connection = "scm:git:git://github.com/R2turnTrue/chzzk4j.git"
                    developerConnection = "scm:git:ssh://github.com:R2turnTrue/chzzk4j.git"
                    url = "https://github.com/R2turnTrue/chzzk4j/tree/master"
                }

                licenses {
                    name = "GNU Lesser General Public License v3.0"
                    url = "https://www.gnu.org/licenses/lgpl-3.0.en.html"
                }
            }
        }
    }
}

signing {
    isRequired = true
    sign(publishing.publications[rootProject.name])
}