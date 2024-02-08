package de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.management;

import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.input.InputLocationDTO;

import java.io.Serializable;
import java.util.Map;
import java.util.Properties;

public record NotifierDTO(
        String type,
        boolean sendOnError,
        boolean sendOnFailure,
        boolean sendOnSuccess,
        boolean sendOnWarning,
        String address,
        Properties configuration,
        Map<Object, InputLocationDTO> locations,
        InputLocationDTO location,
        InputLocationDTO typeLocation,
        InputLocationDTO sendOnErrorLocation,
        InputLocationDTO sendOnFailureLocation,
        InputLocationDTO sendOnSuccessLocation,
        InputLocationDTO sendOnWarningLocation,
        InputLocationDTO addressLocation,
        InputLocationDTO configurationLocation
) implements Serializable {
}
