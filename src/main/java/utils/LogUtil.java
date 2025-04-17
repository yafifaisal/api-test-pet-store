package utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class LogUtil {

    private static final Logger logger = LoggerFactory.getLogger(LogUtil.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Logs an informational message.
     *
     * @param message the message to log.
     */
    public static void logInfo(String message) {
        logger.info("[INFO] " + message);
    }

    /**
     * Logs an error message.
     *
     * @param message the message to log.
     */
    public static void logError(String message) {
        logger.error("[ERROR] " + message);
    }

    /**
     * Logs the request details (method, URI, headers, params, body).
     *
     * @param apiName The name of the API.
     * @param method  The HTTP method used.
     * @param uri     The URI being accessed.
     * @param headers The headers sent in the request.
     * @param params  The query parameters sent in the request.
     * @param body    The request body.
     */
    public static void logRequest(String apiName, String method, String uri, Map<String, Object> headers,
            Map<String, Object> params, Object body) {
        String timestamp = ZonedDateTime.now(ZoneId.of("Asia/Jakarta"))
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z"));
        StringBuilder logBuilder = new StringBuilder();
        logBuilder.append("Timestamp: ").append(
                timestamp).append("\n")
                .append("Method: ").append(method).append("\n")
                .append("URI: ").append(uri).append("\n")
                .append("Headers: ").append(formatMap(headers)).append("\n")
                .append("Params: ").append(formatMap(params)).append("\n")
                .append("Body: ").append(toJson(body)).append("\n");
        logger.info(logBuilder.toString());
        Allure.addAttachment("Request", "application/json", logBuilder.toString(), ".json");

    }

    /**
     * Logs the response details (status code, headers, body).
     *
     * @param apiName  The name of the API.
     * @param response The response received.
     */
    public static void logResponse(String apiName, Response response) {
        String timestamp = ZonedDateTime.now(ZoneId.of("Asia/Jakarta"))
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z"));
        StringBuilder logBuilder = new StringBuilder();
        logBuilder.append("Timestamp: ").append(
                timestamp).append("\n")
                .append("Status Code: ").append(response.getStatusCode()).append("\n")
                .append("Headers: ").append(response.getHeaders()).append("\n")
                .append("Body: ").append(response.getBody().asPrettyString()).append("\n");
        logger.info(logBuilder.toString());
        Allure.addAttachment("Response", "application/json", logBuilder.toString(), ".json");
    }

    /**
     * Logs a POJO object as JSON.
     *
     * @param object The POJO object to log.
     */
    public static void logPojo(Object object) {
        try {
            String json = toJson(object);
            logger.info("[POJO] " + json);
        } catch (Exception e) {
            logError("Failed to log POJO: " + e.getMessage());
        }
    }

    /**
     * Converts an object to a JSON string.
     *
     * @param object The object to convert.
     * @return The JSON representation of the object.
     */
    private static String toJson(Object object) {
        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
        } catch (Exception e) {
            logError("Failed to convert object to JSON: " + e.getMessage());
            return "{}";
        }
    }

    /**
     * Utility method to format a map as a string.
     *
     * @param map The map to format.
     * @return The formatted string.
     */
    private static String formatMap(Map<String, Object> map) {
        if (map == null || map.isEmpty()) {
            return "{}";
        }
        StringBuilder builder = new StringBuilder("{");
        map.forEach((key, value) -> builder.append(key).append(": ").append(value).append(", "));
        if (builder.length() > 1) {
            builder.setLength(builder.length() - 2); // Remove trailing comma and space
        }
        builder.append("}");
        return builder.toString();
    }

    @Attachment(value = "API Logs", type = "text/plain", fileExtension = ".log")
    public static String attachLogs() {
        try {
            return InMemoryAppender.getLogs();
        } catch (Exception e) {
            LoggerFactory.getLogger(LogUtil.class).error("Error getting logs: {}", e.getMessage(), e);
            return "Error retrieving logs: " + e.getMessage();
        }
    }

    // @AfterEach // JUnit 5 annotation to run after each test
    // public void clearLogs() {
    // InMemoryAppender.clearLogs();
    // }
}
