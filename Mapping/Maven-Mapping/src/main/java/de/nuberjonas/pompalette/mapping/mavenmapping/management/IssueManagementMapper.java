package de.nuberjonas.pompalette.mapping.mavenmapping.management;

import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.management.IssueManagementDTO;
import de.nuberjonas.pompalette.mapping.mavenmapping.input.InputLocationMapper;
import org.apache.maven.model.IssueManagement;

import java.util.Optional;

public class IssueManagementMapper {

    private IssueManagementMapper(){ throw new AssertionError("Utility class, cannot be instantiated"); }

    public static IssueManagementDTO mapToDTO(IssueManagement issueManagement){
        return Optional.ofNullable(issueManagement)
                .map(src -> new IssueManagementDTO(
                        src.getSystem(),
                        src.getUrl(),
                        InputLocationMapper.mapToInputLocationDTOMap(src),
                        InputLocationMapper.mapToDTO(src.getLocation("")),
                        InputLocationMapper.mapToDTO(src.getLocation("system")),
                        InputLocationMapper.mapToDTO(src.getLocation("url"))))
                .orElse(null);
    }

     public static IssueManagement mapToModel(IssueManagementDTO issueManagementDTO){
        if(issueManagementDTO == null){
            return null;
        }

        var issueManagement = new IssueManagement();
        issueManagement.setSystem(issueManagementDTO.system());
        issueManagement.setUrl(issueManagementDTO.url());

        if (issueManagementDTO.locations() != null) {
            issueManagementDTO.locations().forEach((key, value) ->
                    issueManagement.setOtherLocation(key, InputLocationMapper.mapToModel(value))
            );
        }

        issueManagement.setLocation("", InputLocationMapper.mapToModel(issueManagementDTO.location()));
        issueManagement.setLocation("system", InputLocationMapper.mapToModel(issueManagementDTO.systemLocation()));
        issueManagement.setLocation("url", InputLocationMapper.mapToModel(issueManagementDTO.urlLocation()));

        return issueManagement;
    }
}
