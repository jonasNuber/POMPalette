package de.nuberjonas.pompalette.infrastructure.parsing.projectparsingmavenimpl.factory;

import org.apache.maven.model.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Properties;

class MavenProjectFactoryTest {
    private static Model validModel;
    @Test
    public void createProjectDTO_withValidModel_Test(){

    }

    @BeforeAll
    public static void createValidModel(){
        Model model = new Model();

        InputSource inputSource = new InputSource();
        inputSource.setModelId("someModelID");
        inputSource.setLocation("/some/location");

        InputLocation location = new InputLocation(2,3, inputSource);
        InputLocation location2 = new InputLocation(2,3, inputSource);
        location.setLocation( new Object(), location2);

        Properties properties = new Properties();
        properties.setProperty("Key", "Value");
        properties.setProperty("org.bla", "15");

        Parent parent = new Parent();
        parent.setGroupId("com.example");
        parent.setArtifactId("my-project");
        parent.setVersion("1.0.0");
        parent.setRelativePath("/relative/path");
        parent.setLocation(new Object(), location);

        Organization organization = new Organization();
        organization.setName("Example Organization");
        organization.setUrl("https://www.example.com");
        organization.setLocation(new Object(), location);

        License license = new License();
        license.setName("Apache License 2.0");
        license.setUrl("https://www.apache.org/licenses/LICENSE-2.0");
        license.setDistribution("repo");

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
        developer.setLocation(new Object(), location2);

        MailingList mailingList = new MailingList();
        mailingList.setName("User List");
        mailingList.setSubscribe("user-subscribe@example.com");
        mailingList.setUnsubscribe("user-unsubscribe@example.com");
        mailingList.setPost("user-post@example.com");
        mailingList.setArchive("https://example.com/mailing-list-archive");
        mailingList.setOtherArchives(List.of("https://example.com/mailing-list-archive/other"));
        mailingList.setLocation(new Object(), location);

        Prerequisites prerequisites = new Prerequisites();
        prerequisites.setMaven("3.8.1");
        prerequisites.setLocation(new Object(), location2);

        Scm scm = new Scm();
        scm.setConnection("scm:git:https://github.com/example/my-project.git");
        scm.setDeveloperConnection("scm:git:https://github.com/example/my-project.git");
        scm.setUrl("https://github.com/example/my-project");
        scm.setTag("RELEASE_1.0.0");
        scm.setLocation(new Object(), location);

        IssueManagement issueManagement = new IssueManagement();
        issueManagement.setSystem("JIRA");
        issueManagement.setUrl("https://example.com/jira");
        issueManagement.setLocation(new Object(), location2);

        Notifier notifier = new Notifier();
        notifier.setAddress("mail@address");
        notifier.setConfiguration(properties);
        notifier.setLocation(new Object(), location2);

        CiManagement ciManagement = new CiManagement();
        ciManagement.setSystem("Jenkins");
        ciManagement.setUrl("https://example.com/jenkins");
        ciManagement.setNotifiers(List.of(notifier));
        ciManagement.setLocation(new Object(), location);

        Extension extension = new Extension();
        extension.setGroupId("com.example");
        extension.setArtifactId("my-maven-extension");
        extension.setVersion("1.0.0");
        extension.setLocation(new Object(), location2);

        Resource resource = new Resource();
        resource.setTargetPath("some/target/Path");
        resource.setMergeId("mergeID");
        resource.setDirectory("src/main/resources");
        resource.setIncludes(List.of("inclusion1", "inclusion2"));
        resource.setExcludes(List.of("exclusion1", "exclusion2"));
        resource.setLocation(new Object(), location);

        PluginExecution pluginExecution = new PluginExecution();
        pluginExecution.setPhase("compile");
        pluginExecution.setGoals(List.of("install"));

        Exclusion exclusion = new Exclusion();
        exclusion.setGroupId("some.artifact.I.do.not.want");
        exclusion.setGroupId("bla");
        exclusion.setLocation(new Object(), location2);

        Dependency dependency = new Dependency();
        dependency.setGroupId("some.group");
        dependency.setArtifactId("Artifact2");
        dependency.setVersion("1.0");
        dependency.setClassifier("classifier");
        dependency.setScope("runtime");
        dependency.setSystemPath("java.home");
        dependency.setExclusions(List.of(exclusion));
        dependency.setLocation(new Object(), location);

        Plugin plugin = new Plugin();
        plugin.setGroupId("org.apache.maven.plugins");
        plugin.setArtifactId("Artifact");
        plugin.setVersion("1.0.0-SNAPSHOT");
        plugin.setExecutions(List.of(pluginExecution));
        plugin.setDependencies(List.of(dependency));
        plugin.setInherited(false);
        plugin.setLocation(new Object(), location2);

        PluginManagement pluginManagement = new PluginManagement();
        pluginManagement.setPlugins(List.of(plugin));
        pluginManagement.setLocation(new Object(), location);

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
        build.setLocation(new Object(), location2);

        ActivationOS activationOS = new ActivationOS();
        activationOS.setName("Windows XP");
        activationOS.setFamily("windows");
        activationOS.setArch("AMD");
        activationOS.setVersion("42");
        activationOS.setLocation(new Object(), location);

        ActivationProperty activationProperty = new ActivationProperty();
        activationProperty.setName("some profile");
        activationProperty.setValue("some value");
        activationProperty.setLocation(new Object(), location2);

        ActivationFile activationFile = new ActivationFile();
        activationFile.setMissing("file.xml");
        activationFile.setExists("file2.xml");
        activationFile.setLocation(new Object(), location);

        Activation activation = new Activation();
        activation.setJdk("21");
        activation.setOs(activationOS);
        activation.setProperty(activationProperty);
        activation.setFile(activationFile);
        activation.setLocation(new Object(), location2);

        BuildBase buildBase = new BuildBase();
        buildBase.setDefaultGoal("clean install");
        buildBase.setResources(List.of(resource));
        buildBase.setTestResources(List.of(resource));
        buildBase.setFilters(List.of("Filter1"));
        buildBase.setPluginManagement(pluginManagement);
        buildBase.setPlugins(List.of(plugin));
        buildBase.setLocation(new Object(), location2);

        Profile profile = new Profile();
        profile.setActivation(activation);
        profile.setBuild(buildBase);

        RepositoryPolicy repositoryPolicy = new RepositoryPolicy();
        repositoryPolicy.setEnabled(true);
        repositoryPolicy.setUpdatePolicy("daily");
        repositoryPolicy.setChecksumPolicy("ignore");
        repositoryPolicy.setLocation(new Object(), location);

        DeploymentRepository deploymentRepository = new DeploymentRepository();
        deploymentRepository.setReleases(repositoryPolicy);
        deploymentRepository.setSnapshots(repositoryPolicy);
        deploymentRepository.setId("id");
        deploymentRepository.setName("Name");
        deploymentRepository.setUrl("protocol://hostname/path");
        deploymentRepository.setLocation(new Object(), location2);

        Site site = new Site();
        site.setId("site");
        site.setName("siteName");
        site.setUrl("protocol://hostname/path");
        site.setLocation(new Object(), location);

        Relocation relocation = new Relocation();
        relocation.setGroupId("some.group");
        relocation.setArtifactId("Artifact123");
        relocation.setVersion("1.0.1");
        relocation.setMessage("reason");
        relocation.setLocation(new Object(), location);

        DistributionManagement distributionManagement = new DistributionManagement();
        distributionManagement.setRepository(deploymentRepository);
        distributionManagement.setSnapshotRepository(deploymentRepository);
        distributionManagement.setSite(site);
        distributionManagement.setDownloadUrl("someurl.com");
        distributionManagement.setRelocation(relocation);
        distributionManagement.setStatus("status");
        distributionManagement.setLocation(new Object(), location2);

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
        model.setLocation(new Object(), location2);

        validModel = model;
    }
}