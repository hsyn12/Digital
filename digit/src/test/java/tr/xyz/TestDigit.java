package tr.xyz;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import tr.xyz.digit.Digit;

public class TestDigit {
	private static final int INC_DEC_LOOP_COUNT = 19;
	
	public static void log(Object msg, Object... args) {
		System.out.printf((msg != null ? msg.toString() : "null") + "%n", args);
	}
	
	public static void debugDigit(@NotNull Digit digit) {
		System.out.println("---------- Digit ---------");
		System.out.println("Value       : " + digit.getValue());
		System.out.println("Cycle Count : " + digit.getCycleCount());
		System.out.println("Range       : " + digit.getRange());
		System.out.println("Min         : " + digit.getMin());
		System.out.println("Max         : " + digit.getMax());
		System.out.println("--------------------------");
	}
	
	@Test
	public void testDigitCreation() {
		// range style creation
		var digit = Digit.range(1, 12);
		
		Assertions.assertEquals(1, digit.getValue());
		Assertions.assertEquals(0, digit.getCycleCount());
		Assertions.assertEquals(12, digit.getRange());
		Assertions.assertEquals(1, digit.getMin());
		Assertions.assertEquals(12, digit.getMax());
		
		// value style creation
		digit = Digit.of(9);
		
		Assertions.assertEquals(9, digit.getValue());
		Assertions.assertEquals(0, digit.getCycleCount());
		Assertions.assertEquals(digit.getMax() - digit.getMin() + 1, digit.getRange());
		Assertions.assertEquals(Digit.MIN, digit.getMin());
		Assertions.assertEquals(Digit.MAX, digit.getMax());
		
		// full value style creation
		digit = Digit.of(9, 1, 12);
		Assertions.assertEquals(9, digit.getValue());
		Assertions.assertEquals(0, digit.getCycleCount());
		Assertions.assertEquals(12, digit.getRange());
		Assertions.assertEquals(1, digit.getMin());
		Assertions.assertEquals(12, digit.getMax());
		
		digit = Digit.zero();
		
		Assertions.assertEquals(0, digit.getValue());
		Assertions.assertEquals(0, digit.getCycleCount());
		Assertions.assertEquals((Digit.MAX - Digit.MIN) + 1, digit.getRange());
		Assertions.assertEquals(Digit.MIN, digit.getMin());
		Assertions.assertEquals(Digit.MAX, digit.getMax());
		
		digit = Digit.of(-5, 5);
		Assertions.assertEquals(-5, digit.getValue());
		Assertions.assertEquals(0, digit.getCycleCount());
		Assertions.assertEquals(11, digit.getRange());
		Assertions.assertEquals(-5, digit.getMin());
		Assertions.assertEquals(5, digit.getMax());
		
		digit = Digit.of(0, 0);
		Assertions.assertEquals(0, digit.getValue());
		Assertions.assertEquals(0, digit.getCycleCount());
		Assertions.assertEquals(1, digit.getRange());
		Assertions.assertEquals(0, digit.getMin());
		Assertions.assertEquals(0, digit.getMax());
		
		digit = Digit.of(1, 1);
		
		Assertions.assertEquals(1, digit.getValue());
		Assertions.assertEquals(0, digit.getCycleCount());
		Assertions.assertEquals(1, digit.getRange());
		Assertions.assertEquals(1, digit.getMin());
		Assertions.assertEquals(1, digit.getMax());
		
		digit = Digit.of(Digit.MAX);
		
		Assertions.assertEquals(Digit.MAX, digit.getValue());
		Assertions.assertEquals(0, digit.getCycleCount());
		Assertions.assertEquals((Digit.MAX - Digit.MIN) + 1, digit.getRange());
		Assertions.assertEquals(Digit.MIN, digit.getMin());
		Assertions.assertEquals(Digit.MAX, digit.getMax());
		
		digit = Digit.of(0, 1, 12);
		
		Assertions.assertEquals(12, digit.getValue());
		Assertions.assertEquals(-1, digit.getCycleCount());
		Assertions.assertEquals(12, digit.getRange());
		Assertions.assertEquals(1, digit.getMin());
		Assertions.assertEquals(12, digit.getMax());
		
		digit = Digit.of(Long.MAX_VALUE, 1, 12);
//        debugDigit(digit);
		
		digit = Digit.of(Long.MIN_VALUE, 1, 12);
//        debugDigit(digit);
		
		// Minimum value is greater than maximum value. This is wrong.
		Assertions.assertThrows(IllegalArgumentException.class, () -> Digit.of(19, 12));
		
	}
	
