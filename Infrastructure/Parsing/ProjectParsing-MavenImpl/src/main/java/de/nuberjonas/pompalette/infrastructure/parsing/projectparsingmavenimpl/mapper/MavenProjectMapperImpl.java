package de.nuberjonas.pompalette.infrastructure.parsing.projectparsingmavenimpl.mapper;

import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.activation.ActivationDTO;
import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.activation.ActivationFileDTO;
import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.activation.ActivationOS_DTO;
import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.activation.ActivationPropertyDTO;
import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.build.BuildBaseDTO;
import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.build.BuildDTO;
import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.build.ResourceDTO;
import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.contributing.ContributerDTO;
import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.contributing.DeveloperDTO;
import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.contributing.MailingListDTO;
import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.contributing.NotifierDTO;
import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.contributing.OrganizationDTO;
import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.dependency.DependencyDTO;
import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.dependency.DependencyManagementDTO;
import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.dependency.ExclusionDTO;
import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.input.InputLocationDTO;
import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.management.CiManagementDTO;
import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.management.DistributionManagementDTO;
import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.management.IssueManagementDTO;
import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.management.RelocationDTO;
import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.management.SiteDTO;
import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.plugin.PluginDTO;
import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.plugin.PluginExecutionDTO;
import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.plugin.PluginManagementDTO;
import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.project.ExtensionDTO;
import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.project.LicenseDTO;
import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.project.ParentDTO;
import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.project.PrerequisitesDTO;
import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.project.ProfileDTO;
import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.project.ProjectDTO;
import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.project.ScmDTO;
import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.reporting.ReportPluginDTO;
import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.reporting.ReportSetDTO;
import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.reporting.ReportingDTO;
import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.repository.DeploymentRepositoryDTO;
import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.repository.RepositoryDTO;
import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.repository.RepositoryPolicyDTO;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.apache.maven.model.Activation;
import org.apache.maven.model.ActivationFile;
import org.apache.maven.model.ActivationOS;
import org.apache.maven.model.ActivationProperty;
import org.apache.maven.model.Build;
import org.apache.maven.model.BuildBase;
import org.apache.maven.model.CiManagement;
import org.apache.maven.model.Contributor;
import org.apache.maven.model.Dependency;
import org.apache.maven.model.DependencyManagement;
import org.apache.maven.model.DeploymentRepository;
import org.apache.maven.model.Developer;
import org.apache.maven.model.DistributionManagement;
import org.apache.maven.model.Exclusion;
import org.apache.maven.model.Extension;
import org.apache.maven.model.IssueManagement;
import org.apache.maven.model.License;
import org.apache.maven.model.MailingList;
import org.apache.maven.model.Model;
import org.apache.maven.model.Notifier;
import org.apache.maven.model.Organization;
import org.apache.maven.model.Parent;
import org.apache.maven.model.Plugin;
import org.apache.maven.model.PluginExecution;
import org.apache.maven.model.PluginManagement;
import org.apache.maven.model.Prerequisites;
import org.apache.maven.model.Profile;
import org.apache.maven.model.Relocation;
import org.apache.maven.model.ReportPlugin;
import org.apache.maven.model.ReportSet;
import org.apache.maven.model.Reporting;
import org.apache.maven.model.Repository;
import org.apache.maven.model.RepositoryPolicy;
import org.apache.maven.model.Resource;
import org.apache.maven.model.Scm;
import org.apache.maven.model.Site;

/*
@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-01-26T18:43:52+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21 (Oracle Corporation)"
)
*/
public class MavenProjectMapperImpl implements MavenProjectMapper {

    @Override
    public ProjectDTO mapToDto(Model model) {
        if ( model == null ) {
            return null;
        }

        List<String> modules = null;
        DistributionManagementDTO distributionManagement = null;
        Properties properties = null;
        DependencyManagementDTO dependencyManagement = null;
        List<DependencyDTO> dependencies = null;
        List<RepositoryDTO> repositories = null;
        List<RepositoryDTO> pluginRepositories = null;
        ReportingDTO reporting = null;
        String modelVersion = null;
        ParentDTO parent = null;
        String groupId = null;
        String artifactId = null;
        String version = null;
        String packaging = null;
        String name = null;
        String description = null;
        String url = null;
        String childProjectUrlInheritAppendPath = null;
        String inceptionYear = null;
        OrganizationDTO organization = null;
        List<LicenseDTO> licenses = null;
        List<DeveloperDTO> developers = null;
        List<ContributerDTO> contributors = null;
        List<MailingListDTO> mailingLists = null;
        PrerequisitesDTO prerequisites = null;
        ScmDTO scm = null;
        IssueManagementDTO issueManagement = null;
        CiManagementDTO ciManagement = null;
        BuildDTO build = null;
        List<ProfileDTO> profiles = null;

        List<String> list = model.getModules();
        if ( list != null ) {
            modules = new ArrayList<String>( list );
        }
        distributionManagement = distributionManagementToDistributionManagementDTO( model.getDistributionManagement() );
        Properties properties1 = model.getProperties();
        if ( properties1 != null ) {
            properties = new Properties( properties1 );
        }
        dependencyManagement = dependencyManagementToDependencyManagementDTO( model.getDependencyManagement() );
        dependencies = dependencyListToDependencyDTOList( model.getDependencies() );
        repositories = repositoryListToRepositoryDTOList( model.getRepositories() );
        pluginRepositories = repositoryListToRepositoryDTOList( model.getPluginRepositories() );
        reporting = reportingToReportingDTO( model.getReporting() );
        modelVersion = model.getModelVersion();
        parent = parentToParentDTO( model.getParent() );
        groupId = model.getGroupId();
        artifactId = model.getArtifactId();
        version = model.getVersion();
        packaging = model.getPackaging();
        name = model.getName();
        description = model.getDescription();
        url = model.getUrl();
        childProjectUrlInheritAppendPath = model.getChildProjectUrlInheritAppendPath();
        inceptionYear = model.getInceptionYear();
        organization = organizationToOrganizationDTO( model.getOrganization() );
        licenses = licenseListToLicenseDTOList( model.getLicenses() );
        developers = developerListToDeveloperDTOList( model.getDevelopers() );
        contributors = contributorListToContributerDTOList( model.getContributors() );
        mailingLists = mailingListListToMailingListDTOList( model.getMailingLists() );
        prerequisites = prerequisitesToPrerequisitesDTO( model.getPrerequisites() );
        scm = scmToScmDTO( model.getScm() );
        issueManagement = issueManagementToIssueManagementDTO( model.getIssueManagement() );
        ciManagement = ciManagementToCiManagementDTO( model.getCiManagement() );
        build = buildToBuildDTO( model.getBuild() );
        profiles = profileListToProfileDTOList( model.getProfiles() );

        Map<Object, InputLocationDTO> locations = null;
        InputLocationDTO location = null;
        InputLocationDTO modulesLocation = null;
        InputLocationDTO distributionManagementLocation = null;
        InputLocationDTO propertiesLocation = null;
        InputLocationDTO dependencyManagementLocation = null;
        InputLocationDTO dependenciesLocation = null;
        InputLocationDTO repositoriesLocation = null;
        InputLocationDTO pluginRepositoriesLocation = null;
        InputLocationDTO reportsLocation = null;
        InputLocationDTO reportingLocation = null;
        String modelEncodings = null;

        ProjectDTO projectDTO = new ProjectDTO( modules, distributionManagement, properties, dependencyManagement, dependencies, repositories, pluginRepositories, reporting, locations, location, modulesLocation, distributionManagementLocation, propertiesLocation, dependencyManagementLocation, dependenciesLocation, repositoriesLocation, pluginRepositoriesLocation, reportsLocation, reportingLocation, modelVersion, parent, groupId, artifactId, version, packaging, name, description, url, childProjectUrlInheritAppendPath, inceptionYear, organization, licenses, developers, contributors, mailingLists, prerequisites, scm, issueManagement, ciManagement, build, profiles, modelEncodings );

        return projectDTO;
    }

    @Override
    public Model mapToModel(ProjectDTO projectDTO) {
        if ( projectDTO == null ) {
            return null;
        }

        Model model = new Model();

        model.setDependencies( dependencyDTOListToDependencyList( projectDTO.dependencies() ) );
        model.setDependencyManagement( dependencyManagementDTOToDependencyManagement( projectDTO.dependencyManagement() ) );
        model.setDistributionManagement( distributionManagementDTOToDistributionManagement( projectDTO.distributionManagement() ) );
        List<String> list1 = projectDTO.modules();
        if ( list1 != null ) {
            model.setModules( new ArrayList<String>( list1 ) );
        }
        model.setPluginRepositories( repositoryDTOListToRepositoryList( projectDTO.pluginRepositories() ) );
        Properties properties = projectDTO.properties();
        if ( properties != null ) {
            model.setProperties( new Properties( properties ) );
        }
        model.setReporting( reportingDTOToReporting( projectDTO.reporting() ) );
        model.setRepositories( repositoryDTOListToRepositoryList( projectDTO.repositories() ) );
        model.setArtifactId( projectDTO.artifactId() );
        model.setBuild( buildDTOToBuild( projectDTO.build() ) );
        model.setChildProjectUrlInheritAppendPath( projectDTO.childProjectUrlInheritAppendPath() );
        model.setCiManagement( ciManagementDTOToCiManagement( projectDTO.ciManagement() ) );
        model.setContributors( contributerDTOListToContributorList( projectDTO.contributors() ) );
        model.setDescription( projectDTO.description() );
        model.setDevelopers( developerDTOListToDeveloperList( projectDTO.developers() ) );
        model.setGroupId( projectDTO.groupId() );
        model.setInceptionYear( projectDTO.inceptionYear() );
        model.setIssueManagement( issueManagementDTOToIssueManagement( projectDTO.issueManagement() ) );
        model.setLicenses( licenseDTOListToLicenseList( projectDTO.licenses() ) );
        model.setMailingLists( mailingListDTOListToMailingListList( projectDTO.mailingLists() ) );
        model.setModelVersion( projectDTO.modelVersion() );
        model.setName( projectDTO.name() );
        model.setOrganization( organizationDTOToOrganization( projectDTO.organization() ) );
        model.setPackaging( projectDTO.packaging() );
        model.setParent( parentDTOToParent( projectDTO.parent() ) );
        model.setPrerequisites( prerequisitesDTOToPrerequisites( projectDTO.prerequisites() ) );
        model.setProfiles( profileDTOListToProfileList( projectDTO.profiles() ) );
        model.setScm( scmDTOToScm( projectDTO.scm() ) );
        model.setUrl( projectDTO.url() );
        model.setVersion( projectDTO.version() );

        return model;
    }

