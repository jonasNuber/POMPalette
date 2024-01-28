module de.nuberjonas.pompalette.infrastructure.parsing.projectparsingmavenimpl {
    requires de.nuberjonas.pompalette.infrastructure.parsing.projectparsingapi;
    requires transitive de.nuberjonas.pompalette.core.sharedkernel.projectdtos;
    requires maven.core;
    requires maven.model;
    requires plexus.xml;
    requires plexus.utils;

    exports de.nuberjonas.pompalette.infrastructure.parsing.projectparsingmavenimpl;

    provides de.nuberjonas.pompalette.infrastructure.parsing.projectparsingapi.ProjectParsingService
            with de.nuberjonas.pompalette.infrastructure.parsing.projectparsingmavenimpl.MavenProjectParsingService;
    provides de.nuberjonas.pompalette.core.sharedkernel.projectdtos.factory.ProjectFactory
            with de.nuberjonas.pompalette.infrastructure.parsing.projectparsingmavenimpl.factory.MavenProjectFactory;
}