package xyz.r2turntrue.chzzk4j.session;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.socket.client.IO;
import io.socket.client.Socket;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.jetbrains.annotations.Nullable;
import xyz.r2turntrue.chzzk4j.ChzzkClient;
import xyz.r2turntrue.chzzk4j.session.event.*;
import xyz.r2turntrue.chzzk4j.session.message.SessionChatMessage;
import xyz.r2turntrue.chzzk4j.session.message.SessionDonationMessage;
import xyz.r2turntrue.chzzk4j.session.message.system.ClientboundSystemConnected;
import xyz.r2turntrue.chzzk4j.session.message.system.ClientboundSystemRevoked;
import xyz.r2turntrue.chzzk4j.session.message.system.ClientboundSystemSubscribed;
import xyz.r2turntrue.chzzk4j.session.message.system.ClientboundSystemUnsubscribed;
import xyz.r2turntrue.chzzk4j.util.RawApiUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

class ChzzkSession {

    private static final IO.Options SOCKET_OPTIONS = new IO.Options();

    static {
        SOCKET_OPTIONS.reconnection = false;
        SOCKET_OPTIONS.forceNew = true;
        SOCKET_OPTIONS.timeout = 3000L;
        SOCKET_OPTIONS.transports = new String[] { "websocket" };
    }

    protected String sessionListUrl = "/open/v1/sessions/client";
    protected String sessionCreateUrl = "/open/v1/sessions/auth";
    protected boolean userSession = false;

    HashMap<Class<? extends SessionEvent>, ArrayList<Consumer<? extends SessionEvent>>> handlerMap = new HashMap<>();

    private ChzzkClient chzzk;
    private boolean autoRecreate = true;
    private boolean connected = false;
    private boolean disconnectedForce = true;
    private String sessionKey = "";

    private List<ChzzkSessionSubscriptionType> subscriptions = new ArrayList<>();
    private List<ChzzkSessionSubscriptionType> appliedSubscriptions = new ArrayList<>();

    private Socket socket;

    @Nullable
    public String getSessionKey() {
        if (!connected) {
            return null;
        }

        return sessionKey;
    }

    public boolean isConnected() {
        return connected;
    }

    public boolean willAutoRecreate() {
        return autoRecreate;
    }

    ChzzkSession(ChzzkClient chzzk, boolean autoRecreate) {
        this.chzzk = chzzk;
        this.autoRecreate = autoRecreate;
    }

    public CompletableFuture<Boolean> subscribeAsync(ChzzkSessionSubscriptionType subscriptionType) {
        if (!userSession) {
            throw new IllegalStateException("This API requires the user session!");
        }

        return CompletableFuture.supplyAsync(() -> {
            if (subscriptions.contains(subscriptionType)) {
                return false;
            }

            subscriptions.add(subscriptionType);
            try {
                applySubscriptionAsync(subscriptionType).join();
            } catch (IllegalStateException ignored) {

            }

            return true;
        });
    }

    public CompletableFuture<Boolean> unsubscribeAsync(ChzzkSessionSubscriptionType subscriptionType) {
        return CompletableFuture.supplyAsync(() -> {
            if (!subscriptions.contains(subscriptionType)) {
                return false;
            }

            subscriptions.remove(subscriptionType);
            try {
                takeOffSubscriptionAsync(subscriptionType).join();
            } catch (IllegalStateException ignored) {

            }

            return true;
        });
    }

    private CompletableFuture<Void> applySubscriptionsAsync() throws IOException {
        if (!connected) {
            throw new IllegalStateException("Socket not connected!");
        }

        return CompletableFuture.runAsync(() -> {
            for (ChzzkSessionSubscriptionType subscriptionType : subscriptions) {
                applySubscriptionAsync(subscriptionType).join();
            }
        });
    }

