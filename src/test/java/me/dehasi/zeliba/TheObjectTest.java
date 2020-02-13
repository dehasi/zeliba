package me.dehasi.zeliba;

import org.junit.jupiter.api.Test;

import static me.dehasi.zeliba.TheObject.a;
import static me.dehasi.zeliba.TheObject.an;
import static me.dehasi.zeliba.TheObject.the;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        assertTrue(an("11").isNotEqualTo("1"));
        assertFalse(a("1").isNotEqualTo("1"));
    }
}
