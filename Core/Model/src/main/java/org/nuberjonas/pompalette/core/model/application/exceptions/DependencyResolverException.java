package org.nuberjonas.pompalette.core.model.application.exceptions;

public class DependencyResolverException extends RuntimeException{
    public DependencyResolverException() {
    }

    public DependencyResolverException(String message) {
        super(message);
    }

    public DependencyResolverException(String message, Throwable cause) {
        super(message, cause);
    }
}
