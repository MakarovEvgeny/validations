package project.dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

/**
 * Класс для ленивой загрузки sql запросов с кэшированием.
 */
public class RequestRegistry {

    private static ConcurrentMap<String, String> cache = new ConcurrentHashMap<>();

    public static String lookup(String requestId) {
        String request = cache.get(requestId);
        if (request == null) {
            // Нет блокровки, т.к. параллельно могут грузиться разные запросы.
            request = loadRequest(requestId);
            cache.putIfAbsent(requestId, request);
        }

        return request;
    }

    private static String loadRequest(String requestId) {
        String fileName = "/queries/" + requestId + ".sql";
        try {
            try (InputStream is = RequestRegistry.class.getResourceAsStream(fileName);
                 BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
                return br.lines().collect(Collectors.joining("\n"));
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load query from path: " + fileName);
        }
    }


}
