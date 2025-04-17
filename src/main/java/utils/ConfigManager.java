package utils;

import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {

    private static final Properties properties = new Properties();
    private static final String ENV = System.getProperty("env", "test");

    static {
        try {
            String configFile = "config/config." + ENV + ".properties";
            InputStream inputStream = ConfigManager.class.getClassLoader().getResourceAsStream(configFile);

            if (inputStream == null) {
                throw new RuntimeException("Configuration file not found: " + configFile);
            }

            properties.load(inputStream);
            inputStream.close();
            System.out.println("Loaded configuration from: " + configFile);

        } catch (Exception e) {
            System.err.println("Error loading configuration for environment: " + ENV);
            e.printStackTrace();
            throw new RuntimeException("Failed to load config file for environment: " + ENV, e);
        }
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }

    public static String get(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }
}
