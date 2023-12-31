plugins {
    id("java")
    `maven-publish`
    `java-library`
}

group = "io.github.R2turnTrue"
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
            groupId = "com.github.R2turnTrue"
            version = "1.0-SNAPSHOT"

            from(components["java"])

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