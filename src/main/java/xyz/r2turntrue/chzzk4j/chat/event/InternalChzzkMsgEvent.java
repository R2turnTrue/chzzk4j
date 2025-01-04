package xyz.r2turntrue.chzzk4j.chat.event;

import xyz.r2turntrue.chzzk4j.chat.ChatMessage;

class InternalChzzkMsgEvent<T extends ChatMessage> extends ChzzkEvent {
    private T msg;

    public InternalChzzkMsgEvent(T msg) {
        this.msg = msg;
    }

    public T getMessage() {
        return msg;
    }
}
