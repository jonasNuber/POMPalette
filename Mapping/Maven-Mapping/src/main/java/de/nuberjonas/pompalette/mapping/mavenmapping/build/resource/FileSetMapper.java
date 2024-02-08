package de.nuberjonas.pompalette.mapping.mavenmapping.build.resource;

import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.build.resource.FileSetDTO;
import de.nuberjonas.pompalette.mapping.mappingapi.mapper.SuperClassMapper;
import org.apache.maven.model.FileSet;

import java.util.Optional;

public class FileSetMapper {

    private FileSetMapper(){ throw new AssertionError("Utility class, cannot be instantiated"); }

    public static FileSetDTO mapToDTO(FileSet fileSet){
        return Optional.ofNullable(fileSet)
                .map(src -> new FileSetDTO(
                        PatternSetMapper.mapToDTO(src),
                        src.getDirectory()
                ))
                .orElse(null);
    }

    public static FileSet mapToModel(FileSetDTO fileSetDTO){
        if(fileSetDTO == null){
            return null;
        }

        var fileSet = new FileSet();

        if(fileSetDTO.patternSet() != null){
            fileSet = SuperClassMapper.mapFields(PatternSetMapper.mapToModel(fileSetDTO.patternSet()), FileSet.class);
        }

        fileSet.setDirectory(fileSetDTO.directory());

        return fileSet;
    }
}
