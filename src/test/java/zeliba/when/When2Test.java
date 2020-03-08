package zeliba.when;

import java.math.BigDecimal;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import org.junit.jupiter.api.Test;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.TEN;
import static java.math.BigDecimal.ZERO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static zeliba.the.TheComparable.the;
import static zeliba.when.When.when;

class When2Test {

    private static final Predicate<Object> TRUE = p -> true;
    private static final Predicate<Object> FALSE = p -> false;
    public static final BiPredicate<Integer, Integer> BI_TRUE = (a1, b1) -> true;
    public static final BiPredicate<Integer, Integer> BI_FALSE = (a, b) -> false;

    @Test void is_constant_returnsCovariantResult() {
        int x = 1, y = 1;

        String result = when(x, y)
            .is(0, 0).then("0,0")
            .is(1, 2).then("2,1")
            .is(2, 1).then("2,1")
            .is(2, 2).then("2,2")
            .is(1, 1).then("1,1")
            .is(1, 1).then("too late")
            .orElse("?");

        assertEquals("1,1", result);
    }

    @Test void is_notMatches_returnsElse() {
        int x = 1, y = 1;

        assertEquals("?", when(x, y).is(0, 0).then("fail").orElse("?"));
        assertEquals("?", when(x, y).is(0, 1).then("fail").orElse("?"));
        assertEquals("?", when(x, y).is(1, 0).then("fail").orElse("?"));
        assertEquals("+", when(x, y).is(1, 1).then("+").orElse("?"));

    }

    @Test void is_biPredicate_returnsCovariantResult() {
        int x = 1, y = 1;

        String result = when(x, y)
            .is((v1, v2) -> v1 + v2 == 0).then("x+y=0")
            .is((v1, v2) -> v1 + v2 < 0).then("x+y<0")
            .is((v1, v2) -> v1 + v2 > 0).then("x+y>0")
            .orElseThrow();

        assertEquals("x+y>0", result);
    }

    @Test void is_twoPredicates_returnsCorrectResult() {
        BigDecimal x = ONE;
        int y = -2;

        String result = when(x, y)
            .is(FALSE, FALSE).then("I Quadrant")
            .is(p -> the(p).isGreaterThan(ZERO), p -> p > 0).then("I Quadrant")
            .is(p -> the(p).isLessThan(ZERO), p -> p > 0).then("II Quadrant")
            .is(p -> the(p).isLessThan(ZERO), p -> p < 0).then("III Quadrant")
            .is(p -> the(p).isGreaterThan(ZERO), p -> p < 0).then("IV Quadrant")
            .orElse("zero");

        assertEquals("IV Quadrant", result);
    }

    @Test void is_predicates_notMatches_returnsElse() {

        assertEquals("match", when(ONE, TEN)
            .is(TRUE, TRUE).then("match").is(TRUE, TRUE).then("to late")
            .orElseThrow());
        assertEquals("match", when(ONE, TEN).is(FALSE, TRUE).then("fail").orElse("match"));
        assertEquals("match", when(ONE, TEN).is(TRUE, FALSE).then("fail").orElse("match"));
        assertEquals("match", when(ONE, TEN).is(FALSE, FALSE).then("fail").orElse("match"));
    }

    @Test void isNot_constants_returnsCorrectResult() {
        int x = 1, y = 1;

        String result = when(x, y)
            .isNot(1, 1).then("x != 1 and y != 1")
            .isNot(1, 2).then("x != 1 and y != 2")
            .isNot(2, 1).then("x != 2 and y != 1")
            .isNot(2, 2).then("x != 2 and y != 2")
            .orElseThrow();

        assertEquals("x != 2 and y != 2", result);
    }

    @Test void isNot_constantsFirstMatch_returnsOnlyAllConstantMatches() {
        int x = 1, y = 1;

        assertEquals("match", when(x, y).isNot(1, 1).then("fail").orElse("match"));
        assertEquals("match", when(x, y).isNot(1, 2).then("fail").orElse("match"));
        assertEquals("match", when(x, y).isNot(2, 1).then("fail").orElse("match"));

        assertEquals("match", when(x, y).isNot(2, 2).then("match").orElse("fail"));
    }

    @Test void and_biPredicate() {
        int x = 1, y = 1;

        assertEquals("match", when(x, y).is(BI_TRUE).and(BI_TRUE).then("match").orElse("fail"));

        assertEquals("match", when(x, y).is(BI_TRUE).and(BI_FALSE).then("fail").orElse("match"));
        assertEquals("match", when(x, y).is(BI_FALSE).and(BI_TRUE).then("fail").orElse("match"));
        assertEquals("match", when(x, y).is(BI_FALSE).and(BI_FALSE).then("fail").orElse("match"));
    }

    @Test void and_twoPredicates() {
        int x = 1, y = 1;

        assertEquals("match", when(x, y).is(BI_TRUE).and(TRUE, TRUE).then("match").orElse("fail"));

        assertEquals("match", when(x, y).is(BI_TRUE).and(BI_FALSE).then("fail").orElse("match"));
        assertEquals("match", when(x, y).is(BI_TRUE).and(FALSE, TRUE).then("fail").orElse("match"));
        assertEquals("match", when(x, y).is(BI_TRUE).and(TRUE, FALSE).then("fail").orElse("match"));
        assertEquals("match", when(x, y).is(BI_TRUE).and(FALSE, FALSE).then("fail").orElse("match"));

        assertEquals("match", when(x, y).is(BI_FALSE).and(TRUE, TRUE).then("fail").orElse("match"));
        assertEquals("match", when(x, y).is(BI_FALSE).and(FALSE, TRUE).then("fail").orElse("match"));
        assertEquals("match", when(x, y).is(BI_FALSE).and(TRUE, FALSE).then("fail").orElse("match"));
        assertEquals("match", when(x, y).is(BI_FALSE).and(FALSE, FALSE).then("fail").orElse("match"));
    }

    @Test void and_twoPredicatesSecondMatch() {
        int x = 1, y = 1;

        assertEquals("match", when(x, y).is(BI_FALSE).then("fail").is(BI_TRUE).and(TRUE, TRUE).then("match").orElse("fail"));

        assertEquals("match", when(x, y).is(BI_FALSE).then("fail").is(BI_TRUE).and(BI_FALSE).then("fail").orElse("match"));
        assertEquals("match", when(x, y).is(BI_FALSE).then("fail").is(BI_TRUE).and(FALSE, TRUE).then("fail").orElse("match"));
        assertEquals("match", when(x, y).is(BI_FALSE).then("fail").is(BI_TRUE).and(TRUE, FALSE).then("fail").orElse("match"));
        assertEquals("match", when(x, y).is(BI_FALSE).then("fail").is(BI_TRUE).and(FALSE, FALSE).then("fail").orElse("match"));

        assertEquals("match", when(x, y).is(BI_FALSE).then("fail").is(BI_FALSE).and(TRUE, TRUE).then("fail").orElse("match"));
        assertEquals("match", when(x, y).is(BI_FALSE).then("fail").is(BI_FALSE).and(FALSE, TRUE).then("fail").orElse("match"));
        assertEquals("match", when(x, y).is(BI_FALSE).then("fail").is(BI_FALSE).and(TRUE, FALSE).then("fail").orElse("match"));
        assertEquals("match", when(x, y).is(BI_FALSE).then("fail").is(BI_FALSE).and(FALSE, FALSE).then("fail").orElse("match"));
    }
}
