package zeliba;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class When<ARGUMENT> {

    private final ARGUMENT argument;
    private final When<ARGUMENT> when = this;
    private final Is is = new Is();
    private final List<Predicate<ARGUMENT>> predicates = new ArrayList<>();
    private final List results = new ArrayList();

    private When(ARGUMENT argument) {
        this.argument = argument;
    }

    public static <ARGUMENT> When<ARGUMENT> when(ARGUMENT argument) {
        return new When<>(argument);
    }

    public <RESULT> RESULT orElse(RESULT other) {
        return orElse(() -> other);
    }

    public <RESULT> RESULT orElse(Supplier<RESULT> other) {
        Optional<Integer> index = doPredicate();
        return (RESULT)index.map(i -> results.get(i)).orElse(other.get());
    }

    private Optional<Integer> doPredicate() {
        for (int i = 0; i < predicates.size(); i++) {
            if (predicates.get(i).test(argument))
                return Optional.of(i);
        }
        return Optional.empty();
    }

    public <RESULT> RESULT orElse(Function<? super ARGUMENT, ? extends RESULT> other) {
        return other.apply(argument);
    }

    public Is is(ARGUMENT argument) {
        return is(arg -> Objects.equals(arg, argument));
    }

    public Is is(Predicate<ARGUMENT> predicate) {
        predicates.add(predicate);
        return is;
    }

    public class Is {

        public <RESULT> When<ARGUMENT> then(RESULT result) {
            results.add(result);
            return when;
        }
    }
}
