package de.nuberjonas.pompalette.mapping.mappingapi.mapper;

import de.nuberjonas.pompalette.mapping.mappingapi.exceptions.MappingException;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ListMapperTest {

    @Test
    void mapList_ShouldReturnNullForEmptyInput(){
        var actual = ListMapper.mapList(null, (s) -> s);

        assertThat(actual).isNull();
    }

    @Test
    void mapList_ShouldThrowExceptionForEmptyMappingFunction(){
        assertThatThrownBy(() ->
                ListMapper.mapList(Collections.emptyList(), null)
        ).isInstanceOf(MappingException.class)
                .hasMessageContaining("No mapping function specified");
    }

    @Test
    void mapList_ShouldMapListCorrectlyForSimpleValidInput(){
        List<String> expected = List.of("test", "test2");

        var actual = ListMapper.mapList(expected, (str1) -> str1);

        assertThat(actual).isEqualTo(expected);
    }
}