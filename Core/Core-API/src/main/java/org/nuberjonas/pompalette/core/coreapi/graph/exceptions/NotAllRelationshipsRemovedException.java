package org.nuberjonas.pompalette.core.coreapi.graph.exceptions;

import java.util.List;

public class NotAllRelationshipsRemovedException extends RuntimeException{
    private final List<Exception> exceptions;

    public NotAllRelationshipsRemovedException(List<Exception> exceptions) {
        super("Not all relationships could be removed. Exceptions: " + exceptions);
        this.exceptions = exceptions;
    }

    public List<Exception> getExceptions() {
        return exceptions;
    }
}
