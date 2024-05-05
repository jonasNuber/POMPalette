package org.nuberjonas.pompalette.core.model.domain.project;

import com.brunomnsilva.smartgraph.graphview.SmartShapeTypeSource;

public interface Project {

    @SmartShapeTypeSource
    String getShape();

    ProjectCoordinates getCoordinates();
}