    protected RepositoryPolicyDTO repositoryPolicyToRepositoryPolicyDTO(RepositoryPolicy repositoryPolicy) {
        if ( repositoryPolicy == null ) {
            return null;
        }

        String enabled = null;
        String updatePolicy = null;
        String checksumPolicy = null;

        enabled = repositoryPolicy.getEnabled();
        updatePolicy = repositoryPolicy.getUpdatePolicy();
        checksumPolicy = repositoryPolicy.getChecksumPolicy();

        Map<Object, InputLocationDTO> locations = null;
        InputLocationDTO location = null;
        InputLocationDTO enabledLocation = null;
        InputLocationDTO updatePolicyLocation = null;
        InputLocationDTO checksumPolicyLocation = null;

        RepositoryPolicyDTO repositoryPolicyDTO = new RepositoryPolicyDTO( enabled, updatePolicy, checksumPolicy, locations, location, enabledLocation, updatePolicyLocation, checksumPolicyLocation );

        return repositoryPolicyDTO;
    }

    protected DeploymentRepositoryDTO deploymentRepositoryToDeploymentRepositoryDTO(DeploymentRepository deploymentRepository) {
        if ( deploymentRepository == null ) {
            return null;
        }

        String id = null;
        String name = null;
        String url = null;
        String layout = null;
        RepositoryPolicyDTO releases = null;
        RepositoryPolicyDTO snapshots = null;
        boolean uniqueVersion = false;

        id = deploymentRepository.getId();
        name = deploymentRepository.getName();
        url = deploymentRepository.getUrl();
        layout = deploymentRepository.getLayout();
        releases = repositoryPolicyToRepositoryPolicyDTO( deploymentRepository.getReleases() );
        snapshots = repositoryPolicyToRepositoryPolicyDTO( deploymentRepository.getSnapshots() );
        uniqueVersion = deploymentRepository.isUniqueVersion();

        Map<Object, InputLocationDTO> locations = null;
        InputLocationDTO locationDTO = null;
        InputLocationDTO idLocation = null;
        InputLocationDTO nameLocation = null;
        InputLocationDTO urlLocation = null;
        InputLocationDTO layoutLocation = null;

        DeploymentRepositoryDTO deploymentRepositoryDTO = new DeploymentRepositoryDTO( id, name, url, layout, locations, locationDTO, idLocation, nameLocation, urlLocation, layoutLocation, releases, snapshots, uniqueVersion );

        return deploymentRepositoryDTO;
    }

    protected SiteDTO siteToSiteDTO(Site site) {
        if ( site == null ) {
            return null;
        }

        String id = null;
        String name = null;
        String url = null;
        String childSiteUrlInheritAppendPath = null;

        id = site.getId();
        name = site.getName();
        url = site.getUrl();
        childSiteUrlInheritAppendPath = site.getChildSiteUrlInheritAppendPath();

        Map<Object, InputLocationDTO> locations = null;
        InputLocationDTO location = null;
        InputLocationDTO idLocation = null;
        InputLocationDTO nameLocation = null;
        InputLocationDTO urlLocation = null;
        InputLocationDTO childSiteUrlInheritAppendPathLocation = null;

        SiteDTO siteDTO = new SiteDTO( id, name, url, childSiteUrlInheritAppendPath, locations, location, idLocation, nameLocation, urlLocation, childSiteUrlInheritAppendPathLocation );

        return siteDTO;
    }

    protected RelocationDTO relocationToRelocationDTO(Relocation relocation) {
        if ( relocation == null ) {
            return null;
        }

        String artifactId = null;
        String groupId = null;
        String version = null;
        String message = null;

        artifactId = relocation.getArtifactId();
        groupId = relocation.getGroupId();
        version = relocation.getVersion();
        message = relocation.getMessage();

        Map<Object, InputLocationDTO> locations = null;
        InputLocationDTO location = null;
        InputLocationDTO groupIdLocation = null;
        InputLocationDTO artifactIdLocation = null;
        InputLocationDTO versionLocation = null;
        InputLocationDTO messageLocation = null;

        RelocationDTO relocationDTO = new RelocationDTO( artifactId, groupId, version, message, locations, location, groupIdLocation, artifactIdLocation, versionLocation, messageLocation );

        return relocationDTO;
    }

    protected DistributionManagementDTO distributionManagementToDistributionManagementDTO(DistributionManagement distributionManagement) {
        if ( distributionManagement == null ) {
            return null;
        }

        DeploymentRepositoryDTO repository = null;
        DeploymentRepositoryDTO snapshotRepository = null;
        SiteDTO site = null;
        String downloadUrl = null;
        RelocationDTO relocation = null;
        String status = null;

        repository = deploymentRepositoryToDeploymentRepositoryDTO( distributionManagement.getRepository() );
        snapshotRepository = deploymentRepositoryToDeploymentRepositoryDTO( distributionManagement.getSnapshotRepository() );
        site = siteToSiteDTO( distributionManagement.getSite() );
        downloadUrl = distributionManagement.getDownloadUrl();
        relocation = relocationToRelocationDTO( distributionManagement.getRelocation() );
        status = distributionManagement.getStatus();

        Map<Object, InputLocationDTO> locations = null;
        InputLocationDTO location = null;
        InputLocationDTO repositoryLocation = null;
        InputLocationDTO snapshotRepositoryLocation = null;
        InputLocationDTO siteLocation = null;
        InputLocationDTO downloadUrlLocation = null;
        InputLocationDTO relocationLocation = null;
        InputLocationDTO statusLocation = null;

        DistributionManagementDTO distributionManagementDTO = new DistributionManagementDTO( repository, snapshotRepository, site, downloadUrl, relocation, status, locations, location, repositoryLocation, snapshotRepositoryLocation, siteLocation, downloadUrlLocation, relocationLocation, statusLocation );

        return distributionManagementDTO;
    }

    protected ExclusionDTO exclusionToExclusionDTO(Exclusion exclusion) {
        if ( exclusion == null ) {
            return null;
        }

        String groupId = null;
        String artifactId = null;

        groupId = exclusion.getGroupId();
        artifactId = exclusion.getArtifactId();

        Map<Object, InputLocationDTO> locations = null;
        InputLocationDTO location = null;
        InputLocationDTO groupIdLocation = null;
        InputLocationDTO artifactIdLocation = null;

        ExclusionDTO exclusionDTO = new ExclusionDTO( groupId, artifactId, locations, location, groupIdLocation, artifactIdLocation );

        return exclusionDTO;
    }

    protected List<ExclusionDTO> exclusionListToExclusionDTOList(List<Exclusion> list) {
        if ( list == null ) {
            return null;
        }

        List<ExclusionDTO> list1 = new ArrayList<ExclusionDTO>( list.size() );
        for ( Exclusion exclusion : list ) {
            list1.add( exclusionToExclusionDTO( exclusion ) );
        }

        return list1;
    }

    protected DependencyDTO dependencyToDependencyDTO(Dependency dependency) {
        if ( dependency == null ) {
            return null;
        }

        String groupId = null;
        String artifactId = null;
        String version = null;
        String type = null;
        String classifier = null;
        String scope = null;
        String systemPath = null;
        List<ExclusionDTO> exclusions = null;
        String optional = null;

        groupId = dependency.getGroupId();
        artifactId = dependency.getArtifactId();
        version = dependency.getVersion();
        type = dependency.getType();
        classifier = dependency.getClassifier();
        scope = dependency.getScope();
        systemPath = dependency.getSystemPath();
        exclusions = exclusionListToExclusionDTOList( dependency.getExclusions() );
        optional = dependency.getOptional();

        Map<Object, InputLocationDTO> locations = null;
        InputLocationDTO location = null;
        InputLocationDTO groupIdLocation = null;
        InputLocationDTO artifactIdLocation = null;
        InputLocationDTO versionLocation = null;
        InputLocationDTO typeLocation = null;
        InputLocationDTO classifierLocation = null;
        InputLocationDTO scopeLocation = null;
        InputLocationDTO systemPathLocation = null;
        InputLocationDTO exclusionsLocation = null;
        InputLocationDTO optionalLocation = null;

        DependencyDTO dependencyDTO = new DependencyDTO( groupId, artifactId, version, type, classifier, scope, systemPath, exclusions, optional, locations, location, groupIdLocation, artifactIdLocation, versionLocation, typeLocation, classifierLocation, scopeLocation, systemPathLocation, exclusionsLocation, optionalLocation );

        return dependencyDTO;
    }

    protected List<DependencyDTO> dependencyListToDependencyDTOList(List<Dependency> list) {
        if ( list == null ) {
            return null;
        }

        List<DependencyDTO> list1 = new ArrayList<DependencyDTO>( list.size() );
        for ( Dependency dependency : list ) {
            list1.add( dependencyToDependencyDTO( dependency ) );
        }

        return list1;
    }

    protected DependencyManagementDTO dependencyManagementToDependencyManagementDTO(DependencyManagement dependencyManagement) {
        if ( dependencyManagement == null ) {
            return null;
        }

        List<DependencyDTO> dependencies = null;

        dependencies = dependencyListToDependencyDTOList( dependencyManagement.getDependencies() );

        Map<Object, InputLocationDTO> locations = null;
        InputLocationDTO location = null;
        InputLocationDTO dependenciesLocation = null;

        DependencyManagementDTO dependencyManagementDTO = new DependencyManagementDTO( dependencies, locations, location, dependenciesLocation );

        return dependencyManagementDTO;
    }

