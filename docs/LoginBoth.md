# LoginBoth
이 페이지에서는 `ChzzkClient`에서 두 개 이상의 `ChzzkLoginAdapter`를 사용하는 방법에 대해 설명합니다.

## 두 개 이상의 `ChzzkLoginAdapter`를 사용하는 이유가 있나요?

현재 치지직의 공식 API는 모두 완성되지 않은 상태로, 로그인된 유저의 팔로우 정보와 같은 일부 정보는 가져올 수 없습니다.

따라서 OpenAPI를 이용한 인증과 네이버 자동 로그인 혹은 쿠키를 이용한 인증 방식 모두 동시에 이용할 수 있도록 구현하였습니다.

## 어떻게 하나요?

간단합니다. `ChzzkClientBuilder`를 이용할 때 `withLoginAdapter`를 여러 번 호출하면 됩니다.

```java
var naverAdapter = new NaverAutologinAdapter("네이버 ID", "네이버 비밀번호");
var oauthAdapter = new ChzzkOauthLoginAdapter("localhost", 8080);

var client = new ChzzkClientBuilder(apiClientId, apiSecret)
        .withDebugMode()
        .withLoginAdapter(naverAdapter)
        .withLoginAdapter(oauthAdapter)
        .build();
```