module de.nuberjonas.pompalette.infrastructure.parsing.projectparsingmavenimpl {
    requires de.nuberjonas.pompalette.infrastructure.parsing.projectparsingapi;
    requires transitive de.nuberjonas.pompalette.core.sharedkernel.projectdtos;
    requires de.nuberjonas.pompalette.mapping.mappingapi;
    requires maven.core;
    requires maven.model;
    requires plexus.xml;
    requires plexus.utils;

    exports de.nuberjonas.pompalette.infrastructure.parsing.projectparsingmavenimpl;

    provides de.nuberjonas.pompalette.infrastructure.parsing.projectparsingapi.ProjectParsingService
            with de.nuberjonas.pompalette.infrastructure.parsing.projectparsingmavenimpl.MavenProjectParsingService;
}