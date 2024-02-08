package de.nuberjonas.pompalette.mapping.mavenmapping.contributing;

import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.contributing.MailingListDTO;
import de.nuberjonas.pompalette.mapping.mavenmapping.MavenMapperBaseTest;
import org.apache.maven.model.MailingList;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;

import static org.assertj.core.api.Assertions.assertThat;

class MailingListMapperTest extends MavenMapperBaseTest {

    @Test
    void mapToDTO_ShouldReturnNullIfInputIsNull(){
        var dto = MailingListMapper.mapToDTO(null);

        assertThat(dto).isNull();
    }

    @Test
    void mapToDTO_ShouldMapMailingListCorrectlyForValidInput() throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        var expected = validMailingList();

        var actual = MailingListMapper.mapToDTO(expected);

        assertEquals(actual, expected);
    }

    @Test
    void mapToDTO_ShouldMapMailingListCorrectlyForEmptyInput() throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        var expected = new MailingList();

        var actual = MailingListMapper.mapToDTO(expected);

        assertEquals(actual, expected);
    }

    @Test
    void mapToModel_ShouldReturnNullIfInputIsNull(){
        var model = MailingListMapper.mapToModel(null);

        assertThat(model).isNull();
    }

    @Test
    void mapToModel_ShouldMapMailingListDTOCorrectlyForValidInput() throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        var expected = validMailingListDTO();

        var actual = MailingListMapper.mapToModel(expected);

        assertEquals(expected, actual);
    }

    @Test
    void mapToModel_ShouldMapMailingListDTOCorrectlyForEmptyInput() throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        var expected = new MailingListDTO(null, null, null, null,null,null,null,null, null,null, null, null, null, null);

        var actual = MailingListMapper.mapToModel(expected);

        assertEquals(expected, actual);
    }
}