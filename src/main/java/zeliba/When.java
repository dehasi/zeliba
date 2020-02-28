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
    private final Is is = new Is();
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
        return is;
    }

    private Optional<Integer> predicateIndex() {
        for (int i = 0; i < predicates.size(); i++) {
            if (predicates.get(i).test(argument))
                return Optional.of(i);
        }
        return Optional.empty();
    }

    public class Is {

        public <RESULT> When<ARGUMENT> then(RESULT result) {
            return then(() -> result);
        }

        public <RESULT> When<ARGUMENT> then(Supplier<? extends RESULT> result) {
            return then(x -> result.get());
        }

        public <RESULT> When<ARGUMENT> then(Function<? super ARGUMENT, ? extends RESULT> result) {
            results.add(result);
            return when;
        }
    }
}
