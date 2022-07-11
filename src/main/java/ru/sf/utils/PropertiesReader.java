package ru.sf.utils;

import ru.sf.exceptions.AppException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesReader {

    private PropertiesReader() {}

    public static Properties loadProperties(String propertiesFilename) throws AppException {
        try (InputStream inputStream = PropertiesReader.class.getResourceAsStream(propertiesFilename)) {
            Properties properties = new Properties();
            properties.load(inputStream);
            return properties;
        } catch (IOException | NullPointerException e) {
            throw new AppException("Properties file \"" + propertiesFilename + "\" not found!");
        }
    }
}
