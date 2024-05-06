package tr.xyz;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import tr.xyz.durations.Duration;
import tr.xyz.times.TimeDigit;
import tr.xyz.times.Times;

import java.time.LocalDateTime;
import java.time.Period;

import static tr.xyz.TestDigit.log;

public class TestTimeDigits {
	@Test
	public void testCreation() {
		var nowLocalDateTime = LocalDateTime.now();
		var nowTime          = Times.of(nowLocalDateTime); // Create from LocalDateTime
		var nowTime2         = Times.now(); // Create from now
		
		// All must be equal
		Assertions.assertEquals(nowLocalDateTime.getYear(), nowTime.getYear().getValue());
		Assertions.assertEquals(nowLocalDateTime.getMonthValue(), nowTime.getMonth().getValue());
		Assertions.assertEquals(nowLocalDateTime.getDayOfMonth(), nowTime.getDay().getValue());
		Assertions.assertEquals(nowLocalDateTime.getHour(), nowTime.getHour().getValue());
		Assertions.assertEquals(nowLocalDateTime.getMinute(), nowTime.getMinute().getValue());
		Assertions.assertEquals(nowLocalDateTime.getSecond(), nowTime.getSecond().getValue());
		Assertions.assertEquals(nowLocalDateTime.getNano() / 1_000_000, nowTime.getMillisecond().getValue());
		
		// All must be equal except Nanoseconds
		Assertions.assertEquals(nowLocalDateTime.getYear(), nowTime2.getYear().getValue());
		Assertions.assertEquals(nowLocalDateTime.getMonthValue(), nowTime2.getMonth().getValue());
		Assertions.assertEquals(nowLocalDateTime.getDayOfMonth(), nowTime2.getDay().getValue());
		Assertions.assertEquals(nowLocalDateTime.getHour(), nowTime2.getHour().getValue());
		Assertions.assertEquals(nowLocalDateTime.getMinute(), nowTime2.getMinute().getValue());
		Assertions.assertEquals(nowLocalDateTime.getSecond(), nowTime2.getSecond().getValue());
		// Nanoseconds are not equal because of creation order.
		Assertions.assertNotEquals(nowLocalDateTime.getNano() / 1_000_000, nowTime2.getMillisecond().getValue());
		
		// Create from another time
		var clone = nowTime.copy();
		Assertions.assertEquals(nowLocalDateTime.getYear(), clone.getYear().getValue());
		Assertions.assertEquals(nowLocalDateTime.getMonthValue(), clone.getMonth().getValue());
		Assertions.assertEquals(nowLocalDateTime.getDayOfMonth(), clone.getDay().getValue());
		Assertions.assertEquals(nowLocalDateTime.getHour(), clone.getHour().getValue());
		Assertions.assertEquals(nowLocalDateTime.getMinute(), clone.getMinute().getValue());
		Assertions.assertEquals(nowLocalDateTime.getSecond(), clone.getSecond().getValue());
		Assertions.assertEquals(nowLocalDateTime.getNano() / 1_000_000, clone.getMillisecond().getValue());
		
		// The same times must always be equal.
		// Check equality on a clone.
		Assertions.assertEquals(0, nowTime.compareTo(clone));
		Assertions.assertEquals(clone, nowTime);
		Assertions.assertEquals(nowTime, clone);
		
		// Check equality on different times.
		// These have different creation time by nanoseconds because of the creation order. The nowTime2 created later than nowTime.
		Assertions.assertNotEquals(nowTime, nowTime2);
		Assertions.assertNotEquals(nowTime.getMillisecond(), nowTime2.getMillisecond());
		Assertions.assertEquals(-1, nowTime.compareTo(nowTime2));
		Assertions.assertEquals(1, nowTime2.compareTo(nowTime));
		Assertions.assertNotEquals(0, nowTime.compareTo(nowTime2));
	}
	
	@Test
	public void testBetween() {
		var now        = Times.of(2024, 5, 6, 14, 50);
		var myBirthDay = Times.of(1981, 12, 4, 23, 45);
		var between    = now.between(myBirthDay);
		var test       = myBirthDay.plus(between);
		Assertions.assertEquals(now, test); // ✪
		
		
	}
	
	@Test
	public void testPlus() {
		var onesUponATime = Times.of(1881);
		// This operation does not change the value of onesUponATime, returns a new instance.
		var oneYearsLater = onesUponATime.plus(Times.of(1));
		Assertions.assertEquals(1882, oneYearsLater.getYear().getValue());
		Assertions.assertEquals(1881, onesUponATime.getYear().getValue());
		
		// This operation changes the value of onesUponATime.
		onesUponATime.getYear().increment();
		Assertions.assertEquals(1882, onesUponATime.getYear().getValue());
		
	}
	
}
