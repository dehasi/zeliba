package me.dehasi.zeliba;

public class TheComparable<T extends Comparable<T>> {

    private final T value;

    private TheComparable(T value) {
        this.value = value;
    }

    public static <T extends Comparable<T>> TheComparable<T> the(T value) {
        return new TheComparable<>(value);
    }

    public boolean isGreater(T that) {
        return value.compareTo(that) > 0;
    }
}
