package xyz.r2turntrue.chzzk4j.chat.event;

import xyz.r2turntrue.chzzk4j.chat.MissionDonationMessage;

public class MissionDonationEvent extends InternalChzzkMsgEvent<MissionDonationMessage> {
    public MissionDonationEvent(MissionDonationMessage msg) {
        super(msg);
    }
}
