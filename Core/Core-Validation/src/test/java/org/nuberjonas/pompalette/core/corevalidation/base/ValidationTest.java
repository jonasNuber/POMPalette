package org.nuberjonas.pompalette.core.corevalidation.base;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ValidationTest {

    private static Validation<Object> valid;
    private static Validation<Object> inValid;

    @BeforeAll
    public static void innit() {
        valid = (k) -> ValidationResult.ok();
        inValid = (k) -> ValidationResult.fail("Validation failed");
    }

    @Test
    void and_ShouldBeTrueForTwoValidValidation() {
        var result = valid.and(valid).test(null);

        assertThat(result.isValid()).isTrue();
    }

    @Test
    void and_ShouldBeInvalidForTwoInvalidValidations() {
        var result = inValid.and(inValid).test(null);

        assertThat(result.isValid()).isFalse();
    }

    @Test
    void and_ShouldBeInvalidForOneValidAndOneInvalidValidation() {
        var resultValidInvalid = valid.and(inValid).test(null);
        var resultInvalidValid = inValid.and(valid).test(null);

        assertThat(resultValidInvalid.isValid()).isFalse();
        assertThat(resultInvalidValid.isValid()).isFalse();
    }

    @Test
    void or_ShouldBeValidForTwoValidValidations() {
        var result = valid.or(valid).test(null);

        assertThat(result.isValid()).isTrue();
    }

    @Test
    void or_ShouldBeInvalidForTwoInvalidValidations() {
        var result = inValid.or(inValid).test(null);

        assertThat(result.isValid()).isFalse();
    }

    @Test
    void or_ShouldBeValidForOneInvalidAndOneValidValidation() {
        var resultInvalidValid = inValid.or(valid).test(null);
        var resultValidInvalid = valid.or(inValid).test(null);

        assertThat(resultInvalidValid.isValid()).isTrue();
        assertThat(resultValidInvalid.isValid()).isTrue();
    }
}