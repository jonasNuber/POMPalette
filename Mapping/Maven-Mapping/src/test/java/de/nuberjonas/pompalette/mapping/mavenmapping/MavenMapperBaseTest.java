package de.nuberjonas.pompalette.mapping.mavenmapping;

import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.contributing.ContributorDTO;
import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.contributing.DeveloperDTO;
import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.contributing.OrganizationDTO;
import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.dependency.DependencyDTO;
import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.dependency.DependencyManagementDTO;
import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.dependency.ExclusionDTO;
import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.input.InputLocationDTO;
import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.input.InputSourceDTO;
import de.nuberjonas.pompalette.mapping.mappingapi.mapper.SuperClassMapper;
import org.apache.maven.model.*;

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
        Developer developer = SuperClassMapper.copyFields(validContributor(), Developer.class);
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
