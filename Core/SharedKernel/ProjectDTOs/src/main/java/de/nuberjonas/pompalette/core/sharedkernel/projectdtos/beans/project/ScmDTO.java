package de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.project;

import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.input.InputLocationDTO;

import java.util.Map;

public record ScmDTO(
        String connection,
        String developerConnection,
        String tag,
        String url,
        boolean childScmConnectionInheritAppendPath,
        boolean childScmDeveloperConnectionInheritAppendPath,
        boolean childScmUrlInheritAppendPath,
        Map<Object, InputLocationDTO> locations,
        InputLocationDTO location,
        InputLocationDTO connectionLocation,
        InputLocationDTO developerConnectionLocation,
        InputLocationDTO tagLocation,
        InputLocationDTO urlLocation,
        InputLocationDTO childScmConnectionInheritAppendPathLocation,
        InputLocationDTO childScmDeveloperConnectionInheritAppendPathLocation,
        InputLocationDTO childScmUrlInheritAppendPathLocation
) {
}
