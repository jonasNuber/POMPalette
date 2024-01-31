package de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.input;

import java.io.Serializable;

public record InputSourceDTO(
        String modelId,
        String location
) implements Serializable {
}
