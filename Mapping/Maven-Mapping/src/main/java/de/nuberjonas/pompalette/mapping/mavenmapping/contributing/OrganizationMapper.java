package de.nuberjonas.pompalette.mapping.mavenmapping.contributing;

import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.contributing.OrganizationDTO;
import de.nuberjonas.pompalette.mapping.mavenmapping.input.InputLocationMapper;
import org.apache.maven.model.Organization;

import java.util.Optional;

public class OrganizationMapper {

    private OrganizationMapper(){
        throw new AssertionError("Utility class, cannot be instantiated");
    }

    public static OrganizationDTO mapToDTO(Organization organization){
        return Optional.ofNullable(organization)
                .map(src -> new OrganizationDTO(
                        src.getName(),
                        src.getUrl(),
                        InputLocationMapper.mapToInputLocationDTOMap(src),
                        InputLocationMapper.mapToDTO(src.getLocation("")),
                        InputLocationMapper.mapToDTO(src.getLocation("name")),
                        InputLocationMapper.mapToDTO(src.getLocation("url"))))
                .orElse(null);
    }

    public static Organization mapToModel(OrganizationDTO organizationDTO){
        if(organizationDTO == null){
            return null;
        }

        var organization = new Organization();
        organization.setName(organizationDTO.name());
        organization.setUrl(organizationDTO.url());

        if (organizationDTO.locations() != null) {
            organizationDTO.locations().forEach((key, value) ->
                    organization.setOtherLocation(key, InputLocationMapper.mapToModel(value))
            );
        }

        organization.setLocation("", InputLocationMapper.mapToModel(organizationDTO.location()));
        organization.setLocation("name", InputLocationMapper.mapToModel(organizationDTO.nameLocation()));
        organization.setLocation("url", InputLocationMapper.mapToModel(organizationDTO.urlLocation()));

        return organization;
    }
}
