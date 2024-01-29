package de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.repository;

import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.input.InputLocationDTO;

import java.util.Map;

public record RepositoryPolicyDTO(
        boolean enabled,
        String updatePolicy,
        String checksumPolicy,
        Map<Object, InputLocationDTO> locations,
        InputLocationDTO location,
        InputLocationDTO enabledLocation,
        InputLocationDTO updatePolicyLocation,
        InputLocationDTO checksumPolicyLocation
) {
}
