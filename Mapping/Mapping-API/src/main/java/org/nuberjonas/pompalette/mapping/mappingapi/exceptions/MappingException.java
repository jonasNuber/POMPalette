package org.nuberjonas.pompalette.mapping.mappingapi.exceptions;

public class MappingException extends RuntimeException{

    public MappingException() {
    }

    public MappingException(String message) {
        super(message);
    }

    public MappingException(String message, Throwable cause) {
        super(message, cause);
    }
}
