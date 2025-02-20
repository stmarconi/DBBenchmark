package com.dbbenchmark.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

public class ApplicationUtils {
    private static final Properties properties = new Properties();

    public static void readApplicationProperties() throws IOException {
        String rootPath = Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("")).getPath();
        String appConfigPath = rootPath + "application.properties";
        properties.load(new FileInputStream(appConfigPath));
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}
