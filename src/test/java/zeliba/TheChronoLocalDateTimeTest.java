package zeliba;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static zeliba.TheChronoLocalDateTime.the;

class TheChronoLocalDateTimeTest {

    @MethodSource("dateTimes")
    @ParameterizedTest void isAfterOrEqual(LocalDateTime dateTime) {
        assertTrue(the(dateTime).isAfterOrEqual(dateTime));
        assertTrue(the(dateTime).isAfterOrEqual(dateTime.minusDays(1)));
        assertFalse(the(dateTime.minusDays(1)).isAfterOrEqual(dateTime));
    }

    @MethodSource("dateTimes")
    @ParameterizedTest void isNotAfter_theSameAs_isBeforeOrEqual(LocalDateTime dateTime) {
        assertEquals(the(dateTime).isBeforeOrEqual(dateTime), the(dateTime).isNotAfter(dateTime));
        assertEquals(the(dateTime).isBeforeOrEqual(dateTime.minusDays(1)), the(dateTime).isNotAfter(dateTime.minusDays(1)));
        assertEquals(the(dateTime.minusDays(1)).isBeforeOrEqual(dateTime), the(dateTime.minusDays(1)).isNotAfter(dateTime));
    }

    @MethodSource("dateTimes")
    @ParameterizedTest void isBeforeOrEqual(LocalDateTime dateTime) {
        assertTrue(the(dateTime).isBeforeOrEqual(dateTime));
        assertTrue(the(dateTime.minusDays(1)).isBeforeOrEqual(dateTime));
        assertFalse(the(dateTime).isBeforeOrEqual(dateTime.minusDays(1)));
    }

    @MethodSource("dateTimes")
    @ParameterizedTest void isNotBefore_theSameAs_isAfterOrEqual(LocalDateTime dateTime) {
        assertEquals(the(dateTime).isAfterOrEqual(dateTime), the(dateTime).isNotBefore(dateTime));
        assertEquals(the(dateTime).isAfterOrEqual(dateTime.minusDays(1)), the(dateTime).isNotBefore(dateTime.minusDays(1)));
        assertEquals(the(dateTime.minusDays(1)).isAfterOrEqual(dateTime), the(dateTime.minusDays(1)).isNotBefore(dateTime));
    }

    private static Stream<Arguments> dateTimes() {
        return Stream.generate(() -> Arguments.of(randomDateTime())).limit(10);
    }

    private static LocalDateTime randomDateTime() {
        long minDay = LocalDateTime.of(1970, 1, 1, 0, 0, 0).toEpochSecond(ZoneOffset.UTC);
        long maxDay = LocalDateTime.of(2038, 1, 19, 0, 0, 0).toEpochSecond(ZoneOffset.UTC);
        long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
        return LocalDateTime.ofEpochSecond(randomDay, 0, ZoneOffset.UTC);
    }
}
