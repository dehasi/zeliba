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

    public boolean isEqualsTo(T that) {
        return value.compareTo(that) == 0;
    }

    public boolean isGreaterThan(T that) {
        return value.compareTo(that) > 0;
    }

    public boolean isGreaterOrEqualsThan(T that) {
        return value.compareTo(that) >= 0;
    }

    public boolean isLessThan(T that) {
        return value.compareTo(that) < 0;
    }

    public boolean isLessOrEqualsThan(T that) {
        return value.compareTo(that) <= 0;
    }

    public IntervalBuilder isInTheInterval() {
        return new IntervalBuilder();
    }

    public class IntervalBuilder {

        public IntervalFrom fromIncluded(T fromIncluded) {
            return new IntervalFrom(fromIncluded.compareTo(value) <= 0);
        }

        public IntervalFrom fromExcluded(T fromExcluded) {
            return new IntervalFrom(fromExcluded.compareTo(value) < 0);
        }

        public class IntervalFrom {
            private final boolean isInFromInterval;

            public IntervalFrom(boolean interval) {
                isInFromInterval = interval;
            }

            public boolean toIncluded(T toIncluded) {
                return isInFromInterval && toIncluded.compareTo(value) >= 0;
            }

            public boolean toExcluded(T toExcluded) {
                return isInFromInterval && toExcluded.compareTo(value) > 0;
            }
        }
    }
}
