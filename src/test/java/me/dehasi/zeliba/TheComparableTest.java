package me.dehasi.zeliba;

import org.junit.jupiter.api.Test;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;
import static me.dehasi.zeliba.TheComparable.the;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TheComparableTest {

    @Test void isGreater_oneIsGreaterZero_true() {
        assertTrue(the(ONE).isGreater(ZERO));
    }

    @Test void isGreater_zeroIsGreaterOne_false() {
        assertFalse(the(ZERO).isGreater(ONE));
    }

}
