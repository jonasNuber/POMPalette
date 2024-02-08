package org.nuberjonas.pompalette.mapping.mavenmapping.reporting;

import org.apache.maven.model.ReportSet;
import org.junit.jupiter.api.Test;
import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.reporting.ReportSetDTO;
import org.nuberjonas.pompalette.mapping.mavenmapping.MavenMapperBaseTest;

import java.lang.reflect.InvocationTargetException;

import static org.assertj.core.api.Assertions.assertThat;

class ReportSetMapperTest extends MavenMapperBaseTest {

    @Test
    void mapToDTO_ShouldReturnNullIfInputIsNull(){
        var dto = ReportSetMapper.mapToDTO(null);

        assertThat(dto).isNull();
    }

    @Test
    void mapToDTO_ShouldMapReportSetCorrectlyForValidInput() throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        var expected = validReportSet();

        var actual = ReportSetMapper.mapToDTO(expected);

        assertEquals(actual, expected);
    }

    @Test
    void mapToDTO_ShouldMapReportSetCorrectlyForEmptyInput() throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        var expected = new ReportSet();

        var actual = ReportSetMapper.mapToDTO(expected);

        assertEquals(actual, expected);
    }

    @Test
    void mapToModel_ShouldReturnNullIfInputIsNull(){
        var model = ReportSetMapper.mapToModel(null);

        assertThat(model).isNull();
    }

    @Test
    void mapToModel_ShouldMapReportSetDTOCorrectlyForValidInput() throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        var expected = validReportSetDTO();

        var actual = ReportSetMapper.mapToModel(expected);

        assertEquals(expected, actual);
    }

    @Test
    void mapToModel_ShouldMapReportSetDTOCorrectlyForEmptyInput() throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        var expected = new ReportSetDTO(null, null, null);

        var actual = ReportSetMapper.mapToModel(expected);

        assertEquals(expected, actual);
    }
}