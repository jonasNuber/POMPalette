module org.nuberjonas.pompalette.infrastructure.parsing.projectparsingmavenimpl {
    requires org.nuberjonas.pompalette.infrastructure.parsing.projectparsingapi;
    requires transitive org.nuberjonas.pompalette.core.sharedkernel.projectdtos;
    requires org.nuberjonas.pompalette.mapping.mappingapi;
    requires org.nuberjonas.pompalette.infrastructure.serviceloading;

    requires maven.model;
    requires plexus.xml;

    exports org.nuberjonas.pompalette.infrastructure.parsing.projectparsingmavenimpl;

    uses org.nuberjonas.pompalette.infrastructure.parsing.projectparsingapi.ProjectParsingService;
    provides org.nuberjonas.pompalette.infrastructure.parsing.projectparsingapi.ProjectParsingService
            with org.nuberjonas.pompalette.infrastructure.parsing.projectparsingmavenimpl.MavenProjectParsingService;
}