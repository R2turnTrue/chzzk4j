package xyz.r2turntrue.chzzk4j.session.event;

import xyz.r2turntrue.chzzk4j.session.message.SessionDonationMessage;

public class SessionDonationEvent extends SessionEvent {
    private SessionDonationMessage message;

    public SessionDonationEvent(SessionDonationMessage message) {
        this.message = message;
    }

    public SessionDonationMessage getMessage() {
        return message;
    }
}
