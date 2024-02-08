package de.nuberjonas.pompalette.mapping.mavenmapping.activation;

import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.activation.ActivationDTO;
import de.nuberjonas.pompalette.mapping.mavenmapping.input.InputLocationMapper;
import org.apache.maven.model.Activation;

import java.util.Optional;

public class ActivationMapper {

    private ActivationMapper(){ throw new AssertionError("Utility class, cannot be instantiated"); }

    public static ActivationDTO mapToDTO(Activation activation){
        return Optional.ofNullable(activation)
                .map(src -> new ActivationDTO(
                        src.isActiveByDefault(),
                        src.getJdk(),
                        ActivationOsMapper.mapToDTO(src.getOs()),
                        ActivationPropertyMapper.mapToDTO(src.getProperty()),
                        ActivationFileMapper.mapToDTO(src.getFile()),
                        InputLocationMapper.mapToInputLocationDTOMap(src),
                        InputLocationMapper.mapToDTO(src.getLocation("")),
                        InputLocationMapper.mapToDTO(src.getLocation("activeByDefault")),
                        InputLocationMapper.mapToDTO(src.getLocation("jdk")),
                        InputLocationMapper.mapToDTO(src.getLocation("os")),
                        InputLocationMapper.mapToDTO(src.getLocation("property")),
                        InputLocationMapper.mapToDTO(src.getLocation("file"))
                ))
                .orElse(null);
    }

    public static Activation mapToModel(ActivationDTO activationDTO){
        if(activationDTO == null){
            return null;
        }

        var activation = new Activation();
        activation.setActiveByDefault(activationDTO.activeByDefault());
        activation.setJdk(activationDTO.jdk());
        activation.setOs(ActivationOsMapper.mapToModel(activationDTO.os()));
        activation.setProperty(ActivationPropertyMapper.mapToModel(activationDTO.property()));
        activation.setFile(ActivationFileMapper.mapToModel(activationDTO.file()));

        if (activationDTO.locations() != null) {
            activationDTO.locations().forEach((key, value) ->
                    activation.setOtherLocation(key, InputLocationMapper.mapToModel(value))
            );
        }

        activation.setLocation("", InputLocationMapper.mapToModel(activationDTO.location()));
        activation.setLocation("activeByDefault", InputLocationMapper.mapToModel(activationDTO.activeByDefaultLocation()));
        activation.setLocation("jdk", InputLocationMapper.mapToModel(activationDTO.jdkLocation()));
        activation.setLocation("os", InputLocationMapper.mapToModel(activationDTO.osLocation()));
        activation.setLocation("property", InputLocationMapper.mapToModel(activationDTO.propertyLocation()));
        activation.setLocation("file", InputLocationMapper.mapToModel(activationDTO.fileLocation()));

        return activation;
    }
}
