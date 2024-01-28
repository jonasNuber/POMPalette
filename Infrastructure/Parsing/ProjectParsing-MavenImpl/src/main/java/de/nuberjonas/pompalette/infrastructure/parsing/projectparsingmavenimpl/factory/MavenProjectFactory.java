package de.nuberjonas.pompalette.infrastructure.parsing.projectparsingmavenimpl.factory;

import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.contributing.ContributerDTO;
import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.contributing.DeveloperDTO;
import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.contributing.MailingListDTO;
import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.contributing.OrganizationDTO;
import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.input.InputLocationDTO;
import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.input.InputSourceDTO;
import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.management.IssueManagementDTO;
import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.project.*;
import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.factory.ProjectFactory;
import org.apache.maven.model.*;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class MavenProjectFactory implements ProjectFactory<Model> {

    @Override
    public Optional<ProjectDTO> createProjectDTO(Model project) {
       return project == null ? Optional.empty() : Optional.of(
               new ProjectDTO(
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
                       null,
                       null,
                       project.getModelVersion(),
                       createParentDTO(project.getParent()),
                       project.getGroupId(),
                       project.getArtifactId(),
                       project.getVersion(),
                       project.getPackaging(),
                       project.getName(),
                       project.getDescription(),
                       project.getUrl(),
                       null,
                       project.getInceptionYear(),
                       createOrganizationDTO(project.getOrganization()),
                       createList(project.getLicenses(), (license) -> createLicenseDTO(license)),
                       createList(project.getDevelopers(), (developer) -> createContributorDTO(developer)),
                       createList(project.getContributors(), (contributor) -> createContributorDTO(contributor)),
                       createList(project.getMailingLists(), (mailingList) -> createMailingListDTO(mailingList)),
                       createPrerequisitesDTO(project.getPrerequisites()),
                       createScmDTO(project.getScm()),
                       createIssueManagementDTO(project.getIssueManagement()),
                       null,
                       null,
                       null,
                       null
               )
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
                createInputLocationDTO(parent.getLocation("groupId")),
                createInputLocationDTO(parent.getLocation("artifactId")),
                createInputLocationDTO(parent.getLocation("version")),
                createInputLocationDTO(parent.getLocation("relativePath"))
        );
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

    @SuppressWarnings("unchecked")
    private Map<Object, InputLocationDTO> createInputLocationDTOMap(Object object) {
        try {
            Field field = getFieldIncludingSuperclass(object.getClass(), "locations");
            field.setAccessible(true);

            return createInputLocationDTOMap((Map<Object, InputLocation>)field.get(object));

        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private Field getFieldIncludingSuperclass(Class<?> clazz, String fieldName) throws NoSuchFieldException {
        while (clazz != null) {
            try {
                return clazz.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass();
            }
        }

        throw new NoSuchFieldException(fieldName);
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

    private DeveloperDTO createContributorDTO(Developer developer){
        return new DeveloperDTO(
                developer.getName(),
                developer.getEmail(),
                developer.getUrl(),
                developer.getOrganization(),
                developer.getOrganizationUrl(),
                developer.getRoles(),
                developer.getTimezone(),
                developer.getProperties(),
                createInputLocationDTOMap(developer),
                createInputLocationDTO(developer.getLocation("")),
                createInputLocationDTO(developer.getLocation("name")),
                createInputLocationDTO(developer.getLocation("email")),
                createInputLocationDTO(developer.getLocation("url")),
                createInputLocationDTO(developer.getLocation("organization")),
                createInputLocationDTO(developer.getLocation("organizationUrl")),
                createInputLocationDTO(developer.getLocation("roles")),
                createInputLocationDTO(developer.getLocation("timezone")),
                createInputLocationDTO(developer.getLocation("properties")),
                developer.getId());
    }

    private ContributerDTO createContributorDTO(Contributor contributor){
        return new ContributerDTO(
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
                createInputLocationDTO(contributor.getLocation("properties")));
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
                scm.getChildScmConnectionInheritAppendPath(),
                scm.getChildScmDeveloperConnectionInheritAppendPath(),
                scm.getChildScmUrlInheritAppendPath(),
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

    @Override
    public Optional<Model> createProject(ProjectDTO projectDTO) {
        return null;
    }
}
