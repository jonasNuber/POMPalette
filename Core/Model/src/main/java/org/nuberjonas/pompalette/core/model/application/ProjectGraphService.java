package org.nuberjonas.pompalette.core.model.application;

import org.nuberjonas.pompalette.core.model.domain.graph.ProjectGraph;
import org.nuberjonas.pompalette.infrastructure.parsing.projectparsingapi.ProjectParsingService;
import org.nuberjonas.pompalette.infrastructure.parsing.projectparsingmavenimpl.MavenProjectParsingService;

import java.nio.file.Path;

public class ProjectGraphService {

    private ProjectParsingService parsingService;
    private ProjectGraphMapper mapper;

    public ProjectGraphService(ProjectParsingService parsingService) {
        this.parsingService = parsingService;
        this.mapper = new ProjectGraphMapper();
    }

    public ProjectGraphService(){
        this(new MavenProjectParsingService());
    }

    public ProjectGraph loadProject(Path projectPath, ProjectGraph graph){
        return mapper.mapToGraph(parsingService.loadMultiModuleProject(projectPath), graph);
    }
}
