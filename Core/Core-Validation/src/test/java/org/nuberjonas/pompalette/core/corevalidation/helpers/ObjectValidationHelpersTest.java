package org.nuberjonas.pompalette.core.corevalidation.helpers;

import org.junit.jupiter.api.Test;
import org.nuberjonas.pompalette.core.corevalidation.base.Validation;

import static org.assertj.core.api.Assertions.assertThat;

class ObjectValidationHelpersTest {

    @Test
    void notNull_ShouldReturnValidResult_ForNotNullObject() {
        var validation = ObjectValidationHelpers.notNull();

        assertValid(validation, new Object());
    }

    @Test
    void notNull_ShouldReturnInvalidResult_ForNullObject() {
        var validation = ObjectValidationHelpers.notNull();

        assertInvalid(validation, null);
    }

    @Test
    void isEqualTo_ShouldReturnValidResult_ForEqualObjects() {
        var testString = "test";

        var validation = ObjectValidationHelpers.isEqualTo(testString);

        assertValid(validation, "test");
    }

    @Test
    void isEqualTo_ShouldReturnInvalidResult_ForNotEqualObjects() {
        var testString = "test";

        var validation = ObjectValidationHelpers.isEqualTo(testString);

        assertInvalid(validation, "different");
    }

    private <K> void assertValid(Validation<K> validation, K value) {
        assertThat(validation.test(value).isValid()).isTrue();
    }

    private <K> void assertInvalid(Validation<K> validation, K value) {
        assertThat(validation.test(value).isInvalid()).isTrue();
    }
}