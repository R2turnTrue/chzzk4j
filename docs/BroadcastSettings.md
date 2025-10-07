# BroadcastSettings
이 페이지에서는 방송 / 채팅 설정을 가져오거나 변경하는 방법에 대해 설명합니다.

{% hint style="info" %}
해당 API는 [API 키](GettingStarted.md), 그리고 [OpenAPI를 통한 인증](LoginOauth.md)를 필요로 합니다!
{% endhint %}

## 방송 설정 가져오기
```java
ChzzkLiveSettings live = client.fetchLiveSettings().join();
System.out.println(live);
```

## 방송 설정 변경하기
```java
// 우선 기존 방송 설정을 가져옵니다.
ChzzkLiveSettings live = client.fetchLiveSettings().join();
// 혹은 새 라이브 설정을 생성합니다.
//ChzzkLiveSettings live = new ChzzkLiveSettings();

// 방송 제목 변경하기
live.setDefaultLiveTitle("안녕, 세상!");

// 카테고리 변경하기
// 우선 방송 카테고리를 검색한 이후 변경합니다.
ChzzkLiveCategory[] categories = client.searchCategories("마인크래프트", 1);
live.setCategory(categories[0]);

// 태그 변경하기
live.getTags().clear();
live.getTags().add("프로그래밍");

// 변경된 방송 설정을 서버에 전송합니다.
client.modifyLiveSettings(live).join();
```

## 채팅 설정 가져오기
```java
ChzzkChatSettings settings = client.fetchChatSettings().join();
System.out.println(settings);
```

## 채팅 설정 변경하기
```java
ChzzkChatSettings settings = client.fetchChatSettings().join();

// 본인인증 여부 설정 조건을 변경합니다.
settings.setChatAvailableCondition(ChzzkChatSettings.ChatAvailableCondition.REAL_NAME);

// 채팅 참여 범위 설정 조건을 변경합니다.
settings.setChatAvailableGroup(ChzzkChatSettings.ChatAvailableGroup.FOLLOWER);
// (위 ChatAvailableGroup이 FOLLOWER일 경우) 최소 팔로잉 기간을 설정합니다.
settings.setMinFollowerMinute(ChzzkChatSettings.MinFollowerMinute.M_60);
// (위 ChatAvailableGroup이 FOLLOWER일 경우) 구독자는 최소 팔로잉 기간 조건 대상에서 제외/허용 할지 여부를 설정합니다.
settings.setAllowSubscriberInFollowerMode(false);

// 변경된 방송 설정을 서버에 전송합니다.
client.modifyChatSettings(settings).join();
```