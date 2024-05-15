package org.nuberjonas.pompalette.core.model.domain.graph.relationship;

import java.util.Objects;

public record ModuleRelationship(String denominator) implements Relationship {

    public static final String MODULE = "Module";
    public static final String BOM = "BOM";

    public static ModuleRelationship module(){
        return new ModuleRelationship(MODULE);
    }

    public static ModuleRelationship bom(){
        return new ModuleRelationship(BOM);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ModuleRelationship that = (ModuleRelationship) o;
        return Objects.equals(denominator, that.denominator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(denominator);
    }

    @Override
    public String toString() {
        return denominator;
    }

    @Override
    public String tooltipText() {
        return denominator;
    }
}
