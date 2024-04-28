package org.nuberjonas.pompalette.infrastructure.serviceloading.exceptions;

public class ServiceNotFoundException extends RuntimeException{
    public ServiceNotFoundException() {
    }

    public ServiceNotFoundException(String message) {
        super(message);
    }

    public ServiceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
