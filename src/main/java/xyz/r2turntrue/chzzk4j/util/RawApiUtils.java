package xyz.r2turntrue.chzzk4j.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.*;

import java.io.IOException;

public class RawApiUtils {
    public static Request.Builder httpGetRequest(String url) {
        return new Request.Builder()
                .url(url)
                .get();
    }

    public static Request.Builder httpPostRequest(String url, String body) {
        return new Request.Builder()
                .url(url)
                .post(RequestBody.create(body, MediaType.parse("application/json; charset=utf-8")));
    }

    public static Request.Builder httpPutRequest(String url, String body) {
        return new Request.Builder()
                .url(url)
                .put(RequestBody.create(body, MediaType.parse("application/json; charset=utf-8")));
    }

    public static JsonObject getRawJson(OkHttpClient httpClient, Request request, boolean isDebug) throws IOException {
        Response response = httpClient.newCall(request).execute();

        if (response.isSuccessful()) {
            ResponseBody body = response.body();
            assert body != null;
            String bodyString = body.string();
            //\System.out.println("BD: " + bodyString);

            return JsonParser.parseString(bodyString).getAsJsonObject();
        } else {
            System.err.println(response);
            if (isDebug) {
                ResponseBody body = response.body();
                if (body != null) {
                    String bodyString = body.string();
                    System.out.println("BD: " + bodyString);
                }
            }
            throw new IOException("Response was not successful!");
        }
    }

    public static JsonElement getContentJson(OkHttpClient httpClient, Request request, boolean isDebug) throws IOException {
        return getRawJson(httpClient, request, isDebug).get("content");
    }
}
