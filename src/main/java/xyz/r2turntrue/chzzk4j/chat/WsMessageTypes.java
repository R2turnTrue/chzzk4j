package xyz.r2turntrue.chzzk4j.chat;

class WsMessageTypes {
    static class Commands {
        public static final int PING = 0;
        public static final int PONG = 10000;
        public static final int CONNECT = 100;
        public static final int CONNECTED = 10100;
        public static final int REQUEST_RECENT_CHAT = 5101;
        public static final int RECENT_CHAT = 15101;
        public static final int EVENT = 93006;
        public static final int CHAT = 93101;
        public static final int DONATION = 93102;
        public static final int KICK = 94005;
        public static final int BLOCK = 94006;
        public static final int BLIND = 94008;
        public static final int NOTICE = 94010;
        public static final int PENALTY = 94015;
        public static final int SEND_CHAT = 3101;
    }

    static class ChatTypes {
        public static final int TEXT = 1,
        IMAGE = 2, // ?
        STICKER = 3, // ?
        VIDEO = 4, // ?
        RICH = 5, // ?
        DONATION = 10,
        SYSTEM_MESSAGE = 30; // ?
    }
}
