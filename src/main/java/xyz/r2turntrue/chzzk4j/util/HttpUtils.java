package xyz.r2turntrue.chzzk4j.util;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class HttpUtils {
    // https://stackoverflow.com/questions/11640025/how-to-obtain-the-query-string-in-a-get-with-java-httpserver-httpexchange
    public static Map<String, String> queryToMap(String query) {
        if (query == null) {
            return null;
        }
        Map<String, String> result = new HashMap<>();
        for (String param : query.split("&")) {
            String[] entry = param.split("=");
            if (entry.length > 1) {
                result.put(
                        URLDecoder.decode(entry[0], StandardCharsets.UTF_8),
                        URLDecoder.decode(entry[1], StandardCharsets.UTF_8)
                );
            } else {
                result.put(
                        URLDecoder.decode(entry[0], StandardCharsets.UTF_8),
                        ""
                );
            }
        }
        return result;
    }

    // https://velog.io/@jeong_lululala/java-http-server
    public static void sendContent(HttpExchange exchange, String content, int rCode) throws IOException, IOException {
        byte[] bytes = content.getBytes();

        exchange.sendResponseHeaders(rCode, bytes.length);

        OutputStream outputStream = exchange.getResponseBody();
        outputStream.write(bytes);
        outputStream.flush();
    }
}
