package org.nuberjonas.pompalette.mapping.projectmapping.activation;

import org.apache.maven.model.ActivationOS;
import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.activation.ActivationOsDTO;
import org.nuberjonas.pompalette.mapping.projectmapping.input.InputLocationMapper;

import java.util.Optional;

public class ActivationOsMapper {

    private ActivationOsMapper(){ throw new AssertionError("Utility class, cannot be instantiated"); }

    public static ActivationOsDTO mapToDTO(ActivationOS activationOS){
        return Optional.ofNullable(activationOS)
                .map(src -> new ActivationOsDTO(
                        src.getName(),
                        src.getFamily(),
                        src.getArch(),
                        src.getVersion(),
                        InputLocationMapper.mapToInputLocationDTOMap(src),
                        InputLocationMapper.mapToDTO(src.getLocation("")),
                        InputLocationMapper.mapToDTO(src.getLocation("name")),
                        InputLocationMapper.mapToDTO(src.getLocation("family")),
                        InputLocationMapper.mapToDTO(src.getLocation("arch")),
                        InputLocationMapper.mapToDTO(src.getLocation("version"))
                ))
                .orElse(null);
    }

    public static ActivationOS mapToModel(ActivationOsDTO activationOsDTO){
        if(activationOsDTO == null){
            return null;
        }

        var activationOS = new ActivationOS();
        activationOS.setName(activationOsDTO.name());
        activationOS.setFamily(activationOsDTO.family());
        activationOS.setArch(activationOsDTO.arch());
        activationOS.setVersion(activationOsDTO.version());

        if (activationOsDTO.locations() != null) {
            activationOsDTO.locations().forEach((key, value) ->
                    activationOS.setOtherLocation(key, InputLocationMapper.mapToModel(value))
            );
        }

        activationOS.setLocation("", InputLocationMapper.mapToModel(activationOsDTO.location()));
        activationOS.setLocation("name", InputLocationMapper.mapToModel(activationOsDTO.nameLocation()));
        activationOS.setLocation("family", InputLocationMapper.mapToModel(activationOsDTO.familyLocation()));
        activationOS.setLocation("arch", InputLocationMapper.mapToModel(activationOsDTO.archLocation()));
        activationOS.setLocation("version", InputLocationMapper.mapToModel(activationOsDTO.versionLocation()));

        return activationOS;
    }
}
