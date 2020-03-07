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

    @Test void is_twoPredicates_returnsCorrectResult() {
        int x = 1;
        int y = 2;

        String string = when(x, y)
            .is(p -> p > 0, p -> p > 0).then("I Quadrant")
            .is(p -> p < 0, p -> p > 0).then("II Quadrant")
            .is(p -> p < 0, p -> p < 0).then("III Quadrant")
            .is(p -> p > 0, p -> p < 0).then("IV Quadrant")
            .orElse("zero");

        assertEquals("I Quadrant", string);
    }
}
