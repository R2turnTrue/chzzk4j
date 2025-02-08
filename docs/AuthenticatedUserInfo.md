# UserInfo
이 페이지에서는 로그인한 사용자의 정보를 가져오는 방법에 대해 설명합니다.

{% hint style="warning" %}
해당 API는 [내부 API를 통한 인증](Login.md)를 필요로 합니다!
{% endhint %}

{% hint style="info" %}
해당 API는 API 키를 필요로 하지 않습니다!
{% endhint %}

## 로그인한 사용자의 정보 가져오기
```java
ChzzkUser myself = client.fetchLoggedUser();
```

## 로그인한 사용자의 채널 팔로우 상태 확인하기
```java
ChzzkChannelFollowingData following = loginChzzk.fetchFollowingStatus("채널 ID");

if (followingStatus.isFollowing()) {
    System.out.println("현재 해당 채널을 팔로우 중입니다!");
} else {
    System.out.println("현재 해당 채널을 팔로우하지 않았습니다!");
}
```

## 로그인한 사용자의 팔로우 채널 목록 가져오기
```java
ChzzkChannel[] followingChannels = client.fetchFollowingChannels();
```