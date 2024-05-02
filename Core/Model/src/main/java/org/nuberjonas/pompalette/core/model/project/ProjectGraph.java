package org.nuberjonas.pompalette.core.model.project;

import java.util.HashMap;
import java.util.Map;

public class ProjectGraph {

    private final Map<ProjectCoordinates, MavenProject> vertecies;

    public ProjectGraph(){
        vertecies = new HashMap<>();
    }

    public void addVertex(MavenProject project){
        vertecies.put(project.getCoordinates(), project);
    }

    public void addEdge(MavenProject source, MavenProject destination){
        if(projectsAreInGraph(source.getCoordinates(), destination.getCoordinates())){
            defineEdge(source, destination);
        }
    }

    public void addEdge(ProjectCoordinates source, ProjectCoordinates destination){
        if(projectsAreInGraph(source, destination)){
            defineEdge(vertecies.get(source), vertecies.get(destination));
        }
    }

    private boolean projectsAreInGraph(ProjectCoordinates... coordinates){
        for(var coordinate : coordinates){
            if(!vertecies.containsKey(coordinate)){
                throw new IllegalArgumentException(String.format("The Project with the Coordinate: %s does not exist in the ProjectGraph", coordinate));
            }
        }

        return true;
    }

    private void defineEdge(MavenProject source, MavenProject destination){
        source.addModule(destination);
        destination.setParent(source);
    }
}
