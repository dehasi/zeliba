package zeliba.when;

import java.util.function.BiPredicate;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static zeliba.when.When2.when;

class When2Test {

    private static final BiPredicate<Object, Object> TRUE = (p1, p2) -> true;
    private static final BiPredicate<Object, Object> FALSE = (p1, p2) -> false;

    @Test void is_constant_returnsCovariantResult() {
        int x = 1;
        int y = 1;

        String string = when(x, y)
            .is(1, 1).then("11")
            .orElse("?");

        assertEquals("11", string);
    }

    @Test void is_biPredicate_returnsCovariantResult() {
        int x = 1;
        int y = 1;

        String string = when(x, y)
            .is((o1, o2) -> o1 == o2).then("eq")
            .orElse("nq");

        assertEquals("eq", string);
    }

    @Test void is_twoPredicates_returnsCovariantResult() {
        int x = 1;
        int y = 2;

        String string = when(x, y)
            .is(2,2).then("2,2")
            .is(o -> o == 1, o -> o == 2).then("1,2")
            .orElse("nq");

        assertEquals("1,2", string);
    }
}
