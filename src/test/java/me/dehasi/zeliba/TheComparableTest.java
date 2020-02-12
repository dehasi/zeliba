package me.dehasi.zeliba;

import java.math.BigDecimal;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;
import static me.dehasi.zeliba.TheComparable.a;
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

    @MethodSource("bigDecimals")
    @ParameterizedTest void isLessThan(BigDecimal val) {
        assertTrue(the(ZERO).isLessThan(val));
    }

    @MethodSource("bigDecimals")
    @ParameterizedTest void withMethodSource(BigDecimal val) {
        assertTrue(a(val).isEqualsTo(val));
    }

    private static Stream<Arguments> bigDecimals() {
        return Stream.generate(() -> Arguments.of(random(43))).limit(40);

    }

    private static BigDecimal random(int range) {
        BigDecimal max = new BigDecimal(range);
        BigDecimal randFromDouble = new BigDecimal(Math.random());
        BigDecimal actualRandomDec = randFromDouble.multiply(max);
        return actualRandomDec.setScale(2, BigDecimal.ROUND_DOWN);
    }
}
