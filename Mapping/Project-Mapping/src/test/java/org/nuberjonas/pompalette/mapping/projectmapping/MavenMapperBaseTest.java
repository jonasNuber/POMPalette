package org.nuberjonas.pompalette.mapping.projectmapping;

import org.apache.maven.model.*;
import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.ProjectDTO;
import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.activation.ActivationDTO;
import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.activation.ActivationFileDTO;
import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.activation.ActivationOsDTO;
import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.activation.ActivationPropertyDTO;
import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.build.BuildBaseDTO;
import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.build.BuildDTO;
import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.build.ExtensionDTO;
import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.build.resource.FileSetDTO;
import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.build.resource.PatternSetDTO;
import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.build.resource.ResourceDTO;
import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.contributing.ContributorDTO;
import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.contributing.DeveloperDTO;
import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.contributing.MailingListDTO;
import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.contributing.OrganizationDTO;
import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.dependency.DependencyDTO;
import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.dependency.DependencyManagementDTO;
import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.dependency.ExclusionDTO;
import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.input.InputLocationDTO;
import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.input.InputSourceDTO;
import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.management.*;
import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.model.*;
import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.plugin.*;
import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.reporting.ReportPluginDTO;
import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.reporting.ReportSetDTO;
import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.reporting.ReportingDTO;
import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.repository.DeploymentRepositoryDTO;
import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.repository.RepositoryBaseDTO;
import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.repository.RepositoryDTO;
import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.repository.RepositoryPolicyDTO;
import org.nuberjonas.pompalette.mapping.mappingapi.mapper.utilities.SuperClassMapper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class MavenMapperBaseTest {

    protected InputLocation validInputLocation(){
        var inputLocation = new InputLocation(
                2,
                3,
                validInputSource());
        inputLocation.setLocation("", new InputLocation(
                2,
                3,
                validInputSource()));

        return inputLocation;
    }

    protected InputLocationDTO validInputLocationDTO(){
        return new InputLocationDTO(
                2,
                3,
                validInputSourceDTO(),
                null,
                new InputLocationDTO(
                        2,
                        3,
                        validInputSourceDTO(),
                        null,
                        null
                )
        );
    }

     protected void assertEquals(InputLocationDTO inputLocationDTO, InputLocation inputLocation){
        assertTrue(bothAreEmptyOrBothArePresent(inputLocation, inputLocationDTO));

        if(bothArePresent(inputLocation, inputLocationDTO)){
            assertThat(inputLocation.getLineNumber()).isEqualTo(inputLocationDTO.lineNumber());
            assertThat(inputLocation.getColumnNumber()).isEqualTo(inputLocationDTO.columnNumber());
            assertEquals(inputLocation.getSource(), inputLocationDTO.source());

            if(inputLocation.getLocations() != null){
                assertThat(inputLocation.getLocations()).hasSameSizeAs(inputLocationDTO.locations());
            }

            assertEquals(inputLocationDTO.location(), inputLocation.getLocation(""));
        }

    }

    protected InputSource validInputSource(){
        var inputSource = new InputSource();
        inputSource.setModelId("someModelID");
        inputSource.setLocation("/some/location");

        return inputSource;
    }

    protected InputSourceDTO validInputSourceDTO(){
         return new InputSourceDTO(
                 "someModelID",
                 "/some/location"
         );
    }

    protected void assertEquals(InputSource inputSource, InputSourceDTO inputSourceDTO){
        assertTrue(bothAreEmptyOrBothArePresent(inputSource, inputSourceDTO));

        if(bothArePresent(inputSource, inputSourceDTO)){
            assertThat(inputSource.getModelId()).isEqualTo(inputSourceDTO.modelId());
            assertThat(inputSource.getLocation()).isEqualTo(inputSourceDTO.location());
        }
    }

    protected InputLocation validInputLocationWithLocationsMap(){
        Map<Object, InputLocation> locations = new LinkedHashMap<>();
        locations.put("someKey", validInputLocation());

        var inputLocation = validInputLocation();
        inputLocation.setLocations(locations);

        return inputLocation;
    }

    protected InputLocation emptyInputLocationWithLocationsMap(){
        Map<Object, InputLocation> locations = new LinkedHashMap<>();

        var inputLocation = validInputLocation();
        inputLocation.setLocations(locations);

        return inputLocation;
    }

    protected Map<Object, InputLocationDTO> validInputLocationDTOs(){
        Map<Object, InputLocationDTO> locationDTOs = new LinkedHashMap<>();

        locationDTOs.put("someKey", validInputLocationDTO());

        return locationDTOs;
    }

    @SuppressWarnings("unchecked")
    protected void assertLocationsAreEqual(Object projectMemberObject, Object modelMemberObject) throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
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

    protected void assertEquals(Map<Object, InputLocation> modelLocations, Map<Object, InputLocationDTO> projectLocations){
        assertThat(modelLocations).hasSameSizeAs(projectLocations);

        for(Object key : modelLocations.keySet()){
            assertThat(projectLocations).containsKey(key);
            assertEquals(projectLocations.get(key), modelLocations.get(key));
        }
    }

    protected Exclusion validExclusion() {
        var exclusion = new Exclusion();
        exclusion.setGroupId("some.artifact.I.do.not.want");
        exclusion.setArtifactId("bla");
        exclusion.setLocation("", validInputLocation());
        exclusion.setLocation("groupId", validInputLocation());
        exclusion.setLocation("artifactId", validInputLocation());

        return exclusion;
    }

    protected ExclusionDTO validExclusionDTO(){
        return new ExclusionDTO(
                "some.artifact.I.do.not.want",
                "bla",
                new LinkedHashMap<>(),
                validInputLocationDTO(),
                validInputLocationDTO(),
                validInputLocationDTO()
        );
    }
    protected void assertEquals(ExclusionDTO exclusionDTO, Exclusion exclusion) throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        assertTrue(bothAreEmptyOrBothArePresent(exclusionDTO, exclusion));

        if(bothArePresent(exclusionDTO, exclusion)){
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
    }

    private <F,S> boolean bothAreEmptyOrBothArePresent(F first, S second){
        return bothAreEmpty(first, second) || bothArePresent(first, second);
    }

    private <F, S> boolean bothAreEmpty(F first, S second){
        return first == null && second == null;
    }

    private <F, S> boolean bothArePresent(F first, S second){
        return first != null && second != null;
    }

    protected Dependency validDependency(){
        var dependency = new Dependency();
        dependency.setGroupId("some.group");
        dependency.setArtifactId("Artifact2");
        dependency.setVersion("1.0");
        dependency.setType("jar");
        dependency.setClassifier("classifier");
        dependency.setScope("runtime");
        dependency.setSystemPath("java.home");
        dependency.setExclusions(List.of(validExclusion()));
        dependency.setOptional(true);
        dependency.setLocation("", validInputLocation());
        dependency.setLocation("groupId", validInputLocation());
        dependency.setLocation("artifactId", validInputLocation());
        dependency.setLocation("version", validInputLocation());
        dependency.setLocation("type", validInputLocation());
        dependency.setLocation("classifier", validInputLocation());
        dependency.setLocation("scope", validInputLocation());
        dependency.setLocation("systemPath", validInputLocation());
        dependency.setLocation("exclusions", validInputLocation());
        dependency.setLocation("optional", validInputLocation());

        return dependency;
    }

    protected DependencyDTO validDependencyDTO(){
        return new DependencyDTO(
                "some.group",
                "Artifact2",
                "1.0",
                "jar",
                "classifier",
                "runtime",
                "java.home",
                List.of(validExclusionDTO()),
                true,
                null,
                validInputLocationDTO(),
                validInputLocationDTO(),
                validInputLocationDTO(),
                validInputLocationDTO(),
                validInputLocationDTO(),
                validInputLocationDTO(),
                validInputLocationDTO(),
                validInputLocationDTO(),
                validInputLocationDTO(),
                validInputLocationDTO()
        );
    }

    protected void assertEquals(DependencyDTO dependencyDTO, Dependency dependency) throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        assertTrue(bothAreEmptyOrBothArePresent(dependencyDTO, dependency));

        if(bothArePresent(dependencyDTO, dependency)){
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
    }

    private <T, U> void assertListsAreEqual(List<T> list1, List<U> list2, ElementComparator<T, U> comparator) throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        assertTrue(bothAreEmptyOrBothArePresent(list1, list2) || (list1 == null && list2.isEmpty()) || (list2 == null && list1.isEmpty()));

        if(bothArePresent(list1, list2)){
            assertThat(list1).hasSameSizeAs(list2);
            for (int i = 0; i < list1.size(); i++) {
                comparator.compareElements(list1.get(i), list2.get(i));
            }
        }
    }

    protected DependencyManagement validDependencyManagement(){
        var dependencyManagement = new DependencyManagement();
        dependencyManagement.setDependencies(List.of(validDependency()));
        dependencyManagement.setLocation("", validInputLocation());
        dependencyManagement.setLocation("dependencies", validInputLocation());

        return dependencyManagement;
    }

    protected DependencyManagementDTO validDependencyManagementDTO(){
        return new DependencyManagementDTO(
                List.of(validDependencyDTO()),
                null,
                validInputLocationDTO(),
                validInputLocationDTO()
        );
    }

    protected void assertEquals(DependencyManagementDTO dependencyManagementDTO, DependencyManagement dependencyManagement) throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        assertTrue(bothAreEmptyOrBothArePresent(dependencyManagementDTO, dependencyManagement));

        if(bothArePresent(dependencyManagement, dependencyManagementDTO)){
            assertListsAreEqual(dependencyManagementDTO.dependencies(), dependencyManagement.getDependencies(), this::assertEquals);
            assertLocationsAreEqual(dependencyManagementDTO, dependencyManagement);

            assertTrue(bothAreEmptyOrBothArePresent(dependencyManagementDTO.location(), dependencyManagement.getLocation("")));
            assertEquals(dependencyManagementDTO.location(), dependencyManagement.getLocation(""));
            assertTrue(bothAreEmptyOrBothArePresent(dependencyManagementDTO.dependenciesLocation(), dependencyManagement.getLocation("dependencies")));
            assertEquals(dependencyManagementDTO.dependenciesLocation(), dependencyManagement.getLocation("dependencies"));
        }
    }

    protected Properties validProperties(){
        var properties = new Properties();
        properties.setProperty("Key", "Value");
        properties.setProperty("org.bla", "15");

        return properties;
    }

    protected Contributor validContributor(){
        var contributor = new Contributor();
        contributor.setName("John Doe");
        contributor.setEmail("john.doe@example.com");
        contributor.setUrl("https://github.com/johndoe");
        contributor.setOrganization("Example Organization");
        contributor.setOrganizationUrl("https://www.example.com");
        contributor.setRoles(List.of("Core Dev"));
        contributor.setTimezone("UTC+1:00");
        contributor.setProperties(validProperties());
        contributor.setLocation("", validInputLocation());
        contributor.setLocation("name", validInputLocation());
        contributor.setLocation("email", validInputLocation());
        contributor.setLocation("url", validInputLocation());
        contributor.setLocation("organization", validInputLocation());
        contributor.setLocation("organizationUrl", validInputLocation());
        contributor.setLocation("roles", validInputLocation());
        contributor.setLocation("timezone", validInputLocation());
        contributor.setLocation("properties", validInputLocation());

        return contributor;
    }

    protected ContributorDTO validContributorDTO(){
        return new ContributorDTO(
                "John Doe",
                "john.doe@example.com",
                "https://github.com/johndoe",
                "Example Organization",
                "https://www.example.com",
                List.of("Core Dev"),
                "UTC+1:00",
                validProperties(),
                null,
                validInputLocationDTO(),
                validInputLocationDTO(),
                validInputLocationDTO(),
                validInputLocationDTO(),
                validInputLocationDTO(),
                validInputLocationDTO(),
                validInputLocationDTO(),
                validInputLocationDTO(),
                validInputLocationDTO()

        );
    }

    protected ContributorDTO emptyContributorDTO(){
        return new ContributorDTO(
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );
    }

    protected void assertEquals(ContributorDTO contributorDTO, Contributor contributor) throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        assertTrue(bothAreEmptyOrBothArePresent(contributorDTO, contributor));

        if(bothArePresent(contributor, contributorDTO)){
            assertThat(contributorDTO.name()).isEqualTo(contributor.getName());
            assertThat(contributorDTO.email()).isEqualTo(contributor.getEmail());
            assertThat(contributorDTO.url()).isEqualTo(contributor.getUrl());
            assertThat(contributorDTO.organization()).isEqualTo(contributor.getOrganization());
            assertThat(contributorDTO.organizationUrl()).isEqualTo(contributor.getOrganizationUrl());
            assertListsAreEqual(contributorDTO.roles(), contributor.getRoles(), (roleDTO, role) -> assertThat(roleDTO).isEqualTo(role));
            assertThat(contributorDTO.timezone()).isEqualTo(contributor.getTimezone());
            assertEquals(contributorDTO.properties(), contributor.getProperties());
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
            assertTrue(bothAreEmptyOrBothArePresent(contributorDTO.propertiesLocation(), contributor.getLocation("properties")));
            assertEquals(contributorDTO.propertiesLocation(), contributor.getLocation("properties"));
        }
    }

    private void assertEquals(Properties properties1, Properties properties2){
        if(bothArePresent(properties1, properties2)){
            assertThat(properties1).isEqualTo(properties2);
        } else {
            assertTrue(bothAreEmpty(properties1, properties2) || (properties1 == null && properties2.isEmpty()) || (properties2 == null && properties1.isEmpty()));
        }
    }

    protected Developer validDeveloper(){
        Developer developer = SuperClassMapper.mapFields(validContributor(), Developer.class);
        developer.setId("DevId123");

        return developer;
    }

    protected DeveloperDTO validDeveloperDTO(){
        return new DeveloperDTO(validContributorDTO(), "DevId123");
    }

    protected void assertEquals(DeveloperDTO developerDTO, Developer developer) throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
       if(developerDTO.contributor() == null){
           assertEquals(emptyContributorDTO(), developer);
       } else {
           assertEquals(developerDTO.contributor(), developer);
       }
       
       assertThat(developerDTO.id()).isEqualTo(developer.getId());
    }

    protected Organization validOrganization(){
        var organization = new Organization();
        organization.setName("Example Organization");
        organization.setUrl("https://www.example.com");
        organization.setLocation("", validInputLocation());
        organization.setLocation("name", validInputLocation());
        organization.setLocation("url", validInputLocation());

        return organization;
    }

    protected OrganizationDTO validOrganizationDTO(){
        return new OrganizationDTO(
                "Example Organization",
                "https://www.example.com",
                null,
                validInputLocationDTO(),
                validInputLocationDTO(),
                validInputLocationDTO()
        );
    }

    protected void assertEquals(OrganizationDTO organizationDTO, Organization organization) throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        assertTrue(bothAreEmptyOrBothArePresent(organizationDTO, organization));

        if(bothArePresent(organizationDTO, organization)){
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
    }

    protected Notifier validNotifier(){
        var notifier = new Notifier();
        notifier.setType("mail");
        notifier.setSendOnError(true);
        notifier.setSendOnFailure(true);
        notifier.setSendOnSuccess(true);
        notifier.setSendOnWarning(true);
        notifier.setAddress("mail@address");
        notifier.setConfiguration(validProperties());
        notifier.setLocation("", validInputLocation());
        notifier.setLocation("type", validInputLocation());
        notifier.setLocation("sendOnError", validInputLocation());
        notifier.setLocation("sendOnFailure", validInputLocation());
        notifier.setLocation("sendOnSuccess", validInputLocation());
        notifier.setLocation("sendOnWarning", validInputLocation());
        notifier.setLocation("address", validInputLocation());
        notifier.setLocation("configuration", validInputLocation());

        return notifier;
    }

    protected NotifierDTO validNotifierDTO(){
        return new NotifierDTO(
                "mail",
                true,
                true,
                true,
                true,
                "mail@address",
                validProperties(),
                null,
                validInputLocationDTO(),
                validInputLocationDTO(),
                validInputLocationDTO(),
                validInputLocationDTO(),
                validInputLocationDTO(),
                validInputLocationDTO(),
                validInputLocationDTO(),
                validInputLocationDTO()
        );
    }

    protected void assertEquals(NotifierDTO notifierDTO, Notifier notifier) throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        assertTrue(bothAreEmptyOrBothArePresent(notifier, notifierDTO));

        if(bothArePresent(notifier,notifierDTO)){
            assertThat(notifierDTO.type()).isEqualTo(notifier.getType());
            assertThat(notifierDTO.sendOnError()).isEqualTo(notifier.isSendOnError());
            assertThat(notifierDTO.sendOnFailure()).isEqualTo(notifier.isSendOnFailure());
            assertThat(notifierDTO.sendOnSuccess()).isEqualTo(notifier.isSendOnSuccess());
            assertThat(notifierDTO.sendOnWarning()).isEqualTo(notifier.isSendOnWarning());
            assertThat(notifierDTO.address()).isEqualTo(notifier.getAddress());
            assertEquals(notifierDTO.configuration(), notifier.getConfiguration());
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
    }

    protected MailingList validMailingList(){
        var mailingList = new MailingList();
        mailingList.setName("User List");
        mailingList.setSubscribe("user-subscribe@example.com");
        mailingList.setUnsubscribe("user-unsubscribe@example.com");
        mailingList.setPost("user-post@example.com");
        mailingList.setArchive("https://example.com/mailing-list-archive");
        mailingList.setOtherArchives(List.of("https://example.com/mailing-list-archive/other"));
        mailingList.setLocation("", validInputLocation());
        mailingList.setLocation("name", validInputLocation());
        mailingList.setLocation("subscribe", validInputLocation());
        mailingList.setLocation("unsubscribe", validInputLocation());
        mailingList.setLocation("post", validInputLocation());
        mailingList.setLocation("archive", validInputLocation());
        mailingList.setLocation("otherArchives", validInputLocation());

        return mailingList;
    }

    protected MailingListDTO validMailingListDTO(){
        return new MailingListDTO(
                "User List",
                "user-subscribe@example.com",
                "user-unsubscribe@example.com",
                "user-post@example.com",
                "https://example.com/mailing-list-archive",
                List.of("https://example.com/mailing-list-archive/other"),
                null,
                validInputLocationDTO(),
                validInputLocationDTO(),
                validInputLocationDTO(),
                validInputLocationDTO(),
                validInputLocationDTO(),
                validInputLocationDTO(),
                validInputLocationDTO()
        );
    }

    protected void assertEquals(MailingListDTO mailingListDTO, MailingList mailingList) throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        assertTrue(bothAreEmptyOrBothArePresent(mailingListDTO, mailingList));

        if(bothArePresent(mailingList, mailingListDTO)){
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
    }

    protected ActivationProperty validActivationProperty(){
        var activationProperty = new ActivationProperty();
        activationProperty.setName("some profile");
        activationProperty.setValue("some value");
        activationProperty.setLocation("", validInputLocation());
        activationProperty.setLocation("name", validInputLocation());
        activationProperty.setLocation("value", validInputLocation());

        return activationProperty;
    }

    protected ActivationPropertyDTO validActivationPropertyDTO(){
        return new ActivationPropertyDTO(
                "some profile",
                "some value",
                null,
                validInputLocationDTO(),
                validInputLocationDTO(),
                validInputLocationDTO()
        );
    }

    protected void assertEquals(ActivationPropertyDTO activationPropertyDTO, ActivationProperty activationProperty) throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        assertTrue(bothAreEmptyOrBothArePresent(activationPropertyDTO, activationProperty));

        if(bothArePresent(activationProperty, activationPropertyDTO)){
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
    }

    protected ActivationOS validActivationOS(){
        var activationOS = new ActivationOS();
        activationOS.setName("Windows XP");
        activationOS.setFamily("windows");
        activationOS.setArch("AMD");
        activationOS.setVersion("42");
        activationOS.setLocation("", validInputLocation());
        activationOS.setLocation("name", validInputLocation());
        activationOS.setLocation("family", validInputLocation());
        activationOS.setLocation("arch", validInputLocation());
        activationOS.setLocation("version", validInputLocation());

        return activationOS;
    }

    protected ActivationOsDTO validActivationOsDTO(){
        return new ActivationOsDTO(
                "Windows XP",
                "windows",
                "AMD",
                "42",
                null,
                validInputLocationDTO(),
                validInputLocationDTO(),
                validInputLocationDTO(),
                validInputLocationDTO(),
                validInputLocationDTO()
        );
    }

    protected void assertEquals(ActivationOsDTO activationOsDTO, ActivationOS activationOS) throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        assertTrue(bothAreEmptyOrBothArePresent(activationOsDTO, activationOS));

        if(bothArePresent(activationOS, activationOsDTO)){
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
    }

    protected ActivationFile validActivationFile(){
        var activationFile = new ActivationFile();
        activationFile.setMissing("file.xml");
        activationFile.setExists("file2.xml");
        activationFile.setLocation("", validInputLocation());
        activationFile.setLocation("missing", validInputLocation());
        activationFile.setLocation("exists", validInputLocation());

        return activationFile;
    }

    protected ActivationFileDTO validActivationFileDTO(){
        return new ActivationFileDTO(
                "file.xml",
                "file2.xml",
                null,
                validInputLocationDTO(),
                validInputLocationDTO(),
                validInputLocationDTO()
        );
    }

    protected void assertEquals(ActivationFileDTO activationFileDTO, ActivationFile activationFile) throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        assertTrue(bothAreEmptyOrBothArePresent(activationFileDTO, activationFile));

        if(bothArePresent(activationFile, activationFileDTO)){
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
    }

    protected Activation validActivation(){
        Activation activation = new Activation();
        activation.setJdk("21");
        activation.setOs(validActivationOS());
        activation.setProperty(validActivationProperty());
        activation.setFile(validActivationFile());
        activation.setLocation("", validInputLocation());
        activation.setLocation("activeByDefault", validInputLocation());
        activation.setLocation("jdk", validInputLocation());
        activation.setLocation("os", validInputLocation());
        activation.setLocation("property", validInputLocation());
        activation.setLocation("file", validInputLocation());

        return activation;
    }

    protected ActivationDTO validActivationDTO(){
        return new ActivationDTO(
                true,
                "21",
                validActivationOsDTO(),
                validActivationPropertyDTO(),
                validActivationFileDTO(),
                null,
                validInputLocationDTO(),
                validInputLocationDTO(),
                validInputLocationDTO(),
                validInputLocationDTO(),
                validInputLocationDTO(),
                validInputLocationDTO()
        );
    }

    protected void assertEquals(ActivationDTO activationDTO, Activation activation) throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        assertTrue(bothAreEmptyOrBothArePresent(activationDTO, activation));

        if(bothArePresent(activationDTO, activation)){
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
            assertEquals(activationDTO.fileLocation(), activation.getLocation("file"));
        }
    }

    protected Site validSite(){
        var site = new Site();
        site.setId("site");
        site.setName("siteName");
        site.setUrl("protocol://hostname/path");
        site.setLocation("", validInputLocation());
        site.setLocation("id", validInputLocation());
        site.setLocation("name", validInputLocation());
        site.setLocation("url", validInputLocation());
        site.setLocation("childSiteUrlInheritAppendPath", validInputLocation());

        return site;
    }

    protected SiteDTO validSiteDTO(){
        return new SiteDTO(
                "site",
                "siteName",
                "protocol://hostname/path",
                false,
                null,
                validInputLocationDTO(),
                validInputLocationDTO(),
                validInputLocationDTO(),
                validInputLocationDTO(),
                validInputLocationDTO()
        );
    }

    protected void assertEquals(SiteDTO siteDTO, Site site) throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        assertTrue(bothAreEmptyOrBothArePresent(siteDTO, site));

        if(bothArePresent(site, siteDTO)){
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
    }

    protected Relocation validRelocation(){
        var relocation = new Relocation();
        relocation.setGroupId("some.group");
        relocation.setArtifactId("Artifact123");
        relocation.setVersion("1.0.1");
        relocation.setMessage("reason");
        relocation.setLocation("", validInputLocation());
        relocation.setLocation("groupId", validInputLocation());
        relocation.setLocation("artifactId", validInputLocation());
        relocation.setLocation("version", validInputLocation());
        relocation.setLocation("message", validInputLocation());

        return relocation;
    }

    protected RelocationDTO validRelocationDTO(){
        return new RelocationDTO(
                "some.group",
                "Artifact123",
                "1.0.1",
                "reason",
                null,
                validInputLocationDTO(),
                validInputLocationDTO(),
                validInputLocationDTO(),
                validInputLocationDTO(),
                validInputLocationDTO()
        );
    }

    protected void assertEquals(RelocationDTO relocationDTO, Relocation relocation) throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        assertTrue(bothAreEmptyOrBothArePresent(relocationDTO, relocation));

        if(bothArePresent(relocationDTO, relocation)){
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
    }

    protected IssueManagement validIssueManagement(){
        var issueManagement = new IssueManagement();
        issueManagement.setSystem("JIRA");
        issueManagement.setUrl("https://example.com/jira");
        issueManagement.setLocation("", validInputLocation());
        issueManagement.setLocation("system", validInputLocation());
        issueManagement.setLocation("url", validInputLocation());

        return issueManagement;
    }

    protected IssueManagementDTO validIssueManagementDTO(){
        return new IssueManagementDTO(
                "JIRA",
                "https://example.com/jira",
                null,
                validInputLocationDTO(),
                validInputLocationDTO(),
                validInputLocationDTO()
        );
    }

    protected void assertEquals(IssueManagementDTO issueManagementDTO, IssueManagement issueManagement) throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        assertTrue(bothAreEmptyOrBothArePresent(issueManagementDTO, issueManagement));

        if(bothArePresent(issueManagement, issueManagementDTO)){
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
    }

    protected CiManagement validCiManagement(){
        var ciManagement = new CiManagement();
        ciManagement.setSystem("Jenkins");
        ciManagement.setUrl("https://example.com/jenkins");
        ciManagement.setNotifiers(List.of(validNotifier()));
        ciManagement.setLocation("", validInputLocation());
        ciManagement.setLocation("system", validInputLocation());
        ciManagement.setLocation("url", validInputLocation());
        ciManagement.setLocation("notifiers", validInputLocation());

        return ciManagement;
    }

    protected CiManagementDTO validCiManagementDTO(){
        return new CiManagementDTO(
                "Jenkins",
                "https://example.com/jenkins",
                List.of(validNotifierDTO()),
                null,
                validInputLocationDTO(),
                validInputLocationDTO(),
                validInputLocationDTO(),
                validInputLocationDTO()
        );
    }

    protected void assertEquals(CiManagementDTO ciManagementDTO, CiManagement ciManagement) throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        assertTrue(bothAreEmptyOrBothArePresent(ciManagementDTO, ciManagement));

        if(bothArePresent(ciManagementDTO, ciManagement)){
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
    }

    protected RepositoryPolicy validRepositoryPolicy(){
        var repositoryPolicy = new RepositoryPolicy();
        repositoryPolicy.setEnabled(true);
        repositoryPolicy.setUpdatePolicy("daily");
        repositoryPolicy.setChecksumPolicy("ignore");
        repositoryPolicy.setLocation("", validInputLocation());
        repositoryPolicy.setLocation("enabled", validInputLocation());
        repositoryPolicy.setLocation("updatePolicy", validInputLocation());
        repositoryPolicy.setLocation("checksumPolicy", validInputLocation());

        return repositoryPolicy;
    }

    protected RepositoryPolicyDTO validRepositoryPolicyDTO(){
        return new RepositoryPolicyDTO(
                true,
                "daily",
                "ignore",
                null,
                validInputLocationDTO(),
                validInputLocationDTO(),
                validInputLocationDTO(),
                validInputLocationDTO()
        );
    }

    protected void assertEquals(RepositoryPolicyDTO repositoryPolicyDTO, RepositoryPolicy repositoryPolicy) throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        assertTrue(bothAreEmptyOrBothArePresent(repositoryPolicyDTO, repositoryPolicy));

        if(bothArePresent(repositoryPolicy, repositoryPolicyDTO)){
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
    }

    protected RepositoryBase validRepositoryBase(){
        var repositoryBase = new RepositoryBase();
        repositoryBase.setId("id");
        repositoryBase.setName("Name");
        repositoryBase.setUrl("protocol://hostname/path");
        repositoryBase.setLayout("some Layout information");
        repositoryBase.setLocation("", validInputLocation());
        repositoryBase.setLocation("id", validInputLocation());
        repositoryBase.setLocation("name", validInputLocation());
        repositoryBase.setLocation("url", validInputLocation());
        repositoryBase.setLocation("layout", validInputLocation());

        return repositoryBase;
    }

    protected RepositoryBaseDTO validRepositoryBaseDTO(){
        return new RepositoryBaseDTO(
                "id",
                "name",
                "protocol://hostname/path",
                "some layout information",
                null,
                validInputLocationDTO(),
                validInputLocationDTO(),
                validInputLocationDTO(),
                validInputLocationDTO(),
                validInputLocationDTO()
        );
    }

    protected RepositoryBaseDTO emptyRepositoryBaseDTO(){
        return new RepositoryBaseDTO(
                null,
                null,
                null,
                "default",
                null,
                null,
                null,
                null,
                null,
                null
        );
    }


    protected void assertEquals(RepositoryBaseDTO repositoryBaseDTO, RepositoryBase repositoryBase) throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        assertTrue(bothAreEmptyOrBothArePresent(repositoryBaseDTO, repositoryBase));

        if(bothArePresent(repositoryBaseDTO, repositoryBase)){
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
    }

    protected Repository validRepository(){
        var repository = SuperClassMapper.mapFields(validRepositoryBase(), Repository.class);
        repository.setReleases(validRepositoryPolicy());
        repository.setSnapshots(validRepositoryPolicy());

        return repository;
    }

    protected RepositoryDTO validRepositoryDTO(){
        return new RepositoryDTO(
                validRepositoryBaseDTO(),
                validRepositoryPolicyDTO(),
                validRepositoryPolicyDTO()
        );
    }

    protected RepositoryDTO emptyRepositoryDTO(){
        return new RepositoryDTO(
                null,
                null,
                null
        );
    }

    protected void assertEquals(RepositoryDTO repositoryDTO, Repository repository) throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        assertTrue(bothAreEmptyOrBothArePresent(repositoryDTO, repository));

        if(bothArePresent(repositoryDTO, repository)){
            if(repositoryDTO.repositoryBase() == null){
                assertEquals(emptyRepositoryBaseDTO(), repository);
            } else {
                assertEquals(repositoryDTO.repositoryBase(), repository);
            }

            assertEquals(repositoryDTO.releases(), repository.getReleases());
            assertEquals(repositoryDTO.snapshots(), repository.getSnapshots());
        }
    }

    protected DeploymentRepository validDeploymentRepository(){
        var deploymentRepository = SuperClassMapper.mapFields(validRepository(), DeploymentRepository.class);
        deploymentRepository.setUniqueVersion(true);

        return deploymentRepository;
    }

    protected DeploymentRepositoryDTO validDeploymentRepositoryDTO(){
        return new DeploymentRepositoryDTO(
                validRepositoryDTO(),
                true
        );
    }

    protected DeploymentRepositoryDTO emptyDeploymentRepositoryDTO(){
        return new DeploymentRepositoryDTO(
                null,
                false
        );
    }

    protected void assertEquals(DeploymentRepositoryDTO deploymentRepositoryDTO, DeploymentRepository deploymentRepository) throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        assertTrue(bothAreEmptyOrBothArePresent(deploymentRepository, deploymentRepositoryDTO));

        if(bothArePresent(deploymentRepository, deploymentRepositoryDTO)){
            if(deploymentRepositoryDTO.repository() == null){
                assertEquals(emptyRepositoryDTO(), deploymentRepository);
            } else {
                assertEquals(deploymentRepositoryDTO.repository(), deploymentRepository);
            }

            assertThat(deploymentRepositoryDTO.uniqueVersion()).isEqualTo(deploymentRepository.isUniqueVersion());
        }
    }

    protected DistributionManagement validDistributionManagement(){
        var distributionManagement = new DistributionManagement();
        distributionManagement.setRepository(validDeploymentRepository());
        distributionManagement.setSnapshotRepository(validDeploymentRepository());
        distributionManagement.setSite(validSite());
        distributionManagement.setDownloadUrl("someurl.com");
        distributionManagement.setRelocation(validRelocation());
        distributionManagement.setStatus("status");
        distributionManagement.setLocation("", validInputLocation());
        distributionManagement.setLocation("repository", validInputLocation());
        distributionManagement.setLocation("snapshotRepository", validInputLocation());
        distributionManagement.setLocation("site", validInputLocation());
        distributionManagement.setLocation("downloadUrl", validInputLocation());
        distributionManagement.setLocation("relocation", validInputLocation());
        distributionManagement.setLocation("status", validInputLocation());

        return distributionManagement;
    }

    protected DistributionManagementDTO validDistributionManagementDTO(){
        return new DistributionManagementDTO(
                validDeploymentRepositoryDTO(),
                validDeploymentRepositoryDTO(),
                validSiteDTO(),
                "someurl.com",
                validRelocationDTO(),
                "status",
                null,
                validInputLocationDTO(),
                validInputLocationDTO(),
                validInputLocationDTO(),
                validInputLocationDTO(),
                validInputLocationDTO(),
                validInputLocationDTO(),
                validInputLocationDTO()
        );
    }

    protected void assertEquals(DistributionManagementDTO distributionManagementDTO, DistributionManagement distributionManagement) throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        assertTrue(bothAreEmptyOrBothArePresent(distributionManagementDTO, distributionManagement));

        if(bothArePresent(distributionManagement, distributionManagementDTO)){
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
    }

    protected ConfigurationContainer validConfigurationContainer(){
        var configurationContainer = new ConfigurationContainer();
        configurationContainer.setInherited(false);
        configurationContainer.setConfiguration(new Object());
        configurationContainer.setLocation("", validInputLocation());
        configurationContainer.setLocation("inherited", validInputLocation());
        configurationContainer.setLocation("configuration", validInputLocation());

        return configurationContainer;
    }

    protected ConfigurationContainerDTO validConfigurationContainerDTO(){
        return new ConfigurationContainerDTO(
                false,
                new Object(),
                null,
                validInputLocationDTO(),
                validInputLocationDTO(),
                validInputLocationDTO(),
                false
        );
    }

    protected ConfigurationContainerDTO emptyConfigurationContainerDTO(){
        return new ConfigurationContainerDTO(
                true,
                null,
                null,
                null,
                null,
                null,
                true
        );
    }

    protected void assertEquals(ConfigurationContainerDTO configurationContainerDTO, ConfigurationContainer configurationContainer) throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        assertTrue(bothAreEmptyOrBothArePresent(configurationContainerDTO, configurationContainer));

        if(bothArePresent(configurationContainer, configurationContainerDTO)){
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
    }

    protected PluginExecution validPluginExecution(){
        var pluginExecution = SuperClassMapper.mapFields(validConfigurationContainer(), PluginExecution.class);
        pluginExecution.setId("Id");
        pluginExecution.setPhase("compile");
        pluginExecution.setGoals(List.of("install"));

        return pluginExecution;
    }

    protected PluginExecutionDTO validPluginExecutionDTO(){
        return new PluginExecutionDTO(
                validConfigurationContainerDTO(),
                "Id",
                "compile",
                List.of("install")
        );
    }

    protected void assertEquals(PluginExecutionDTO pluginExecutionDTO, PluginExecution pluginExecution) throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        assertTrue(bothAreEmptyOrBothArePresent(pluginExecution, pluginExecutionDTO));

        if(bothArePresent(pluginExecution, pluginExecutionDTO)){
            if(pluginExecutionDTO.configurationContainer() == null){
                assertEquals(emptyConfigurationContainerDTO(), pluginExecution);
            } else {
                assertEquals(pluginExecutionDTO.configurationContainer(), pluginExecution);
            }

            assertThat(pluginExecutionDTO.id()).isEqualTo(pluginExecution.getId());
            assertThat(pluginExecutionDTO.phase()).isEqualTo(pluginExecution.getPhase());
            assertListsAreEqual(pluginExecutionDTO.goals(), pluginExecution.getGoals(), String::equals);
        }
    }

    protected Plugin validPlugin(){
        var plugin = SuperClassMapper.mapFields(validConfigurationContainer(), Plugin.class);
        plugin.setGroupId("org.apache.maven.plugins");
        plugin.setArtifactId("Artifact");
        plugin.setVersion("1.0.0-SNAPSHOT");
        plugin.setExtensions(true);
        plugin.setExecutions(List.of(validPluginExecution()));
        plugin.setDependencies(List.of(validDependency()));

        return plugin;
    }

    protected PluginDTO validPluginDTO(){
        return new PluginDTO(
                validConfigurationContainerDTO(),
                "org.apache.maven.plugins",
                "Artifact",
                "1.0.0-SNAPSHOT",
                true,
                List.of(validPluginExecutionDTO()),
                List.of(validDependencyDTO())
        );
    }

    protected void assertEquals(PluginDTO pluginDTO, Plugin plugin) throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        assertTrue(bothAreEmptyOrBothArePresent(plugin, pluginDTO));

        if(bothArePresent(plugin, pluginDTO)){
            if(pluginDTO.configurationContainer() == null){
                assertEquals(emptyConfigurationContainerDTO(), plugin);
            } else {
                assertEquals(pluginDTO.configurationContainer(), plugin);
            }

            assertThat(pluginDTO.groupId()).isEqualTo(plugin.getGroupId());
            assertThat(pluginDTO.artifactId()).isEqualTo(plugin.getArtifactId());
            assertThat(pluginDTO.version()).isEqualTo(plugin.getVersion());
            assertThat(pluginDTO.extensions()).isEqualTo(plugin.isExtensions());
            assertListsAreEqual(pluginDTO.executions(), plugin.getExecutions(), this::assertEquals);
            assertListsAreEqual(pluginDTO.dependencies(), plugin.getDependencies(), this::assertEquals);
        }
    }

    protected PluginContainer validPluginContainer(){
        var pluginContainer = new PluginContainer();
        pluginContainer.setPlugins(List.of(validPlugin()));
        pluginContainer.setLocation("", validInputLocation());
        pluginContainer.setLocation("plugins", validInputLocation());

        return pluginContainer;
    }

    protected PluginContainerDTO validPluginContainerDTO(){
        return new PluginContainerDTO(
                List.of(validPluginDTO()),
                null,
                validInputLocationDTO(),
                validInputLocationDTO()
        );
    }

    protected PluginContainerDTO emptyPluginContainerDTO(){
        return new PluginContainerDTO(
                null,
                null,
                null,
                null
        );
    }

    protected void assertEquals(PluginContainerDTO pluginContainerDTO, PluginContainer pluginContainer) throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        assertTrue(bothAreEmptyOrBothArePresent(pluginContainer, pluginContainerDTO));

        if(bothArePresent(pluginContainer, pluginContainerDTO)){
            assertListsAreEqual(pluginContainerDTO.plugins(), pluginContainer.getPlugins(), this::assertEquals);
            assertLocationsAreEqual(pluginContainerDTO, pluginContainer);

            assertTrue(bothAreEmptyOrBothArePresent(pluginContainerDTO.location(), pluginContainer.getLocation("")));
            assertEquals(pluginContainerDTO.location(), pluginContainer.getLocation(""));
            assertTrue(bothAreEmptyOrBothArePresent(pluginContainerDTO.pluginsLocation(), pluginContainer.getLocation("plugins")));
            assertEquals(pluginContainerDTO.pluginsLocation(), pluginContainer.getLocation("plugins"));
        }
    }
    protected PluginManagement validPluginManagement(){
        var pluginManagement = new PluginManagement();
        pluginManagement.setPlugins(List.of(validPlugin()));
        pluginManagement.setLocation("", validInputLocation());
        pluginManagement.setLocation("plugins", validInputLocation());

        return pluginManagement;
    }

    protected PluginManagementDTO validPluginManagementDTO(){
        return new PluginManagementDTO(validPluginContainerDTO());
    }

    protected void assertEquals(PluginManagementDTO pluginManagementDTO, PluginManagement pluginManagement) throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        if(bothArePresent(pluginManagement, pluginManagementDTO.pluginContainer())){
            assertEquals(pluginManagementDTO.pluginContainer(), pluginManagement);
        }
    }

    protected PluginConfiguration validPluginConfiguration(){
        var pluginConfiguration = SuperClassMapper.mapFields(validPluginContainer(), PluginConfiguration.class);
        pluginConfiguration.setPluginManagement(validPluginManagement());

        return pluginConfiguration;
    }

    protected PluginConfigurationDTO validPluginConfigurationDTO(){
        return new PluginConfigurationDTO(
                validPluginContainerDTO(),
                validPluginManagementDTO()
        );
    }

    protected PluginConfigurationDTO emptyPluginConfigurationDTO(){
        return new PluginConfigurationDTO(
                null,
                null
        );
    }

    protected void assertEquals(PluginConfigurationDTO pluginConfigurationDTO, PluginConfiguration pluginConfiguration) throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        assertTrue(bothAreEmptyOrBothArePresent(pluginConfiguration, pluginConfigurationDTO));

        if(bothArePresent(pluginConfiguration, pluginConfigurationDTO)){
            if(pluginConfigurationDTO.pluginContainer() == null){
                assertEquals(emptyPluginContainerDTO(), pluginConfiguration);
            } else {
                assertEquals(pluginConfigurationDTO.pluginContainer(), pluginConfiguration);
            }

            if(bothArePresent(pluginConfiguration.getPluginManagement(), pluginConfigurationDTO.pluginManagement())){
                assertEquals(pluginConfigurationDTO.pluginManagement(), pluginConfiguration.getPluginManagement());
            }
        }
    }

    protected ReportSet validReportSet(){
        var reportSet = SuperClassMapper.mapFields(validConfigurationContainer(), ReportSet.class);
        reportSet.setId("id");
        reportSet.setReports(List.of("report1"));

        return reportSet;
    }

    protected ReportSetDTO validReportSetDTO(){
        return new ReportSetDTO(
                validConfigurationContainerDTO(),
                "id",
                List.of("report1")
        );
    }

    protected void assertEquals(ReportSetDTO reportSetDTO, ReportSet reportSet) throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        assertTrue(bothAreEmptyOrBothArePresent(reportSet, reportSetDTO));

        if(bothArePresent(reportSet, reportSetDTO)){
            if(reportSetDTO.configurationContainer() == null){
                assertEquals(emptyConfigurationContainerDTO(), reportSet);
            } else {
                assertEquals(reportSetDTO.configurationContainer(), reportSet);
            }

            assertThat(reportSetDTO.id()).isEqualTo(reportSet.getId());
            assertListsAreEqual(reportSet.getReports(), reportSetDTO.reports(), String::equals);
        }
    }

    protected ReportPlugin validReportPlugin(){
        var reportPlugin = SuperClassMapper.mapFields(validConfigurationContainer(), ReportPlugin.class);
        reportPlugin.setGroupId("group.vl");
        reportPlugin.setArtifactId("artifact");
        reportPlugin.setVersion("1.0.0");
        reportPlugin.setReportSets(List.of(validReportSet()));

        return reportPlugin;
    }

    protected ReportPluginDTO validReportPluginDTO(){
        return new ReportPluginDTO(
                validConfigurationContainerDTO(),
                "group.vl",
                "artifact",
                "1.0.0",
                List.of(validReportSetDTO())
        );
    }

    protected void assertEquals(ReportPluginDTO reportPluginDTO, ReportPlugin reportPlugin) throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        assertTrue(bothAreEmptyOrBothArePresent(reportPlugin, reportPluginDTO));

        if(bothArePresent(reportPlugin, reportPluginDTO)){
            if(reportPluginDTO.configurationContainer() == null){
                assertEquals(emptyConfigurationContainerDTO(), reportPlugin);
            } else {
                assertEquals(reportPluginDTO.configurationContainer(), reportPlugin);
            }

            assertThat(reportPluginDTO.groupId()).isEqualTo(reportPlugin.getGroupId());
            assertThat(reportPluginDTO.artifactId()).isEqualTo(reportPlugin.getArtifactId());
            assertThat(reportPluginDTO.version()).isEqualTo(reportPlugin.getVersion());
            assertListsAreEqual(reportPluginDTO.reportSets(), reportPlugin.getReportSets(), this::assertEquals);
        }
    }

    protected Reporting validReporting(){
        var reporting = new Reporting();
        reporting.setExcludeDefaults(false);
        reporting.setOutputDirectory("/site");
        reporting.setPlugins(List.of(validReportPlugin()));
        reporting.setLocation("", validInputLocation());
        reporting.setLocation("excludeDefaults", validInputLocation());
        reporting.setLocation("outputDirectory", validInputLocation());
        reporting.setLocation("plugins", validInputLocation());

        return reporting;
    }

    protected ReportingDTO validReportingDTO(){
        return new ReportingDTO(
                false,
                "/site",
                List.of(validReportPluginDTO()),
                null,
                validInputLocationDTO(),
                validInputLocationDTO(),
                validInputLocationDTO(),
                validInputLocationDTO()
        );
    }

    protected void assertEquals(ReportingDTO reportingDTO, Reporting reporting) throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        assertTrue(bothAreEmptyOrBothArePresent(reporting, reportingDTO));

        if(bothArePresent(reporting, reportingDTO)){
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
    }

    protected PatternSet validPatternSet(){
        var patternSet = new PatternSet();
        patternSet.setIncludes(List.of("inclusion1", "inclusion2"));
        patternSet.setExcludes(List.of("exclusion1", "exclusion2"));
        patternSet.setLocation("", validInputLocation());
        patternSet.setLocation("includes", validInputLocation());
        patternSet.setLocation("excludes", validInputLocation());

        return patternSet;
    }

    protected PatternSetDTO validPatternSetDTO(){
        return new PatternSetDTO(
                List.of("inclusion1", "inclusion2"),
                List.of("exclusion1", "exclusion2"),
                null,
                validInputLocationDTO(),
                validInputLocationDTO(),
                validInputLocationDTO()
        );
    }

    protected PatternSetDTO emptyPatternSetDTO(){
        return new PatternSetDTO(
                null,
                null,
                null,
                null,
                null,
                null
        );
    }

    protected void assertEquals(PatternSetDTO patternSetDTO, PatternSet patternSet) throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        assertTrue(bothAreEmptyOrBothArePresent(patternSet, patternSetDTO));

        if(bothArePresent(patternSet, patternSetDTO)){
            assertListsAreEqual(patternSet.getIncludes(), patternSetDTO.includes(), String::equals);
            assertListsAreEqual(patternSet.getExcludes(), patternSetDTO.excludes(), String::equals);
            assertLocationsAreEqual(patternSetDTO, patternSet);

            assertTrue(bothAreEmptyOrBothArePresent(patternSetDTO.location(), patternSet.getLocation("")));
            assertEquals(patternSetDTO.location(), patternSet.getLocation(""));
            assertTrue(bothAreEmptyOrBothArePresent(patternSetDTO.includesLocation(), patternSet.getLocation("includes")));
            assertEquals(patternSetDTO.includesLocation(), patternSet.getLocation("includes"));
            assertTrue(bothAreEmptyOrBothArePresent(patternSetDTO.excludesLocation(), patternSet.getLocation("excludes")));
            assertEquals(patternSetDTO.excludesLocation(), patternSet.getLocation("excludes"));
        }
    }

    protected FileSet validFileSet(){
        var fileSet = SuperClassMapper.mapFields(validPatternSet(), FileSet.class);
        fileSet.setDirectory("/someDir");

        return fileSet;
    }

    protected FileSetDTO validFileSetDTO(){
        return new FileSetDTO(
                validPatternSetDTO(),
                "/someDir"
        );
    }

    protected FileSetDTO emptyFileSetDTO(){
        return new FileSetDTO(
                null,
                null
        );
    }

    protected void assertEquals(FileSetDTO fileSetDTO, FileSet fileSet) throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        assertTrue(bothAreEmptyOrBothArePresent(fileSetDTO, fileSet));

        if(bothArePresent(fileSetDTO, fileSet)) {
            if (fileSetDTO.patternSet() == null) {
                assertEquals(emptyPatternSetDTO(), fileSet);
            } else {
                assertEquals(fileSetDTO.patternSet(), fileSet);
            }
            assertThat(fileSetDTO.directory()).isEqualTo(fileSet.getDirectory());
        }
    }

    protected Resource validResource(){
        var resource = SuperClassMapper.mapFields(validFileSet(), Resource.class);
        resource.setTargetPath("some/target/Path");
        resource.setFiltering(true);
        resource.setMergeId("mergeID");

        return resource;
    }

    protected ResourceDTO validResourceDTO(){
        return new ResourceDTO(
                validFileSetDTO(),
                "some/target/Path",
                true,
                "mergeId"
        );
    }

    protected void assertEquals(ResourceDTO resourceDTO, Resource resource) throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        assertTrue(bothAreEmptyOrBothArePresent(resourceDTO, resource));

        if(bothArePresent(resource, resourceDTO)){
            if(resourceDTO.fileSet() == null){
                assertEquals(emptyFileSetDTO(), resource);
            } else {
                assertEquals(resourceDTO.fileSet(), resource);
            }
            assertThat(resourceDTO.targetPath()).isEqualTo(resource.getTargetPath());
            assertThat(resourceDTO.filtering()).isEqualTo(resource.isFiltering());
            assertThat(resourceDTO.mergeId()).isEqualTo(resource.getMergeId());
        }
    }

    protected BuildBase validBuildBase(){
        var buildBase = SuperClassMapper.mapFields(validPluginConfiguration(), BuildBase.class);
        buildBase.setDefaultGoal("clean install");
        buildBase.setResources(List.of(validResource()));
        buildBase.setTestResources(List.of(validResource()));
        buildBase.setDirectory("/someDir");
        buildBase.setFinalName("finalName");
        buildBase.setFilters(List.of("Filter1"));

        return buildBase;
    }

    protected BuildBaseDTO validBuildBaseDTO(){
        return new BuildBaseDTO(
                validPluginConfigurationDTO(),
                "clean install",
                List.of(validResourceDTO()),
                List.of(validResourceDTO()),
                "/someDir",
                "finalName",
                List.of("Filter1")
        );
    }

    protected BuildBaseDTO emptyBuildBaseDTO(){
        return new BuildBaseDTO(
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );
    }

    protected void assertEquals(BuildBaseDTO buildBaseDTO, BuildBase buildBase) throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        assertTrue(bothAreEmptyOrBothArePresent(buildBase, buildBaseDTO));

        if(bothArePresent(buildBase, buildBaseDTO)){
            if(buildBaseDTO.pluginConfiguration() == null){
                assertEquals(emptyPluginConfigurationDTO(), buildBase);
            } else {
                assertEquals(buildBaseDTO.pluginConfiguration(), buildBase);
            }

            assertThat(buildBaseDTO.defaultGoal()).isEqualTo(buildBase.getDefaultGoal());
            assertListsAreEqual(buildBaseDTO.resources(), buildBase.getResources(), this::assertEquals);
            assertListsAreEqual(buildBaseDTO.testResources(), buildBase.getTestResources(), this::assertEquals);
            assertThat(buildBaseDTO.directory()).isEqualTo(buildBase.getDirectory());
            assertThat(buildBaseDTO.finalName()).isEqualTo(buildBase.getFinalName());
            assertListsAreEqual(buildBaseDTO.filters(), buildBase.getFilters(), String::equals);
        }
    }

    protected Extension validExtension(){
        var extension = new Extension();
        extension.setGroupId("com.example");
        extension.setArtifactId("my-maven-extension");
        extension.setVersion("1.0.0");
        extension.setLocation("", validInputLocation());
        extension.setLocation("groupId", validInputLocation());
        extension.setLocation("artifactId", validInputLocation());
        extension.setLocation("version", validInputLocation());

        return extension;
    }

    protected ExtensionDTO validExtensionDTO(){
        return new ExtensionDTO(
                "com.example",
                "my-maven-extension",
                "1.0.0",
                null,
                validInputLocationDTO(),
                validInputLocationDTO(),
                validInputLocationDTO(),
                validInputLocationDTO()
        );
    }

    protected void assertEquals(ExtensionDTO extensionDTO, Extension extension) throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        assertTrue(bothAreEmptyOrBothArePresent(extension, extensionDTO));

        if(bothArePresent(extension, extensionDTO)){
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
    }

    protected Build validBuild(){
        var build = SuperClassMapper.mapFields(validBuildBase(), Build.class);
        build.setSourceDirectory("src/main/java");
        build.setScriptSourceDirectory("src/main/java");
        build.setTestSourceDirectory("src/test/java");
        build.setOutputDirectory("target/classes");
        build.setTestOutputDirectory("target/test-classes");
        build.setExtensions(List.of(validExtension()));

        return build;
    }

    protected BuildDTO validBuildDTO(){
        return new BuildDTO(
                validBuildBaseDTO(),
                "src/main/java",
                "src/main/java",
                "src/test/java",
                "target/classes",
                "target/test-classes",
                List.of(validExtensionDTO())
        );
    }

    protected void assertEquals(BuildDTO buildDTO, Build build) throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        assertTrue(bothAreEmptyOrBothArePresent(buildDTO, build));

        if(bothArePresent(build, buildDTO)){
            if(buildDTO.buildBase() == null){
                assertEquals(emptyBuildBaseDTO(), build);
            } else {
                assertEquals(buildDTO.buildBase(), build);
            }

            assertThat(buildDTO.sourceDirectory()).isEqualTo(build.getSourceDirectory());
            assertThat(buildDTO.scriptSourceDirectory()).isEqualTo(build.getScriptSourceDirectory());
            assertThat(buildDTO.testSourceDirectory()).isEqualTo(build.getTestSourceDirectory());
            assertThat(buildDTO.outputDirectory()).isEqualTo(build.getOutputDirectory());
            assertThat(buildDTO.testOutputDirectory()).isEqualTo(build.getTestOutputDirectory());
            assertListsAreEqual(buildDTO.extensions(), build.getExtensions(), this::assertEquals);
        }
    }

    protected License validLicense(){
        var license = new License();
        license.setName("Apache License 2.0");
        license.setUrl("https://www.apache.org/licenses/LICENSE-2.0");
        license.setDistribution("repo");
        license.setComments("some Comment");
        license.setLocation("", validInputLocation());
        license.setLocation("name", validInputLocation());
        license.setLocation("url", validInputLocation());
        license.setLocation("distribution", validInputLocation());
        license.setLocation("comments", validInputLocation());

        return license;
    }

    protected LicenseDTO validLicenseDTO(){
        return new LicenseDTO(
                "Apache License 2.0",
                "https://www.apache.org/licenses/LICENSE-2.0",
                "repo",
                "some Comment",
                null,
                validInputLocationDTO(),
                validInputLocationDTO(),
                validInputLocationDTO(),
                validInputLocationDTO(),
                validInputLocationDTO()
        );
    }

    protected void assertEquals(LicenseDTO licenseDTO, License license) throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        assertTrue(bothAreEmptyOrBothArePresent(licenseDTO, license));

        if(bothArePresent(license, licenseDTO)){
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
    }

    protected Scm validScm(){
        var scm = new Scm();
        scm.setConnection("scm:git:https://github.com/example/my-project.git");
        scm.setUrl("https://github.com/example/my-project");
        scm.setTag("RELEASE_1.0.0");
        scm.setDeveloperConnection("scm:git:https://github.com/example/my-project.git");
        scm.setChildScmConnectionInheritAppendPath(true);
        scm.setChildScmDeveloperConnectionInheritAppendPath(true);
        scm.setChildScmUrlInheritAppendPath(false);
        scm.setOtherLocation("someStringKey", validInputLocation());
        scm.setLocation("", validInputLocation());
        scm.setLocation("connection", validInputLocation());
        scm.setLocation("developerConnection", validInputLocation());
        scm.setLocation("tag", validInputLocation());
        scm.setLocation("url", validInputLocation());
        scm.setLocation("childScmConnectionInheritAppendPath", validInputLocation());
        scm.setLocation("childScmDeveloperConnectionInheritAppendPath", validInputLocation());
        scm.setLocation("childScmUrlInheritAppendPath", validInputLocation());

        return scm;
    }

    protected ScmDTO validScmDTO(){
        return new ScmDTO(
                "scm:git:https://github.com/example/my-project.git",
                "scm:git:https://github.com/example/my-project.git",
                "RELEASE_1.0.0",
                "https://github.com/example/my-project",
                false,
                false,
                false,
                null,
                validInputLocationDTO(),
                validInputLocationDTO(),
                validInputLocationDTO(),
                validInputLocationDTO(),
                validInputLocationDTO(),
                validInputLocationDTO(),
                validInputLocationDTO(),
                validInputLocationDTO()
        );
    }

    protected void assertEquals(ScmDTO scmDTO, Scm scm) throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        assertTrue(bothAreEmptyOrBothArePresent(scm, scmDTO));

        if(bothArePresent(scm, scmDTO)) {
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
            assertTrue(bothAreEmptyOrBothArePresent(scmDTO.childScmUrlInheritAppendPathLocation(), scm.getLocation("childScmUrlInheritAppendPath")));
            assertEquals(scmDTO.childScmUrlInheritAppendPathLocation(), scm.getLocation("childScmUrlInheritAppendPath"));
        }
    }

    protected Prerequisites validPrerequisites(){
        var prerequisites = new Prerequisites();
        prerequisites.setMaven("3.8.1");
        prerequisites.setLocation("", validInputLocation());
        prerequisites.setLocation("maven", validInputLocation());

        return prerequisites;
    }

    protected PrerequisitesDTO validPrerequisitesDTO(){
        return new PrerequisitesDTO(
                "3.8.1",
                null,
                validInputLocationDTO(),
                validInputLocationDTO()
        );
    }

    protected void assertEquals(PrerequisitesDTO prerequisitesDTO, Prerequisites prerequisites) throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        assertTrue(bothAreEmptyOrBothArePresent(prerequisites, prerequisitesDTO));

        if(bothArePresent(prerequisites, prerequisitesDTO)){
            assertThat(prerequisitesDTO.maven()).isEqualTo(prerequisites.getMaven());
            assertLocationsAreEqual(prerequisitesDTO, prerequisites);

            assertTrue(bothAreEmptyOrBothArePresent(prerequisitesDTO.location(), prerequisites.getLocation("")));
            assertEquals(prerequisitesDTO.location(),prerequisites.getLocation(""));
            assertTrue(bothAreEmptyOrBothArePresent(prerequisitesDTO.mavenLocation(), prerequisites.getLocation("maven")));
            assertEquals(prerequisitesDTO.mavenLocation(),prerequisites.getLocation("maven"));
        }
    }

    protected Parent validParent(){
        var parent = new Parent();
        parent.setGroupId("com.example");
        parent.setArtifactId("my-project");
        parent.setVersion("1.0.0");
        parent.setRelativePath("/relative/path");
        parent.setLocation("", validInputLocation());
        parent.setLocation("groupId", validInputLocation());
        parent.setLocation("artifactId", validInputLocation());
        parent.setLocation("version", validInputLocation());
        parent.setLocation("relativePath", validInputLocation());

        return parent;
    }

    protected ParentDTO validParentDTO(){
        return new ParentDTO(
                "com.example",
                "my-project",
                "1.0.0",
                "/relative/path",
                null,
                validInputLocationDTO(),
                validInputLocationDTO(),
                validInputLocationDTO(),
                validInputLocationDTO(),
                validInputLocationDTO()
        );
    }

    protected void assertEquals(ParentDTO parentDTO, Parent parent) throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        assertTrue(bothAreEmptyOrBothArePresent(parentDTO, parent));

        if(bothArePresent(parent, parentDTO)){
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
    }

    protected ModelBase validModelBase(){
        var modelBase = new ModelBase();
        modelBase.setModules(List.of("module1", "module2"));
        modelBase.setDistributionManagement(validDistributionManagement());
        modelBase.setProperties(validProperties());
        modelBase.setDependencyManagement(validDependencyManagement());
        modelBase.setDependencies(List.of(validDependency()));
        modelBase.setRepositories(List.of(validRepository()));
        modelBase.setPluginRepositories(List.of(validRepository()));
        modelBase.setReporting(validReporting());
        modelBase.setLocation("", validInputLocation());
        modelBase.setLocation("modules", validInputLocation());
        modelBase.setLocation("distributionManagement", validInputLocation());
        modelBase.setLocation("properties", validInputLocation());
        modelBase.setLocation("dependencyManagement", validInputLocation());
        modelBase.setLocation("dependencies", validInputLocation());
        modelBase.setLocation("repositories", validInputLocation());
        modelBase.setLocation("pluginRepositories", validInputLocation());
        modelBase.setLocation("reporting", validInputLocation());

        return modelBase;
    }

    protected ModelBaseDTO validModelBaseDTO(){
        return new ModelBaseDTO(
                List.of("module1", "module2"),
                validDistributionManagementDTO(),
                validProperties(),
                validDependencyManagementDTO(),
                List.of(validDependencyDTO()),
                List.of(validRepositoryDTO()),
                List.of(validRepositoryDTO()),
                validReportingDTO(),
                null,
                validInputLocationDTO(),
                validInputLocationDTO(),
                validInputLocationDTO(),
                validInputLocationDTO(),
                validInputLocationDTO(),
                validInputLocationDTO(),
                validInputLocationDTO(),
                validInputLocationDTO(),
                validInputLocationDTO()
        );
    }

    protected ModelBaseDTO emptyModelBaseDTO(){
        return new ModelBaseDTO(
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );
    }

    protected void assertEquals(ModelBaseDTO modelBaseDTO, ModelBase modelBase) throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        assertTrue(bothAreEmptyOrBothArePresent(modelBase, modelBaseDTO));

        if(bothArePresent(modelBase, modelBaseDTO)){
            assertListsAreEqual(modelBaseDTO.modules(), modelBase.getModules(), String::equals);
            assertEquals(modelBaseDTO.distributionManagement(), modelBase.getDistributionManagement());
            assertEquals(modelBaseDTO.properties(), modelBase.getProperties());
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
    }

    protected Profile validProfile(){
        var profile = SuperClassMapper.mapFields(validModelBase(), Profile.class);
        profile.setActivation(validActivation());
        profile.setId("someId");
        profile.setBuild(validBuildBase());

        return profile;
    }

    protected ProfileDTO validProfileDTO(){
        return new ProfileDTO(
                validModelBaseDTO(),
                "someId",
                validActivationDTO(),
                validBuildBaseDTO()
        );
    }

    protected void assertEquals(ProfileDTO profileDTO, Profile profile) throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        assertTrue(bothAreEmptyOrBothArePresent(profileDTO, profile));

        if(bothArePresent(profileDTO, profile)){
            if(profileDTO.modelBase() == null){
                assertEquals(emptyModelBaseDTO(), profile);
            } else {
                assertEquals(profileDTO.modelBase(), profile);
            }

            assertThat(profileDTO.id()).isEqualTo(profile.getId());
            assertEquals(profileDTO.activation(), profile.getActivation());
            assertEquals(profileDTO.build(), profile.getBuild());
        }
    }

    protected Model validModel(){
        var model = SuperClassMapper.mapFields(validModelBase(), Model.class);
        model.setModelVersion("4.0.0");
        model.setParent(validParent());
        model.setGroupId("com.example");
        model.setArtifactId("my-project");
        model.setVersion("1.0.0");
        model.setPackaging("pom");
        model.setName("My Project");
        model.setDescription("Description of my project");
        model.setUrl("https://www.somewebsite.com");
        model.setChildProjectUrlInheritAppendPath(false);
        model.setInceptionYear("2024");
        model.setOrganization(validOrganization());
        model.setLicenses(List.of(validLicense()));
        model.setDevelopers(List.of(validDeveloper()));
        model.setContributors(List.of(validContributor()));
        model.setMailingLists(List.of(validMailingList()));
        model.setPrerequisites(validPrerequisites());
        model.setScm(validScm());
        model.setIssueManagement(validIssueManagement());
        model.setCiManagement(validCiManagement());
        model.setBuild(validBuild());
        model.setProfiles(List.of(validProfile()));
        model.setModelEncoding("UTF-8");

        return model;
    }

    protected ProjectDTO validProjectDTO(){
        return new ProjectDTO(
                validModelBaseDTO(),
                "4.0.0",
                validParentDTO(),
                "com.example",
                "my-project",
                "1.0.0",
                "pom",
                "My Project",
                "Description of my project",
                "https://www.somewebsite.com",
                false,
                "2024",
                validOrganizationDTO(),
                List.of(validLicenseDTO()),
                List.of(validDeveloperDTO()),
                List.of(validContributorDTO()),
                List.of(validMailingListDTO()),
                validPrerequisitesDTO(),
                validScmDTO(),
                validIssueManagementDTO(),
                validCiManagementDTO(),
                validBuildDTO(),
                List.of(validProfileDTO()),
                "UTF-8"
        );
    }

    protected ProjectDTO emptyProjectDTO(){
        return new ProjectDTO(
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                false,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );
    }

    protected void assertEquals(ProjectDTO projectDTO, Model model) throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        assertTrue(bothAreEmptyOrBothArePresent(projectDTO, model));

        if(bothArePresent(projectDTO, model)){
            if(projectDTO.modelBase() == null){
                assertEquals(emptyModelBaseDTO(), model);
            } else {
                assertEquals(projectDTO.modelBase(), model);
            }

            assertThat(projectDTO.modelVersion()).isEqualTo(model.getModelVersion());
            assertEquals(projectDTO.parent(), model.getParent());
            assertThat(projectDTO.groupId()).isEqualTo(model.getGroupId());
            assertThat(projectDTO.artifactId()).isEqualTo(model.getArtifactId());
            assertThat(projectDTO.version()).isEqualTo(model.getVersion());
            assertThat(projectDTO.packaging()).isEqualTo(model.getPackaging());
            assertThat(projectDTO.name()).isEqualTo(model.getName());
            assertThat(projectDTO.description()).isEqualTo(model.getDescription());
            assertThat(projectDTO.url()).isEqualTo(model.getUrl());
            assertThat(projectDTO.childProjectUrlInheritAppendPath()).isEqualTo(model.isChildProjectUrlInheritAppendPath());
            assertThat(projectDTO.inceptionYear()).isEqualTo(model.getInceptionYear());
            assertEquals(projectDTO.organization(), model.getOrganization());
            assertListsAreEqual(projectDTO.licenses(), model.getLicenses(), this::assertEquals);
            assertListsAreEqual(projectDTO.developers(), model.getDevelopers(), this::assertEquals);
            assertListsAreEqual(projectDTO.contributors(), model.getContributors(), this::assertEquals);
            assertListsAreEqual(projectDTO.mailingLists(), model.getMailingLists(), this::assertEquals);
            assertEquals(projectDTO.prerequisites(), model.getPrerequisites());
            assertEquals(projectDTO.scm(), model.getScm());
            assertEquals(projectDTO.issueManagement(), model.getIssueManagement());
            assertEquals(projectDTO.ciManagement(), model.getCiManagement());
            assertEquals(projectDTO.build(), model.getBuild());
            assertListsAreEqual(projectDTO.profiles(), model.getProfiles(), this::assertEquals);
            assertThat(projectDTO.modelEncodings()).isEqualTo(model.getModelEncoding());
        }
    }
}
