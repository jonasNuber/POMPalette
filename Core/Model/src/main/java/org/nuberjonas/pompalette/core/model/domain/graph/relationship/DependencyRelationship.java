package org.nuberjonas.pompalette.core.model.domain.graph.relationship;

public record DependencyRelationship(String denominator, String version) implements Relationship {

    public static DependencyRelationship internal(String version){
        return new DependencyRelationship("Internal", version);
    }

    public static DependencyRelationship external(String version){
        return new DependencyRelationship("External", version);
    }

    public static DependencyRelationship managed(String version){
        return new DependencyRelationship("Managed", version);
    }

    public static DependencyRelationship resolved(String version){
        return new DependencyRelationship("Resolved", version);
    }

    public static DependencyRelationship transitive(String version){
        return new DependencyRelationship("Transitive", version);
    }

    @Override
    public String toString() {
        return version;
    }
}
