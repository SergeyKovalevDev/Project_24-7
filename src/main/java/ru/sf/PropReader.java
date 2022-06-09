package ru.sf;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropReader {

    private final String propFilename;

    public PropReader(String propFilename) {
        this.propFilename = propFilename;
    }

    public String getProperty(String propertyName) {
        String propertyValue;
        Properties property = new Properties();
        try (InputStream inputStream = this.getClass().getResourceAsStream(propFilename)) {
            property.load(inputStream);
            propertyValue = property.getProperty(propertyName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return propertyValue;
    }

}
