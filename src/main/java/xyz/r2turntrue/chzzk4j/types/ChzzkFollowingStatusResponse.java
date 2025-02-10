package xyz.r2turntrue.chzzk4j.types;

import xyz.r2turntrue.chzzk4j.types.channel.ChzzkPartialChannel;

import java.util.Objects;

public class ChzzkFollowingStatusResponse {
    public ChzzkPartialChannel channel;

    private ChzzkFollowingStatusResponse() {}

    @Override
    public String toString() {
        return "ChzzkFollowingStatusResponse{" +
                "channel=" + channel +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChzzkFollowingStatusResponse that = (ChzzkFollowingStatusResponse) o;
        return Objects.equals(channel, that.channel);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(channel);
    }
}