    protected RepositoryDTO repositoryToRepositoryDTO(Repository repository) {
        if ( repository == null ) {
            return null;
        }

        String id = null;
        String name = null;
        String url = null;
        String layout = null;
        RepositoryPolicyDTO releases = null;
        RepositoryPolicyDTO snapshots = null;

        id = repository.getId();
        name = repository.getName();
        url = repository.getUrl();
        layout = repository.getLayout();
        releases = repositoryPolicyToRepositoryPolicyDTO( repository.getReleases() );
        snapshots = repositoryPolicyToRepositoryPolicyDTO( repository.getSnapshots() );

        Map<Object, InputLocationDTO> locations = null;
        InputLocationDTO locationDTO = null;
        InputLocationDTO idLocation = null;
        InputLocationDTO nameLocation = null;
        InputLocationDTO urlLocation = null;
        InputLocationDTO layoutLocation = null;

        RepositoryDTO repositoryDTO = new RepositoryDTO( id, name, url, layout, locations, locationDTO, idLocation, nameLocation, urlLocation, layoutLocation, releases, snapshots );

        return repositoryDTO;
    }

    protected List<RepositoryDTO> repositoryListToRepositoryDTOList(List<Repository> list) {
        if ( list == null ) {
            return null;
        }

        List<RepositoryDTO> list1 = new ArrayList<RepositoryDTO>( list.size() );
        for ( Repository repository : list ) {
            list1.add( repositoryToRepositoryDTO( repository ) );
        }

        return list1;
    }

    protected ReportSetDTO reportSetToReportSetDTO(ReportSet reportSet) {
        if ( reportSet == null ) {
            return null;
        }

        String inherited = null;
        Object configuration = null;
        String id = null;
        List<String> reports = null;

        inherited = reportSet.getInherited();
        configuration = reportSet.getConfiguration();
        id = reportSet.getId();
        List<String> list = reportSet.getReports();
        if ( list != null ) {
            reports = new ArrayList<String>( list );
        }

        Map<Object, InputLocationDTO> locations = null;
        InputLocationDTO location = null;
        InputLocationDTO inheritedLocation = null;
        InputLocationDTO configurationLocation = null;

        ReportSetDTO reportSetDTO = new ReportSetDTO( inherited, configuration, locations, location, inheritedLocation, configurationLocation, id, reports );

        return reportSetDTO;
    }

    protected List<ReportSetDTO> reportSetListToReportSetDTOList(List<ReportSet> list) {
        if ( list == null ) {
            return null;
        }

        List<ReportSetDTO> list1 = new ArrayList<ReportSetDTO>( list.size() );
        for ( ReportSet reportSet : list ) {
            list1.add( reportSetToReportSetDTO( reportSet ) );
        }

        return list1;
    }

    protected ReportPluginDTO reportPluginToReportPluginDTO(ReportPlugin reportPlugin) {
        if ( reportPlugin == null ) {
            return null;
        }

        String inherited = null;
        Object configuration = null;
        String groupId = null;
        String artifactId = null;
        String version = null;
        List<ReportSetDTO> reportSets = null;

        inherited = reportPlugin.getInherited();
        configuration = reportPlugin.getConfiguration();
        groupId = reportPlugin.getGroupId();
        artifactId = reportPlugin.getArtifactId();
        version = reportPlugin.getVersion();
        reportSets = reportSetListToReportSetDTOList( reportPlugin.getReportSets() );

        Map<Object, InputLocationDTO> locations = null;
        InputLocationDTO location = null;
        InputLocationDTO inheritedLocation = null;
        InputLocationDTO configurationLocation = null;

        ReportPluginDTO reportPluginDTO = new ReportPluginDTO( inherited, configuration, locations, location, inheritedLocation, configurationLocation, groupId, artifactId, version, reportSets );

        return reportPluginDTO;
    }

    protected List<ReportPluginDTO> reportPluginListToReportPluginDTOList(List<ReportPlugin> list) {
        if ( list == null ) {
            return null;
        }

        List<ReportPluginDTO> list1 = new ArrayList<ReportPluginDTO>( list.size() );
        for ( ReportPlugin reportPlugin : list ) {
            list1.add( reportPluginToReportPluginDTO( reportPlugin ) );
        }

        return list1;
    }

    protected ReportingDTO reportingToReportingDTO(Reporting reporting) {
        if ( reporting == null ) {
            return null;
        }

        String excludeDefaults = null;
        String outputDirectory = null;
        List<ReportPluginDTO> plugins = null;

        excludeDefaults = reporting.getExcludeDefaults();
        outputDirectory = reporting.getOutputDirectory();
        plugins = reportPluginListToReportPluginDTOList( reporting.getPlugins() );

        Map<Object, InputLocationDTO> locations = null;
        InputLocationDTO location = null;
        InputLocationDTO excludeDefaultsLocation = null;
        InputLocationDTO outputDirectoryLocation = null;
        InputLocationDTO pluginsLocation = null;

        ReportingDTO reportingDTO = new ReportingDTO( excludeDefaults, outputDirectory, plugins, locations, location, excludeDefaultsLocation, outputDirectoryLocation, pluginsLocation );

        return reportingDTO;
    }

    protected ParentDTO parentToParentDTO(Parent parent) {
        if ( parent == null ) {
            return null;
        }

        String groupId = null;
        String artifactId = null;
        String version = null;
        String relativePath = null;

        groupId = parent.getGroupId();
        artifactId = parent.getArtifactId();
        version = parent.getVersion();
        relativePath = parent.getRelativePath();

        Map<Object, InputLocationDTO> locations = null;
        InputLocationDTO location = null;
        InputLocationDTO groupIdLocation = null;
        InputLocationDTO artifactIdLocation = null;
        InputLocationDTO versionLocation = null;
        InputLocationDTO relativePathLocation = null;

        ParentDTO parentDTO = new ParentDTO( groupId, artifactId, version, relativePath, locations, location, groupIdLocation, artifactIdLocation, versionLocation, relativePathLocation );

        return parentDTO;
    }

    protected OrganizationDTO organizationToOrganizationDTO(Organization organization) {
        if ( organization == null ) {
            return null;
        }

        String name = null;
        String url = null;

        name = organization.getName();
        url = organization.getUrl();

        Map<Object, InputLocationDTO> locations = null;
        InputLocationDTO location = null;
        InputLocationDTO nameLocation = null;
        InputLocationDTO urlLocation = null;

        OrganizationDTO organizationDTO = new OrganizationDTO( name, url, locations, location, nameLocation, urlLocation );

        return organizationDTO;
    }

    protected LicenseDTO licenseToLicenseDTO(License license) {
        if ( license == null ) {
            return null;
        }

        String name = null;
        String url = null;
        String distribution = null;
        String comments = null;

        name = license.getName();
        url = license.getUrl();
        distribution = license.getDistribution();
        comments = license.getComments();

        Map<Object, InputLocationDTO> locations = null;
        InputLocationDTO location = null;
        InputLocationDTO nameLocation = null;
        InputLocationDTO urlLocation = null;
        InputLocationDTO distributionLocation = null;
        InputLocationDTO commentsLocation = null;

        LicenseDTO licenseDTO = new LicenseDTO( name, url, distribution, comments, locations, location, nameLocation, urlLocation, distributionLocation, commentsLocation );

        return licenseDTO;
    }

    protected List<LicenseDTO> licenseListToLicenseDTOList(List<License> list) {
        if ( list == null ) {
            return null;
        }

        List<LicenseDTO> list1 = new ArrayList<LicenseDTO>( list.size() );
        for ( License license : list ) {
            list1.add( licenseToLicenseDTO( license ) );
        }

        return list1;
    }

    protected DeveloperDTO developerToDeveloperDTO(Developer developer) {
        if ( developer == null ) {
            return null;
        }

        String name = null;
        String email = null;
        String url = null;
        String organization = null;
        String organizationUrl = null;
        List<String> roles = null;
        String timezone = null;
        Properties properties = null;
        String id = null;

        name = developer.getName();
        email = developer.getEmail();
        url = developer.getUrl();
        organization = developer.getOrganization();
        organizationUrl = developer.getOrganizationUrl();
        List<String> list = developer.getRoles();
        if ( list != null ) {
            roles = new ArrayList<String>( list );
        }
        timezone = developer.getTimezone();
        Properties properties1 = developer.getProperties();
        if ( properties1 != null ) {
            properties = new Properties( properties1 );
        }
        id = developer.getId();

        Map<Object, InputLocationDTO> locations = null;
        InputLocationDTO location = null;
        InputLocationDTO nameLocation = null;
        InputLocationDTO emailLocation = null;
        InputLocationDTO urlLocation = null;
        InputLocationDTO organizationLocation = null;
        InputLocationDTO organizationUrlLocation = null;
        InputLocationDTO rolesLocation = null;
        InputLocationDTO timezoneLocation = null;
        InputLocationDTO propertiesLocation = null;

        DeveloperDTO developerDTO = new DeveloperDTO( name, email, url, organization, organizationUrl, roles, timezone, properties, locations, location, nameLocation, emailLocation, urlLocation, organizationLocation, organizationUrlLocation, rolesLocation, timezoneLocation, propertiesLocation, id );

        return developerDTO;
    }

    protected List<DeveloperDTO> developerListToDeveloperDTOList(List<Developer> list) {
        if ( list == null ) {
            return null;
        }

        List<DeveloperDTO> list1 = new ArrayList<DeveloperDTO>( list.size() );
        for ( Developer developer : list ) {
            list1.add( developerToDeveloperDTO( developer ) );
        }

        return list1;
    }

    protected ContributerDTO contributorToContributerDTO(Contributor contributor) {
        if ( contributor == null ) {
            return null;
        }

        String name = null;
        String email = null;
        String url = null;
        String organization = null;
        String organizationUrl = null;
        List<String> roles = null;
        String timezone = null;
        Properties properties = null;

        name = contributor.getName();
        email = contributor.getEmail();
        url = contributor.getUrl();
        organization = contributor.getOrganization();
        organizationUrl = contributor.getOrganizationUrl();
        List<String> list = contributor.getRoles();
        if ( list != null ) {
            roles = new ArrayList<String>( list );
        }
        timezone = contributor.getTimezone();
        Properties properties1 = contributor.getProperties();
        if ( properties1 != null ) {
            properties = new Properties( properties1 );
        }

        Map<Object, InputLocationDTO> locations = null;
        InputLocationDTO location = null;
        InputLocationDTO nameLocation = null;
        InputLocationDTO emailLocation = null;
        InputLocationDTO urlLocation = null;
        InputLocationDTO organizationLocation = null;
        InputLocationDTO organizationUrlLocation = null;
        InputLocationDTO rolesLocation = null;
        InputLocationDTO timezoneLocation = null;
        InputLocationDTO propertiesLocation = null;

        ContributerDTO contributerDTO = new ContributerDTO( name, email, url, organization, organizationUrl, roles, timezone, properties, locations, location, nameLocation, emailLocation, urlLocation, organizationLocation, organizationUrlLocation, rolesLocation, timezoneLocation, propertiesLocation );

        return contributerDTO;
    }

