package org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.model;

import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.activation.ActivationDTO;
import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.build.BuildBaseDTO;

import java.io.Serializable;

public record ProfileDTO(
        ModelBaseDTO modelBase,
        String id,
        ActivationDTO activation,
        BuildBaseDTO build
) implements Serializable {
}
