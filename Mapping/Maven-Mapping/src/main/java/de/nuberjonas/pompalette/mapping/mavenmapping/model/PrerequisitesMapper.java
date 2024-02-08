package de.nuberjonas.pompalette.mapping.mavenmapping.model;

import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.model.PrerequisitesDTO;
import de.nuberjonas.pompalette.mapping.mavenmapping.input.InputLocationMapper;
import org.apache.maven.model.Prerequisites;

import java.util.Optional;

public class PrerequisitesMapper {

    private PrerequisitesMapper(){ throw new AssertionError("Utility class, cannot be instantiated"); }

    public static PrerequisitesDTO mapToDTO(Prerequisites prerequisites){
        return Optional.ofNullable(prerequisites)
                .map(src -> new PrerequisitesDTO(
                        src.getMaven(),
                        InputLocationMapper.mapToInputLocationDTOMap(src),
                        InputLocationMapper.mapToDTO(src.getLocation("")),
                        InputLocationMapper.mapToDTO(src.getLocation("maven"))))
                .orElse(null);
    }

    public static Prerequisites mapToModel(PrerequisitesDTO prerequisitesDTO){
        if(prerequisitesDTO == null){
            return null;
        }

        var prerequisites = new Prerequisites();
        prerequisites.setMaven(prerequisitesDTO.maven());

        if (prerequisitesDTO.locations() != null) {
            prerequisitesDTO.locations().forEach((key, value) ->
                    prerequisites.setOtherLocation(key, InputLocationMapper.mapToModel(value))
            );
        }

        prerequisites.setLocation("", InputLocationMapper.mapToModel(prerequisitesDTO.location()));
        prerequisites.setLocation("maven", InputLocationMapper.mapToModel(prerequisitesDTO.mavenLocation()));

        return prerequisites;
    }
}
