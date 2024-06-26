package org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.contributing;

import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.input.InputLocationDTO;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public record ContributorDTO(
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
) implements Serializable {
}
