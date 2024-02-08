package de.nuberjonas.pompalette.mapping.mavenmapping.reporting;

import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.reporting.ReportSetDTO;
import de.nuberjonas.pompalette.mapping.mappingapi.mapper.SuperClassMapper;
import de.nuberjonas.pompalette.mapping.mavenmapping.plugin.ConfigurationContainerMapper;
import org.apache.maven.model.ReportSet;

import java.util.Optional;

public class ReportSetMapper {

    private ReportSetMapper(){ throw new AssertionError("Utility class, cannot be instantiated"); }

    public static ReportSetDTO mapToDTO(ReportSet reportSet){
        return Optional.ofNullable(reportSet)
                .map(src -> new ReportSetDTO(
                        ConfigurationContainerMapper.mapToDTO(src),
                        src.getId(),
                        src.getReports()
                ))
                .orElse(null);
    }

    public static ReportSet mapToModel(ReportSetDTO reportSetDTO){
        if(reportSetDTO == null){
            return null;
        }

        var reportSet = new ReportSet();

        if(reportSetDTO.configurationContainer() != null){
            reportSet = SuperClassMapper.mapFields(ConfigurationContainerMapper.mapToModel(reportSetDTO.configurationContainer()), ReportSet.class);
        }

        reportSet.setId(reportSetDTO.id());
        reportSet.setReports(reportSetDTO.reports());

        return reportSet;
    }
}
