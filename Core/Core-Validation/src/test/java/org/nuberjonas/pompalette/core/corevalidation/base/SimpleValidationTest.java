package org.nuberjonas.pompalette.core.corevalidation.base;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.nuberjonas.pompalette.core.corevalidation.BaseTest;

class SimpleValidationTest extends BaseTest {

    private static SimpleValidation<Integer> validation;

    @BeforeAll
    static void init(){
        validation = SimpleValidation.from(i -> i == 2, "error");
    }

    @Test
    void test_ShouldReturnValidResult_ForValidInput() {
        var result = validation.test(2);

        var isValid = result.isValid();

        assertValid(isValid);
    }

    @Test
    void test_ShouldReturnInvalidResult_ForInvalidInput() {
        var result = validation.test(3);

        var isInvalid = result.isInvalid();

        assertInvalid(isInvalid);
    }
}