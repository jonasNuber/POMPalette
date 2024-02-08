package org.nuberjonas.pompalette.mapping.mavenmapping.input;

import org.apache.maven.model.InputSource;
import org.junit.jupiter.api.Test;
import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.input.InputSourceDTO;
import org.nuberjonas.pompalette.mapping.mavenmapping.MavenMapperBaseTest;

import static org.assertj.core.api.Assertions.assertThat;

class InputSourceMapperTest extends MavenMapperBaseTest {

    @Test
    void mapToDTO_ShouldReturnNullIfInputIsNull(){
        var dto = InputSourceMapper.mapToDTO(null);

        assertThat(dto).isNull();
    }

    @Test
    void mapToDTO_ShouldMapInputSourceCorrectlyForValidInput(){
        var expected = validInputSource();

        var actual = InputSourceMapper.mapToDTO(expected);

        assertEquals(expected, actual);
    }

    @Test
    void mapToDTO_ShouldMapInputSourceCorrectlyForEmptyInput(){
        var expected = new InputSource();

        var actual = InputSourceMapper.mapToDTO(expected);

        assertEquals(expected, actual);
    }

    @Test
    void mapToModel_ShouldReturnNullIfInputIsNull(){
        var model = InputSourceMapper.mapToModel(null);

        assertThat(model).isNull();
    }

    @Test
    void mapToModel_ShouldMapInputSourceDtoCorrectlyForValidInput(){
        var expected = validInputSourceDTO();

        var actual = InputSourceMapper.mapToModel(expected);

        assertEquals(actual, expected);
    }
    @Test
    void mapToModel_ShouldMapInputSourceDtoCorrectlyForEmptyInput(){
        var expected = new InputSourceDTO(null, null);

        var actual = InputSourceMapper.mapToModel(expected);

        assertEquals(actual, expected);
    }
}