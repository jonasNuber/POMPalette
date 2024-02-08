package org.nuberjonas.pompalette.mapping.mavenmapping.model;

import org.apache.maven.model.Scm;
import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.model.ScmDTO;
import org.nuberjonas.pompalette.mapping.mavenmapping.input.InputLocationMapper;

import java.util.Optional;

public class ScmMapper {

    private ScmMapper(){ throw new AssertionError("Utility class, cannot be instantiated"); }

    public static ScmDTO mapToDTO(Scm scm){
        return Optional.ofNullable(scm)
                .map(src -> new ScmDTO(
                        src.getConnection(),
                        src.getDeveloperConnection(),
                        src.getTag(),
                        src.getUrl(),
                        src.isChildScmConnectionInheritAppendPath(),
                        src.isChildScmDeveloperConnectionInheritAppendPath(),
                        src.isChildScmUrlInheritAppendPath(),
                        InputLocationMapper.mapToInputLocationDTOMap(src),
                        InputLocationMapper.mapToDTO(src.getLocation("")),
                        InputLocationMapper.mapToDTO(src.getLocation("connection")),
                        InputLocationMapper.mapToDTO(src.getLocation("developerConnection")),
                        InputLocationMapper.mapToDTO(src.getLocation("tag")),
                        InputLocationMapper.mapToDTO(src.getLocation("url")),
                        InputLocationMapper.mapToDTO(src.getLocation("childScmConnectionInheritAppendPath")),
                        InputLocationMapper.mapToDTO(src.getLocation("childScmDeveloperConnectionInheritAppendPath")),
                        InputLocationMapper.mapToDTO(src.getLocation("childScmUrlInheritAppendPath"))))
                .orElse(null);
    }

    public static Scm mapToModel(ScmDTO scmDTO){
        if(scmDTO == null){
            return null;
        }

        var scm = new Scm();
        scm.setConnection(scmDTO.connection());
        scm.setDeveloperConnection(scmDTO.developerConnection());
        scm.setTag(scmDTO.tag());
        scm.setUrl(scmDTO.url());
        scm.setChildScmConnectionInheritAppendPath(scmDTO.childScmConnectionInheritAppendPath());
        scm.setChildScmDeveloperConnectionInheritAppendPath(scmDTO.childScmDeveloperConnectionInheritAppendPath());
        scm.setChildScmUrlInheritAppendPath(scmDTO.childScmUrlInheritAppendPath());

        if (scmDTO.locations() != null) {
            scmDTO.locations().forEach((key, value) ->
                    scm.setOtherLocation(key, InputLocationMapper.mapToModel(value))
            );
        }

        scm.setLocation("", InputLocationMapper.mapToModel(scmDTO.location()));
        scm.setLocation("connection", InputLocationMapper.mapToModel(scmDTO.connectionLocation()));
        scm.setLocation("developerConnection", InputLocationMapper.mapToModel(scmDTO.developerConnectionLocation()));
        scm.setLocation("tag", InputLocationMapper.mapToModel(scmDTO.tagLocation()));
        scm.setLocation("url", InputLocationMapper.mapToModel(scmDTO.urlLocation()));
        scm.setLocation("childScmConnectionInheritAppendPath", InputLocationMapper.mapToModel(scmDTO.childScmConnectionInheritAppendPathLocation()));
        scm.setLocation("childScmDeveloperConnectionInheritAppendPath", InputLocationMapper.mapToModel(scmDTO.location()));
        scm.setLocation("childScmUrlInheritAppendPath", InputLocationMapper.mapToModel(scmDTO.location()));

        return scm;
    }
}
