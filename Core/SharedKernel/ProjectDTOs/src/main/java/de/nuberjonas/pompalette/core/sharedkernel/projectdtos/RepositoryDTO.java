package de.nuberjonas.pompalette.core.sharedkernel.projectdtos;

import java.util.Map;

public record RepositoryDTO(
        // RepositoryBase
        String id,
        String name,
        String url,
        String layout,
        Map<Object, InputLocationDTO> locations,
        InputLocationDTO locationDTO,
        InputLocationDTO idLocation,
        InputLocationDTO nameLocation,
        InputLocationDTO urlLocation,
        InputLocationDTO layoutLocation,

        //Repository
        RepositoryPolicyDTO releases,
        RepositoryPolicyDTO snapshots
) {
}
