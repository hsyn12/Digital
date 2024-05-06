package tr.xyz;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import tr.xyz.times.Millisecond;
import tr.xyz.times.TimeDigit;
import tr.xyz.times.Year;

public class TestTimeDigit {
	@Test
	public void testCreation() {
		
		// As like Digit, cycled.
		TimeDigit digit = TimeDigit.second(-1);
		
		Assertions.assertEquals(59, digit.getValue());
		Assertions.assertEquals(-1, digit.getCycleCount());
		Assertions.assertEquals(60, digit.getRange());
		Assertions.assertEquals(0, digit.getMin());
		Assertions.assertEquals(59, digit.getMax());
		
		digit = TimeDigit.minute(-1);
		
		Assertions.assertEquals(59, digit.getValue());
		Assertions.assertEquals(-1, digit.getCycleCount());
		Assertions.assertEquals(60, digit.getRange());
		Assertions.assertEquals(0, digit.getMin());
		Assertions.assertEquals(59, digit.getMax());
		
		digit = TimeDigit.of(Year.class, 1981);
		
		Assertions.assertEquals(1981, digit.getValue());
		Assertions.assertInstanceOf(Year.class, digit);
		Assertions.assertEquals(0, digit.getCycleCount());
		Assertions.assertEquals(Year.MAX - Year.MIN + 1, digit.getRange());
		Assertions.assertEquals(Year.MIN, digit.getMin());
		Assertions.assertEquals(Year.MAX, digit.getMax());
		
		Year year = TimeDigit.of(Year.class, 1981);
		Assertions.assertInstanceOf(Year.class, digit);
		Assertions.assertEquals(digit, year);
		
	}
	
	@Test
	public void testPlus() {
		TimeDigit digit = TimeDigit.of(Millisecond.class, 12);
		Assertions.assertEquals(12, digit.getValue());
		Assertions.assertEquals(0, digit.getCycleCount());
		Assertions.assertEquals(1000, digit.getRange());
		Assertions.assertEquals(0, digit.getMin());
		Assertions.assertEquals(999, digit.getMax());
		
		digit.increment();
		Assertions.assertEquals(13, digit.getValue());
		Assertions.assertEquals(0, digit.getCycleCount());
		
		digit.plus(900);
		Assertions.assertEquals(913, digit.getValue());
		Assertions.assertEquals(0, digit.getCycleCount());
		
		digit.plus(87);
		Assertions.assertEquals(0, digit.getValue());
		Assertions.assertEquals(1, digit.getCycleCount());
		
		digit.plus(1000);
		Assertions.assertEquals(0, digit.getValue());
		Assertions.assertEquals(1, digit.getCycleCount());
		
		digit.plus(1000 * 101);
		Assertions.assertEquals(0, digit.getValue());
		Assertions.assertEquals(101, digit.getCycleCount());
		
		
	}
	
	@Test
	public void testMinus() {
		TimeDigit digit = TimeDigit.of(Millisecond.class, 12);
		Assertions.assertEquals(12, digit.getValue());
		Assertions.assertEquals(0, digit.getCycleCount());
		Assertions.assertEquals(1000, digit.getRange());
		Assertions.assertEquals(0, digit.getMin());
		Assertions.assertEquals(999, digit.getMax());
		
		digit.decrement();
		Assertions.assertEquals(11, digit.getValue());
		Assertions.assertEquals(0, digit.getCycleCount());
		
		digit.minus(11);
		Assertions.assertEquals(0, digit.getValue());
		Assertions.assertEquals(0, digit.getCycleCount());
		
		digit.minus(1);
		Assertions.assertEquals(999, digit.getValue());
		Assertions.assertEquals(-1, digit.getCycleCount());
		
		digit.minus(1000);
		Assertions.assertEquals(999, digit.getValue());
		Assertions.assertEquals(-1, digit.getCycleCount());
		
		digit.minus(1000);
		Assertions.assertEquals(999, digit.getValue());
		Assertions.assertEquals(-1, digit.getCycleCount());
		
		digit.minus(1000);
		Assertions.assertEquals(999, digit.getValue());
		Assertions.assertEquals(-1, digit.getCycleCount());
		
		digit.increment();
		Assertions.assertEquals(0, digit.getValue());
		Assertions.assertEquals(1, digit.getCycleCount());
		
		digit.minus(1000);
		Assertions.assertEquals(0, digit.getValue());
		Assertions.assertEquals(-1, digit.getCycleCount());
		
		digit.minus(1000 * 9);
		Assertions.assertEquals(0, digit.getValue());
		Assertions.assertEquals(-9, digit.getCycleCount());
		
		
	}
}
