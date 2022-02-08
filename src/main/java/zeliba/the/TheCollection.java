package zeliba.the;

import java.util.Collection;

import static java.util.Collections.emptyList;

public class TheCollection {

    private final Collection<?> collection;

    private TheCollection(Collection<?> collection) {
        this.collection = collection != null ? collection : emptyList();
    }

    public static TheCollection the(Collection<?> value) {
        return new TheCollection(value);
    }

    public boolean isEmpty() {
        return collection.isEmpty();
    }

    public boolean isNotEmpty() {
        return !collection.isEmpty();
    }
}
