package org.nuberjonas.pompalette.mapping.mavenmapping;

import org.apache.maven.model.Model;
import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.ProjectDTO;
import org.nuberjonas.pompalette.mapping.mappingapi.mapper.ListMapper;
import org.nuberjonas.pompalette.mapping.mappingapi.mapper.MapperService;
import org.nuberjonas.pompalette.mapping.mappingapi.mapper.SuperClassMapper;
import org.nuberjonas.pompalette.mapping.mavenmapping.build.BuildMapper;
import org.nuberjonas.pompalette.mapping.mavenmapping.contributing.ContributorMapper;
import org.nuberjonas.pompalette.mapping.mavenmapping.contributing.DeveloperMapper;
import org.nuberjonas.pompalette.mapping.mavenmapping.contributing.MailingListMapper;
import org.nuberjonas.pompalette.mapping.mavenmapping.contributing.OrganizationMapper;
import org.nuberjonas.pompalette.mapping.mavenmapping.management.CiManagementMapper;
import org.nuberjonas.pompalette.mapping.mavenmapping.management.IssueManagementMapper;
import org.nuberjonas.pompalette.mapping.mavenmapping.model.*;

import java.util.Optional;

public class ProjectMapperService implements MapperService<Model, ProjectDTO> {
    @Override
    public ProjectDTO mapToDestination(Model model) {
        return Optional.ofNullable(model)
                .map(src -> new ProjectDTO(
                        ModelBaseMapper.mapToDTO(src),
                        src.getModelVersion(),
                        ParentMapper.mapToDTO(src.getParent()),
                        src.getGroupId(),
                        src.getArtifactId(),
                        src.getVersion(),
                        src.getPackaging(),
                        src.getName(),
                        src.getDescription(),
                        src.getUrl(),
                        src.isChildProjectUrlInheritAppendPath(),
                        src.getInceptionYear(),
                        OrganizationMapper.mapToDTO(src.getOrganization()),
                        ListMapper.mapList(src.getLicenses(), LicenseMapper::mapToDTO),
                        ListMapper.mapList(src.getDevelopers(), DeveloperMapper::mapToDTO),
                        ListMapper.mapList(src.getContributors(), ContributorMapper::mapToDTO),
                        ListMapper.mapList(src.getMailingLists(), MailingListMapper::mapToDTO),
                        PrerequisitesMapper.mapToDTO(src.getPrerequisites()),
                        ScmMapper.mapToDTO(src.getScm()),
                        IssueManagementMapper.mapToDTO(src.getIssueManagement()),
                        CiManagementMapper.mapToDTO(src.getCiManagement()),
                        BuildMapper.mapToDTO(src.getBuild()),
                        ListMapper.mapList(src.getProfiles(), ProfileMapper::mapToDTO),
                        src.getModelEncoding()
                ))
                .orElseThrow(() -> new IllegalArgumentException("Input cannot be empty"));
    }

    @Override
    public Model mapToSource(ProjectDTO projectDTO) {
        if(projectDTO == null){
            throw new IllegalArgumentException("Input cannot be empty");
        }

        var model = new Model();

        if(projectDTO.modelBase() != null){
            model = SuperClassMapper.mapFields(ModelBaseMapper.mapToModel(projectDTO.modelBase()), Model.class);
        }

        model.setModelVersion(projectDTO.modelVersion());
        model.setParent(ParentMapper.mapToModel(projectDTO.parent()));
        model.setGroupId(projectDTO.groupId());
        model.setArtifactId(projectDTO.artifactId());
        model.setVersion(projectDTO.version());
        model.setPackaging(projectDTO.packaging());
        model.setName(projectDTO.name());
        model.setDescription(projectDTO.description());
        model.setUrl(projectDTO.url());
        model.setChildProjectUrlInheritAppendPath(projectDTO.childProjectUrlInheritAppendPath());
        model.setInceptionYear(projectDTO.inceptionYear());
        model.setOrganization(OrganizationMapper.mapToModel(projectDTO.organization()));
        model.setLicenses(ListMapper.mapList(projectDTO.licenses(), LicenseMapper::mapToModel));
        model.setDevelopers(ListMapper.mapList(projectDTO.developers(), DeveloperMapper::mapToModel));
        model.setContributors(ListMapper.mapList(projectDTO.contributors(), ContributorMapper::mapToModel));
        model.setMailingLists(ListMapper.mapList(projectDTO.mailingLists(), MailingListMapper::mapToModel));
        model.setPrerequisites(PrerequisitesMapper.mapToModel(projectDTO.prerequisites()));
        model.setScm(ScmMapper.mapToModel(projectDTO.scm()));
        model.setIssueManagement(IssueManagementMapper.mapToModel(projectDTO.issueManagement()));
        model.setCiManagement(CiManagementMapper.mapToModel(projectDTO.ciManagement()));
        model.setBuild(BuildMapper.mapToModel(projectDTO.build()));
        model.setProfiles(ListMapper.mapList(projectDTO.profiles(), ProfileMapper::mapToModel));
        model.setModelEncoding(projectDTO.modelEncodings());

        return model;
    }
}
