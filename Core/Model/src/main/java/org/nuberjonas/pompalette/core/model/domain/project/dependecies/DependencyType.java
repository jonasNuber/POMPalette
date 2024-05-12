package org.nuberjonas.pompalette.core.model.domain.project.dependecies;

import org.apache.commons.lang3.StringUtils;

public enum DependencyType {
    JAR,
    WAR,
    EAR,
    POM,
    ZIP,
    RAR;

    public static DependencyType fromString(String value) {
        if(StringUtils.isEmpty(value)) {
            return JAR;
        }

        for (DependencyType enumValue : DependencyType.values()) {
            if (enumValue.name().equalsIgnoreCase(value)) {
                return enumValue;
            }
        }
        return JAR;
    }
}
