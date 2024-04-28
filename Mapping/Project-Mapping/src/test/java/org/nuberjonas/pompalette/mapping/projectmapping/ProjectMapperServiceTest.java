package org.nuberjonas.pompalette.mapping.projectmapping;

import org.apache.maven.model.Model;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProjectMapperServiceTest extends MavenMapperBaseTest{

    private static ProjectMapperService mapperService;

    @BeforeAll
    static void init(){
        mapperService = new ProjectMapperService();
    }

    @Test
    void mapToDestination_ShouldThrowErrorWhenInputIsEmpty(){
        assertThatThrownBy(() ->
                mapperService.mapToDestination(null)
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Input cannot be empty");
    }

    @Test
    void mapToDestination_ShouldMapCorrectlyForValidInput() throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        var expected = validModel();

        var actual = mapperService.mapToDestination(expected);

        assertEquals(actual, expected);
    }

    @Test
    void mapToDestination_ShouldMapCorrectlyForEmptyInput() throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        var expected = new Model();

        var actual = mapperService.mapToDestination(expected);

        assertEquals(actual, expected);
    }

    @Test
    void mapToSource_ShouldThrowErrorWhenInputIsEmpty(){
        assertThatThrownBy(() ->
                mapperService.mapToSource(null)
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Input cannot be empty");
    }

    @Test
    void mapToSource_ShouldMapCorrectlyForValidInput() throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        var expected = validProjectDTO();

        var actual = mapperService.mapToSource(expected);

        assertEquals(expected, actual);
    }

    @Test
    void mapToSource_ShouldMapCorrectlyForEmptyInput() throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        var expected = emptyProjectDTO();

        var actual = mapperService.mapToSource(expected);

        assertEquals(expected, actual);
    }
}