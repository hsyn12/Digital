package tr.xyz.digit;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

@Getter
public class SimpleDigit implements Digit {
	
	private final long value;
	private final long min;
	private final long max;
	private final long cycleCount;
	private final long range;
	
	public SimpleDigit(long value) {
		this(value, Digit.MIN, Digit.MAX);
	}
	
	public SimpleDigit(long value, long min, long max) {
		this.min = checkMinLimit(min);
		this.max = checkMaxLimit(max);
		if (min > max) throw new IllegalArgumentException("Minimum value cannot be greater than maximum value.");
		this.range = (max - min) + 1;
		var r = computeValue(value);
		this.value      = r[0];
		this.cycleCount = r[1];
	}
	
	private long checkMinLimit(long min) {
		if (min >= Digit.MIN) return min;
		throw new IllegalArgumentException("Minimum value cannot be less than " + Digit.MIN);
	}
	
	private long checkMaxLimit(long max) {
		if (max <= Digit.MAX) return max;
		throw new IllegalArgumentException("Maximum value cannot be less than " + Digit.MAX);
	}
	
	protected final long @NotNull [] computeValue(long value) {
		final var result = new long[]{value, 0};
		if (isInRange(value)) {
			return result;
		}
		if (value < min) {
			var interval = min - value;
			if (interval < range) {
				result[0] = (max - interval) + 1;
				result[1] = -1;
			}
			else if (interval > range) {
				result[0] = ((max + 1) - range % interval);
				result[1] = -(interval / range);
			}
			else {
				result[0] = min;
				result[1] = -1;
			}
		}
		else {
			var interval = value - max;
			if (interval < range) {
				result[0] = (min + interval) - 1;
				result[1] = 1;
			}
			else if (interval > range) {
				result[0] = (min + interval % range) - 1;
				result[1] = value / range;
			}
			else {
				result[0] = min;
				result[1] = 1;
			}
		}
		return result;
	}
	
	@NotNull
	@Override
	public Digit decrement() {
		return new SimpleDigit(value - 1, min, max);
	}
	
	@NotNull
	@Override
	public Digit increment() {
		return new SimpleDigit(value + 1, min, max);
	}
	
	@NotNull
	@Override
	public String toString() {
		return Long.toString(value);
	}
	
	@Override
	public @NotNull Digit plus(long plus) {
		return new SimpleDigit(value + plus, min, max);
	}
	
	@Override
	public @NotNull Digit minus(long minus) {
		return new SimpleDigit(value - minus, min, max);
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj instanceof Digit digit && digit.getValue() == value && digit.getMin() == min && digit.getMax() == max;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(value, min, max);
	}
}
