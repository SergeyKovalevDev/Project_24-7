package ru.sf.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.sf.xlsxutils.XLSXParser;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesReader {
    private static final Logger logger = LoggerFactory.getLogger(XLSXParser.class);

    public PropertiesReader() {}

    public Properties loadProperties(String propertiesFilename) {
        Properties properties = new Properties();
        try (InputStream inputStream = this.getClass().getResourceAsStream("/" + propertiesFilename)) {
            if (inputStream == null) throw new IOException();
            properties.load(inputStream);
        } catch (IOException e) {
            logger.error("Properties file \"{}\" not found!", propertiesFilename);
            return null;
        }
        return properties;
    }
}
