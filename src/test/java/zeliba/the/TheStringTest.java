package zeliba.the;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static zeliba.the.TheString.the;

class TheStringTest {

    @Test void substring_returnsMaxPossible() {
        assertEquals(the("abcdefgh").substring(-1, 20), "abcdefgh");
        assertEquals(the("abcdefgh").substring(5, 20), "fgh");
        assertEquals(the("abcdefgh").substring(-1, 3), "abc");
    }

    @Test void substring_notInInterval_returnsEmptyString() {
        assertEquals(the("abcdefgh").substring(5, 3), "");
        assertEquals(the("abcdefgh").substring(10, 15), "");
        assertEquals(the("abcdefgh").substring(-1, 0), "");
    }
}
