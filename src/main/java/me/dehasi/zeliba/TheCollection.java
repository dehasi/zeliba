package me.dehasi.zeliba;

import java.util.Collection;

public class TheCollection<COLLECTION extends Collection<?>> {

    private final COLLECTION collection;

    private TheCollection(COLLECTION collection) {
        this.collection = collection;
    }

    public static <COLLECTION extends Collection<?>> TheCollection a(COLLECTION value) {
        return the(value);
    }

    public static <COLLECTION extends Collection<?>> TheCollection an(COLLECTION value) {
        return the(value);
    }

    public static <COLLECTION extends Collection<?>> TheCollection the(COLLECTION value) {
        return new TheCollection<>(value);
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