	@Test
	public void testIncrement() {
		var  digit   = Digit.range(1, 12);
		long counter = digit.getValue();
		long cycle   = 0;
		int  loop    = 1;
		
		do {
//            debugDigit(digit);
			Assertions.assertEquals(counter, digit.getValue());
			Assertions.assertEquals(cycle, digit.getCycleCount());
			Assertions.assertEquals(12, digit.getRange());
			Assertions.assertEquals(1, digit.getMin());
			Assertions.assertEquals(12, digit.getMax());
			digit = digit.increment();
			counter++;
			if (counter > digit.getMax()) {
				counter = 1;
				cycle   = 1;
			}
			else cycle = 0;
			
			loop++;
		} while (loop < INC_DEC_LOOP_COUNT);
		
	}
	
	@Test
	public void testDecrement() {
		var  digit   = Digit.range(1, 12);
		long counter = digit.getValue();
		long cycle   = 0;
		int  loop    = 1;
		
		do {
			Assertions.assertEquals(counter, digit.getValue());
			Assertions.assertEquals(cycle, digit.getCycleCount());
			Assertions.assertEquals(12, digit.getRange());
			Assertions.assertEquals(1, digit.getMin());
			Assertions.assertEquals(12, digit.getMax());
			digit = digit.decrement();
			counter--;
			if (counter < digit.getMin()) {
				counter = digit.getMax();
				cycle   = -1;
			}
			else cycle = 0;
			
			loop++;
		} while (loop < INC_DEC_LOOP_COUNT);
	}
	
