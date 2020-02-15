package me.dehasi.zeliba;

import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoLocalDateTime;

public class TheChronoLocalDateTime<T extends ChronoLocalDateTime<? extends ChronoLocalDate>> {

    private final T value;

    private TheChronoLocalDateTime(T value) {
        this.value = value;
    }

    public static <T extends ChronoLocalDateTime<? extends ChronoLocalDate>> TheChronoLocalDateTime<T> a(T value) {
        return the(value);
    }

    public static <T extends ChronoLocalDateTime<? extends ChronoLocalDate>> TheChronoLocalDateTime<T> an(T value) {
        return the(value);
    }

    public static <T extends ChronoLocalDateTime<? extends ChronoLocalDate>> TheChronoLocalDateTime<T> the(T value) {
        return new TheChronoLocalDateTime<>(value);
    }

    public boolean isAfterOrEqual(T that){
       return value.isAfter(that) || value.isEqual(that);
    }

    public boolean isBeforeOrEqual(T that){
        return value.isBefore(that) || value.isEqual(that);
    }
}
