package de.nuberjonas.pompalette.mapping.mavenmapping;

import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.input.InputLocationDTO;
import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.input.InputSourceDTO;
import org.apache.maven.model.InputLocation;
import org.apache.maven.model.InputSource;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class MavenMapperBaseTest {

     protected void assertEquals(InputLocationDTO inputLocationDTO, InputLocation inputLocation){
        if(inputLocationDTO != null && inputLocation != null){
            assertThat(inputLocation.getColumnNumber()).isEqualTo(inputLocationDTO.columnNumber());
            assertThat(inputLocation.getLineNumber()).isEqualTo(inputLocationDTO.lineNumber());
            assertEquals(inputLocation.getSource(), inputLocationDTO.source());

            if(inputLocation.getLocations() != null){
                assertThat(inputLocation.getLocations()).hasSameSizeAs(inputLocationDTO.locations());
            }

            assertEquals(inputLocationDTO, inputLocation.getLocation(""));
        }
    }

    protected void assertEquals(InputSource inputSource, InputSourceDTO inputSourceDTO){
        assertThat(inputSource.getModelId()).isEqualTo(inputSourceDTO.modelId());
        assertThat(inputSource.getLocation()).isEqualTo(inputSourceDTO.location());
    }
}
