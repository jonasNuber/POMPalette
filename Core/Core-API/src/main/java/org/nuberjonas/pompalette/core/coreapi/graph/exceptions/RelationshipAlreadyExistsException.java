package org.nuberjonas.pompalette.core.coreapi.graph.exceptions;

public class RelationshipAlreadyExistsException extends RuntimeException{
    public RelationshipAlreadyExistsException() {
    }

    public RelationshipAlreadyExistsException(String message) {
        super(message);
    }

    public RelationshipAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
