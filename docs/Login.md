# Login
이 페이지에서는 `ChzzkClient`를 로그인하는 방법에 대해 설명합니다.

{% hint style="info" %}
현재 chzzk4j에서는 공식 API를 이용해 로그인하는 방식을 지원하지 않습니다.
빠른 시일 내에 준비할 예정이니, 조금만 더 기다려 주세요! :(
{% endhint %}

## `NID_AUT` / `NID_SES` 쿠키를 이용한 방법
네이버의 인증 쿠키를 이용해 인증하는 방법입니다.
```java
ChzzkLegacyLoginAdapter adapter = new ChzzkLegacyLoginAdapter("NID_AUT", "NID_SES");

ChzzkClient client = new ChzzkClientBuilder("API_CLIENT_ID", "API_SECRET")
        .withLoginAdapter(adapter)
        .build();

client.loginAsync().join();
```

## WebDriver를 이용하는 방법
WebDriver를 이용해 `NID_AUT`와 `NID_SES` 쿠키를 자동으로 받을 수 있습니다.
사용하기 위해서는 먼저 ChromeDriver를 설치해야 합니다.
```java
// ChromeDriver가 설치된 위치 (driver/chromedriver.exe)
// 환경변수 PATH에 포함된 디렉터리에 설치되어 있다면 필요하지 않습니다.
System.setProperty("webdriver.chrome.driver", "driver/chromedriver.exe");

var adapter = new NaverAutologinAdapter("네이버 ID", "네이버 비밀번호");

var client = new ChzzkClientBuilder(apiClientId, apiSecret)
        .withDebugMode()
        .withLoginAdapter(adapter)
        .build();

client.loginAsync().join();
```