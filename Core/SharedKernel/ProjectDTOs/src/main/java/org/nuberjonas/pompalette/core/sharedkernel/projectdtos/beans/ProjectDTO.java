package org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans;

import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.build.BuildDTO;
import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.contributing.ContributorDTO;
import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.contributing.DeveloperDTO;
import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.contributing.MailingListDTO;
import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.contributing.OrganizationDTO;
import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.management.CiManagementDTO;
import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.management.IssueManagementDTO;
import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.model.*;

import java.io.Serializable;
import java.util.List;

public record ProjectDTO(
        ModelBaseDTO modelBase,
        String modelVersion,
        ParentDTO parent,
        String groupId,
        String artifactId,
        String version,
        String packaging,
        String name,
        String description,
        String url,
        boolean childProjectUrlInheritAppendPath,
        String inceptionYear,
        OrganizationDTO organization,
        List<LicenseDTO> licenses,
        List<DeveloperDTO> developers,
        List<ContributorDTO> contributors,
        List<MailingListDTO> mailingLists,
        PrerequisitesDTO prerequisites,
        ScmDTO scm,
        IssueManagementDTO issueManagement,
        CiManagementDTO ciManagement,
        BuildDTO build,
        List<ProfileDTO> profiles,
        String modelEncodings
) implements Serializable {

    public ProjectDTO setParentCoordinatesIfEmpty(ParentCoordinates parentCoordinates){
        if(this.groupId == null){
            return new ProjectDTO(
                    this.modelBase,
                    this.modelVersion,
                    this.parent,
                    parentCoordinates.groupId(),
                    this.artifactId,
                    parentCoordinates.version(),
                    this.packaging,
                    this.name,
                    this.description,
                    this.url,
                    this.childProjectUrlInheritAppendPath,
                    this.inceptionYear,
                    this.organization,
                    this.licenses,
                    this.developers,
                    this.contributors,
                    this.mailingLists,
                    this.prerequisites,
                    this.scm,
                    this.issueManagement,
                    this.ciManagement,
                    this.build,
                    this.profiles,
                    this.modelEncodings
            );
        } else {
            return this;
        }
    }
}
