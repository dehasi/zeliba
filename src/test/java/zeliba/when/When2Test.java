package zeliba.when;

import java.math.BigDecimal;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.TEN;
import static java.math.BigDecimal.ZERO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static zeliba.the.TheComparable.the;
import static zeliba.when.When2.when;

class When2Test {

    private static final Predicate<Object> TRUE = p -> true;
    private static final Predicate<Object> FALSE = p -> false;
    private static final BiPredicate<Object, Object> BI_TRUE = (p1, p2) -> true;
    private static final BiPredicate<Object, Object> BI_FALSE = (p1, p2) -> false;

    @Test void is_constant_returnsCovariantResult() {
        int x = 1;
        int y = 1;

        String string = when(x, y)
            .is(0, 0).then("1,2")
            .is(1, 2).then("2,1")
            .is(2, 1).then("2,1")
            .is(2, 2).then("2,2")
            .is(1, 1).then("1,1")
            .is(1, 1).then("too late")
            .orElse("?");

        assertEquals("1,1", string);
    }

    @Test void is_notMatches_returnsElse() {
        int x = 1;
        int y = 1;

        assertEquals("?", when(x, y).is(0, 0).then("fail").orElse("?"));
        assertEquals("?", when(x, y).is(0, 1).then("fail").orElse("?"));
        assertEquals("?", when(x, y).is(1, 0).then("fail").orElse("?"));
        assertEquals("+", when(x, y).is(1, 1).then("+").orElse("?"));

    }

    @Test void is_fewMatches_returnsFirst() {
        int x = 1;
        int y = 1;

        String string = when(x, y)
            .is(1, 1).then("expected")
            .is(1, 1).then("too late")
            .orElse("?");

        assertEquals("expected", string);
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

    @Test void is_biPredicate_fewMatches_returnsFirs() {
        int x = 1;
        int y = 1;

        String string = when(x, y)
            .is((v1, v2) -> v1 + v2 > 0).then("expected")
            .is((v1, v2) -> v1 + v2 > 0).then("too late")
            .orElseThrow();

        assertEquals("expected", string);
    }

    @Test void is_twoPredicates_returnsCorrectResult() {
        BigDecimal x = ONE;
        int y = -2;

        String string = when(x, y)
            .is(FALSE, FALSE).then("I Quadrant")
            .is(p -> the(p).isGreaterThan(ZERO), p -> p > 0).then("I Quadrant")
            .is(p -> the(p).isLessThan(ZERO), p -> p > 0).then("II Quadrant")
            .is(p -> the(p).isLessThan(ZERO), p -> p < 0).then("III Quadrant")
            .is(p -> the(p).isGreaterThan(ZERO), p -> p < 0).then("IV Quadrant")
            .orElse("zero");

        assertEquals("IV Quadrant", string);
    }

    @MethodSource("predicates")
    @ParameterizedTest void is_twoPredicates_returnsCorrectResult(Predicate<Object> p1, Predicate<Object> p2) {
        String string = when(ONE, TEN)
            .is(p1, p2).then("not match")
            .is(TRUE, TRUE).then("expected")
            .orElseThrow();

        assertEquals("expected", string);
    }

    @Test void isNot_constants_returnsCorrectResult() {
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

    @Test void isNot_constantsFirstMatch_returnsOnlyAllConstantMatches() {
        int x = 1;
        int y = 1;

        String string = when(x, y)
            .isNot(1, 2).then("x != 1 and y != 1")
            .isNot(2, 2).then("x != 2 and y != 2")
            .orElseThrow();

        assertEquals("x != 2 and y != 2", string);
    }

    @Test void isNot_constantsSecondMatch_returnsOnlyAllConstantMatches() {
        int x = 1;
        int y = 1;

        String string = when(x, y)
            .isNot(2, 1).then("x != 1 and y != 1")
            .isNot(2, 2).then("x != 2 and y != 2")
            .orElseThrow();

        assertEquals("x != 2 and y != 2", string);
    }

    @Test void isNot_fewMatches_returnsFirstMatch() {
        int x = 1;
        int y = 1;

        String string = when(x, y)
            .isNot(2, 2).then("x != 2 and y != 2")
            .isNot(2, 2).then("too late")
            .orElseThrow();

        assertEquals("x != 2 and y != 2", string);
    }

    private static Stream<Arguments> predicates() {
        return Stream.of(Arguments.of(FALSE, FALSE), Arguments.of(TRUE, FALSE), Arguments.of(FALSE, TRUE));
    }
}
