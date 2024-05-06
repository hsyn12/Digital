package tr.xyz.times;

import tr.xyz.digit.Digit;

/**
 * Defines the ranges of {@link TimeDigit} unit durations.
 */
abstract class TimeDigitRange {
	/**
	 * The range of {@link Millisecond} ({@code 0-999}).
	 */
	public static final Digit MILLISECONDS = Digit.range(0, 999);
	/**
	 * The range of {@link Second} ({@code 0-59}).
	 */
	public static final Digit SECONDS      = Digit.range(0, 59);
	/**
	 * The range of {@link Minute} ({@code 0-59}).
	 */
	public static final Digit MINUTES      = Digit.range(0, 59);
	/**
	 * The range of {@link Hour} ({@code 0-23}).
	 */
	public static final Digit HOURS        = Digit.range(0, 23);
	/**
	 * The range of {@link Day} ({@code 1-30}).
	 */
	public static final Digit DAYS         = Digit.range(1, 30);
	/**
	 * The range of {@link Month} ({@code 1-12}).
	 */
	public static final Digit MONTHS       = Digit.range(1, 12);
	/**
	 * The range of {@link Year} ({@code Digit.MIN-Digit.MAX}).
	 */
	public static final Digit YEARS        = Digit.range(Digit.MIN, Digit.MAX);
}
