package de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.build.resource;

import java.io.Serializable;

public record FileSetDTO(
        PatternSetDTO patternSet,
        String directory
) implements Serializable {
}
