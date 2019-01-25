package com.szepe.peter.jcstress;

import java.util.HashMap;
import java.util.Map;

public final class BadDesignLazyLoadMap {

    private volatile Map<Integer, Integer> map = null;
    private final int size;

    public BadDesignLazyLoadMap(int size) {
        this.size = size;
    }

    public Map<Integer, Integer> getMap() {
        if (map == null) {
            synchronized (this) {
                if (map == null) {
                    map = new HashMap<>();
                    for (int i = 0; i < size; ++i) {
                        map.put(i, i);
                    }
                }
            }
        }
        return map;
    }
}
