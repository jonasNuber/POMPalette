package de.nuberjonas.pompalette.mapping.mavenmapping.model;

import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.model.ModelBaseDTO;
import de.nuberjonas.pompalette.mapping.mappingapi.mapper.ListMapper;
import de.nuberjonas.pompalette.mapping.mavenmapping.dependency.DependencyManagementMapper;
import de.nuberjonas.pompalette.mapping.mavenmapping.dependency.DependencyMapper;
import de.nuberjonas.pompalette.mapping.mavenmapping.input.InputLocationMapper;
import de.nuberjonas.pompalette.mapping.mavenmapping.management.DistributionManagementMapper;
import de.nuberjonas.pompalette.mapping.mavenmapping.reporting.ReportingMapper;
import de.nuberjonas.pompalette.mapping.mavenmapping.repository.RepositoryMapper;
import org.apache.maven.model.ModelBase;

import java.util.Optional;

public class ModelBaseMapper {

    private ModelBaseMapper(){ throw new AssertionError("Utility class, cannot be instantiated"); }

    public static ModelBaseDTO mapToDTO(ModelBase modelBase){
        return Optional.ofNullable(modelBase)
                .map(src -> new ModelBaseDTO(
                        src.getModules(),
                        DistributionManagementMapper.mapToDTO(src.getDistributionManagement()),
                        src.getProperties(),
                        DependencyManagementMapper.mapToDTO(src.getDependencyManagement()),
                        ListMapper.mapList(src.getDependencies(), DependencyMapper::mapToDTO),
                        ListMapper.mapList(src.getRepositories(), RepositoryMapper::mapToDTO),
                        ListMapper.mapList(src.getPluginRepositories(), RepositoryMapper::mapToDTO),
                        ReportingMapper.mapToDTO(src.getReporting()),
                        InputLocationMapper.mapToInputLocationDTOMap(src),
                        InputLocationMapper.mapToDTO(src.getLocation("")),
                        InputLocationMapper.mapToDTO(src.getLocation("modules")),
                        InputLocationMapper.mapToDTO(src.getLocation("distributionManagement")),
                        InputLocationMapper.mapToDTO(src.getLocation("properties")),
                        InputLocationMapper.mapToDTO(src.getLocation("dependencyManagement")),
                        InputLocationMapper.mapToDTO(src.getLocation("dependencies")),
                        InputLocationMapper.mapToDTO(src.getLocation("repositories")),
                        InputLocationMapper.mapToDTO(src.getLocation("pluginRepositories")),
                        InputLocationMapper.mapToDTO(src.getLocation("reporting"))))
                .orElse(null);
    }

    public static ModelBase mapToModel(ModelBaseDTO modelBaseDTO){
        if(modelBaseDTO == null){
            return null;
        }

        var modelBase = new ModelBase();
        modelBase.setModules(modelBaseDTO.modules());
        modelBase.setDistributionManagement(DistributionManagementMapper.mapToModel(modelBaseDTO.distributionManagement()));
        modelBase.setProperties(modelBaseDTO.properties());
        modelBase.setDependencyManagement(DependencyManagementMapper.mapToModel(modelBaseDTO.dependencyManagement()));
        modelBase.setDependencies(ListMapper.mapList(modelBaseDTO.dependencies(), DependencyMapper::mapToModel));
        modelBase.setRepositories(ListMapper.mapList(modelBaseDTO.repositories(), RepositoryMapper::mapToModel));
        modelBase.setPluginRepositories(ListMapper.mapList(modelBaseDTO.pluginRepositories(), RepositoryMapper::mapToModel));
        modelBase.setReporting(ReportingMapper.mapToModel(modelBaseDTO.reporting()));

        if (modelBaseDTO.locations() != null) {
            modelBaseDTO.locations().forEach((key, value) ->
                    modelBase.setOtherLocation(key, InputLocationMapper.mapToModel(value))
            );
        }

        modelBase.setLocation("", InputLocationMapper.mapToModel(modelBaseDTO.location()));
        modelBase.setLocation("modules", InputLocationMapper.mapToModel(modelBaseDTO.modulesLocation()));
        modelBase.setLocation("distributionManagement", InputLocationMapper.mapToModel(modelBaseDTO.distributionManagementLocation()));
        modelBase.setLocation("properties", InputLocationMapper.mapToModel(modelBaseDTO.propertiesLocation()));
        modelBase.setLocation("dependencyManagement", InputLocationMapper.mapToModel(modelBaseDTO.dependencyManagementLocation()));
        modelBase.setLocation("dependencies", InputLocationMapper.mapToModel(modelBaseDTO.dependenciesLocation()));
        modelBase.setLocation("repositories", InputLocationMapper.mapToModel(modelBaseDTO.repositoriesLocation()));
        modelBase.setLocation("pluginRepositories", InputLocationMapper.mapToModel(modelBaseDTO.pluginRepositoriesLocation()));
        modelBase.setLocation("reporting", InputLocationMapper.mapToModel(modelBaseDTO.reportingLocation()));

        return modelBase;
    }
}
