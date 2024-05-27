package org.nuberjonas.pompalette.core.corevalidation.base;

import org.junit.jupiter.api.Test;
import org.nuberjonas.pompalette.core.corevalidation.exceptions.InvalidAttributeValueException;

import static org.assertj.core.api.Assertions.*;

class ValidationResultTest {

    @Test
    void ok_ShouldReturnValidResult() {
        var result = ValidationResult.ok();

        assertThat(result.isValid()).isTrue();
        assertThat(result.isInvalid()).isFalse();
    }

    @Test
    void fail_ShouldReturnInvalidResult() {
        var errorMessage = "error message";
        var result = ValidationResult.fail(errorMessage);

        assertThat(result.isValid()).isFalse();
        assertThat(result.isInvalid()).isTrue();
    }

    @Test
    void throwIfInvalid_ShouldNotThrowExceptionForValidResult() {
        var result = ValidationResult.ok();

        assertThatCode(() -> result.throwIfInvalid("fieldName"))
                .doesNotThrowAnyException();
    }

    @Test
    void throwIfInvalid_ShouldThrowExceptionForInvalidResult() {
        var errorMessage = "is not valid";
        var result = ValidationResult.fail(errorMessage);

        assertThatThrownBy(() -> result.throwIfInvalid("fieldName"))
                .isInstanceOf(InvalidAttributeValueException.class)
                .hasMessage(String.format("The field: \"%s\" is invalid, because %s", "fieldName", errorMessage));
    }
}