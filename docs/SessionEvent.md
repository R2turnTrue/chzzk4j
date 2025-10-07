# SessionEvent
이 페이지에서는 OpenAPI를 이용해 `ChzzkSession` 에 이벤트를 구독하는 방법에 대해 설명합니다.

{% hint style="info" %}
해당 API는 [API 키](GettingStarted.md), 그리고 [OpenAPI를 통한 인증](LoginOauth.md)를 필요로 합니다!
{% endhint %}

## 이벤트 구독하기
```java
ChzzkUserSession session = // 세션 생성 코드

// 이벤트를 구독하기 전까지도 이벤트 리스너는 등록할 수 있습니다.
session.on(SessionChatMessageEvent.class, (event) -> {
    var msg = event.getMessage();
    System.out.printf("[CHAT] %s: %s [at %s]%n", msg.getProfile().getNickname(), msg.getContent(), msg.getMessageTime());
});

session.on(SessionDonationEvent.class, (event) -> {
    var msg = event.getMessage();
    System.out.printf("[DONATION] %s: %s [%s]%n", msg.getDonatorNickname(), msg.getDonationText(), msg.getDonationType());
});
        
// 이벤트에 구독하는 시점은 크게 상관이 있지는 않습니다.
session.subscribeAsync(ChzzkSessionSubscriptionType.CHAT).join();

// 다만 세션이 연결되는 도중에 이벤트가 구독되는 경우에는 문제가 발생할 수도 있으니, 이 점에 대해서는 주의 바랍니다.
session.createAndConnectAsync().join();

// 세션이 연결된 이후에 이벤트에 구독해도 됩니다.
session.subscribeAsync(ChzzkSessionSubscriptionType.DONATION).join();
```

## Q. 연결이 끊어지고 다시 세션이 연결되는 과정에서 이벤트를 또 구독해줘야 하나요?

아니요, 끊어졌던 세션이 다시 생성되었을 때 chzzk4j가 다시 자동으로 이벤트를 구독합니다.