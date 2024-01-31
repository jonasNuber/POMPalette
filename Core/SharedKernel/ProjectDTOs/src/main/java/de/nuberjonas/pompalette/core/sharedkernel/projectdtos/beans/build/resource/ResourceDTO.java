package de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.build.resource;

import java.io.Serializable;

public record ResourceDTO(
        FileSetDTO fileSet,
        String targetPath,
        boolean filtering,
        String mergeId
) implements Serializable {
}
