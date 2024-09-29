package xyz.r2turntrue.chzzk4j.chat;

public class MissionDonationMessage extends DonationMessage {
    public int getDurationTime() {
        return extras.durationTime;
    }

    public String getMissionDonationId() {
        return extras.missionDonationId;
    }

    public String getMissionCreatedTime() {
        return extras.missionCreatedTime;
    }

    public String getMissionEndTime() {
        return extras.missionEndTime;
    }

    public String getMissionText() {
        return extras.missionText;
    }

    public String getMissionStatus() {
        return extras.status;
    }

    public boolean isMissionSucceed() {
        return extras.success;
    }
}
