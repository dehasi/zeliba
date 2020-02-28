package zeliba;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static java.util.Objects.requireNonNull;

public class When<ARGUMENT> {

    private final ARGUMENT argument;

    private When(ARGUMENT argument) {
        this.argument = argument;
    }

    public static <ARGUMENT> When<ARGUMENT> when(ARGUMENT argument) {
        return new When<>(argument);
    }

    public RawIs is(ARGUMENT argument) {
        return is(arg -> Objects.equals(arg, argument));
    }

    public RawIs is(Predicate<ARGUMENT> predicate) {
        return new RawIs(requireNonNull(predicate));
    }

    public <RESULT> RESULT orElse(RESULT other) {
        return orElse(() -> other);
    }

    public <RESULT> RESULT orElse(Supplier<? extends RESULT> other) {
        requireNonNull(other);
        return orElse(x -> other.get());
    }

    public <RESULT> RESULT orElse(Function<? super ARGUMENT, RESULT> other) {
        requireNonNull(other);
        return other.apply(argument);
    }

    public class RawIs {
        private final Predicate<ARGUMENT> predicate;

        private RawIs(Predicate<ARGUMENT> predicate) {
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

        private Then(Pair<RESULT> result) {
            pairs.add(result);
        }

        private Then(List<Pair<RESULT>> pairs) {
            this.pairs = pairs;
        }

        public Is<RESULT> is(ARGUMENT argument) {
            return is(arg -> Objects.equals(arg, argument));
        }

        public Is<RESULT> is(Predicate<ARGUMENT> predicate) {
            return new Is<RESULT>(pairs, predicate);
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

            for (Pair<RESULT> pair : pairs) {
                if (pair.predicate.test(argument))
                    return pair.result.apply(argument);
            }
            return other.apply(argument);
        }
    }

    public class Is<RESULT> {
        private final List<Pair<RESULT>> pairs;
        private final Predicate<ARGUMENT> predicate;

        private Is(List<Pair<RESULT>> pairs, Predicate<ARGUMENT> predicate) {
            this.pairs = pairs;
            this.predicate = predicate;
        }

        public Then<RESULT> then(RESULT result) {
            return then(() -> result);
        }

        public Then<RESULT> then(Supplier<? extends RESULT> result) {
            return then(x -> result.get());
        }

        public Then<RESULT> then(Function<? super ARGUMENT, ? extends RESULT> result) {
            pairs.add(new Pair<>(predicate, result));
            return new Then<RESULT>(pairs);
        }
    }

    private class Pair<RESULT> {
        final Predicate<ARGUMENT> predicate;
        final Function<? super ARGUMENT, ? extends RESULT> result;

        Pair(Predicate<ARGUMENT> predicate,
            Function<? super ARGUMENT, ? extends RESULT> result) {
            this.predicate = predicate;
            this.result = result;
        }
    }
}