    private CompletableFuture<Void> applySubscriptionAsync(ChzzkSessionSubscriptionType subscriptionType) {
        if (!connected) {
            throw new IllegalStateException("Socket not connected!");
        }

        return CompletableFuture.runAsync(() -> {
            if (appliedSubscriptions.contains(subscriptionType)) {
                return;
            }

            Response response = null;
            try {
                response = chzzk.getHttpClient().newCall(new Request.Builder()
                        .url(ChzzkClient.OPENAPI_URL + subscriptionType.getSubscribeUrl() + "?sessionKey=" + sessionKey)
                        .post(RequestBody.create("", MediaType.parse("application/json")))
                        .build()).execute();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            if (chzzk.isDebug) {
                System.out.println(response);
            }

            if (response.code() != 200) {
                if (!userSession && response.code() == 401) {
                    throw new IllegalStateException("This API requires the user session!");
                }

                try {
                    throw new RuntimeException("Failed to subscribe to event",
                            new IOException(response.body().string()));
                } catch (IOException e) {
                    throw new RuntimeException("Failed to subscribe to event",
                            new IOException(response.toString()));
                }
            }

            appliedSubscriptions.add(subscriptionType);
        });
    }

    private CompletableFuture<Void> takeOffSubscriptionAsync(ChzzkSessionSubscriptionType subscriptionType) {
        if (!connected) {
            throw new IllegalStateException("Socket not connected!");
        }

        return CompletableFuture.runAsync(() -> {
            if (!appliedSubscriptions.contains(subscriptionType)) {
                return;
            }

            Response response = null;
            try {
                response = chzzk.getHttpClient().newCall(new Request.Builder()
                        .url(ChzzkClient.OPENAPI_URL + subscriptionType.getUnsubscribeUrl())
                        .post(RequestBody.create("", MediaType.parse("application/json")))
                        .build()).execute();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            if (chzzk.isDebug) {
                System.out.println(response);
            }

            if (response.code() != 200) {
                if (!userSession && response.code() == 401) {
                    throw new IllegalStateException("This API requires the user session!");
                }

                try {
                    throw new RuntimeException("Failed to subscribe to event",
                            new IOException(response.body().string()));
                } catch (IOException e) {
                    throw new RuntimeException("Failed to subscribe to event",
                            new IOException(response.toString()));
                }
            }

            appliedSubscriptions.remove(subscriptionType);
        });
    }

    public CompletableFuture<Void> createAndConnectAsync() {
        return CompletableFuture.runAsync(() -> {
            try {
                JsonElement urlRaw = RawApiUtils.getContentJson(chzzk.getHttpClient(),
                                RawApiUtils.httpGetRequest(ChzzkClient.OPENAPI_URL + sessionCreateUrl).build(), chzzk.isDebug)
                        .getAsJsonObject()
                        .get("url");

                if (urlRaw.isJsonNull()) {
                    throw new RuntimeException("Failed to fetch session url!");
                }

                connectAsync(urlRaw.getAsString()).join();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private CompletableFuture<Void> connectAsync(String url) {
        disconnectedForce = false;
        return CompletableFuture.runAsync(() -> {
            try {
                socket = IO.socket(url, SOCKET_OPTIONS);

                socket.on("connect", (args) -> {
                    if (chzzk.isDebug)
                        System.out.println(Arrays.toString(args));
                });

                socket.on("disconnect", (args) -> {
                    if (chzzk.isDebug)
                        System.out.println(Arrays.toString(args));
                    appliedSubscriptions.clear();
                    emit(SessionDisconnectedEvent.class, new SessionDisconnectedEvent());

                    if (!disconnectedForce && autoRecreate) {
                        if (chzzk.isDebug)
                            System.out.println("Recreating the session...");
                        createAndConnectAsync().join();
                    }
                });

                socket.on("SYSTEM", (args) -> {
                    if (chzzk.isDebug)
                        System.out.println(Arrays.toString(args));

                    JsonObject json = JsonParser.parseString(args[0].toString())
                            .getAsJsonObject();
                    String msgType = json.get("type").getAsString();

                    if (msgType.equalsIgnoreCase("connected")) {
                        ClientboundSystemConnected msg = chzzk.getGson().fromJson(json.get("data"), ClientboundSystemConnected.class);

                        if(chzzk.isDebug) System.out.println(msg);

                        connected = true;
                        sessionKey = msg.getSessionKey();

                        emit(SessionConnectedEvent.class, new SessionConnectedEvent());

                        try {
                            applySubscriptionsAsync().join();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    } else if (msgType.equalsIgnoreCase("subscribed")) {
                        ClientboundSystemSubscribed msg = chzzk.getGson().fromJson(json.get("data"), ClientboundSystemSubscribed.class);
                        if (chzzk.isDebug) System.out.println(msg);

                        emit(SessionSubscribedEvent.class, new SessionSubscribedEvent(msg.getEventType(), msg.getChannelId()));
                    } else if (msgType.equalsIgnoreCase("unsubscribed")) {
                        ClientboundSystemUnsubscribed msg = chzzk.getGson().fromJson(json.get("data"), ClientboundSystemUnsubscribed.class);
                        if (chzzk.isDebug) System.out.println(msg);

                        emit(SessionUnsubscribedEvent.class, new SessionUnsubscribedEvent(msg.getEventType(), msg.getChannelId()));
                    } else if (msgType.equalsIgnoreCase("revoked")) {
                        ClientboundSystemRevoked msg = chzzk.getGson().fromJson(json.get("data"), ClientboundSystemRevoked.class);
                        ChzzkSessionSubscriptionType eventType = msg.getEventType();

                        emit(SessionSubscriptionRevokedEvent.class, new SessionSubscriptionRevokedEvent(msg.getEventType(), msg.getChannelId()));

                        subscriptions.remove(eventType);
                        appliedSubscriptions.remove(eventType);
                    }
                });

                socket.on("CHAT", (args) -> {
                    if (chzzk.isDebug)
                        System.out.println(Arrays.toString(args));

                    SessionChatMessage msg = chzzk.getGson().fromJson(args[0].toString(), SessionChatMessage.class);
                    if (chzzk.isDebug)
                        System.out.println(msg);

                    emit(SessionChatMessageEvent.class, new SessionChatMessageEvent(msg));
                });

                socket.on("DONATION", (args) -> {
                    if (chzzk.isDebug)
                        System.out.println(Arrays.toString(args));
                    SessionDonationMessage msg = chzzk.getGson().fromJson(args[0].toString(), SessionDonationMessage.class);
                    if (chzzk.isDebug)
                        System.out.println(msg);

                    emit(SessionDonationEvent.class, new SessionDonationEvent(msg));
                });

                socket.connect();
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public CompletableFuture<Void> disconnectAsync() {
        return disconnectAsync(true);
    }

    public CompletableFuture<Void> disconnectAsync(boolean processAsForced) {
        return CompletableFuture.runAsync(() -> {
            disconnectedForce = processAsForced;
            socket.disconnect();
        });
    }

    public <T extends SessionEvent> void on(Class<T> clazz, Consumer<T> action) {
        if (!handlerMap.containsKey(clazz)) {
            handlerMap.put(clazz, new ArrayList<>());
        }

        handlerMap.get(clazz).add(action);
    }

    public <T extends SessionEvent> void emit(Class<T> clazz, T obj) {
        if (handlerMap.containsKey(clazz)) {
            for (Consumer<? extends SessionEvent> handler : handlerMap.get(clazz)) {
                @SuppressWarnings("unchecked")
                Consumer<T> specificHandler = (Consumer<T>) handler;
                specificHandler.accept(obj);
            }
        }
    }
}