    protected List<ContributerDTO> contributorListToContributerDTOList(List<Contributor> list) {
        if ( list == null ) {
            return null;
        }

        List<ContributerDTO> list1 = new ArrayList<ContributerDTO>( list.size() );
        for ( Contributor contributor : list ) {
            list1.add( contributorToContributerDTO( contributor ) );
        }

        return list1;
    }

    protected MailingListDTO mailingListToMailingListDTO(MailingList mailingList) {
        if ( mailingList == null ) {
            return null;
        }

        String name = null;
        String subscribe = null;
        String unsubscribe = null;
        String post = null;
        String archive = null;
        List<String> otherArchives = null;

        name = mailingList.getName();
        subscribe = mailingList.getSubscribe();
        unsubscribe = mailingList.getUnsubscribe();
        post = mailingList.getPost();
        archive = mailingList.getArchive();
        List<String> list = mailingList.getOtherArchives();
        if ( list != null ) {
            otherArchives = new ArrayList<String>( list );
        }

        Map<Object, InputLocationDTO> locations = null;
        InputLocationDTO location = null;
        InputLocationDTO nameLocation = null;
        InputLocationDTO subscribeLocation = null;
        InputLocationDTO unsubscribeLocation = null;
        InputLocationDTO postLocation = null;
        InputLocationDTO archiveLocation = null;
        InputLocationDTO otherArchivesLocation = null;

        MailingListDTO mailingListDTO = new MailingListDTO( name, subscribe, unsubscribe, post, archive, otherArchives, locations, location, nameLocation, subscribeLocation, unsubscribeLocation, postLocation, archiveLocation, otherArchivesLocation );

        return mailingListDTO;
    }

    protected List<MailingListDTO> mailingListListToMailingListDTOList(List<MailingList> list) {
        if ( list == null ) {
            return null;
        }

        List<MailingListDTO> list1 = new ArrayList<MailingListDTO>( list.size() );
        for ( MailingList mailingList : list ) {
            list1.add( mailingListToMailingListDTO( mailingList ) );
        }

        return list1;
    }

    protected PrerequisitesDTO prerequisitesToPrerequisitesDTO(Prerequisites prerequisites) {
        if ( prerequisites == null ) {
            return null;
        }

        String maven = null;

        maven = prerequisites.getMaven();

        Map<Object, InputLocationDTO> locations = null;
        InputLocationDTO location = null;
        InputLocationDTO mavenLocation = null;

        PrerequisitesDTO prerequisitesDTO = new PrerequisitesDTO( maven, locations, location, mavenLocation );

        return prerequisitesDTO;
    }

    protected ScmDTO scmToScmDTO(Scm scm) {
        if ( scm == null ) {
            return null;
        }

        String connection = null;
        String developerConnection = null;
        String tag = null;
        String url = null;
        String childScmConnectionInheritAppendPath = null;
        String childScmDeveloperConnectionInheritAppendPath = null;
        String childScmUrlInheritAppendPath = null;

        connection = scm.getConnection();
        developerConnection = scm.getDeveloperConnection();
        tag = scm.getTag();
        url = scm.getUrl();
        childScmConnectionInheritAppendPath = scm.getChildScmConnectionInheritAppendPath();
        childScmDeveloperConnectionInheritAppendPath = scm.getChildScmDeveloperConnectionInheritAppendPath();
        childScmUrlInheritAppendPath = scm.getChildScmUrlInheritAppendPath();

        Map<Object, InputLocationDTO> locations = null;
        InputLocationDTO location = null;
        InputLocationDTO connectionLocation = null;
        InputLocationDTO developerConnectionLocation = null;
        InputLocationDTO tagLocation = null;
        InputLocationDTO urlLocation = null;
        InputLocationDTO childScmConnectionInheritAppendPathLocation = null;
        InputLocationDTO childScmDeveloperConnectionInheritAppendPathLocation = null;
        InputLocationDTO childScmUrlInheritAppendPathLocation = null;

        ScmDTO scmDTO = new ScmDTO( connection, developerConnection, tag, url, childScmConnectionInheritAppendPath, childScmDeveloperConnectionInheritAppendPath, childScmUrlInheritAppendPath, locations, location, connectionLocation, developerConnectionLocation, tagLocation, urlLocation, childScmConnectionInheritAppendPathLocation, childScmDeveloperConnectionInheritAppendPathLocation, childScmUrlInheritAppendPathLocation );

        return scmDTO;
    }

    protected IssueManagementDTO issueManagementToIssueManagementDTO(IssueManagement issueManagement) {
        if ( issueManagement == null ) {
            return null;
        }

        String system = null;
        String url = null;

        system = issueManagement.getSystem();
        url = issueManagement.getUrl();

        Map<Object, InputLocationDTO> locations = null;
        InputLocationDTO location = null;
        InputLocationDTO systemLocation = null;
        InputLocationDTO urlLocation = null;

        IssueManagementDTO issueManagementDTO = new IssueManagementDTO( system, url, locations, location, systemLocation, urlLocation );

        return issueManagementDTO;
    }

    protected NotifierDTO notifierToNotifierDTO(Notifier notifier) {
        if ( notifier == null ) {
            return null;
        }

        String type = null;
        boolean sendOnError = false;
        boolean sendOnFailure = false;
        boolean sendOnSuccess = false;
        boolean sendOnWarning = false;

        type = notifier.getType();
        sendOnError = notifier.isSendOnError();
        sendOnFailure = notifier.isSendOnFailure();
        sendOnSuccess = notifier.isSendOnSuccess();
        sendOnWarning = notifier.isSendOnWarning();

        Properties properties = null;
        Map<Object, InputLocationDTO> locations = null;
        InputLocationDTO location = null;
        InputLocationDTO typeLocation = null;
        InputLocationDTO sendOnErrorLocation = null;
        InputLocationDTO sendOnFailureLocation = null;
        InputLocationDTO sendOnSuccessLocation = null;
        InputLocationDTO sendOnWarningLocation = null;
        InputLocationDTO addressLocation = null;
        InputLocationDTO configurationLocation = null;

        NotifierDTO notifierDTO = new NotifierDTO( type, sendOnError, sendOnFailure, sendOnSuccess, sendOnWarning, properties, locations, location, typeLocation, sendOnErrorLocation, sendOnFailureLocation, sendOnSuccessLocation, sendOnWarningLocation, addressLocation, configurationLocation );

        return notifierDTO;
    }

    protected List<NotifierDTO> notifierListToNotifierDTOList(List<Notifier> list) {
        if ( list == null ) {
            return null;
        }

        List<NotifierDTO> list1 = new ArrayList<NotifierDTO>( list.size() );
        for ( Notifier notifier : list ) {
            list1.add( notifierToNotifierDTO( notifier ) );
        }

        return list1;
    }

    protected CiManagementDTO ciManagementToCiManagementDTO(CiManagement ciManagement) {
        if ( ciManagement == null ) {
            return null;
        }

        String system = null;
        String url = null;
        List<NotifierDTO> notifiers = null;

        system = ciManagement.getSystem();
        url = ciManagement.getUrl();
        notifiers = notifierListToNotifierDTOList( ciManagement.getNotifiers() );

        Map<Object, InputLocationDTO> locations = null;
        InputLocationDTO location = null;
        InputLocationDTO systemLocation = null;
        InputLocationDTO urlLocation = null;
        InputLocationDTO notifiersLocation = null;

        CiManagementDTO ciManagementDTO = new CiManagementDTO( system, url, notifiers, locations, location, systemLocation, urlLocation, notifiersLocation );

        return ciManagementDTO;
    }

    protected PluginExecutionDTO pluginExecutionToPluginExecutionDTO(PluginExecution pluginExecution) {
        if ( pluginExecution == null ) {
            return null;
        }

        String inherited = null;
        Object configuration = null;
        String id = null;
        String phase = null;
        List<String> goals = null;

        inherited = pluginExecution.getInherited();
        configuration = pluginExecution.getConfiguration();
        id = pluginExecution.getId();
        phase = pluginExecution.getPhase();
        List<String> list = pluginExecution.getGoals();
        if ( list != null ) {
            goals = new ArrayList<String>( list );
        }

        Map<Object, InputLocationDTO> locations = null;
        InputLocationDTO location = null;
        InputLocationDTO inheritedLocation = null;
        InputLocationDTO configurationLocation = null;

        PluginExecutionDTO pluginExecutionDTO = new PluginExecutionDTO( inherited, configuration, locations, location, inheritedLocation, configurationLocation, id, phase, goals );

        return pluginExecutionDTO;
    }

    protected List<PluginExecutionDTO> pluginExecutionListToPluginExecutionDTOList(List<PluginExecution> list) {
        if ( list == null ) {
            return null;
        }

        List<PluginExecutionDTO> list1 = new ArrayList<PluginExecutionDTO>( list.size() );
        for ( PluginExecution pluginExecution : list ) {
            list1.add( pluginExecutionToPluginExecutionDTO( pluginExecution ) );
        }

        return list1;
    }

    protected PluginDTO pluginToPluginDTO(Plugin plugin) {
        if ( plugin == null ) {
            return null;
        }

        String inherited = null;
        Object configuration = null;
        String groupId = null;
        String artifactId = null;
        String version = null;
        String extensions = null;
        List<PluginExecutionDTO> executions = null;
        List<DependencyDTO> dependencies = null;

        inherited = plugin.getInherited();
        configuration = plugin.getConfiguration();
        groupId = plugin.getGroupId();
        artifactId = plugin.getArtifactId();
        version = plugin.getVersion();
        extensions = plugin.getExtensions();
        executions = pluginExecutionListToPluginExecutionDTOList( plugin.getExecutions() );
        dependencies = dependencyListToDependencyDTOList( plugin.getDependencies() );

        Map<Object, InputLocationDTO> locations = null;
        InputLocationDTO location = null;
        InputLocationDTO inheritedLocation = null;
        InputLocationDTO configurationLocation = null;

        PluginDTO pluginDTO = new PluginDTO( inherited, configuration, locations, location, inheritedLocation, configurationLocation, groupId, artifactId, version, extensions, executions, dependencies );

        return pluginDTO;
    }

