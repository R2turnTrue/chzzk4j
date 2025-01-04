![](docs/banner.png)
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
    implementation("io.github.R2turnTrue:chzzk4j:0.0.12")
}
```

## usage
Check at [our docs](https://r2turntrue.github.io/chzzk4j)!

## examples
### minimal chat example
```java
ChzzkClient chzzk = new ChzzkClientBuilder("API_CLIENT_ID", "API_SECRET")
        .build();
ChzzkChat chat = new ChzzkChatBuilder(chzzk,
        "CHANNEL_ID")
        .build();

chat.on(ConnectEvent.class, (evt) -> {
    System.out.println("connected to chat :)");
});

chat.on(ChatMessageEvent.class, (evt) -> {
    ChatMessage msg = evt.getMessage();
    
    if (msg.getProfile() == null) {
        System.out.println(String.format("익명: %s", msg.getContent()));
        return;
    }

    System.out.println(
            String.format("[Chat] %s: %s",
                msg.getProfile().getNickname(),
                msg.getContent()));
});

chat.connectBlocking();
```

## features

- [x] get channel information & rules
- [x] get current user's information
- [x] get channel followed status
- [x] async chat integration (read/send)
- [x] get recommendation channels
- [x] fix invalid json (chat)
- [x] load emoji pack
- [x] get live status
- [x] get live detail

### need to implement

- [ ] write javadocs of all methods/classes/etc..
- [ ] parse emoji from chat message
- [ ] get following channels of user that logged in
- [ ] get video information
- [ ] get cheese ranking