package org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.repository;

import java.io.Serializable;

public record RepositoryDTO(
        RepositoryBaseDTO repositoryBase,
        RepositoryPolicyDTO releases,
        RepositoryPolicyDTO snapshots
) implements Serializable {
}
