package org.nuberjonas.pompalette.mapping.mavenmapping.repository;

import org.apache.maven.model.Repository;
import org.junit.jupiter.api.Test;
import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.repository.RepositoryDTO;
import org.nuberjonas.pompalette.mapping.mavenmapping.MavenMapperBaseTest;

import java.lang.reflect.InvocationTargetException;

import static org.assertj.core.api.Assertions.assertThat;

class RepositoryMapperTest extends MavenMapperBaseTest {

    @Test
    void mapToDTO_ShouldReturnNullIfInputIsNull(){
        var dto = RepositoryMapper.mapToDTO(null);

        assertThat(dto).isNull();
    }

    @Test
    void mapToDTO_ShouldMapRepositoryCorrectlyForValidInput() throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        var expected = validRepository();

        var actual = RepositoryMapper.mapToDTO(expected);

        assertEquals(actual, expected);
    }

    @Test
    void mapToDTO_ShouldMapRepositoryCorrectlyForEmptyInput() throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        var expected = new Repository();

        var actual = RepositoryMapper.mapToDTO(expected);

        assertEquals(actual, expected);
    }

    @Test
    void mapToModel_ShouldReturnNullIfInputIsNull(){
        var model = RepositoryMapper.mapToModel(null);

        assertThat(model).isNull();
    }

    @Test
    void mapToModel_ShouldMapRepositoryDTOCorrectlyForValidInput() throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        var expected = validRepositoryDTO();

        var actual = RepositoryMapper.mapToModel(expected);

        assertEquals(expected, actual);
    }

    @Test
    void mapToModel_ShouldMapRepositoryDTOCorrectlyForEmptyInput() throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        var expected = new RepositoryDTO(null, null, null);

        var actual = RepositoryMapper.mapToModel(expected);

        assertEquals(expected, actual);
    }
}