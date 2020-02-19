package zeliba;

import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static zeliba.TheChronoLocalDate.the;

class TheChronoLocalDateTest {

    @MethodSource("dates")
    @ParameterizedTest void isAfterOrEqual(LocalDate date) {
        assertTrue(the(date).isAfterOrEqual(date));
        assertTrue(the(date).isAfterOrEqual(date.minusDays(1)));
        assertFalse(the(date.minusDays(1)).isAfterOrEqual(date));
    }

    @MethodSource("dates")
    @ParameterizedTest void isBeforeOrEqual(LocalDate date) {
        assertTrue(the(date).isBeforeOrEqual(date));
        assertTrue(the(date.minusDays(1)).isBeforeOrEqual(date));
        assertFalse(the(date).isBeforeOrEqual(date.minusDays(1)));
    }

    private static Stream<Arguments> dates() {
        return Stream.generate(() -> Arguments.of(randomDate())).limit(40);
    }

    private static LocalDate randomDate() {
        long minDay = LocalDate.of(1970, 1, 1).toEpochDay();
        long maxDay = LocalDate.of(2038, 1, 19).toEpochDay();
        long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
        return LocalDate.ofEpochDay(randomDay);
    }
}
