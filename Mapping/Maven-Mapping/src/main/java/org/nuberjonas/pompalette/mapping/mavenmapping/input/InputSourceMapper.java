package org.nuberjonas.pompalette.mapping.mavenmapping.input;

import org.apache.maven.model.InputSource;
import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.input.InputSourceDTO;

import java.util.Optional;

public class InputSourceMapper{

    private InputSourceMapper(){
        throw new AssertionError("Utility class, cannot be instantiated");
    }

    public static InputSourceDTO mapToDTO(InputSource inputSource) {
        return Optional.ofNullable(inputSource)
                .map(src -> new InputSourceDTO(
                        src.getModelId(),
                        src.getLocation()))
                .orElse(null);
    }

    public static InputSource mapToModel(InputSourceDTO inputSourceDTO) {
        if (inputSourceDTO == null){
            return null;
        }

        var inputSource = new InputSource();
        inputSource.setModelId(inputSourceDTO.modelId());
        inputSource.setLocation(inputSourceDTO.location());

        return inputSource;
    }
}
