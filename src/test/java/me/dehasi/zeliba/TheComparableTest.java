package me.dehasi.zeliba;

import java.math.BigDecimal;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static java.math.BigDecimal.TEN;
import static me.dehasi.zeliba.TheComparable.a;
import static me.dehasi.zeliba.TheComparable.the;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TheComparableTest {

    @MethodSource("bigDecimals")
    @ParameterizedTest void isEqualsTo(BigDecimal val) {
        assertTrue(a(val).isEqualsTo(val));
    }

    @MethodSource("bigDecimals")
    @ParameterizedTest void isGreaterThan(BigDecimal val) {
        assertTrue(the(val.multiply(TEN)).isGreaterThan(val));
    }

    @MethodSource("bigDecimals")
    @ParameterizedTest void isGreaterOrEqualsThan(BigDecimal val) {
        assertTrue(the(val).isGreaterOrEqualsThan(val));
        assertTrue(the(val.multiply(TEN)).isGreaterOrEqualsThan(val));
    }

    @MethodSource("bigDecimals")
    @ParameterizedTest void isLessThan(BigDecimal val) {
        assertTrue(the(val).isLessThan(val.multiply(TEN)));
    }

    @MethodSource("bigDecimals")
    @ParameterizedTest void isLessOrEqualsThan(BigDecimal val) {
        assertTrue(the(val).isLessOrEqualsThan(val));
        assertTrue(the(val).isLessOrEqualsThan(val.multiply(TEN)));
    }

    @MethodSource("bigDecimals")
    @ParameterizedTest void inInterval(BigDecimal val) {
        assertTrue(the(val).inTheInterval().fromIncluded(val).toIncluded(val));
//        assertTrue(the(val).isLessOrEqualsThan(val.multiply(TEN)));
    }


    private static Stream<Arguments> bigDecimals() {
        return Stream.generate(() -> Arguments.of(random(42))).limit(40);
    }

    private static BigDecimal random(int range) {
        BigDecimal max = new BigDecimal(range);
        BigDecimal randFromDouble = new BigDecimal(Math.random());
        BigDecimal actualRandomDec = randFromDouble.multiply(max);
        return actualRandomDec.setScale(2, BigDecimal.ROUND_DOWN);
    }
}
