package xyz.r2turntrue.chzzk4j.types.channel;

public record BlindMessageRequestBody(String chatChannelId, long messageTime, String senderChannelId) {
}
