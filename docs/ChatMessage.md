# 채팅 이벤트
이 페이지에서는 채팅의 메시지, 후원을 비롯한 이벤트 핸들링에 대해 설명합니다.

## 일반 메시지 받기
{% hint style="warning" %}
익명 프로필의 경우 `ChatMessage`의 프로필이 `null`일 수 있습니다!
{% endhint %}

```java
chat.on(ChatMessageEvent.class, (evt) -> {
    ChatMessage msg = evt.getMessage();
    
    if (msg.getProfile() == null) {
        System.out.println("[Chat] 익명: " + msg.getContent());
        return;
    }

    System.out.println("[Chat] " + msg.getProfile().getNickname() + ": " + msg.getContent());
});
```

## 일반 후원 메시지 받기
```java
chat.on(NormalDonationEvent.class, (evt) -> {
    DonationMessage msg = evt.getMessage();
    
    if (msg.getProfile() == null) {
        System.out.println("[Donation] 익명: " + msg.getContent() + ": " + msg.getContent() + " [" + msg.getPayAmount() + "원]");
        return;
    }

    System.out.println("[Donation] " + msg.getProfile().getNickname() + ": " + msg.getContent() + " [" + msg.getPayAmount() + "원]");
});
```

## 구독 메시지 받기
```java
chat.on(SubscriptionMessageEvent.class, (evt) -> {
    SubscriptionMessage msg = evt.getMessage();
    
    if (msg.getProfile() == null) {
        System.out.println("[Subscription] 익명: " + msg.getContent() + ": [" + msg.getSubscriptionMonth() + "개월 " + msg.getSubscriptionTierName() + "]");
        return;
    }

    System.out.println("[Subscription] " + msg.getProfile().getNickname() + ": [" + msg.getSubscriptionMonth() + "개월 " + msg.getSubscriptionTierName() + "]");
});
```

## 미션 후원 메시지 받기
```java
chat.on(MissionDonationEvent.class, (evt) -> {
    MissionDonationMessage msg = evt.getMessage();
    
    if (msg.getProfile() == null) {
        System.out.println("[Mission] 익명: " + msg.getMissionText() + ": [" + msg.getPayAmount() + "원]");
        return;
    }

    System.out.println("[Mission] 익명: " + msg.getMissionText() + ": [" + msg.getPayAmount() + "원]");
});
```