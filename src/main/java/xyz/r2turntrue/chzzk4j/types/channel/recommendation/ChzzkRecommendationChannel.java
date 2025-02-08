package xyz.r2turntrue.chzzk4j.types.channel.recommendation;

import xyz.r2turntrue.chzzk4j.types.channel.ChzzkPartialChannel;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChzzkRecommendationChannel that = (ChzzkRecommendationChannel) o;
        return Objects.equals(channel, that.channel);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(channel);
    }
}