    protected List<PluginDTO> pluginListToPluginDTOList(List<Plugin> list) {
        if ( list == null ) {
            return null;
        }

        List<PluginDTO> list1 = new ArrayList<PluginDTO>( list.size() );
        for ( Plugin plugin : list ) {
            list1.add( pluginToPluginDTO( plugin ) );
        }

        return list1;
    }

    protected PluginManagementDTO pluginManagementToPluginManagementDTO(PluginManagement pluginManagement) {
        if ( pluginManagement == null ) {
            return null;
        }

        List<PluginDTO> plugins = null;

        plugins = pluginListToPluginDTOList( pluginManagement.getPlugins() );

        Map<Object, InputLocationDTO> locations = null;
        InputLocationDTO location = null;
        InputLocationDTO pluginsLocation = null;

        PluginManagementDTO pluginManagementDTO = new PluginManagementDTO( plugins, locations, location, pluginsLocation );

        return pluginManagementDTO;
    }

    protected ResourceDTO resourceToResourceDTO(Resource resource) {
        if ( resource == null ) {
            return null;
        }

        List<String> includes = null;
        List<String> excludes = null;
        String directory = null;
        String targetPath = null;
        String filtering = null;
        String mergeId = null;

        List<String> list = resource.getIncludes();
        if ( list != null ) {
            includes = new ArrayList<String>( list );
        }
        List<String> list1 = resource.getExcludes();
        if ( list1 != null ) {
            excludes = new ArrayList<String>( list1 );
        }
        directory = resource.getDirectory();
        targetPath = resource.getTargetPath();
        filtering = resource.getFiltering();
        mergeId = resource.getMergeId();

        Map<Object, InputLocationDTO> locations = null;
        InputLocationDTO location = null;
        InputLocationDTO includesLocation = null;
        InputLocationDTO excludesLocation = null;

        ResourceDTO resourceDTO = new ResourceDTO( includes, excludes, locations, location, includesLocation, excludesLocation, directory, targetPath, filtering, mergeId );

        return resourceDTO;
    }

    protected List<ResourceDTO> resourceListToResourceDTOList(List<Resource> list) {
        if ( list == null ) {
            return null;
        }

        List<ResourceDTO> list1 = new ArrayList<ResourceDTO>( list.size() );
        for ( Resource resource : list ) {
            list1.add( resourceToResourceDTO( resource ) );
        }

        return list1;
    }

    protected ExtensionDTO extensionToExtensionDTO(Extension extension) {
        if ( extension == null ) {
            return null;
        }

        String groupId = null;
        String artifactId = null;
        String version = null;

        groupId = extension.getGroupId();
        artifactId = extension.getArtifactId();
        version = extension.getVersion();

        Map<Object, InputLocationDTO> locations = null;
        InputLocationDTO location = null;
        InputLocationDTO groupIdLocation = null;
        InputLocationDTO artifactIdLocation = null;
        InputLocationDTO versionLocation = null;

        ExtensionDTO extensionDTO = new ExtensionDTO( groupId, artifactId, version, locations, location, groupIdLocation, artifactIdLocation, versionLocation );

        return extensionDTO;
    }

    protected List<ExtensionDTO> extensionListToExtensionDTOList(List<Extension> list) {
        if ( list == null ) {
            return null;
        }

        List<ExtensionDTO> list1 = new ArrayList<ExtensionDTO>( list.size() );
        for ( Extension extension : list ) {
            list1.add( extensionToExtensionDTO( extension ) );
        }

        return list1;
    }

    protected BuildDTO buildToBuildDTO(Build build) {
        if ( build == null ) {
            return null;
        }

        List<PluginDTO> plugins = null;
        PluginManagementDTO pluginManagement = null;
        String defaultGoal = null;
        List<ResourceDTO> resources = null;
        List<ResourceDTO> testResources = null;
        String directory = null;
        String finalName = null;
        List<String> filters = null;
        String sourceDirectory = null;
        String scriptSourceDirectory = null;
        String testSourceDirectory = null;
        String outputDirectory = null;
        String testOutputDirectory = null;
        List<ExtensionDTO> extensions = null;

        plugins = pluginListToPluginDTOList( build.getPlugins() );
        pluginManagement = pluginManagementToPluginManagementDTO( build.getPluginManagement() );
        defaultGoal = build.getDefaultGoal();
        resources = resourceListToResourceDTOList( build.getResources() );
        testResources = resourceListToResourceDTOList( build.getTestResources() );
        directory = build.getDirectory();
        finalName = build.getFinalName();
        List<String> list3 = build.getFilters();
        if ( list3 != null ) {
            filters = new ArrayList<String>( list3 );
        }
        sourceDirectory = build.getSourceDirectory();
        scriptSourceDirectory = build.getScriptSourceDirectory();
        testSourceDirectory = build.getTestSourceDirectory();
        outputDirectory = build.getOutputDirectory();
        testOutputDirectory = build.getTestOutputDirectory();
        extensions = extensionListToExtensionDTOList( build.getExtensions() );

        Map<Object, InputLocationDTO> locations = null;
        InputLocationDTO location = null;
        InputLocationDTO pluginsLocation = null;

        BuildDTO buildDTO = new BuildDTO( plugins, locations, location, pluginsLocation, pluginManagement, defaultGoal, resources, testResources, directory, finalName, filters, sourceDirectory, scriptSourceDirectory, testSourceDirectory, outputDirectory, testOutputDirectory, extensions );

        return buildDTO;
    }

    protected ActivationOS_DTO activationOSToActivationOS_DTO(ActivationOS activationOS) {
        if ( activationOS == null ) {
            return null;
        }

        String name = null;
        String family = null;
        String arch = null;
        String version = null;

        name = activationOS.getName();
        family = activationOS.getFamily();
        arch = activationOS.getArch();
        version = activationOS.getVersion();

        Map<Object, InputLocationDTO> locations = null;
        InputLocationDTO location = null;
        InputLocationDTO nameLocation = null;
        InputLocationDTO familyLocation = null;
        InputLocationDTO archLocation = null;
        InputLocationDTO versionLocation = null;

        ActivationOS_DTO activationOS_DTO = new ActivationOS_DTO( name, family, arch, version, locations, location, nameLocation, familyLocation, archLocation, versionLocation );

        return activationOS_DTO;
    }

    protected ActivationPropertyDTO activationPropertyToActivationPropertyDTO(ActivationProperty activationProperty) {
        if ( activationProperty == null ) {
            return null;
        }

        String name = null;
        String value = null;

        name = activationProperty.getName();
        value = activationProperty.getValue();

        Map<Object, InputLocationDTO> locations = null;
        InputLocationDTO location = null;
        InputLocationDTO nameLocation = null;
        InputLocationDTO valueLocation = null;

        ActivationPropertyDTO activationPropertyDTO = new ActivationPropertyDTO( name, value, locations, location, nameLocation, valueLocation );

        return activationPropertyDTO;
    }

    protected ActivationFileDTO activationFileToActivationFileDTO(ActivationFile activationFile) {
        if ( activationFile == null ) {
            return null;
        }

        String missing = null;
        String exists = null;

        missing = activationFile.getMissing();
        exists = activationFile.getExists();

        Map<Object, InputLocationDTO> locations = null;
        InputLocationDTO location = null;
        InputLocationDTO missingLocation = null;
        InputLocationDTO existsLocation = null;

        ActivationFileDTO activationFileDTO = new ActivationFileDTO( missing, exists, locations, location, missingLocation, existsLocation );

        return activationFileDTO;
    }

    protected ActivationDTO activationToActivationDTO(Activation activation) {
        if ( activation == null ) {
            return null;
        }

        boolean activeByDefault = false;
        String jdk = null;
        ActivationOS_DTO os = null;
        ActivationPropertyDTO property = null;
        ActivationFileDTO file = null;

        activeByDefault = activation.isActiveByDefault();
        jdk = activation.getJdk();
        os = activationOSToActivationOS_DTO( activation.getOs() );
        property = activationPropertyToActivationPropertyDTO( activation.getProperty() );
        file = activationFileToActivationFileDTO( activation.getFile() );

        Map<Object, InputLocationDTO> locations = null;
        InputLocationDTO location = null;
        InputLocationDTO activeByDefaultLocation = null;
        InputLocationDTO jdkLocation = null;
        InputLocationDTO osLocation = null;
        InputLocationDTO propertyLocation = null;
        InputLocationDTO fileLocation = null;

        ActivationDTO activationDTO = new ActivationDTO( activeByDefault, jdk, os, property, file, locations, location, activeByDefaultLocation, jdkLocation, osLocation, propertyLocation, fileLocation );

        return activationDTO;
    }

    protected BuildBaseDTO buildBaseToBuildBaseDTO(BuildBase buildBase) {
        if ( buildBase == null ) {
            return null;
        }

        List<PluginDTO> plugins = null;
        PluginManagementDTO pluginManagement = null;
        String defaultGoal = null;
        List<ResourceDTO> resources = null;
        List<ResourceDTO> testResources = null;
        String directory = null;
        String finalName = null;
        List<String> filters = null;

        plugins = pluginListToPluginDTOList( buildBase.getPlugins() );
        pluginManagement = pluginManagementToPluginManagementDTO( buildBase.getPluginManagement() );
        defaultGoal = buildBase.getDefaultGoal();
        resources = resourceListToResourceDTOList( buildBase.getResources() );
        testResources = resourceListToResourceDTOList( buildBase.getTestResources() );
        directory = buildBase.getDirectory();
        finalName = buildBase.getFinalName();
        List<String> list3 = buildBase.getFilters();
        if ( list3 != null ) {
            filters = new ArrayList<String>( list3 );
        }

        Map<Object, InputLocationDTO> locations = null;
        InputLocationDTO location = null;
        InputLocationDTO pluginsLocation = null;

        BuildBaseDTO buildBaseDTO = new BuildBaseDTO( plugins, locations, location, pluginsLocation, pluginManagement, defaultGoal, resources, testResources, directory, finalName, filters );

        return buildBaseDTO;
    }

