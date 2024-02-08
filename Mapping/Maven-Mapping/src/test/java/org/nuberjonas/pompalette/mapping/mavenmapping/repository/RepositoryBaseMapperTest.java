package org.nuberjonas.pompalette.mapping.mavenmapping.repository;

import org.apache.maven.model.RepositoryBase;
import org.junit.jupiter.api.Test;
import org.nuberjonas.pompalette.mapping.mavenmapping.MavenMapperBaseTest;

import java.lang.reflect.InvocationTargetException;

import static org.assertj.core.api.Assertions.assertThat;

class RepositoryBaseMapperTest extends MavenMapperBaseTest {

    @Test
    void mapToDTO_ShouldReturnNullIfInputIsNull(){
        var dto = RepositoryBaseMapper.mapToDTO(null);

        assertThat(dto).isNull();
    }

    @Test
    void mapToDTO_ShouldMapRepositoryBaseCorrectlyForValidInput() throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        var expected = validRepositoryBase();

        var actual = RepositoryBaseMapper.mapToDTO(expected);

        assertEquals(actual, expected);
    }

    @Test
    void mapToDTO_ShouldMapRepositoryBaseCorrectlyForEmptyInput() throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        var expected = new RepositoryBase();

        var actual = RepositoryBaseMapper.mapToDTO(expected);

        assertEquals(actual, expected);
    }

    @Test
    void mapToModel_ShouldReturnNullIfInputIsNull(){
        var model = RepositoryBaseMapper.mapToModel(null);

        assertThat(model).isNull();
    }

    @Test
    void mapToModel_ShouldMapRepositoryBaseDTOCorrectlyForValidInput() throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        var expected = validRepositoryBaseDTO();

        var actual = RepositoryBaseMapper.mapToModel(expected);

        assertEquals(expected, actual);
    }

    @Test
    void mapToModel_ShouldMapRepositoryBaseDTOCorrectlyForEmptyInput() throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        var expected = emptyRepositoryBaseDTO();

        var actual = RepositoryBaseMapper.mapToModel(expected);

        assertEquals(expected, actual);
    }
}