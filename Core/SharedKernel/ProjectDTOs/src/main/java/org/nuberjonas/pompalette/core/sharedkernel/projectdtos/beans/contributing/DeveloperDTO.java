package org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.contributing;

import java.io.Serializable;

public record DeveloperDTO(
        ContributorDTO contributor,
        String id
) implements Serializable {
}
