package de.nuberjonas.pompalette.core.sharedkernel.projectdtos;

import java.util.Map;

public record RepositoryPolicyDTO(
        String enabled,
        String updatePolicy,
        String checksumPolicy,
        Map<Object, InputLocationDTO> locations,
        InputLocationDTO location,
        InputLocationDTO enabledLocation,
        InputLocationDTO updatePolicyLocation,
        InputLocationDTO checksumPolicyLocation
) {
}
