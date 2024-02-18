package org.nuberjonas.pompalette.mapping.projectmapping.activation;

import org.apache.maven.model.ActivationFile;
import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.activation.ActivationFileDTO;
import org.nuberjonas.pompalette.mapping.projectmapping.input.InputLocationMapper;

import java.util.Optional;

public class ActivationFileMapper {

    private ActivationFileMapper(){ throw new AssertionError("Utility class, cannot be instantiated"); }

    public static ActivationFileDTO mapToDTO(ActivationFile activationFile){
        return Optional.ofNullable(activationFile)
                .map(src -> new ActivationFileDTO(
                        src.getMissing(),
                        src.getExists(),
                        InputLocationMapper.mapToInputLocationDTOMap(src),
                        InputLocationMapper.mapToDTO(src.getLocation("")),
                        InputLocationMapper.mapToDTO(src.getLocation("missing")),
                        InputLocationMapper.mapToDTO(src.getLocation("exists"))
                ))
                .orElse(null);
    }

    public static ActivationFile mapToModel(ActivationFileDTO activationFileDTO){
        if (activationFileDTO == null){
            return null;
        }

        var activationFile = new ActivationFile();
        activationFile.setMissing(activationFileDTO.missing());
        activationFile.setExists(activationFileDTO.exists());

        if (activationFileDTO.locations() != null) {
            activationFileDTO.locations().forEach((key, value) ->
                    activationFile.setOtherLocation(key, InputLocationMapper.mapToModel(value))
            );
        }

        activationFile.setLocation("", InputLocationMapper.mapToModel(activationFileDTO.location()));
        activationFile.setLocation("missing", InputLocationMapper.mapToModel(activationFileDTO.missingLocation()));
        activationFile.setLocation("exists", InputLocationMapper.mapToModel(activationFileDTO.existsLocation()));

        return activationFile;
    }
}
