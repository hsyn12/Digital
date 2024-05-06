package tr.xyz.times;

import org.jetbrains.annotations.NotNull;
import tr.xyz.digit.Digit;
import tr.xyz.digit.SimpleDigit;

/**
 * {@code TimeDigit} is a {@link Digit} which represents a time duration.
 * Each duration has a range.
 *
 * <ul>
 *     <li>{@link TimeDigit#millisecond(long)} -> {@code 0-999} -> {@link TimeDigitRange#MILLISECONDS}</li>
 *     <li>{@link TimeDigit#second(long)} -> {@code 0-59} -> {@link TimeDigitRange#SECONDS}</li>
 *     <li>{@link TimeDigit#minute(long)} -> {@code 0-59} -> {@link TimeDigitRange#MINUTES}</li>
 *     <li>{@link TimeDigit#hour(long)} -> {@code 0-23} -> {@link TimeDigitRange#HOURS}</li>
 *     <li>{@link TimeDigit#day(long)} -> {@code 1-30} -> {@link TimeDigitRange#DAYS}</li>
 *     <li>{@link TimeDigit#month(long)} -> {@code 1-12} -> {@link TimeDigitRange#MONTHS}</li>
 *     <li>{@link TimeDigit#year(long)} -> {@code Digit.MIN-Digit.MAX} -> {@link TimeDigitRange#YEARS}</li>
 * </ul>
 *
 *
 * <p>
 * {@snippet :
 *     var minutes = TimeDigit.minute(-1); // 59 minutes
 *}
 * <p>
 *     The {@code TimeDigit} is mutable. All out of range values will be cycled (like above).
 *     Digit value when cycled, {@link TimeDigit#onCycle(long)} will be called with the cycle count.
 *     The cycle count is the number of times the digit has been cycled. And if this value is negative,
 *     this means that the digit has been cycled from the minimum limit (like above).
 *     If the cycle count is positive, it means that the digit has been cycled from the maximum limit.
 *
 * <p>
 *     This is noting else than a mutable {@link Digit}.
 *     It is mutable and has certain ranges. This is it.
 */
public interface TimeDigit extends Digit, Comparable<TimeDigit> {
	
	/**
	 * Returns a new {@link TimeDigit} with the given class and value.
	 *
	 * @param clazz class of the new {@link TimeDigit}
	 * @param value value of the new {@link TimeDigit}
	 * @return a new {@link TimeDigit}
	 */
	@SuppressWarnings("unchecked")
	static <T extends TimeDigit> T of(Class<T> clazz, long value) {
		if (clazz == Millisecond.class) return (T) millisecond(value);
		if (clazz == Second.class) return (T) second(value);
		if (clazz == Minute.class) return (T) minute(value);
		if (clazz == Hour.class) return (T) hour(value);
		if (clazz == Day.class) return (T) day(value);
		if (clazz == Month.class) return (T) month(value);
		if (clazz == Year.class) return (T) year(value);
		throw new IllegalArgumentException();
	}
	
	/**
	 * Returns a new {@link Millisecond} with the given value.
	 *
	 * @param value the millisecond value
	 * @return a new {@link Millisecond}
	 */
	@NotNull
	static Millisecond millisecond(long value) {
		return new MillisecondImpl(value);
	}
	
	
	/**
	 * Returns a new {@link TimeDigit} in seconds limits with the given value.
	 * The given value if not in the range of {@code 0-59}, it will be cycled.
	 *
	 * @param value the second value
	 * @return a new {@link TimeDigit}
	 */
	@NotNull
	static Second second(long value) {
		return new SecondImpl(value);
	}
	
	/**
	 * Returns a new {@link TimeDigit} in minutes limits with the given value.
	 * The given value if not in the range of {@code 0-59}, it will be cycled.
	 *
	 * @param value the minute value
	 * @return a new {@link TimeDigit}
	 */
	@NotNull
	static Minute minute(long value) {
		return new MinuteImpl(value);
	}
	
	/**
	 * Returns a new {@link TimeDigit} in hours limits with the given value.
	 * The given value if not in the range of {@code 0-23}, it will be cycled.
	 *
	 * @param value the hour value
	 * @return a new {@link TimeDigit}
	 */
	@NotNull
	static Hour hour(long value) {
		return new HourImpl(value);
	}
	
