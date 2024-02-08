package org.nuberjonas.pompalette.mapping.mavenmapping.model;

import org.apache.maven.model.Profile;
import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.model.ProfileDTO;
import org.nuberjonas.pompalette.mapping.mappingapi.mapper.SuperClassMapper;
import org.nuberjonas.pompalette.mapping.mavenmapping.activation.ActivationMapper;
import org.nuberjonas.pompalette.mapping.mavenmapping.build.BuildBaseMapper;

import java.util.Optional;

public class ProfileMapper {

    private ProfileMapper(){ throw new AssertionError("Utility class, cannot be instantiated"); }

    public static ProfileDTO mapToDTO(Profile profile){
        return Optional.ofNullable(profile)
                .map(src -> new ProfileDTO(
                        ModelBaseMapper.mapToDTO(src),
                        src.getId(),
                        ActivationMapper.mapToDTO(src.getActivation()),
                        BuildBaseMapper.mapToDTO(src.getBuild())
                ))
                .orElse(null);
    }

    public static Profile mapToModel(ProfileDTO profileDTO){
        if(profileDTO == null){
            return null;
        }

        var profile = new Profile();

        if(profileDTO.modelBase() != null){
            profile = SuperClassMapper.mapFields(ModelBaseMapper.mapToModel(profileDTO.modelBase()), Profile.class);
        }

        profile.setId(profileDTO.id());
        profile.setActivation(ActivationMapper.mapToModel(profileDTO.activation()));
        profile.setBuild(BuildBaseMapper.mapToModel(profileDTO.build()));

        return profile;
    }
}
