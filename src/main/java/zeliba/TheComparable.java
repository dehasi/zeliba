package zeliba;

import java.util.function.Predicate;

public class TheComparable<T extends Comparable<T>> {

    private final T value;

    private TheComparable(T value) {
        this.value = value;
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

        private IntervalBuilder() {}

        public IntervalChecker fromIncluded(T fromIncluded) {
            return new IntervalChecker(value -> fromIncluded.compareTo(value) <= 0);
        }

        public IntervalChecker fromExcluded(T fromExcluded) {
            return new IntervalChecker(value -> fromExcluded.compareTo(value) < 0);
        }

        public class IntervalChecker {

            private final Predicate<T> from;

            private IntervalChecker(Predicate<T> from) {
                this.from = from;
            }

            public boolean toIncluded(T toIncluded) {
                return from.test(value) && toIncluded.compareTo(value) >= 0;
            }

            public boolean toExcluded(T toExcluded) {
                return from.test(value) && toExcluded.compareTo(value) > 0;
            }
        }
    }
}
