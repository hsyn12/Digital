package tr.xyz.times;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import tr.xyz.durations.*;
import tr.xyz.durations.Day;
import tr.xyz.durations.Hour;
import tr.xyz.durations.Millisecond;
import tr.xyz.durations.Minute;
import tr.xyz.durations.Month;
import tr.xyz.durations.Second;
import tr.xyz.durations.Year;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Objects;

@Getter
public final class TimeDigits implements Times {
	private final TimeDigit year;
	private final TimeDigit month;
	private final TimeDigit day;
	private final TimeDigit hour;
	private final TimeDigit minute;
	private final TimeDigit second;
	private final TimeDigit millisecond;
	
	TimeDigits(long year, long month, long day) {
		this(year, month, day, 0, 0, 0, 0);
	}
	
	TimeDigits(long year, long month, long day, long hour, long minute) {
		this(year, month, day, hour, minute, 0, 0);
	}
	
	TimeDigits(long year, long month, long day, long hour, long minute, long second, long millisecond) {
		this.year        = TimeDigit.year(year);
		this.month       = TimeDigit.month(month);
		this.day         = TimeDigit.day(day);
		this.hour        = TimeDigit.hour(hour);
		this.minute      = TimeDigit.minute(minute);
		this.second      = TimeDigit.second(second);
		this.millisecond = TimeDigit.millisecond(millisecond);
		couple();
	}
	
	TimeDigits(@NotNull LocalDateTime dateTime) {
		this.year        = TimeDigit.year(dateTime.getYear());
		this.month       = TimeDigit.month(dateTime.getMonthValue());
		this.day         = TimeDigit.day(dateTime.getDayOfMonth());
		this.hour        = TimeDigit.hour(dateTime.getHour());
		this.minute      = TimeDigit.minute(dateTime.getMinute());
		this.second      = TimeDigit.second(dateTime.getSecond());
		this.millisecond = TimeDigit.millisecond(dateTime.getNano() / 1_000_000);
		
		couple();
	}
	
	private void couple() {
		millisecond.leftDigit(second);
		second.leftDigit(minute);
		minute.leftDigit(hour);
		hour.leftDigit(day);
		day.leftDigit(month);
		month.leftDigit(year);
	}
	
	@Override
	public @NotNull Times plus(@NotNull Times times) {
		TimeDigits first = new TimeDigits(this.toLocalDateTime());
		first.millisecond.plus(times.getMillisecond().getValue());
		first.second.plus(times.getSecond().getValue());
		first.minute.plus(times.getMinute().getValue());
		first.hour.plus(times.getHour().getValue());
		first.day.plus(times.getDay().getValue());
		first.month.plus(times.getMonth().getValue());
		first.year.plus(times.getYear().getValue());
		return first;
	}
	
	@Override
	public @NotNull Times plus(@NotNull Duration duration) {
		TimeDigits time = new TimeDigits(this.toLocalDateTime());
		if (duration instanceof Millisecond) time.millisecond.plus(duration.getValue());
		else if (duration instanceof Second) time.second.plus(duration.getValue());
		else if (duration instanceof Minute) time.minute.plus(duration.getValue());
		else if (duration instanceof Hour) time.hour.plus(duration.getValue());
		else if (duration instanceof Day) time.day.plus(duration.getValue());
		else if (duration instanceof Month) time.month.plus(duration.getValue());
		else if (duration instanceof Year) time.year.plus(duration.getValue());
		return time;
	}
	
	@Override
	public @NotNull Times plus(@NotNull TimeDigit timeDigit) {
		TimeDigits time = new TimeDigits(toLocalDateTime());
		if (timeDigit instanceof tr.xyz.times.Millisecond) time.millisecond.plus(timeDigit.getValue());
		if (timeDigit instanceof tr.xyz.times.Second) time.second.plus(timeDigit.getValue());
		if (timeDigit instanceof tr.xyz.times.Minute) time.minute.plus(timeDigit.getValue());
		if (timeDigit instanceof tr.xyz.times.Hour) time.hour.plus(timeDigit.getValue());
		if (timeDigit instanceof tr.xyz.times.Day) time.day.plus(timeDigit.getValue());
		if (timeDigit instanceof tr.xyz.times.Month) time.month.plus(timeDigit.getValue());
		if (timeDigit instanceof tr.xyz.times.Year) time.year.plus(timeDigit.getValue());
		return time;
	}
	
