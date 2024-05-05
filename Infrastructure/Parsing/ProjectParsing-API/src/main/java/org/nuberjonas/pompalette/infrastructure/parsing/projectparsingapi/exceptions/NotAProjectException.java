package org.nuberjonas.pompalette.infrastructure.parsing.projectparsingapi.exceptions;

public class NotAProjectException extends RuntimeException{
    public NotAProjectException() {
    }

    public NotAProjectException(String message) {
        super(message);
    }

    public NotAProjectException(String message, Throwable cause) {
        super(message, cause);
    }
}
