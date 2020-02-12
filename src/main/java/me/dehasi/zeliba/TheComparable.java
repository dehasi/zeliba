package me.dehasi.zeliba;

public class TheComparable<T extends Comparable<T>> {

    private final T value;

    private TheComparable(T value) {
        this.value = value;
    }

    public static <T extends Comparable<T>> TheComparable<T> a(T value) {
        return the(value);
    }

    public static <T extends Comparable<T>> TheComparable<T> an(T value) {
        return the(value);
    }

    public static <T extends Comparable<T>> TheComparable<T> the(T value) {
        return new TheComparable<>(value);
    }

    public boolean isGreaterThan(T that) {
        return value.compareTo(that) > 0;
    }

    public boolean isLessThan(T that) {
        return value.compareTo(that) < 0;
    }
}
