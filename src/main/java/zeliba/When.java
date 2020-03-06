package zeliba;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static java.util.Objects.requireNonNull;
import static java.util.function.Predicate.isEqual;

public class When<VALUE> {

    private final VALUE value;

    private When(VALUE value) {
        this.value = requireNonNull(value);
    }

    public static <VALUE> When<VALUE> when(VALUE value) {
        return new When<>(value);
    }

    public RawIs is(VALUE value) {
        return is(isEqual(value));
    }

    public RawIs isNot(VALUE value) {
        return is(isEqual(value).negate());
    }

    public RawIs is(Predicate<? super VALUE> predicate) {
        return new RawIs(requireNonNull(predicate));
    }

    public class RawIs {

        private Predicate<? super VALUE> predicate;

        private RawIs(Predicate<? super VALUE> predicate) {
            this.predicate = predicate;
        }

        public RawIs and(Predicate<? super VALUE> predicate) {
            this.predicate = ((Predicate<VALUE>)this.predicate).and(predicate);
            return this;
        }

        public <RESULT> Then<RESULT> then(RESULT result) {
            return then(() -> result);
        }

        public <RESULT> Then<RESULT> then(Supplier<? extends RESULT> result) {
            return then(x -> result.get());
        }

        public <RESULT> Then<RESULT> then(Function<? super VALUE, ? extends RESULT> result) {
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

        public Is<RESULT> is(VALUE value) {
            return is(isEqual(value));
        }

        public Is<RESULT> isNot(VALUE value) {
            return is(isEqual(value).negate());
        }

        public Is<RESULT> is(Predicate<? super VALUE> predicate) {
            return is.with(this, predicate);
        }

        public RESULT orElse(RESULT other) {
            return orElse(() -> other);
        }

        public RESULT orElse(Supplier<? extends RESULT> other) {
            requireNonNull(other);
            return orElse(x -> other.get());
        }

        public RESULT orElse(Function<? super VALUE, ? extends RESULT> other) {
            requireNonNull(other);

            return asOptional().orElse(other.apply(value));
        }

        public RESULT orElseThrow() {
            return orElseThrow(String.format("No matches for value [%s]", value));
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
                .filter(pair1 -> pair1.predicate.test(value))
                .<RESULT>map(pair1 -> pair1.result.apply(value))
                .findFirst();
        }
    }

    public class Is<RESULT> {

        private Then<RESULT> then;
        private Predicate<? super VALUE> predicate;

        private Is() {}

        private Is<RESULT> with(Then<RESULT> then, Predicate<? super VALUE> predicate) {
            this.then = then;
            this.predicate = predicate;
            return this;
        }

        public Is<RESULT> and(Predicate<? super VALUE> predicate) {
            this.predicate = ((Predicate<VALUE>)this.predicate).and(predicate);
            return this;
        }

        public Then<RESULT> then(RESULT result) {
            return then(() -> result);
        }

        public Then<RESULT> then(Supplier<? extends RESULT> result) {
            return then(x -> result.get());
        }

        public Then<RESULT> then(Function<? super VALUE, ? extends RESULT> result) {
            return then.with(new Pair<>(predicate, result));
        }
    }

    private class Pair<RESULT> {

        final Predicate<? super VALUE> predicate;
        final Function<? super VALUE, ? extends RESULT> result;

        private Pair(Predicate<? super VALUE> predicate, Function<? super VALUE, ? extends RESULT> result) {
            this.predicate = predicate;
            this.result = result;
        }
    }
}
