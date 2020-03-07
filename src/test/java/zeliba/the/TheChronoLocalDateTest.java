package zeliba.the;

import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static zeliba.the.TheChronoLocalDate.the;

class TheChronoLocalDateTest {

    @MethodSource("dates")
    @ParameterizedTest void isAfterOrEqual(LocalDate date) {
        assertTrue(the(date).isAfterOrEqual(date));
        assertTrue(the(date).isAfterOrEqual(date.minusDays(1)));
        assertFalse(the(date.minusDays(1)).isAfterOrEqual(date));
    }

    @MethodSource("dates")
    @ParameterizedTest void isNotAfter_theSameAs_isBeforeOrEqual(LocalDate date) {
        assertEquals(the(date).isBeforeOrEqual(date), the(date).isNotAfter(date));
        assertEquals(the(date).isBeforeOrEqual(date.minusDays(1)), the(date).isNotAfter(date.minusDays(1)));
        assertEquals(the(date.minusDays(1)).isBeforeOrEqual(date), the(date.minusDays(1)).isNotAfter(date));
    }

    @MethodSource("dates")
    @ParameterizedTest void isBeforeOrEqual(LocalDate date) {
        assertTrue(the(date).isBeforeOrEqual(date));
        assertTrue(the(date.minusDays(1)).isBeforeOrEqual(date));
        assertFalse(the(date).isBeforeOrEqual(date.minusDays(1)));
    }

    @MethodSource("dates")
    @ParameterizedTest void isNotBefore_theSameAs_isAfterOrEqual(LocalDate date) {
        assertEquals(the(date).isAfterOrEqual(date), the(date).isNotBefore(date));
        assertEquals(the(date).isAfterOrEqual(date.minusDays(1)), the(date).isNotBefore(date.minusDays(1)));
        assertEquals(the(date.minusDays(1)).isAfterOrEqual(date.minusDays(1)), the(date).isNotBefore(date));
    }

    private static Stream<Arguments> dates() {
        return Stream.generate(() -> Arguments.of(randomDate())).limit(10);
    }

    private static LocalDate randomDate() {
        long minDay = LocalDate.of(1970, 1, 1).toEpochDay();
        long maxDay = LocalDate.of(2038, 1, 19).toEpochDay();
        long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
        return LocalDate.ofEpochDay(randomDay);
    }
}
