package org.nuberjonas.pompalette.core.coreapi.graph.exceptions;

import java.util.List;

public class NotAllEntitiesRemovedException extends RuntimeException{
    private final List<Exception> exceptions;

    public NotAllEntitiesRemovedException(List<Exception> exceptions) {
        super("Not all entities could be removed. Exceptions: " + exceptions);
        this.exceptions = exceptions;
    }

    public List<Exception> getExceptions() {
        return exceptions;
    }
}
