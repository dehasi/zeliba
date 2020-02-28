package zeliba;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static java.util.Objects.requireNonNull;

public class When<ARGUMENT> {

    private final ARGUMENT argument;

    private When(ARGUMENT argument) {
        this.argument = requireNonNull(argument);
    }

    public static <ARGUMENT> When<ARGUMENT> when(ARGUMENT argument) {
        return new When<>(argument);
    }

    public RawIs is(ARGUMENT argument) {
        return is(arg -> Objects.equals(arg, argument));
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
        private List<Pair<RESULT>> pairs = new ArrayList<>();
        private Is<RESULT> is;

        private Then(Pair<RESULT> result) {
            pairs.add(result);
        }

        private Then(List<Pair<RESULT>> pairs) {
            this.pairs = pairs;
        }

        public Is<RESULT> is(ARGUMENT argument) {
            return is(arg -> Objects.equals(arg, argument));
        }

        public Is<RESULT> is(Predicate<? super ARGUMENT> predicate) {
            return is2(predicate);
        }

        private Is<RESULT> is2(Predicate<? super ARGUMENT> predicate) {
            if (is == null) {
                is = new Is<RESULT>(pairs, predicate);
            }
            return is.with(pairs, predicate);
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

        public <EXCEPTION extends Throwable> RESULT orElseThrow(
            Supplier<? extends EXCEPTION> exceptionSupplier) throws EXCEPTION {

            return result().orElseThrow(exceptionSupplier);
        }

        private Optional<RESULT> result() {
            for (Pair<RESULT> pair : pairs) {
                if (pair.predicate.test(argument))
                    return Optional.of(pair.result.apply(argument));
            }
            return Optional.empty();
        }
    }

    public class Is<RESULT> {
        private List<Pair<RESULT>> pairs;
        private Predicate<? super ARGUMENT> predicate;

        private Is(List<Pair<RESULT>> pairs, Predicate<? super ARGUMENT> predicate) {
            this.pairs = pairs;
            this.predicate = predicate;
        }

        private Is<RESULT> with(List<Pair<RESULT>> pairs, Predicate<? super ARGUMENT> predicate) {
            this.pairs = pairs;
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
            pairs.add(new Pair<RESULT>(predicate, result));
            return new Then<RESULT>(pairs);
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
