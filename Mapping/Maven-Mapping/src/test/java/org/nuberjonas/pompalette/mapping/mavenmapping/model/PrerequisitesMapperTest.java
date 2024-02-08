package org.nuberjonas.pompalette.mapping.mavenmapping.model;

import org.apache.maven.model.Prerequisites;
import org.junit.jupiter.api.Test;
import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.model.PrerequisitesDTO;
import org.nuberjonas.pompalette.mapping.mavenmapping.MavenMapperBaseTest;

import java.lang.reflect.InvocationTargetException;

import static org.assertj.core.api.Assertions.assertThat;

class PrerequisitesMapperTest extends MavenMapperBaseTest {

    @Test
    void mapToDTO_ShouldReturnNullIfInputIsNull(){
        var dto = PrerequisitesMapper.mapToDTO(null);

        assertThat(dto).isNull();
    }

    @Test
    void mapToDTO_ShouldMapPrerequisitesCorrectlyForValidInput() throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        var expected = validPrerequisites();

        var actual = PrerequisitesMapper.mapToDTO(expected);

        assertEquals(actual, expected);
    }

    @Test
    void mapToDTO_ShouldMapPrerequisitesCorrectlyForEmptyInput() throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        var expected = new Prerequisites();

        var actual = PrerequisitesMapper.mapToDTO(expected);

        assertEquals(actual, expected);
    }

    @Test
    void mapToModel_ShouldReturnNullIfInputIsNull(){
        var model = PrerequisitesMapper.mapToModel(null);

        assertThat(model).isNull();
    }

    @Test
    void mapToModel_ShouldMapPrerequisitesDTOCorrectlyForValidInput() throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        var expected = validPrerequisitesDTO();

        var actual = PrerequisitesMapper.mapToModel(expected);

        assertEquals(expected, actual);
    }

    @Test
    void mapToModel_ShouldMapPrerequisitesDTOCorrectlyForEmptyInput() throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        var expected = new PrerequisitesDTO(null, null, null, null);

        var actual = PrerequisitesMapper.mapToModel(expected);

        assertEquals(expected, actual);
    }
}