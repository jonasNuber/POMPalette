package org.nuberjonas.pompalette.core.model.domain.project;

import com.brunomnsilva.smartgraph.graphview.SmartShapeTypeSource;

public interface Project {

    @SmartShapeTypeSource
    default String getShape(){
        return "square";
    }

    ProjectCoordinates getCoordinates();
}
