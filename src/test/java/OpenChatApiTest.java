import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import xyz.r2turntrue.chzzk4j.ChzzkClientBuilder;
import xyz.r2turntrue.chzzk4j.auth.ChzzkOauthLoginAdapter;
import xyz.r2turntrue.chzzk4j.exception.NotLoggedInException;
import xyz.r2turntrue.chzzk4j.types.ChzzkChatSettings;
import xyz.r2turntrue.chzzk4j.types.channel.ChzzkChannelFollower;
import xyz.r2turntrue.chzzk4j.types.channel.ChzzkChannelSubscriber;

import java.util.Arrays;
import java.util.UUID;

public class OpenChatApiTest extends ChzzkTestBase {
    @Test
    void fetchFollowers() {
        Assertions.assertDoesNotThrow(() -> {
            var adapter = new ChzzkOauthLoginAdapter(5000);

            var client = new ChzzkClientBuilder(apiClientId, apiSecret)
                    .withDebugMode()
                    .withLoginAdapter(adapter)
                    .build();

            System.out.println(adapter.getAccountInterlockUrl(apiClientId, false));
            client.loginAsync().get();

            ChzzkChannelFollower[] followers = client.fetchChannelFollowers().get().getFollowers();

            System.out.println(Arrays.toString(followers));
        });
    }

    @Test
    void fetchSubscribers() {
        Assertions.assertDoesNotThrow(() -> {
            var adapter = new ChzzkOauthLoginAdapter(5000);

            var client = new ChzzkClientBuilder(apiClientId, apiSecret)
                    .withDebugMode()
                    .withLoginAdapter(adapter)
                    .build();

            System.out.println(adapter.getAccountInterlockUrl(apiClientId, false));
            client.loginAsync().get();

            ChzzkChannelSubscriber[] subscribers = client.fetchChannelSubscribers().get().getFollowers();

            System.out.println(Arrays.toString(subscribers));
        });
    }

    @Test
    public void testOpenChatApi() {
        Assertions.assertDoesNotThrow(() -> {
            var adapter = new ChzzkOauthLoginAdapter(5000);

            var client = new ChzzkClientBuilder(apiClientId, apiSecret)
                    .withDebugMode()
                    .withLoginAdapter(adapter)
                    .build();

            System.out.println(adapter.getAccountInterlockUrl(apiClientId, false));

            client.loginAsync().join();

            System.out.println(client.fetchLoggedUser());

            try {
                System.out.println(Arrays.toString(client.searchCategories("마인크래프트").join()));

                var live = client.fetchLiveSettings().join();
                System.out.println(live);

                live.setDefaultLiveTitle(UUID.randomUUID().toString());

                client.modifyLiveSettings(live).join();

                var newLive = client.fetchLiveSettings().join();

                Assertions.assertEquals(live.getDefaultLiveTitle(), newLive.getDefaultLiveTitle());

                var chatId = client.sendChatToLoggedInChannel("안녕, 세상! --> " + UUID.randomUUID()).join();
                System.out.println(chatId);
                client.setAnnouncementOfLoggedInChannel("안녕, 세상! [공지] --> " + UUID.randomUUID()).join();

                var oldSettings = client.fetchChatSettings().join();
                System.out.println(oldSettings);

                var settings = client.fetchChatSettings().join();

                settings.setChatAvailableCondition(ChzzkChatSettings.ChatAvailableCondition.REAL_NAME);
                settings.setChatAvailableGroup(ChzzkChatSettings.ChatAvailableGroup.FOLLOWER);
                settings.setAllowSubscriberInFollowerMode(false);
                settings.setMinFollowerMinute(ChzzkChatSettings.MinFollowerMinute.M_60);
                settings.setChatSlowModeSec(ChzzkChatSettings.ChatSlowModeSec.S_120);
                settings.setChatEmojiMode(true);

                client.modifyChatSettings(settings).join();

                var newSettings = client.fetchChatSettings().join();

                Assertions.assertNotEquals(oldSettings, settings);
                Assertions.assertEquals(settings, newSettings);

                client.modifyChatSettings(oldSettings).join();
            } catch (NotLoggedInException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Test
    public void testAddRestriction() {
        Assertions.assertDoesNotThrow(() -> {
            var adapter = new ChzzkOauthLoginAdapter(5000);

            var client = new ChzzkClientBuilder(apiClientId, apiSecret)
                    .withDebugMode()
                    .withLoginAdapter(adapter)
                    .build();

            System.out.println(adapter.getAccountInterlockUrl(apiClientId, false));

            client.loginAsync().join();

            System.out.println(client.fetchLoggedUser());

            client.restrictChannel("5f9e076713488378d70f870fd4971e32").get();
        });
    }

    @Test
    public void testFetchRestrictions() {
        Assertions.assertDoesNotThrow(() -> {
            var adapter = new ChzzkOauthLoginAdapter(5000);

            var client = new ChzzkClientBuilder(apiClientId, apiSecret)
                    .withDebugMode()
                    .withLoginAdapter(adapter)
                    .build();

            System.out.println(adapter.getAccountInterlockUrl(apiClientId, false));

            client.loginAsync().join();

            System.out.println(client.fetchLoggedUser());

            System.out.println(client.fetchRestrictedChannels().get());
        });
    }

    @Test
    public void testRemoveRestriction() {
        Assertions.assertDoesNotThrow(() -> {
            var adapter = new ChzzkOauthLoginAdapter(5000);

            var client = new ChzzkClientBuilder(apiClientId, apiSecret)
                    .withDebugMode()
                    .withLoginAdapter(adapter)
                    .build();

            System.out.println(adapter.getAccountInterlockUrl(apiClientId, false));

            client.loginAsync().join();

            System.out.println(client.fetchLoggedUser());

            client.unrestrictChannel("5f9e076713488378d70f870fd4971e32").get();
        });
    }

}