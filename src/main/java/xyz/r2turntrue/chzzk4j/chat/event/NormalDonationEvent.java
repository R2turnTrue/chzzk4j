package xyz.r2turntrue.chzzk4j.chat.event;

import xyz.r2turntrue.chzzk4j.chat.DonationMessage;

public class NormalDonationEvent extends InternalChzzkMsgEvent<DonationMessage> {
    public NormalDonationEvent(DonationMessage msg) {
        super(msg);
    }
}
