package org.nuberjonas.pompalette.core.corevalidation.helpers;

import org.junit.jupiter.api.Test;
import org.nuberjonas.pompalette.core.corevalidation.base.Validation;

import static org.assertj.core.api.Assertions.assertThat;

class StringValidationHelpersTest {

    @Test
    void exactly_ShouldReturnValidResult_ForStringOfExactLength() {
        var size = 5;

        var validation = StringValidationHelpers.exactly(size);

        assertValid(validation, "abcde");
    }

    @Test
    void exactly_ShouldReturnInvalidResult_ForStringOfSmallerLength() {
        var size = 5;

        var validation = StringValidationHelpers.exactly(size);

        assertInvalid(validation, "abcd");
    }

    @Test
    void exactly_ShouldReturnInvalidResult_ForStringOfGreaterLength() {
        var size = 5;

        var validation = StringValidationHelpers.exactly(size);

        assertInvalid(validation, "abcdef");
    }

    @Test
    void moreThan_ShouldReturnValidResult_ForStringOfGreaterLengthThanMin() {
        var minimum = 3;

        var validation = StringValidationHelpers.moreThan(minimum);

        assertValid(validation, "abcd");
    }

    @Test
    void moreThan_ShouldReturnInvalidResult_ForStringOfSameLengthThanMin() {
        var minimum = 3;

        var validation = StringValidationHelpers.moreThan(minimum);

        assertInvalid(validation, "abc");
    }

    @Test
    void moreThan_ShouldReturnInvalidResult_ForStringOfSmallerLengthThanMin() {
        var minimum = 3;

        var validation = StringValidationHelpers.moreThan(minimum);

        assertInvalid(validation, "ab");
    }

    @Test
    void lessThan_ShouldReturnValidResult_ForStringOfSmallerLengthThanMax() {
        var maximum = 5;

        var validation = StringValidationHelpers.lessThan(maximum);

        assertValid(validation, "abcd");
    }

    @Test
    void lessThan_ShouldReturnInvalidResult_ForStringOfSameLengthAsMax() {
        var maximum = 5;

        var validation = StringValidationHelpers.lessThan(maximum);

        assertInvalid(validation, "abcde");
    }

    @Test
    void lessThan_ShouldReturnInvalidResult_ForStringOfGreaterLengthThanMax() {
        var maximum = 5;

        var validation = StringValidationHelpers.lessThan(maximum);

        assertInvalid(validation, "abcdef");
    }

    @Test
    void between_ShouldReturnValidResult_ForStringOfLengthSmallerThanMaxAndGreaterThanMin() {
        var minSize = 3;
        var maxSize = 10;

        var validation = StringValidationHelpers.between(minSize, maxSize);

        assertValid(validation, "abcd");
        assertValid(validation, "abcdefg");
    }

    @Test
    void between_ShouldReturnInvalidResult_ForStringOfSameLengthThanMin() {
        var minSize = 3;
        var maxSize = 10;

        var validation = StringValidationHelpers.between(minSize, maxSize);

        assertInvalid(validation, "abc");
    }

    @Test
    void between_ShouldReturnInvalidResult_ForStringOfSameLengthThanMax() {
        var minSize = 3;
        var maxSize = 10;

        var validation = StringValidationHelpers.between(minSize, maxSize);

        assertInvalid(validation, "abcdefghij");
    }

    @Test
    void between_ShouldReturnInvalidResult_ForStringOfSmallerLengthThanMin() {
        var minSize = 3;
        var maxSize = 10;

        var validation = StringValidationHelpers.between(minSize, maxSize);

        assertInvalid(validation, "ab");
    }

    @Test
    void between_ShouldReturnInvalidResult_ForStringOfGreaterLengthThanMax() {
        var minSize = 3;
        var maxSize = 10;

        var validation = StringValidationHelpers.between(minSize, maxSize);

        assertInvalid(validation, "abcdefghijk");
    }

    @Test
    void contains_ShouldReturnValidResult_ForContainedString() {
        var str = "test";

        var validation = StringValidationHelpers.contains(str);

        assertValid(validation, "this is a test");
    }

    @Test
    void contains_ShouldReturnInvalidResult_ForCaseSensitiveString() {
        var str = "test";

        var validation = StringValidationHelpers.contains(str);

        assertInvalid(validation, "this is a Test");
    }

    @Test
    void containsIgnoreCase_ShouldReturnValidResult_ForCaseInsensitiveString() {
        var str = "test";

        var validation = StringValidationHelpers.containsIgnoreCase(str);

        assertValid(validation, "this is a Test");
    }

    @Test
    void containsIgnoreCase_ShouldReturnInvalidResult_ForNotContainedString() {
        var str = "test";

        var validation = StringValidationHelpers.containsIgnoreCase(str);

        assertInvalid(validation, "this is a tes");
    }

    private void assertValid(Validation<String> validation, String value) {
        assertThat(validation.test(value).isValid()).isTrue();
    }

    private void assertInvalid(Validation<String> validation, String value) {
        assertThat(validation.test(value).isInvalid()).isTrue();
    }
}