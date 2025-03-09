# SessionConnect
이 페이지에서는 OpenAPI를 이용해 `ChzzkSession` 을 생성하고 연결하는 방법에 대해 설명합니다.

{% hint style="info" %}
해당 API는 [API 키](GettingStarted.md), 그리고 [OpenAPI를 통한 인증](LoginOauth.md)를 필요로 합니다!
{% endhint %}

## 세션 생성 / 연결하기
```java
// 먼저 OpenAPI 인증을 완료한 클라이언트를 준비합니다.
ChzzkOauthLoginAdapter adapter = new ChzzkOauthLoginAdapter("localhost", 포트);

ChzzkClient client = new ChzzkClientBuilder("API_CLIENT_ID", "API_SECRET")
        .withDebugMode()
        .withLoginAdapter(adapter)
        .build();

client.loginAsync().join();

// ...

ChzzkUserSession session = new ChzzkSessionBuilder(client)
        // chzzk4j는 서버측에서 세션의 연결을 끊었을 때 자동으로 세션을 다시 생성합니다.
        // 기본적으로는 이 기능이 활성화되어 있으나, 만약 비활성화하길 원한다면 Builder에서 withAutoRecreate를 false로 설정할 수 있습니다.
        //.withAutoRecreate(false)
        .buildUserSession();
        // 원한다면 Client Session을 이용할 수도 있습니다만..
        // 현재로서는 되도록이면 User Session을 활용해야 합니다.
        //.buildClientSession();

session.on(SessionConnectedEvent.class, (event) -> {
    System.out.println("Connected!");
});

session.createAndConnectAsync().join();
```