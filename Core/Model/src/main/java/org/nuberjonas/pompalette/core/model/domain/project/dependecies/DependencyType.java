package org.nuberjonas.pompalette.core.model.domain.project.dependecies;

public enum DependencyType {
    JAR,
    WAR,
    EAR,
    POM,
    ZIP,
    RAR;

    public static DependencyType fromString(String value) {
        for (DependencyType enumValue : DependencyType.values()) {
            if (enumValue.name().equalsIgnoreCase(value)) {
                return enumValue;
            }
        }
        return JAR;
    }
}
