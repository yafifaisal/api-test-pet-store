package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class RequestBuilder {

    private final Map<String, Object> headers;
    private final Map<String, Object> payload;
    private final Map<String, Object> params;

    /**
     * Constructor to initialize request with or without a JSON template.
     *
     * @param templatePath Path to the JSON template, can be null or empty.
     */
    public RequestBuilder(String templatePath) {
        this.headers = new HashMap<>();
        this.params = new HashMap<>();

        if (templatePath == null || templatePath.isEmpty()) {
            LogUtil.logInfo("No request template specified. Initializing empty payload.");
            this.payload = new HashMap<>();
        } else {
            this.payload = loadTemplate(templatePath);
        }
    }

    /**
     * Loads a JSON file as a map.
     *
     * @param path Path to the JSON file.
     * @return Map representation of the JSON file.
     */
    @SuppressWarnings("unchecked")
    private Map<String, Object> loadTemplate(String path) {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(path)) {
            if (inputStream != null) {
                var mapper = new ObjectMapper();
                return mapper.readValue(inputStream, Map.class);
            } else {
                LogUtil.logError("Template file not found in classpath: " + path);
                return new HashMap<>();
            }
        } catch (Exception e) {
            LogUtil.logError("Failed to load template from classpath: " + path + ". Error: " + e.getMessage());
            return new HashMap<>();
        }
    }

    /**
     * Adds or updates a header field.
     *
     * @param key   Header name.
     * @param value Header value.
     * @return Updated RequestBuilder instance.
     */
    public RequestBuilder putHeader(String key, Object value) {
        if (key != null && value != null) {
            headers.put(key, value);
        } else {
            LogUtil.logError("Header key or value is null");
        }
        return this;
    }

    /**
     * Removes a header field.
     *
     * @param key Header name to be removed.
     * @return Updated RequestBuilder instance.
     */
    public RequestBuilder removeHeader(String key) {
        if (headers.remove(key) != null) {
            LogUtil.logInfo("Removed header: " + key);
        } else {
            LogUtil.logError("Header not found: " + key);
        }
        return this;
    }

    /**
     * Adds or updates a payload field.
     *
     * @param key   Payload field name.
     * @param value Payload field value.
     * @return Updated RequestBuilder instance.
     */
    public RequestBuilder putPayload(String key, Object value) {
        if (key != null && value != null) {
            payload.put(key, value);
        } else {
            LogUtil.logError("Payload key or value is null");
        }
        return this;
    }

    /**
     * Removes a payload field.
     *
     * @param key Payload field name to be removed.
     * @return Updated RequestBuilder instance.
     */
    public RequestBuilder removePayload(String key) {
        if (payload.remove(key) != null) {
            LogUtil.logInfo("Removed payload: " + key);
        } else {
            LogUtil.logError("Payload not found: " + key);
        }
        return this;
    }

    /**
     * Adds or updates a query parameter.
     *
     * @param key   Parameter name.
     * @param value Parameter value.
     * @return Updated RequestBuilder instance.
     */
    public RequestBuilder putParam(String key, Object value) {
        if (key != null && value != null) {
            params.put(key, value);
        } else {
            LogUtil.logError("Parameter key or value is null");
        }
        return this;
    }

    /**
     * Removes a query parameter.
     *
     * @param key Parameter name to be removed.
     * @return Updated RequestBuilder instance.
     */
    public RequestBuilder removeParam(String key) {
        if (params.remove(key) != null) {
            LogUtil.logInfo("Removed param: " + key);
        } else {
            LogUtil.logError("Param not found: " + key);
        }
        return this;
    }

    /**
     * Clears all headers.
     */
    public void clearHeaders() {
        headers.clear();
        LogUtil.logInfo("Cleared all headers.");
    }

    /**
     * Clears all payload data.
     */
    public void clearPayload() {
        payload.clear();
        LogUtil.logInfo("Cleared all payload data.");
    }

    /**
     * Clears all parameters.
     */
    public void clearParams() {
        params.clear();
        LogUtil.logInfo("Cleared all parameters.");
    }

    /**
     * Gets the headers map.
     *
     * @return Map of headers.
     */
    public Map<String, Object> getHeaders() {
        return headers;
    }

    /**
     * Gets the payload map.
     *
     * @return Map of payload.
     */
    public Map<String, Object> getPayload() {
        return payload;
    }

    /**
     * Gets the parameters map.
     *
     * @return Map of parameters.
     */
    public Map<String, Object> getParams() {
        return params;
    }
}
