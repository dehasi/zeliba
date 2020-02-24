package zeliba;

import java.util.Collections;
import java.util.Map;
import org.junit.jupiter.api.Test;

import static java.util.Collections.emptyMap;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static zeliba.TheMap.entry;
import static zeliba.TheMap.the;

class TheMapTest {

    private final Map<String, String> map = Collections.singletonMap("1", "2");

    @Test void isNull() {
        assertFalse(the(emptyMap()).isNull());
        assertTrue(the(null).isNull());
    }

    @Test void isNullOrEmpty() {
        assertTrue(the(emptyMap()).isNullOrEmpty());
        assertTrue(the(null).isNullOrEmpty());
        assertFalse(the(map).isNullOrEmpty());
    }

    @Test void isNotNull() {
        assertTrue(the(emptyMap()).isNotNull());
        assertFalse(the(null).isNotNull());
    }

    @Test void isNotEmpty() {
        assertTrue(the(map).isNotEmpty());
        assertFalse(the(emptyMap()).isNotEmpty());
    }

    @Test
    void contains() {
        assertTrue(the(map).contains("1", "2"));
        assertTrue(the(map).contains(entry("1", "2")));

        assertFalse(the(map).contains("2", "2"));
        assertFalse(the(map).contains(entry("2", "2")));

        assertFalse(the(map).contains("1", "3"));
        assertFalse(the(map).contains(entry("1", "3")));
    }
}
