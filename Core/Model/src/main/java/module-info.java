module org.nuberjonas.pompalette.core.model {
    requires smartgraph;

    requires org.nuberjonas.pompalette.mapping.mappingapi;
    requires org.nuberjonas.pompalette.infrastructure.parsing.projectparsingapi;
    requires org.nuberjonas.pompalette.infrastructure.parsing.projectparsingmavenimpl;
    requires org.nuberjonas.pompalette.infrastructure.eventbus;

    requires org.apache.commons.lang3;

    exports org.nuberjonas.pompalette.core.model;
    exports org.nuberjonas.pompalette.core.model.domain.project;
    exports org.nuberjonas.pompalette.core.model.domain.graph;
    exports org.nuberjonas.pompalette.core.model.domain.searchlist;
    exports org.nuberjonas.pompalette.core.model.application;
    exports org.nuberjonas.pompalette.core.model.domain.project.dependecies;
    exports org.nuberjonas.pompalette.core.model.application.mapper;
    exports org.nuberjonas.pompalette.core.model.domain.graph.relationship;
}