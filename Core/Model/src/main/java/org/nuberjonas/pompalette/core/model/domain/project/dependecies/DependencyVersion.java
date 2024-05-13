package org.nuberjonas.pompalette.core.model.domain.project.dependecies;

import java.util.Objects;

public record DependencyVersion(String version) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DependencyVersion that = (DependencyVersion) o;
        return Objects.equals(version, that.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(version);
    }

    @Override
    public String toString() {
        return version;
    }
}
