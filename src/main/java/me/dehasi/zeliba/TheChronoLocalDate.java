package me.dehasi.zeliba;

import java.time.chrono.ChronoLocalDate;

public class TheChronoLocalDate<T extends ChronoLocalDate> {

    private final T value;

    private TheChronoLocalDate(T value) {
        this.value = value;
    }

    public static <T extends ChronoLocalDate> TheChronoLocalDate<T> a(T value) {
        return the(value);
    }

    public static <T extends ChronoLocalDate> TheChronoLocalDate<T> an(T value) {
        return the(value);
    }

    public static <T extends ChronoLocalDate> TheChronoLocalDate<T> the(T value) {
        return new TheChronoLocalDate<>(value);
    }

    public boolean isAfterOrEqual(T that){
       return value.isAfter(that) || value.isEqual(that);
    }

    public boolean isBeforeOrEqual(T that){
        return value.isBefore(that) || value.isEqual(that);
    }
}
