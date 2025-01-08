package xyz.r2turntrue.chzzk4j.chat.event;

import xyz.r2turntrue.chzzk4j.chat.ChatMessage;

public class ChatMessageEvent extends InternalChzzkMsgEvent<ChatMessage> {
    public ChatMessageEvent(ChatMessage msg) {
        super(msg);
    }
}
