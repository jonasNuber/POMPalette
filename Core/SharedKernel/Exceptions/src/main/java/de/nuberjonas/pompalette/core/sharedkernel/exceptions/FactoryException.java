package de.nuberjonas.pompalette.core.sharedkernel.exceptions;

public class FactoryException extends RuntimeException{

    public FactoryException(String message) {
        super(message);
    }

    public FactoryException(String message, Throwable cause) {
        super(message, cause);
    }

    public FactoryException(Throwable cause) {
        super(cause);
    }
}
