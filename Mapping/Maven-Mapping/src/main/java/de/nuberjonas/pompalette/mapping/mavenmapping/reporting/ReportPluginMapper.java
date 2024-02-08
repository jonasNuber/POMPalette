package de.nuberjonas.pompalette.mapping.mavenmapping.reporting;

import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.reporting.ReportPluginDTO;
import de.nuberjonas.pompalette.mapping.mappingapi.mapper.ListMapper;
import de.nuberjonas.pompalette.mapping.mappingapi.mapper.SuperClassMapper;
import de.nuberjonas.pompalette.mapping.mavenmapping.plugin.ConfigurationContainerMapper;
import org.apache.maven.model.ReportPlugin;

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
