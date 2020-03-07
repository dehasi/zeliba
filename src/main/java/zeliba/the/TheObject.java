package zeliba.the;

import java.util.Objects;

public class TheObject {

    private final Object value;

    private TheObject(Object value) {
        this.value = value;
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
