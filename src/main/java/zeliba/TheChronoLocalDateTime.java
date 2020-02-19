package zeliba;

import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoLocalDateTime;

import static java.util.Objects.requireNonNull;

public class TheChronoLocalDateTime<T extends ChronoLocalDateTime<? extends ChronoLocalDate>> {

    private final T value;

    private TheChronoLocalDateTime(T value) {
        this.value = requireNonNull(value);
    }

    public static <T extends ChronoLocalDateTime<? extends ChronoLocalDate>> TheChronoLocalDateTime<T> the(T value) {
        return new TheChronoLocalDateTime<>(value);
    }

    public boolean isAfterOrEqual(T that) {
        return value.isAfter(that) || value.isEqual(that);
    }

    public boolean isBeforeOrEqual(T that) {
        return value.isBefore(that) || value.isEqual(that);
    }
}
