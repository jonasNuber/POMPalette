package de.nuberjonas.pompalette.mapping.mavenmapping.management;

import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.management.DistributionManagementDTO;
import de.nuberjonas.pompalette.mapping.mavenmapping.input.InputLocationMapper;
import de.nuberjonas.pompalette.mapping.mavenmapping.repository.DeploymentRepositoryMapper;
import org.apache.maven.model.DistributionManagement;

import java.util.Optional;

public class DistributionManagementMapper {

    private DistributionManagementMapper(){ throw new AssertionError("Utility class, cannot be instantiated"); }

    public static DistributionManagementDTO mapToDTO(DistributionManagement distributionManagement){
        return Optional.ofNullable(distributionManagement)
                .map(src -> new DistributionManagementDTO(
                        DeploymentRepositoryMapper.mapToDTO(src.getRepository()),
                        DeploymentRepositoryMapper.mapToDTO(src.getSnapshotRepository()),
                        SiteMapper.mapToDTO(src.getSite()),
                        src.getDownloadUrl(),
                        RelocationMapper.mapToDTO(src.getRelocation()),
                        src.getStatus(),
                        InputLocationMapper.mapToInputLocationDTOMap(src),
                        InputLocationMapper.mapToDTO(src.getLocation("")),
                        InputLocationMapper.mapToDTO(src.getLocation("repository")),
                        InputLocationMapper.mapToDTO(src.getLocation("snapshotRepository")),
                        InputLocationMapper.mapToDTO(src.getLocation("site")),
                        InputLocationMapper.mapToDTO(src.getLocation("downloadUrl")),
                        InputLocationMapper.mapToDTO(src.getLocation("relocation")),
                        InputLocationMapper.mapToDTO(src.getLocation("status"))
                ))
                .orElse(null);
    }

    public static DistributionManagement mapToModel(DistributionManagementDTO distributionManagementDTO){
        if(distributionManagementDTO == null){
            return null;
        }

        var distributionManagement = new DistributionManagement();
        distributionManagement.setRepository(DeploymentRepositoryMapper.mapToModel(distributionManagementDTO.repository()));
        distributionManagement.setSnapshotRepository(DeploymentRepositoryMapper.mapToModel(distributionManagementDTO.snapshotRepository()));
        distributionManagement.setSite(SiteMapper.mapToModel(distributionManagementDTO.site()));
        distributionManagement.setDownloadUrl(distributionManagementDTO.downloadUrl());
        distributionManagement.setRelocation(RelocationMapper.mapToModel(distributionManagementDTO.relocation()));
        distributionManagement.setStatus(distributionManagementDTO.status());

        if (distributionManagementDTO.locations() != null) {
            distributionManagementDTO.locations().forEach((key, value) ->
                    distributionManagement.setOtherLocation(key, InputLocationMapper.mapToModel(value))
            );
        }

        distributionManagement.setLocation("", InputLocationMapper.mapToModel(distributionManagementDTO.location()));
        distributionManagement.setLocation("repository", InputLocationMapper.mapToModel(distributionManagementDTO.repositoryLocation()));
        distributionManagement.setLocation("snapshotRepository", InputLocationMapper.mapToModel(distributionManagementDTO.snapshotRepositoryLocation()));
        distributionManagement.setLocation("site", InputLocationMapper.mapToModel(distributionManagementDTO.siteLocation()));
        distributionManagement.setLocation("downloadUrl", InputLocationMapper.mapToModel(distributionManagementDTO.downloadUrlLocation()));
        distributionManagement.setLocation("relocation", InputLocationMapper.mapToModel(distributionManagementDTO.relocationLocation()));
        distributionManagement.setLocation("status", InputLocationMapper.mapToModel(distributionManagementDTO.statusLocation()));

        return distributionManagement;
    }
}
