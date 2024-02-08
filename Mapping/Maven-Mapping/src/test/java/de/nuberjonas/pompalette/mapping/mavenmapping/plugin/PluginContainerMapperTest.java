package de.nuberjonas.pompalette.mapping.mavenmapping.plugin;

import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.plugin.PluginContainerDTO;
import de.nuberjonas.pompalette.mapping.mavenmapping.MavenMapperBaseTest;
import org.apache.maven.model.PluginContainer;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;

import static org.assertj.core.api.Assertions.assertThat;

class PluginContainerMapperTest extends MavenMapperBaseTest {

    @Test
    void mapToDTO_ShouldReturnNullIfInputIsNull(){
        var dto = PluginContainerMapper.mapToDTO(null);

        assertThat(dto).isNull();
    }

    @Test
    void mapToDTO_ShouldMapPluginContainerCorrectlyForValidInput() throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        var expected = validPluginContainer();

        var actual = PluginContainerMapper.mapToDTO(expected);

        assertEquals(actual, expected);
    }

    @Test
    void mapToDTO_ShouldMapPluginContainerCorrectlyForEmptyInput() throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        var expected = new PluginContainer();

        var actual = PluginContainerMapper.mapToDTO(expected);

        assertEquals(actual, expected);
    }

    @Test
    void mapToModel_ShouldReturnNullIfInputIsNull(){
        var model = PluginContainerMapper.mapToModel(null);

        assertThat(model).isNull();
    }

    @Test
    void mapToModel_ShouldMapPluginContainerDTOCorrectlyForValidInput() throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        var expected = validPluginContainerDTO();

        var actual = PluginContainerMapper.mapToModel(expected);

        assertEquals(expected, actual);
    }

    @Test
    void mapToModel_ShouldMapPluginContainerDTOCorrectlyForEmptyInput() throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        var expected = new PluginContainerDTO(null, null, null, null);

        var actual = PluginContainerMapper.mapToModel(expected);

        assertEquals(expected, actual);
    }
}