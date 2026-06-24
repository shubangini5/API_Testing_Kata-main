package com.booking.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

    private static final Properties properties = new Properties();

    static {
        try (InputStream inputStream =
                     ConfigReader.class.getClassLoader()
                             .getResourceAsStream("config.properties")) {

            if (inputStream == null) {
                throw new RuntimeException("app.properties not found in resources");
            }

            properties.load(inputStream);

        } catch (IOException e) {
            throw new RuntimeException("Failed to load properties file", e);
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}