package me.dehasi.zeliba;

import org.junit.jupiter.api.Test;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;
import static me.dehasi.zeliba.TheComparable.the;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TheComparableTest {

    @Test void isGreaterThan_oneIsGreaterZero_true() {
        assertTrue(the(ONE).isGreaterThan(ZERO));
    }

    @Test void isGreaterThan_zeroIsGreaterOne_false() {
        assertFalse(the(ZERO).isGreaterThan(ONE));
    }

    @Test void isLessThan_oneIsLessThanZero_false() {
        assertFalse(the(ONE).isLessThan(ZERO));
    }

    @Test void isLessThan_zeroIsLessThanOne_true() {
        assertTrue(the(ZERO).isLessThan(ONE));
    }
}
