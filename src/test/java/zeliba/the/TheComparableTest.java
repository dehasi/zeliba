package zeliba.the;

import java.math.BigDecimal;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static java.lang.Math.abs;
import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.TEN;
import static java.math.BigDecimal.ZERO;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static zeliba.the.TheComparable.the;

class TheComparableTest {

    @MethodSource("bigDecimals")
    @ParameterizedTest void isEqualsTo(BigDecimal val) {
        assertTrue(the(val).isEqualsTo(val));
        assertFalse(the(val).isEqualsTo(val.add(ONE)));
    }

    @MethodSource("bigDecimals")
    @ParameterizedTest void isGreaterThan(BigDecimal val) {
        assertTrue(the(val.add(TEN)).isGreaterThan(val));
        assertFalse(the(val).isGreaterThan(val.add(ONE)));
    }

    @MethodSource("bigDecimals")
    @ParameterizedTest void isGreaterOrEqualsThan(BigDecimal val) {
        assertTrue(the(val).isGreaterOrEqualsThan(val));
        assertTrue(the(val.add(TEN)).isGreaterOrEqualsThan(val));
        assertFalse(the(val).isGreaterOrEqualsThan(val.add(TEN)));
    }

    @MethodSource("bigDecimals")
    @ParameterizedTest void isLessThan(BigDecimal val) {
        assertTrue(the(val).isLessThan(val.add(TEN)));
        assertFalse(the(val.add(ONE)).isLessThan(val));
    }

    @MethodSource("bigDecimals")
    @ParameterizedTest void isLessOrEqualsThan(BigDecimal val) {
        assertTrue(the(val).isLessOrEqualsThan(val));
        assertTrue(the(val).isLessOrEqualsThan(val.add(TEN)));
        assertFalse(the(val.add(TEN)).isLessOrEqualsThan(val));
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
        BigDecimal randFromDouble = new BigDecimal(abs(Math.random()));
        BigDecimal actualRandomDec = randFromDouble.multiply(max);
        return actualRandomDec.setScale(2, BigDecimal.ROUND_DOWN);
    }
}
