package org.nuberjonas.pompalette.core.coreapi.graph.api.exceptions;

public class RelationshipNotFoundException extends RuntimeException{
    public RelationshipNotFoundException() {
    }

    public RelationshipNotFoundException(String message) {
        super(message);
    }

    public RelationshipNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
