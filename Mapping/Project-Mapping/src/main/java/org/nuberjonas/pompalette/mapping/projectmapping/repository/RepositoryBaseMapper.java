package org.nuberjonas.pompalette.mapping.projectmapping.repository;

import org.apache.maven.model.RepositoryBase;
import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.repository.RepositoryBaseDTO;
import org.nuberjonas.pompalette.mapping.projectmapping.input.InputLocationMapper;

import java.util.Optional;

public class RepositoryBaseMapper {

    private RepositoryBaseMapper(){ throw new AssertionError("Utility class, cannot be instantiated"); }

    public static RepositoryBaseDTO mapToDTO(RepositoryBase repositoryBase){
        return Optional.ofNullable(repositoryBase)
                .map(src -> new RepositoryBaseDTO(
                        src.getId(),
                        src.getName(),
                        src.getUrl(),
                        src.getLayout(),
                        InputLocationMapper.mapToInputLocationDTOMap(src),
                        InputLocationMapper.mapToDTO(src.getLocation("")),
                        InputLocationMapper.mapToDTO(src.getLocation("id")),
                        InputLocationMapper.mapToDTO(src.getLocation("name")),
                        InputLocationMapper.mapToDTO(src.getLocation("url")),
                        InputLocationMapper.mapToDTO(src.getLocation("layout"))
                ))
                .orElse(null);
    }

    public static RepositoryBase mapToModel(RepositoryBaseDTO repositoryBaseDTO){
        if(repositoryBaseDTO == null){
            return null;
        }

        var repositoryBase = new RepositoryBase();
        repositoryBase.setId(repositoryBaseDTO.id());
        repositoryBase.setName(repositoryBaseDTO.name());
        repositoryBase.setUrl(repositoryBaseDTO.url());
        repositoryBase.setLayout(repositoryBaseDTO.layout());

        if (repositoryBaseDTO.locations() != null) {
            repositoryBaseDTO.locations().forEach((key, value) ->
                    repositoryBase.setOtherLocation(key, InputLocationMapper.mapToModel(value))
            );
        }

        repositoryBase.setLocation("", InputLocationMapper.mapToModel(repositoryBaseDTO.location()));
        repositoryBase.setLocation("id", InputLocationMapper.mapToModel(repositoryBaseDTO.idLocation()));
        repositoryBase.setLocation("name", InputLocationMapper.mapToModel(repositoryBaseDTO.nameLocation()));
        repositoryBase.setLocation("url", InputLocationMapper.mapToModel(repositoryBaseDTO.urlLocation()));
        repositoryBase.setLocation("layout", InputLocationMapper.mapToModel(repositoryBaseDTO.layoutLocation()));

        return repositoryBase;
    }
}
