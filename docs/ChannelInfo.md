# Channel Info
이 페이지에서는 채널 정보를 가져오는 방법에 대해 설명합니다.

{% hint style="info" %}
해당 API는 API 키를 필요로 하지 않습니다!
{% endhint %}

## ID를 통해 채널 정보 가져오기
```java
String CHANNEL_ID = "채널 ID";
ChzzkChannel channel = client.getChannel("CHANNEL_ID");

// 채널 이름 출력하기
System.out.println(channel.getChannelName());

ChzzkChannelRules rules = channel.getRules(client);
// 또는...
ChzzkChannelRules rules = client.getChannelChatRules("7ce8032370ac5121dcabce7bad375ced");

// 채널 규칙 출력하기
System.out.println(rules.getRule());
```