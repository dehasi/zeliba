package zeliba;

import java.math.BigDecimal;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.TEN;
import static java.math.BigDecimal.ZERO;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static zeliba.TheComparable.the;

class TheComparableTest {

    @MethodSource("bigDecimals")
    @ParameterizedTest void isEqualsTo(BigDecimal val) {
        assertTrue(the(val).isEqualsTo(val));
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

    @Test void isInTheInterval_included_included() {
        assertTrue(the(ONE).isInTheInterval().fromIncluded(ONE).toIncluded(ONE));
        assertFalse(the(ZERO).isInTheInterval().fromIncluded(ONE).toIncluded(TEN));
        assertFalse(the(TEN).isInTheInterval().fromIncluded(ZERO).toIncluded(ONE));
    }

    @Test void isInTheInterval_included_excluded() {
        assertTrue(the(ONE).isInTheInterval().fromIncluded(ONE).toExcluded(TEN));
        assertFalse(the(TEN).isInTheInterval().fromIncluded(ZERO).toExcluded(ONE));
        assertFalse(the(ZERO).isInTheInterval().fromIncluded(ONE).toExcluded(ONE));
    }

    @Test void isInTheInterval_excluded_included() {
        assertTrue(the(ONE).isInTheInterval().fromExcluded(ZERO).toIncluded(ONE));
        assertFalse(the(ONE).isInTheInterval().fromExcluded(ONE).toIncluded(TEN));
        assertFalse(the(TEN).isInTheInterval().fromExcluded(ZERO).toIncluded(ONE));
    }

    @Test void isInTheInterval_excluded_excluded() {
        assertTrue(the(ONE).isInTheInterval().fromExcluded(ZERO).toExcluded(TEN));
        assertFalse(the(ONE).isInTheInterval().fromExcluded(ZERO).toExcluded(ONE));
        assertFalse(the(ONE).isInTheInterval().fromExcluded(ONE).toExcluded(TEN));
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
