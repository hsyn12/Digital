package tr.xyz.digit;

import org.jetbrains.annotations.NotNull;

/**
 * The {@code Digit} interface defines a contract for objects that represent a single
 * digit.
 * <p>
 * The digit value will always be in the range of {@link #MIN} and {@link #MAX} (both inclusive).
 * {@link Digit#getMin()} must be less or equal than {@link Digit#getMax()}.
 * The minimum value cannot be greater than the maximum value ({@link IllegalArgumentException} will be thrown).
 * <br>
 * {@snippet lang = java:
 * // With minimum and maximum value 1-12.
 * Digit months = Digit.of(1, 12); // Digit value starts with minimum value (in this case 1).
 * months.isInRange(1); // true
 * months.isInRange(12); // true
 * months.isInRange(0); // false
 * months.isInRange(13); // false
 *}
 * <br>
 * {@code months} object is immutable. All static methods in the {@code Digit} interface produce immutable objects.
 * Operations on these objects will return a new object.
 * <br>
 * {@snippet lang = java:
 * Digit april = months.plus(3);
 *}
 *
 * @author hsyn 1 May 2024
 */
public interface Digit {
    /**
     * The minimum value of the digit. (inclusive)
     */
    long MIN = (Long.MIN_VALUE + 1) / 2;
    /**
     * The maximum value of the digit. (inclusive)
     */
    long MAX = (Long.MAX_VALUE - 1) / 2;

    /**
     * Returns a new immutable instance of {@code Digit} representing the value {@code 0}.
     * Maximum value will be set to the {@link #MAX} constant.
     * Minimum value will be set to the {@link #MIN} constant.
     *
     * @return a new immutable instance of {@code Digit} representing the value {@code 0}
     */
    @NotNull
    static Digit zero() {
        return new SimpleDigit(0L);
    }

    /**
     * Returns a new immutable instance of {@code Digit} representing the given {@code value}.
     * Maximum value will be set to the {@link #MAX} constant.
     * Minimum value will be set to the {@link #MIN} constant.
     *
     * @param value the value of the digit
     * @return a new immutable instance of {@code Digit} representing the given {@code value}
     */
    @NotNull
    static Digit of(long value) {
        return new SimpleDigit(value);
    }

    /**
     * Returns a new immutable instance of {@code Digit} representing the given {@code range}.
     * Digit value will be set to the minimum value of the range.
     *
     * @param min minimum value (inclusive)
     * @param max maximum value (inclusive)
     * @return a new immutable instance of {@code Digit} representing the given {@code range}
     */
    @NotNull
    static Digit range(long min, long max) {
        return new SimpleDigit(min, min, max);
    }

    /**
     * Returns a new immutable instance of {@code Digit} representing the given {@code value}.
     *
     * @param value value of the digit
     * @param min   minimum value (inclusive)
     * @param max   maximum value (inclusive)
     * @return a new immutable instance of {@code Digit} representing the given {@code value}
     */
    @NotNull
    static Digit of(long value, long min, long max) {
        return new SimpleDigit(value, min, max);
    }

    /**
     * Returns a new immutable instance of {@code Digit} representing the given {@code range}.
     *
     * @param min minimum value (inclusive)
     * @param max maximum value (inclusive)
     * @return a new immutable instance of {@code Digit} representing the given {@code range}
     */
    @NotNull
    static Digit of(long min, long max) {
        return new SimpleDigit(min, min, max);
    }

    /**
     * @return the value of the digit
     */
    long getValue();

    /**
     * @return the number of times the digit has been cycled.
     */
    long getCycleCount();

    long getRange();

    /**
     * The minimum value of the digit. (inclusive)
     *
     * @return the minimum value of the digit
     */
    default long getMin() {
        return MIN;
    }

    /**
     * The maximum value of the digit. (inclusive)
     *
     * @return the maximum value of the digit
     */
    default long getMax() {
        return MAX;
    }

    /**
     * Determines if the given value is within the range defined by the minimum
     * and maximum values.
     *
     * @param value the value to check if it is within the range
     * @return {@code true} if the value is within the range, {@code false}
     * otherwise
     */
    default boolean isInRange(long value) {
        return value >= getMin() && value <= getMax();
    }

    /**
     * Determines if the given Digit object is within the range defined by the
     * minimum and maximum values.
     *
     * @param digit the Digit object to check if it is within the range
     * @return {@code true} if the Digit object is within the range,
     * {@code false} otherwise h4ftXEkXHed9
     */
    default boolean isInRange(@NotNull Digit digit) {
        return isInRange(digit.getValue());
    }

    /**
     * Increments the value of the digit by 1.
     *
     * @return {@code Digit} representing the incremented value.
     */
    @NotNull
    Digit increment();

    /**
     * Decrements the value of the digit by 1.
     *
     * @return {@code Digit} representing the decremented value.
     */
    @NotNull
    Digit decrement();

    /**
     * Adds the given value to the value of the digit.
     *
     * @param plus the value to add
     * @return {@code Digit} representing the result of the addition.
     */
    @NotNull
    Digit plus(long plus);

    /**
     * Subtracts the given value from the value of the digit.
     *
     * @param minus the value to subtract
     * @return {@code Digit} representing the result of the subtraction.
     */
    @NotNull
    Digit minus(long minus);


}
