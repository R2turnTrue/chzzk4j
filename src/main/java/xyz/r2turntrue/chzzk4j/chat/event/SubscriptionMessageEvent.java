package xyz.r2turntrue.chzzk4j.chat.event;

import xyz.r2turntrue.chzzk4j.chat.SubscriptionMessage;

public class SubscriptionMessageEvent extends InternalChzzkMsgEvent<SubscriptionMessage> {
    public SubscriptionMessageEvent(SubscriptionMessage msg) {
        super(msg);
    }
}
