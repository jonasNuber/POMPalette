package de.nuberjonas.pompalette.infrastructure.parsing.projectparsingmavenimpl.factory;

import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.activation.ActivationDTO;
import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.activation.ActivationFileDTO;
import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.activation.ActivationOsDTO;
import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.activation.ActivationPropertyDTO;
import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.build.BuildBaseDTO;
import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.build.BuildDTO;
import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.build.PluginConfigurationDTO;
import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.build.PluginContainerDTO;
import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.build.resource.FileSetDTO;
import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.build.resource.PatternSetDTO;
import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.build.resource.ResourceDTO;
import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.contributing.*;
import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.dependency.DependencyDTO;
import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.dependency.DependencyManagementDTO;
import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.dependency.ExclusionDTO;
import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.input.InputLocationDTO;
import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.input.InputSourceDTO;
import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.management.*;
import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.plugin.ConfigurationContainerDTO;
import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.plugin.PluginDTO;
import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.plugin.PluginExecutionDTO;
import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.plugin.PluginManagementDTO;
import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.project.*;
import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.reporting.ReportPluginDTO;
import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.reporting.ReportSetDTO;
import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.reporting.ReportingDTO;
import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.repository.DeploymentRepositoryDTO;
import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.repository.RepositoryBaseDTO;
import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.repository.RepositoryDTO;
import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.repository.RepositoryPolicyDTO;
import de.nuberjonas.pompalette.mapping.mappingapi.exceptions.MappingException;
import de.nuberjonas.pompalette.mapping.mappingapi.mapper.ListMapper;
import de.nuberjonas.pompalette.mapping.mappingapi.mapper.MapperService;
import org.apache.maven.model.*;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class MavenProjectFactory implements MapperService<Model, ProjectDTO> {

    private static final String GROUP_ID = "groupId";
    private static final String ARTIFACT_ID = "artifactId";
    private static final String VERSION = "version";
    private static final String CONFIGURATION = "configuration";

    @Override
    public ProjectDTO mapToDestination(Model toMap) {
        return new ProjectDTO(
                createModelBaseDTO(toMap),
                toMap.getModelVersion(),
                createParentDTO(toMap.getParent()),
                toMap.getGroupId(),
                toMap.getArtifactId(),
                toMap.getVersion(),
                toMap.getPackaging(),
                toMap.getName(),
                toMap.getDescription(),
                toMap.getUrl(),
                toMap.isChildProjectUrlInheritAppendPath(),
                toMap.getInceptionYear(),
                createOrganizationDTO(toMap.getOrganization()),
                ListMapper.mapList(toMap.getLicenses(), this::createLicenseDTO),
                ListMapper.mapList(toMap.getDevelopers(), this::createDeveloperDTO),
                ListMapper.mapList(toMap.getContributors(), this::createContributorDTO),
                ListMapper.mapList(toMap.getMailingLists(), this::createMailingListDTO),
                createPrerequisitesDTO(toMap.getPrerequisites()),
                createScmDTO(toMap.getScm()),
                createIssueManagementDTO(toMap.getIssueManagement()),
                createCiManagementDTO(toMap.getCiManagement()),
                createBuildDTO(toMap.getBuild()),
                ListMapper.mapList(toMap.getProfiles(), this::createProfileDTO),
                toMap.getModelEncoding()
        );
    }

    private ModelBaseDTO createModelBaseDTO(ModelBase modelBase){
        return new ModelBaseDTO(
                modelBase.getModules(),
                createDistributionManagementDTO(modelBase.getDistributionManagement()),
                modelBase.getProperties(),
                createDependencyManagementDTO(modelBase.getDependencyManagement()),
                ListMapper.mapList(modelBase.getDependencies(), this::createDependencyDTO),
                ListMapper.mapList(modelBase.getRepositories(), this::createRepositoryDTO),
                ListMapper.mapList(modelBase.getPluginRepositories(), this::createRepositoryDTO),
                createReportingDTO(modelBase.getReporting()),
                createInputLocationDTOMap(modelBase),
                createInputLocationDTO(modelBase.getLocation("")),
                createInputLocationDTO(modelBase.getLocation("modules")),
                createInputLocationDTO(modelBase.getLocation("distributionManagement")),
                createInputLocationDTO(modelBase.getLocation("properties")),
                createInputLocationDTO(modelBase.getLocation("dependencyManagement")),
                createInputLocationDTO(modelBase.getLocation("dependencies")),
                createInputLocationDTO(modelBase.getLocation("repositories")),
                createInputLocationDTO(modelBase.getLocation("pluginRepositories")),
                createInputLocationDTO(modelBase.getLocation("reporting")));
    }

    private DistributionManagementDTO createDistributionManagementDTO(DistributionManagement distributionManagement){
        return new DistributionManagementDTO(
                createDeploymentRepositoryDTO(distributionManagement.getRepository()),
                createDeploymentRepositoryDTO(distributionManagement.getSnapshotRepository()),
                createSiteDTO(distributionManagement.getSite()),
                distributionManagement.getDownloadUrl(),
                createRelocationDTO(distributionManagement.getRelocation()),
                distributionManagement.getStatus(),
                createInputLocationDTOMap(distributionManagement),
                createInputLocationDTO(distributionManagement.getLocation("")),
                createInputLocationDTO(distributionManagement.getLocation("repository")),
                createInputLocationDTO(distributionManagement.getLocation("snapshotRepository")),
                createInputLocationDTO(distributionManagement.getLocation("site")),
                createInputLocationDTO(distributionManagement.getLocation("downloadUrl")),
                createInputLocationDTO(distributionManagement.getLocation("relocation")),
                createInputLocationDTO(distributionManagement.getLocation("status"))
        );
    }

    private DeploymentRepositoryDTO createDeploymentRepositoryDTO(DeploymentRepository deploymentRepository){
        return new DeploymentRepositoryDTO(
                createRepositoryDTO(deploymentRepository),
                deploymentRepository.isUniqueVersion()
        );
    }

    private RepositoryDTO createRepositoryDTO(Repository repository){
        return new RepositoryDTO(
                createRepositoryBaseDTO(repository),
                createRepositoryPolicyDTO(repository.getReleases()),
                createRepositoryPolicyDTO(repository.getSnapshots())
        );
    }

    private RepositoryBaseDTO createRepositoryBaseDTO(RepositoryBase repositoryBase){
        return new RepositoryBaseDTO(
                repositoryBase.getId(),
                repositoryBase.getName(),
                repositoryBase.getUrl(),
                repositoryBase.getLayout(),
                createInputLocationDTOMap(repositoryBase),
                createInputLocationDTO(repositoryBase.getLocation("")),
                createInputLocationDTO(repositoryBase.getLocation("id")),
                createInputLocationDTO(repositoryBase.getLocation("name")),
                createInputLocationDTO(repositoryBase.getLocation("url")),
                createInputLocationDTO(repositoryBase.getLocation("layout"))
        );
    }

    @SuppressWarnings("unchecked")
    private Map<Object, InputLocationDTO> createInputLocationDTOMap(Object object) {
        try {
            Field field = getLocationsField(object.getClass());
            field.setAccessible(true);

            return createInputLocationDTOMap((Map<Object, InputLocation>)field.get(object));

        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new MappingException("Could not map locations for DTO generation", e);
        }
    }

    private Field getLocationsField(Class<?> clazz) throws NoSuchFieldException {
        while (clazz != null) {
            try {
                return clazz.getDeclaredField("locations");
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass();
            }
        }

        throw new NoSuchFieldException("The locations field does not exist!");
    }

    private Map<Object, InputLocationDTO> createInputLocationDTOMap(Map<Object, InputLocation> inputLocationMap) {
        return Optional.ofNullable(inputLocationMap)
                .map(map -> map.entrySet()
                        .stream()
                        .collect(Collectors.toMap(Map.Entry::getKey,
                                entry -> createInputLocationDTO(entry.getValue()),
                                (existing, replacement) -> existing,
                                LinkedHashMap::new)))
                .orElse(null);
    }

    private InputLocationDTO createInputLocationDTO(InputLocation inputLocation){
        return new InputLocationDTO(
                inputLocation.getLineNumber(),
                inputLocation.getColumnNumber(),
                createInputSourceDTO(inputLocation.getSource()),
                createInputLocationDTOMap(inputLocation.getLocations()),
                inputLocation.getLocation("") == null ? null : createInputLocationDTO(inputLocation.getLocation(""))
        );
    }

    private InputSourceDTO createInputSourceDTO(InputSource inputSource) {
        return new InputSourceDTO(
                inputSource.getModelId(),
                inputSource.getLocation()
        );
    }

    private RepositoryPolicyDTO createRepositoryPolicyDTO(RepositoryPolicy repositoryPolicy){
        return new RepositoryPolicyDTO(
                repositoryPolicy.isEnabled(),
                repositoryPolicy.getUpdatePolicy(),
                repositoryPolicy.getChecksumPolicy(),
                createInputLocationDTOMap(repositoryPolicy),
                createInputLocationDTO(repositoryPolicy.getLocation("")),
                createInputLocationDTO(repositoryPolicy.getLocation("enabled")),
                createInputLocationDTO(repositoryPolicy.getLocation("updatePolicy")),
                createInputLocationDTO(repositoryPolicy.getLocation("checksumPolicy"))
        );
    }

    private SiteDTO createSiteDTO(Site site){
        return new SiteDTO(
                site.getId(),
                site.getName(),
                site.getUrl(),
                site.isChildSiteUrlInheritAppendPath(),
                createInputLocationDTOMap(site),
                createInputLocationDTO(site.getLocation("")),
                createInputLocationDTO(site.getLocation("id")),
                createInputLocationDTO(site.getLocation("name")),
                createInputLocationDTO(site.getLocation("url")),
                createInputLocationDTO(site.getLocation("childSiteUrlInheritAppendPath"))
        );
    }

    private RelocationDTO createRelocationDTO(Relocation relocation){
        return new RelocationDTO(
                relocation.getGroupId(),
                relocation.getArtifactId(),
                relocation.getVersion(),
                relocation.getMessage(),
                createInputLocationDTOMap(relocation),
                createInputLocationDTO(relocation.getLocation("")),
                createInputLocationDTO(relocation.getLocation(GROUP_ID)),
                createInputLocationDTO(relocation.getLocation(ARTIFACT_ID)),
                createInputLocationDTO(relocation.getLocation(VERSION)),
                createInputLocationDTO(relocation.getLocation("message"))
        );
    }

    private DependencyManagementDTO createDependencyManagementDTO(DependencyManagement dependencyManagement){
        return new DependencyManagementDTO(
                ListMapper.mapList(dependencyManagement.getDependencies(), this::createDependencyDTO),
                createInputLocationDTOMap(dependencyManagement),
                createInputLocationDTO(dependencyManagement.getLocation("")),
                createInputLocationDTO(dependencyManagement.getLocation("dependencies"))
        );
    }

    private ReportingDTO createReportingDTO(Reporting reporting){
        return new ReportingDTO(
                reporting.isExcludeDefaults(),
                reporting.getOutputDirectory(),
                ListMapper.mapList(reporting.getPlugins(), this::createReportPluginDTO),
                createInputLocationDTOMap(reporting),
                createInputLocationDTO(reporting.getLocation("")),
                createInputLocationDTO(reporting.getLocation("excludeDefaults")),
                createInputLocationDTO(reporting.getLocation("outputDirectory")),
                createInputLocationDTO(reporting.getLocation("plugins"))
        );
    }

    private ReportPluginDTO createReportPluginDTO(ReportPlugin reportPlugin){
        return new ReportPluginDTO(
                createConfigurationContainerDTO(reportPlugin),
                reportPlugin.getGroupId(),
                reportPlugin.getArtifactId(),
                reportPlugin.getVersion(),
                ListMapper.mapList(reportPlugin.getReportSets(), this::createReportSetDTO)
        );
    }

    private ReportSetDTO createReportSetDTO(ReportSet reportSet){
        return new ReportSetDTO(
                createConfigurationContainerDTO(reportSet),
                reportSet.getId(),
                reportSet.getReports()
        );
    }

    private ParentDTO createParentDTO(Parent parent){
        return new ParentDTO(
                parent.getGroupId(),
                parent.getArtifactId(),
                parent.getVersion(),
                parent.getRelativePath(),
                createInputLocationDTOMap(parent),
                createInputLocationDTO(parent.getLocation("")),
                createInputLocationDTO(parent.getLocation(GROUP_ID)),
                createInputLocationDTO(parent.getLocation(ARTIFACT_ID)),
                createInputLocationDTO(parent.getLocation(VERSION)),
                createInputLocationDTO(parent.getLocation("relativePath"))
        );
    }

    private OrganizationDTO createOrganizationDTO(Organization organization){
        return new OrganizationDTO(
                organization.getName(),
                organization.getUrl(),
                createInputLocationDTOMap(organization),
                createInputLocationDTO(organization.getLocation("")),
                createInputLocationDTO(organization.getLocation("name")),
                createInputLocationDTO(organization.getLocation("url")));
    }

    private LicenseDTO createLicenseDTO(License license){
        return new LicenseDTO(
                license.getName(),
                license.getUrl(),
                license.getDistribution(),
                license.getComments(),
                createInputLocationDTOMap(license),
                createInputLocationDTO(license.getLocation("")),
                createInputLocationDTO(license.getLocation("name")),
                createInputLocationDTO(license.getLocation("url")),
                createInputLocationDTO(license.getLocation("distribution")),
                createInputLocationDTO(license.getLocation("comments")));
    }

    private DeveloperDTO createDeveloperDTO(Developer developer){
        return new DeveloperDTO(
                createContributorDTO(developer),
                developer.getId());
    }

    private ContributorDTO createContributorDTO(Contributor contributor){
        return new ContributorDTO(
                contributor.getName(),
                contributor.getEmail(),
                contributor.getUrl(),
                contributor.getOrganization(),
                contributor.getOrganizationUrl(),
                contributor.getRoles(),
                contributor.getTimezone(),
                contributor.getProperties(),
                createInputLocationDTOMap(contributor),
                createInputLocationDTO(contributor.getLocation("")),
                createInputLocationDTO(contributor.getLocation("name")),
                createInputLocationDTO(contributor.getLocation("email")),
                createInputLocationDTO(contributor.getLocation("url")),
                createInputLocationDTO(contributor.getLocation("organization")),
                createInputLocationDTO(contributor.getLocation("organizationUrl")),
                createInputLocationDTO(contributor.getLocation("roles")),
                createInputLocationDTO(contributor.getLocation("timezone")),
                createInputLocationDTO(contributor.getLocation(CONFIGURATION)));
    }

    private MailingListDTO createMailingListDTO(MailingList mailingList){
        return new MailingListDTO(
                mailingList.getName(),
                mailingList.getSubscribe(),
                mailingList.getUnsubscribe(),
                mailingList.getPost(),
                mailingList.getArchive(),
                mailingList.getOtherArchives(),
                createInputLocationDTOMap(mailingList),
                createInputLocationDTO(mailingList.getLocation("")),
                createInputLocationDTO(mailingList.getLocation("name")),
                createInputLocationDTO(mailingList.getLocation("subscribe")),
                createInputLocationDTO(mailingList.getLocation("unsubscribe")),
                createInputLocationDTO(mailingList.getLocation("post")),
                createInputLocationDTO(mailingList.getLocation("archive")),
                createInputLocationDTO(mailingList.getLocation("otherArchives")));
    }

    private PrerequisitesDTO createPrerequisitesDTO(Prerequisites prerequisites){
        return new PrerequisitesDTO(
                prerequisites.getMaven(),
                createInputLocationDTOMap(prerequisites),
                createInputLocationDTO(prerequisites.getLocation("")),
                createInputLocationDTO(prerequisites.getLocation("maven")));
    }

    private ScmDTO createScmDTO(Scm scm){
        return new ScmDTO(
                scm.getConnection(),
                scm.getDeveloperConnection(),
                scm.getTag(),
                scm.getUrl(),
                scm.isChildScmConnectionInheritAppendPath(),
                scm.isChildScmDeveloperConnectionInheritAppendPath(),
                scm.isChildScmUrlInheritAppendPath(),
                createInputLocationDTOMap(scm),
                createInputLocationDTO(scm.getLocation("")),
                createInputLocationDTO(scm.getLocation("connection")),
                createInputLocationDTO(scm.getLocation("developerConnection")),
                createInputLocationDTO(scm.getLocation("tag")),
                createInputLocationDTO(scm.getLocation("url")),
                createInputLocationDTO(scm.getLocation("childScmConnectionInheritAppendPath")),
                createInputLocationDTO(scm.getLocation("childScmDeveloperConnectionInheritAppendPath")),
                createInputLocationDTO(scm.getLocation("childScmUrlInheritAppendPath")));
    }

    private IssueManagementDTO createIssueManagementDTO(IssueManagement issueManagement){
        return new IssueManagementDTO(
                issueManagement.getSystem(),
                issueManagement.getUrl(),
                createInputLocationDTOMap(issueManagement),
                createInputLocationDTO(issueManagement.getLocation("")),
                createInputLocationDTO(issueManagement.getLocation("system")),
                createInputLocationDTO(issueManagement.getLocation("url")));
    }

    private CiManagementDTO createCiManagementDTO(CiManagement ciManagement){
        return new CiManagementDTO(
                ciManagement.getSystem(),
                ciManagement.getUrl(),
                ListMapper.mapList(ciManagement.getNotifiers(), this::createNotifierDTO),
                createInputLocationDTOMap(ciManagement),
                createInputLocationDTO(ciManagement.getLocation("")),
                createInputLocationDTO(ciManagement.getLocation("system")),
                createInputLocationDTO(ciManagement.getLocation("url")),
                createInputLocationDTO(ciManagement.getLocation("notifiers")));
    }

    private NotifierDTO createNotifierDTO(Notifier notifier){
        return new NotifierDTO(
                notifier.getType(),
                notifier.isSendOnError(),
                notifier.isSendOnFailure(),
                notifier.isSendOnSuccess(),
                notifier.isSendOnWarning(),
                notifier.getConfiguration(),
                createInputLocationDTOMap(notifier),
                createInputLocationDTO(notifier.getLocation("")),
                createInputLocationDTO(notifier.getLocation("type")),
                createInputLocationDTO(notifier.getLocation("sendOnError")),
                createInputLocationDTO(notifier.getLocation("sendOnFailure")),
                createInputLocationDTO(notifier.getLocation("sendOnSuccess")),
                createInputLocationDTO(notifier.getLocation("sendOnWarning")),
                createInputLocationDTO(notifier.getLocation("address")),
                createInputLocationDTO(notifier.getLocation(CONFIGURATION)));
    }

    private BuildDTO createBuildDTO(Build build){
        return new BuildDTO(
                createBuildBaseDTO(build),
                build.getSourceDirectory(),
                build.getScriptSourceDirectory(),
                build.getTestSourceDirectory(),
                build.getOutputDirectory(),
                build.getTestOutputDirectory(),
                ListMapper.mapList(build.getExtensions(), this::createExtensionDTO)
        );
    }

    private BuildBaseDTO createBuildBaseDTO(BuildBase buildBase){
        return new BuildBaseDTO(
                createPluginConfigurationDTO(buildBase),
                buildBase.getDefaultGoal(),
                ListMapper.mapList(buildBase.getResources(), this::createResourceDTO),
                ListMapper.mapList(buildBase.getTestResources(), this::createResourceDTO),
                buildBase.getDirectory(),
                buildBase.getFinalName(),
                buildBase.getFilters()
        );
    }


    private PluginConfigurationDTO createPluginConfigurationDTO(PluginConfiguration pluginConfiguration){
        return new PluginConfigurationDTO(
                createPluginContainerDTO(pluginConfiguration),
                createPluginManagementDTO(pluginConfiguration.getPluginManagement())
        );
    }

    private PluginContainerDTO createPluginContainerDTO(PluginContainer pluginContainer){
        return new PluginContainerDTO(
                ListMapper.mapList(pluginContainer.getPlugins(), this::createPluginDTO),
                createInputLocationDTOMap(pluginContainer),
                createInputLocationDTO(pluginContainer.getLocation("")),
                createInputLocationDTO(pluginContainer.getLocation("plugins"))
        );
    }

    private PluginDTO createPluginDTO(Plugin plugin){
        return new PluginDTO(
                createConfigurationContainerDTO(plugin),
                plugin.getGroupId(),
                plugin.getArtifactId(),
                plugin.getVersion(),
                plugin.getExtensions(),
                ListMapper.mapList(plugin.getExecutions(), this::createPluginExecutionDTO),
                ListMapper.mapList(plugin.getDependencies(), this::createDependencyDTO));
    }

    private ConfigurationContainerDTO createConfigurationContainerDTO(ConfigurationContainer configurationContainer){
        return new ConfigurationContainerDTO(
                configurationContainer.isInherited(),
                configurationContainer.getConfiguration(),
                createInputLocationDTOMap(configurationContainer),
                createInputLocationDTO(configurationContainer.getLocation("")),
                createInputLocationDTO(configurationContainer.getLocation("inherited")),
                createInputLocationDTO(configurationContainer.getLocation(CONFIGURATION)),
                configurationContainer.isInheritanceApplied()
        );
    }

    private PluginExecutionDTO createPluginExecutionDTO(PluginExecution pluginExecution){
        return new PluginExecutionDTO(
                createConfigurationContainerDTO(pluginExecution),
                pluginExecution.getId(),
                pluginExecution.getPhase(),
                pluginExecution.getGoals());
    }

    private DependencyDTO createDependencyDTO(Dependency dependency){
        return new DependencyDTO(
                dependency.getGroupId(),
                dependency.getArtifactId(),
                dependency.getVersion(),
                dependency.getType(),
                dependency.getClassifier(),
                dependency.getScope(),
                dependency.getSystemPath(),
                ListMapper.mapList(dependency.getExclusions(), this::createExclusionDTO),
                dependency.isOptional(),
                createInputLocationDTOMap(dependency),
                createInputLocationDTO(dependency.getLocation("")),
                createInputLocationDTO(dependency.getLocation(GROUP_ID)),
                createInputLocationDTO(dependency.getLocation(ARTIFACT_ID)),
                createInputLocationDTO(dependency.getLocation(VERSION)),
                createInputLocationDTO(dependency.getLocation("type")),
                createInputLocationDTO(dependency.getLocation("classifier")),
                createInputLocationDTO(dependency.getLocation("scope")),
                createInputLocationDTO(dependency.getLocation("systemPath")),
                createInputLocationDTO(dependency.getLocation("exclusions")),
                createInputLocationDTO(dependency.getLocation("optional"))
        );
    }

    private ExclusionDTO createExclusionDTO(Exclusion exclusion){
        return new ExclusionDTO(
                exclusion.getGroupId(),
                exclusion.getArtifactId(),
                createInputLocationDTOMap(exclusion),
                createInputLocationDTO(exclusion.getLocation("")),
                createInputLocationDTO(exclusion.getLocation(GROUP_ID)),
                createInputLocationDTO(exclusion.getLocation(ARTIFACT_ID)));
    }

    private PluginManagementDTO createPluginManagementDTO(PluginManagement pluginManagement){
        return new PluginManagementDTO(
                createPluginContainerDTO(pluginManagement)
        );
    }

    private ResourceDTO createResourceDTO(Resource resource){
        return new ResourceDTO(
                createFileSetDTO(resource),
                resource.getTargetPath(),
                resource.isFiltering(),
                resource.getMergeId()
        );
    }

    private FileSetDTO createFileSetDTO(FileSet fileSet){
        return new FileSetDTO(
                createPatternSetDTO(fileSet),
                fileSet.getDirectory()
        );
    }

    private PatternSetDTO createPatternSetDTO(PatternSet patternSet){
        return new PatternSetDTO(
                patternSet.getIncludes(),
                patternSet.getExcludes(),
                createInputLocationDTOMap(patternSet),
                createInputLocationDTO(patternSet.getLocation("")),
                createInputLocationDTO(patternSet.getLocation("includes")),
                createInputLocationDTO(patternSet.getLocation("excludes"))
        );
    }

    private ExtensionDTO createExtensionDTO(Extension extension){
        return new ExtensionDTO(
                extension.getGroupId(),
                extension.getArtifactId(),
                extension.getVersion(),
                createInputLocationDTOMap(extension),
                createInputLocationDTO(extension.getLocation("")),
                createInputLocationDTO(extension.getLocation(GROUP_ID)),
                createInputLocationDTO(extension.getLocation(ARTIFACT_ID)),
                createInputLocationDTO(extension.getLocation(VERSION))
        );
    }

    private ProfileDTO createProfileDTO(Profile profile){
        return new ProfileDTO(
                createModelBaseDTO(profile),
                profile.getId(),
                createActivationDTO(profile.getActivation()),
                createBuildBaseDTO(profile.getBuild())
        );
    }

    private ActivationDTO createActivationDTO(Activation activation){
        return new ActivationDTO(
                activation.isActiveByDefault(),
                activation.getJdk(),
                createActivationOsDTO(activation.getOs()),
                createActivationPropertyDTO(activation.getProperty()),
                createActivationFileDTO(activation.getFile()),
                createInputLocationDTOMap(activation),
                createInputLocationDTO(activation.getLocation("")),
                createInputLocationDTO(activation.getLocation("activeByDefault")),
                createInputLocationDTO(activation.getLocation("jdk")),
                createInputLocationDTO(activation.getLocation("os")),
                createInputLocationDTO(activation.getLocation("property")),
                createInputLocationDTO(activation.getLocation("file"))
        );
    }

    private ActivationOsDTO createActivationOsDTO(ActivationOS activationOS){
        return new ActivationOsDTO(
                activationOS.getName(),
                activationOS.getFamily(),
                activationOS.getArch(),
                activationOS.getVersion(),
                createInputLocationDTOMap(activationOS),
                createInputLocationDTO(activationOS.getLocation("")),
                createInputLocationDTO(activationOS.getLocation("name")),
                createInputLocationDTO(activationOS.getLocation("family")),
                createInputLocationDTO(activationOS.getLocation("arch")),
                createInputLocationDTO(activationOS.getLocation(VERSION))
        );
    }

    private ActivationPropertyDTO createActivationPropertyDTO(ActivationProperty activationProperty){
        return new ActivationPropertyDTO(
                activationProperty.getName(),
                activationProperty.getValue(),
                createInputLocationDTOMap(activationProperty),
                createInputLocationDTO(activationProperty.getLocation("")),
                createInputLocationDTO(activationProperty.getLocation("name")),
                createInputLocationDTO(activationProperty.getLocation("value"))
        );
    }

    private ActivationFileDTO createActivationFileDTO(ActivationFile activationFile){
        return new ActivationFileDTO(
                activationFile.getMissing(),
                activationFile.getExists(),
                createInputLocationDTOMap(activationFile),
                createInputLocationDTO(activationFile.getLocation("")),
                createInputLocationDTO(activationFile.getLocation("missing")),
                createInputLocationDTO(activationFile.getLocation("exists"))
        );
    }

    @Override
    public Model mapToSource(ProjectDTO toMap) {
        return null;
    }
}
