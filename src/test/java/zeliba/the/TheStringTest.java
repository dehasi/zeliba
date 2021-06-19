package zeliba.the;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static zeliba.the.TheString.the;

class TheStringTest {

    @Test void isEmpty() {
        assertTrue(the(null).isEmpty());
        assertTrue(the("").isEmpty());

        assertFalse(the(" ").isEmpty());
        assertFalse(the(" aa").isEmpty());
        assertFalse(the(" aa ").isEmpty());
    }

    @Test void isBlank() {
        assertTrue(the(null).isBlank());
        assertTrue(the("").isBlank());
        assertTrue(the(" ").isBlank());

        assertFalse(the(" aa").isBlank());
        assertFalse(the(" aa ").isBlank());
    }

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
