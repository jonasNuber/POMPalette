package de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.model;

import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.dependency.DependencyDTO;
import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.dependency.DependencyManagementDTO;
import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.input.InputLocationDTO;
import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.management.DistributionManagementDTO;
import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.reporting.ReportingDTO;
import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.repository.RepositoryDTO;

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
