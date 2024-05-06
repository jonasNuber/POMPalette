package org.nuberjonas.pompalette.core.model.domain.project.dependecies;

import org.nuberjonas.pompalette.core.model.domain.project.ProjectCoordinates;

public class TransitiveDependency implements Dependency{

    private Dependency dependency;

    public TransitiveDependency(Dependency dependency) {
        this.dependency = dependency;
    }

    @Override
    public ProjectCoordinates getCoordinates() {
        return dependency.getCoordinates();
    }
}