	@Override
	public @NotNull Times between(@NotNull Times timeDigits) {
		
		Times first;
		Times second;
		
		if (this.compareTo(timeDigits) > 0) {
			first  = new TimeDigits(this.toLocalDateTime());
			second = timeDigits;
		}
		else if (this.compareTo(timeDigits) < 0) {
			first  = new TimeDigits(timeDigits.toLocalDateTime());
			second = this;
		}
		else return new TimeDigits(LocalDateTime.of(0, 1, 1, 0, 0, 0));
		
		first.getMillisecond().minus(second.getMillisecond().getValue());
		first.getSecond().minus(second.getSecond().getValue());
		first.getMinute().minus(second.getMinute().getValue());
		first.getHour().minus(second.getHour().getValue());
		first.getDay().minus(second.getDay().getValue());
		first.getMonth().minus(second.getMonth().getValue());
		first.getYear().minus(second.getYear().getValue());
		return first;
	}
	
	@Override
	public @NotNull LocalDateTime toLocalDateTime() {
		return LocalDateTime.of((int) year.getValue(), (int) month.getValue(), (int) day.getValue(), (int) hour.getValue(), (int) minute.getValue(), (int) second.getValue(), (int) (millisecond.getValue() * 1_000_000));
	}
	
	@Override
	public @NotNull Times copy() {
		return new TimeDigits(year.getValue(), month.getValue(), day.getValue(), hour.getValue(), minute.getValue(), second.getValue(), millisecond.getValue());
	}
	
	@Override
	public @NotNull Times with(@NotNull TimeDigit timeDigit) {
		if (timeDigit instanceof Millisecond) return withMillisecond(timeDigit.getValue());
		if (timeDigit instanceof Second) return withSecond(timeDigit.getValue());
		if (timeDigit instanceof Minute) return withMinute(timeDigit.getValue());
		if (timeDigit instanceof Hour) return withHour(timeDigit.getValue());
		if (timeDigit instanceof Day) return withDay(timeDigit.getValue());
		if (timeDigit instanceof Month) return withMonth(timeDigit.getValue());
		if (timeDigit instanceof Year) return withYear(timeDigit.getValue());
		return this;
	}
	
	private @NotNull Times withYear(long value) {
		return new TimeDigits(value, month.getValue(), day.getValue(), hour.getValue(), minute.getValue(), second.getValue(), millisecond.getValue());
	}
	
	private @NotNull Times withMonth(long value) {
		return new TimeDigits(year.getValue(), value, day.getValue(), hour.getValue(), minute.getValue(), second.getValue(), millisecond.getValue());
	}
	
	private @NotNull Times withDay(long value) {
		return new TimeDigits(year.getValue(), month.getValue(), value, hour.getValue(), minute.getValue(), second.getValue(), millisecond.getValue());
	}
	
	private @NotNull Times withHour(long value) {
		return new TimeDigits(year.getValue(), month.getValue(), day.getValue(), value, minute.getValue(), second.getValue(), millisecond.getValue());
	}
	
	private @NotNull Times withMinute(long value) {
		return new TimeDigits(year.getValue(), month.getValue(), day.getValue(), hour.getValue(), value, second.getValue(), millisecond.getValue());
	}
	
	private @NotNull Times withSecond(long value) {
		return new TimeDigits(year.getValue(), month.getValue(), day.getValue(), hour.getValue(), minute.getValue(), value, millisecond.getValue());
	}
	
	private @NotNull Times withMillisecond(long value) {
		return new TimeDigits(year.getValue(), month.getValue(), day.getValue(), hour.getValue(), minute.getValue(), second.getValue(), value);
	}
	
	@Override
	public int compareTo(@NotNull Times o) {
		if (this.year.compareTo(o.getYear()) != 0) return this.year.compareTo(o.getYear());
		if (this.month.compareTo(o.getMonth()) != 0) return this.month.compareTo(o.getMonth());
		if (this.day.compareTo(o.getDay()) != 0) return this.day.compareTo(o.getDay());
		if (this.hour.compareTo(o.getHour()) != 0) return this.hour.compareTo(o.getHour());
		if (this.minute.compareTo(o.getMinute()) != 0) return this.minute.compareTo(o.getMinute());
		if (this.second.compareTo(o.getSecond()) != 0) return this.second.compareTo(o.getSecond());
		if (this.millisecond.compareTo(o.getMillisecond()) != 0) return this.millisecond.compareTo(o.getMillisecond());
		return 0;
	}
	
	@Override
	public String toString() {
		return String.format("%d-%d-%d %02d:%02d:%02d:%03d", year.getValue(), month.getValue(), day.getValue(), hour.getValue(), minute.getValue(), second.getValue(), millisecond.getValue());
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj instanceof Times times && compareTo(times) == 0;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(year, month, day, hour, minute, second, millisecond);
	}
}
