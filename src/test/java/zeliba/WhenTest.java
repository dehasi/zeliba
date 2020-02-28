package zeliba;

import java.math.BigDecimal;
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

    @Test void is_fewConstant_returnsCorrectResult() {
        int value = 1;
        String string = when(value)
            .is(1).then("1")
            .is(2).then("2")
            .is(3).then("3")
            .orElse("?");

        assertEquals("1", string);
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
}
