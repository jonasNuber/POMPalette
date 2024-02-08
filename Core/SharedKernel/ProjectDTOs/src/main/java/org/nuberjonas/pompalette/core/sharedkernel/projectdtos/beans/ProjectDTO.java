package org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans;

import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.build.BuildDTO;
import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.contributing.ContributorDTO;
import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.contributing.DeveloperDTO;
import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.contributing.MailingListDTO;
import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.contributing.OrganizationDTO;
import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.management.CiManagementDTO;
import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.management.IssueManagementDTO;
import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.model.*;
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

}
