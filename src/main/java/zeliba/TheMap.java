package zeliba;

import java.util.Map;

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
}
