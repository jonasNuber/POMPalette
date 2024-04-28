package org.nuberjonas.pompalette.mapping.projectmapping.activation;

import org.apache.maven.model.ActivationProperty;
import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.activation.ActivationPropertyDTO;
import org.nuberjonas.pompalette.mapping.projectmapping.input.InputLocationMapper;

import java.util.Optional;

public class ActivationPropertyMapper {

    private ActivationPropertyMapper(){ throw new AssertionError("Utility class, cannot be instantiated"); }

    public static ActivationPropertyDTO mapToDTO(ActivationProperty activationProperty){
        return Optional.ofNullable(activationProperty)
                .map(src -> new ActivationPropertyDTO(
                        src.getName(),
                        src.getValue(),
                        InputLocationMapper.mapToInputLocationDTOMap(src),
                        InputLocationMapper.mapToDTO(src.getLocation("")),
                        InputLocationMapper.mapToDTO(src.getLocation("name")),
                        InputLocationMapper.mapToDTO(src.getLocation("value"))
                ))
                .orElse(null);
    }

    public static ActivationProperty mapToModel(ActivationPropertyDTO activationPropertyDTO){
        if(activationPropertyDTO == null){
            return null;
        }

        var activationProperty = new ActivationProperty();
        activationProperty.setName(activationPropertyDTO.name());
        activationProperty.setValue(activationPropertyDTO.value());

        if (activationPropertyDTO.locations() != null) {
            activationPropertyDTO.locations().forEach((key, value) ->
                    activationProperty.setOtherLocation(key, InputLocationMapper.mapToModel(value))
            );
        }

        activationProperty.setLocation("", InputLocationMapper.mapToModel(activationPropertyDTO.location()));
        activationProperty.setLocation("name", InputLocationMapper.mapToModel(activationPropertyDTO.nameLocation()));
        activationProperty.setLocation("value", InputLocationMapper.mapToModel(activationPropertyDTO.valueLocation()));

        return activationProperty;
    }
}
