package de.nuberjonas.pompalette.infrastructure.parsing.projectparsingmavenimpl.factory;

import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.build.BuildDTO;
import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.contributing.*;
import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.dependency.DependencyDTO;
import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.dependency.ExclusionDTO;
import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.input.InputLocationDTO;
import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.input.InputSourceDTO;
import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.management.CiManagementDTO;
import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.management.IssueManagementDTO;
import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.plugin.PluginDTO;
import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.plugin.PluginExecutionDTO;
import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.project.*;
import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.factory.ProjectFactory;
import org.apache.maven.model.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

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

        validProjectDTO = createValidProjectDTO();
        validModel = createValidModel();

        generatedProjectDTO = (ProjectDTO) factory.createProjectDTO(validModel).get();
        //generatedModel = (Model) factory.createProject(validProjectDTO).get();
    }

    private static Model createValidModel(){
        Model model = new Model();

        InputSource inputSource = new InputSource();
        inputSource.setModelId("someModelID");
        inputSource.setLocation("/some/location");

        InputLocation location = new InputLocation(2,3, inputSource);
        InputLocation location2 = new InputLocation(2,3, inputSource);
        InputLocation location3 = new InputLocation(2,3, inputSource);
        location.setLocation("", location3);
        location2.setLocation("", location3);

        Properties properties = new Properties();
        properties.setProperty("Key", "Value");
        properties.setProperty("org.bla", "15");

        Parent parent = new Parent();
        parent.setGroupId("com.example");
        parent.setArtifactId("my-project");
        parent.setVersion("1.0.0");
        parent.setRelativePath("/relative/path");
        parent.setLocation("", location);
        parent.setLocation("groupId", location);
        parent.setLocation("artifactId", location);
        parent.setLocation("version", location);
        parent.setLocation("relativePath", location);

        Organization organization = new Organization();
        organization.setName("Example Organization");
        organization.setUrl("https://www.example.com");
        organization.setLocation("", location2);
        organization.setLocation("name", location2);
        organization.setLocation("url", location2);

        License license = new License();
        license.setName("Apache License 2.0");
        license.setUrl("https://www.apache.org/licenses/LICENSE-2.0");
        license.setDistribution("repo");
        license.setComments("some Comment");
        license.setLocation("", location);
        license.setLocation("name", location);
        license.setLocation("url", location);
        license.setLocation("distribution", location);
        license.setLocation("comments", location);

        Developer developer = new Developer();
        developer.setId("john-doe");
        developer.setName("John Doe");
        developer.setEmail("john.doe@example.com");
        developer.setUrl("https://github.com/johndoe");
        developer.setOrganization("Example Organization");
        developer.setOrganizationUrl("https://www.example.com");
        developer.setRoles(List.of("Core Dev"));
        developer.setTimezone("UTC+1:00");
        developer.setProperties(properties);
        developer.setLocation("", location2);
        developer.setLocation("name", location2);
        developer.setLocation("email", location2);
        developer.setLocation("url", location2);
        developer.setLocation("organization", location2);
        developer.setLocation("organizationUrl", location2);
        developer.setLocation("roles", location2);
        developer.setLocation("timezone", location2);
        developer.setLocation("configuration", location2);

        MailingList mailingList = new MailingList();
        mailingList.setName("User List");
        mailingList.setSubscribe("user-subscribe@example.com");
        mailingList.setUnsubscribe("user-unsubscribe@example.com");
        mailingList.setPost("user-post@example.com");
        mailingList.setArchive("https://example.com/mailing-list-archive");
        mailingList.setOtherArchives(List.of("https://example.com/mailing-list-archive/other"));
        mailingList.setLocation("", location);
        mailingList.setLocation("name", location);
        mailingList.setLocation("subscribe", location);
        mailingList.setLocation("unsubscribe", location);
        mailingList.setLocation("post", location);
        mailingList.setLocation("archive", location);
        mailingList.setLocation("otherArchives", location);

        Prerequisites prerequisites = new Prerequisites();
        prerequisites.setMaven("3.8.1");
        prerequisites.setLocation("", location2);
        prerequisites.setLocation("maven", location2);

        Scm scm = new Scm();
        scm.setConnection("scm:git:https://github.com/example/my-project.git");
        scm.setUrl("https://github.com/example/my-project");
        scm.setTag("RELEASE_1.0.0");
        scm.setDeveloperConnection("scm:git:https://github.com/example/my-project.git");
        scm.setChildScmConnectionInheritAppendPath(true);
        scm.setChildScmDeveloperConnectionInheritAppendPath(true);
        scm.setChildScmUrlInheritAppendPath(false);
        scm.setOtherLocation("someStringKey", location);
        scm.setLocation("", location);
        scm.setLocation("connection", location);
        scm.setLocation("developerConnection", location);
        scm.setLocation("tag", location);
        scm.setLocation("url", location);
        scm.setLocation("childScmConnectionInheritAppendPath", location);
        scm.setLocation("childScmDeveloperConnectionInheritAppendPath", location);
        scm.setLocation("childScmUrlInheritAppendPath", location);

        IssueManagement issueManagement = new IssueManagement();
        issueManagement.setSystem("JIRA");
        issueManagement.setUrl("https://example.com/jira");
        issueManagement.setLocation("", location2);
        issueManagement.setLocation("system", location2);
        issueManagement.setLocation("url", location2);

        Notifier notifier = new Notifier();
        notifier.setAddress("mail@address");
        notifier.setConfiguration(properties);
        notifier.setLocation("", location2);
        notifier.setLocation("type", location2);
        notifier.setLocation("sendOnError", location2);
        notifier.setLocation("sendOnFailure", location2);
        notifier.setLocation("sendOnSuccess", location2);
        notifier.setLocation("sendOnWarning", location2);
        notifier.setLocation("address", location2);
        notifier.setLocation("configuration", location2);

        CiManagement ciManagement = new CiManagement();
        ciManagement.setSystem("Jenkins");
        ciManagement.setUrl("https://example.com/jenkins");
        ciManagement.setNotifiers(List.of(notifier));
        ciManagement.setLocation("", location);
        ciManagement.setLocation("system", location);
        ciManagement.setLocation("url", location);
        ciManagement.setLocation("notifiers", location);

        Extension extension = new Extension();
        extension.setGroupId("com.example");
        extension.setArtifactId("my-maven-extension");
        extension.setVersion("1.0.0");
        extension.setLocation("", location2);

        Resource resource = new Resource();
        resource.setTargetPath("some/target/Path");
        resource.setMergeId("mergeID");
        resource.setDirectory("src/main/resources");
        resource.setIncludes(List.of("inclusion1", "inclusion2"));
        resource.setExcludes(List.of("exclusion1", "exclusion2"));
        resource.setLocation("", location);

        PluginExecution pluginExecution = new PluginExecution();
        pluginExecution.setPhase("compile");
        pluginExecution.setGoals(List.of("install"));
        pluginExecution.setLocation("", location);
        pluginExecution.setLocation("inherited", location);
        pluginExecution.setLocation("configuration", location);

        Exclusion exclusion = new Exclusion();
        exclusion.setGroupId("some.artifact.I.do.not.want");
        exclusion.setArtifactId("bla");
        exclusion.setLocation("", location2);
        exclusion.setLocation("groupId", location2);
        exclusion.setLocation("artifactId", location2);

        Dependency dependency = new Dependency();
        dependency.setGroupId("some.group");
        dependency.setArtifactId("Artifact2");
        dependency.setVersion("1.0");
        dependency.setClassifier("classifier");
        dependency.setScope("runtime");
        dependency.setSystemPath("java.home");
        dependency.setExclusions(List.of(exclusion));
        dependency.setOptional(true);
        dependency.setLocation("", location);
        dependency.setLocation("groupId", location);
        dependency.setLocation("artifactId", location);
        dependency.setLocation("version", location);
        dependency.setLocation("type", location);
        dependency.setLocation("classifier", location);
        dependency.setLocation("scope", location);
        dependency.setLocation("systemPath", location);
        dependency.setLocation("exclusions", location);
        dependency.setLocation("optional", location);

        Plugin plugin = new Plugin();
        plugin.setGroupId("org.apache.maven.plugins");
        plugin.setArtifactId("Artifact");
        plugin.setVersion("1.0.0-SNAPSHOT");
        plugin.setExecutions(List.of(pluginExecution));
        plugin.setDependencies(List.of(dependency));
        plugin.setInherited(false);
        plugin.setLocation("", location2);
        plugin.setLocation("inherited", location2);
        plugin.setLocation("configuration", location2);

        PluginManagement pluginManagement = new PluginManagement();
        pluginManagement.setPlugins(List.of(plugin));
        pluginManagement.setLocation("", location);

        Build build = new Build();
        build.setSourceDirectory("src/main/java");
        build.setTestSourceDirectory("src/test/java");
        build.setOutputDirectory("target/classes");
        build.setTestOutputDirectory("target/test-classes");
        build.setExtensions(List.of(extension));
        build.setDefaultGoal("clean install");
        build.setResources(List.of(resource));
        build.setTestResources(List.of(resource));
        build.setFilters(List.of("Filter1"));
        build.setPluginManagement(pluginManagement);
        build.setPlugins(List.of(plugin));
        build.setLocation("", location2);
        build.setLocation("plugins", location2);

        ActivationOS activationOS = new ActivationOS();
        activationOS.setName("Windows XP");
        activationOS.setFamily("windows");
        activationOS.setArch("AMD");
        activationOS.setVersion("42");
        activationOS.setLocation("", location);

        ActivationProperty activationProperty = new ActivationProperty();
        activationProperty.setName("some profile");
        activationProperty.setValue("some value");
        activationProperty.setLocation("", location2);

        ActivationFile activationFile = new ActivationFile();
        activationFile.setMissing("file.xml");
        activationFile.setExists("file2.xml");
        activationFile.setLocation("", location);

        Activation activation = new Activation();
        activation.setJdk("21");
        activation.setOs(activationOS);
        activation.setProperty(activationProperty);
        activation.setFile(activationFile);
        activation.setLocation("", location2);

        BuildBase buildBase = new BuildBase();
        buildBase.setDefaultGoal("clean install");
        buildBase.setResources(List.of(resource));
        buildBase.setTestResources(List.of(resource));
        buildBase.setFilters(List.of("Filter1"));
        buildBase.setPluginManagement(pluginManagement);
        buildBase.setPlugins(List.of(plugin));
        buildBase.setLocation("", location2);

        Profile profile = new Profile();
        profile.setActivation(activation);
        profile.setBuild(buildBase);

        RepositoryPolicy repositoryPolicy = new RepositoryPolicy();
        repositoryPolicy.setEnabled(true);
        repositoryPolicy.setUpdatePolicy("daily");
        repositoryPolicy.setChecksumPolicy("ignore");
        repositoryPolicy.setLocation("", location);

        DeploymentRepository deploymentRepository = new DeploymentRepository();
        deploymentRepository.setReleases(repositoryPolicy);
        deploymentRepository.setSnapshots(repositoryPolicy);
        deploymentRepository.setId("id");
        deploymentRepository.setName("Name");
        deploymentRepository.setUrl("protocol://hostname/path");
        deploymentRepository.setLocation("", location2);

        Site site = new Site();
        site.setId("site");
        site.setName("siteName");
        site.setUrl("protocol://hostname/path");
        site.setLocation("", location);

        Relocation relocation = new Relocation();
        relocation.setGroupId("some.group");
        relocation.setArtifactId("Artifact123");
        relocation.setVersion("1.0.1");
        relocation.setMessage("reason");
        relocation.setLocation("", location);

        DistributionManagement distributionManagement = new DistributionManagement();
        distributionManagement.setRepository(deploymentRepository);
        distributionManagement.setSnapshotRepository(deploymentRepository);
        distributionManagement.setSite(site);
        distributionManagement.setDownloadUrl("someurl.com");
        distributionManagement.setRelocation(relocation);
        distributionManagement.setStatus("status");
        distributionManagement.setLocation("", location2);

        DependencyManagement dependencyManagement = new DependencyManagement();
        dependencyManagement.setDependencies(List.of(dependency));

        ReportSet reportSet = new ReportSet();
        reportSet.setId("id");
        reportSet.setReports(List.of("report1"));

        ReportPlugin reportPlugin = new ReportPlugin();
        reportPlugin.setGroupId("group.vl");
        reportPlugin.setArtifactId("artifact");
        reportPlugin.setVersion("1.0.0");
        reportPlugin.setReportSets(List.of(reportSet));

        Reporting reporting = new Reporting();
        reporting.setExcludeDefaults(false);
        reporting.setOutputDirectory("/site");
        reporting.setPlugins(List.of(reportPlugin));

        model.setModelVersion("4.0.0");
        model.setParent(parent);
        model.setGroupId("com.example");
        model.setArtifactId("my-project");
        model.setVersion("1.0.0");
        model.setName("My Project");
        model.setDescription("Description of my project");
        model.setUrl("https://www.somewebsite.com");
        model.setInceptionYear("2024");
        model.setOrganization(organization);
        model.setLicenses(List.of(license));
        model.setDevelopers(List.of(developer));
        model.setContributors(List.of(developer));
        model.setMailingLists(List.of(mailingList));
        model.setPrerequisites(prerequisites);
        model.setScm(scm);
        model.setIssueManagement(issueManagement);
        model.setCiManagement(ciManagement);
        model.setBuild(build);
        model.setProfiles(List.of(profile));
        model.setModules(List.of("TestModule", "Important-Module"));
        model.setDistributionManagement(distributionManagement);
        model.setProperties(properties);
        model.setDependencyManagement(dependencyManagement);
        model.setDependencies(List.of(dependency));
        model.setRepositories(List.of(deploymentRepository));
        model.setPluginRepositories(List.of(deploymentRepository));
        model.setReporting(reporting);
        model.setLocation("", location2);

        return model;
    }

    private static ProjectDTO createValidProjectDTO(){
        return null;
    }

    @Test
    void createProjectDTOWithEmptyModel_shouldReturnEmptyOptional(){
        var projectDTO = factory.createProjectDTO(null);

        assertThat(projectDTO).isEmpty();
    }

    @Test
    void createProjectDTO_shouldMapModelVersionCorrectly(){
        assertStringIsEqual(generatedProjectDTO.modelVersion(), validModel.getModelVersion());
    }

    private void assertStringIsEqual(String first, String second){
        assertThat(first).isEqualTo(second);
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

    private <F,S> boolean bothAreEmptyOrBothArePresent(F first, S second){
        return (first == null && second == null) || (first != null && second != null);
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

    @Test
    void createProjectDTO_shouldMapGroupIdCorrectly(){
        assertStringIsEqual(generatedProjectDTO.groupId(), validModel.getGroupId());
    }

    @Test
    void createProjectDTO_shouldMapArtifactIdCorrectly(){
        assertStringIsEqual(generatedProjectDTO.artifactId(), validModel.getArtifactId());
    }

    @Test
    void createProjectDTO_shouldMapVersionCorrectly(){
        assertStringIsEqual(generatedProjectDTO.version(), validModel.getVersion());
    }

    @Test
    void createProjectDTO_shouldMapPackagingCorrectly(){
        assertStringIsEqual(generatedProjectDTO.packaging(), validModel.getPackaging());
    }

    @Test
    void createProjectDTO_shouldMapNameCorrectly(){
        assertStringIsEqual(generatedProjectDTO.name(), validModel.getName());
    }

    @Test
    void createProjectDTO_shouldMapDescriptionCorrectly(){
        assertStringIsEqual(generatedProjectDTO.description(), validModel.getDescription());
    }

    @Test
    void createProjectDTO_shouldMapUrlCorrectly(){
        assertStringIsEqual(generatedProjectDTO.url(), validModel.getUrl());
    }

    @Test
    void createProjectDTO_shouldMapInceptionYearCorrectly(){
        assertStringIsEqual(generatedProjectDTO.inceptionYear(), validModel.getInceptionYear());
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
        assertThat(developerDTO.name()).isEqualTo(developer.getName());
        assertThat(developerDTO.email()).isEqualTo(developer.getEmail());
        assertThat(developerDTO.url()).isEqualTo(developer.getUrl());
        assertThat(developerDTO.organization()).isEqualTo(developer.getOrganization());
        assertThat(developerDTO.organizationUrl()).isEqualTo(developer.getOrganizationUrl());
        assertThat(developerDTO.roles()).isEqualTo(developer.getRoles());
        assertThat(developerDTO.timezone()).isEqualTo(developer.getTimezone());
        assertThat(developerDTO.properties()).isEqualTo(developer.getProperties());
        assertLocationsAreEqual(developerDTO, developer);

        assertTrue(bothAreEmptyOrBothArePresent(developerDTO.location(), developer.getLocation("")));
        assertEquals(developerDTO.location(), developer.getLocation(""));
        assertTrue(bothAreEmptyOrBothArePresent(developerDTO.nameLocation(), developer.getLocation("name")));
        assertEquals(developerDTO.nameLocation(), developer.getLocation("name"));
        assertTrue(bothAreEmptyOrBothArePresent(developerDTO.emailLocation(), developer.getLocation("email")));
        assertEquals(developerDTO.emailLocation(), developer.getLocation("email"));
        assertTrue(bothAreEmptyOrBothArePresent(developerDTO.urlLocation(), developer.getLocation("url")));
        assertEquals(developerDTO.urlLocation(), developer.getLocation("url"));
        assertTrue(bothAreEmptyOrBothArePresent(developerDTO.urlLocation(), developer.getLocation("url")));
        assertEquals(developerDTO.urlLocation(), developer.getLocation("url"));
        assertTrue(bothAreEmptyOrBothArePresent(developerDTO.organizationLocation(), developer.getLocation("organization")));
        assertEquals(developerDTO.organizationLocation(), developer.getLocation("organization"));
        assertTrue(bothAreEmptyOrBothArePresent(developerDTO.organizationUrlLocation(), developer.getLocation("organizationUrl")));
        assertEquals(developerDTO.organizationUrlLocation(), developer.getLocation("organizationUrl"));
        assertTrue(bothAreEmptyOrBothArePresent(developerDTO.rolesLocation(), developer.getLocation("roles")));
        assertEquals(developerDTO.rolesLocation(), developer.getLocation("roles"));
        assertTrue(bothAreEmptyOrBothArePresent(developerDTO.timezoneLocation(), developer.getLocation("timezone")));
        assertEquals(developerDTO.timezoneLocation(), developer.getLocation("timezone"));
        assertTrue(bothAreEmptyOrBothArePresent(developerDTO.propertiesLocation(), developer.getLocation("configuration")));
        assertEquals(developerDTO.propertiesLocation(), developer.getLocation("configuration"));
    }

    @Test
    void createProjectDTO_shouldMapContributersCorrectly() throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        var actualContributers = generatedProjectDTO.contributors();
        var expectedContributers = validModel.getContributors();

        assertListsAreEqual(actualContributers, expectedContributers, this::assertEquals);
    }

    private void assertEquals(ContributerDTO contributerDTO, Contributor contributor) throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        assertThat(contributerDTO.name()).isEqualTo(contributor.getName());
        assertThat(contributerDTO.email()).isEqualTo(contributor.getEmail());
        assertThat(contributerDTO.url()).isEqualTo(contributor.getUrl());
        assertThat(contributerDTO.organization()).isEqualTo(contributor.getOrganization());
        assertThat(contributerDTO.organizationUrl()).isEqualTo(contributor.getOrganizationUrl());
        assertThat(contributerDTO.roles()).isEqualTo(contributor.getRoles());
        assertThat(contributerDTO.timezone()).isEqualTo(contributor.getTimezone());
        assertThat(contributerDTO.properties()).isEqualTo(contributor.getProperties());
        assertLocationsAreEqual(contributerDTO, contributor);

        assertTrue(bothAreEmptyOrBothArePresent(contributerDTO.location(), contributor.getLocation("")));
        assertEquals(contributerDTO.location(), contributor.getLocation(""));
        assertTrue(bothAreEmptyOrBothArePresent(contributerDTO.nameLocation(), contributor.getLocation("name")));
        assertEquals(contributerDTO.nameLocation(), contributor.getLocation("name"));
        assertTrue(bothAreEmptyOrBothArePresent(contributerDTO.emailLocation(), contributor.getLocation("email")));
        assertEquals(contributerDTO.emailLocation(), contributor.getLocation("email"));
        assertTrue(bothAreEmptyOrBothArePresent(contributerDTO.urlLocation(), contributor.getLocation("url")));
        assertEquals(contributerDTO.urlLocation(), contributor.getLocation("url"));
        assertTrue(bothAreEmptyOrBothArePresent(contributerDTO.urlLocation(), contributor.getLocation("url")));
        assertEquals(contributerDTO.urlLocation(), contributor.getLocation("url"));
        assertTrue(bothAreEmptyOrBothArePresent(contributerDTO.organizationLocation(), contributor.getLocation("organization")));
        assertEquals(contributerDTO.organizationLocation(), contributor.getLocation("organization"));
        assertTrue(bothAreEmptyOrBothArePresent(contributerDTO.organizationUrlLocation(), contributor.getLocation("organizationUrl")));
        assertEquals(contributerDTO.organizationUrlLocation(), contributor.getLocation("organizationUrl"));
        assertTrue(bothAreEmptyOrBothArePresent(contributerDTO.rolesLocation(), contributor.getLocation("roles")));
        assertEquals(contributerDTO.rolesLocation(), contributor.getLocation("roles"));
        assertTrue(bothAreEmptyOrBothArePresent(contributerDTO.timezoneLocation(), contributor.getLocation("timezone")));
        assertEquals(contributerDTO.timezoneLocation(), contributor.getLocation("timezone"));
        assertTrue(bothAreEmptyOrBothArePresent(contributerDTO.propertiesLocation(), contributor.getLocation("configuration")));
        assertEquals(contributerDTO.propertiesLocation(), contributor.getLocation("configuration"));
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
                (actual, expected) -> { assertThat(actual).isEqualTo(expected); });
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
        assertListsAreEqual(buildDTO.plugins(), build.getPlugins(), this::assertEquals);
        assertLocationsAreEqual(buildDTO, build);

        assertTrue(bothAreEmptyOrBothArePresent(buildDTO.location(), build.getLocation("")));
        assertEquals(buildDTO.location(), build.getLocation(""));
        assertTrue(bothAreEmptyOrBothArePresent(buildDTO.pluginsLocation(), build.getLocation("plugins")));
        assertEquals(buildDTO.pluginsLocation(), build.getLocation("plugins"));
    }

    private void assertEquals(PluginDTO pluginDTO, Plugin plugin) throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        assertThat(pluginDTO.inherited()).isEqualTo(plugin.isInherited());
        assertThat(pluginDTO.configuration()).isEqualTo(plugin.getConfiguration());
        assertLocationsAreEqual(pluginDTO, plugin);

        assertTrue(bothAreEmptyOrBothArePresent(pluginDTO.location(), plugin.getLocation("")));
        assertEquals(pluginDTO.location(), plugin.getLocation(""));
        assertTrue(bothAreEmptyOrBothArePresent(pluginDTO.inheritedLocation(), plugin.getLocation("inherited")));
        assertEquals(pluginDTO.inheritedLocation(), plugin.getLocation("inherited"));
        assertTrue(bothAreEmptyOrBothArePresent(pluginDTO.configurationLocation(), plugin.getLocation("configuration")));
        assertEquals(pluginDTO.configurationLocation(), plugin.getLocation("configuration"));

        assertThat(pluginDTO.inheritanceApplied()).isEqualTo(plugin.isInheritanceApplied());
        assertThat(pluginDTO.groupId()).isEqualTo(plugin.getGroupId());
        assertThat(pluginDTO.artifactId()).isEqualTo(plugin.getArtifactId());
        assertThat(pluginDTO.version()).isEqualTo(plugin.getVersion());
        assertThat(pluginDTO.extensions()).isEqualTo(plugin.getExtensions());
        assertListsAreEqual(pluginDTO.executions(), plugin.getExecutions(), this::assertEquals);
        assertListsAreEqual(pluginDTO.dependencies(), plugin.getDependencies(), this::assertEquals);
    }

    private void assertEquals(PluginExecutionDTO pluginExecutionDTO, PluginExecution pluginExecution) throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        assertThat(pluginExecutionDTO.inherited()).isEqualTo(pluginExecution.isInherited());
        assertThat(pluginExecutionDTO.configuration()).isEqualTo(pluginExecution.getConfiguration());
        assertLocationsAreEqual(pluginExecutionDTO, pluginExecution);

        assertTrue(bothAreEmptyOrBothArePresent(pluginExecutionDTO.location(), pluginExecution.getLocation("")));
        assertEquals(pluginExecutionDTO.location(), pluginExecution.getLocation(""));
        assertTrue(bothAreEmptyOrBothArePresent(pluginExecutionDTO.inheritedLocation(), pluginExecution.getLocation("inherited")));
        assertEquals(pluginExecutionDTO.inheritedLocation(), pluginExecution.getLocation("inherited"));
        assertTrue(bothAreEmptyOrBothArePresent(pluginExecutionDTO.configurationLocation(), pluginExecution.getLocation("configuration")));
        assertEquals(pluginExecutionDTO.configurationLocation(), pluginExecution.getLocation("configuration"));

        assertThat(pluginExecutionDTO.inheritanceApplied()).isEqualTo(pluginExecution.isInheritanceApplied());
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
        assertTrue(bothAreEmptyOrBothArePresent(exclusionDTO.artifactIdLocation(), exclusion.getLocation("artifactId")));
        assertEquals(exclusionDTO.artifactIdLocation(), exclusion.getLocation("artifactId"));
        assertTrue(bothAreEmptyOrBothArePresent(exclusionDTO.groupIdLocation(), exclusion.getLocation("groupId")));
        assertEquals(exclusionDTO.groupIdLocation(), exclusion.getLocation("groupId"));
    }
}