# 채팅 연결하기
이 페이지에서는 채팅에 연결하는 방법에 대해 다룹니다.

{% hint style="warning" %}
현재 chzzk4j는 치지직의 비공개 API에서 OpenAPI로 마이그레이션 중에 있습니다.
되도록이면 공식 API를 활용한 채팅을 이용해주세요.
{% endhint %}

## `ChzzkChat` 생성하기
```java
ChzzkChat chat = new ChzzkChatBuilder(client,
        "연결하려는 채널의 ID")
        .build();

chat.on(ConnectEvent.class, (evt) -> {
        System.out.println("Connected to chat!");
        if (!evt.isReconnecting()) {
            chat.requestRecentChat(50); // 만약 재연결이 아니라면 최근 50개의 채팅을 요청합니다.
            // 요청한 채팅은 채팅 이벤트로 받게 됩니다.
        }
});
```

## `ChzzkChat` 연결하기
```java
// 비동기
chat.connectAsync();

// 동기
chat.connectBlocking();
```