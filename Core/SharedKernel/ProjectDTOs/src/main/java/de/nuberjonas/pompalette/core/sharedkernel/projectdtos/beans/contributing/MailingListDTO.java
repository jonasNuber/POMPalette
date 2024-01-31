package de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.contributing;

import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.input.InputLocationDTO;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public record MailingListDTO(
        String name,
        String subscribe,
        String unsubscribe,
        String post,
        String archive,
        List<String> otherArchives,
        Map<Object, InputLocationDTO> locations,
        InputLocationDTO location,
        InputLocationDTO nameLocation,
        InputLocationDTO subscribeLocation,
        InputLocationDTO unsubscribeLocation,
        InputLocationDTO postLocation,
        InputLocationDTO archiveLocation,
        InputLocationDTO otherArchivesLocation
) implements Serializable {
}
