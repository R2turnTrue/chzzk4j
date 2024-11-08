import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import xyz.r2turntrue.chzzk4j.types.channel.live.ChzzkLiveDetail;
import xyz.r2turntrue.chzzk4j.types.channel.live.ChzzkLiveStatus;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

public class LiveApiTest extends ChzzkTestBase {

    private final @NotNull String CHANNEL_TO_TEST = "8a59b34b46271960c1bf172bb0fac758";
    private final @NotNull String INVALID_CHANNEL_TO_TEST = "INVALID";

    @Test
    public void testChzzkLiveStatusGet() {
        AtomicReference<ChzzkLiveStatus> status = new AtomicReference<>();
        Assertions.assertDoesNotThrow(() -> {
            status.set(chzzk.getLiveStatus(CHANNEL_TO_TEST));
        });
        System.out.println(status);
    }

    @Test
    public void testInvalidChzzkLiveStatusGet() {
        Assertions.assertThrowsExactly(IOException.class, () -> {
            chzzk.getLiveStatus(INVALID_CHANNEL_TO_TEST);
        });
    }

    @Test
    public void testChzzkLiveDetailGet() {
        AtomicReference<ChzzkLiveDetail> detail = new AtomicReference<>();
        Assertions.assertDoesNotThrow(() -> {
            detail.set(chzzk.getLiveDetail(CHANNEL_TO_TEST));
        });
        System.out.println(detail);
    }

    @Test
    public void testInvalidChzzkLiveDetailGet() {
        Assertions.assertThrowsExactly(IOException.class, () -> {
            chzzk.getLiveDetail(INVALID_CHANNEL_TO_TEST);
        });
    }

}
