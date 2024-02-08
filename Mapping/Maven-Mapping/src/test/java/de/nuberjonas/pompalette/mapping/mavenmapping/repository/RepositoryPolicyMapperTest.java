package de.nuberjonas.pompalette.mapping.mavenmapping.repository;

import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.repository.RepositoryPolicyDTO;
import de.nuberjonas.pompalette.mapping.mavenmapping.MavenMapperBaseTest;
import org.apache.maven.model.RepositoryPolicy;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;

import static org.assertj.core.api.Assertions.assertThat;

class RepositoryPolicyMapperTest extends MavenMapperBaseTest {

    @Test
    void mapToDTO_ShouldReturnNullIfInputIsNull(){
        var dto = RepositoryPolicyMapper.mapToDTO(null);

        assertThat(dto).isNull();
    }

    @Test
    void mapToDTO_ShouldMapRepositoryPolicyCorrectlyForValidInput() throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        var expected = validRepositoryPolicy();

        var actual = RepositoryPolicyMapper.mapToDTO(expected);

        assertEquals(actual, expected);
    }

    @Test
    void mapToDTO_ShouldMapRepositoryPolicyCorrectlyForEmptyInput() throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        var expected = new RepositoryPolicy();

        var actual = RepositoryPolicyMapper.mapToDTO(expected);

        assertEquals(actual, expected);
    }

    @Test
    void mapToModel_ShouldReturnNullIfInputIsNull(){
        var model = RepositoryPolicyMapper.mapToModel(null);

        assertThat(model).isNull();
    }

    @Test
    void mapToModel_ShouldMapRepositoryPolicyDTOCorrectlyForValidInput() throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        var expected = validRepositoryPolicyDTO();

        var actual = RepositoryPolicyMapper.mapToModel(expected);

        assertEquals(expected, actual);
    }

    @Test
    void mapToModel_ShouldMapRepositoryPolicyDTOCorrectlyForEmptyInput() throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        var expected = new RepositoryPolicyDTO(false, null, null, null, null, null, null, null);

        var actual = RepositoryPolicyMapper.mapToModel(expected);

        assertEquals(expected, actual);
    }
}