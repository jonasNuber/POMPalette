package de.nuberjonas.pompalette.core.sharedkernel.projectdtos;

import java.util.Map;

public record ScmDTO(
        String connection,
        String developerConnection,
        String tag,
        String url,
        String childScmConnectionInheritAppendPath,
        String childScmDeveloperConnectionInheritAppendPath,
        String childScmUrlInheritAppendPath,
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
