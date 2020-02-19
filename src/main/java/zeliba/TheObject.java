package zeliba;

import java.util.Objects;

public class TheObject {

    private final Object value;

    private TheObject(Object value) {
        this.value = value;
    }

    public static TheObject a(Object value) {
        return the(value);
    }

    public static TheObject an(Object value) {
        return the(value);
    }

    public static TheObject the(Object value) {
        return new TheObject(value);
    }

    public boolean isNull() {
        return value == null;
    }

    public boolean isNotNull() {
        return !isNull();
    }

    public boolean isNotEqualTo(Object that) {
        return !Objects.equals(value, that);
    }
}
