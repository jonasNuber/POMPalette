package org.nuberjonas.pompalette.core.corevalidation;

import org.nuberjonas.pompalette.core.corevalidation.base.Validation;
import org.nuberjonas.pompalette.core.corevalidation.helpers.IntegerValidationHelpers;
import org.nuberjonas.pompalette.core.corevalidation.helpers.ObjectValidationHelpers;
import org.nuberjonas.pompalette.core.corevalidation.helpers.StringValidationHelpers;

/**
 * A functional interface representing a validator for objects of type {@code K}.
 * <p>
 * This interface defines a single method {@code validate(K toValidate)} to perform validation on an object.
 * Additionally, it provides default methods for various pre-defined validations using helper classes:
 * <ul>
 *     <li>{@link ObjectValidationHelpers}: {@code notNull()}, {@code isEqualTo(K other)}</li>
 *     <li>{@link StringValidationHelpers}: {@code exactly(int size)}, {@code moreThan(int size)}, {@code lessThan(int size)},
 *         {@code between(int minSize, int maxSize)}, {@code contains(String str)}, {@code containsIgnoreCase(String str)}</li>
 *     <li>{@link IntegerValidationHelpers}: {@code sameAmount(int exact)}, {@code lowerThan(int max)}, {@code greaterThan(int min)},
 *         {@code inBetween(int min, int max)}</li>
 * </ul>
 * <p>
 * Implementations of this interface should provide custom validation logic within the {@code validate} method,
 * and may use the default methods to compose validations using pre-defined conditions.
 * </p>
 *
 * @author Jonas Nuber
 *
 * @param <K> The type of objects to be validated.
 */
@FunctionalInterface
public interface Validator<K> {

	/**
	 * Validates the given object.
	 *
	 * @param toValidate The object to be validated.
	 */
	void validate(K toValidate);

	/**
	 * Returns a validation that checks if the object is not null.
	 *
	 * @return The validation for not null.
	 */
	default <R> Validation<R> notNull() {
		return ObjectValidationHelpers.notNull();
	}

	/**
	 * Returns a validation that checks if the object is equal to the specified value.
	 *
	 * @param other The value to compare with.
	 * @return The validation for equality.
	 */
	default Validation<K> isEqualTo(K other) {
		return ObjectValidationHelpers.isEqualTo(other);
	}

	/**
	 * Returns a validation that checks if the string has exactly the specified number of characters.
	 *
	 * @param size The exact number of characters.
	 * @return The validation for exact string length.
	 */
	default Validation<String> exactly(int size) {
		return StringValidationHelpers.exactly(size);
	}

	/**
	 * Returns a validation that checks if the string has more than the specified number of characters.
	 *
	 * @param size The minimum number of characters.
	 * @return The validation for minimum string length.
	 */
	default Validation<String> moreThan(int size) {
		return StringValidationHelpers.moreThan(size);
	}

	/**
	 * Returns a validation that checks if the string has less than the specified number of characters.
	 *
	 * @param size The maximum number of characters.
	 * @return The validation for maximum string length.
	 */
	default Validation<String> lessThan(int size) {
		return StringValidationHelpers.lessThan(size);
	}

	/**
	 * Returns a validation that checks if the string length is between the specified minimum and maximum values (exclusive).
	 *
	 * @param minSize The minimum number of characters.
	 * @param maxSize The maximum number of characters.
	 * @return The validation for string length within a range.
	 */
	default Validation<String> between(int minSize, int maxSize) {
		return StringValidationHelpers.between(minSize, maxSize);
	}

	/**
	 * Returns a validation that checks if the string contains the specified substring.
	 *
	 * @param str The substring to search for.
	 * @return The validation for string containment.
	 */
	default Validation<String> contains(String str) {
		return StringValidationHelpers.contains(str);
	}

	/**
	 * Returns a validation that checks if the string contains the specified substring (case-insensitive).
	 *
	 * @param str The substring to search for.
	 * @return The validation for case-insensitive string containment.
	 */
	default Validation<String> containsIgnoreCase(String str) {
		return StringValidationHelpers.containsIgnoreCase(str);
	}

	/**
	 * Returns a validation that checks if the integer value is equal to the specified value.
	 *
	 * @param exact The exact value to compare with.
	 * @return The validation for equality.
	 */
	default Validation<Integer> sameAmount(int exact) {
		return IntegerValidationHelpers.sameAmount(exact);
	}

	/**
	 * Returns a validation that checks if the integer value is lower than the specified maximum.
	 *
	 * @param max The maximum value the integer must not exceed.
	 * @return The validation for values lower than the maximum.
	 */
	default Validation<Integer> lowerThan(int max) {
		return IntegerValidationHelpers.lowerThan(max);
	}

	/**
	 * Returns a validation that checks if the integer value is greater than the specified minimum.
	 *
	 * @param min The minimum value the integer must exceed.
	 * @return The validation for values greater than the minimum.
	 */
	default Validation<Integer> greaterThan(int min) {
		return IntegerValidationHelpers.greaterThan(min);
	}

	/**
	 * Returns a validation that checks if the integer value is within the specified range (exclusive).
	 *
	 * @param min The minimum value the integer must not be less than.
	 * @param max The maximum value the integer must not exceed.
	 * @return The validation for values within the range.
	 */
	default Validation<Integer> inBetween(int min, int max) {
		return IntegerValidationHelpers.inBetween(min, max);
	}
}
