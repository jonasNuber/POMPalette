package org.nuberjonas.pompalette.mapping.mavenmapping.reporting;

import org.apache.maven.model.ReportPlugin;
import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.reporting.ReportPluginDTO;
import org.nuberjonas.pompalette.mapping.mappingapi.mapper.utilities.ListMapper;
import org.nuberjonas.pompalette.mapping.mappingapi.mapper.utilities.SuperClassMapper;
import org.nuberjonas.pompalette.mapping.mavenmapping.plugin.ConfigurationContainerMapper;

import java.util.Optional;

public class ReportPluginMapper {

    private ReportPluginMapper(){ throw new AssertionError("Utility class, cannot be instantiated"); }

    protected static ReportPluginDTO mapToDTO(ReportPlugin reportPlugin){
        return Optional.ofNullable(reportPlugin)
                .map(src -> new ReportPluginDTO(
                        ConfigurationContainerMapper.mapToDTO(src),
                        src.getGroupId(),
                        src.getArtifactId(),
                        src.getVersion(),
                        ListMapper.mapList(src.getReportSets(), ReportSetMapper::mapToDTO)
                ))
                .orElse(null);
    }

    protected static ReportPlugin mapToModel(ReportPluginDTO reportPluginDTO){
        if(reportPluginDTO == null){
            return null;
        }

        var reportPlugin = new ReportPlugin();

        if(reportPluginDTO.configurationContainer() != null){
            reportPlugin = SuperClassMapper.mapFields(ConfigurationContainerMapper.mapToModel(reportPluginDTO.configurationContainer()), ReportPlugin.class);
        }

        reportPlugin.setGroupId(reportPluginDTO.groupId());
        reportPlugin.setArtifactId(reportPluginDTO.artifactId());
        reportPlugin.setVersion(reportPluginDTO.version());
        reportPlugin.setReportSets(ListMapper.mapList(reportPluginDTO.reportSets(), ReportSetMapper::mapToModel));

        return reportPlugin;
    }
}
