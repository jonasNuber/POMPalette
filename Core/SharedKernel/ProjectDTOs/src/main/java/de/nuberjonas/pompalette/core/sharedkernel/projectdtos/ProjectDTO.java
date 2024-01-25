package de.nuberjonas.pompalette.core.sharedkernel.projectdtos;

import java.util.List;
import java.util.Map;
import java.util.Properties;

public record ProjectDTO(
        //ModelBase
        List<String> moduleNames,
        List<ProjectDTO> modules, // added
        DistributionManagementDTO distributionManagement,
        Properties properties,
        DependencyManagementDTO dependencyManagement,
        List<DependencyDTO> dependencies,
        List<RepositoryDTO> repositories,
        List<RepositoryDTO> pluginRepositories,
        Object reports,
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
        String modelEncodinga
) {

}
