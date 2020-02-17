package me.dehasi.zeliba;

import org.junit.jupiter.api.Test;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static me.dehasi.zeliba.TheCollection.a;
import static me.dehasi.zeliba.TheCollection.an;
import static me.dehasi.zeliba.TheCollection.the;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TheCollectionTest {

    @Test void isNull() {
        assertFalse(the(emptyList()).isNull());
        assertTrue(a(null).isNull());
    }

    @Test void isNullOrEmpty() {
        assertTrue(the(emptyList()).isNullOrEmpty());
        assertTrue(a(null).isNullOrEmpty());
    }

    @Test void isNotNull() {
        assertTrue(an(emptyList()).isNotNull());
        assertFalse(a(null).isNotNull());
    }

    @Test void isNotEmpty() {
        assertFalse(the(emptyList()).isNotEmpty());
        assertTrue(a(singletonList(1)).isNotEmpty());
    }
}