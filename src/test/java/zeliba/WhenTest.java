package zeliba;

import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.Test;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.TEN;
import static java.math.BigDecimal.ZERO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static zeliba.When.when;

class WhenTest {

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

    @Test void isNot_returnsCorrectResult() {
        int value = 1;

        String string = when(value)
            .isNot(0).then("not 0")
            .isNot(2).then("not 2")
            .orElse("?");

        assertEquals("not 0", string);
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
        assertEquals("No matches for argument [1]", exception.getMessage());
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
        assertEquals("?", testWhen(42));
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
            .orElse("?");
    }
}
