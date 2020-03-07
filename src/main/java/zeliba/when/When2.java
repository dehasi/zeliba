package zeliba.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Supplier;

import static java.util.Objects.requireNonNull;

public class When2<V1, V2> {

    private final V1 first;
    private final V2 second;

    public When2(V1 first, V2 second) {
        this.first = requireNonNull(first);
        this.second = requireNonNull(second);
    }

    public static <V1, V2> When2<V1, V2> when(V1 v1, V2 v2) {
        return new When2<>(v1, v2);
    }

    public RawIs is(V1 v1, V2 v2) {
        BiPredicate<V1, V2> equals = (x1, x2) -> Objects.equals(x1, v1) && Objects.equals(x2, v2);
        return is(equals);
    }

    public RawIs isNot(V1 v1, V2 v2) {
        BiPredicate<V1, V2> equals = (x1, x2) -> Objects.equals(x1, v1) && Objects.equals(x2, v2);
        return is(equals.negate());
    }

    public RawIs is(BiPredicate<? super V1, ? super V2> predicate) {
        return new RawIs(requireNonNull(predicate));
    }

    public class RawIs {

        private BiPredicate<? super V1, ? super V2> predicate;

        private RawIs(BiPredicate<? super V1, ? super V2> predicate) {
            this.predicate = predicate;
        }

//        public RawIs and(BiPredicate<? super V1, ? super V2> predicate) {
//            this.predicate = ((Predicate<V1>)this.predicate).and(predicate);
//            return this;
//        }
//
//        public RawIs or(V1 v1, V2 v2) {
//            return or((a,b) -> Objects.equals(v1, v2) && Objects.equals(v1, v2));
//        }
//
//        public RawIs or(BiPredicate<? super V1, ? super V2> predicate) {
//            this.predicate = ((Predicate<V1>)this.predicate).and(predicate);
//            return this;
//        }

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
            BiPredicate<V1, V2> equals = (x1, x2) -> Objects.equals(x1, v1) && Objects.equals(x2, v2);
            return is(equals.negate());
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

            return asOptional().orElse(other.apply(first, second));
        }

        public RESULT orElseThrow() {
            return orElseThrow(String.format("No matches for values [%s] [%s]", first, second));
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
                .filter(pair1 -> pair1.predicate.test(first, second))
                .<RESULT>map(pair1 -> pair1.result.apply(first, second))
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

//        public Is<RESULT> and(Predicate<? super V1> predicate) {
//            this.predicate = ((Predicate<V1>)this.predicate).and(predicate);
//            return this;
//        }
//
//        public Is<RESULT> or(V1 value) {
//            return or(isEqual(value));
//        }
//
//        public Is<RESULT> or(Predicate<? super V1> predicate) {
//            this.predicate = ((Predicate<V1>)this.predicate).or(predicate);
//            return this;
//        }

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
