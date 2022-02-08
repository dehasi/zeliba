package zeliba.the;

import org.junit.jupiter.api.Test;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static zeliba.the.TheCollection.the;

class TheCollectionTest {

    @Test void isEmpty() {
        assertTrue(the(emptyList()).isEmpty());
        assertTrue(the(null).isEmpty());

        assertFalse(the(singletonList(1)).isEmpty());
    }

    @Test void isNotEmpty() {
        assertTrue(the(singletonList(1)).isNotEmpty());

        assertFalse(the(null).isNotEmpty());
        assertFalse(the(emptyList()).isNotEmpty());
    }
}
