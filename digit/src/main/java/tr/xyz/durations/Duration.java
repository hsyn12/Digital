package tr.xyz.durations;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * {@code Duration} is a holder that can be hold any {@code long} value.
 * It has no limits. Duration units:
 * <ul>
 *      <li>{@link Second} can be created with {@link Duration#ofSeconds(long)}</li>
 *      <li>{@link Minute} can be created with {@link Duration#ofMinutes(long)}</li>
 *      <li>{@link Hour} can be created with {@link Duration#ofHours(long)}</li>
 *      <li>{@link Day} can be created with {@link Duration#ofDays(long)}</li>
 *      <li>{@link Month} can be created with {@link Duration#ofMonths(long)}</li>
 *      <li>{@link Year} can be created with {@link Duration#ofYears(long)}</li>
 * </ul>
 */
public interface Duration {
	
	/**
	 * Creates a new {@link Duration} instance with the given {@link Class} and {@code long} value.
	 *
	 * @param durationClass the {@link Class} of the {@link Duration}
	 * @param value         the {@code long} value
	 * @return the created {@link Duration}
	 */
	@NotNull
	static Duration of(@NotNull Class<? extends Duration> durationClass, long value) {
		if (durationClass == Millisecond.class) return ofMilliseconds(value);
		else if (durationClass == Second.class) return ofSeconds(value);
		else if (durationClass == Minute.class) return ofMinutes(value);
		else if (durationClass == Hour.class) return ofHours(value);
		else if (durationClass == Day.class) return ofDays(value);
		else if (durationClass == Month.class) return ofMonths(value);
		else if (durationClass == Year.class) return ofYears(value);
		else throw new IllegalArgumentException();
	}
	
	@NotNull
	static Duration ofMilliseconds(long value) {
		return new MillisecondImpl(value);
	}
	
	/**
	 * Creates a new {@link Second} instance with the given {@code long} value.
	 *
	 * @param value the {@code long} value
	 * @return the created {@link Second}
	 */
	@NotNull
	static Second ofSeconds(long value) {
		return new SecondImpl(value);
	}
	
	/**
	 * Creates a new {@link Minute} instance with the given {@code long} value.
	 *
	 * @param value the {@code long} value
	 * @return the created {@link Minute}
	 */
	@NotNull
	static Minute ofMinutes(long value) {
		return new MinuteImpl(value);
	}
	
	/**
	 * Creates a new {@link Hour} instance with the given {@code long} value.
	 *
	 * @param value the {@code long} value
	 * @return the created {@link Hour}
	 */
	@NotNull
	static Hour ofHours(long value) {
		return new HourImpl(value);
	}
	
	/**
	 * Creates a new {@link Day} instance with the given {@code long} value.
	 *
	 * @param value the {@code long} value
	 * @return the created {@link Day}
	 */
	@NotNull
	static Day ofDays(long value) {
		return new DayImpl(value);
	}
	
	/**
	 * Creates a new {@link Month} instance with the given {@code long} value.
	 *
	 * @param value the {@code long} value
	 * @return the created {@link Month}
	 */
	@NotNull
	static Month ofMonths(long value) {
		return new MonthImpl(value);
	}
	
	/**
	 * Creates a new {@link Year} instance with the given {@code long} value.
	 *
	 * @param value the {@code long} value
	 * @return the created {@link Year}
	 */
	@NotNull
	static Year ofYears(long value) {
		return new YearImpl(value);
	}
	
	/**
	 * @return the {@code long} value of the {@link Duration}
	 */
	long getValue();
	
	
	/**
	 * Adds the given {@code long} value to the {@link Duration}.
	 *
	 * @param value the {@code long} value to add
	 * @return new {@link Duration} with the added {@code long} value
	 */
	@SuppressWarnings("unchecked")
	default Duration plus(long value) {
		return Duration.of((Class<? extends Duration>) this.getClass().getInterfaces()[0], getValue() + value);
	}
	
	/**
	 * Subtracts the given {@code long} value from the {@link Duration}.
	 *
	 * @param value the {@code long} value to subtract
	 * @return new {@link Duration} with the subtracted {@code long} value
	 */
	@SuppressWarnings("unchecked")
	default Duration minus(long value) {
		return Duration.of((Class<? extends Duration>) this.getClass().getInterfaces()[0], getValue() - value);
	}
}

abstract class AbstractDuration implements Duration {
	
	@Override
	public int hashCode() {
		return Objects.hashCode(getValue());
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj == null) return false;
		return getClass().getInterfaces()[0] == obj.getClass().getInterfaces()[0] && getValue() == ((Duration) obj).getValue();
	}
	
	@Override
	public String toString() {
		if (this instanceof Second) return "S" + getValue();
		else if (this instanceof Minute) return "M" + getValue();
		else if (this instanceof Hour) return "H" + getValue();
		else if (this instanceof Day) return "D" + getValue();
		else if (this instanceof Month) return "M" + getValue();
		else if (this instanceof Year) return "Y" + getValue();
		else return "null";
	}
}

final class MillisecondImpl extends AbstractDuration implements Millisecond {
	private final long value;
	
	MillisecondImpl(long value) {
		this.value = value;
	}
	
	@Override
	public long getValue() {
		return value;
	}
}

final class SecondImpl extends AbstractDuration implements Second {
	private final long value;
	
	SecondImpl(long value) {
		this.value = value;
	}
	
	@Override
	public long getValue() {
		return value;
	}
}

final class MinuteImpl extends AbstractDuration implements Minute {
	private final long value;
	
	MinuteImpl(long value) {
		this.value = value;
	}
	
	@Override
	public long getValue() {
		return value;
	}
}

final class HourImpl extends AbstractDuration implements Hour {
	private final long value;
	
	HourImpl(long value) {
		this.value = value;
	}
	
	@Override
	public long getValue() {
		return value;
	}
}

final class DayImpl extends AbstractDuration implements Day {
	private final long value;
	
	DayImpl(long value) {
		this.value = value;
	}
	
	@Override
	public long getValue() {
		return value;
	}
}

final class MonthImpl extends AbstractDuration implements Month {
	private final long value;
	
	MonthImpl(long value) {
		this.value = value;
	}
	
	@Override
	public long getValue() {
		return value;
	}
}

final class YearImpl extends AbstractDuration implements Year {
	private final long value;
	
	YearImpl(long value) {
		this.value = value;
	}
	
	@Override
	public long getValue() {
		return value;
	}
}


