package org.nuberjonas.pompalette.core.model.domain.graph.relationship;

public record ModuleRelationship(String denominator) implements Relationship {

    public static ModuleRelationship module(){
        return new ModuleRelationship("Module");
    }

    public static ModuleRelationship bom(){
        return new ModuleRelationship("BOM");
    }
    @Override
    public String toString() {
        return denominator;
    }
}
