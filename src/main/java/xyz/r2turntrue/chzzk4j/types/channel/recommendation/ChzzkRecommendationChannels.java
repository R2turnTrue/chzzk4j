package xyz.r2turntrue.chzzk4j.types.channel.recommendation;

import java.util.Arrays;

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
}
