package zeliba;

import java.time.chrono.ChronoLocalDate;

import static java.util.Objects.requireNonNull;

public class TheChronoLocalDate<DATE extends ChronoLocalDate> {

    private final DATE value;

    private TheChronoLocalDate(DATE value) {
        this.value = requireNonNull(value);
    }

    public static <T extends ChronoLocalDate> TheChronoLocalDate<T> the(T value) {
        return new TheChronoLocalDate<>(value);
    }

    public boolean isAfterOrEqual(DATE that) {
        return value.isAfter(that) || value.isEqual(that);
    }

    public boolean isNotAfter(DATE that) {
        return isBeforeOrEqual(that);
    }

    public boolean isBeforeOrEqual(DATE that) {
        return value.isBefore(that) || value.isEqual(that);
    }

    public boolean isNotBefore(DATE that) {
        return isAfterOrEqual(that);
    }
}
