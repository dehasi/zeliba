package zeliba;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

import static java.util.Collections.emptyMap;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static zeliba.TheMap.a;
import static zeliba.TheMap.an;
import static zeliba.TheMap.the;

class TheMapTest {

    @Test void isNull() {
        assertFalse(the(emptyMap()).isNull());
        assertTrue(a(null).isNull());
    }

    @Test void isNullOrEmpty() {
        assertTrue(the(emptyMap()).isNullOrEmpty());
        assertTrue(a(null).isNullOrEmpty());
    }

    @Test void isNotNull() {
        assertTrue(an(emptyMap()).isNotNull());
        assertFalse(a(null).isNotNull());
    }

    @Test void isNotEmpty() {
        Map<String, String> map = new HashMap<>();
        map.put("1", "2");
        assertTrue(a(map).isNotEmpty());
        assertFalse(the(emptyMap()).isNotEmpty());
    }
}
