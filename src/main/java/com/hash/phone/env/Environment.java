package com.hash.phone.env;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Environment {
    private static Environment instance;
    private Properties properties;

    private Environment() {
        this.properties = new Properties();
        try (InputStream input = Environment.class.getClassLoader().getResourceAsStream("application.properties")) {
            properties.load(input);
        } catch (IOException e) {
            throw new EnvironmentInitializationException("Fail to initialize environment", e);
        }
    }

    public static Environment get() {
        if (instance == null) {
            synchronized (Environment.class) {
                if (instance == null) {
                    instance = new Environment();
                }
            }
        }
        return instance;
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public String getProperty(String key, String defaultVal) {
        return properties.getProperty(key, defaultVal);
    }

    public int getPropertyAsInt(String key) {
        return Integer.parseInt(getProperty(key));
    }

    public int getPropertyAsInt(String key, int defaultVal) {
        String val = getProperty(key);
        return val != null ? Integer.parseInt(val) : defaultVal;
    }

    public void setProperty(String key, String value) {
        properties.setProperty(key, value);
    }

    public void removeProperty(String key) {
        properties.remove(key);
    }
}
