# Installation
chzzk4j와 그 하위 의존성을 관리하기 위해 의존성 관리 도구를 사용하는 것을 추천합니다.
![](https://img.shields.io/maven-central/v/io.github.R2turnTrue/chzzk4j)
{% tabs %}
{% tab title="Gradle" %}
```groovy
repositories {
    mavenCentral()
}

dependencies {
    implementation 'io.github.R2turnTrue:chzzk4j:<라이브러리 버전>'
}
```
{% endtab %}
{% tab title="Gradle - Kotlin DSL" %}
```kotlin
repositories {
    mavenCentral()
}

dependencies {
    implementation("io.github.R2turnTrue:chzzk4j:<라이브러리 버전>")
}
```
{% endtab %}
{% tab title="Maven" %}
```xml
<dependency>
    <groupId>io.github.R2turnTrue</groupId>
    <artifactId>chzzk4j</artifactId>
    <version>라이브러리 버전</version>
</dependency>
```
{% endtab %}
{% endtabs %}