	@Test
	public void testPlus() {
		var digit = Digit.of(1, 12);
		digit = digit.plus(2);
		
		Assertions.assertEquals(3, digit.getValue());
		Assertions.assertEquals(0, digit.getCycleCount());
		Assertions.assertEquals(12, digit.getRange());
		Assertions.assertEquals(1, digit.getMin());
		Assertions.assertEquals(12, digit.getMax());
		
		digit = digit.plus(10);
		Assertions.assertEquals(1, digit.getValue());
		Assertions.assertEquals(1, digit.getCycleCount());
		Assertions.assertEquals(12, digit.getRange());
		Assertions.assertEquals(1, digit.getMin());
		Assertions.assertEquals(12, digit.getMax());
		
		digit = Digit.zero().plus(1);
		Assertions.assertEquals(1, digit.getValue());
		Assertions.assertEquals(0, digit.getCycleCount());
		Assertions.assertEquals(Digit.MAX, digit.getMax());
		Assertions.assertEquals(Digit.MIN, digit.getMin());
		Assertions.assertEquals((Digit.MAX - Digit.MIN) + 1, digit.getRange());
		
		digit = digit.plus(-1);
		Assertions.assertEquals(0, digit.getValue());
		Assertions.assertEquals(0, digit.getCycleCount());
		Assertions.assertEquals(Digit.MAX, digit.getMax());
		Assertions.assertEquals(Digit.MIN, digit.getMin());
		Assertions.assertEquals((Digit.MAX - Digit.MIN) + 1, digit.getRange());
		
		digit = digit.plus(-1);
		Assertions.assertEquals(-1, digit.getValue());
		Assertions.assertEquals(0, digit.getCycleCount());
		Assertions.assertEquals(Digit.MAX, digit.getMax());
		Assertions.assertEquals(Digit.MIN, digit.getMin());
		Assertions.assertEquals((Digit.MAX - Digit.MIN) + 1, digit.getRange());
		
		digit = digit.plus(-2);
		Assertions.assertEquals(-3, digit.getValue());
		Assertions.assertEquals(0, digit.getCycleCount());
		Assertions.assertEquals(Digit.MAX, digit.getMax());
		Assertions.assertEquals(Digit.MIN, digit.getMin());
		Assertions.assertEquals((Digit.MAX - Digit.MIN) + 1, digit.getRange());
		
		digit = digit.plus(5);
		Assertions.assertEquals(2, digit.getValue());
		Assertions.assertEquals(0, digit.getCycleCount());
		Assertions.assertEquals(Digit.MAX, digit.getMax());
		Assertions.assertEquals(Digit.MIN, digit.getMin());
		Assertions.assertEquals((Digit.MAX - Digit.MIN) + 1, digit.getRange());
		
		digit = digit.plus(-5);
		Assertions.assertEquals(-3, digit.getValue());
		Assertions.assertEquals(0, digit.getCycleCount());
		Assertions.assertEquals(Digit.MAX, digit.getMax());
		Assertions.assertEquals(Digit.MIN, digit.getMin());
		Assertions.assertEquals((Digit.MAX - Digit.MIN) + 1, digit.getRange());
		
		
		digit = Digit.of(5, 1, 12);
		digit = digit.plus(5);
		Assertions.assertEquals(10, digit.getValue());
		Assertions.assertEquals(0, digit.getCycleCount());
		Assertions.assertEquals(12, digit.getRange());
		Assertions.assertEquals(1, digit.getMin());
		Assertions.assertEquals(12, digit.getMax());
		
		digit = digit.plus(-10);
		Assertions.assertEquals(12, digit.getValue());
		Assertions.assertEquals(-1, digit.getCycleCount());
		Assertions.assertEquals(12, digit.getRange());
		Assertions.assertEquals(1, digit.getMin());
		Assertions.assertEquals(12, digit.getMax());
		
		digit = digit.plus(1);
		Assertions.assertEquals(1, digit.getValue());
		Assertions.assertEquals(1, digit.getCycleCount());
		Assertions.assertEquals(12, digit.getRange());
		Assertions.assertEquals(1, digit.getMin());
		Assertions.assertEquals(12, digit.getMax());
		
		digit = digit.plus(-2);
		Assertions.assertEquals(11, digit.getValue());
		Assertions.assertEquals(-1, digit.getCycleCount());
		Assertions.assertEquals(12, digit.getRange());
		Assertions.assertEquals(1, digit.getMin());
		Assertions.assertEquals(12, digit.getMax());
		
		digit = Digit.of(Digit.MAX);
		digit = digit.plus(1);
		Assertions.assertEquals(Digit.MIN, digit.getValue());
		Assertions.assertEquals(1, digit.getCycleCount());
		Assertions.assertEquals(Digit.MAX, digit.getMax());
		Assertions.assertEquals(Digit.MIN, digit.getMin());
		Assertions.assertEquals((Digit.MAX - Digit.MIN) + 1, digit.getRange());
		
		digit = Digit.of(Digit.MIN);
		digit = digit.plus(-1);
		Assertions.assertEquals(Digit.MAX, digit.getValue());
		Assertions.assertEquals(-1, digit.getCycleCount());
		Assertions.assertEquals(Digit.MAX, digit.getMax());
		Assertions.assertEquals(Digit.MIN, digit.getMin());
		Assertions.assertEquals((Digit.MAX - Digit.MIN) + 1, digit.getRange());
	}
	
