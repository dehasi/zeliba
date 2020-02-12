package me.dehasi.zeliba;

import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static me.dehasi.zeliba.TheChronoLocalDate.the;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TheChronoLocalDateTest {

    @MethodSource("dates")
    @ParameterizedTest void isAfterOrEqual(LocalDate date) {
        assertTrue(the(date).isAfterOrEqual(date));
    }

    @MethodSource("dates")
    @ParameterizedTest void isBeforeOrEqual(LocalDate date) {
        assertTrue(the(date).isBeforeOrEqual(date));
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
