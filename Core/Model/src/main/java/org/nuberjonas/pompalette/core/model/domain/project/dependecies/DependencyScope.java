package org.nuberjonas.pompalette.core.model.domain.project.dependecies;

import org.apache.commons.lang3.StringUtils;

public enum DependencyScope {
    COMPILE,
    PROVIDED,
    RUNTIME,
    TEST,
    SYSTEM,
    IMPORT;

    public static DependencyScope fromString(String value) {
        if(StringUtils.isEmpty(value)){
            return COMPILE;
        }

        for (DependencyScope enumValue : DependencyScope.values()) {
            if (enumValue.name().equalsIgnoreCase(value)) {
                return enumValue;
            }
        }
        throw new IllegalArgumentException(String.format("No %s dependencyScope", value));
    }
}
