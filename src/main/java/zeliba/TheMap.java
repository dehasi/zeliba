package zeliba;

import java.util.AbstractMap;
import java.util.Map;
import java.util.Objects;

public class TheMap<MAP extends Map<?, ?>> {

    private final MAP map;

    private TheMap(MAP map) {
        this.map = map;
    }

    public static <MAP extends Map<?, ?>> TheMap the(MAP value) {
        return new TheMap<>(value);
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


    public boolean contains(Object key, Object value) {
        return Objects.equals(map.get(key), value);
    }

    public <KEY, VALUE> boolean contains(Map.Entry<KEY, VALUE> entry) {
        return contains(entry.getKey(), entry.getValue());
    }

    public static <KEY, VALUE> Map.Entry<KEY, VALUE> entry(KEY key, VALUE value) {
        return new AbstractMap.SimpleEntry<>(key, value);
    }
}
