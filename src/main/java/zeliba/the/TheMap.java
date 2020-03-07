package zeliba.the;

import java.util.AbstractMap.SimpleEntry;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;

public class TheMap<KEY, VALUE> {

    private final Map<KEY, VALUE> map;

    private TheMap(Map<KEY, VALUE> map) {
        this.map = map;
    }

    public static <KEY, VALUE> TheMap<KEY, VALUE> the(Map<KEY, VALUE> map) {
        return new TheMap<>(map);
    }

    public boolean isNull() {
        return map == null;
    }

    public boolean isNullOrEmpty() {
        return isNull() || map.isEmpty();
    }

    public boolean isNotNull() {
        return !isNull();
    }

    public boolean isNotEmpty() {
        return !map.isEmpty();
    }

    public boolean contains(KEY key, Object value) {
        return Objects.equals(map.get(key), value);
    }

    public boolean contains(Entry<KEY, VALUE> entry) {
        return contains(entry.getKey(), entry.getValue());
    }

    public Optional<VALUE> get(KEY key) {
        return Optional.ofNullable(map.get(key));
    }

    public static <KEY, VALUE> Entry<KEY, VALUE> entry(KEY key, VALUE value) {
        return new SimpleEntry<>(key, value);
    }
}
