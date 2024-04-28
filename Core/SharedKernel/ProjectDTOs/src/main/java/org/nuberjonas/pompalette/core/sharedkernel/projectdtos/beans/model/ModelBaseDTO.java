package org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.model;

import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.dependency.DependencyDTO;
import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.dependency.DependencyManagementDTO;
import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.input.InputLocationDTO;
import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.management.DistributionManagementDTO;
import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.reporting.ReportingDTO;
import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.repository.RepositoryDTO;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public record ModelBaseDTO(
        List<String> modules,
        DistributionManagementDTO distributionManagement,
        Properties properties,
        DependencyManagementDTO dependencyManagement,
        List<DependencyDTO> dependencies,
        List<RepositoryDTO> repositories,
        List<RepositoryDTO> pluginRepositories,
        ReportingDTO reporting,
        Map<Object, InputLocationDTO> locations,
        InputLocationDTO location,
        InputLocationDTO modulesLocation,
        InputLocationDTO distributionManagementLocation,
        InputLocationDTO propertiesLocation,
        InputLocationDTO dependencyManagementLocation,
        InputLocationDTO dependenciesLocation,
        InputLocationDTO repositoriesLocation,
        InputLocationDTO pluginRepositoriesLocation,
        InputLocationDTO reportingLocation
) implements Serializable {
}
