package org.nuberjonas.pompalette.mapping.projectmapping.dependency;

import org.apache.maven.model.DependencyManagement;
import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.dependency.DependencyManagementDTO;
import org.nuberjonas.pompalette.mapping.mappingapi.mapper.utilities.ListMapper;
import org.nuberjonas.pompalette.mapping.projectmapping.input.InputLocationMapper;

import java.util.Optional;

public class DependencyManagementMapper {

    private DependencyManagementMapper(){
        throw new AssertionError("Utility class, cannot be instantiated");
    }

    public static DependencyManagementDTO mapToDTO(DependencyManagement dependencyManagement){
        return Optional.ofNullable(dependencyManagement)
                .map(src -> new DependencyManagementDTO(
                        ListMapper.mapList(src.getDependencies(), DependencyMapper::mapToDTO),
                        InputLocationMapper.mapToInputLocationDTOMap(src),
                        InputLocationMapper.mapToDTO(src.getLocation("")),
                        InputLocationMapper.mapToDTO(src.getLocation("dependencies"))
                ))
                .orElse(null);
    }

    public static DependencyManagement mapToModel(DependencyManagementDTO dependencyManagementDTO){
        if(dependencyManagementDTO == null){
            return null;
        }

        var dependencyManagement = new DependencyManagement();
        dependencyManagement.setDependencies(ListMapper.mapList(dependencyManagementDTO.dependencies(), DependencyMapper::mapToModel));

        if (dependencyManagementDTO.locations() != null) {
            dependencyManagementDTO.locations().forEach((key, value) ->
                    dependencyManagement.setOtherLocation(key, InputLocationMapper.mapToModel(value))
            );
        }

        dependencyManagement.setLocation("", InputLocationMapper.mapToModel(dependencyManagementDTO.location()));
        dependencyManagement.setLocation("dependencies", InputLocationMapper.mapToModel(dependencyManagementDTO.dependenciesLocation()));

        return dependencyManagement;
    }
}
