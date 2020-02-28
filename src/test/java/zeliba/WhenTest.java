package zeliba;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.Test;

import static java.math.BigDecimal.ONE;
import static org.junit.jupiter.api.Assertions.assertEquals;
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

    @Test void orElse_constant_returnsCovariantResult() {
        String string = when(ONE)
            .orElse("1");

        int integer = when(ONE)
            .orElse(1);

        assertEquals("1", string);
        assertEquals(1, integer);
    }

    @Test void orElse_supplier_returnsCovariantResult() {
        String string = when(ONE)
            .orElse(() -> "1");

        int integer = when(ONE)
            .orElse(() -> 1);

        assertEquals("1", string);
        assertEquals(1, integer);
    }

    @Test void orElse_function_returnsCovariantResult() {
        BigDecimal two = when(ONE)
            .orElse(x -> x.add(x));

        int length = when("123456")
            .orElse(String::length);

        assertEquals(new BigDecimal("2"), two);
        assertEquals(6, length);
    }

    @Test void complexExample() {
        assertEquals("negative 1", testWhen(-1));
        assertEquals("zero", testWhen(0));
        assertEquals("positive supplier", testWhen(1));
        assertEquals("?", testWhen(100_500));
    }

    private String testWhen(int value) {
        return when(value)
            .is(i -> i < 0).then(i -> String.format("negative %s", -i))
            .is(0).then("zero")
            .is(1).then(() -> "positive supplier")
            .orElse("?");
    }
}
