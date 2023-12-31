package xyz.r2turntrue.chzzk4j.chat;

public interface ChatEventListener {
    default void onConnect() {}

    default void onConnectionClosed(int code, String reason, boolean remote) {}

    default void onChat(ChatMessage msg) {}

    default void onDonationChat(ChatMessage msg) {}
}
