package xyz.r2turntrue.chzzk4j.chat;

public interface ChatEventListener {
    default void onConnect(ChzzkChat chat, boolean isReconnecting) {}

    default void onConnectionClosed(int code, String reason, boolean remote, boolean tryingToReconnect) {}

    default void onError(Exception ex) {
        ex.printStackTrace();
    }

    default void onChat(ChatMessage msg) {}

    default void onDonationChat(DonationMessage msg) {}

    default void onMissionDonationChat(MissionDonationMessage msg) {}

    default void onSubscriptionChat(SubscriptionMessage msg) {}
}
