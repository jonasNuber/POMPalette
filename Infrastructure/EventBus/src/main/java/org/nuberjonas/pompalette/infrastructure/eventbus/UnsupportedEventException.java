package org.nuberjonas.pompalette.infrastructure.eventbus;

public class UnsupportedEventException extends RuntimeException {

    public UnsupportedEventException() {
    }

    public UnsupportedEventException(String message) {
        super(message);
    }

    public UnsupportedEventException(String message, Throwable cause) {
        super(message, cause);
    }
}
