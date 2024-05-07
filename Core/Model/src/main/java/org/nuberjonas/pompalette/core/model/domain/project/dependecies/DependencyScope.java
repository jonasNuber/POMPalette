package org.nuberjonas.pompalette.core.model.domain.project.dependecies;

public enum DependencyScope {
    COMPILE,
    PROVIDED,
    RUNTIME,
    TEST,
    SYSTEM,
    IMPORT;

    public static DependencyScope fromString(String value) {
        for (DependencyScope enumValue : DependencyScope.values()) {
            if (enumValue.name().equalsIgnoreCase(value)) {
                return enumValue;
            }
        }
        throw new IllegalArgumentException(String.format("No %s dependencyScope", value));
    }
}
