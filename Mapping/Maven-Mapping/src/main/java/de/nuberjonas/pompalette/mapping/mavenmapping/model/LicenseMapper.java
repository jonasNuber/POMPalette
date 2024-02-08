package de.nuberjonas.pompalette.mapping.mavenmapping.model;

import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.model.LicenseDTO;
import de.nuberjonas.pompalette.mapping.mavenmapping.input.InputLocationMapper;
import org.apache.maven.model.License;

import java.util.Optional;

public class LicenseMapper {

    private LicenseMapper(){ throw new AssertionError("Utility class, cannot be instantiated"); }

    public static LicenseDTO mapToDTO(License license){
        return Optional.ofNullable(license)
                .map(src -> new LicenseDTO(
                        src.getName(),
                        src.getUrl(),
                        src.getDistribution(),
                        src.getComments(),
                        InputLocationMapper.mapToInputLocationDTOMap(src),
                        InputLocationMapper.mapToDTO(src.getLocation("")),
                        InputLocationMapper.mapToDTO(src.getLocation("name")),
                        InputLocationMapper.mapToDTO(src.getLocation("url")),
                        InputLocationMapper.mapToDTO(src.getLocation("distribution")),
                        InputLocationMapper.mapToDTO(src.getLocation("comments"))))
                .orElse(null);
    }

    public static License mapToModel(LicenseDTO licenseDTO){
        if(licenseDTO == null){
            return null;
        }

        var license = new License();
        license.setName(licenseDTO.name());
        license.setUrl(licenseDTO.url());
        license.setDistribution(licenseDTO.distribution());
        license.setComments(licenseDTO.comments());

        if (licenseDTO.locations() != null) {
            licenseDTO.locations().forEach((key, value) ->
                    license.setOtherLocation(key, InputLocationMapper.mapToModel(value))
            );
        }

        license.setLocation("", InputLocationMapper.mapToModel(licenseDTO.location()));
        license.setLocation("name", InputLocationMapper.mapToModel(licenseDTO.nameLocation()));
        license.setLocation("url", InputLocationMapper.mapToModel(licenseDTO.urlLocation()));
        license.setLocation("distribution", InputLocationMapper.mapToModel(licenseDTO.distributionLocation()));
        license.setLocation("comments", InputLocationMapper.mapToModel(licenseDTO.commentsLocation()));

        return license;
    }
}
