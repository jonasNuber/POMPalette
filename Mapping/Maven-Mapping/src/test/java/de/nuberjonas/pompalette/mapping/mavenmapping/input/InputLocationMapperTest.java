package de.nuberjonas.pompalette.mapping.mavenmapping.input;

import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.input.InputLocationDTO;
import de.nuberjonas.pompalette.mapping.mappingapi.exceptions.MappingException;
import de.nuberjonas.pompalette.mapping.mavenmapping.MavenMapperBaseTest;
import org.apache.maven.model.InputLocation;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class InputLocationMapperTest extends MavenMapperBaseTest {

    @Test
    void mapToDTO_ShouldReturnNullIfInputIsNull(){
        var dto = InputLocationMapper.mapToDTO(null);

        assertThat(dto).isNull();
    }

    @Test
    void mapToDTO_ShouldMapInputLocationCorrectlyForValidInput(){
        var expected = validInputLocation();

        var actual = InputLocationMapper.mapToDTO(expected);

        assertEquals(actual, expected);
    }

    @Test
    void mapToDTO_ShouldMapInputLocationCorrectlyForEmptyInput(){
        var expected = new InputLocation(0, 0);

        var actual = InputLocationMapper.mapToDTO(expected);

        assertEquals(actual, expected);
    }

    @Test
    void mapToModel_ShouldReturnNullIfInputIsNull(){
        var model = InputLocationMapper.mapToModel(null);

        assertThat(model).isNull();
    }

    @Test
    void mapToDTO_ShouldMapInputLocationDTOCorrectlyForValidInput(){
        var expected = validInputLocationDTO();

        var actual = InputLocationMapper.mapToModel(expected);

        assertEquals(expected, actual);
    }

    @Test
    void mapToDTO_ShouldMapInputLocationDTOCorrectlyForEmptyInput(){
        var expected = new InputLocationDTO(
                0,
                0,
                null,
                null,
                null
        );

        var actual = InputLocationMapper.mapToModel(expected);

        assertEquals(expected, actual);
    }

    @Test
    void mapToInputLocationMap_ShouldReturnNullIfInputIsNull(){
        var dto = InputLocationMapper.mapToInputLocationMap(null);

        assertThat(dto).isNull();
    }

    @Test
    void mapToInputLocationMap_ShouldMapLocationsCorrectlyForValidInput(){
        var expected = validInputLocationDTOs();

        var actual = InputLocationMapper.mapToInputLocationMap(expected);

        assertEquals(actual, expected);
    }

    @Test
    void mapToInputLocationMap_ShouldMapLocationsCorrectlyForEmptyInput(){
        Map<Object, InputLocationDTO> expected = new LinkedHashMap<>();

        var actual = InputLocationMapper.mapToInputLocationMap(expected);

        assertEquals(actual, expected);
    }

    @Test
    void mapToInputLocationDTOMap_ShouldReturnNullIfInputIsNull(){
        var dto = InputLocationMapper.mapToInputLocationDTOMap(null);

        assertThat(dto).isNull();
    }

    @Test
    void mapToInputLocationDTOMap_ShouldMapLocationsCorrectlyForValidInput(){
        var objectWithLocationsMap = validInputLocationWithLocationsMap();
        var expected = objectWithLocationsMap.getLocations();

        var actual = InputLocationMapper.mapToInputLocationDTOMap(objectWithLocationsMap);

        assertEquals(expected, actual);
    }

    @Test
    void mapToInputLocationDTOMap_ShouldMapLocationsCorrectlyForEmptyInput(){
        var objectWithLocationsMap = emptyInputLocationWithLocationsMap();
        var expected = objectWithLocationsMap.getLocations();

        var actual = InputLocationMapper.mapToInputLocationDTOMap(objectWithLocationsMap);

        assertEquals(expected, actual);
    }

    @Test
    void mapToInputLocationDTOMap_ShouldThrowExceptionIfInputNotInsideMavenModelPackage(){
        assertThatThrownBy(() ->
            InputLocationMapper.mapToInputLocationDTOMap(new Object())
        ).isInstanceOf(MappingException.class)
                .hasMessageContaining("Object is not part of the Maven model package or the model definition. The Locations can not be extracted");

    }
}