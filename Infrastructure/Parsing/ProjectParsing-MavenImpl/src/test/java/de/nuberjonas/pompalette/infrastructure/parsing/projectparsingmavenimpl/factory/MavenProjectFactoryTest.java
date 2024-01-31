package de.nuberjonas.pompalette.infrastructure.parsing.projectparsingmavenimpl.factory;

import de.nuberjonas.pompalette.core.sharedkernel.exceptions.FactoryException;
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
import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.factory.ProjectFactory;
import org.apache.maven.model.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SuppressWarnings("unchecked")
class MavenProjectFactoryTest {
    private static Model validModel;
    private static Model generatedModel;
    private static ProjectDTO validProjectDTO;
    private static ProjectDTO generatedProjectDTO;

    private static ProjectFactory<Model> factory;

    @BeforeAll
    public static void initialize(){
        factory = new MavenProjectFactory();

        validProjectDTO = deserializeObject( "src/test/resources/factoryTest/validProjectDTO.ser", ProjectDTO.class);
        validModel = deserializeObject( "src/test/resources/factoryTest/validModel.ser", Model.class);

        generatedProjectDTO = factory.createProjectDTO(validModel).get();
        //generatedModel = (Model) factory.createProject(validProjectDTO).get();
    }

    private static <T> T deserializeObject(String fileName, Class<T> clazz) {
        T obj;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            obj = clazz.cast(ois.readObject());
            System.out.println("Object has been deserialized from " + fileName);
        } catch (IOException | ClassNotFoundException e) {
            throw new FactoryException("Could Not deserialize Test File");
        }
        return obj;
    }

    @Test
    void createProjectDTOWithEmptyModel_shouldReturnEmptyOptional(){
        var projectDTO = factory.createProjectDTO(null);

        assertThat(projectDTO).isEmpty();
    }

    @Test
    void createProjectDTO_shouldMapModelBaseCorrectly() throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        assertEquals(generatedProjectDTO.modelBase(), validModel);
    }
    
    private void assertEquals(ModelBaseDTO modelBaseDTO, ModelBase modelBase) throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        assertThat(modelBaseDTO.modules()).isEqualTo(modelBase.getModules());
        assertEquals(modelBaseDTO.distributionManagement(), modelBase.getDistributionManagement());
        assertThat(modelBaseDTO.properties()).isEqualTo(modelBase.getProperties());
        assertEquals(modelBaseDTO.dependencyManagement(), modelBase.getDependencyManagement());
        assertListsAreEqual(modelBaseDTO.dependencies(), modelBase.getDependencies(), this::assertEquals);
        assertListsAreEqual(modelBaseDTO.repositories(), modelBase.getRepositories(), this::assertEquals);
        assertListsAreEqual(modelBaseDTO.pluginRepositories(), modelBase.getPluginRepositories(), this::assertEquals);
        assertEquals(modelBaseDTO.reporting(), modelBase.getReporting());
        assertLocationsAreEqual(modelBaseDTO, modelBase);

        assertTrue(bothAreEmptyOrBothArePresent(modelBaseDTO.location(), modelBase.getLocation("")));
        assertEquals(modelBaseDTO.location(), modelBase.getLocation(""));
        assertTrue(bothAreEmptyOrBothArePresent(modelBaseDTO.modulesLocation(), modelBase.getLocation("modules")));
        assertEquals(modelBaseDTO.modulesLocation(), modelBase.getLocation("modules"));
        assertTrue(bothAreEmptyOrBothArePresent(modelBaseDTO.distributionManagementLocation(), modelBase.getLocation("distributionManagement")));
        assertEquals(modelBaseDTO.distributionManagementLocation(), modelBase.getLocation("distributionManagement"));
        assertTrue(bothAreEmptyOrBothArePresent(modelBaseDTO.propertiesLocation(), modelBase.getLocation("properties")));
        assertEquals(modelBaseDTO.propertiesLocation(), modelBase.getLocation("properties"));
        assertTrue(bothAreEmptyOrBothArePresent(modelBaseDTO.dependencyManagementLocation(), modelBase.getLocation("dependencyManagement")));
        assertEquals(modelBaseDTO.dependencyManagementLocation(), modelBase.getLocation("dependencyManagement"));
        assertTrue(bothAreEmptyOrBothArePresent(modelBaseDTO.dependenciesLocation(), modelBase.getLocation("dependencies")));
        assertEquals(modelBaseDTO.dependenciesLocation(), modelBase.getLocation("dependencies"));
        assertTrue(bothAreEmptyOrBothArePresent(modelBaseDTO.repositoriesLocation(), modelBase.getLocation("repositories")));
        assertEquals(modelBaseDTO.repositoriesLocation(), modelBase.getLocation("repositories"));
        assertTrue(bothAreEmptyOrBothArePresent(modelBaseDTO.pluginRepositoriesLocation(), modelBase.getLocation("pluginRepositories")));
        assertEquals(modelBaseDTO.pluginRepositoriesLocation(), modelBase.getLocation("pluginRepositories"));
        assertTrue(bothAreEmptyOrBothArePresent(modelBaseDTO.reportingLocation(), modelBase.getLocation("reporting")));
        assertEquals(modelBaseDTO.reportingLocation(), modelBase.getLocation("reporting"));
    }

    private void assertEquals(DistributionManagementDTO distributionManagementDTO, DistributionManagement distributionManagement) throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        assertEquals(distributionManagementDTO.repository(), distributionManagement.getRepository());
        assertEquals(distributionManagementDTO.snapshotRepository(), distributionManagement.getSnapshotRepository());
        assertEquals(distributionManagementDTO.site(), distributionManagement.getSite());
        assertThat(distributionManagementDTO.downloadUrl()).isEqualTo(distributionManagement.getDownloadUrl());
        assertEquals(distributionManagementDTO.relocation(), distributionManagement.getRelocation());
        assertThat(distributionManagementDTO.status()).isEqualTo(distributionManagement.getStatus());
        assertLocationsAreEqual(distributionManagementDTO, distributionManagement);

        assertTrue(bothAreEmptyOrBothArePresent(distributionManagementDTO.location(), distributionManagement.getLocation("")));
        assertEquals(distributionManagementDTO.location(), distributionManagement.getLocation(""));
        assertTrue(bothAreEmptyOrBothArePresent(distributionManagementDTO.repositoryLocation(), distributionManagement.getLocation("repository")));
        assertEquals(distributionManagementDTO.repositoryLocation(), distributionManagement.getLocation("repository"));
        assertTrue(bothAreEmptyOrBothArePresent(distributionManagementDTO.snapshotRepositoryLocation(), distributionManagement.getLocation("snapshotRepository")));
        assertEquals(distributionManagementDTO.snapshotRepositoryLocation(), distributionManagement.getLocation("snapshotRepository"));
        assertTrue(bothAreEmptyOrBothArePresent(distributionManagementDTO.siteLocation(), distributionManagement.getLocation("site")));
        assertEquals(distributionManagementDTO.siteLocation(), distributionManagement.getLocation("site"));
        assertTrue(bothAreEmptyOrBothArePresent(distributionManagementDTO.downloadUrlLocation(), distributionManagement.getLocation("downloadUrl")));
        assertEquals(distributionManagementDTO.downloadUrlLocation(), distributionManagement.getLocation("downloadUrl"));
        assertTrue(bothAreEmptyOrBothArePresent(distributionManagementDTO.relocationLocation(), distributionManagement.getLocation("relocation")));
        assertEquals(distributionManagementDTO.relocationLocation(), distributionManagement.getLocation("relocation"));
        assertTrue(bothAreEmptyOrBothArePresent(distributionManagementDTO.statusLocation(), distributionManagement.getLocation("status")));
        assertEquals(distributionManagementDTO.statusLocation(), distributionManagement.getLocation("status"));
    }

    private void assertEquals(DeploymentRepositoryDTO deploymentRepositoryDTO, DeploymentRepository deploymentRepository) throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        assertEquals(deploymentRepositoryDTO.repository(), deploymentRepository);
        assertThat(deploymentRepositoryDTO.uniqueVersion()).isEqualTo(deploymentRepository.isUniqueVersion());
    }

    private void assertEquals(RepositoryDTO repositoryDTO, Repository repository) throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        assertEquals(repositoryDTO.repositoryBase(), repository);
        assertEquals(repositoryDTO.releases(), repository.getReleases());
        assertEquals(repositoryDTO.snapshots(), repository.getSnapshots());
    }

    private void assertEquals(RepositoryBaseDTO repositoryBaseDTO, RepositoryBase repositoryBase) throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        assertThat(repositoryBaseDTO.id()).isEqualTo(repositoryBase.getId());
        assertThat(repositoryBaseDTO.name()).isEqualTo(repositoryBase.getName());
        assertThat(repositoryBaseDTO.url()).isEqualTo(repositoryBase.getUrl());
        assertThat(repositoryBaseDTO.layout()).isEqualTo(repositoryBase.getLayout());
        assertLocationsAreEqual(repositoryBaseDTO, repositoryBase);

        assertTrue(bothAreEmptyOrBothArePresent(repositoryBaseDTO.location(), repositoryBase.getLocation("")));
        assertEquals(repositoryBaseDTO.location(), repositoryBase.getLocation(""));
        assertTrue(bothAreEmptyOrBothArePresent(repositoryBaseDTO.idLocation(), repositoryBase.getLocation("id")));
        assertEquals(repositoryBaseDTO.idLocation(), repositoryBase.getLocation("id"));
        assertTrue(bothAreEmptyOrBothArePresent(repositoryBaseDTO.nameLocation(), repositoryBase.getLocation("name")));
        assertEquals(repositoryBaseDTO.nameLocation(), repositoryBase.getLocation("name"));
        assertTrue(bothAreEmptyOrBothArePresent(repositoryBaseDTO.urlLocation(), repositoryBase.getLocation("url")));
        assertEquals(repositoryBaseDTO.urlLocation(), repositoryBase.getLocation("url"));
        assertTrue(bothAreEmptyOrBothArePresent(repositoryBaseDTO.layoutLocation(), repositoryBase.getLocation("layout")));
        assertEquals(repositoryBaseDTO.layoutLocation(), repositoryBase.getLocation("layout"));
    }

    private void assertLocationsAreEqual(Object projectMemberObject, Object modelMemberObject) throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        var projectMemberClass = projectMemberObject.getClass();
        var projectLocationsMethod = projectMemberClass.getDeclaredMethod("locations");
        var modelLocationsField = getLocationsField(modelMemberObject.getClass());

        modelLocationsField.setAccessible(true);

        var modelLocations = (Map<Object, InputLocation>) modelLocationsField.get(modelMemberObject);
        var projectLocations = (Map<Object, InputLocationDTO>) projectLocationsMethod.invoke(projectMemberObject);

        if(modelLocations != null){
            assertEquals(modelLocations, projectLocations);
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

        throw new NoSuchFieldException("locations");
    }

    private void assertEquals(Map<Object, InputLocation> modelLocations, Map<Object, InputLocationDTO> projectLocations){
        assertThat(modelLocations).hasSameSizeAs(projectLocations);

        for(Object key : modelLocations.keySet()){
            assertThat(projectLocations).containsKey(key);
            assertEquals(projectLocations.get(key), modelLocations.get(key));
        }
    }

    private void assertEquals(InputLocationDTO inputLocationDTO, InputLocation inputLocation){
        if(inputLocationDTO != null && inputLocation != null){
            assertThat(inputLocation.getColumnNumber()).isEqualTo(inputLocationDTO.columnNumber());
            assertThat(inputLocation.getLineNumber()).isEqualTo(inputLocationDTO.lineNumber());
            assertEquals(inputLocation.getSource(), inputLocationDTO.source());

            if(inputLocation.getLocations() != null){
                assertThat(inputLocation.getLocations()).hasSameSizeAs(inputLocationDTO.locations());
            }

            assertEquals(inputLocationDTO, inputLocation.getLocation(""));
        }
    }

    private void assertEquals(InputSource inputSource, InputSourceDTO inputSourceDTO){
        assertThat(inputSource.getModelId()).isEqualTo(inputSourceDTO.modelId());
        assertThat(inputSource.getLocation()).isEqualTo(inputSourceDTO.location());
    }

    private void assertEquals(RepositoryPolicyDTO repositoryPolicyDTO, RepositoryPolicy repositoryPolicy) throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        assertThat(repositoryPolicyDTO.enabled()).isEqualTo(repositoryPolicy.isEnabled());
        assertThat(repositoryPolicyDTO.updatePolicy()).isEqualTo(repositoryPolicy.getUpdatePolicy());
        assertThat(repositoryPolicyDTO.checksumPolicy()).isEqualTo(repositoryPolicy.getChecksumPolicy());
        assertLocationsAreEqual(repositoryPolicyDTO, repositoryPolicy);

        assertTrue(bothAreEmptyOrBothArePresent(repositoryPolicyDTO.location(), repositoryPolicy.getLocation("")));
        assertEquals(repositoryPolicyDTO.location(), repositoryPolicy.getLocation(""));
        assertTrue(bothAreEmptyOrBothArePresent(repositoryPolicyDTO.enabledLocation(), repositoryPolicy.getLocation("enabled")));
        assertEquals(repositoryPolicyDTO.enabledLocation(), repositoryPolicy.getLocation("enabled"));
        assertTrue(bothAreEmptyOrBothArePresent(repositoryPolicyDTO.updatePolicyLocation(), repositoryPolicy.getLocation("updatePolicy")));
        assertEquals(repositoryPolicyDTO.updatePolicyLocation(), repositoryPolicy.getLocation("updatePolicy"));
        assertTrue(bothAreEmptyOrBothArePresent(repositoryPolicyDTO.checksumPolicyLocation(), repositoryPolicy.getLocation("checksumPolicy")));
        assertEquals(repositoryPolicyDTO.checksumPolicyLocation(), repositoryPolicy.getLocation("checksumPolicy"));
    }

    private void assertEquals(SiteDTO siteDTO, Site site) throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        assertThat(siteDTO.id()).isEqualTo(site.getId());
        assertThat(siteDTO.name()).isEqualTo(site.getName());
        assertThat(siteDTO.url()).isEqualTo(site.getUrl());
        assertThat(siteDTO.childSiteUrlInheritAppendPath()).isEqualTo(site.isChildSiteUrlInheritAppendPath());
        assertLocationsAreEqual(siteDTO, site);

        assertTrue(bothAreEmptyOrBothArePresent(siteDTO.location(), site.getLocation("")));
        assertEquals(siteDTO.location(), site.getLocation(""));
        assertTrue(bothAreEmptyOrBothArePresent(siteDTO.idLocation(), site.getLocation("id")));
        assertEquals(siteDTO.idLocation(), site.getLocation("id"));
        assertTrue(bothAreEmptyOrBothArePresent(siteDTO.nameLocation(), site.getLocation("name")));
        assertEquals(siteDTO.nameLocation(), site.getLocation("name"));
        assertTrue(bothAreEmptyOrBothArePresent(siteDTO.urlLocation(), site.getLocation("url")));
        assertEquals(siteDTO.urlLocation(), site.getLocation("url"));
        assertTrue(bothAreEmptyOrBothArePresent(siteDTO.childSiteUrlInheritAppendPathLocation(), site.getLocation("childSiteUrlInheritAppendPath")));
        assertEquals(siteDTO.childSiteUrlInheritAppendPathLocation(), site.getLocation("childSiteUrlInheritAppendPath"));
    }

    private void assertEquals(RelocationDTO relocationDTO, Relocation relocation) throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        assertThat(relocationDTO.groupId()).isEqualTo(relocation.getGroupId());
        assertThat(relocationDTO.artifactId()).isEqualTo(relocation.getArtifactId());
        assertThat(relocationDTO.version()).isEqualTo(relocation.getVersion());
        assertThat(relocationDTO.message()).isEqualTo(relocation.getMessage());
        assertLocationsAreEqual(relocationDTO, relocation);

        assertTrue(bothAreEmptyOrBothArePresent(relocationDTO.location(), relocation.getLocation("")));
        assertEquals(relocationDTO.location(), relocation.getLocation(""));
        assertTrue(bothAreEmptyOrBothArePresent(relocationDTO.groupIdLocation(), relocation.getLocation("groupId")));
        assertEquals(relocationDTO.groupIdLocation(), relocation.getLocation("groupId"));
        assertTrue(bothAreEmptyOrBothArePresent(relocationDTO.artifactIdLocation(), relocation.getLocation("artifactId")));
        assertEquals(relocationDTO.artifactIdLocation(), relocation.getLocation("artifactId"));
        assertTrue(bothAreEmptyOrBothArePresent(relocationDTO.versionLocation(), relocation.getLocation("version")));
        assertEquals(relocationDTO.versionLocation(), relocation.getLocation("version"));
        assertTrue(bothAreEmptyOrBothArePresent(relocationDTO.messageLocation(), relocation.getLocation("message")));
        assertEquals(relocationDTO.messageLocation(), relocation.getLocation("message"));
    }

    private <F,S> boolean bothAreEmptyOrBothArePresent(F first, S second){
        return (first == null && second == null) || (first != null && second != null);
    }

    private void assertEquals(DependencyManagementDTO dependencyManagementDTO, DependencyManagement dependencyManagement) throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        assertListsAreEqual(dependencyManagementDTO.dependencies(), dependencyManagement.getDependencies(), this::assertEquals);
        assertLocationsAreEqual(dependencyManagementDTO, dependencyManagement);

        assertTrue(bothAreEmptyOrBothArePresent(dependencyManagementDTO.location(), dependencyManagement.getLocation("")));
        assertEquals(dependencyManagementDTO.location(), dependencyManagement.getLocation(""));
        assertTrue(bothAreEmptyOrBothArePresent(dependencyManagementDTO.dependenciesLocation(), dependencyManagement.getLocation("dependencies")));
        assertEquals(dependencyManagementDTO.dependenciesLocation(), dependencyManagement.getLocation("dependencies"));
    }

    private void assertEquals(ReportingDTO reportingDTO, Reporting reporting) throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        assertThat(reportingDTO.excludeDefaults()).isEqualTo(reporting.isExcludeDefaults());
        assertThat(reportingDTO.outputDirectory()).isEqualTo(reporting.getOutputDirectory());
        assertListsAreEqual(reportingDTO.plugins(), reporting.getPlugins(), this::assertEquals);
        assertLocationsAreEqual(reportingDTO, reporting);

        assertTrue(bothAreEmptyOrBothArePresent(reportingDTO.location(), reporting.getLocation("")));
        assertEquals(reportingDTO.location(), reporting.getLocation(""));
        assertTrue(bothAreEmptyOrBothArePresent(reportingDTO.excludeDefaultsLocation(), reporting.getLocation("excludeDefaults")));
        assertEquals(reportingDTO.excludeDefaultsLocation(), reporting.getLocation("excludeDefaults"));
        assertTrue(bothAreEmptyOrBothArePresent(reportingDTO.outputDirectoryLocation(), reporting.getLocation("outputDirectory")));
        assertEquals(reportingDTO.outputDirectoryLocation(), reporting.getLocation("outputDirectory"));
        assertTrue(bothAreEmptyOrBothArePresent(reportingDTO.pluginsLocation(), reporting.getLocation("plugins")));
        assertEquals(reportingDTO.pluginsLocation(), reporting.getLocation("plugins"));
    }

    private void assertEquals(ReportPluginDTO reportPluginDTO, ReportPlugin reportPlugin) throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        assertEquals(reportPluginDTO.configurationContainer(), reportPlugin);
        assertThat(reportPluginDTO.groupId()).isEqualTo(reportPlugin.getGroupId());
        assertThat(reportPluginDTO.artifactId()).isEqualTo(reportPlugin.getArtifactId());
        assertThat(reportPluginDTO.version()).isEqualTo(reportPlugin.getVersion());
        assertListsAreEqual(reportPluginDTO.reportSets(), reportPlugin.getReportSets(), this::assertEquals);
    }

    private void assertEquals(ReportSetDTO reportSetDTO, ReportSet reportSet) throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        assertEquals(reportSetDTO.configurationContainer(), reportSet);
        assertThat(reportSetDTO.id()).isEqualTo(reportSet.getId());
        assertThat(reportSetDTO.reports()).isEqualTo(reportSet.getReports());
    }

    @Test
    void createProjectDTO_shouldMapModelVersionCorrectly(){
        assertThat(generatedProjectDTO.modelVersion()).isEqualTo(validModel.getModelVersion());
    }

    @Test
    void createProjectDTO_shouldMapParentCorrectly() throws NoSuchFieldException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        assertParentIsEqual(generatedProjectDTO.parent(), validModel.getParent());
    }

    private void assertParentIsEqual(ParentDTO parentDTO, Parent parent) throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        assertTrue(bothAreEmptyOrBothArePresent(parentDTO, parent));
        assertThat(parentDTO.groupId()).isEqualTo(parent.getGroupId());
        assertThat(parentDTO.artifactId()).isEqualTo(parent.getArtifactId());
        assertThat(parentDTO.version()).isEqualTo(parent.getVersion());
        assertThat(parentDTO.relativePath()).isEqualTo(parent.getRelativePath());

        assertLocationsAreEqual(parentDTO, parent);
        assertTrue(bothAreEmptyOrBothArePresent(parentDTO.location(), parent.getLocation("")));
        assertEquals(parentDTO.location(), parent.getLocation(""));
        assertTrue(bothAreEmptyOrBothArePresent(parentDTO.groupIdLocation(), parent.getLocation("groupId")));
        assertEquals(parentDTO.groupIdLocation(), parent.getLocation("groupId"));
        assertTrue(bothAreEmptyOrBothArePresent(parentDTO.artifactIdLocation(), parent.getLocation("artifactId")));
        assertEquals(parentDTO.artifactIdLocation(), parent.getLocation("artifactId"));
        assertTrue(bothAreEmptyOrBothArePresent(parentDTO.versionLocation(), parent.getLocation("version")));
        assertEquals(parentDTO.versionLocation(), parent.getLocation("version"));
        assertTrue(bothAreEmptyOrBothArePresent(parentDTO.relativePathLocation(), parent.getLocation("relativePath")));
        assertEquals(parentDTO.relativePathLocation(), parent.getLocation("relativePath"));
    }

    @Test
    void createProjectDTO_shouldMapGroupIdCorrectly(){
        assertThat(generatedProjectDTO.groupId()).isEqualTo(validModel.getGroupId());
    }

    @Test
    void createProjectDTO_shouldMapArtifactIdCorrectly(){
        assertThat(generatedProjectDTO.artifactId()).isEqualTo(validModel.getArtifactId());
    }

    @Test
    void createProjectDTO_shouldMapVersionCorrectly(){
        assertThat(generatedProjectDTO.version()).isEqualTo(validModel.getVersion());
    }

    @Test
    void createProjectDTO_shouldMapPackagingCorrectly(){
        assertThat(generatedProjectDTO.packaging()).isEqualTo(validModel.getPackaging());
    }

    @Test
    void createProjectDTO_shouldMapNameCorrectly(){
        assertThat(generatedProjectDTO.name()).isEqualTo(validModel.getName());
    }

    @Test
    void createProjectDTO_shouldMapDescriptionCorrectly(){
        assertThat(generatedProjectDTO.description()).isEqualTo(validModel.getDescription());
    }

    @Test
    void createProjectDTO_shouldMapUrlCorrectly(){
        assertThat(generatedProjectDTO.url()).isEqualTo(validModel.getUrl());
    }

    @Test
    void createProjectDTO_shouldMapChildProjectUrlInheritAppendPathCorrectly(){
        assertThat(generatedProjectDTO.childProjectUrlInheritAppendPath()).isEqualTo(validModel.isChildProjectUrlInheritAppendPath());
    }

    @Test
    void createProjectDTO_shouldMapInceptionYearCorrectly(){
        assertThat(generatedProjectDTO.inceptionYear()).isEqualTo(validModel.getInceptionYear());
    }

    @Test
    void createProjectDTO_shouldMapOrganizationCorrectly() throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        assertEquals(generatedProjectDTO.organization(), validModel.getOrganization());
    }

    private void assertEquals(OrganizationDTO organizationDTO, Organization organization) throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        assertTrue(bothAreEmptyOrBothArePresent(organizationDTO, organization));
        assertThat(organizationDTO.name()).isEqualTo(organization.getName());
        assertThat(organizationDTO.url()).isEqualTo(organization.getUrl());
        assertLocationsAreEqual(organizationDTO, organization);

        assertTrue(bothAreEmptyOrBothArePresent(organizationDTO.location(), organization.getLocation("")));
        assertEquals(organizationDTO.location(), organization.getLocation(""));
        assertTrue(bothAreEmptyOrBothArePresent(organizationDTO.nameLocation(), organization.getLocation("name")));
        assertEquals(organizationDTO.nameLocation(), organization.getLocation("name"));
        assertTrue(bothAreEmptyOrBothArePresent(organizationDTO.urlLocation(), organization.getLocation("url")));
        assertEquals(organizationDTO.urlLocation(), organization.getLocation("url"));
    }

    @Test
    void createProjectDTO_shouldMapLicensesCorrectly() throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        var actualLicenses = generatedProjectDTO.licenses();
        var expectedLicenses = validModel.getLicenses();

        assertListsAreEqual(actualLicenses, expectedLicenses, this::assertEquals);
    }

    public <T, U> void assertListsAreEqual(List<T> list1, List<U> list2, ElementComparator<T, U> comparator) throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        assertTrue(bothAreEmptyOrBothArePresent(list1, list2));
        assertThat(list1).hasSameSizeAs(list2);

        for (int i = 0; i < list1.size(); i++) {
            comparator.compareElements(list1.get(i), list2.get(i));
        }
    }

    private void assertEquals(LicenseDTO licenseDTO, License license) throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        assertThat(licenseDTO.name()).isEqualTo(license.getName());
        assertThat(licenseDTO.url()).isEqualTo(license.getUrl());
        assertThat(licenseDTO.distribution()).isEqualTo(license.getDistribution());
        assertThat(licenseDTO.comments()).isEqualTo(license.getComments());
        assertLocationsAreEqual(licenseDTO, license);

        assertTrue(bothAreEmptyOrBothArePresent(licenseDTO.location(), license.getLocation("")));
        assertEquals(licenseDTO.location(), license.getLocation(""));
        assertTrue(bothAreEmptyOrBothArePresent(licenseDTO.nameLocation(), license.getLocation("name")));
        assertEquals(licenseDTO.nameLocation(), license.getLocation("name"));
        assertTrue(bothAreEmptyOrBothArePresent(licenseDTO.urlLocation(), license.getLocation("url")));
        assertEquals(licenseDTO.urlLocation(), license.getLocation("url"));
        assertTrue(bothAreEmptyOrBothArePresent(licenseDTO.distributionLocation(), license.getLocation("distribution")));
        assertEquals(licenseDTO.distributionLocation(), license.getLocation("distribution"));
        assertTrue(bothAreEmptyOrBothArePresent(licenseDTO.commentsLocation(), license.getLocation("comments")));
        assertEquals(licenseDTO.commentsLocation(), license.getLocation("comments"));
    }

    @Test
    void createProjectDTO_shouldMapDevelopersCorrectly() throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        var actualDevelopers = generatedProjectDTO.developers();
        var expectedDevelopers = validModel.getDevelopers();

        assertListsAreEqual(actualDevelopers, expectedDevelopers, this::assertEquals);
    }

    private void assertEquals(DeveloperDTO developerDTO, Developer developer) throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        assertThat(developerDTO.id()).isEqualTo(developer.getId());
        assertEquals(developerDTO.contributor(), developer);
    }

    @Test
    void createProjectDTO_shouldMapContributorsCorrectly() throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        var actualContributors = generatedProjectDTO.contributors();
        var expectedContributors = validModel.getContributors();

        assertListsAreEqual(actualContributors, expectedContributors, this::assertEquals);
    }

    private void assertEquals(ContributorDTO contributorDTO, Contributor contributor) throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        assertThat(contributorDTO.name()).isEqualTo(contributor.getName());
        assertThat(contributorDTO.email()).isEqualTo(contributor.getEmail());
        assertThat(contributorDTO.url()).isEqualTo(contributor.getUrl());
        assertThat(contributorDTO.organization()).isEqualTo(contributor.getOrganization());
        assertThat(contributorDTO.organizationUrl()).isEqualTo(contributor.getOrganizationUrl());
        assertThat(contributorDTO.roles()).isEqualTo(contributor.getRoles());
        assertThat(contributorDTO.timezone()).isEqualTo(contributor.getTimezone());
        assertThat(contributorDTO.properties()).isEqualTo(contributor.getProperties());
        assertLocationsAreEqual(contributorDTO, contributor);

        assertTrue(bothAreEmptyOrBothArePresent(contributorDTO.location(), contributor.getLocation("")));
        assertEquals(contributorDTO.location(), contributor.getLocation(""));
        assertTrue(bothAreEmptyOrBothArePresent(contributorDTO.nameLocation(), contributor.getLocation("name")));
        assertEquals(contributorDTO.nameLocation(), contributor.getLocation("name"));
        assertTrue(bothAreEmptyOrBothArePresent(contributorDTO.emailLocation(), contributor.getLocation("email")));
        assertEquals(contributorDTO.emailLocation(), contributor.getLocation("email"));
        assertTrue(bothAreEmptyOrBothArePresent(contributorDTO.urlLocation(), contributor.getLocation("url")));
        assertEquals(contributorDTO.urlLocation(), contributor.getLocation("url"));
        assertTrue(bothAreEmptyOrBothArePresent(contributorDTO.urlLocation(), contributor.getLocation("url")));
        assertEquals(contributorDTO.urlLocation(), contributor.getLocation("url"));
        assertTrue(bothAreEmptyOrBothArePresent(contributorDTO.organizationLocation(), contributor.getLocation("organization")));
        assertEquals(contributorDTO.organizationLocation(), contributor.getLocation("organization"));
        assertTrue(bothAreEmptyOrBothArePresent(contributorDTO.organizationUrlLocation(), contributor.getLocation("organizationUrl")));
        assertEquals(contributorDTO.organizationUrlLocation(), contributor.getLocation("organizationUrl"));
        assertTrue(bothAreEmptyOrBothArePresent(contributorDTO.rolesLocation(), contributor.getLocation("roles")));
        assertEquals(contributorDTO.rolesLocation(), contributor.getLocation("roles"));
        assertTrue(bothAreEmptyOrBothArePresent(contributorDTO.timezoneLocation(), contributor.getLocation("timezone")));
        assertEquals(contributorDTO.timezoneLocation(), contributor.getLocation("timezone"));
        assertTrue(bothAreEmptyOrBothArePresent(contributorDTO.propertiesLocation(), contributor.getLocation("configuration")));
        assertEquals(contributorDTO.propertiesLocation(), contributor.getLocation("configuration"));
    }

    @Test
    void createProjectDTO_shouldMapMailingListsCorrectly() throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        var actualMailingList = generatedProjectDTO.mailingLists();
        var expectedMailingLists = validModel.getMailingLists();

        assertListsAreEqual(actualMailingList, expectedMailingLists, this::assertEquals);
    }

    private void assertEquals(MailingListDTO mailingListDTO, MailingList mailingList) throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        assertThat(mailingListDTO.name()).isEqualTo(mailingList.getName());
        assertThat(mailingListDTO.subscribe()).isEqualTo(mailingList.getSubscribe());
        assertThat(mailingListDTO.unsubscribe()).isEqualTo(mailingList.getUnsubscribe());
        assertThat(mailingListDTO.post()).isEqualTo(mailingList.getPost());
        assertThat(mailingListDTO.archive()).isEqualTo(mailingList.getArchive());
        assertListsAreEqual(mailingListDTO.otherArchives(), mailingList.getOtherArchives(),
                (actual, expected) -> assertThat(actual).isEqualTo(expected));
        assertLocationsAreEqual(mailingListDTO, mailingList);

        assertTrue(bothAreEmptyOrBothArePresent(mailingListDTO.location(), mailingList.getLocation("")));
        assertEquals(mailingListDTO.location(), mailingList.getLocation(""));
        assertTrue(bothAreEmptyOrBothArePresent(mailingListDTO.nameLocation(), mailingList.getLocation("name")));
        assertEquals(mailingListDTO.nameLocation(), mailingList.getLocation("name"));
        assertTrue(bothAreEmptyOrBothArePresent(mailingListDTO.subscribeLocation(), mailingList.getLocation("subscribe")));
        assertEquals(mailingListDTO.subscribeLocation(), mailingList.getLocation("subscribe"));
        assertTrue(bothAreEmptyOrBothArePresent(mailingListDTO.unsubscribeLocation(), mailingList.getLocation("unsubscribe")));
        assertEquals(mailingListDTO.unsubscribeLocation(), mailingList.getLocation("unsubscribe"));
        assertTrue(bothAreEmptyOrBothArePresent(mailingListDTO.postLocation(), mailingList.getLocation("post")));
        assertEquals(mailingListDTO.postLocation(), mailingList.getLocation("post"));
        assertTrue(bothAreEmptyOrBothArePresent(mailingListDTO.archiveLocation(), mailingList.getLocation("archive")));
        assertEquals(mailingListDTO.archiveLocation(), mailingList.getLocation("archive"));
        assertTrue(bothAreEmptyOrBothArePresent(mailingListDTO.otherArchivesLocation(), mailingList.getLocation("otherArchives")));
        assertEquals(mailingListDTO.otherArchivesLocation(), mailingList.getLocation("otherArchives"));
    }

    @Test
    void createProjectDTO_shouldMapPrerequisitesCorrectly() throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        assertEquals(generatedProjectDTO.prerequisites(), validModel.getPrerequisites());
    }

    private void assertEquals(PrerequisitesDTO prerequisitesDTO, Prerequisites prerequisites) throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        assertThat(prerequisitesDTO.maven()).isEqualTo(prerequisites.getMaven());
        assertLocationsAreEqual(prerequisitesDTO, prerequisites);

        assertTrue(bothAreEmptyOrBothArePresent(prerequisitesDTO.location(), prerequisites.getLocation("")));
        assertEquals(prerequisitesDTO.location(),prerequisites.getLocation(""));
        assertTrue(bothAreEmptyOrBothArePresent(prerequisitesDTO.mavenLocation(), prerequisites.getLocation("maven")));
        assertEquals(prerequisitesDTO.mavenLocation(),prerequisites.getLocation("maven"));
    }

    @Test
    void createProjectDTO_shouldMapScmCorrectly() throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        assertEquals(generatedProjectDTO.scm(), validModel.getScm());
    }

    private void assertEquals(ScmDTO scmDTO, Scm scm) throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        assertThat(scmDTO.connection()).isEqualTo(scm.getConnection());
        assertThat(scmDTO.developerConnection()).isEqualTo(scm.getDeveloperConnection());
        assertThat(scmDTO.tag()).isEqualTo(scm.getTag());
        assertThat(scmDTO.childScmConnectionInheritAppendPath()).isEqualTo(scm.isChildScmConnectionInheritAppendPath());
        assertThat(scmDTO.childScmDeveloperConnectionInheritAppendPath()).isEqualTo(scm.isChildScmDeveloperConnectionInheritAppendPath());
        assertThat(scmDTO.childScmUrlInheritAppendPath()).isEqualTo(scm.isChildScmUrlInheritAppendPath());
        assertLocationsAreEqual(scmDTO, scm);

        assertTrue(bothAreEmptyOrBothArePresent(scmDTO.location(), scm.getLocation("")));
        assertEquals(scmDTO.location(), scm.getLocation(""));
        assertTrue(bothAreEmptyOrBothArePresent(scmDTO.connectionLocation(), scm.getLocation("connection")));
        assertEquals(scmDTO.connectionLocation(), scm.getLocation("connection"));
        assertTrue(bothAreEmptyOrBothArePresent(scmDTO.developerConnectionLocation(), scm.getLocation("developerConnection")));
        assertEquals(scmDTO.developerConnectionLocation(), scm.getLocation("developerConnection"));
        assertTrue(bothAreEmptyOrBothArePresent(scmDTO.tagLocation(), scm.getLocation("tag")));
        assertEquals(scmDTO.tagLocation(), scm.getLocation("tag"));
        assertTrue(bothAreEmptyOrBothArePresent(scmDTO.tagLocation(), scm.getLocation("url")));
        assertEquals(scmDTO.tagLocation(), scm.getLocation("url"));
        assertTrue(bothAreEmptyOrBothArePresent(scmDTO.childScmConnectionInheritAppendPathLocation(), scm.getLocation("childScmConnectionInheritAppendPath")));
        assertEquals(scmDTO.childScmConnectionInheritAppendPathLocation(), scm.getLocation("childScmConnectionInheritAppendPath"));
        assertTrue(bothAreEmptyOrBothArePresent(scmDTO.childScmDeveloperConnectionInheritAppendPathLocation(), scm.getLocation("childScmDeveloperConnectionInheritAppendPath")));
        assertEquals(scmDTO.childScmDeveloperConnectionInheritAppendPathLocation(), scm.getLocation("childScmDeveloperConnectionInheritAppendPath"));
        assertTrue(bothAreEmptyOrBothArePresent(scmDTO.childScmUrlInheritAppendPath(), scm.getLocation("childScmUrlInheritAppendPath")));
        assertEquals(scmDTO.childScmUrlInheritAppendPathLocation(), scm.getLocation("childScmUrlInheritAppendPath"));
    }

    @Test
    void createProjectDTO_shouldMapIssueManagementCorrectly() throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        assertEquals(generatedProjectDTO.issueManagement(), validModel.getIssueManagement());
    }

    private void assertEquals(IssueManagementDTO issueManagementDTO, IssueManagement issueManagement) throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        assertThat(issueManagementDTO.system()).isEqualTo(issueManagement.getSystem());
        assertThat(issueManagementDTO.url()).isEqualTo(issueManagement.getUrl());
        assertLocationsAreEqual(issueManagementDTO, issueManagement);

        assertTrue(bothAreEmptyOrBothArePresent(issueManagementDTO.location(), issueManagement.getLocation("")));
        assertEquals(issueManagementDTO.location(), issueManagement.getLocation(""));
        assertTrue(bothAreEmptyOrBothArePresent(issueManagementDTO.systemLocation(), issueManagement.getLocation("system")));
        assertEquals(issueManagementDTO.systemLocation(), issueManagement.getLocation("system"));
        assertTrue(bothAreEmptyOrBothArePresent(issueManagementDTO.urlLocation(), issueManagement.getLocation("url")));
        assertEquals(issueManagementDTO.urlLocation(), issueManagement.getLocation("url"));
    }

    @Test
    void createProjectDTO_shouldMapCiManagementCorrectly() throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        assertEquals(generatedProjectDTO.ciManagement(), validModel.getCiManagement());
    }

    private void assertEquals(CiManagementDTO ciManagementDTO, CiManagement ciManagement) throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        assertThat(ciManagementDTO.system()).isEqualTo(ciManagement.getSystem());
        assertThat(ciManagementDTO.url()).isEqualTo(ciManagement.getUrl());
        assertListsAreEqual(ciManagementDTO.notifiers(), ciManagement.getNotifiers(), this::assertEquals);
        assertLocationsAreEqual(ciManagementDTO, ciManagement);

        assertTrue(bothAreEmptyOrBothArePresent(ciManagementDTO.location(), ciManagement.getLocation("")));
        assertEquals(ciManagementDTO.location(), ciManagement.getLocation(""));
        assertTrue(bothAreEmptyOrBothArePresent(ciManagementDTO.systemLocation(), ciManagement.getLocation("system")));
        assertEquals(ciManagementDTO.systemLocation(), ciManagement.getLocation("system"));
        assertTrue(bothAreEmptyOrBothArePresent(ciManagementDTO.urlLocation(), ciManagement.getLocation("url")));
        assertEquals(ciManagementDTO.urlLocation(), ciManagement.getLocation("url"));
        assertTrue(bothAreEmptyOrBothArePresent(ciManagementDTO.notifiersLocation(), ciManagement.getLocation("notifiers")));
        assertEquals(ciManagementDTO.notifiersLocation(), ciManagement.getLocation("notifiers"));
    }

    private void assertEquals(NotifierDTO notifierDTO, Notifier notifier) throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        assertThat(notifierDTO.type()).isEqualTo(notifier.getType());
        assertThat(notifierDTO.sendOnError()).isEqualTo(notifier.isSendOnError());
        assertThat(notifierDTO.sendOnFailure()).isEqualTo(notifier.isSendOnFailure());
        assertThat(notifierDTO.sendOnSuccess()).isEqualTo(notifier.isSendOnSuccess());
        assertThat(notifierDTO.sendOnWarning()).isEqualTo(notifier.isSendOnWarning());
        assertThat(notifierDTO.configuration()).isEqualTo(notifier.getConfiguration());
        assertLocationsAreEqual(notifierDTO, notifier);

        assertTrue(bothAreEmptyOrBothArePresent(notifierDTO.location(), notifier.getLocation("")));
        assertEquals(notifierDTO.location(), notifier.getLocation(""));
        assertTrue(bothAreEmptyOrBothArePresent(notifierDTO.typeLocation(), notifier.getLocation("type")));
        assertEquals(notifierDTO.typeLocation(), notifier.getLocation("type"));
        assertTrue(bothAreEmptyOrBothArePresent(notifierDTO.sendOnErrorLocation(), notifier.getLocation("sendOnError")));
        assertEquals(notifierDTO.sendOnErrorLocation(), notifier.getLocation("sendOnError"));
        assertTrue(bothAreEmptyOrBothArePresent(notifierDTO.sendOnFailureLocation(), notifier.getLocation("sendOnFailure")));
        assertEquals(notifierDTO.sendOnFailureLocation(), notifier.getLocation("sendOnFailure"));
        assertTrue(bothAreEmptyOrBothArePresent(notifierDTO.sendOnSuccessLocation(), notifier.getLocation("sendOnSuccess")));
        assertEquals(notifierDTO.sendOnSuccessLocation(), notifier.getLocation("sendOnSuccess"));
        assertTrue(bothAreEmptyOrBothArePresent(notifierDTO.sendOnWarningLocation(), notifier.getLocation("sendOnWarning")));
        assertEquals(notifierDTO.sendOnWarningLocation(), notifier.getLocation("sendOnWarning"));
        assertTrue(bothAreEmptyOrBothArePresent(notifierDTO.addressLocation(), notifier.getLocation("address")));
        assertEquals(notifierDTO.addressLocation(), notifier.getLocation("address"));
        assertTrue(bothAreEmptyOrBothArePresent(notifierDTO.configurationLocation(), notifier.getLocation("configuration")));
        assertEquals(notifierDTO.configurationLocation(), notifier.getLocation("configuration"));
    }

    @Test
    void createProjectDTO_shouldMapBuildCorrectly() throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        assertEquals(generatedProjectDTO.build(), validModel.getBuild());
    }

    private void assertEquals(BuildDTO buildDTO, Build build) throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        assertEquals(buildDTO.buildBase(), build);
        assertThat(buildDTO.sourceDirectory()).isEqualTo(build.getSourceDirectory());
        assertThat(buildDTO.scriptSourceDirectory()).isEqualTo(build.getScriptSourceDirectory());
        assertThat(buildDTO.testSourceDirectory()).isEqualTo(build.getTestSourceDirectory());
        assertThat(buildDTO.outputDirectory()).isEqualTo(build.getOutputDirectory());
        assertThat(buildDTO.testOutputDirectory()).isEqualTo(build.getTestOutputDirectory());
        assertListsAreEqual(buildDTO.extensions(), build.getExtensions(), this::assertEquals);
    }

    private void assertEquals(BuildBaseDTO buildBaseDTO, BuildBase buildBase) throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        assertEquals(buildBaseDTO.pluginConfiguration(), buildBase);
        assertThat(buildBaseDTO.defaultGoal()).isEqualTo(buildBase.getDefaultGoal());
        assertListsAreEqual(buildBaseDTO.resources(), buildBase.getResources(), this::assertEquals);
        assertListsAreEqual(buildBaseDTO.testResources(), buildBase.getTestResources(), this::assertEquals);
        assertThat(buildBaseDTO.directory()).isEqualTo(buildBase.getDirectory());
        assertThat(buildBaseDTO.finalName()).isEqualTo(buildBase.getFinalName());
        assertThat(buildBaseDTO.filters()).isEqualTo(buildBase.getFilters());
    }

    private void assertEquals(PluginConfigurationDTO pluginConfigurationDTO, PluginConfiguration pluginConfiguration) throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        assertEquals(pluginConfigurationDTO.pluginContainer(), pluginConfiguration);
        assertEquals(pluginConfigurationDTO.pluginManagement(), pluginConfiguration.getPluginManagement());
    }

    private void assertEquals(PluginContainerDTO pluginContainerDTO, PluginContainer pluginContainer) throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        assertListsAreEqual(pluginContainerDTO.plugins(), pluginContainer.getPlugins(), this::assertEquals);
        assertLocationsAreEqual(pluginContainerDTO, pluginContainer);

        assertTrue(bothAreEmptyOrBothArePresent(pluginContainerDTO.location(), pluginContainer.getLocation("")));
        assertEquals(pluginContainerDTO.location(), pluginContainer.getLocation(""));
        assertTrue(bothAreEmptyOrBothArePresent(pluginContainerDTO.pluginsLocation(), pluginContainer.getLocation("plugins")));
        assertEquals(pluginContainerDTO.pluginsLocation(), pluginContainer.getLocation("plugins"));
    }

    private void assertEquals(PluginDTO pluginDTO, Plugin plugin) throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        assertEquals(pluginDTO.configurationContainer(), plugin);
        assertThat(pluginDTO.groupId()).isEqualTo(plugin.getGroupId());
        assertThat(pluginDTO.artifactId()).isEqualTo(plugin.getArtifactId());
        assertThat(pluginDTO.version()).isEqualTo(plugin.getVersion());
        assertThat(pluginDTO.extensions()).isEqualTo(plugin.getExtensions());
        assertListsAreEqual(pluginDTO.executions(), plugin.getExecutions(), this::assertEquals);
        assertListsAreEqual(pluginDTO.dependencies(), plugin.getDependencies(), this::assertEquals);
    }

    private void assertEquals(ConfigurationContainerDTO configurationContainerDTO, ConfigurationContainer configurationContainer) throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        assertThat(configurationContainerDTO.inherited()).isEqualTo(configurationContainer.isInherited());
        assertThat(configurationContainerDTO.configuration()).isEqualTo(configurationContainer.getConfiguration());
        assertLocationsAreEqual(configurationContainerDTO, configurationContainer);

        assertTrue(bothAreEmptyOrBothArePresent(configurationContainerDTO.location(), configurationContainer.getLocation("")));
        assertEquals(configurationContainerDTO.location(), configurationContainer.getLocation(""));
        assertTrue(bothAreEmptyOrBothArePresent(configurationContainerDTO.inheritedLocation(), configurationContainer.getLocation("inherited")));
        assertEquals(configurationContainerDTO.inheritedLocation(), configurationContainer.getLocation("inherited"));
        assertTrue(bothAreEmptyOrBothArePresent(configurationContainerDTO.configurationLocation(), configurationContainer.getLocation("configuration")));
        assertEquals(configurationContainerDTO.configurationLocation(), configurationContainer.getLocation("configuration"));

        assertThat(configurationContainerDTO.inheritanceApplied()).isEqualTo(configurationContainer.isInheritanceApplied());
    }

    private void assertEquals(PluginExecutionDTO pluginExecutionDTO, PluginExecution pluginExecution) throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        assertEquals(pluginExecutionDTO.configurationContainer(), pluginExecution);
        assertThat(pluginExecutionDTO.id()).isEqualTo(pluginExecution.getId());
        assertThat(pluginExecutionDTO.phase()).isEqualTo(pluginExecution.getPhase());
        assertThat(pluginExecutionDTO.goals()).isEqualTo(pluginExecution.getGoals());
    }

    private void assertEquals(DependencyDTO dependencyDTO, Dependency dependency) throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        assertThat(dependencyDTO.groupId()).isEqualTo(dependency.getGroupId());
        assertThat(dependencyDTO.artifactId()).isEqualTo(dependency.getArtifactId());
        assertThat(dependencyDTO.version()).isEqualTo(dependency.getVersion());
        assertThat(dependencyDTO.type()).isEqualTo(dependency.getType());
        assertThat(dependencyDTO.classifier()).isEqualTo(dependency.getClassifier());
        assertThat(dependencyDTO.scope()).isEqualTo(dependency.getScope());
        assertThat(dependencyDTO.systemPath()).isEqualTo(dependency.getSystemPath());
        assertListsAreEqual(dependencyDTO.exclusions(), dependency.getExclusions(), this::assertEquals);
        assertThat(dependencyDTO.optional()).isEqualTo(dependency.isOptional());
        assertLocationsAreEqual(dependencyDTO, dependency);

        assertTrue(bothAreEmptyOrBothArePresent(dependencyDTO.location(), dependency.getLocation("")));
        assertEquals(dependencyDTO.location(), dependency.getLocation(""));
        assertTrue(bothAreEmptyOrBothArePresent(dependencyDTO.groupIdLocation(), dependency.getLocation("groupId")));
        assertEquals(dependencyDTO.groupIdLocation(), dependency.getLocation("groupId"));
        assertTrue(bothAreEmptyOrBothArePresent(dependencyDTO.artifactId(), dependency.getLocation("artifactId")));
        assertEquals(dependencyDTO.artifactIdLocation(), dependency.getLocation("artifactId"));
        assertTrue(bothAreEmptyOrBothArePresent(dependencyDTO.versionLocation(), dependency.getLocation("version")));
        assertEquals(dependencyDTO.versionLocation(), dependency.getLocation("version"));
        assertTrue(bothAreEmptyOrBothArePresent(dependencyDTO.typeLocation(), dependency.getLocation("type")));
        assertEquals(dependencyDTO.typeLocation(), dependency.getLocation("type"));
        assertTrue(bothAreEmptyOrBothArePresent(dependencyDTO.classifierLocation(), dependency.getLocation("classifier")));
        assertEquals(dependencyDTO.classifierLocation(), dependency.getLocation("classifier"));
        assertTrue(bothAreEmptyOrBothArePresent(dependencyDTO.scopeLocation(), dependency.getLocation("scope")));
        assertEquals(dependencyDTO.scopeLocation(), dependency.getLocation("scope"));
        assertTrue(bothAreEmptyOrBothArePresent(dependencyDTO.systemPathLocation(), dependency.getLocation("systemPath")));
        assertEquals(dependencyDTO.systemPathLocation(), dependency.getLocation("systemPath"));
        assertTrue(bothAreEmptyOrBothArePresent(dependencyDTO.exclusionsLocation(), dependency.getLocation("exclusions")));
        assertEquals(dependencyDTO.exclusionsLocation(), dependency.getLocation("exclusions"));
        assertTrue(bothAreEmptyOrBothArePresent(dependencyDTO.optionalLocation(), dependency.getLocation("optional")));
        assertEquals(dependencyDTO.optionalLocation(), dependency.getLocation("optional"));
    }

    private void assertEquals(ExclusionDTO exclusionDTO, Exclusion exclusion) throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        assertThat(exclusionDTO.groupId()).isEqualTo(exclusion.getGroupId());
        assertThat(exclusionDTO.artifactId()).isEqualTo(exclusion.getArtifactId());
        assertLocationsAreEqual(exclusionDTO, exclusion);

        assertTrue(bothAreEmptyOrBothArePresent(exclusionDTO.location(), exclusion.getLocation("")));
        assertEquals(exclusionDTO.location(), exclusion.getLocation(""));
        assertTrue(bothAreEmptyOrBothArePresent(exclusionDTO.groupIdLocation(), exclusion.getLocation("groupId")));
        assertEquals(exclusionDTO.groupIdLocation(), exclusion.getLocation("groupId"));
        assertTrue(bothAreEmptyOrBothArePresent(exclusionDTO.artifactIdLocation(), exclusion.getLocation("artifactId")));
        assertEquals(exclusionDTO.artifactIdLocation(), exclusion.getLocation("artifactId"));
    }

    private void assertEquals(PluginManagementDTO pluginManagementDTO, PluginManagement pluginManagement) throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        assertEquals(pluginManagementDTO.pluginContainer(), pluginManagement);
    }

    private void assertEquals(ResourceDTO resourceDTO, Resource resource) throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        assertEquals(resourceDTO.fileSet(), resource);
        assertThat(resourceDTO.targetPath()).isEqualTo(resource.getTargetPath());
        assertThat(resourceDTO.filtering()).isEqualTo(resource.isFiltering());
        assertThat(resourceDTO.mergeId()).isEqualTo(resource.getMergeId());
    }

    private void assertEquals(FileSetDTO fileSetDTO, FileSet fileSet) throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        assertEquals(fileSetDTO.patternSet(), fileSet);
        assertThat(fileSetDTO.directory()).isEqualTo(fileSet.getDirectory());
    }

    private void assertEquals(PatternSetDTO patternSetDTO, PatternSet patternSet) throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        assertThat(patternSetDTO.includes()).isEqualTo(patternSet.getIncludes());
        assertThat(patternSetDTO.excludes()).isEqualTo(patternSet.getExcludes());
        assertLocationsAreEqual(patternSetDTO, patternSet);

        assertTrue(bothAreEmptyOrBothArePresent(patternSetDTO.location(), patternSet.getLocation("")));
        assertEquals(patternSetDTO.location(), patternSet.getLocation(""));
        assertTrue(bothAreEmptyOrBothArePresent(patternSetDTO.includesLocation(), patternSet.getLocation("includes")));
        assertEquals(patternSetDTO.includesLocation(), patternSet.getLocation("includes"));
        assertTrue(bothAreEmptyOrBothArePresent(patternSetDTO.excludesLocation(), patternSet.getLocation("excludes")));
        assertEquals(patternSetDTO.excludesLocation(), patternSet.getLocation("excludes"));
    }

    private void assertEquals(ExtensionDTO extensionDTO, Extension extension) throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        assertThat(extensionDTO.groupId()).isEqualTo(extension.getGroupId());
        assertThat(extensionDTO.artifactId()).isEqualTo(extension.getArtifactId());
        assertThat(extensionDTO.version()).isEqualTo(extension.getVersion());
        assertLocationsAreEqual(extensionDTO, extension);

        assertTrue(bothAreEmptyOrBothArePresent(extensionDTO.location(), extension.getLocation("")));
        assertEquals(extensionDTO.location(), extension.getLocation(""));
        assertTrue(bothAreEmptyOrBothArePresent(extensionDTO.groupIdLocation(), extension.getLocation("groupId")));
        assertEquals(extensionDTO.groupIdLocation(), extension.getLocation("groupId"));
        assertTrue(bothAreEmptyOrBothArePresent(extensionDTO.artifactIdLocation(), extension.getLocation("artifactId")));
        assertEquals(extensionDTO.artifactIdLocation(), extension.getLocation("artifactId"));
        assertTrue(bothAreEmptyOrBothArePresent(extensionDTO.versionLocation(), extension.getLocation("version")));
        assertEquals(extensionDTO.versionLocation(), extension.getLocation("version"));
    }

    @Test
    void createProjectDTO_shouldMapProfilesCorrectly() throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        assertListsAreEqual(generatedProjectDTO.profiles(), validModel.getProfiles(), this::assertEquals);
    }

    private void assertEquals(ProfileDTO profileDTO, Profile profile) throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        assertEquals(profileDTO.modelBase(), profile);
        assertThat(profileDTO.id()).isEqualTo(profile.getId());
        assertEquals(profileDTO.activation(), profile.getActivation());
        assertEquals(profileDTO.build(), profile.getBuild());
    }

    private void assertEquals(ActivationDTO activationDTO, Activation activation) throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        assertThat(activationDTO.activeByDefault()).isEqualTo(activation.isActiveByDefault());
        assertThat(activationDTO.jdk()).isEqualTo(activation.getJdk());
        assertEquals(activationDTO.os(), activation.getOs());
        assertEquals(activationDTO.property(), activation.getProperty());
        assertEquals(activationDTO.file(), activation.getFile());
        assertLocationsAreEqual(activationDTO, activation);

        assertTrue(bothAreEmptyOrBothArePresent(activationDTO.location(), activation.getLocation("")));
        assertEquals(activationDTO.location(), activation.getLocation(""));
        assertTrue(bothAreEmptyOrBothArePresent(activationDTO.activeByDefaultLocation(), activation.getLocation("activeByDefault")));
        assertEquals(activationDTO.activeByDefaultLocation(), activation.getLocation("activeByDefault"));
        assertTrue(bothAreEmptyOrBothArePresent(activationDTO.jdkLocation(), activation.getLocation("jdk")));
        assertEquals(activationDTO.jdkLocation(), activation.getLocation("jdk"));
        assertTrue(bothAreEmptyOrBothArePresent(activationDTO.osLocation(), activation.getLocation("os")));
        assertEquals(activationDTO.osLocation(), activation.getLocation("os"));
        assertTrue(bothAreEmptyOrBothArePresent(activationDTO.propertyLocation(), activation.getLocation("property")));
        assertEquals(activationDTO.propertyLocation(), activation.getLocation("property"));
        assertTrue(bothAreEmptyOrBothArePresent(activationDTO.fileLocation(), activation.getLocation("file")));
        assertEquals(activationDTO.fileLocation(), activation.getLocation("file"));
    }

    private void assertEquals(ActivationOsDTO activationOsDTO, ActivationOS activationOS) throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        assertThat(activationOsDTO.name()).isEqualTo(activationOS.getName());
        assertThat(activationOsDTO.family()).isEqualTo(activationOS.getFamily());
        assertThat(activationOsDTO.arch()).isEqualTo(activationOS.getArch());
        assertThat(activationOsDTO.version()).isEqualTo(activationOS.getVersion());
        assertLocationsAreEqual(activationOsDTO, activationOS);

        assertTrue(bothAreEmptyOrBothArePresent(activationOsDTO.location(), activationOS.getLocation("")));
        assertEquals(activationOsDTO.location(), activationOS.getLocation(""));
        assertTrue(bothAreEmptyOrBothArePresent(activationOsDTO.nameLocation(), activationOS.getLocation("name")));
        assertEquals(activationOsDTO.nameLocation(), activationOS.getLocation("name"));
        assertTrue(bothAreEmptyOrBothArePresent(activationOsDTO.familyLocation(), activationOS.getLocation("family")));
        assertEquals(activationOsDTO.familyLocation(), activationOS.getLocation("family"));
        assertTrue(bothAreEmptyOrBothArePresent(activationOsDTO.archLocation(), activationOS.getLocation("arch")));
        assertEquals(activationOsDTO.archLocation(), activationOS.getLocation("arch"));
        assertTrue(bothAreEmptyOrBothArePresent(activationOsDTO.versionLocation(), activationOS.getLocation("version")));
        assertEquals(activationOsDTO.versionLocation(), activationOS.getLocation("version"));
    }

    private void assertEquals(ActivationPropertyDTO activationPropertyDTO, ActivationProperty activationProperty) throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        assertThat(activationPropertyDTO.name()).isEqualTo(activationProperty.getName());
        assertThat(activationPropertyDTO.value()).isEqualTo(activationProperty.getValue());
        assertLocationsAreEqual(activationPropertyDTO, activationProperty);

        assertTrue(bothAreEmptyOrBothArePresent(activationPropertyDTO.location(), activationProperty.getLocation("")));
        assertEquals(activationPropertyDTO.location(), activationProperty.getLocation(""));
        assertTrue(bothAreEmptyOrBothArePresent(activationPropertyDTO.nameLocation(), activationProperty.getLocation("name")));
        assertEquals(activationPropertyDTO.nameLocation(), activationProperty.getLocation("name"));
        assertTrue(bothAreEmptyOrBothArePresent(activationPropertyDTO.valueLocation(), activationProperty.getLocation("value")));
        assertEquals(activationPropertyDTO.valueLocation(), activationProperty.getLocation("value"));
    }

    private void assertEquals(ActivationFileDTO activationFileDTO, ActivationFile activationFile) throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        assertThat(activationFileDTO.missing()).isEqualTo(activationFile.getMissing());
        assertThat(activationFileDTO.exists()).isEqualTo(activationFile.getExists());
        assertLocationsAreEqual(activationFileDTO, activationFile);

        assertTrue(bothAreEmptyOrBothArePresent(activationFileDTO.location(), activationFile.getLocation("")));
        assertEquals(activationFileDTO.location(), activationFile.getLocation(""));
        assertTrue(bothAreEmptyOrBothArePresent(activationFileDTO.location(), activationFile.getLocation("missing")));
        assertEquals(activationFileDTO.location(), activationFile.getLocation("missing"));
        assertTrue(bothAreEmptyOrBothArePresent(activationFileDTO.location(), activationFile.getLocation("exists")));
        assertEquals(activationFileDTO.location(), activationFile.getLocation("exists"));
    }

    @Test
    void createProjectDTO_shouldMapModelEncodingCorrectly() {
        assertThat(generatedProjectDTO.modelEncodings()).isEqualTo(validModel.getModelEncoding());
    }
}