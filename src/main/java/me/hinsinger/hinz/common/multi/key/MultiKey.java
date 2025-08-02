package me.hinsinger.hinz.common.multi.key;

import java.util.Arrays;

public class MultiKey {
    private final Object[] keys;

    public MultiKey(Object... keys) {
        if (keys == null || keys.length == 0) throw new IllegalArgumentException("MultiKey must have at least one component");
        this.keys = Arrays.copyOf(keys, keys.length);
    }

    public Object get(int index) {
        if (index < 0 || index >= keys.length) throw new IndexOutOfBoundsException("Index " + index);
        return keys[index];
    }

    public int size() {
        return keys.length;
    }
}
