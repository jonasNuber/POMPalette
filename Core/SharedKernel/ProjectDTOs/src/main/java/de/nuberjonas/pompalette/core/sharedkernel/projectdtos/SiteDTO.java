package de.nuberjonas.pompalette.core.sharedkernel.projectdtos;

import java.util.Map;

public record SiteDTO(
        String id,
        String name,
        String url,
        String childSiteUrlInheritAppendPath,
        Map<Object, InputLocationDTO> locations,
        InputLocationDTO location,
        InputLocationDTO idLocation,
        InputLocationDTO nameLocation,
        InputLocationDTO urlLocation,
        InputLocationDTO childSiteUrlInheritAppendPathLocation
) {
}
