package de.nuberjonas.pompalette.mapping.mavenmapping.reporting;

import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.reporting.ReportingDTO;
import de.nuberjonas.pompalette.mapping.mappingapi.mapper.ListMapper;
import de.nuberjonas.pompalette.mapping.mavenmapping.input.InputLocationMapper;
import org.apache.maven.model.Reporting;

import java.util.Optional;

public class ReportingMapper {

    private ReportingMapper(){ throw new AssertionError("Utility class, cannot be instantiated"); }

    public static ReportingDTO mapToDTO(Reporting reporting){
        return Optional.ofNullable(reporting)
                .map(src -> new ReportingDTO(
                        src.isExcludeDefaults(),
                        src.getOutputDirectory(),
                        ListMapper.mapList(src.getPlugins(), ReportPluginMapper::mapToDTO),
                        InputLocationMapper.mapToInputLocationDTOMap(src),
                        InputLocationMapper.mapToDTO(src.getLocation("")),
                        InputLocationMapper.mapToDTO(src.getLocation("excludeDefaults")),
                        InputLocationMapper.mapToDTO(src.getLocation("outputDirectory")),
                        InputLocationMapper.mapToDTO(src.getLocation("plugins"))
                ))
                .orElse(null);
    }

    public static Reporting mapToModel(ReportingDTO reportingDTO){
        if(reportingDTO == null){
            return null;
        }

        var reporting = new Reporting();
        reporting.setExcludeDefaults(reportingDTO.excludeDefaults());
        reporting.setOutputDirectory(reportingDTO.outputDirectory());
        reporting.setPlugins(ListMapper.mapList(reportingDTO.plugins(), ReportPluginMapper::mapToModel));

        if (reportingDTO.locations() != null) {
            reportingDTO.locations().forEach((key, value) ->
                    reporting.setOtherLocation(key, InputLocationMapper.mapToModel(value))
            );
        }

        reporting.setLocation("", InputLocationMapper.mapToModel(reportingDTO.location()));
        reporting.setLocation("excludeDefaults", InputLocationMapper.mapToModel(reportingDTO.excludeDefaultsLocation()));
        reporting.setLocation("outputDirectory", InputLocationMapper.mapToModel(reportingDTO.outputDirectoryLocation()));
        reporting.setLocation("plugins", InputLocationMapper.mapToModel(reportingDTO.pluginsLocation()));

        return reporting;
    }
}