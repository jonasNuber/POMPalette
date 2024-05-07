package org.nuberjonas.pompalette.core.model.application;

import org.nuberjonas.pompalette.core.model.application.mapper.ProjectGraphMapper;
import org.nuberjonas.pompalette.core.model.domain.graph.ProjectGraph;
import org.nuberjonas.pompalette.infrastructure.eventbus.EventBus;
import org.nuberjonas.pompalette.infrastructure.eventbus.events.NotificationEvent;
import org.nuberjonas.pompalette.infrastructure.parsing.projectparsingapi.ProjectParsingService;
import org.nuberjonas.pompalette.infrastructure.parsing.projectparsingapi.exceptions.NotAProjectException;
import org.nuberjonas.pompalette.infrastructure.parsing.projectparsingapi.exceptions.ProjectParsingException;
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
        try {
            return mapper.mapToGraph(parsingService.loadMultiModuleProject(projectPath), graph);
        } catch(NotAProjectException e) {
            EventBus.getInstance().publish(NotificationEvent.error("Not a maven project!", e));
        } catch (ProjectParsingException e){
            EventBus.getInstance().publish(NotificationEvent.error("Project could not be loaded!", e));

        }
        return null;
    }
}
