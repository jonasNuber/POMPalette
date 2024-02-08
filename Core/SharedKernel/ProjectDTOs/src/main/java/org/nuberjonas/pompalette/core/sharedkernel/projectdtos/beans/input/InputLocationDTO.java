package org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.input;

import java.io.Serializable;
import java.util.Map;

public record InputLocationDTO(
        int lineNumber,
        int columnNumber,
        InputSourceDTO source,
        Map<Object, InputLocationDTO> locations,
        InputLocationDTO location

) implements Serializable {
}
