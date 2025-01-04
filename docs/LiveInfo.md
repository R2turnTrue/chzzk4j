# Live Info
이 페이지에서는 생방송 정보를 가져오는 방법에 대해 설명합니다.

{% hint style="info" %}
해당 API는 API 키를 필요로 하지 않습니다!
{% endhint %}

## ID를 통해 채널 라이브 정보 가져오기
```java
ChzzkLiveStatus status = client.fetchLiveStatus("채널 ID");
```