	/**
	 * Returns a new {@link TimeDigit} in days limits with the given value.
	 * The given value if not in the range of {@code 1-30}, it will be cycled.
	 *
	 * @param value the day value
	 * @return a new {@link TimeDigit}
	 */
	@NotNull
	static Day day(long value) {
		return new DayImpl(value);
	}
	
	/**
	 * Returns a new {@link TimeDigit} in months limits with the given value.
	 * The given value if not in the range of {@code 1-12}, it will be cycled.
	 *
	 * @param value the month value
	 * @return a new {@link TimeDigit}
	 */
	@NotNull
	static Month month(long value) {
		return new MonthImpl(value);
	}
	
	/**
	 * Returns a new {@link TimeDigit} in years limits with the given value.
	 * Year value has no limits. (of course, it is in {@link Digit#MIN} and {@link Digit#MAX})
	 *
	 * @param value the year value
	 * @return a new {@link TimeDigit}
	 */
	@NotNull
	static Year year(long value) {
		return new YearImpl(value);
	}
	
	/**
	 * Called when the digit when cycled.
	 *
	 * @param cycleCount the cycle count
	 */
	void onCycle(long cycleCount);
	
	/**
	 * Sets the left digit.
	 *
	 * @param digit the left digit to get action when cycling occurs.
	 */
	void leftDigit(TimeDigit digit);
}


abstract class AbstractTimeDigit extends SimpleDigit implements TimeDigit {
	private long      value;
	private TimeDigit leftDigit;
	private long      cycleCount;
	
	public AbstractTimeDigit(long value, @NotNull Digit range) {
		super(value, range.getMin(), range.getMax());
		this.value      = super.getValue();
		this.cycleCount = super.getCycleCount();
	}
	
	@Override
	public long getValue() {
		return value;
	}
	
	private void setValue(long value) {
		var result = computeValue(value);
		this.value = result[0];
		setCycleCount(cycleCount = result[1]);
	}
	
	@Override
	public long getCycleCount() {
		return cycleCount;
	}
	
	private void setCycleCount(long cycleCount) {
		if (cycleCount != 0L && leftDigit != null) leftDigit.onCycle(cycleCount);
	}
	
	@Override
	public @NotNull Digit increment() {
		return plus(1);
	}
	
	@Override
	public @NotNull Digit decrement() {
		return minus(1);
	}
	
	@Override
	public @NotNull Digit plus(long plus) {
		setValue(this.value + plus);
		return this;
	}
	
	@Override
	public @NotNull Digit minus(long minus) {
		setValue(this.value - minus);
		return this;
	}
	
	@Override
	public void onCycle(long cycleCount) {
		if (cycleCount != 0L) plus(cycleCount);
	}
	
	@Override
	public void leftDigit(TimeDigit digit) {
		leftDigit = digit;
	}
	
	@Override
	public int compareTo(@NotNull TimeDigit o) {
		if (this.getMin() == o.getMin() && this.getMax() == o.getMax()) {
			return Long.compare(this.value, o.getValue());
		}
		throw new IllegalArgumentException("Comparing digits with different min/max values is not supported");
	}
}


final class MillisecondImpl extends AbstractTimeDigit implements Millisecond {
	public MillisecondImpl(long value) {
		super(value, TimeDigitRange.MILLISECONDS);
	}
}

final class SecondImpl extends AbstractTimeDigit implements Second {
	public SecondImpl(long value) {
		super(value, TimeDigitRange.SECONDS);
	}
}

final class MinuteImpl extends AbstractTimeDigit implements Minute {
	public MinuteImpl(long value) {
		super(value, TimeDigitRange.MINUTES);
	}
}

final class HourImpl extends AbstractTimeDigit implements Hour {
	public HourImpl(long value) {
		super(value, TimeDigitRange.HOURS);
	}
}

final class DayImpl extends AbstractTimeDigit implements Day {
	public DayImpl(long value) {
		super(value, TimeDigitRange.DAYS);
	}
}

final class MonthImpl extends AbstractTimeDigit implements Month {
	public MonthImpl(long value) {
		super(value, TimeDigitRange.MONTHS);
	}
}

final class YearImpl extends AbstractTimeDigit implements Year {
	public YearImpl(long value) {
		super(value, TimeDigitRange.YEARS);
	}
}

