import org.nuberjonas.pompalette.mapping.mappingapi.mapper.MapperService;

module org.nuberjonas.pompalette.mapping.projectmapping {
    requires org.nuberjonas.pompalette.mapping.mappingapi;
    requires org.nuberjonas.pompalette.core.sharedkernel.projectdtos;
    requires maven.model;

    provides MapperService
            with org.nuberjonas.pompalette.mapping.projectmapping.ProjectMapperService;
}