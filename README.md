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
    implementation("io.github.R2turnTrue:chzzk4j:0.0.2")
}
```

## examples
### initializing
```java
// Unauthorized CHZZK (Anonymous)
Chzzk chzzk = new ChzzkBuilder().build();

// Authorized CHZZK
Chzzk chzzk = new ChzzkBuilder(
        "value of NID_AUT cookie",
        "value of NID_SES cookie").build();
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

System.out.println(rules.getRule())
```

### connecting to chats (read)
```java
ChzzkChat chat = chzzk.chat();
// Connect from channel ID
chat.connectFromChannelId("b2665a30ba249486bf2c134973cfc7a2");

// ** WARNING: the events will be emitted from the other thread!
chat.addListener(new ChatEventListener() {
    @Override
    public void onConnect() {
        System.out.println("Connect received!");
        chat.requestRecentChat(50);
    }

    @Override
    public void onChat(ChatMessage msg) {
        System.out.println("[Chat] " + msg.getProfile().getNickname() + ": " + msg.getContent());
    }

    @Override
    public void onDonationChat(ChatMessage msg) {
        System.out.println("[Donation] " + msg.getProfile().getNickname() + ": " + msg.getContent() + " [" + msg.getExtras().getPayAmount() + "원]");
    }
});
Thread.sleep(500000);
chat.close();
```

### connecting to chats (send)
```java
// chzzk need to be logged-in
ChzzkChat chat = chzzk.chat();
// Connect from channel ID
chat.connectFromChannelId("b2665a30ba249486bf2c134973cfc7a2");

// ** WARNING: the events will be emitted from the other thread!
chat.addListener(new ChatEventListener() {
    @Override
    public void onConnect() {
        System.out.println("Connect received!");
        chat.sendChat("안녕하세요!");
    }
});
Thread.sleep(5000);
chat.close();
```

## features

- [x] get channel information & rules
- [x] get current user's information
- [x] get channel followed status
- [x] chat integration (read/send) **(experimental)**

### need to implement

- [ ] parse emoji from chat message
- [ ] get following channels of user that logged in
- [ ] get video information
- [ ] get live status
- [ ] get live detail
- [ ] get recommendation channels
- [ ] get cheese ranking