package xyz.r2turntrue.chzzk4j.types.channel.recommendation;

import xyz.r2turntrue.chzzk4j.types.channel.ChzzkPartialChannel;

public class ChzzkRecommendationChannel {
    private ChzzkPartialChannel channel;

    public ChzzkPartialChannel getChannel() {
        return channel;
    }

    @Override
    public String toString() {
        return "ChzzkRecommendationChannel{" +
                "channel=" + channel +
                '}';
    }
}
