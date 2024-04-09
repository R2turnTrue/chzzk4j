package xyz.r2turntrue.chzzk4j.chat;

public interface ChatEventListener {
    default void onConnect() {}

    default void onConnectionClosed(int code, String reason, boolean remote) {}

    default void onError(Exception ex) {
        ex.printStackTrace();
    }

    default void onChat(ChatMessage msg) {}

    default void onDonationChat(DonationMessage msg) {}

    default void onSubscriptionChat(SubscriptionMessage msg) {}
}
