package org.nuberjonas.pompalette.core.model.domain.project.dependecies;

import com.brunomnsilva.smartgraph.graphview.SmartShapeTypeSource;
import org.nuberjonas.pompalette.core.model.domain.project.Project;

public interface Dependency extends Project {

    DependencyCoordinates dependencyCoordinates();

    @Override
    @SmartShapeTypeSource
    default String getShape(){
        return "triangle";
    }
}
