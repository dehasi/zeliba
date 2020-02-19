package zeliba;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static zeliba.TheChronoLocalDateTime.a;
import static zeliba.TheChronoLocalDateTime.an;
import static zeliba.TheChronoLocalDateTime.the;

class TheChronoLocalDateTimeTest {

    @MethodSource("dateTimes")
    @ParameterizedTest void isAfterOrEqual(LocalDateTime dateTime) {
        assertTrue(the(dateTime).isAfterOrEqual(dateTime));
        assertTrue(a(dateTime).isAfterOrEqual(dateTime.minusDays(1)));
        assertFalse(an(dateTime.minusDays(1)).isAfterOrEqual(dateTime));
    }

    @MethodSource("dateTimes")
    @ParameterizedTest void isBeforeOrEqual(LocalDateTime dateTime) {
        assertTrue(the(dateTime).isBeforeOrEqual(dateTime));
        assertTrue(a(dateTime.minusDays(1)).isBeforeOrEqual(dateTime));
        assertFalse(an(dateTime).isBeforeOrEqual(dateTime.minusDays(1)));
    }

    private static Stream<Arguments> dateTimes() {
        return Stream.generate(() -> Arguments.of(randomDateTime())).limit(40);
    }

    private static LocalDateTime randomDateTime() {
        long minDay = LocalDateTime.of(1970, 1, 1, 0, 0, 0).toEpochSecond(ZoneOffset.UTC);
        long maxDay = LocalDateTime.of(2038, 1, 19, 0, 0, 0).toEpochSecond(ZoneOffset.UTC);
        long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
        return LocalDateTime.ofEpochSecond(randomDay, 0, ZoneOffset.UTC);
    }
}
