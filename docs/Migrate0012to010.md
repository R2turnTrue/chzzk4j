# Migrate

chzzk4j의 `0.1.0` 버전에서는 많은 사항이 변경되었습니다. 이에 따라 업그레이드를 위한 문서를 작성하였습니다.

## `ChzzkClient`

`Chzzk` 클래스가 `ChzzkClient`로, `ChzzkBuilder`가 `ChzzkClientBuilder`로 변경되었습니다.

## `getXXX` -> `fetchXXX`

`Chzzk` 클래스의 `getChannel`, `getLiveStatus` 등 정보를 받는 메서드의 이름의 `fetchChannel`, `fetchLiveStatus` 등으로 변경되었습니다.

## 채팅

`Chzzk` 클래스의 `chat` 메서드가 사라지고, 채팅 이벤트 핸들링 방식이 변경되는 등 다양한 수정 사항이 있었습니다. 채팅 연동 부분은 [채팅 연결하기](ChatConnect.md), [채팅 메시지](ChatMessage.md) 등을 참고해 수정해주세요.

## 로그인/인증

다양한 로그인 방식을 수용할 수 있도록 로그인 코드를 대폭 수정하였습니다. [로그인하기](Login.md) 문서를 참고해 로그인 관련 코드을 변경해주세요.