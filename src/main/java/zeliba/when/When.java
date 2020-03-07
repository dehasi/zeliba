package zeliba.when;

public class When {

    public static <V> When1<V> when(V value) {
        return When1.when(value);
    }

    public static <V1, V2> When2<V1, V2> when(V1 v1, V2 v2) {
        return When2.when(v1, v2);
    }
}
