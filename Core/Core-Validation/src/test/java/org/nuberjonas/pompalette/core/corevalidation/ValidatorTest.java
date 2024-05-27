package org.nuberjonas.pompalette.core.corevalidation;

import org.junit.jupiter.api.Test;
import org.nuberjonas.pompalette.core.corevalidation.base.Validation;

import static org.assertj.core.api.Assertions.assertThat;

class ValidatorTest {

    private final Validator<Object> validator = toValidate -> {
        // No validation logic needed for this test
    };

    @Test
    void notNull_ShouldReturnValidResult_ForNotNullObject() {
        var validation = validator.notNull();

        assertValid(validation, new Object());
    }

    @Test
    void notNull_ShouldReturnInvalidResult_ForNullObject() {
        var validation = validator.notNull();

        assertInvalid(validation, null);
    }

    @Test
    void isEqualTo_ShouldReturnValidResult_ForEqualObject() {
        var testString = "test";

        var validation = validator.isEqualTo(testString);

        assertValid(validation, "test");
    }

    @Test
    void isEqualTo_ShouldReturnInvalidResult_ForNotEqualObject() {
        var testString = "test";

        var validation = validator.isEqualTo(testString);

        assertInvalid(validation, "different");
    }

    @Test
    void exactly_ShouldReturnValidResult_ForStringOfSameLengthAsSize() {
        var size = 5;

        var validation = validator.exactly(size);

        assertValid(validation, "abcde");
    }

    @Test
    void exactly_ShouldReturnInvalidResult_ForStringOfShorterLengthThanSize() {
        var size = 5;

        var validation = validator.exactly(size);

        assertInvalid(validation, "abcd");
    }

    @Test
    void exactly_ShouldReturnInvalidResult_ForStringOfGreaterLengthThanSize() {
        var size = 5;

        var validation = validator.exactly(size);

        assertInvalid(validation, "abcdef");
    }

    @Test
    void moreThan_ShouldReturnValidResult_ForStringOfGreaterLengthThanMin() {
        var minimum = 3;

        var validation = validator.moreThan(minimum);

        assertValid(validation, "abcd");
    }

    @Test
    void moreThan_ShouldReturnInvalidResult_ForStringOfSameLengthThanMin() {
        var minimum = 3;

        var validation = validator.moreThan(minimum);

        assertInvalid(validation, "abc");
    }

    @Test
    void moreThan_ShouldReturnInvalidResult_ForStringOfShorterLengthThanMin() {
        var minimum = 3;

        var validation = validator.moreThan(minimum);

        assertInvalid(validation, "ab");
    }

    @Test
    void lessThan_ShouldReturnValidResult_ForStringShorterLengthThanMax() {
        var maximum = 5;

        var validation = validator.lessThan(maximum);

        assertValid(validation, "abcd");
    }

    @Test
    void lessThan_ShouldReturnInvalidResult_ForStringSameLengthThanMax() {
        var maximum = 5;

        var validation = validator.lessThan(maximum);

        assertInvalid(validation, "abcde");
    }

    @Test
    void lessThan_ShouldReturnInvalidResult_ForStringGreaterLengthThanMax() {
        var maximum = 5;

        var validation = validator.lessThan(maximum);

        assertInvalid(validation, "abcdef");
    }

    @Test
    void between_ShouldReturnValidResult_ForStringSmallerLengthThanMaxAndGreaterLengthThanMin() {
        var minSize = 3;
        var maxSize = 10;

        var validation = validator.between(minSize, maxSize);

        assertValid(validation, "abcd");
        assertValid(validation, "abcdefg");
    }

    @Test
    void between_ShouldReturnInvalidResult_ForStringSameLengthAsMin() {
        var minSize = 3;
        var maxSize = 10;

        var validation = validator.between(minSize, maxSize);

        assertInvalid(validation, "abc");
    }

    @Test
    void between_ShouldReturnInvalidResult_ForStringSameLengthAsMax() {
        var minSize = 3;
        var maxSize = 10;

        Validation<String> validation = validator.between(minSize, maxSize);

        assertInvalid(validation, "abcdefghij");
    }

    @Test
    void between_ShouldReturnInvalidResult_ForStringSmallerLengthThanMin() {
        var minSize = 3;
        var maxSize = 10;

        Validation<String> validation = validator.between(minSize, maxSize);

        assertInvalid(validation, "ab");
    }

