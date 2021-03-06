package zeliba.the;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static zeliba.the.TheObject.the;

class TheObjectTest {

    @Test void nullObject() {
        assertTrue(the(null).isNull());
        assertFalse(the(null).isNotNull());
    }

    @Test void notNullObject() {
        assertTrue(the("1").isNotNull());
        assertFalse(the("2").isNull());
    }

    @Test void isNotEqualTo() {
        assertTrue(the("11").isNotEqualTo("1"));
        assertFalse(the("1").isNotEqualTo("1"));
    }
}
