package zeliba;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import org.junit.jupiter.api.Test;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.TEN;
import static java.math.BigDecimal.ZERO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static zeliba.When.when;

class WhenTest {

    private static final Predicate<Object> TRUE = p -> true;
    private static final Predicate<Object> FALSE = p -> false;

    @Test void is_constant_returnsCovariantResult() {
        int value = 1;

        String string = when(value)
            .is(1).then("1")
            .orElse("?");

        assertEquals("1", string);
    }

    @Test void is_fewConstants_returnsCorrectResult() {
        int value = 1;

        String string = when(value)
            .is(1).then("1")
            .is(2).then("2")
            .is(3).then("3")
            .orElse("?");

        assertEquals("1", string);
    }

    @Test void is_noMatch_returnsElse() {
        int value = 0;

        String string = when(value)
            .is(1).then("1")
            .is(2).then("2")
            .is(3).then("3")
            .orElse("?");

        assertEquals("?", string);
    }

    @Test void is_fewMatches_returnsFirstMatch() {
        int value = 42;

        String string = when(value)
            .is(1).then("1")
            .is(42).then("42-first")
            .is(42).then("42-second")
            .orElse("?");

        assertEquals("42-first", string);
    }

    @Test void is_predicate_returnsCovariantResult() {
        int value = 1;

        String string = when(value)
            .is(i -> i < 0).then("-")
            .is(0).then("0")
            .is(i -> i > 0).then("+")
            .orElse("?");

        assertEquals("+", string);
    }

    @Test void and_predicate_returnsResult() {
        int value = 5;

        String string = when(value)
            .is(v -> v > 0).and(v -> v < 3).then("(0..3)")
            .is(v -> v > 3).and(v -> v < 7).then("(3..7)")
            .orElse("?");

        assertEquals("(3..7)", string);
    }

    @Test void and_covariantPredicate_returnsResult() {
        Predicate<Object> sTrue = p -> true;
        String value = "one";

        String string = when(value)
            .is(TRUE).and(sTrue).and(FALSE).then("1..3")
            .is(TRUE).and(sTrue).and(FALSE).then("1..9")
            .is(TRUE).and(sTrue).and(TRUE).then("expected")
            .is(TRUE).and(sTrue).and(TRUE).then("too late")
            .orElse("?");

        assertEquals("expected", string);
    }

    @Test void or_values_returnsResult() {
        int value = 5;

        String string = when(value)
            .is(0).or(2).or(4).then("0 or 2 or 4")
            .is(1).or(3).or(5).then("1 or 3 or 5")
            .orElse("?");

        assertEquals("1 or 3 or 5", string);
    }

    @Test void and_or_complexExample_returnsResult() {
        int value = 5;

        String string = when(value)
            .is(FALSE).or(FALSE).then("incorrect")
            .is(TRUE).and(FALSE).or(TRUE).then("expected")
            .is(TRUE).and(FALSE).or(FALSE).and(TRUE).then("expected")
            .orElse("?");

        assertEquals("expected", string);
    }

    @Test void complexExample_forDocumentation() {
        int value = 5;

        String string = when(value)
            .is(1).or(2).then("< 3")
            .is(v -> v < 10).and(v -> v > 6)
                .or(5).then("5 or (6;10)")
            .is(v -> v > 0).and(v -> v < 5)
                .or(v -> v > 5).and(v -> v < 10).then("(0;5) or (5;10)")
            .orElse("?");

        assertEquals("5 or (6;10)", string);
    }

    @Test void isNot_returnsCorrectResult() {
        int value = 1;

        String string = when(value)
            .isNot(0).then("not 0")
            .isNot(2).then("not 2")
            .orElse("?");

        assertEquals("not 0", string);
    }

    @Test void asOptional_match_noElse_returnsEmptyOptional() {
        int value = 1;

        Optional<String> string = when(value)
            .is(0).then("0")
            .is(1).then("1")
            .asOptional();

        assertEquals("1", string.get());
    }

    @Test void asOptional_noMatch_noElse_returnsEmptyOptional() {
        int value = 1;

        Optional<String> string = when(value)
            .is(0).then("0")
            .is(2).then("2")
            .asOptional();

        assertFalse(string.isPresent());
    }

    @Test void then_supplier_returnsCovariantResult() {
        int value = 1;

        String string = when(value)
            .is(1).then(() -> "1")
            .is(2).then("2")
            .is(3).then(() -> "3")
            .orElse("?");

        assertEquals("1", string);
    }

    @Test void then_function_returnsCovariantResult() {
        int value = 1;

        String string = when(value)
            .is(1).then(x -> String.valueOf(x + x))
            .is(2).then(x -> String.valueOf(x + x + x))
            .orElse("?");

        assertEquals("2", string);
    }

    @Test void orElse_noMatch_functionsNotCalled() {
        AtomicInteger mock = new AtomicInteger(0);
        int value = 10;

        String string = when(value)
            .is(++value).then(x -> {
                mock.incrementAndGet();
                return "2";
            })
            .orElse("?");

        assertEquals("?", string);
        assertEquals(0, mock.intValue());
    }

    @Test void orElse_noMatch_suppliersNotCalled() {
        AtomicInteger mock = new AtomicInteger(0);
        int value = 10;

        String string = when(value)
            .is(++value).then(() -> {
                mock.incrementAndGet();
                return "2";
            })
            .orElse("?");

        assertEquals("?", string);
        assertEquals(0, mock.intValue());
    }

    @Test void orElseThrow_noMatch_throwsException() {
        IllegalStateException exception = assertThrows(IllegalStateException.class, () ->
            when(ONE)
                .is(TEN).then(ZERO)
                .orElseThrow()
        );
        assertEquals("No matches for value [1]", exception.getMessage());
    }

    @Test void orElseThrow_match_returnsResult() {
        int two = when(1)
            .is(2).then(0)
            .is(1).then(x -> x + x)
            .orElseThrow();

        assertEquals(2, two);
    }

    @Test void complexExample() {
        assertEquals("negative 1", testWhen(-1));
        assertEquals("zero", testWhen(0));
        assertEquals("positive 1", testWhen(1));
        assertEquals("not 42", testWhen(12));
        assertEquals("??", testWhen(42));
        assertThrows(RuntimeException.class, () -> testWhen(100_500));

    }

    private String testWhen(int value) {
        return when(value)
            .is(i -> i < 0).then(i -> String.format("negative %s", -i))
            .is(0).then("zero")
            .is(1).then(() -> String.format("positive %s", value))
            .is(100_500).then(() -> {
                throw new RuntimeException();
            })
            .isNot(42).then("not 42")
            .orElse("??");
    }
}