	@Test
	public void testMinus() {
		
		var digit = Digit.of(1, 12);
		digit = digit.minus(2);
		Assertions.assertEquals(11, digit.getValue());
		Assertions.assertEquals(-1, digit.getCycleCount());
		Assertions.assertEquals(12, digit.getRange());
		Assertions.assertEquals(1, digit.getMin());
		Assertions.assertEquals(12, digit.getMax());
		
		digit = digit.minus(11);
		Assertions.assertEquals(12, digit.getValue());
		Assertions.assertEquals(-1, digit.getCycleCount());
		Assertions.assertEquals(12, digit.getRange());
		Assertions.assertEquals(1, digit.getMin());
		Assertions.assertEquals(12, digit.getMax());
		
		digit = digit.minus(12);
		Assertions.assertEquals(12, digit.getValue());
		Assertions.assertEquals(-1, digit.getCycleCount());
		Assertions.assertEquals(12, digit.getRange());
		Assertions.assertEquals(1, digit.getMin());
		Assertions.assertEquals(12, digit.getMax());
		
		digit = digit.minus(12);
		Assertions.assertEquals(12, digit.getValue());
		Assertions.assertEquals(-1, digit.getCycleCount());
		Assertions.assertEquals(12, digit.getRange());
		Assertions.assertEquals(1, digit.getMin());
		Assertions.assertEquals(12, digit.getMax());
		
		digit = digit.minus(12);
		Assertions.assertEquals(12, digit.getValue());
		Assertions.assertEquals(-1, digit.getCycleCount());
		Assertions.assertEquals(12, digit.getRange());
		Assertions.assertEquals(1, digit.getMin());
		Assertions.assertEquals(12, digit.getMax());
		
		digit = digit.minus(12);
		Assertions.assertEquals(12, digit.getValue());
		Assertions.assertEquals(-1, digit.getCycleCount());
		Assertions.assertEquals(12, digit.getRange());
		Assertions.assertEquals(1, digit.getMin());
		Assertions.assertEquals(12, digit.getMax());
		
		digit = digit.minus(12);
		Assertions.assertEquals(12, digit.getValue());
		Assertions.assertEquals(-1, digit.getCycleCount());
		Assertions.assertEquals(12, digit.getRange());
		Assertions.assertEquals(1, digit.getMin());
		Assertions.assertEquals(12, digit.getMax());
		
		digit = Digit.of(2, 4);
		
		digit = digit.minus(3);
		Assertions.assertEquals(2, digit.getValue());
		Assertions.assertEquals(-1, digit.getCycleCount());
		Assertions.assertEquals(3, digit.getRange());
		Assertions.assertEquals(2, digit.getMin());
		Assertions.assertEquals(4, digit.getMax());
		
	}
	
	@Test
	public void testRange() {
		var digit = Digit.range(1, 12);
		Assertions.assertFalse(digit.isInRange(-2));
		Assertions.assertFalse(digit.isInRange(-1));
		Assertions.assertFalse(digit.isInRange(0));
		Assertions.assertTrue(digit.isInRange(1));
		Assertions.assertTrue(digit.isInRange(2));
		Assertions.assertTrue(digit.isInRange(3));
		Assertions.assertTrue(digit.isInRange(4));
		Assertions.assertTrue(digit.isInRange(5));
		Assertions.assertTrue(digit.isInRange(6));
		Assertions.assertTrue(digit.isInRange(7));
		Assertions.assertTrue(digit.isInRange(8));
		Assertions.assertTrue(digit.isInRange(9));
		Assertions.assertTrue(digit.isInRange(10));
		Assertions.assertTrue(digit.isInRange(11));
		Assertions.assertTrue(digit.isInRange(12));
		Assertions.assertFalse(digit.isInRange(13));
		Assertions.assertFalse(digit.isInRange(14));
		
	}
	
