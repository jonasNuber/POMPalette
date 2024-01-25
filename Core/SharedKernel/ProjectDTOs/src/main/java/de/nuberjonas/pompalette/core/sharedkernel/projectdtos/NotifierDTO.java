package de.nuberjonas.pompalette.core.sharedkernel.projectdtos;

import java.util.Map;
import java.util.Properties;

public record NotifierDTO(
        String type,
        boolean sendOnError,
        boolean sendOnFailure,
        boolean sendOnSuccess,
        boolean sendOnWarning,
        @Deprecated
        String address,
        Properties properties,
        Map<Object, InputLocationDTO> locations,
        InputLocationDTO location,
        InputLocationDTO typeLocation,
        InputLocationDTO sendOnErrorLocation,
        InputLocationDTO sendOnFailureLocation,
        InputLocationDTO sendOnSuccessLocation,
        InputLocationDTO sendOnWarningLocation,
        InputLocationDTO addressLocation,
        InputLocationDTO configurationLocation
) {
}
