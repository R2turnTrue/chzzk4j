package xyz.r2turntrue.chzzk4j.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.IOException;

public class RawApiUtils {
    public static Request.Builder httpGetRequest(String url) {
        return new Request.Builder()
                .url(url)
                .get();
    }

    public static JsonObject getRawJson(OkHttpClient httpClient, Request request) throws IOException {
        Response response = httpClient.newCall(request).execute();

        if (response.isSuccessful()) {
            ResponseBody body = response.body();
            assert body != null;
            String bodyString = body.string();
            //System.out.println("BD: " + bodyString);

            return JsonParser.parseString(bodyString).getAsJsonObject();
        } else {
            System.err.println(response);
            throw new IOException("Response was not successful!");
        }
    }

    public static JsonElement getContentJson(OkHttpClient httpClient, Request request) throws IOException {
        return getRawJson(httpClient, request).get("content");
    }
}