	@Test
	public void testDigit() {
		var digit = Digit.of(2, 4);
		
		digit = digit.decrement();
		Assertions.assertEquals(4, digit.getValue());
		Assertions.assertEquals(-1, digit.getCycleCount());
		
		digit = digit.increment();
		Assertions.assertEquals(2, digit.getValue());
		Assertions.assertEquals(1, digit.getCycleCount());
		
		digit = digit.increment();
		Assertions.assertEquals(3, digit.getValue());
		Assertions.assertEquals(0, digit.getCycleCount());
		
		digit = digit.increment();
		Assertions.assertEquals(4, digit.getValue());
		Assertions.assertEquals(0, digit.getCycleCount());
		
		digit = digit.plus(2);
		Assertions.assertEquals(3, digit.getValue());
		Assertions.assertEquals(1, digit.getCycleCount());
		
		digit = digit.plus(2);
		Assertions.assertEquals(2, digit.getValue());
		Assertions.assertEquals(1, digit.getCycleCount());
		
		digit = digit.plus(2);
		Assertions.assertEquals(4, digit.getValue());
		Assertions.assertEquals(0, digit.getCycleCount());
		
		digit = digit.increment();
		Assertions.assertEquals(2, digit.getValue());
		Assertions.assertEquals(1, digit.getCycleCount());
		
		digit = digit.plus(2 * 3);
		Assertions.assertEquals(2, digit.getValue());
		Assertions.assertEquals(2, digit.getCycleCount());
		
		digit = digit.plus(2 * 3);
		Assertions.assertEquals(2, digit.getValue());
		Assertions.assertEquals(2, digit.getCycleCount());
		
		digit = digit.plus(3);
		Assertions.assertEquals(2, digit.getValue());
		Assertions.assertEquals(1, digit.getCycleCount());
		
		digit = digit.plus(3 * 9);
		Assertions.assertEquals(2, digit.getValue());
		Assertions.assertEquals(9, digit.getCycleCount());
		
		digit = digit.minus(3);
		Assertions.assertEquals(2, digit.getValue());
		Assertions.assertEquals(-1, digit.getCycleCount());
		
		digit = digit.minus(3);
		Assertions.assertEquals(2, digit.getValue());
		Assertions.assertEquals(-1, digit.getCycleCount());
		
		digit = digit.minus(3 * 2);
		Assertions.assertEquals(2, digit.getValue());
		Assertions.assertEquals(-2, digit.getCycleCount());
		
		digit = digit.minus(3 * 3);
		Assertions.assertEquals(2, digit.getValue());
		Assertions.assertEquals(-3, digit.getCycleCount());
		
		digit = digit.minus(3 * 9);
		Assertions.assertEquals(2, digit.getValue());
		Assertions.assertEquals(-9, digit.getCycleCount());
		
		digit = digit.plus(2);
		Assertions.assertEquals(4, digit.getValue());
		Assertions.assertEquals(0, digit.getCycleCount());
		
		digit = digit.increment();
		Assertions.assertEquals(2, digit.getValue());
		Assertions.assertEquals(1, digit.getCycleCount());
		
		digit = digit.increment();
		Assertions.assertEquals(3, digit.getValue());
		Assertions.assertEquals(0, digit.getCycleCount());
		
		digit = digit.increment();
		Assertions.assertEquals(4, digit.getValue());
		Assertions.assertEquals(0, digit.getCycleCount());
		
		digit = digit.increment();
		Assertions.assertEquals(2, digit.getValue());
		Assertions.assertEquals(1, digit.getCycleCount());
		
		digit = digit.plus(3);
		Assertions.assertEquals(2, digit.getValue());
		Assertions.assertEquals(1, digit.getCycleCount());
		
		digit = Digit.of(0, 2);
		Assertions.assertEquals(0, digit.getValue());
		
		debugDigit(digit);
		digit = digit.minus(digit.getRange());
		Assertions.assertEquals(0, digit.getValue());
		Assertions.assertEquals(-1, digit.getCycleCount());
		
		digit = digit.minus(digit.getRange() * 100);
		Assertions.assertEquals(0, digit.getValue());
		Assertions.assertEquals(-100, digit.getCycleCount());
	}
	
	
}
