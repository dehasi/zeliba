package zeliba.when;

import java.math.BigDecimal;
import java.util.function.BiPredicate;
import org.junit.jupiter.api.Test;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static zeliba.the.TheComparable.the;
import static zeliba.when.When2.when;

class When2Test {

    private static final BiPredicate<Object, Object> TRUE = (p1, p2) -> true;
    private static final BiPredicate<Object, Object> FALSE = (p1, p2) -> false;

    @Test void is_constant_returnsCovariantResult() {
        int x = 1;
        int y = 1;

        String string = when(x, y)
            .is(1, 2).then("1,2")
            .is(2, 1).then("2,1")
            .is(2, 2).then("2,2")
            .is(1, 1).then("1,1")
            .is(1, 1).then("too late")
            .orElse("?");

        assertEquals("1,1", string);
    }

    @Test void is_biPredicate_returnsCovariantResult() {
        int x = 1;
        int y = 1;

        String string = when(x, y)
            .is((v1, v2) -> v1 + v2 == 0).then("x+y=0")
            .is((v1, v2) -> v1 + v2 < 0).then("x+y<0")
            .is((v1, v2) -> v1 + v2 > 0).then("x+y>0")
            .orElseThrow();

        assertEquals("x+y>0", string);
    }

    @Test void is_twoPredicates_returnsCorrectResult() {
        BigDecimal x = ONE;
        int y = -2;

        String string = when(x, y)
            .is(p -> the(p).isGreaterThan(ZERO), p -> p > 0).then("I Quadrant")
            .is(p -> the(p).isLessThan(ZERO), p -> p > 0).then("II Quadrant")
            .is(p -> the(p).isLessThan(ZERO), p -> p < 0).then("III Quadrant")
            .is(p -> the(p).isGreaterThan(ZERO), p -> p < 0).then("IV Quadrant")
            .orElse("zero");

        assertEquals("IV Quadrant", string);
    }

    @Test void isNot_biPredicate_returnsCovariantResult() {
        int x = 1;
        int y = 1;

        String string = when(x, y)
            .isNot(1, 1).then("x != 1 and y != 1")
            .isNot(1, 2).then("x != 1 and y != 2")
            .isNot(2, 1).then("x != 2 and y != 1")
            .isNot(2, 2).then("x != 2 and y != 2")
            .orElseThrow();

        assertEquals("x != 2 and y != 2", string);
    }
}
