package org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.management;

import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.input.InputLocationDTO;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public record CiManagementDTO(
        String system,
        String url,
        List<NotifierDTO> notifiers,
        Map<Object, InputLocationDTO> locations,
        InputLocationDTO location,
        InputLocationDTO systemLocation,
        InputLocationDTO urlLocation,
        InputLocationDTO notifiersLocation
) implements Serializable {
}
