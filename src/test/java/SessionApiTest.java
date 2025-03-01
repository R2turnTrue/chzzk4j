import org.junit.jupiter.api.Test;
import xyz.r2turntrue.chzzk4j.ChzzkClientBuilder;
import xyz.r2turntrue.chzzk4j.auth.ChzzkOauthLoginAdapter;
import xyz.r2turntrue.chzzk4j.exception.NotLoggedInException;
import xyz.r2turntrue.chzzk4j.session.ChzzkSessionBuilder;
import xyz.r2turntrue.chzzk4j.session.ChzzkSessionSubscriptionType;
import xyz.r2turntrue.chzzk4j.session.event.*;

import java.io.IOException;
import java.util.UUID;

public class SessionApiTest extends ChzzkTestBase {

    @Test
    public void sessionTest() throws IOException, InterruptedException, NotLoggedInException {
        var adapter = new ChzzkOauthLoginAdapter(5000);

        var client = new ChzzkClientBuilder(apiClientId, apiSecret)
                .withDebugMode()
                .withLoginAdapter(adapter)
                .build();

        System.out.println(adapter.getAccountInterlockUrl(apiClientId, false));

        client.loginAsync().join();

        System.out.println(client.fetchLoggedUser());

        var session = new ChzzkSessionBuilder(client)
                .buildUserSession();

        session.subscribeAsync(ChzzkSessionSubscriptionType.CHAT).join();
        session.subscribeAsync(ChzzkSessionSubscriptionType.DONATION).join();

        session.on(SessionConnectedEvent.class, (event) -> {
            System.out.println("Connected!");
        });

        session.on(SessionDisconnectedEvent.class, (event) -> {
            System.out.println("Disconnected :(");
        });

        session.on(SessionChatMessageEvent.class, (event) -> {
            var msg = event.getMessage();
            System.out.printf("[CHAT] %s: %s [at %s]%n", msg.getProfile().getNickname(), msg.getContent(), msg.getMessageTime());
        });

        session.on(SessionDonationEvent.class, (event) -> {
            var msg = event.getMessage();
            System.out.printf("[DONATION] %s: %s [%s]%n", msg.getDonatorNickname(), msg.getDonationText(), msg.getDonationType());
        });

        session.on(SessionRecreateEvent.class, (event) -> {
            System.out.println("Recreating the session...");
        });

        session.on(SessionSubscribedEvent.class, (event) -> {
            System.out.println("Yeah I subscribed to: " + event.getEventType());
        });

        session.on(SessionUnsubscribedEvent.class, (event) -> {
            System.out.println("Yeah I unsubscribed to: " + event.getEventType());
        });

        session.createAndConnectAsync().join();

        Thread.sleep(Long.MAX_VALUE);
    }

}
