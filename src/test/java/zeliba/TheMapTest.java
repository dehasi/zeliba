package zeliba;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

import static java.util.Collections.emptyMap;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static zeliba.TheMap.the;

class TheMapTest {

    @Test void isNull() {
        assertFalse(the(emptyMap()).isNull());
        assertTrue(the(null).isNull());
    }

    @Test void isNullOrEmpty() {
        assertTrue(the(emptyMap()).isNullOrEmpty());
        assertTrue(the(null).isNullOrEmpty());
    }

    @Test void isNotNull() {
        assertTrue(the(emptyMap()).isNotNull());
        assertFalse(the(null).isNotNull());
    }

    @Test void isNotEmpty() {
        Map<String, String> map = new HashMap<>();
        map.put("1", "2");
        assertTrue(the(map).isNotEmpty());
        assertFalse(the(emptyMap()).isNotEmpty());
    }
}
