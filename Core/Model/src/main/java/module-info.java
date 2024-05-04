module org.nuberjonas.pompalette.core.model {
    requires smartgraph;
    requires org.nuberjonas.pompalette.infrastructure.parsing.projectparsingmavenimpl;
    //requires org.nuberjonas.pompalette.mapping.projectgraph_mapping;

    exports org.nuberjonas.pompalette.core.model;
    exports org.nuberjonas.pompalette.core.model.project;
    exports org.nuberjonas.pompalette.core.model.graph;
}