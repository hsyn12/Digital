package tr.xyz;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import tr.xyz.durations.Duration;
import tr.xyz.durations.Hour;
import tr.xyz.durations.Year;

import static tr.xyz.TestDigit.log;

public class TestDuration {

    @Test
    public void testCreation() {

        Duration duration = Duration.ofSeconds(-1);
        Assertions.assertEquals(-1, duration.getValue());

        duration = Duration.of(Hour.class, 22);
        Assertions.assertEquals(22, duration.getValue());
        Assertions.assertInstanceOf(Hour.class, duration);

        Duration duration2 = Duration.ofHours(22);
        Assertions.assertEquals(22, duration2.getValue());
        Assertions.assertEquals(duration, duration2);

        Duration duration3 = Duration.of(Year.class, 22);
        Assertions.assertEquals(22, duration3.getValue());
        Assertions.assertNotEquals(duration2, duration3);
        Assertions.assertInstanceOf(Year.class, duration3);

        log(duration);
        log(duration2);
        log(duration3);
    }

    @Test
    public void testPlus() {
        Duration duration  = Duration.of(Year.class, 22);
        Duration duration2 = duration.plus(3);
        Assertions.assertEquals(25, duration2.getValue());
        Assertions.assertInstanceOf(Year.class, duration);
        Assertions.assertInstanceOf(Year.class, duration2);
    }

    @Test
    public void testMinus() {
        Duration duration  = Duration.of(Year.class, 22);
        Duration duration2 = duration.minus(3);
        Assertions.assertEquals(19, duration2.getValue());
        Assertions.assertInstanceOf(Year.class, duration);
        Assertions.assertInstanceOf(Year.class, duration2);
    }

}
