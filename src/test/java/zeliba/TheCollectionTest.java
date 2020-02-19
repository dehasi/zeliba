package zeliba;

import org.junit.jupiter.api.Test;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static zeliba.TheCollection.the;

class TheCollectionTest {

    @Test void isNull() {
        assertFalse(the(emptyList()).isNull());
        assertTrue(the(null).isNull());
    }

    @Test void isNullOrEmpty() {
        assertTrue(the(emptyList()).isNullOrEmpty());
        assertTrue(the(null).isNullOrEmpty());
    }

    @Test void isNotNull() {
        assertTrue(the(emptyList()).isNotNull());
        assertFalse(the(null).isNotNull());
    }

    @Test void isNotEmpty() {
        assertFalse(the(emptyList()).isNotEmpty());
        assertTrue(the(singletonList(1)).isNotEmpty());
    }
}
