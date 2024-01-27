package de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.contributing;

import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.input.InputLocationDTO;

import java.util.List;
import java.util.Map;
import java.util.Properties;

public record ContributerDTO(
        String name,
        String email,
        String url,
        String organization,
        String organizationUrl,
        List<String> roles,
        String timezone,
        Properties properties,
        Map<Object, InputLocationDTO> locations,
        InputLocationDTO location,
        InputLocationDTO nameLocation,
        InputLocationDTO emailLocation,
        InputLocationDTO urlLocation,
        InputLocationDTO organizationLocation,
        InputLocationDTO organizationUrlLocation,
        InputLocationDTO rolesLocation,
        InputLocationDTO timezoneLocation,
        InputLocationDTO propertiesLocation
) {
}
