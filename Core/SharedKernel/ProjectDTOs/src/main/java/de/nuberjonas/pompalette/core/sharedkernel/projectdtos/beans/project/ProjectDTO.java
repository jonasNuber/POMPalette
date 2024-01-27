package de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.project;

import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.build.BuildDTO;
import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.contributing.ContributerDTO;
import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.contributing.DeveloperDTO;
import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.contributing.MailingListDTO;
import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.contributing.OrganizationDTO;
import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.dependency.DependencyDTO;
import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.dependency.DependencyManagementDTO;
import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.input.InputLocationDTO;
import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.management.CiManagementDTO;
import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.management.DistributionManagementDTO;
import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.management.IssueManagementDTO;
import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.reporting.ReportingDTO;
import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.repository.RepositoryDTO;

import java.util.List;
import java.util.Map;
import java.util.Properties;

public record ProjectDTO(
        //ModelBase
        List<String> modules,
        //List<ProjectDTO> moduleProjects, // added
        DistributionManagementDTO distributionManagement,
        Properties properties,
        DependencyManagementDTO dependencyManagement,
        List<DependencyDTO> dependencies,
        List<RepositoryDTO> repositories,
        List<RepositoryDTO> pluginRepositories,
        ReportingDTO reporting,
        Map<Object, InputLocationDTO> locations,
        InputLocationDTO location,
        InputLocationDTO modulesLocation,
        InputLocationDTO distributionManagementLocation,
        InputLocationDTO propertiesLocation,
        InputLocationDTO dependencyManagementLocation,
        InputLocationDTO dependenciesLocation,
        InputLocationDTO repositoriesLocation,
        InputLocationDTO pluginRepositoriesLocation,
        InputLocationDTO reportsLocation,
        InputLocationDTO reportingLocation,

        //Model
        String modelVersion,
        ParentDTO parent,
        String groupId,
        String artifactId,
        String version,
        String packaging,
        String name,
        String description,
        String url,
        String childProjectUrlInheritAppendPath,
        String inceptionYear,
        OrganizationDTO organization,
        List<LicenseDTO> licenses,
        List<DeveloperDTO> developers,
        List<ContributerDTO> contributors,
        List<MailingListDTO> mailingLists,
        PrerequisitesDTO prerequisites,
        ScmDTO scm,
        IssueManagementDTO issueManagement,
        CiManagementDTO ciManagement,
        BuildDTO build,
        List<ProfileDTO> profiles,
        String modelEncodings
) {

}
