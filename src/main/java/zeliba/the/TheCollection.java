package zeliba.the;

import java.util.Collection;

public class TheCollection {

    private final Collection<?> collection;

    private TheCollection(Collection<?> collection) {
        this.collection = collection;
    }

    public static TheCollection the(Collection<?> value) {
        return new TheCollection(value);
    }

    public boolean isNull() {
        return collection == null;
    }

    public boolean isNullOrEmpty() {
        return isNull() || collection.isEmpty();
    }

    public boolean isNotNull() {
        return !isNull();
    }

    public boolean isNotEmpty() {
        return !collection.isEmpty();
    }
}
