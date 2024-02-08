package org.nuberjonas.pompalette.mapping.mappingapi.mapper;

import org.junit.jupiter.api.Test;
import org.nuberjonas.pompalette.mapping.mappingapi.exceptions.MappingException;
import org.nuberjonas.pompalette.mapping.mappingapi.mapper.classes.OtherClass;
import org.nuberjonas.pompalette.mapping.mappingapi.mapper.classes.SubClass;
import org.nuberjonas.pompalette.mapping.mappingapi.mapper.classes.SuperClass;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SuperClassMapperTest {

    @Test
    void mapFields_ShouldReturnNullForEmptyInput(){
        var actual = SuperClassMapper.mapFields(null, SubClass.class);

        assertThat(actual).isNull();
    }

    @Test
    void mapFields_ShouldThrowErrorWhenTargetTypeIsNotASubclassOfSourceType(){
        assertThatThrownBy(() ->
                SuperClassMapper.mapFields(new SuperClass(), OtherClass.class)
        ).isInstanceOf(MappingException.class)
                .hasMessageContaining("Target type must be a subclass of source type");
    }

    @Test
    void mapFields_ShouldMapFieldsCorrectlyForValidInput(){
        var expected = new SuperClass();
        expected.setNumber(42);
        expected.setText("someText");

        var actual = SuperClassMapper.mapFields(expected, SubClass.class);

        assertThat(actual.getNumber()).isEqualTo(expected.getNumber());
        assertThat(actual.getText()).isEqualTo(expected.getText());
        assertThat(actual.getSecondText()).isNull();
    }
}