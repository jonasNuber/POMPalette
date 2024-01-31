package de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.repository;

import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.input.InputLocationDTO;

import java.io.Serializable;
import java.util.Map;

public record RepositoryBaseDTO(
        String id,
        String name,
        String url,
        String layout,
        Map<Object, InputLocationDTO> locations,
        InputLocationDTO location,
        InputLocationDTO idLocation,
        InputLocationDTO nameLocation,
        InputLocationDTO urlLocation,
        InputLocationDTO layoutLocation
) implements Serializable {
}
