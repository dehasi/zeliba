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
    private final When<ARGUMENT> when = this;
    private final List<Predicate<ARGUMENT>> predicates = new ArrayList<>();
    private final List<Function<? super ARGUMENT, ?>> results = new ArrayList<>();

    private When(ARGUMENT argument) {
        this.argument = argument;
    }

    public static <ARGUMENT> When<ARGUMENT> when(ARGUMENT argument) {
        return new When<>(argument);
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

        Optional<Integer> index = predicateIndex();
        if (index.isPresent()) {
            return (RESULT)results.get(index.get()).apply(argument);
        }
        return other.apply(argument);
    }

    public Is is(ARGUMENT argument) {
        return is(arg -> Objects.equals(arg, argument));
    }

    public Is is(Predicate<ARGUMENT> predicate) {
        predicates.add(requireNonNull(predicate));
        return new Is(requireNonNull(predicate));
    }

    private Optional<Integer> predicateIndex() {
        for (int i = 0; i < predicates.size(); i++) {
            if (predicates.get(i).test(argument))
                return Optional.of(i);
        }
        return Optional.empty();
    }

    public class Is {
        private final Predicate<ARGUMENT> predicate;

        private Is(Predicate<ARGUMENT> predicate) {
            this.predicate = predicate;
        }

        public <RESULT> ParametrisedIs<RESULT> then(RESULT result) {
            return then(() -> result);
        }

        public <RESULT> ParametrisedIs<RESULT> then(Supplier<? extends RESULT> result) {
            return then(x -> result.get());
        }

        public <RESULT> ParametrisedIs<RESULT> then(Function<? super ARGUMENT, ? extends RESULT> result) {
            return new ParametrisedIs<>(predicate, result);
        }

        private <RESULT> ParametrisedIs<RESULT> then(Predicate<ARGUMENT> predicate,
            Function<? super ARGUMENT, ? extends RESULT> result) {
            return new ParametrisedIs<>(predicate, result);
        }

    }

    public class ParametrisedIs<RESULT> {
        List<Pair<RESULT>> pairs = new ArrayList<>();

        public ParametrisedIs(Predicate<ARGUMENT> predicate, Function<? super ARGUMENT, ? extends RESULT> result) {
            pairs.add(new Pair<>(predicate, result));
        }

        public ParametrisedIs(List<Pair<RESULT>> pairs) {
            this.pairs = pairs;
        }

        public ParametrisedThen<RESULT> is(ARGUMENT argument) {
            return is(arg -> Objects.equals(arg, argument));
        }

        public ParametrisedThen<RESULT> is(Predicate<ARGUMENT> predicate) {
            return new ParametrisedThen<RESULT>(pairs, predicate);
        }

        public RESULT orElse(RESULT other) {
            return orElse(() -> other);
        }

        public RESULT orElse(Supplier<? extends RESULT> other) {
            requireNonNull(other);
            return orElse(x -> other.get());
        }

        public RESULT orElse(Function<? super ARGUMENT, RESULT> other) {
            requireNonNull(other);

            for (Pair<RESULT> pair : pairs) {
                if (pair.predicate.test(argument))
                    return pair.result.apply(argument);
            }
            return other.apply(argument);
        }
    }

    public class ParametrisedThen<RESULT> {
        private final List<Pair<RESULT>> pairs;
        private final Predicate<ARGUMENT> predicate;

        private ParametrisedThen(List<Pair<RESULT>> pairs, Predicate<ARGUMENT> predicate) {
            this.pairs = pairs;
            this.predicate = predicate;
        }

        public ParametrisedIs<RESULT> then(RESULT result) {
            return then(() -> result);
        }

        public ParametrisedIs<RESULT> then(Supplier<? extends RESULT> result) {
            return then(x -> result.get());
        }

        public ParametrisedIs<RESULT> then(Function<? super ARGUMENT, ? extends RESULT> result) {
            pairs.add(new Pair<>(predicate, result));
            return new ParametrisedIs<RESULT>(pairs);
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
