module org.nuberjonas.pompalette.infrastructure.parsing.projectparsingmavenimpl {
    requires org.nuberjonas.pompalette.infrastructure.parsing.projectparsingapi;
    requires transitive org.nuberjonas.pompalette.core.sharedkernel.projectdtos;
    requires de.nuberjonas.pompalette.mapping.mappingapi;
    requires maven.model;

    exports org.nuberjonas.pompalette.infrastructure.parsing.projectparsingmavenimpl;

    provides org.nuberjonas.pompalette.infrastructure.parsing.projectparsingapi.ProjectParsingService
            with org.nuberjonas.pompalette.infrastructure.parsing.projectparsingmavenimpl.MavenProjectParsingService;
}