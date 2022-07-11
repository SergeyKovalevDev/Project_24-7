package ru.sf.exceptions;

import java.io.IOException;
import java.util.logging.Logger;

public class AppException extends IOException {

    public static final Logger logger = Logger.getLogger(AppException.class.getName());

    public AppException(String message) {
        logger.severe(message);
    }
}
