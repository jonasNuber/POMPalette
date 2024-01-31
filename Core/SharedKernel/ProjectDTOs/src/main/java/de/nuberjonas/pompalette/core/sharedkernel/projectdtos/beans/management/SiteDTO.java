package de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.management;

import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.input.InputLocationDTO;

import java.io.Serializable;
import java.util.Map;

public record SiteDTO(
        String id,
        String name,
        String url,
        boolean childSiteUrlInheritAppendPath,
        Map<Object, InputLocationDTO> locations,
        InputLocationDTO location,
        InputLocationDTO idLocation,
        InputLocationDTO nameLocation,
        InputLocationDTO urlLocation,
        InputLocationDTO childSiteUrlInheritAppendPathLocation
) implements Serializable {
}
