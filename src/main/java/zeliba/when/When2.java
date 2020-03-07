package zeliba.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static java.util.Objects.requireNonNull;

public class When2<V1, V2> {

    private final V1 value1;
    private final V2 value2;

    private When2(V1 value1, V2 value2) {
        this.value1 = requireNonNull(value1);
        this.value2 = requireNonNull(value2);
    }

    public static <V1, V2> When2<V1, V2> when(V1 v1, V2 v2) {
        return new When2<>(v1, v2);
    }

    public RawIs is(V1 v1, V2 v2) {
        BiPredicate<V1, V2> equals = (x1, x2) -> Objects.equals(x1, v1) && Objects.equals(x2, v2);
        return is(equals);
    }

    public RawIs isNot(V1 v1, V2 v2) {
        BiPredicate<V1, V2> notEquals = (x1, x2) -> !Objects.equals(x1, v1) && !Objects.equals(x2, v2);
        return is(notEquals);
    }

    public RawIs is(Predicate<? super V1> p1, Predicate<? super V2> p2) {
        return new RawIs((x1, x2) -> p1.test(x1) && p2.test(x2));
    }

    public RawIs is(BiPredicate<? super V1, ? super V2> predicate) {
        return new RawIs(requireNonNull(predicate));
    }

    public class RawIs {

        private BiPredicate<? super V1, ? super V2> predicate;

        private RawIs(BiPredicate<? super V1, ? super V2> predicate) {
            this.predicate = predicate;
        }

        public <RESULT> Then<RESULT> then(RESULT result) {
            return then(() -> result);
        }

        public <RESULT> Then<RESULT> then(Supplier<? extends RESULT> result) {
            return then((x, y) -> result.get());
        }

        public <RESULT> Then<RESULT> then(BiFunction<? super V1, ? super V2, ? extends RESULT> result) {
            return new Then<>(new Pair<>(predicate, result));
        }
    }

    public class Then<RESULT> {

        private Is<RESULT> is = new Is<>();
        private final List<Pair<RESULT>> pairs = new ArrayList<>();

        private Then(Pair<RESULT> result) {
            pairs.add(result);
        }

        private Then<RESULT> with(Pair<RESULT> pair) {
            pairs.add(pair);
            return this;
        }

        public Is<RESULT> is(V1 v1, V2 v2) {
            BiPredicate<V1, V2> equals = (x1, x2) -> Objects.equals(x1, v1) && Objects.equals(x2, v2);
            return is(equals);
        }

        public Is<RESULT> isNot(V1 v1, V2 v2) {
            BiPredicate<V1, V2> notEquals = (x1, x2) -> !Objects.equals(x1, v1) && !Objects.equals(x2, v2);
            return is(notEquals);
        }

        public Is<RESULT> is(Predicate<? super V1> p1, Predicate<? super V2> p2) {
            return is((x1, x2) -> p1.test(x1) && p2.test(x2));
        }

        public Is<RESULT> is(BiPredicate<? super V1, ? super V2> predicate) {
            return is.with(this, predicate);
        }

        public RESULT orElse(RESULT other) {
            return orElse(() -> other);
        }

        public RESULT orElse(Supplier<? extends RESULT> other) {
            requireNonNull(other);
            return orElse((x, y) -> other.get());
        }

        public RESULT orElse(BiFunction<? super V1, ? super V2, ? extends RESULT> other) {
            requireNonNull(other);

            return asOptional().orElse(other.apply(value1, value2));
        }

        public RESULT orElseThrow() {
            return orElseThrow(String.format("No matches for values [%s] [%s]", value1, value2));
        }

        public RESULT orElseThrow(String message) {
            return orElseThrow(() -> new IllegalStateException(message));
        }

        public <EXCEPTION extends Throwable> RESULT orElseThrow(
            Supplier<? extends EXCEPTION> exceptionSupplier) throws EXCEPTION {

            return asOptional().orElseThrow(exceptionSupplier);
        }

        public Optional<RESULT> asOptional() {
            return pairs.stream()
                .filter(pair1 -> pair1.predicate.test(value1, value2))
                .<RESULT>map(pair1 -> pair1.result.apply(value1, value2))
                .findFirst();
        }
    }

    public class Is<RESULT> {

        private Then<RESULT> then;
        private BiPredicate<? super V1, ? super V2> predicate;

        private Is() {}

        private Is<RESULT> with(Then<RESULT> then, BiPredicate<? super V1, ? super V2> predicate) {
            this.then = then;
            this.predicate = predicate;
            return this;
        }

        public Then<RESULT> then(RESULT result) {
            return then(() -> result);
        }

        public Then<RESULT> then(Supplier<? extends RESULT> result) {
            return then((x, y) -> result.get());
        }

        public Then<RESULT> then(BiFunction<? super V1, ? super V2, ? extends RESULT> result) {
            return then.with(new Pair<>(predicate, result));
        }
    }

    private class Pair<RESULT> {

        final BiPredicate<? super V1, ? super V2> predicate;
        final BiFunction<? super V1, ? super V2, ? extends RESULT> result;

        private Pair(
            BiPredicate<? super V1, ? super V2> predicate,
            BiFunction<? super V1, ? super V2, ? extends RESULT> result) {

            this.predicate = predicate;
            this.result = result;
        }
    }
}
