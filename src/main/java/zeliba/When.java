package zeliba;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static java.util.Objects.requireNonNull;
import static java.util.function.Predicate.isEqual;

public class When<ARGUMENT> {

    private final ARGUMENT argument;

    private When(ARGUMENT argument) {
        this.argument = requireNonNull(argument);
    }

    public static <ARGUMENT> When<ARGUMENT> when(ARGUMENT argument) {
        return new When<>(argument);
    }

    public RawIs is(ARGUMENT argument) {
        return is(isEqual(argument));
    }

    public RawIs isNot(ARGUMENT argument) {
        return is(isEqual(argument).negate());
    }

    public RawIs is(Predicate<? super ARGUMENT> predicate) {
        return new RawIs(requireNonNull(predicate));
    }

    public class RawIs {

        private final Predicate<? super ARGUMENT> predicate;

        private RawIs(Predicate<? super ARGUMENT> predicate) {
            this.predicate = predicate;
        }

        public <RESULT> Then<RESULT> then(RESULT result) {
            return then(() -> result);
        }

        public <RESULT> Then<RESULT> then(Supplier<? extends RESULT> result) {
            return then(x -> result.get());
        }

        public <RESULT> Then<RESULT> then(Function<? super ARGUMENT, ? extends RESULT> result) {
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

        public Is<RESULT> is(ARGUMENT argument) {
            return is(isEqual(argument));
        }

        public Is<RESULT> isNot(ARGUMENT argument) {
            return is(isEqual(argument).negate());
        }

        public Is<RESULT> is(Predicate<? super ARGUMENT> predicate) {
            return is.with(this, predicate);
        }

        public RESULT orElse(RESULT other) {
            return orElse(() -> other);
        }

        public RESULT orElse(Supplier<? extends RESULT> other) {
            requireNonNull(other);
            return orElse(x -> other.get());
        }

        public RESULT orElse(Function<? super ARGUMENT, ? extends RESULT> other) {
            requireNonNull(other);

            return result().orElse(other.apply(argument));
        }

        public RESULT orElseThrow() {
            return orElseThrow(String.format("No matches for argument [%s]", argument));
        }

        public RESULT orElseThrow(String message) {
            return orElseThrow(() -> new IllegalStateException(message));
        }

        public <EXCEPTION extends Throwable> RESULT orElseThrow(
            Supplier<? extends EXCEPTION> exceptionSupplier) throws EXCEPTION {

            return result().orElseThrow(exceptionSupplier);
        }

        private Optional<RESULT> result() {
            return pairs.stream()
                .filter(pair1 -> pair1.predicate.test(argument))
                .<RESULT>map(pair1 -> pair1.result.apply(argument))
                .findFirst();
        }
    }

    public class Is<RESULT> {

        private Then<RESULT> then;
        private Predicate<? super ARGUMENT> predicate;

        private Is() {}

        private Is<RESULT> with(Then<RESULT> then, Predicate<? super ARGUMENT> predicate) {
            this.then = then;
            this.predicate = predicate;
            return this;
        }

        public Then<RESULT> then(RESULT result) {
            return then(() -> result);
        }

        public Then<RESULT> then(Supplier<? extends RESULT> result) {
            return then(x -> result.get());
        }

        public Then<RESULT> then(Function<? super ARGUMENT, ? extends RESULT> result) {
            return then.with(new Pair<>(predicate, result));
        }
    }

    private class Pair<RESULT> {

        final Predicate<? super ARGUMENT> predicate;
        final Function<? super ARGUMENT, ? extends RESULT> result;

        private Pair(Predicate<? super ARGUMENT> predicate, Function<? super ARGUMENT, ? extends RESULT> result) {
            this.predicate = predicate;
            this.result = result;
        }
    }
}