    protected ProfileDTO profileToProfileDTO(Profile profile) {
        if ( profile == null ) {
            return null;
        }

        List<String> modules = null;
        DistributionManagementDTO distributionManagement = null;
        Properties properties = null;
        DependencyManagementDTO dependencyManagement = null;
        List<DependencyDTO> dependencies = null;
        List<RepositoryDTO> repositories = null;
        List<RepositoryDTO> pluginRepositories = null;
        ReportingDTO reporting = null;
        String id = null;
        ActivationDTO activation = null;
        BuildBaseDTO build = null;

        List<String> list = profile.getModules();
        if ( list != null ) {
            modules = new ArrayList<String>( list );
        }
        distributionManagement = distributionManagementToDistributionManagementDTO( profile.getDistributionManagement() );
        Properties properties1 = profile.getProperties();
        if ( properties1 != null ) {
            properties = new Properties( properties1 );
        }
        dependencyManagement = dependencyManagementToDependencyManagementDTO( profile.getDependencyManagement() );
        dependencies = dependencyListToDependencyDTOList( profile.getDependencies() );
        repositories = repositoryListToRepositoryDTOList( profile.getRepositories() );
        pluginRepositories = repositoryListToRepositoryDTOList( profile.getPluginRepositories() );
        reporting = reportingToReportingDTO( profile.getReporting() );
        id = profile.getId();
        activation = activationToActivationDTO( profile.getActivation() );
        build = buildBaseToBuildBaseDTO( profile.getBuild() );

        Map<Object, InputLocationDTO> locations = null;
        InputLocationDTO location = null;
        InputLocationDTO modulesLocation = null;
        InputLocationDTO distributionManagementLocation = null;
        InputLocationDTO propertiesLocation = null;
        InputLocationDTO dependencyManagementLocation = null;
        InputLocationDTO dependenciesLocation = null;
        InputLocationDTO repositoriesLocation = null;
        InputLocationDTO pluginRepositoriesLocation = null;
        InputLocationDTO reportsLocation = null;
        InputLocationDTO reportingLocation = null;

        ProfileDTO profileDTO = new ProfileDTO( modules, distributionManagement, properties, dependencyManagement, dependencies, repositories, pluginRepositories, reporting, locations, location, modulesLocation, distributionManagementLocation, propertiesLocation, dependencyManagementLocation, dependenciesLocation, repositoriesLocation, pluginRepositoriesLocation, reportsLocation, reportingLocation, id, activation, build );

        return profileDTO;
    }

    protected List<ProfileDTO> profileListToProfileDTOList(List<Profile> list) {
        if ( list == null ) {
            return null;
        }

        List<ProfileDTO> list1 = new ArrayList<ProfileDTO>( list.size() );
        for ( Profile profile : list ) {
            list1.add( profileToProfileDTO( profile ) );
        }

        return list1;
    }

    protected Exclusion exclusionDTOToExclusion(ExclusionDTO exclusionDTO) {
        if ( exclusionDTO == null ) {
            return null;
        }

        Exclusion exclusion = new Exclusion();

        exclusion.setArtifactId( exclusionDTO.artifactId() );
        exclusion.setGroupId( exclusionDTO.groupId() );

        return exclusion;
    }

    protected List<Exclusion> exclusionDTOListToExclusionList(List<ExclusionDTO> list) {
        if ( list == null ) {
            return null;
        }

        List<Exclusion> list1 = new ArrayList<Exclusion>( list.size() );
        for ( ExclusionDTO exclusionDTO : list ) {
            list1.add( exclusionDTOToExclusion( exclusionDTO ) );
        }

        return list1;
    }

    protected Dependency dependencyDTOToDependency(DependencyDTO dependencyDTO) {
        if ( dependencyDTO == null ) {
            return null;
        }

        Dependency dependency = new Dependency();

        dependency.setArtifactId( dependencyDTO.artifactId() );
        dependency.setClassifier( dependencyDTO.classifier() );
        dependency.setExclusions( exclusionDTOListToExclusionList( dependencyDTO.exclusions() ) );
        dependency.setGroupId( dependencyDTO.groupId() );
        dependency.setOptional( dependencyDTO.optional() );
        dependency.setScope( dependencyDTO.scope() );
        dependency.setSystemPath( dependencyDTO.systemPath() );
        dependency.setType( dependencyDTO.type() );
        dependency.setVersion( dependencyDTO.version() );

        return dependency;
    }

    protected List<Dependency> dependencyDTOListToDependencyList(List<DependencyDTO> list) {
        if ( list == null ) {
            return null;
        }

        List<Dependency> list1 = new ArrayList<Dependency>( list.size() );
        for ( DependencyDTO dependencyDTO : list ) {
            list1.add( dependencyDTOToDependency( dependencyDTO ) );
        }

        return list1;
    }

    protected DependencyManagement dependencyManagementDTOToDependencyManagement(DependencyManagementDTO dependencyManagementDTO) {
        if ( dependencyManagementDTO == null ) {
            return null;
        }

        DependencyManagement dependencyManagement = new DependencyManagement();

        dependencyManagement.setDependencies( dependencyDTOListToDependencyList( dependencyManagementDTO.dependencies() ) );

        return dependencyManagement;
    }

    protected Relocation relocationDTOToRelocation(RelocationDTO relocationDTO) {
        if ( relocationDTO == null ) {
            return null;
        }

        Relocation relocation = new Relocation();

        relocation.setArtifactId( relocationDTO.artifactId() );
        relocation.setGroupId( relocationDTO.groupId() );
        relocation.setMessage( relocationDTO.message() );
        relocation.setVersion( relocationDTO.version() );

        return relocation;
    }

    protected RepositoryPolicy repositoryPolicyDTOToRepositoryPolicy(RepositoryPolicyDTO repositoryPolicyDTO) {
        if ( repositoryPolicyDTO == null ) {
            return null;
        }

        RepositoryPolicy repositoryPolicy = new RepositoryPolicy();

        repositoryPolicy.setChecksumPolicy( repositoryPolicyDTO.checksumPolicy() );
        repositoryPolicy.setEnabled( repositoryPolicyDTO.enabled() );
        repositoryPolicy.setUpdatePolicy( repositoryPolicyDTO.updatePolicy() );

        return repositoryPolicy;
    }

    protected DeploymentRepository deploymentRepositoryDTOToDeploymentRepository(DeploymentRepositoryDTO deploymentRepositoryDTO) {
        if ( deploymentRepositoryDTO == null ) {
            return null;
        }

        DeploymentRepository deploymentRepository = new DeploymentRepository();

        deploymentRepository.setId( deploymentRepositoryDTO.id() );
        deploymentRepository.setLayout( deploymentRepositoryDTO.layout() );
        deploymentRepository.setName( deploymentRepositoryDTO.name() );
        deploymentRepository.setUrl( deploymentRepositoryDTO.url() );
        deploymentRepository.setReleases( repositoryPolicyDTOToRepositoryPolicy( deploymentRepositoryDTO.releases() ) );
        deploymentRepository.setSnapshots( repositoryPolicyDTOToRepositoryPolicy( deploymentRepositoryDTO.snapshots() ) );
        deploymentRepository.setUniqueVersion( deploymentRepositoryDTO.uniqueVersion() );

        return deploymentRepository;
    }

    protected Site siteDTOToSite(SiteDTO siteDTO) {
        if ( siteDTO == null ) {
            return null;
        }

        Site site = new Site();

        site.setChildSiteUrlInheritAppendPath( siteDTO.childSiteUrlInheritAppendPath() );
        site.setId( siteDTO.id() );
        site.setName( siteDTO.name() );
        site.setUrl( siteDTO.url() );

        return site;
    }

    protected DistributionManagement distributionManagementDTOToDistributionManagement(DistributionManagementDTO distributionManagementDTO) {
        if ( distributionManagementDTO == null ) {
            return null;
        }

        DistributionManagement distributionManagement = new DistributionManagement();

        distributionManagement.setDownloadUrl( distributionManagementDTO.downloadUrl() );
        distributionManagement.setRelocation( relocationDTOToRelocation( distributionManagementDTO.relocation() ) );
        distributionManagement.setRepository( deploymentRepositoryDTOToDeploymentRepository( distributionManagementDTO.repository() ) );
        distributionManagement.setSite( siteDTOToSite( distributionManagementDTO.site() ) );
        distributionManagement.setSnapshotRepository( deploymentRepositoryDTOToDeploymentRepository( distributionManagementDTO.snapshotRepository() ) );
        distributionManagement.setStatus( distributionManagementDTO.status() );

        return distributionManagement;
    }

    protected Repository repositoryDTOToRepository(RepositoryDTO repositoryDTO) {
        if ( repositoryDTO == null ) {
            return null;
        }

        Repository repository = new Repository();

        repository.setId( repositoryDTO.id() );
        repository.setLayout( repositoryDTO.layout() );
        repository.setName( repositoryDTO.name() );
        repository.setUrl( repositoryDTO.url() );
        repository.setReleases( repositoryPolicyDTOToRepositoryPolicy( repositoryDTO.releases() ) );
        repository.setSnapshots( repositoryPolicyDTOToRepositoryPolicy( repositoryDTO.snapshots() ) );

        return repository;
    }

    protected List<Repository> repositoryDTOListToRepositoryList(List<RepositoryDTO> list) {
        if ( list == null ) {
            return null;
        }

        List<Repository> list1 = new ArrayList<Repository>( list.size() );
        for ( RepositoryDTO repositoryDTO : list ) {
            list1.add( repositoryDTOToRepository( repositoryDTO ) );
        }

        return list1;
    }

    protected ReportSet reportSetDTOToReportSet(ReportSetDTO reportSetDTO) {
        if ( reportSetDTO == null ) {
            return null;
        }

        ReportSet reportSet = new ReportSet();

        reportSet.setConfiguration( reportSetDTO.configuration() );
        reportSet.setInherited( reportSetDTO.inherited() );
        reportSet.setId( reportSetDTO.id() );
        List<String> list = reportSetDTO.reports();
        if ( list != null ) {
            reportSet.setReports( new ArrayList<String>( list ) );
        }

        return reportSet;
    }

