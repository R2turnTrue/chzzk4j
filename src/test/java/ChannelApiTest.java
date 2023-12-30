import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import xyz.r2turntrue.chzzk4j.Chzzk;
import xyz.r2turntrue.chzzk4j.ChzzkBuilder;
import xyz.r2turntrue.chzzk4j.exception.ChannelNotExistsException;
import xyz.r2turntrue.chzzk4j.exception.NotExistsException;
import xyz.r2turntrue.chzzk4j.exception.NotLoggedInException;
import xyz.r2turntrue.chzzk4j.types.ChzzkUser;
import xyz.r2turntrue.chzzk4j.types.channel.ChzzkChannel;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.jupiter.api.Assertions;
import xyz.r2turntrue.chzzk4j.types.channel.ChzzkChannelFollowingData;
import xyz.r2turntrue.chzzk4j.types.channel.ChzzkChannelRules;

// 8e7a6f0a0b1f0612afee1a673e94027d - 레고칠칠
// c2186ca6edb3a663f137b15ed7346fac - 리얼진짜우왁굳
// 두 채널 모두 팔로우한 뒤 테스트 진행해주세요.
//
// 22bd842599735ae19e454983280f611e - ENCHANT
// 위 채널은 팔로우 해제 후 테스트 진행해주세요.
public class ChannelApiTest extends ChzzkTestBase {
    public final String FOLLOWED_CHANNEL_1 = "8e7a6f0a0b1f0612afee1a673e94027d"; // 레고칠칠
    public final String FOLLOWED_CHANNEL_2 = "c2186ca6edb3a663f137b15ed7346fac"; // 리얼진짜우왁굳
    public final String UNFOLLOWED_CHANNEL = "22bd842599735ae19e454983280f611e"; // ENCHANT

    @Test
    void gettingNormalChannelInfo() throws IOException {
        AtomicReference<ChzzkChannel> channel = new AtomicReference<>();
        Assertions.assertDoesNotThrow(() ->
                channel.set(chzzk.getChannel(FOLLOWED_CHANNEL_2)));

        System.out.println(channel);
    }

    @Test
    void gettingInvalidChannelInfo() throws IOException {
        Assertions.assertThrowsExactly(ChannelNotExistsException.class, () -> {
            chzzk.getChannel("invalidchannelid");
        });
    }

    @Test
    void gettingNormalChannelRules() throws IOException {
        AtomicReference<ChzzkChannelRules> rule = new AtomicReference<>();
        Assertions.assertDoesNotThrow(() ->
                rule.set(chzzk.getChannelChatRules(FOLLOWED_CHANNEL_1)));

        System.out.println(rule);
    }

    @Test
    void gettingInvalidChannelRules() throws IOException {
        Assertions.assertThrowsExactly(NotExistsException.class, () -> {
            chzzk.getChannelChatRules("invalidchannel or no rule channel");
        });
    }

    @Test
    void gettingFollowStatusAnonymous() throws IOException {
        Assertions.assertThrowsExactly(NotLoggedInException.class, () ->
                chzzk.getFollowingStatus("FOLLOWED_CHANNEL_1"));
    }

    @Test
    void gettingFollowStatus() throws IOException {
        AtomicReference<ChzzkChannelFollowingData> followingStatus = new AtomicReference<>();
        Assertions.assertDoesNotThrow(() ->
                followingStatus.set(loginChzzk.getFollowingStatus(FOLLOWED_CHANNEL_1)));

        System.out.println(followingStatus);

        Assertions.assertEquals(followingStatus.get().isFollowing(), true);

        Assertions.assertDoesNotThrow(() ->
                followingStatus.set(loginChzzk.getFollowingStatus(UNFOLLOWED_CHANNEL)));

        System.out.println(followingStatus);

        Assertions.assertEquals(followingStatus.get().isFollowing(), false);
    }

    @Test
    void gettingUserInfo() throws IOException, NotLoggedInException {
        ChzzkUser currentUser = loginChzzk.getLoggedUser();
        System.out.println(currentUser);
        Assertions.assertEquals(currentUser.getUserId(), currentUserId);
    }
}
