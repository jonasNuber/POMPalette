package org.nuberjonas.pompalette.core.model.domain.graph;

public enum EdgeType {
    MODULE("Module"),
    BOM("BOM"),
    DEPENDENCY("Dependency");

    private final String denominator;

    EdgeType(String name) {
        this.denominator = name;
    }

    public String denominator(){
        return denominator;
    }

    @Override
    public String toString() {
        return denominator;
    }
}
