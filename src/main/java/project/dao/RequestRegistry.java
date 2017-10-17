package project.dao;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

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
            byte[] bytes = Files.readAllBytes(Paths.get(RequestRegistry.class.getResource(fileName).toURI()));
            return new String(bytes);
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException("Failed to load query from path: " + fileName);
        }
    }


}
