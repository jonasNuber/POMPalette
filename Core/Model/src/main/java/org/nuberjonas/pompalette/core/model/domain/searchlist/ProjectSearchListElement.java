package org.nuberjonas.pompalette.core.model.domain.searchlist;

import com.brunomnsilva.smartgraph.graph.Vertex;
import org.nuberjonas.pompalette.core.model.domain.graph.relationship.DependencyRelationship;
import org.nuberjonas.pompalette.core.model.domain.project.Project;

import java.util.List;
import java.util.Objects;

public record ProjectSearchListElement(Vertex<Project> projectVertex, List<DependencyRelationship> relationships) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectSearchListElement that = (ProjectSearchListElement) o;
        return Objects.equals(projectVertex, that.projectVertex) && Objects.equals(relationships, that.relationships);
    }

    @Override
    public int hashCode() {
        return Objects.hash(projectVertex, relationships);
    }
}