    @Test
    void between_ShouldReturnInvalidResult_ForStringGreaterLengthThanMax() {
        var minSize = 3;
        var maxSize = 10;

        Validation<String> validation = validator.between(minSize, maxSize);

        assertInvalid(validation, "abcdefghijk");
    }

    @Test
    void contains_ShouldReturnValidResult_ForStringWhichIsContained() {
        var str = "test";

        var validation = validator.contains(str);

        assertValid(validation, "this is a test");
    }

    @Test
    void contains_ShouldReturnInvalidResult_ForStringCaseSensitiveNotContained() {
        var str = "test";

        var validation = validator.contains(str);

        assertInvalid(validation, "this is a Test");
    }

    @Test
    void containsIgnoreCase_ShouldReturnValidResult_ForStringCaseInsensitiveContained() {
        var str = "test";

        var validation = validator.containsIgnoreCase(str);

        assertValid(validation, "this is a Test");
    }

    @Test
    void containsIgnoreCases_ShouldReturnInvalidResult_ForStringNotContained() {
        var str = "test";

        var validation = validator.containsIgnoreCase(str);

        assertInvalid(validation, "this is a tes");
    }

    @Test
    void sameAmount_ShouldReturnValidResult_ForIntegerWithExactValue() {
        var exact = 5;

        var validation = validator.sameAmount(exact);

        assertValid(validation, exact);
    }

    @Test
    void sameAmount_ShouldReturnInvalidResult_ForIntegerLessThanTheExactValue() {
        var exact = 5;

        var validation = validator.sameAmount(exact);

        assertInvalid(validation, exact - 1);
    }

    @Test
    void sameAmount_ShouldReturnInvalidResult_ForIntegerMoreThanTheExactValue() {
        var exact = 5;

        var validation = validator.sameAmount(exact);

        assertInvalid(validation, exact + 1);
    }

    @Test
    void lowerThan_ShouldReturnValidResult_ForIntegerLowerThanMax() {
        var max = 10;

        var validation = validator.lowerThan(max);

        assertValid(validation, max - 1);
    }

    @Test
    void lowerThan_ShouldReturnInvalidResult_ForIntegerSameAsMax() {
        var max = 10;

        var validation = validator.lowerThan(max);

        assertInvalid(validation, max);
    }

    @Test
    void lowerThan_ShouldReturnInvalidResult_ForIntegerGreaterThanMax() {
        var max = 10;

        var validation = validator.lowerThan(max);

        assertInvalid(validation, max + 1);
    }

    @Test
    void greaterThan_ShouldReturnValidResult_ForIntegerGreaterThanMin() {
        var min = 3;

        var validation = validator.greaterThan(min);

        assertValid(validation, min + 1);
    }

    @Test
    void greaterThan_ShouldReturnInvalidResult_ForIntegerSameAsMin() {
        var min = 3;

        var validation = validator.greaterThan(min);

        assertInvalid(validation, min);
    }

    @Test
    void greaterThan_ShouldReturnInvalidResult_ForIntegerLessThanMin() {
        var min = 3;

        var validation = validator.greaterThan(min);

        assertInvalid(validation, min - 1);
    }

    @Test
    void inBetween_ShouldReturnValidResult_ForIntegerBetweenMinAndMax() {
        var min = 3;
        var max = 10;

        var validation = validator.inBetween(min, max);

        assertValid(validation, min + 1);
        assertValid(validation, max - 1);
    }

    @Test
    void inBetween_ShouldReturnInvalidResult_ForIntegerSameAsMin() {
        var min = 3;
        var max = 10;

        var validation = validator.inBetween(min, max);

        assertInvalid(validation, min);
    }

    @Test
    void inBetween_ShouldReturnInvalidResult_ForIntegerSameAsMax() {
        var min = 3;
        var max = 10;

        var validation = validator.inBetween(min, max);

        assertInvalid(validation, max);
    }

    @Test
    void inBetween_ShouldReturnInvalidResult_ForIntegerLessThanMin() {
        var min = 3;
        var max = 10;

        var validation = validator.inBetween(min, max);

        assertInvalid(validation, min - 1);
    }

    @Test
    void inBetween_ShouldReturnInvalidResult_ForIntegerMoreThanMax() {
        var min = 3;
        var max = 10;

        var validation = validator.inBetween(min, max);

        assertInvalid(validation, max + 1);
    }

    private <K> void assertValid(Validation<K> validation, K value) {
        assertThat(validation.test(value).isValid()).isTrue();
    }

    private <K> void assertInvalid(Validation<K> validation, K value) {
        assertThat(validation.test(value).isInvalid()).isTrue();
    }
}