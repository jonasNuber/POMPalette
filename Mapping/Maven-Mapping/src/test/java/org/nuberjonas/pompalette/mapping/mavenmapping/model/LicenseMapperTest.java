package org.nuberjonas.pompalette.mapping.mavenmapping.model;

import org.apache.maven.model.License;
import org.junit.jupiter.api.Test;
import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.model.LicenseDTO;
import org.nuberjonas.pompalette.mapping.mavenmapping.MavenMapperBaseTest;

import java.lang.reflect.InvocationTargetException;

import static org.assertj.core.api.Assertions.assertThat;

class LicenseMapperTest extends MavenMapperBaseTest {

    @Test
    void mapToDTO_ShouldReturnNullIfInputIsNull(){
        var dto = LicenseMapper.mapToDTO(null);

        assertThat(dto).isNull();
    }

    @Test
    void mapToDTO_ShouldMapLicenseCorrectlyForValidInput() throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        var expected = validLicense();

        var actual = LicenseMapper.mapToDTO(expected);

        assertEquals(actual, expected);
    }

    @Test
    void mapToDTO_ShouldMapLicenseCorrectlyForEmptyInput() throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        var expected = new License();

        var actual = LicenseMapper.mapToDTO(expected);

        assertEquals(actual, expected);
    }

    @Test
    void mapToModel_ShouldReturnNullIfInputIsNull(){
        var model = LicenseMapper.mapToModel(null);

        assertThat(model).isNull();
    }

    @Test
    void mapToModel_ShouldMapLicenseDTOCorrectlyForValidInput() throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        var expected = validLicenseDTO();

        var actual = LicenseMapper.mapToModel(expected);

        assertEquals(expected, actual);
    }

    @Test
    void mapToModel_ShouldMapLicenseDTOCorrectlyForEmptyInput() throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        var expected = new LicenseDTO(null, null, null, null, null, null, null, null, null, null);

        var actual = LicenseMapper.mapToModel(expected);

        assertEquals(expected, actual);
    }
}