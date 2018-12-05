package org.ontario.goldendelicious.exceptions;

public class ConvertionException extends RuntimeException {

    public ConvertionException() {
        super();
    }

    public ConvertionException(String message) {
        super(message);
    }

    public ConvertionException(String message, Throwable cause) {
        super(message, cause);
    }
}