    protected List<ReportSet> reportSetDTOListToReportSetList(List<ReportSetDTO> list) {
        if ( list == null ) {
            return null;
        }

        List<ReportSet> list1 = new ArrayList<ReportSet>( list.size() );
        for ( ReportSetDTO reportSetDTO : list ) {
            list1.add( reportSetDTOToReportSet( reportSetDTO ) );
        }

        return list1;
    }

    protected ReportPlugin reportPluginDTOToReportPlugin(ReportPluginDTO reportPluginDTO) {
        if ( reportPluginDTO == null ) {
            return null;
        }

        ReportPlugin reportPlugin = new ReportPlugin();

        reportPlugin.setConfiguration( reportPluginDTO.configuration() );
        reportPlugin.setInherited( reportPluginDTO.inherited() );
        reportPlugin.setArtifactId( reportPluginDTO.artifactId() );
        reportPlugin.setGroupId( reportPluginDTO.groupId() );
        reportPlugin.setReportSets( reportSetDTOListToReportSetList( reportPluginDTO.reportSets() ) );
        reportPlugin.setVersion( reportPluginDTO.version() );

        return reportPlugin;
    }

    protected List<ReportPlugin> reportPluginDTOListToReportPluginList(List<ReportPluginDTO> list) {
        if ( list == null ) {
            return null;
        }

        List<ReportPlugin> list1 = new ArrayList<ReportPlugin>( list.size() );
        for ( ReportPluginDTO reportPluginDTO : list ) {
            list1.add( reportPluginDTOToReportPlugin( reportPluginDTO ) );
        }

        return list1;
    }

    protected Reporting reportingDTOToReporting(ReportingDTO reportingDTO) {
        if ( reportingDTO == null ) {
            return null;
        }

        Reporting reporting = new Reporting();

        reporting.setExcludeDefaults( reportingDTO.excludeDefaults() );
        reporting.setOutputDirectory( reportingDTO.outputDirectory() );
        reporting.setPlugins( reportPluginDTOListToReportPluginList( reportingDTO.plugins() ) );

        return reporting;
    }

    protected PluginExecution pluginExecutionDTOToPluginExecution(PluginExecutionDTO pluginExecutionDTO) {
        if ( pluginExecutionDTO == null ) {
            return null;
        }

        PluginExecution pluginExecution = new PluginExecution();

        pluginExecution.setConfiguration( pluginExecutionDTO.configuration() );
        pluginExecution.setInherited( pluginExecutionDTO.inherited() );
        List<String> list = pluginExecutionDTO.goals();
        if ( list != null ) {
            pluginExecution.setGoals( new ArrayList<String>( list ) );
        }
        pluginExecution.setId( pluginExecutionDTO.id() );
        pluginExecution.setPhase( pluginExecutionDTO.phase() );

        return pluginExecution;
    }

    protected List<PluginExecution> pluginExecutionDTOListToPluginExecutionList(List<PluginExecutionDTO> list) {
        if ( list == null ) {
            return null;
        }

        List<PluginExecution> list1 = new ArrayList<PluginExecution>( list.size() );
        for ( PluginExecutionDTO pluginExecutionDTO : list ) {
            list1.add( pluginExecutionDTOToPluginExecution( pluginExecutionDTO ) );
        }

        return list1;
    }

    protected Plugin pluginDTOToPlugin(PluginDTO pluginDTO) {
        if ( pluginDTO == null ) {
            return null;
        }

        Plugin plugin = new Plugin();

        plugin.setConfiguration( pluginDTO.configuration() );
        plugin.setInherited( pluginDTO.inherited() );
        plugin.setArtifactId( pluginDTO.artifactId() );
        plugin.setDependencies( dependencyDTOListToDependencyList( pluginDTO.dependencies() ) );
        plugin.setExecutions( pluginExecutionDTOListToPluginExecutionList( pluginDTO.executions() ) );
        plugin.setExtensions( pluginDTO.extensions() );
        plugin.setGroupId( pluginDTO.groupId() );
        plugin.setVersion( pluginDTO.version() );

        return plugin;
    }

    protected List<Plugin> pluginDTOListToPluginList(List<PluginDTO> list) {
        if ( list == null ) {
            return null;
        }

        List<Plugin> list1 = new ArrayList<Plugin>( list.size() );
        for ( PluginDTO pluginDTO : list ) {
            list1.add( pluginDTOToPlugin( pluginDTO ) );
        }

        return list1;
    }

    protected PluginManagement pluginManagementDTOToPluginManagement(PluginManagementDTO pluginManagementDTO) {
        if ( pluginManagementDTO == null ) {
            return null;
        }

        PluginManagement pluginManagement = new PluginManagement();

        pluginManagement.setPlugins( pluginDTOListToPluginList( pluginManagementDTO.plugins() ) );

        return pluginManagement;
    }

    protected Resource resourceDTOToResource(ResourceDTO resourceDTO) {
        if ( resourceDTO == null ) {
            return null;
        }

        Resource resource = new Resource();

        List<String> list = resourceDTO.excludes();
        if ( list != null ) {
            resource.setExcludes( new ArrayList<String>( list ) );
        }
        List<String> list1 = resourceDTO.includes();
        if ( list1 != null ) {
            resource.setIncludes( new ArrayList<String>( list1 ) );
        }
        resource.setDirectory( resourceDTO.directory() );
        resource.setFiltering( resourceDTO.filtering() );
        resource.setMergeId( resourceDTO.mergeId() );
        resource.setTargetPath( resourceDTO.targetPath() );

        return resource;
    }

    protected List<Resource> resourceDTOListToResourceList(List<ResourceDTO> list) {
        if ( list == null ) {
            return null;
        }

        List<Resource> list1 = new ArrayList<Resource>( list.size() );
        for ( ResourceDTO resourceDTO : list ) {
            list1.add( resourceDTOToResource( resourceDTO ) );
        }

        return list1;
    }

    protected Extension extensionDTOToExtension(ExtensionDTO extensionDTO) {
        if ( extensionDTO == null ) {
            return null;
        }

        Extension extension = new Extension();

        extension.setArtifactId( extensionDTO.artifactId() );
        extension.setGroupId( extensionDTO.groupId() );
        extension.setVersion( extensionDTO.version() );

        return extension;
    }

    protected List<Extension> extensionDTOListToExtensionList(List<ExtensionDTO> list) {
        if ( list == null ) {
            return null;
        }

        List<Extension> list1 = new ArrayList<Extension>( list.size() );
        for ( ExtensionDTO extensionDTO : list ) {
            list1.add( extensionDTOToExtension( extensionDTO ) );
        }

        return list1;
    }

    protected Build buildDTOToBuild(BuildDTO buildDTO) {
        if ( buildDTO == null ) {
            return null;
        }

        Build build = new Build();

        build.setPlugins( pluginDTOListToPluginList( buildDTO.plugins() ) );
        build.setPluginManagement( pluginManagementDTOToPluginManagement( buildDTO.pluginManagement() ) );
        build.setDefaultGoal( buildDTO.defaultGoal() );
        build.setDirectory( buildDTO.directory() );
        List<String> list1 = buildDTO.filters();
        if ( list1 != null ) {
            build.setFilters( new ArrayList<String>( list1 ) );
        }
        build.setFinalName( buildDTO.finalName() );
        build.setResources( resourceDTOListToResourceList( buildDTO.resources() ) );
        build.setTestResources( resourceDTOListToResourceList( buildDTO.testResources() ) );
        build.setExtensions( extensionDTOListToExtensionList( buildDTO.extensions() ) );
        build.setOutputDirectory( buildDTO.outputDirectory() );
        build.setScriptSourceDirectory( buildDTO.scriptSourceDirectory() );
        build.setSourceDirectory( buildDTO.sourceDirectory() );
        build.setTestOutputDirectory( buildDTO.testOutputDirectory() );
        build.setTestSourceDirectory( buildDTO.testSourceDirectory() );

        return build;
    }

    protected Notifier notifierDTOToNotifier(NotifierDTO notifierDTO) {
        if ( notifierDTO == null ) {
            return null;
        }

        Notifier notifier = new Notifier();

        notifier.setSendOnError( notifierDTO.sendOnError() );
        notifier.setSendOnFailure( notifierDTO.sendOnFailure() );
        notifier.setSendOnSuccess( notifierDTO.sendOnSuccess() );
        notifier.setSendOnWarning( notifierDTO.sendOnWarning() );
        notifier.setType( notifierDTO.type() );

        return notifier;
    }

    protected List<Notifier> notifierDTOListToNotifierList(List<NotifierDTO> list) {
        if ( list == null ) {
            return null;
        }

        List<Notifier> list1 = new ArrayList<Notifier>( list.size() );
        for ( NotifierDTO notifierDTO : list ) {
            list1.add( notifierDTOToNotifier( notifierDTO ) );
        }

        return list1;
    }

    protected CiManagement ciManagementDTOToCiManagement(CiManagementDTO ciManagementDTO) {
        if ( ciManagementDTO == null ) {
            return null;
        }

        CiManagement ciManagement = new CiManagement();

        ciManagement.setNotifiers( notifierDTOListToNotifierList( ciManagementDTO.notifiers() ) );
        ciManagement.setSystem( ciManagementDTO.system() );
        ciManagement.setUrl( ciManagementDTO.url() );

        return ciManagement;
    }

    protected Contributor contributerDTOToContributor(ContributerDTO contributerDTO) {
        if ( contributerDTO == null ) {
            return null;
        }

        Contributor contributor = new Contributor();

        contributor.setEmail( contributerDTO.email() );
        contributor.setName( contributerDTO.name() );
        contributor.setOrganization( contributerDTO.organization() );
        contributor.setOrganizationUrl( contributerDTO.organizationUrl() );
        Properties properties = contributerDTO.properties();
        if ( properties != null ) {
            contributor.setProperties( new Properties( properties ) );
        }
        List<String> list = contributerDTO.roles();
        if ( list != null ) {
            contributor.setRoles( new ArrayList<String>( list ) );
        }
        contributor.setTimezone( contributerDTO.timezone() );
        contributor.setUrl( contributerDTO.url() );

        return contributor;
    }

