# OpenAPIChatSend
이 페이지에서는 OpenAPI를 이용해 채팅을 전송하는 방법에 대해 설명합니다.

{% hint style="info" %}
해당 API는 [API 키](GettingStarted.md), 그리고 [OpenAPI를 통한 인증](LoginOauth.md)를 필요로 합니다!
{% endhint %}

## 채팅 전송

`ChzzkClient`의 `sendChatToLoggedInChannel`을 통해 채팅을 전송할 수 있습니다.

이 때, **API 키를 소유한 계정**이 **`ChzzkClient`에 로그인된 채널**로 채팅이 전송됩니다.

```java
client.sendChatToLoggedInChannel("안녕, 세상!").join();
```

### 결과

```text
(ChzzkClient에 로그인된 채널)

// ...
[API 키 소유자]: 안녕, 세상!
```