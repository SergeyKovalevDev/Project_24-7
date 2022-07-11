package ru.sf.utils;

import ru.sf.exceptions.AppException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesReader {

    public PropertiesReader() {}

    public Properties loadProperties(String propertiesFilename) throws AppException {
        Properties properties = new Properties();
        try (InputStream inputStream = this.getClass().getResourceAsStream("/" + propertiesFilename)) {
            if (inputStream == null) throw new IOException();
            properties.load(inputStream);
            return properties;
        } catch (IOException e) {
            throw new AppException("Properties file \"" + propertiesFilename + "\" not found!");
        }
    }
}
