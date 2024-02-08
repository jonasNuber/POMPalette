package org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.repository;

import java.io.Serializable;

public record DeploymentRepositoryDTO(
        RepositoryDTO repository,
        boolean uniqueVersion
) implements Serializable {
}
