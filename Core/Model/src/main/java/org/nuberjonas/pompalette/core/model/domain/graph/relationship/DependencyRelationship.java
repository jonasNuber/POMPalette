package org.nuberjonas.pompalette.core.model.domain.graph.relationship;

import org.nuberjonas.pompalette.core.model.domain.project.dependecies.DependencyScope;
import org.nuberjonas.pompalette.core.model.domain.project.dependecies.DependencyType;
import org.nuberjonas.pompalette.core.model.domain.project.dependecies.DependencyVersion;

import java.util.Objects;

public record DependencyRelationship(String denominator, DependencyVersion dependencyVersion, DependencyScope scope, DependencyType type, String RelationshipType) implements Relationship {

    public static final String INTERNAL = "Internal";
    public static final String EXTERNAL = "External";
    public static final String MANAGING = "Managing";
    public static final String RESOLVING = "Resolving";
    public static final String DEPENDING = "Depending";

    public static DependencyRelationship internal(String version, DependencyScope scope, DependencyType type){
        return new DependencyRelationship(version, new DependencyVersion(version), scope, type, INTERNAL);
    }

    public static DependencyRelationship external(String version, DependencyScope scope, DependencyType type){
        return new DependencyRelationship(version, new DependencyVersion(version), scope, type, EXTERNAL);
    }

    public static DependencyRelationship manages(){
        return new DependencyRelationship("Manages", null, null, null, MANAGING);
    }

    public static DependencyRelationship resolvedBy(DependencyScope scope, DependencyType type){
        return new DependencyRelationship("Resolved by", null, scope, type, RESOLVING);
    }

    public static DependencyRelationship dependsOn(DependencyScope scope, DependencyType type){
        return new DependencyRelationship("Depends on", null, scope, type, DEPENDING);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DependencyRelationship that = (DependencyRelationship) o;
        return Objects.equals(denominator, that.denominator) && Objects.equals(dependencyVersion, that.dependencyVersion) && scope == that.scope && type == that.type && Objects.equals(RelationshipType, that.RelationshipType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(denominator, dependencyVersion, scope, type, RelationshipType);
    }

    @Override
    public String toString() {
        return denominator;
    }
}
