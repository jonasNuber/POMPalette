package org.nuberjonas.pompalette.core.corevalidation.base;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SimpleValidationTest {

    private static SimpleValidation<Integer> validation;

    @BeforeAll
    static void init(){
        validation = SimpleValidation.from(i -> i == 2, "error");
    }

    @Test
    void test_ShouldReturnValidResultForValidInput() {
        var result = validation.test(2);

        assertThat(result.isValid()).isTrue();
    }

    @Test
    void test_ShouldReturnInvalidResultForInvalidInput() {
        var result = validation.test(3);

        assertThat(result.isValid()).isFalse();
    }
}