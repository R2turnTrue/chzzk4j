package xyz.r2turntrue.chzzk4j.types.channel.recommendation;

import java.util.Arrays;
import java.util.Objects;

public class ChzzkRecommendationChannels {
    private ChzzkRecommendationChannel[] recommendationChannels;

    public ChzzkRecommendationChannel[] getChannels() {
        return recommendationChannels;
    }

    @Override
    public String toString() {
        return "ChzzkRecommendationChannels{" +
                "recommendationChannels=" + Arrays.toString(recommendationChannels) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChzzkRecommendationChannels that = (ChzzkRecommendationChannels) o;
        return Objects.deepEquals(recommendationChannels, that.recommendationChannels);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(recommendationChannels);
    }
}
