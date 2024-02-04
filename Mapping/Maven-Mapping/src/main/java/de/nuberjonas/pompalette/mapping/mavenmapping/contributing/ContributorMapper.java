package de.nuberjonas.pompalette.mapping.mavenmapping.contributing;

import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.contributing.ContributorDTO;
import de.nuberjonas.pompalette.mapping.mavenmapping.input.InputLocationMapper;
import org.apache.maven.model.Contributor;

import java.util.Optional;

public class ContributorMapper {

    private ContributorMapper(){
        throw new AssertionError("Utility class, cannot be instantiated");
    }

    public static ContributorDTO mapToDTO(Contributor contributor){
        return Optional.ofNullable(contributor)
                .map(src -> new ContributorDTO(
                        src.getName(),
                        src.getEmail(),
                        src.getUrl(),
                        src.getOrganization(),
                        src.getOrganizationUrl(),
                        src.getRoles(),
                        src.getTimezone(),
                        src.getProperties(),
                        InputLocationMapper.mapToInputLocationDTOMap(src),
                        InputLocationMapper.mapToDTO(src.getLocation("")),
                        InputLocationMapper.mapToDTO(src.getLocation("name")),
                        InputLocationMapper.mapToDTO(src.getLocation("email")),
                        InputLocationMapper.mapToDTO(src.getLocation("url")),
                        InputLocationMapper.mapToDTO(src.getLocation("organization")),
                        InputLocationMapper.mapToDTO(src.getLocation("organizationUrl")),
                        InputLocationMapper.mapToDTO(src.getLocation("roles")),
                        InputLocationMapper.mapToDTO(src.getLocation("timezone")),
                        InputLocationMapper.mapToDTO(src.getLocation("properties"))))
                .orElse(null);
    }

    public static Contributor mapToModel(ContributorDTO contributorDTO){
        if(contributorDTO == null){
            return null;
        }

        var contributor = new Contributor();
        contributor.setName(contributorDTO.name());
        contributor.setEmail(contributorDTO.email());
        contributor.setUrl(contributorDTO.url());
        contributor.setOrganization(contributorDTO.organization());
        contributor.setOrganizationUrl(contributorDTO.organizationUrl());
        contributor.setRoles(contributorDTO.roles());
        contributor.setTimezone(contributorDTO.timezone());
        contributor.setProperties(contributorDTO.properties());

        if (contributorDTO.locations() != null) {
            contributorDTO.locations().forEach((key, value) ->
                    contributor.setOtherLocation(key, InputLocationMapper.mapToModel(value))
            );
        }

        contributor.setLocation("", InputLocationMapper.mapToModel(contributorDTO.location()));
        contributor.setLocation("name", InputLocationMapper.mapToModel(contributorDTO.nameLocation()));
        contributor.setLocation("email", InputLocationMapper.mapToModel(contributorDTO.emailLocation()));
        contributor.setLocation("url", InputLocationMapper.mapToModel(contributorDTO.urlLocation()));
        contributor.setLocation("organization", InputLocationMapper.mapToModel(contributorDTO.organizationLocation()));
        contributor.setLocation("organizationUrl", InputLocationMapper.mapToModel(contributorDTO.organizationUrlLocation()));
        contributor.setLocation("roles", InputLocationMapper.mapToModel(contributorDTO.rolesLocation()));
        contributor.setLocation("timezone", InputLocationMapper.mapToModel(contributorDTO.timezoneLocation()));
        contributor.setLocation("properties", InputLocationMapper.mapToModel(contributorDTO.propertiesLocation()));

        return contributor;
    }
}
