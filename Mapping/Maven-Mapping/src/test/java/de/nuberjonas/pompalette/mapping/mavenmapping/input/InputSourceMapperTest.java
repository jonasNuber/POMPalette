package de.nuberjonas.pompalette.mapping.mavenmapping.input;

import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.input.InputSourceDTO;
import de.nuberjonas.pompalette.mapping.mavenmapping.MavenMapperBaseTest;
import org.apache.maven.model.InputSource;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class InputSourceMapperTest extends MavenMapperBaseTest {

    @Test
    void mapToDTO_ShouldReturnNullIfInputIsNull(){
        var dto = InputSourceMapper.mapToDTO(null);

        assertThat(dto).isNull();
    }

    @Test
    void mapToDTO_ShouldMapModelCorrectly(){
        var expected = new InputSource();
        expected.setModelId("someModelID");
        expected.setLocation("/some/location");

        var actual = InputSourceMapper.mapToDTO(expected);

        assertEquals(expected, actual);
    }

    @Test
    void mapToModel_ShouldReturnNullIfInputIsNull(){
        var model = InputSourceMapper.mapToModel(null);

        assertThat(model).isNull();
    }

    @Test
    void mapToModel_ShouldMapDtoCorrectly(){
        var expected = new InputSourceDTO(
                "someModelID",
                "/some/location"
        );

        var actual = InputSourceMapper.mapToModel(expected);

        assertEquals(actual, expected);
    }
}