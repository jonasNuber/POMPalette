package org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.build;

import java.io.Serializable;
import java.util.List;

public record BuildDTO(
        BuildBaseDTO buildBase,
        String sourceDirectory,
        String scriptSourceDirectory,
        String testSourceDirectory,
        String outputDirectory,
        String testOutputDirectory,
        List<ExtensionDTO> extensions
) implements Serializable {
}
