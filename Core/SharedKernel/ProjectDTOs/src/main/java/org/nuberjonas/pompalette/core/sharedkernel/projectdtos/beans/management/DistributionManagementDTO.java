package org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.management;

import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.input.InputLocationDTO;
import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.repository.DeploymentRepositoryDTO;

import java.io.Serializable;
import java.util.Map;

public record DistributionManagementDTO(
        DeploymentRepositoryDTO repository,
        DeploymentRepositoryDTO snapshotRepository,
        SiteDTO site,
        String downloadUrl,
        RelocationDTO relocation,
        String status,
        Map<Object, InputLocationDTO> locations,
        InputLocationDTO location,
        InputLocationDTO repositoryLocation,
        InputLocationDTO snapshotRepositoryLocation,
        InputLocationDTO siteLocation,
        InputLocationDTO downloadUrlLocation,
        InputLocationDTO relocationLocation,
        InputLocationDTO statusLocation
) implements Serializable {
}
