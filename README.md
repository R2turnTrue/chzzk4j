# chzzk4j

![](https://img.shields.io/maven-central/v/io.github.R2turnTrue/chzzk4j) / 
[Example Minecraft Plugin](https://github.com/R2turnTrue/chzzk4j_demo) / 
[Discord Server](https://discord.gg/gtJ265XZWn)

Unofficial Java API library of CHZZK (치지직, the video streaming service of Naver)

* This library is not completed. Please contribute a lot through Pull-Request!
* Please feel free to create an issue if you have any problems!

## installation

```kotlin
repositories {
    mavenCentral()
}

dependencies {
    implementation("io.github.R2turnTrue:chzzk4j:0.0.9")
}
```

## examples
### initializing
```java
// Debug Mode (Enables debug logs of System.out)
Chzzk chzzk = new ChzzkBuilder()
        .withDebugMode()
        .build();

// Unauthorized CHZZK (Anonymous)
Chzzk chzzk = new ChzzkBuilder().build();

// You can get values of NID_AUT and NID_SES from developer tools of your browser.
// In Chrome, you can see the values from
// Application > Cookies > https://chzzk.naver.com

// Authorized CHZZK
Chzzk chzzk = new ChzzkBuilder()
                .withAuthorization(NID_AUT, NID_SES)
                .build();
```

### get information of logged-in user

### get channel information
```java
String CHANNEL_ID = "7ce8032370ac5121dcabce7bad375ced";
ChzzkChannel channel = chzzk.getChannel("CHANNEL_ID");

// Print nickname of channel
System.out.println(channel.getChannelName());

ChzzkChannelRules rules = channel.getRules(chzzk);
// or
ChzzkChannelRules rules = chzzk.getChannelChatRules("7ce8032370ac5121dcabce7bad375ced");

System.out.println(rules.getRule());
```

### connecting to chats (read)
```java
ChzzkChat chat = chzzk.chat("7f148028d1b8b3feab3a5442badded46")
        .withChatListener(new ChatEventListener() {
            @Override
            public void onConnect(ChzzkChat chat, boolean isReconnecting) {
                System.out.println("Connect received!");

                // !! when re-connecting, you shouldn't need to request recent chats!
                if (!isReconnecting)
                    chat.requestRecentChat(50);
            }

            @Override
            public void onError(Exception ex) {
                ex.printStackTrace();
            }

            @Override
            public void onChat(ChatMessage msg) {

                System.out.println(msg);

                if (msg.getProfile() == null) {
                    System.out.println("[Chat] 익명: " + msg.getContent());
                    return;
                }

                System.out.println("[Chat] " + msg.getProfile().getNickname() + ": " + msg.getContent());
            }

            @Override
            public void onDonationChat(DonationMessage msg) {
                if (msg.getProfile() == null) {
                    System.out.println("[Donation] 익명: " + msg.getContent() + ": " + msg.getContent() + " [" + msg.getPayAmount() + "원]");
                    return;
                }

                System.out.println("[Donation] " + msg.getProfile().getNickname() + ": " + msg.getContent() + " [" + msg.getPayAmount() + "원]");
            }

            @Override
            public void onSubscriptionChat(SubscriptionMessage msg) {
                if (msg.getProfile() == null) {
                    System.out.println("[Subscription] 익명: " + msg.getContent() + ": [" + msg.getSubscriptionMonth() + "개월 " + msg.getSubscriptionTierName() + "]");
                    return;
                }

                System.out.println("[Subscription] " + msg.getProfile().getNickname() + ": [" + msg.getSubscriptionMonth() + "개월 " + msg.getSubscriptionTierName() + "]");
            }
        })
        .build();

chat.connectBlocking();
Thread.sleep(700000);
chat.closeBlocking();
```

### connecting to chats (send)
```java
Chzzk chzzk = new ChzzkBuilder()
        .withAuthorization(NID_AUT, NID_SES)
        .build();

ChzzkChat chat = chzzk.chat("7f148028d1b8b3feab3a5442badded46")
        .withChatListener(new ChatEventListener() {
            @Override
            public void onConnect(ChzzkChat chat, boolean isReconnecting) {
                System.out.println("Connect received!");
                chat.sendChat("안녕하세요!");
            }
        })
        .build();

chat.connectBlocking();
Thread.sleep(700000);
chat.closeBlocking();
```

## features

- [x] get channel information & rules
- [x] get current user's information
- [x] get channel followed status
- [x] async chat integration (read/send)
- [x] get recommendation channels
- [x] fix invalid json (chat)

### need to implement

- [ ] parse emoji from chat message
- [ ] get following channels of user that logged in
- [ ] get video information
- [ ] get live status
- [ ] get live detail
- [ ] get cheese ranking