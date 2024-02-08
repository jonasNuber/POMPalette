package de.nuberjonas.pompalette.mapping.mavenmapping.contributing;

import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.contributing.DeveloperDTO;
import de.nuberjonas.pompalette.mapping.mappingapi.mapper.SuperClassMapper;
import org.apache.maven.model.Developer;

import java.util.Optional;

public class DeveloperMapper {

    private DeveloperMapper(){
        throw new AssertionError("Utility class, cannot be instantiated");
    }

    public static DeveloperDTO mapToDTO(Developer developer){
        return Optional.ofNullable(developer)
                .map(src -> new DeveloperDTO(
                        ContributorMapper.mapToDTO(src),
                        src.getId()
                ))
                .orElse(null);
    }

    public static Developer mapToModel(DeveloperDTO developerDTO){
        if(developerDTO == null){
            return null;
        }

        var developer = new Developer();

        if(developerDTO.contributor() != null){
            developer = SuperClassMapper.mapFields(ContributorMapper.mapToModel(developerDTO.contributor()), Developer.class);
        }

        developer.setId(developerDTO.id());

        return developer;
    }
}
