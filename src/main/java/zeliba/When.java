package zeliba;

import java.util.function.Function;
import java.util.function.Supplier;

public class When<ARGUMENT> {

    private final ARGUMENT argument;
    private final When<ARGUMENT> thiz;

    private When(ARGUMENT argument) {
        this.argument = argument;
        thiz = this;
    }

    public static <ARGUMENT> When<ARGUMENT> when(ARGUMENT argument) {
        return new When<>(argument);
    }



    public <RESULT> RESULT orElse(RESULT other) {
        return orElse(() -> other);
    }

    public <RESULT> RESULT orElse(Supplier<RESULT> other) {
        return other.get();
    }

    public <RESULT> RESULT orElse(Function<? super ARGUMENT, ? extends RESULT> other) {
        return other.apply(argument);
    }

    public IsCondition is(ARGUMENT argument) {
        return new IsCondition();
    }

    public class IsCondition {

        public <RESULT>  When<ARGUMENT> then(RESULT result){
            return thiz;
        }

        public <RESULT> RESULT orElse(RESULT other) {
            return orElse(() -> other);
        }

        public <RESULT> RESULT orElse(Supplier<RESULT> other) {
            return other.get();
        }

        public <RESULT> RESULT orElse(Function<? super ARGUMENT, ? extends RESULT> other) {
            return other.apply(argument);
        }
    }
}
