package tr.xyz.times;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;
import tr.xyz.durations.Duration;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;


/**
 * The main purpose of this interface to find difference between two times more precisely.
 * {@link LocalDateTime} also have methods to find difference between two times,
 * but they are not precise because of cutting some duration parts.
 * However, they are useful and can be used anyway.
 * <br>
 * {@snippet lang = java:
 * var now        = Times.of(2024, 5, 6, 14, 50);
 * var myBirthDay = Times.of(1981, 12, 4, 23, 45);
 * var between    = now.between(myBirthDay);
 * var test       = myBirthDay.plus(between);
 * Assertions.assertEquals(now, test); // ✪
 *}
 *
 * <p>
 * Also, this interface has static methods that create {@code Times} with the given values
 * and all returned objects are mutable.
 * But all operations between {@code Times} objects return new objects without changing the original.
 * <br>
 * {@snippet lang = java:
 * var onesUponATime = Times.of(1881);
 * // This operation does not change the value of onesUponATime, returns a new instance.
 * var oneYearsLater = onesUponATime.plus(Times.of(1));
 * Assertions.assertEquals(1882, oneYearsLater.getYear().getValue());
 * Assertions.assertEquals(1881, onesUponATime.getYear().getValue());
 *
 * // This operation changes the value of onesUponATime.
 * onesUponATime.getYear().increment();
 * Assertions.assertEquals(1882, onesUponATime.getYear().getValue());
 *}
 */
public interface Times extends Comparable<Times> {
	
	/**
	 * Create new {@code Times} with the given year value.
	 * All other values will be set the defaults, month and day will be 1, hour, minute, second and millisecond will be 0.
	 *
	 * @param year year
	 * @return new {@code Times}
	 */
	@NotNull
	static Times of(long year) {
		return new TimeDigits(year, 1, 1);
	}
	
	/**
	 * Create new {@code Times} with the given values.
	 * The values should be in range but do not have to.
	 * Values that are not in the range will be cycled.
	 * This cycle will change the times.
	 * If you have the time that what you write, keep values in their range.
	 *
	 * @param year  year value
	 * @param month month value
	 * @param day   day value
	 * @return new {@code Times}
	 */
	@NotNull
	static Times of(long year, @Range(from = 1, to = 12) long month, @Range(from = 1, to = 30) long day) {
		return new TimeDigits(year, month, day);
	}
	
	/**
	 * Create new {@code Times} with the given values.
	 * The values should be in range but do not have to.
	 * Values that are not in the range will be cycled.
	 * This cycle will change the times.
	 * If you have the time that what you write, keep values in their range.
	 *
	 * @param year   year
	 * @param month  month
	 * @param day    day
	 * @param hour   hour
	 * @param minute minute
	 * @return new {@code Times}
	 */
	@NotNull
	static Times of(long year, @Range(from = 1, to = 12) long month, @Range(from = 1, to = 30) long day, @Range(from = 0, to = 23) long hour, @Range(from = 0, to = 59) long minute) {
		return new TimeDigits(year, month, day, hour, minute);
	}
	
	/**
	 * Create new {@code Times} with the given values.
	 * The values should be in range but do not have to.
	 * Values that are not in the range will be cycled.
	 * This cycle will change the times.
	 * If you have the time that what you write, keep values in their range.
	 *
	 * @param year        year
	 * @param month       month
	 * @param day         day
	 * @param hour        hour
	 * @param minute      minute
	 * @param second      second
	 * @param millisecond millisecond
	 * @return new {@code Times}
	 */
	@NotNull
	static Times of(long year, @Range(from = 1, to = 12) long month, @Range(from = 1, to = 30) long day, @Range(from = 0, to = 23) long hour, @Range(from = 0, to = 59) long minute, @Range(from = 0, to = 59) long second, @Range(from = 0, to = 999) long millisecond) {
		return new TimeDigits(year, month, day, hour, minute, second, millisecond);
	}
	
	/**
	 * Create new {@code Times} from {@link LocalDateTime}.
	 *
	 * @param dateTime dateTime
	 * @return new {@code Times}
	 */
	@NotNull
	static Times of(@NotNull LocalDateTime dateTime) {
		return new TimeDigits(dateTime);
	}
	
	/**
	 * Returns current time.
	 *
	 * @return new {@code Times}
	 */
	@NotNull
	static Times now() {
		return new TimeDigits(LocalDateTime.now());
	}
	
	/**
	 * Returns epoch time.
	 *
	 * @return new {@code Times}
	 */
	@NotNull
	static Times epoch() {
		return new TimeDigits(
				LocalDateTime.ofEpochSecond(
						0,
						0,
						ZoneOffset.of(ZoneId.systemDefault().getRules().getOffset(Instant.now()).getId())));
	}
	
	/**
	 * Returns copy of this.
	 *
	 * @return new {@code Times}
	 */
	@NotNull
	Times copy();
	
	/**
	 * Returns new {@code Times} with the given value added to this.
	 *
	 * @param times the time to add
	 * @return new {@code Times}
	 */
	@NotNull
	Times plus(@NotNull Times times);
	
	/**
	 * Returns new {@code Times} with the given {@link Duration} added to this.
	 *
	 * @param duration the duration to add
	 * @return new {@code Times}
	 */
	@NotNull
	Times plus(@NotNull Duration duration);
	
	/**
	 * Returns new {@code Times} with the given {@link TimeDigit} added to this.
	 *
	 * @param timeDigit the time digit to add
	 * @return new {@code Times}
	 */
	@NotNull
	Times plus(@NotNull TimeDigit timeDigit);
	
	/**
	 * Returns new {@code Times} that represents the time between this and the given {@code Times}.
	 *
	 * @param times the given {@code Times}
	 * @return new {@code Times}
	 */
	@NotNull
	Times between(@NotNull Times times);
	
	/**
	 * @return new {@code LocalDateTime} equals of this.
	 */
	@NotNull
	LocalDateTime toLocalDateTime();
	
	/**
	 * @return {@link Millisecond} of this time.
	 */
	@NotNull
	TimeDigit getMillisecond();
	
	/**
	 * @return {@link Second} of this time.
	 */
	@NotNull
	TimeDigit getSecond();
	
	/**
	 * @return {@link Minute} of this time.
	 */
	@NotNull
	TimeDigit getMinute();
	
	/**
	 * @return {@link Hour} of this time.
	 */
	@NotNull
	TimeDigit getHour();
	
	/**
	 * @return {@link Day} of this time.
	 */
	@NotNull
	TimeDigit getDay();
	
	/**
	 * @return {@link Month} of this time.
	 */
	@NotNull
	TimeDigit getMonth();
	
	/**
	 * @return {@link Year} of this time.
	 */
	@NotNull
	TimeDigit getYear();
	
	/**
	 * Returns new {@code Times} with the given {@link TimeDigit} replaced with this time's time digit.
	 *
	 * @param timeDigit the time digit to replace
	 * @return new {@code Times}
	 */
	@NotNull
	Times with(@NotNull TimeDigit timeDigit);
	
}
