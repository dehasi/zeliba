package zeliba.the;

import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.Test;

import static java.util.Collections.emptyMap;
import static java.util.Collections.singletonMap;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static zeliba.the.TheMap.entry;
import static zeliba.the.TheMap.the;

class TheMapTest {

    private final Map<String, String> map = singletonMap("1", "2");

    @Test void isEmpty() {
        assertTrue(the(emptyMap()).isEmpty());
        assertTrue(the(null).isEmpty());

        assertFalse(the(map).isEmpty());
    }

    @Test void isNotEmpty() {
        assertTrue(the(map).isNotEmpty());

        assertFalse(the(emptyMap()).isNotEmpty());
        assertFalse(the(null).isNotEmpty());
    }

    @Test void contains() {
        assertTrue(the(map).contains("1", "2"));
        assertTrue(the(map).contains(entry("1", "2")));

        assertFalse(the(map).contains("2", "2"));
        assertFalse(the(map).contains(entry("2", "2")));

        assertFalse(the(map).contains("1", "3"));
        assertFalse(the(map).contains(entry("1", "3")));
    }

    @Test void get_valuePresents_returnsOptionalOfValue() {
        Optional<String> optionalValue = the(map).get("1");

        assertTrue(optionalValue.isPresent());
        assertEquals("2", optionalValue.get());
    }

    @Test void get_noValue_returnsEmptyOptional() {
        Optional<String> optionalValue = the(map).get("2");

        assertFalse(optionalValue.isPresent());
    }

    @Test void get_nullValue_returnsEmptyOptional() {
        String value = null;
        Optional<String> optionalValue = the(singletonMap("2", value)).get("2");

        assertFalse(optionalValue.isPresent());
    }
}