    protected List<Contributor> contributerDTOListToContributorList(List<ContributerDTO> list) {
        if ( list == null ) {
            return null;
        }

        List<Contributor> list1 = new ArrayList<Contributor>( list.size() );
        for ( ContributerDTO contributerDTO : list ) {
            list1.add( contributerDTOToContributor( contributerDTO ) );
        }

        return list1;
    }

    protected Developer developerDTOToDeveloper(DeveloperDTO developerDTO) {
        if ( developerDTO == null ) {
            return null;
        }

        Developer developer = new Developer();

        developer.setEmail( developerDTO.email() );
        developer.setName( developerDTO.name() );
        developer.setOrganization( developerDTO.organization() );
        developer.setOrganizationUrl( developerDTO.organizationUrl() );
        Properties properties = developerDTO.properties();
        if ( properties != null ) {
            developer.setProperties( new Properties( properties ) );
        }
        List<String> list = developerDTO.roles();
        if ( list != null ) {
            developer.setRoles( new ArrayList<String>( list ) );
        }
        developer.setTimezone( developerDTO.timezone() );
        developer.setUrl( developerDTO.url() );
        developer.setId( developerDTO.id() );

        return developer;
    }

    protected List<Developer> developerDTOListToDeveloperList(List<DeveloperDTO> list) {
        if ( list == null ) {
            return null;
        }

        List<Developer> list1 = new ArrayList<Developer>( list.size() );
        for ( DeveloperDTO developerDTO : list ) {
            list1.add( developerDTOToDeveloper( developerDTO ) );
        }

        return list1;
    }

    protected IssueManagement issueManagementDTOToIssueManagement(IssueManagementDTO issueManagementDTO) {
        if ( issueManagementDTO == null ) {
            return null;
        }

        IssueManagement issueManagement = new IssueManagement();

        issueManagement.setSystem( issueManagementDTO.system() );
        issueManagement.setUrl( issueManagementDTO.url() );

        return issueManagement;
    }

    protected License licenseDTOToLicense(LicenseDTO licenseDTO) {
        if ( licenseDTO == null ) {
            return null;
        }

        License license = new License();

        license.setComments( licenseDTO.comments() );
        license.setDistribution( licenseDTO.distribution() );
        license.setName( licenseDTO.name() );
        license.setUrl( licenseDTO.url() );

        return license;
    }

    protected List<License> licenseDTOListToLicenseList(List<LicenseDTO> list) {
        if ( list == null ) {
            return null;
        }

        List<License> list1 = new ArrayList<License>( list.size() );
        for ( LicenseDTO licenseDTO : list ) {
            list1.add( licenseDTOToLicense( licenseDTO ) );
        }

        return list1;
    }

    protected MailingList mailingListDTOToMailingList(MailingListDTO mailingListDTO) {
        if ( mailingListDTO == null ) {
            return null;
        }

        MailingList mailingList = new MailingList();

        mailingList.setArchive( mailingListDTO.archive() );
        mailingList.setName( mailingListDTO.name() );
        List<String> list = mailingListDTO.otherArchives();
        if ( list != null ) {
            mailingList.setOtherArchives( new ArrayList<String>( list ) );
        }
        mailingList.setPost( mailingListDTO.post() );
        mailingList.setSubscribe( mailingListDTO.subscribe() );
        mailingList.setUnsubscribe( mailingListDTO.unsubscribe() );

        return mailingList;
    }

    protected List<MailingList> mailingListDTOListToMailingListList(List<MailingListDTO> list) {
        if ( list == null ) {
            return null;
        }

        List<MailingList> list1 = new ArrayList<MailingList>( list.size() );
        for ( MailingListDTO mailingListDTO : list ) {
            list1.add( mailingListDTOToMailingList( mailingListDTO ) );
        }

        return list1;
    }

    protected Organization organizationDTOToOrganization(OrganizationDTO organizationDTO) {
        if ( organizationDTO == null ) {
            return null;
        }

        Organization organization = new Organization();

        organization.setName( organizationDTO.name() );
        organization.setUrl( organizationDTO.url() );

        return organization;
    }

    protected Parent parentDTOToParent(ParentDTO parentDTO) {
        if ( parentDTO == null ) {
            return null;
        }

        Parent parent = new Parent();

        parent.setArtifactId( parentDTO.artifactId() );
        parent.setGroupId( parentDTO.groupId() );
        parent.setRelativePath( parentDTO.relativePath() );
        parent.setVersion( parentDTO.version() );

        return parent;
    }

    protected Prerequisites prerequisitesDTOToPrerequisites(PrerequisitesDTO prerequisitesDTO) {
        if ( prerequisitesDTO == null ) {
            return null;
        }

        Prerequisites prerequisites = new Prerequisites();

        prerequisites.setMaven( prerequisitesDTO.maven() );

        return prerequisites;
    }

    protected ActivationFile activationFileDTOToActivationFile(ActivationFileDTO activationFileDTO) {
        if ( activationFileDTO == null ) {
            return null;
        }

        ActivationFile activationFile = new ActivationFile();

        activationFile.setExists( activationFileDTO.exists() );
        activationFile.setMissing( activationFileDTO.missing() );

        return activationFile;
    }

    protected ActivationOS activationOS_DTOToActivationOS(ActivationOS_DTO activationOS_DTO) {
        if ( activationOS_DTO == null ) {
            return null;
        }

        ActivationOS activationOS = new ActivationOS();

        activationOS.setArch( activationOS_DTO.arch() );
        activationOS.setFamily( activationOS_DTO.family() );
        activationOS.setName( activationOS_DTO.name() );
        activationOS.setVersion( activationOS_DTO.version() );

        return activationOS;
    }

    protected ActivationProperty activationPropertyDTOToActivationProperty(ActivationPropertyDTO activationPropertyDTO) {
        if ( activationPropertyDTO == null ) {
            return null;
        }

        ActivationProperty activationProperty = new ActivationProperty();

        activationProperty.setName( activationPropertyDTO.name() );
        activationProperty.setValue( activationPropertyDTO.value() );

        return activationProperty;
    }

    protected Activation activationDTOToActivation(ActivationDTO activationDTO) {
        if ( activationDTO == null ) {
            return null;
        }

        Activation activation = new Activation();

        activation.setActiveByDefault( activationDTO.activeByDefault() );
        activation.setFile( activationFileDTOToActivationFile( activationDTO.file() ) );
        activation.setJdk( activationDTO.jdk() );
        activation.setOs( activationOS_DTOToActivationOS( activationDTO.os() ) );
        activation.setProperty( activationPropertyDTOToActivationProperty( activationDTO.property() ) );

        return activation;
    }

    protected BuildBase buildBaseDTOToBuildBase(BuildBaseDTO buildBaseDTO) {
        if ( buildBaseDTO == null ) {
            return null;
        }

        BuildBase buildBase = new BuildBase();

        buildBase.setPlugins( pluginDTOListToPluginList( buildBaseDTO.plugins() ) );
        buildBase.setPluginManagement( pluginManagementDTOToPluginManagement( buildBaseDTO.pluginManagement() ) );
        buildBase.setDefaultGoal( buildBaseDTO.defaultGoal() );
        buildBase.setDirectory( buildBaseDTO.directory() );
        List<String> list1 = buildBaseDTO.filters();
        if ( list1 != null ) {
            buildBase.setFilters( new ArrayList<String>( list1 ) );
        }
        buildBase.setFinalName( buildBaseDTO.finalName() );
        buildBase.setResources( resourceDTOListToResourceList( buildBaseDTO.resources() ) );
        buildBase.setTestResources( resourceDTOListToResourceList( buildBaseDTO.testResources() ) );

        return buildBase;
    }

    protected Profile profileDTOToProfile(ProfileDTO profileDTO) {
        if ( profileDTO == null ) {
            return null;
        }

        Profile profile = new Profile();

        profile.setDependencies( dependencyDTOListToDependencyList( profileDTO.dependencies() ) );
        profile.setDependencyManagement( dependencyManagementDTOToDependencyManagement( profileDTO.dependencyManagement() ) );
        profile.setDistributionManagement( distributionManagementDTOToDistributionManagement( profileDTO.distributionManagement() ) );
        List<String> list1 = profileDTO.modules();
        if ( list1 != null ) {
            profile.setModules( new ArrayList<String>( list1 ) );
        }
        profile.setPluginRepositories( repositoryDTOListToRepositoryList( profileDTO.pluginRepositories() ) );
        Properties properties = profileDTO.properties();
        if ( properties != null ) {
            profile.setProperties( new Properties( properties ) );
        }
        profile.setReporting( reportingDTOToReporting( profileDTO.reporting() ) );
        profile.setRepositories( repositoryDTOListToRepositoryList( profileDTO.repositories() ) );
        profile.setActivation( activationDTOToActivation( profileDTO.activation() ) );
        profile.setBuild( buildBaseDTOToBuildBase( profileDTO.build() ) );
        profile.setId( profileDTO.id() );

        return profile;
    }

    protected List<Profile> profileDTOListToProfileList(List<ProfileDTO> list) {
        if ( list == null ) {
            return null;
        }

        List<Profile> list1 = new ArrayList<Profile>( list.size() );
        for ( ProfileDTO profileDTO : list ) {
            list1.add( profileDTOToProfile( profileDTO ) );
        }

        return list1;
    }

    protected Scm scmDTOToScm(ScmDTO scmDTO) {
        if ( scmDTO == null ) {
            return null;
        }

        Scm scm = new Scm();

        scm.setChildScmConnectionInheritAppendPath( scmDTO.childScmConnectionInheritAppendPath() );
        scm.setChildScmDeveloperConnectionInheritAppendPath( scmDTO.childScmDeveloperConnectionInheritAppendPath() );
        scm.setChildScmUrlInheritAppendPath( scmDTO.childScmUrlInheritAppendPath() );
        scm.setConnection( scmDTO.connection() );
        scm.setDeveloperConnection( scmDTO.developerConnection() );
        scm.setTag( scmDTO.tag() );
        scm.setUrl( scmDTO.url() );

        return scm;
    }
}
