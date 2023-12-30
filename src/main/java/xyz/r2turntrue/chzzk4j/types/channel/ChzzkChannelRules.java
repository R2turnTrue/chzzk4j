package xyz.r2turntrue.chzzk4j.types.channel;

public class ChzzkChannelRules {
    private boolean agree;
    private String channelId;
    private String rule;
    private String updatedDate;
    private boolean serviceAgree;

    private ChzzkChannelRules() {}

    /**
     * Get the user is agreed to the rules of channel.
     */
    public boolean isAgree() {
        return agree;
    }

    /**
     * Get the id of channel.
     */
    public String getChannelId() {
        return channelId;
    }

    /**
     * Get the rule string of channel.
     */
    public String getRule() {
        return rule;
    }

    /**
     * Get when the rule updated in yyyy-mm-dd HH:mm:ss format.
     */
    public String getUpdatedDate() {
        return updatedDate;
    }

    /**
     * Get the user is agreed to the rules of channel.
     */
    public boolean isServiceAgree() {
        return serviceAgree;
    }

    @Override
    public String toString() {
        return "ChzzkChannelRules{" +
                "agree=" + agree +
                ", channelId='" + channelId + '\'' +
                ", rule='" + rule + '\'' +
                ", updatedDate='" + updatedDate + '\'' +
                ", serviceAgree=" + serviceAgree +
                '}';
    }
}
